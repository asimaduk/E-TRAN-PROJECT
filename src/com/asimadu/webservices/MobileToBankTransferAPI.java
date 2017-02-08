package com.asimadu.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asimadu.data.DataAccess;

@Path("/mobileToBankTransfer") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MobileToBankTransferAPI {
	@GET  
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{fromName}/{fromPhoneNumber}/{toBank}/{toNumber}/{toBranch}/{amount}")
	public double internalTransfer(
			@PathParam("fromName") String fromName, 
			@PathParam("fromPhoneNumber") String fromPhoneNumber,
			@PathParam("toBank") String toBank, 
			@PathParam("toNumber") String toNumber, 
			@PathParam("toBranch") String toBranch,
			@PathParam("amount") double amount) 
	{  
		String mobileName = "";
	
		if((fromPhoneNumber.substring(4, 6).equals("24")) || (fromPhoneNumber.substring(4, 6).equals("54") || (fromPhoneNumber.substring(4, 6).equals("55")))){
			mobileName = "MTN";
			System.out.println(mobileName);
		}
		
		else if((fromPhoneNumber.substring(4, 6).equals("27")) || (fromPhoneNumber.substring(4, 6).equals("57"))){
			mobileName = "TIGO";
			System.out.println(mobileName);
		}
		
		else if((fromPhoneNumber.substring(4, 6).equals("20")) || (fromPhoneNumber.substring(4, 6).equals("50"))){
			mobileName = "VODAFONE";
			System.out.println(mobileName);
		}
		
		else if(fromPhoneNumber.substring(4, 6).equals("26")){
			mobileName = "AIRTEL";
			System.out.println(mobileName);
		}
		
		else {
			System.out.println(fromPhoneNumber.substring(4, 6)+" and if failed");
		}
		
		if("ECOBANK".equalsIgnoreCase(toBank)){
			return DataAccess.mobileToBankTransfer(fromName, fromPhoneNumber, toBank, toNumber, toBranch, mobileName, amount);
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
