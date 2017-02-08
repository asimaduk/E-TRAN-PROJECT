package com.asimadu.processing;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asimadu.data.DataAccess;
import com.asimadu.util.ValidateStrings;

@WebServlet("/RecoverPassword")
public class PasswordRecovery extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private String email = "";
	private String identifier = "";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		email = request.getParameter("email");
		identifier = request.getParameter("identifier");
		
		if(ValidateStrings.validateString(email) && ValidateStrings.validateString(identifier)){
			request.getRequestDispatcher("/ResetPassword").forward(request, response);
		} else {
			
		}		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String password = request.getParameter("password");
		String c_password = request.getParameter("c_password");
		System.out.println(code+password+c_password);
		
		if((code.length() == 4) && (ValidateStrings.validateString(code)) && (ValidateStrings.validateString(password)) && (ValidateStrings.validateString(c_password)) && (password.equals(c_password))){
			Client client = ClientBuilder.newClient();
			WebTarget baseTarget = client.target(DataAccess.baseUrl+"custom-rest/recoverPassword/"+email+"/"+identifier+"/"+code+"/"+password);
			Response resp = baseTarget.request(MediaType.APPLICATION_JSON).get();
			int status = resp.readEntity(Integer.class);
			
			if(status == 1){
				response.setStatus(200);
				response.getWriter().println("New password has been set successfully");
			} else {
				response.setStatus(201);
				response.getWriter().println("Password reset failed!");
			}	
		} else {
			response.setStatus(201);
			response.getWriter().println("Reset failed!");
		}
	}

}
