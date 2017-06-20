package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twat.dao.CalgatherDAO;


@WebServlet("/StartGetGourpId")
public class StartGetGourpId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       



	protected void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = request.getServletContext();
		CalgatherDAO calDao = CalgatherDAO.getInstance();
		PrintWriter out = response.getWriter();
		int count = calDao.getLastGroupId();
		
		application.setAttribute("groupCount", count);
		out.println(application.getAttribute("groupCount"));
		
	}


}
