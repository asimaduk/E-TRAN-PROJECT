package com.asimadu.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.asimadu.data.UserDetails;
import com.asimadu.data.DataAccess;

@Path("/authenticate") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationAPI {
	@GET  
	@Produces(MediaType.APPLICATION_XML)  
	public UserDetails authenticateXML(@QueryParam("username") String username, 
								 @QueryParam("password") String password) {  
		return DataAccess.getUser(username, password);
	}
	
	@GET  
	@Produces(MediaType.APPLICATION_JSON)  
	public UserDetails authenticateJSON(@QueryParam("username") String username, 
								 @QueryParam("password") String password) {  
		return DataAccess.getUser(username, password);
	}
}
