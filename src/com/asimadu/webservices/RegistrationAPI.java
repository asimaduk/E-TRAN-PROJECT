package com.asimadu.webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asimadu.data.User;
import com.asimadu.data.DataAccess;

@Path("/register") 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationAPI {
	@POST  
	@Consumes(MediaType.APPLICATION_JSON)
	public int register(User user) { 
		int result = 0;
		result = DataAccess.addUser(user, user.getEmail());
		return result;
	}
}
