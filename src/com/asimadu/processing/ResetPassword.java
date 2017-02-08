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

@WebServlet("/ResetPassword")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Pages/recoverPassword.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email =  request.getParameter("email");
		if(ValidateStrings.validateEmail(email)){
			System.out.println(email);
			
			Client client = ClientBuilder.newClient();
			WebTarget baseTarget = client.target(DataAccess.baseUrl+"custom-rest/recoverPassword/"+email);
			Response resp = baseTarget.request(MediaType.APPLICATION_JSON).get();
			int status = resp.readEntity(Integer.class);
			
			if(status == 1){
				response.setStatus(200);
			} else {
				response.setStatus(201);
			}
		}
	}
}
