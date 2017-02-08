package com.asimadu.processing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asimadu.data.DataAccess;
import com.asimadu.data.User;
import com.asimadu.util.StringRefiner;
import com.asimadu.util.StringRefiner.CannotPerformOperationException;
import com.asimadu.util.ValidateStrings;

@MultipartConfig(maxFileSize = 16177215)

@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phone-number");
		String gender = request.getParameter("gender");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm-password");
		String address = request.getParameter("address");
		
		int noOfBankAccts = Integer.parseInt(request.getParameter("number-of-bank-accounts"));
		int noOfMobileAccts = Integer.parseInt(request.getParameter("number-of-mobile-accounts"));
		
		String banks = "";
		for(int i=1; i<=noOfBankAccts; i++){
			if(i == noOfBankAccts)
				banks += request.getParameter("bank-account"+i);
			else
				banks += request.getParameter("bank-account"+i)+"/";
		}
		
		String mobiles = "";
		for(int i=1; i<=noOfMobileAccts; i++){
			if(i == noOfMobileAccts)
				mobiles += request.getParameter("mobile-account"+i);
			else
				mobiles += request.getParameter("mobile-account"+i)+"/";
		}
		
		System.out.println("banks: "+banks);
		System.out.println("mobiles: "+mobiles);
		
		ArrayList<String> requestParametersList = new ArrayList<>();
		requestParametersList.add(firstname);
		requestParametersList.add(lastname);
		requestParametersList.add(email);
		requestParametersList.add(phoneNumber);
		requestParametersList.add(gender);
		requestParametersList.add(username);
		requestParametersList.add(password);
		requestParametersList.add(confirmPassword);
		requestParametersList.add(address);
			
		String imageID = "";
		Random rn = new Random();
		for(int i=1; i<21; i++){
			imageID += rn.nextInt(10);
		}
		
		requestParametersList.add(imageID);
		
		if(ValidateStrings.validateArrayOfStrings(requestParametersList) && password.equals(confirmPassword) && (password.length() >= 8))
		{
			User user = null;
			try {
				user = new User(firstname,lastname,email,
							phoneNumber,gender,username,
							StringRefiner.refinePassword(password),
							address,banks,mobiles,imageID);
			} catch (CannotPerformOperationException e1) {
				e1.printStackTrace();
			}
			
			if(user != null){
				Client client = ClientBuilder.newClient();
				WebTarget baseTarget = client.target(DataAccess.baseUrl+"custom-rest/register");
				Response resp = baseTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(user));
				int status = resp.readEntity(Integer.class);
				
				if(status == 1){
					InputStream inputStream = null; 
					
			        Part filePart = request.getPart("photo");
			        if ((filePart != null) && (filePart.getName().equals("photo")) 
			        					   && (filePart.getSize() <= 2000000) 
			        					   && (filePart.getContentType().equals("image/jpeg") || filePart.getContentType().equals("image/jpg") || filePart.getContentType().equals("image/JPG"))) {
			
			            inputStream = filePart.getInputStream();
			            
			            String fileLocation = DataAccess.profilePicturesFolder+imageID;

					    try {  
					          FileOutputStream out = new FileOutputStream(new File(fileLocation));  
					          int read = 0;  
					          byte[] bytes = new byte[1024];    
					          while ((read = inputStream.read(bytes)) != -1) {  
					              out.write(bytes, 0, read);  
					          }  
					          out.flush();  
					          out.close();
					          
					          System.out.println("done writing file contents");
					          response.setStatus(200);
					          response.getWriter().println("Registration successful. Please check your registered "
					          		+ "email and mobile sms to activate your account. Thank you.");
					     } catch (IOException e) {
					    	 e.printStackTrace();
					    	 response.setStatus(500);
					     }  
			        
					} else {
						System.out.println("error: filepart failed");
						response.setStatus(500);
					}
				
				} else {
					System.out.println("error: happened from database");
					response.setStatus(500);
				}
			}
			
		
		} else {
			response.setStatus(500);
		}
	
	}

}
