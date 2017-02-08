package com.asimadu.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asimadu.data.DataAccess;

@Path("/activateAccount") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivationAPI {
	@GET  
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{email}/{identifier}/{code}")
	public int register(@PathParam("email") String email, @PathParam("identifier") String identifier, @PathParam("code") String code) {  
		return DataAccess.activateUser(email, identifier, code);
	}
}
