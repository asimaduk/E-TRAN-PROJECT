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
import com.asimadu.data.UserDetails;

@WebServlet("/MobileToBankTransfer")
public class MobileToBankTransfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mobileNumber = "+"+req.getParameter("mobile").substring(1,13);
		double amount = Double.parseDouble(req.getParameter("amount"));
		
		String toBank = req.getParameter("bank");
		String toNumber = req.getParameter("number");
		String toBranch = req.getParameter("branch");
		
		UserDetails user = (UserDetails) req.getSession().getAttribute("userDetails");
		String fromName = user.getFirstname();
		
		double x = -1;
		int index = 0; 
		
		for(int i=0; i<user.getUserMobileAccounts().size(); i++){
			if(user.getUserMobileAccounts().get(i).getNumber().equals(mobileNumber)){
				x = user.getUserMobileAccounts().get(i).getBalance();
				index = i;
			}
		}
		
		if(x > 0){
			Client client = ClientBuilder.newClient();
			WebTarget baseTarget = client.target(DataAccess.baseUrl+"custom-rest/mobileToBankTransfer/"
				+fromName
				+"/"+mobileNumber
				+"/"+toBank
				+"/"+toNumber
				+"/"+toBranch
				+"/"+amount);
			
			Response res = baseTarget.request(MediaType.APPLICATION_JSON).get();
			double balance = res.readEntity(Double.class);
			
			if(balance > 0){
				user.getUserBankAccounts().get(index).setBalance(balance);
				
				req.getSession().setAttribute("userDetails", user);
				
				System.out.println("MTB: balance is "+balance);
				
				resp.setStatus(200);
				resp.getWriter().println(balance);
				
			} else {
				resp.setStatus(201);
				System.out.println("external: got some other balance: MTB "+balance);
			}
		} else {
			System.out.println("x failed...");
		}
	}
}
