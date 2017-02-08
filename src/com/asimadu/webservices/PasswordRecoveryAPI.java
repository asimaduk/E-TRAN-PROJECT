package com.asimadu.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asimadu.data.DataAccess;

@Path("/recoverPassword") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PasswordRecoveryAPI {
	@GET  
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{email}/{identifier}/{code}/{password}")
	public int recoverPassword(@PathParam("email") String email, @PathParam("identifier") String identifier, @PathParam("code") String code, @PathParam("password") String password) {  
		return DataAccess.resetPassword(email, identifier, code, password);
	}
	
	@GET  
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{email}")
	public int processPasswordRecovery(@PathParam("email") String email) { 
		return DataAccess.processPasswordRecovery(email);
	}
}
