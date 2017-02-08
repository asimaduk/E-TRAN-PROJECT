package com.asimadu.processing;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@WebServlet("/GetUser")
public class GetUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String userDetailsJSON = ow.writeValueAsString(req.getSession().getAttribute("userDetails"));
		
		resp.getWriter().println(userDetailsJSON);
	}

}
