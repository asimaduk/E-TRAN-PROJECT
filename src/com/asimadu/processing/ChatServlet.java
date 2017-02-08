package com.asimadu.processing;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.concurrent.ConcurrentLinkedQueue;

@WebServlet(name = "ChatServlet", urlPatterns = {"/ChatServlet"}, asyncSupported = true)

//@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private List<AsyncContext> contexts = new LinkedList<AsyncContext>();
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	List<AsyncContext> aContexts = new ArrayList<AsyncContext>(this.contexts);
        this.contexts.clear();
        
        String name = request.getParameter("name");
        String msg = request.getParameter("msg");
        
        String date = request.getParameter("t");
        System.out.println(date);
        System.out.println("user is: "+name);
        
        String currentMessages = (String) request.getServletContext().getAttribute("message");
        
        String htmlMsg = "<div id='s-div' class='"+name+"'><p>"+msg+"<br></p><span id='chat-date'>"+date+"</span></div>";

        if(currentMessages != null) {
            currentMessages = htmlMsg + currentMessages;
        } else{
            currentMessages = htmlMsg;
        }

        request.getServletContext().setAttribute("message", currentMessages);
//      request.getRequestDispatcher("/index.jsp").forward(request,response);
        for(AsyncContext context : aContexts){
            try(PrintWriter writer = context.getResponse().getWriter()){
                writer.println(htmlMsg);
                writer.flush();
                context.complete();
            }catch (Exception ex){

            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(10*60*1000);
        contexts.add(asyncContext);
    }

}
