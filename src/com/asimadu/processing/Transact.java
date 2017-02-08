package com.asimadu.processing;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Transacting")
public class Transact extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("status").equals("true")){
			request.getSession().setAttribute("transacting", request.getParameter("status"));
			System.out.println("now transacting");
		} else {
			request.getSession().setAttribute("transacting", null);
			System.out.println("stopped transacting");
		}	
	}
}
