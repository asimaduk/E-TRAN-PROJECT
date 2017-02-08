package com.asimadu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextListener;

import com.asimadu.util.Mailer;
import com.asimadu.util.SMSSender;
import com.asimadu.util.StringRefiner;
import com.asimadu.util.StringRefiner.CannotPerformOperationException;

public class DataAccess implements ServletContextListener{
	private static Connection conn = null;
	
	public static String baseUrl = null;
	public static String profilePicturesFolder = null;
	public static String supportedBanks = null;
	
	public static String twilioAccountSID = null;
	public static String twilioAuthToken = null;
	
	public DataAccess() {
		Context env;
		String url = null;
		String driver = null;
		String username = null;
		String password = null;
		
		try {
			env = (Context) new InitialContext().lookup("java:comp/env");
			url = (String) env.lookup("url");
			driver = (String) env.lookup("driver");
			username = (String) env.lookup("username");
			password = (String) env.lookup("password");
			
			baseUrl = (String) env.lookup("baseUrl");
			supportedBanks = (String) env.lookup("supportedBanks");
			profilePicturesFolder = (String) env.lookup("profilePicturesFolder");
			
			twilioAccountSID = (String) env.lookup("twilioAccountSID");
			twilioAuthToken = (String) env.lookup("twilioAuthToken");
			
			Class.forName(driver).newInstance();
	    	conn = DriverManager.getConnection(url,username,password);	     
			
		} catch (NamingException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        System.out.println("Data access constructor..."); 
	}
	
	public static int addUser(User user, String emailTo){
		int outcome = 0;
		try {
			conn.setAutoCommit(false);
			
			CallableStatement cbstmt = conn.prepareCall("{call addUser(?,?,?,?,?,?,?,?,?,?,?) }");
			
			cbstmt.setString(1, user.getFirstname());
	        cbstmt.setString(2, user.getLastname());
	        cbstmt.setString(3, user.getEmail());
	        cbstmt.setString(4, user.getPhoneNumber());
	        cbstmt.setString(5, user.getGender());
	        cbstmt.setString(6, user.getUsername());
	        cbstmt.setString(7, user.getPassword());
	        cbstmt.setString(8, user.getAddress());
	        cbstmt.setString(9, user.getImageID());
	        
	        String randomStringAndHashed = StringRefiner.getRandomStringAndHashed();
	        String identifier = randomStringAndHashed.split(":")[0];
	        String hashed = randomStringAndHashed.split(":")[1];
	        
	        cbstmt.setString(10, hashed);
	        
	        String shortCode = StringRefiner.getShortCode(4);
	        cbstmt.setString(11, shortCode);
	       
//	        if(!user.getBanks().isEmpty()){
//	        	System.out.println("Registering banks...");
//	        	
//	        	CallableStatement cs = conn.prepareCall("{call addBankAccount(?,?,?)}");
//	        	String banks[] = user.getBanks().split("/");
//	        	for(int i=0; i<banks.length; i++){
//	        		String bank[] = banks[0].split(":");
//	        		cs.setString(1, user.getFirstname()+" "+user.getLastname());
//	        		cs.setString(2, bank[0]);
//	        		cs.setString(3, bank[1]);
//	        		if(cs.executeUpdate() == 1){
//	        			System.out.println("Bank addition successfull: id-"+i);
//	        		} else
//	        			System.out.println("bank addition failed: id-"+i);
//	        	}
//	        } else {
//	        	System.out.println("No banks...");
//	        }
//	        
//	        if(!user.getMobiles().isEmpty()){
//	        	System.out.println("Registering mobiles...");
//	        } else {
//	        	System.out.println("No mobiles...");
//	        }
	        
//	        if((cbstmt.executeUpdate() == 1) 
//					&& (SMSSender.Send("Hi "+user.getFirstname()+", your E-TRAN registration was successful: account activation code is "
//					+shortCode+", provide this in activating your account and anytime when transacting.", user.getPhoneNumber()) == 1) 
//					&& Mailer.send(emailTo,identifier,1))
//			{
	        
			if((cbstmt.executeUpdate() == 1))
			{
				cbstmt = conn.prepareCall("{call getUserID(?)}");
				cbstmt.setString(1, user.getUsername());
				
				ResultSet rs = cbstmt.executeQuery();
				String uid = "";
				
				if(rs.next()){
					 uid = rs.getString("rec_id");
					 
					 if(!user.getBanks().isEmpty()){
				        	System.out.println("Registering banks...");
				        	
				        	cbstmt = conn.prepareCall("{call addBankAccount(?,?,?,?)}");
				        	String banks[] = user.getBanks().split("/");
				        	for(int i=0; i<banks.length; i++){
				        		String bank[] = banks[i].split(":");
				        		cbstmt.setString(1, user.getFirstname()+" "+user.getLastname());
				        		cbstmt.setString(2, bank[0]);
				        		cbstmt.setString(3, bank[1]);
				        		cbstmt.setInt(4, Integer.parseInt(uid));
				        		
				        		if(cbstmt.executeUpdate() == 1){
				        			System.out.println("Bank addition successfull: id-"+i);
				        		} else
				        			System.out.println("bank addition failed: id-"+i);
				        	}
				        } else {
				        	System.out.println("No banks...");
				        }
					 
					 	if(!user.getMobiles().isEmpty()){
				        	System.out.println("Registering mobiles...");
				        	
				        	cbstmt = conn.prepareCall("{call addMobileAccount(?,?,?)}");
				        	String mobiles[] = user.getMobiles().split("/");
				        	
				        	for(int i=0; i<mobiles.length; i++){
				        		cbstmt.setString(1, "PENDING");
				        		cbstmt.setString(2, mobiles[i]);
				        		cbstmt.setInt(3, Integer.parseInt(uid));
				        		
				        		if(cbstmt.executeUpdate() == 1){
				        			System.out.println("Mobile addition successfull: id-"+i);
				        		} else
				        			System.out.println("Mobile addition failed: id-"+i);
				        	}
				        } else {
				        	System.out.println("No mobiles...");
				        }
				} else {
					System.out.println("No user id found...");
				}
				
				if((SMSSender.Send("Hi "+user.getFirstname()+", your E-TRAN registration was successful: account activation code is "
						+shortCode+", provide this in activating your account and anytime when transacting.", user.getPhoneNumber()) == 1) 
						&& Mailer.send(emailTo,identifier,1))
				{
					System.out.println("database, sms and email sent succesfully");
		    		outcome = 1;
		        	conn.commit();
				} else {
					System.out.println("Notifications failed...");
				}
			
				
	        } else {
	        	conn.rollback();
	        	System.out.println("executeUpdate, sms or email failed, check internet connection");
	        }
		
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
		
		return outcome;
	}
	
	public static User getUser(int userID){
		User user = null;
		
		try {
			CallableStatement cbstmt = conn.prepareCall("{call getUser_ID(?) }");
			cbstmt.setInt(1, userID);
			ResultSet result = cbstmt.executeQuery();
			
			if(result.next()){
				user = new User(result.getInt(1),result.getString(2));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return user;
	}
	
	public static UserDetails getUser(String username, String password){
		UserDetails userDetails = null;
		try {
			CallableStatement cbstmt = conn.prepareCall("{call getUser_Username(?) }");
			cbstmt.setString(1, username);
			ResultSet result = cbstmt.executeQuery();

			if(result.next() 
					&& (username.equals(result.getString("username")))
					&& (result.getString("password").length() == 56) 
					&& (StringRefiner.verifyPassword(password, result.getString("password")))) 
			{
				userDetails = new UserDetails();
				userDetails.setFirstname(result.getString("firstname"));
				userDetails.setMobileNumber(result.getString("phone"));
				userDetails.setIsAdmin(result.getInt("isAdmin"));
				userDetails.setImageId(result.getString("image_id"));
				
				String recID = result.getString("rec_id");
				
				userDetails.setUserBankAccounts(getUserBankAccounts(recID));
				userDetails.setUserMobileAccounts(getUserMobileAccounts(recID));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return userDetails;
	}
	
	public static ArrayList<UserBankAccount> getUserBankAccounts(String recID){
		ArrayList<UserBankAccount> bankAccounts = null;
		try {
			CallableStatement cbstmt = conn.prepareCall("{call getBankAccount(?) }");
			cbstmt.setString(1, recID);
			ResultSet result = cbstmt.executeQuery();
			
			while(result.next()){
				if(bankAccounts == null)
					bankAccounts = new ArrayList<>();
				
				bankAccounts.add(new UserBankAccount(result.getString("name"), 
						result.getLong("id"), 
						result.getString("bank"), 
						result.getString("branch"), 
						result.getString("type"), 
						result.getDouble("balance")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bankAccounts;
	}

	public static ArrayList<UserMobileAccount> getUserMobileAccounts(String recID){
		ArrayList<UserMobileAccount> mobileAccounts = null;
		try {
			CallableStatement cbstmt = conn.prepareCall("{call getMobileAccount(?) }");
			cbstmt.setString(1, recID);
			ResultSet result = cbstmt.executeQuery();
			
			while(result.next()){
				if(mobileAccounts == null)
					mobileAccounts = new ArrayList<>();
				
				mobileAccounts.add(new UserMobileAccount(result.getString("name"), 
						result.getString("number"), 
						result.getDouble("balance"), 
						result.getInt("shortCode")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mobileAccounts;
	}
	
	public static int deleteUser(int userID){
		int outcome = 0;

		try {
			conn.setAutoCommit(false);
			CallableStatement cbstmt = conn.prepareCall("{call deleteUser(?) }");
			cbstmt.setInt(1, userID);
			
			outcome = cbstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
		
		return outcome;
	}
	
	public static int updateUser(User user){
		int outcome = 0;
		
		try {
		conn.setAutoCommit(false);
		CallableStatement cbstmt = conn.prepareCall("{call updateUser(?,?,?,?,?,?,?,?,?) }");
		
		cbstmt.setInt(1, user.getId());
		cbstmt.setString(2, user.getFirstname());
        cbstmt.setString(3, user.getLastname());
        cbstmt.setString(4, user.getEmail());
        cbstmt.setString(5, user.getPhoneNumber());
        cbstmt.setString(6, user.getGender());
        cbstmt.setString(7, user.getUsername());
        cbstmt.setString(8, user.getPassword());
        cbstmt.setString(9, user.getAddress());
        
		outcome = cbstmt.executeUpdate();
		conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
	
		return outcome;
	}
	
	public static List<User> getAllUsers(){
		List<User> users = null;
		
		try {
			CallableStatement cbstmt = conn.prepareCall("{call getAllUsers() }");
			
			ResultSet result = cbstmt.executeQuery();
			
			while(result.next()){
				if(users == null)
					users = new ArrayList<>();
				
				users.add(new User(result.getInt(1),result.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public static int activateUser(String email, String identifier, String code){
		int res = 0;
		try {
			CallableStatement cbstmt = conn.prepareCall("{call getUser_Identifier_ActivationCode(?,?) }");
			cbstmt.setString(1, email);
			cbstmt.setString(2, code);
			ResultSet result = cbstmt.executeQuery();
			
			if(result.next() && StringRefiner.verifyPassword(identifier, result.getString("active_hash"))){
				conn.setAutoCommit(false);
				cbstmt = conn.prepareCall("{call activateUser(?)}");
				cbstmt.setString(1, email);
				res = cbstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
		return res;
	}
	
	public static int resetPassword(String email, String identifier, String code, String newPassword){
		int res = 0;
		try {
			CallableStatement cbstmt = conn.prepareCall("{call getUser_Identifier_PR(?,?) }");
			cbstmt.setString(1, email);
			cbstmt.setString(2, code);
			ResultSet result = cbstmt.executeQuery();
			
			if(result.next() && StringRefiner.verifyPassword(identifier, result.getString("recover_password_hash"))){
				conn.setAutoCommit(false);
				cbstmt = conn.prepareCall("{call resetUserPassword(?,?)}");
				cbstmt.setString(1, email);
				cbstmt.setString(2, StringRefiner.refinePassword(newPassword));
				res = cbstmt.executeUpdate();
			}
		} catch (SQLException | CannotPerformOperationException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
		return res;
	}
	
	public static int processPasswordRecovery(String email){
		int status = 0;
		String[] stringAndHash = StringRefiner.getRandomStringAndHashed().split(":");
		String randomString = stringAndHash[0];
		String hash = stringAndHash[1];
		
		try {
			CallableStatement cbstmt = conn.prepareCall("{call getUser_PHONENUBMER(?) }");
			cbstmt.setString(1, email);
			ResultSet result = cbstmt.executeQuery();
			
			if(result.next()){ 
				conn.setAutoCommit(false);
				
				cbstmt = conn.prepareCall("{call updateUser_PR(?,?,?) }");
				cbstmt.setString(1, email);
				cbstmt.setString(2, hash);
				
		        String shortCode = StringRefiner.getShortCode(4);
		        cbstmt.setString(3, shortCode);
		        
				if((cbstmt.executeUpdate() == 1) && (Mailer.send(email, randomString, 2)) 
						&& (SMSSender.Send("E-TRAN password-reset request code to provide is "+shortCode, result.getString("phone")) == 1)){
					status = 1;
					conn.commit();
				} else {
					System.out.println("password reset finals failed");
					conn.rollback();
				}
			} else {
				System.out.println("nothing returned from db: PR");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
		
		return status;
	}
	
	public static int processCookie(String username, String identifier, String token){
		try {
			conn.setAutoCommit(false);
			CallableStatement cbstmt = conn.prepareCall("{call processCookie(?,?,?) }");
			cbstmt.setString(1, username);
			cbstmt.setString(2, identifier);
			cbstmt.setString(3, token);
			
			if(cbstmt.executeUpdate() == 1){
				conn.commit();
				return 1;
			} else {
				conn.rollback();
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
	}
	
	public static String verifyCookie(String identifier, String token){
		String res = null;
		try {
			CallableStatement cbstmt = conn.prepareCall("{call getString(?) }");
			cbstmt.setString(1, identifier);
			
			ResultSet result = cbstmt.executeQuery();
			
			if(result.next() && (StringRefiner.verifyPassword(token, result.getString("cookie_token")))){
				res = result.getString("firstname")+":"+result.getString("image_id");
			} else {
				processCookie(result.getString("email"), null, null);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static double internalTransfer(String bank, String fromName, String fromPhoneNumber, Long fID, 
										  String fBranch, Long tID, String tBranch, double amount){
		try {
			conn.setAutoCommit(false);
			CallableStatement cbstmt = conn.prepareCall("{call IntraBankTransfer(?,?,?,?,?,?) }");
			cbstmt.setLong(1, fID);
			cbstmt.setString(2, fBranch);
			cbstmt.setLong(3, tID);
			cbstmt.setString(4, tBranch);
			cbstmt.setDouble(5, amount);
			cbstmt.setString(6, bank);
			
			int status = cbstmt.executeUpdate();
			
			if(status > 0){			
				cbstmt = conn.prepareCall("{call getBankBalancesInternal(?,?,?,?,?,?,?,?,?) }");
				cbstmt.setString(1, bank);
				cbstmt.setLong(2, fID);
				cbstmt.setString(3, fBranch);
				cbstmt.setLong(4, tID);
				cbstmt.setString(5, tBranch);
				cbstmt.setString(6, "fromBalance");
				cbstmt.setString(7, "toName");
				cbstmt.setString(8, "toBalance");
				cbstmt.setString(9, "toPhoneNumber");
				
				cbstmt.registerOutParameter("fromBalance", java.sql.Types.DOUBLE);
				cbstmt.registerOutParameter("toName", java.sql.Types.VARCHAR);
				cbstmt.registerOutParameter("toBalance", java.sql.Types.DOUBLE);
				cbstmt.registerOutParameter("toPhoneNumber", java.sql.Types.VARCHAR);
				
				cbstmt.executeUpdate();
				
				if(cbstmt.getDouble("fromBalance") > 0){
					double fromBalance = cbstmt.getDouble("fromBalance");
					
					String toName = cbstmt.getString("toName");
					double toBalance = cbstmt.getDouble("toBalance");
					String toPhoneNumber = cbstmt.getString("toPhoneNumber");
					
					String transactionId = StringRefiner.getShortCode(10);
					String date = new Date().toString();
					
					
					if(((addTransation(new Transaction(Long.parseLong(transactionId), 
							"B2B-INTERNAL-"+bank, fromName, fID.toString(), toName, tID.toString(), date)) == 1)) 
						&& (SMSSender.toSender(fromName, bank, fID.toString(), 
									date, bank+"-INTERNAL", transactionId, 
									amount, fromBalance, fromPhoneNumber) == 1)
							
						&& (SMSSender.toReceiver(toName, bank, tID.toString(),
									date, bank+"-INTERNAL", fromName, fromPhoneNumber, transactionId, 
									amount, toBalance, toPhoneNumber) == 1)) 
					{
						conn.commit();
						return fromBalance;
					} 
					
					else
						conn.rollback();
						return 0;
				} else {
					conn.rollback();
					return 0;
				}
			} else {
				conn.rollback();
				return 0;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("main exception");
			try {
				conn.rollback();
				System.out.println("successfully rolled back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
	}
	
	public static double externalTransfer(String fromBank, String fromName, String fromPhoneNumber, Long fID, 
			String fBranch, Long tID, String tBranch, String toBank, 
			double amount){
		try {
			conn.setAutoCommit(false);
			CallableStatement cbstmt = conn.prepareCall("{call ExternalBankTransfer(?,?,?,?,?,?,?) }");
			cbstmt.setString(1, fromBank);
			cbstmt.setLong(2, fID);
			cbstmt.setString(3, fBranch);
			cbstmt.setString(4, toBank);
			cbstmt.setLong(5, tID);
			cbstmt.setString(6, tBranch);
			cbstmt.setDouble(7, amount);
			
			int status = cbstmt.executeUpdate();
		
			if(status > 0){			
				cbstmt = conn.prepareCall("{call getBankBalancesExternal(?,?,?,?,?,?,?,?,?,?) }");
				cbstmt.setString(1, fromBank);
				cbstmt.setLong(2, fID);
				cbstmt.setString(3, fBranch);
				cbstmt.setString(4, toBank);
				cbstmt.setLong(5, tID);
				cbstmt.setString(6, tBranch);
				cbstmt.setString(7, "fromBalance");
				cbstmt.setString(8, "toName");
				cbstmt.setString(9, "toBalance");
				cbstmt.setString(10, "toPhoneNumber");
				
				cbstmt.registerOutParameter("fromBalance", java.sql.Types.DOUBLE);
				cbstmt.registerOutParameter("toName", java.sql.Types.VARCHAR);
				cbstmt.registerOutParameter("toBalance", java.sql.Types.DOUBLE);
				cbstmt.registerOutParameter("toPhoneNumber", java.sql.Types.VARCHAR);
				
				cbstmt.executeUpdate();
				
				if(cbstmt.getDouble("fromBalance") > 0){
					double fromBalance = cbstmt.getDouble("fromBalance");
					
					String toName = cbstmt.getString("toName");
					double toBalance = cbstmt.getDouble("toBalance");
					String toPhoneNumber = cbstmt.getString("toPhoneNumber");
					
					String transactionId = StringRefiner.getShortCode(10);
					String date = new Date().toString();
					
					
					if(((addTransation(new Transaction(Long.parseLong(transactionId), 
								"B2B-EXTERNAL-("+fromBank+"-"+toBank+")", fromName, fID.toString(), toName, tID.toString(), date)) == 1))
						&& (SMSSender.toSender(fromName, fromBank, fID.toString(), 
									date, "EXTERNAL("+fromBank+"-"+toBank+")", transactionId, 
									amount, fromBalance, fromPhoneNumber) == 1)
							
						&& (SMSSender.toReceiver(toName, toBank, tID.toString(),
									date, "EXTERNAL("+fromBank+"-"+toBank+")", fromName, fromPhoneNumber, transactionId, 
									amount, toBalance, toPhoneNumber) == 1)) 
					{
						conn.commit();
						return fromBalance;
					} 
					
					else
						conn.rollback();
						return 0;
				} else {
					conn.rollback();
					return 0;
				}
			} else {
				conn.rollback();
				return 0;
			}		

			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("main exception");
			try {
				conn.rollback();
				System.out.println("successfully rolled back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
	}
	
	public static int addTransation(Transaction transaction){

		try {
			conn.setAutoCommit(false);
			CallableStatement cbstmt = conn.prepareCall("{call addTransaction(?,?,?,?,?,?,?) }");
			
			cbstmt.setLong(1, transaction.getId());
			cbstmt.setString(2, transaction.getName());
			cbstmt.setString(3, transaction.getFromName());
			cbstmt.setString(4, transaction.getFromAccount());
			cbstmt.setString(5, transaction.getToName());
			cbstmt.setString(6, transaction.getToAccount());
			cbstmt.setString(7, transaction.getDate());
			int status = cbstmt.executeUpdate();
			if(status > 0){
				conn.commit();
				return 1;
			} else 
				return 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
	}
	
	public static int addTransationMobile(Transaction transaction){

		try {
			conn.setAutoCommit(false);
			CallableStatement cbstmt = conn.prepareCall("{call addTransactionMobile(?,?,?,?,?,?,?) }");
			
			cbstmt.setLong(1, transaction.getId());
			cbstmt.setString(2, transaction.getName());
			cbstmt.setString(3, transaction.getFromName());
			cbstmt.setString(4, transaction.getFromAccount());
			cbstmt.setString(5, transaction.getToName());
			cbstmt.setString(6, transaction.getToAccount());
			cbstmt.setString(7, transaction.getDate());
			int status = cbstmt.executeUpdate();
			if(status > 0){
				conn.commit();
				return 1;
			} else 
				return 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
	}
	
	
	public static double bankToMobileTransfer(String fromBank, String fromName, String fromPhoneNumber, Long fID, 
			String fBranch, String toMobile, String mobileName, double amount){
		try {
			conn.setAutoCommit(false);
			CallableStatement cbstmt = conn.prepareCall("{call BankToMobileTransfer(?,?,?,?,?) }");
			cbstmt.setString(1, fromBank);
			cbstmt.setLong(2, fID);
			cbstmt.setString(3, fBranch);
			cbstmt.setString(4, toMobile);
			cbstmt.setDouble(5, amount);
			
			int status = cbstmt.executeUpdate();
		
			if(status > 0){			
				cbstmt = conn.prepareCall("{call getBankBalancesBTM(?,?,?,?,?,?,?) }");
				cbstmt.setString(1, fromBank);
				cbstmt.setLong(2, fID);
				cbstmt.setString(3, fBranch);
				cbstmt.setString(4, toMobile);
				cbstmt.setString(5, "fromBalance");
				cbstmt.setString(6, "toName");
				cbstmt.setString(7, "toBalance");
				
				cbstmt.registerOutParameter("fromBalance", java.sql.Types.DOUBLE);
				cbstmt.registerOutParameter("toName", java.sql.Types.VARCHAR);
				cbstmt.registerOutParameter("toBalance", java.sql.Types.DOUBLE);
				
				cbstmt.executeUpdate();
				
				if(cbstmt.getDouble("fromBalance") > 0){
					double fromBalance = cbstmt.getDouble("fromBalance");
					
					String toName = cbstmt.getString("toName");
					double toBalance = cbstmt.getDouble("toBalance");
					String toPhoneNumber = toMobile;
					
					String transactionId = StringRefiner.getShortCode(10);
					String date = new Date().toString();
					
					
					if(((addTransation(new Transaction(Long.parseLong(transactionId), 
								"B2M-("+fromBank+"-"+mobileName+")", fromName, fID.toString(), toName, toMobile, date)) == 1))
						&& (SMSSender.toSender(fromName, fromBank, fID.toString(), 
									date, "BankToMobile("+fromBank+"-"+mobileName+")", transactionId, 
									amount, fromBalance, fromPhoneNumber) == 1)
							
						&& (SMSSender.toReceiver(toName, mobileName, toMobile,
									date, "BankToMobile("+fromBank+"-"+mobileName+")", fromName, fromPhoneNumber, transactionId, 
									amount, toBalance, toPhoneNumber) == 1)) 
					{
						conn.commit();
						return fromBalance;
					} 
					
					else
						conn.rollback();
						return 0;
				} else {
					conn.rollback();
					return 0;
				}
			} else {
				conn.rollback();
				return 0;
			}		

			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("main exception");
			try {
				conn.rollback();
				System.out.println("successfully rolled back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
	}
	
	public static double mobileToBankTransfer(String fromName, String fromPhoneNumber, String toBank, 
			String toNumber, String toBranch, String mobileName, double amount){
		try {
			conn.setAutoCommit(false);
			CallableStatement cbstmt = conn.prepareCall("{call MobileToBankTransfer(?,?,?,?,?) }");
			cbstmt.setString(1, fromPhoneNumber);
			cbstmt.setString(2, toBank);
			cbstmt.setString(3, toNumber);
			cbstmt.setString(4, toBranch);
			cbstmt.setDouble(5, amount);
			
			int status = cbstmt.executeUpdate();
		
			if(status > 0){			
				cbstmt = conn.prepareCall("{call getBankBalancesMTB(?,?,?,?,?,?,?,?) }");
				cbstmt.setString(1, fromPhoneNumber);
				cbstmt.setString(2, toBank);
				cbstmt.setString(3, toNumber);
				cbstmt.setString(4, toBranch);
				cbstmt.setString(5, "fromBalance");
				cbstmt.setString(6, "toName");
				cbstmt.setString(7, "toBalance");
				cbstmt.setString(8, "toNumber");
				
				cbstmt.registerOutParameter("fromBalance", java.sql.Types.DOUBLE);
				cbstmt.registerOutParameter("toName", java.sql.Types.VARCHAR);
				cbstmt.registerOutParameter("toBalance", java.sql.Types.DOUBLE);
				cbstmt.registerOutParameter("toNumber", java.sql.Types.VARCHAR);
				
				cbstmt.executeUpdate();
				
				if(cbstmt.getDouble("fromBalance") > 0){
					double fromBalance = cbstmt.getDouble("fromBalance");
					
					String toName = cbstmt.getString("toName");
					double toBalance = cbstmt.getDouble("toBalance");
					String toPhoneNumber = cbstmt.getString("toNumber");
					
					String transactionId = StringRefiner.getShortCode(10);
					String date = new Date().toString();
					
					
					if(((addTransationMobile(new Transaction(Long.parseLong(transactionId), 
								"M2B-("+mobileName+"-"+toBank+")", fromName, fromPhoneNumber, toName, toBank, date)) == 1))
						&& (SMSSender.toSender(fromName, mobileName, fromPhoneNumber, 
									date, "MobileToBank("+mobileName+"-"+toBank+")", transactionId, 
									amount, fromBalance, fromPhoneNumber) == 1)
							
						&& (SMSSender.toReceiver(toName, toBank, toNumber,
									date, "MobileToBank("+mobileName+"-"+toBank+")", fromName, fromPhoneNumber, transactionId, 
									amount, toBalance, toPhoneNumber) == 1)) 
					{
						conn.commit();
						return fromBalance;
					} 
					
					else
						conn.rollback();
						return 0;
				} else {
					conn.rollback();
					return 0;
				}
			} else {
				conn.rollback();
				return 0;
			}		

			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("main exception");
			try {
				conn.rollback();
				System.out.println("successfully rolled back");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}
	}
}
