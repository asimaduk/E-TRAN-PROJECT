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

@WebServlet("/Activate")
public class Activate extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String identifier = request.getParameter("identifier");
		
		if(ValidateStrings.validateString(email) && ValidateStrings.validateString(identifier)){
			request.setAttribute("show", true);
			request.getRequestDispatcher("/Pages/accountactivation.jsp").forward(request, response);
			
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("identifier", identifier);
		} else {
			request.setAttribute("show", false);
			request.setAttribute("message", "Activation failed! Kindly try again or if it persist, "
					+ "try registering again. Possibly the duration for the validity of the link to activate your "
					+ "account has expired!");
			request.getRequestDispatcher("/Pages/accountactivation.jsp").forward(request, response);				
		}	
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		String code = request.getParameter("code");
		String email = (String) request.getSession().getAttribute("email");
		String identifier = (String) request.getSession().getAttribute("identifier");
		
		System.out.println(code+email+identifier);
		
		Client client = ClientBuilder.newClient();
		WebTarget baseTarget = client.target(DataAccess.baseUrl+"custom-rest/activateAccount/"+email+"/"+identifier+"/"+code);
		Response res = baseTarget.request(MediaType.APPLICATION_JSON).get();
		int status = res.readEntity(Integer.class);
		System.out.println("activation status is: "+status);
		
		
		if(status > 0){
			resp.setStatus(200);
			resp.getWriter().println("Activation successful. You can now visit the homepage to login, welcome.");
			
		} else {
			resp.setStatus(201);
			resp.getWriter().println("Activation failed! Kindly try again or if it persist, "
					+ "try registering again. Possibly the duration for the validity of the link to activate your "
					+ "account has expired!");				
		}
	}

}
