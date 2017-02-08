package com.asimadu.util;

import java.security.SecureRandom;
import java.util.ArrayList;

import com.asimadu.util.StringRefiner.CannotPerformOperationException;

public class Test {
	static String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public static void main(String[] args) throws CannotPerformOperationException {
		ArrayList<String> list = new ArrayList<>();
		
		
		list.add("anothe one");
		list.add("");
		boolean stat = ValidateStrings.validateArrayOfStrings(list);
		
		System.out.println("status in main is "+stat);
		String xx ;
		try {
			xx = StringRefiner.refinePassword("i love yo");
			
			System.out.println("\nto data base: "+xx);
			
			System.out.println(StringRefiner.verifyPassword("i love yo", xx));
		} catch (CannotPerformOperationException e) {
			e.printStackTrace();
		}
		
//		StringBuilder sb = new StringBuilder();
//		for(int i=0;i<128;i++){
//			sb.append(AB.charAt(rnd.nextInt(AB.length())));
//		}
//
//		System.out.println(sb.toString()+"\n");
//		
//		System.out.println(StringRefiner.refinePassword(sb.toString()));
//		
//		System.out.println(StringRefiner.verifyPassword(sb.toString(), StringRefiner.refinePassword(sb.toString())));
		
		System.out.println(StringRefiner.getRandomStringAndHashed());

	}

}
