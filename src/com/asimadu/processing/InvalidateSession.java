package com.asimadu.processing;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asimadu.data.DataAccess;

@WebServlet("/Logout")
public class InvalidateSession extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		DataAccess.processCookie("", null, null);
		response.setStatus(200);
		System.out.println("you are logged out");
	}
}
