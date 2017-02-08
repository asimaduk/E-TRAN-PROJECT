package com.asimadu.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.asimadu.data.DataAccess;

@WebListener
public class AppListener implements ServletContextListener {

    public AppListener() {
        
    }

    public final void contextDestroyed(ServletContextEvent sce)  { 
         
    }

    public final void contextInitialized(ServletContextEvent sce)  { 
    	//Initializing data access
    	new DataAccess();
    }
	
}
