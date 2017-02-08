package com.asimadu.util;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.xml.bind.DatatypeConverter;

public class StringRefiner {

    @SuppressWarnings("serial")
    static public class InvalidHashException extends Exception {
        public InvalidHashException(String message) {
            super(message);
        }
        public InvalidHashException(String message, Throwable source) {
            super(message, source);
        }
    }

    @SuppressWarnings("serial")
    static public class CannotPerformOperationException extends Exception {
        public CannotPerformOperationException(String message) {
            super(message);
        }
        public CannotPerformOperationException(String message, Throwable source) {
            super(message, source);
        }
    }

    static final String string = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
    
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    // These constants may be changed without breaking existing hashes.
    public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 18;
    public static final int PBKDF2_ITERATIONS = 64000;

    // These constants define the encoding and may not be changed.
    public static final int HASH_SECTIONS = 5;
    public static final int HASH_ALGORITHM_INDEX = 0;
    public static final int ITERATION_INDEX = 1;
    public static final int HASH_SIZE_INDEX = 2;
    public static final int SALT_INDEX = 3;
    public static final int PBKDF2_INDEX = 4;

    public static String createHash(String password) throws CannotPerformOperationException {
        return createHash(password.toCharArray());
    }

    public static String createHash(char[] password) throws CannotPerformOperationException {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        int hashSize = hash.length;

        // format: algorithm:iterations:hashSize:salt:hash
        String parts = "sha1:" +
            PBKDF2_ITERATIONS +
            ":" + hashSize +
            ":" +
            toBase64(salt) +
            ":" +
            toBase64(hash);
        return parts;
    }
    
    
    public static String refinePassword(String userPassword) throws CannotPerformOperationException{
    	String hashedPassword = createHash(userPassword);
    	String pass = "";
		String[] params = hashedPassword.split(":");
		
		for(int i=0; i<params[4].length(); i++){;
			pass += params[3].charAt(i) +""+ params[4].charAt(i);
		}
	
		pass += params[3].substring(SALT_BYTE_SIZE, SALT_BYTE_SIZE+8);

		return pass;
    }
    
    public static boolean verifyPassword(String submittedPassword, String passwordFromDatabase){
    	String hash = "";
    	String salt = "";
    	
		for(int i=0; i<(SALT_BYTE_SIZE*2); i++){
			if(i%2 == 0)
				salt += passwordFromDatabase.charAt(i);
			else
				hash += passwordFromDatabase.charAt(i);
		}
		
		salt += passwordFromDatabase.substring(48, 56);
		
		String parts = "sha1:" +
	            PBKDF2_ITERATIONS +
	            ":" + HASH_BYTE_SIZE +
	            ":" +
	            salt +
	            ":" +
	            hash;
		try {
			return verifyPasswordInternal(submittedPassword, parts);
		} catch (CannotPerformOperationException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidHashException e) {
			e.printStackTrace();
			return false;
		}
    } 
    
    public static String getRandomStringAndHashed() {
    	String randomString = "";
    	String hashedString = "";
    	
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<128;i++){
			sb.append(string.charAt(rnd.nextInt(string.length())));
		}

		randomString = sb.toString();
		//System.out.println(randomString);
		
		try {
			hashedString = refinePassword(sb.toString());
			//System.out.println(hashedString);

		} catch (CannotPerformOperationException e) {
			e.printStackTrace();
		}
		
		return randomString+":"+hashedString;
    }
    
    public static String getShortCode(int length){
    	String num = "0123456789";
    	StringBuilder sb = new StringBuilder();
		for(int i=0;i<length;i++){
			sb.append(num.charAt(rnd.nextInt(num.length())));
		}
		
		if(sb.toString().charAt(0) == '0')
			return sb.toString().replaceFirst("0", "6"); 
		else {
			return sb.toString();
		} 			
    }

    private static boolean verifyPasswordInternal(String password, String correctHash) throws CannotPerformOperationException, InvalidHashException {
        return verifyPassword(password.toCharArray(), correctHash);
    }

    private static boolean verifyPassword(char[] password, String correctHash) throws CannotPerformOperationException, InvalidHashException {
        // Decode the hash into its parameters
        String[] params = correctHash.split(":");
        if (params.length != HASH_SECTIONS) {
            throw new InvalidHashException(
                "Fields are missing from the password hash."
            );
        }

        // Currently, Java only supports SHA1.
        if (!params[HASH_ALGORITHM_INDEX].equals("sha1")) {
            throw new CannotPerformOperationException(
                "Unsupported hash type."
            );
        }

        int iterations = 0;
        try {
            iterations = Integer.parseInt(params[ITERATION_INDEX]);
        } catch (NumberFormatException ex) {
            throw new InvalidHashException(
                "Could not parse the iteration count as an integer.",
                ex
            );
        }

        if (iterations < 1) {
            throw new InvalidHashException(
                "Invalid number of iterations. Must be >= 1."
            );
        }


        byte[] salt = null;
        try {
            salt = fromBase64(params[SALT_INDEX]);
        } catch (IllegalArgumentException ex) {
            throw new InvalidHashException(
                "Base64 decoding of salt failed.",
                ex
            );
        }

        byte[] hash = null;
        try {
            hash = fromBase64(params[PBKDF2_INDEX]);
        } catch (IllegalArgumentException ex) {
            throw new InvalidHashException(
                "Base64 decoding of pbkdf2 output failed.",
                ex
            );
        }


        int storedHashSize = 0;
        try {
            storedHashSize = Integer.parseInt(params[HASH_SIZE_INDEX]);
        } catch (NumberFormatException ex) {
            throw new InvalidHashException(
                "Could not parse the hash size as an integer.",
                ex
            );
        }

        if (storedHashSize != hash.length) {
            throw new InvalidHashException(
                "Hash length doesn't match stored hash length."
            );
        }

        // Compute the hash of the provided password, using the same salt, 
        // iteration count, and hash length
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);
    }


    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws CannotPerformOperationException {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new CannotPerformOperationException(
                "Hash algorithm not supported.",
                ex
            );
        } catch (InvalidKeySpecException ex) {
            throw new CannotPerformOperationException(
                "Invalid key spec.",
                ex
            );
        }
    }

    private static byte[] fromBase64(String hex) throws IllegalArgumentException {
        return DatatypeConverter.parseBase64Binary(hex);
    }

    private static String toBase64(byte[] array) {
        return DatatypeConverter.printBase64Binary(array);
    }

}