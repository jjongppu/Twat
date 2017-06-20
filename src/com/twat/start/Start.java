package com.twat.start;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.twat.dao.CalgatherDAO;

public class Start implements ServletContextListener {
	
	public Start() throws Exception { }
	
	
	public void contextInitialized(ServletContextEvent event){
		CalgatherDAO calDao = CalgatherDAO.getInstance();
		int count = calDao.getLastGroupId();
		
		 ServletContext application = event.getServletContext();

		application.setAttribute("groupCount", count);
		
	} 
	
	public void contextDestroyed(ServletContextEvent event) { 
		
		
	}
	
}

