package com.asimadu.processing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asimadu.data.DataAccess;
import com.asimadu.util.ValidateStrings;

@WebServlet("/GetImage")
public class GetImage extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imageId = request.getParameter("imageId");
		
		if(ValidateStrings.validateString(imageId)){
			
			String fileLocation = DataAccess.profilePicturesFolder+imageId;
			
			 try {
		          FileInputStream in = new FileInputStream(new File(fileLocation));  
		          int read = 0;  
		          byte[] bytes = new byte[1024];    
		          
		          while ((read = in.read(bytes)) != -1) {  
		              response.getOutputStream().write(bytes, 0, read);
		          }    
		          
		          in.close();
		       
		          response.setContentType("image/jpg");
                  response.setContentLength(bytes.length);
		     } catch (IOException e) {
		    	 e.printStackTrace();
		     }
			
		}
	}

}
