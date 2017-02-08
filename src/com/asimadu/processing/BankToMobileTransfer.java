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

@WebServlet("/BankToMobileTransfer")
public class BankToMobileTransfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mobileNumber = req.getParameter("mobile").substring(5,14);
		int accountIndex = Integer.parseInt(req.getParameter("index"));
		double amount = Double.parseDouble(req.getParameter("amount"));
		
		UserDetails user = (UserDetails) req.getSession().getAttribute("userDetails");
		String fromBank = user.getUserBankAccounts().get(accountIndex).getBank();
		String fromName = user.getUserBankAccounts().get(accountIndex).getName();
		String fromBranch = user.getUserBankAccounts().get(accountIndex).getBranch();
		Long fromID =  user.getUserBankAccounts().get(accountIndex).getId();
		String fromPhoneNumber = user.getMobileNumber();
		
		if(amount < user.getUserBankAccounts().get(accountIndex).getBalance()){
			Client client = ClientBuilder.newClient();
			WebTarget baseTarget = client.target(DataAccess.baseUrl+"custom-rest/bankToMobileTransfer/"
				+fromBank
				+"/"+fromName
				+"/"+fromPhoneNumber
				+"/"+fromID
				+"/"+fromBranch
				+"/+233"+mobileNumber
				+"/"+amount);
			
			Response res = baseTarget.request(MediaType.APPLICATION_JSON).get();
			double balance = res.readEntity(Double.class);
			
			if(balance > 0){
				user.getUserBankAccounts().get(accountIndex).setBalance(balance);
				
				req.getSession().setAttribute("userDetails", user);
				
				System.out.println("BTM: balance is "+balance);
				
				resp.setStatus(200);
				resp.getWriter().println(balance);
				
			} else {
				resp.setStatus(201);
				System.out.println("external: got some other balance: BTM "+balance);
			}
		}
	}

}
