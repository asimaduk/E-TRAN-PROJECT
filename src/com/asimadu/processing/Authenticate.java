package com.asimadu.processing;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asimadu.data.DataAccess;
import com.asimadu.data.UserDetails;
import com.asimadu.util.StringRefiner;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String targetString = String.format(DataAccess.baseUrl+"custom-rest/authenticate?"
	      		+ "username=%1$s&password=%2$s", 
	      		request.getParameter("username"), 
	      		request.getParameter("password"));
	      
		  System.out.println("try logging in... username="+request.getParameter("username")+" and pass="+request.getParameter("password"));
	      
	      Client client = ClientBuilder.newClient();
	      Response res = client.target(targetString).request(MediaType.APPLICATION_JSON).get();
	      String result = res.readEntity(String.class);
	         
          if(!result.equals("") && !result.equals(null)){ 
        	  System.out.println(result);
        	  
	    	  ObjectMapper mapper = new ObjectMapper();
	    	  UserDetails userDetails = mapper.readValue(result, UserDetails.class);
	    	  
	    	  HttpSession session = request.getSession(true);
	          session.setAttribute("auth", "true");
	          session.setAttribute("firstname", userDetails.getFirstname());
	          session.setAttribute("isAdmin", userDetails.getIsAdmin());
	          session.setAttribute("imageId", userDetails.getImageId());
	          session.setAttribute("userDetails", userDetails);
	          session.setAttribute("userDetailsJSON", result);
	         
	          String rememberMe = request.getParameter("remember-me");
	          if(rememberMe.equals("true")){
	        	  String string = StringRefiner.getRandomStringAndHashed();
	        	  String identifier = string.split(":")[0];
	        	  
	        	  String string2 = StringRefiner.getRandomStringAndHashed();
	        	  String token = string2.split(":")[0];
	        	  String token2 = string2.split(":")[1];
	        	  
	        	  String cookieValue = identifier+"___"+token;
	        	  
	        	  int status = DataAccess.processCookie(request.getParameter("username"), identifier, token2);
	        	  
	        	  if(status == 1)
	        		  System.out.println("cookie inserted");
	        	  else 
	        		  System.out.println("failed to insert");
	        	  
	        	  Cookie cookie = new Cookie("user_r", cookieValue);
	        	  cookie.setMaxAge(60*60*24);
	        	  response.addCookie(cookie);
	          } 
	      
        	  response.getWriter().println(result);
        	  response.setStatus(200);
          }
          else {
        	  response.setStatus(203);
        	  response.getWriter().println("Login failed! username/password mismatch"); 
          }
	}
}
