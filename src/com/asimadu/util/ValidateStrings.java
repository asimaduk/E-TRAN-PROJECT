package com.asimadu.util;

import java.util.ArrayList;

public class ValidateStrings {
	public static boolean validateArrayOfStrings(ArrayList<String> arrayList){
		boolean status = false;
		for(int i=0; i<arrayList.size(); i++){
				status = validateString(arrayList.get(i));
				if(!status)
					break;
		}

		return status;
	}
	
	public static boolean validateString(String string){
		int check = 0;
		for(int i=0; i<string.length(); i++){;
			if(string.charAt(i) == ' '){
				check++;
			}
		}
		
		if(check == string.length()){
			return false;
		}

		return true;
	}
	
	public static boolean validateEmail(String email){
    	String symbolsDisallowed = "±!#$%^&*()_+|';\\/,~";
    	boolean status = false;
    	for(int i=0; i<email.length(); i++){
    		//check for any symbol apart from '@' and '.'
    		for(int j=0; j<symbolsDisallowed.length(); j++){
				if(email.charAt(i) == symbolsDisallowed.charAt(j)){
					return status;
				}
			}
    		
    		//check for '@.' existing and '@' appearing before '.'
    		if((email.indexOf('@') > 2) && (email.indexOf('.') > (email.indexOf('@')+2)) && (email.charAt(email.indexOf('.')+1) > 0) && (email.charAt(email.indexOf('.')+2) > 0)){
    			status = true;
    			return status;
    		}    		
    	}	
    	return status;
	}
}
