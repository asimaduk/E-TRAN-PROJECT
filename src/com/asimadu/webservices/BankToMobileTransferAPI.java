package com.asimadu.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asimadu.data.DataAccess;

@Path("/bankToMobileTransfer") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BankToMobileTransferAPI {	
	@GET  
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{fromBank}/{fromName}/{fromPhoneNumber}/{fromID}/{fromBranch}/{toMobile}/{amount}")
	public double internalTransfer(
			@PathParam("fromBank") String fromBank,
			@PathParam("fromName") String fromName, 
			@PathParam("fromPhoneNumber") String fromPhoneNumber,
			@PathParam("fromID") Long fromID, 
			@PathParam("fromBranch") String fromBranch, 
			@PathParam("toMobile") String toMobile,
			@PathParam("amount") double amount) 
	{  
		String mobileName = "";
	
		if((toMobile.substring(4, 6).equals("24")) || (toMobile.substring(4, 6).equals("54") || (toMobile.substring(4, 6).equals("55")))){
			mobileName = "MTN";
			System.out.println(mobileName);
		}
		
		else if((toMobile.substring(4, 6).equals("27")) || (toMobile.substring(4, 6).equals("57"))){
			mobileName = "TIGO";
			System.out.println(mobileName);
		}
		
		else if((toMobile.substring(4, 6).equals("20")) || (toMobile.substring(4, 6).equals("50"))){
			mobileName = "VODAFONE";
			System.out.println(mobileName);
		}
		
		else if(toMobile.substring(4, 6).equals("26")){
			mobileName = "AIRTEL";
			System.out.println(mobileName);
		}
		
		else {
			System.out.println(toMobile.substring(4, 6)+" and if failed");
		}
		
		if("ECOBANK".equalsIgnoreCase(fromBank)){
			return DataAccess.bankToMobileTransfer(fromBank, fromName, fromPhoneNumber, fromID, fromBranch, toMobile, mobileName, amount);
		}
		
//		else if("GCB".equalsIgnoreCase(bank)){
//			//return DataAccess.internalTransfer(bank, fromName, fromPhoneNumber, fromID, fromBranch, toID, toBranch, amount);
//		}
//		
//		else if("BARCLAYS".equalsIgnoreCase(bank)){
//			//return DataAccess.internalTransfer(bank, fromName, fromPhoneNumber, fromID, fromBranch, toID, toBranch, amount);
//		}
		else
			return 0;
	}
}
