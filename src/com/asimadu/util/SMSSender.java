package com.asimadu.util;

import com.asimadu.data.DataAccess;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSSender {
	public static final String ACCOUNT_SID = DataAccess.twilioAccountSID;
	public static final String AUTH_TOKEN = DataAccess.twilioAuthToken;

	public static int Send(String msg, String phoneNumber){
		 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		    Message message = Message.creator(new PhoneNumber(phoneNumber),
		        new PhoneNumber("+1234567890"), 
		        msg).create();
		    
		    String id = "";
		    id = message.getSid();
		    if(!id.equals("")){
		    	return 1;
		    }
		 return 0;
	}
	
	public static int toSender(String name, String bank,String account, String date, String type, String transactionId, 
						double amount, double balance, String phoneNumber){
		String message = String.format("Hello %1$s, your %2$s account: %3$s has been debited with"
				+ " GHC %4$s. DESC: E-TRAN %5$s transfer. Transaction ID: %6$s. Date: %7$s. Curr. avail. bal: %8$s", 
				name,bank,account,amount,type,transactionId,date,balance);
		
		return Send(message, phoneNumber);
	}
	
	public static int toReceiver(String name, String bank,String account, String date, 
								 String type, String fromName, String fromPhoneNumber, String transactionId, 
								 double amount, double balance, String phoneNumber)
	{
		String message = String.format("Hi %1$s, your %2$s account: %3$s has been credited with"
			+ " GHC %4$s. DESC: E-TRAN %5$s transfer, from %6$s(%7$s). Transaction ID: %8$s. Date: %9$s. Curr. avail. bal: %10$s", 
			name,bank,account,amount,type,fromName,fromPhoneNumber,transactionId,date,balance);
		
		return Send(message, phoneNumber);
	}
}
