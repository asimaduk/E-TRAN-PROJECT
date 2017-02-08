package com.asimadu.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asimadu.data.DataAccess;

@Path("/externalBankTransfer") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BankToBankExternalAPI {
	@GET  
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{fromBank}/{fromName}/{fromPhoneNumber}/{fromID}/{fromBranch}/{toID}/{toBranch}/{amount}/{toBank}")
	public double internalTransfer(
			@PathParam("fromBank") String fromBank,
			@PathParam("fromName") String fromName, 
			@PathParam("fromPhoneNumber") String fromPhoneNumber,
			@PathParam("fromID") Long fromID, 
			@PathParam("fromBranch") String fromBranch, 
			@PathParam("toID") Long toID,
			@PathParam("toBranch") String toBranch,
			@PathParam("amount") double amount,
			@PathParam("toBank") String toBank) {  
	
		return DataAccess.externalTransfer(fromBank, fromName, fromPhoneNumber, fromID, fromBranch, toID, toBranch, toBank, amount);
	}
}
