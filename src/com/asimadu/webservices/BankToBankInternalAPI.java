package com.asimadu.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asimadu.data.DataAccess;

@Path("/internalBankTransfer") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BankToBankInternalAPI {
	@GET  
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{bank}/{fromName}/{fromPhoneNumber}/{fromID}/{fromBranch}/{toID}/{toBranch}/{amount}")
	public double internalTransfer(
			@PathParam("bank") String bank,
			@PathParam("fromName") String fromName, 
			@PathParam("fromPhoneNumber") String fromPhoneNumber,
			@PathParam("fromID") Long fromID, 
			@PathParam("fromBranch") String fromBranch, 
			@PathParam("toID") Long toID,
			@PathParam("toBranch") String toBranch,
			@PathParam("amount") double amount) {  
	
		if("ECOBANK".equalsIgnoreCase(bank)){
			return DataAccess.internalTransfer(bank, fromName, fromPhoneNumber, fromID, fromBranch, toID, toBranch, amount);
		}
		
		else if("GCB".equalsIgnoreCase(bank)){
			return DataAccess.internalTransfer(bank, fromName, fromPhoneNumber, fromID, fromBranch, toID, toBranch, amount);
		}
		
		else if("BARCLAYS".equalsIgnoreCase(bank)){
			return DataAccess.internalTransfer(bank, fromName, fromPhoneNumber, fromID, fromBranch, toID, toBranch, amount);
		} else
			return 0;
	}
}
