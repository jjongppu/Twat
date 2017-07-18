package com.mtwat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.MemberDAO;

@WebServlet("/MakeSession.do")
public class MakeSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MakeSession() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession();
		
		String loginCookie = request.getParameter("LoginCookie");
	
		
		
		PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
		
		session.setAttribute("loginUserId", loginCookie);
		
		
		jsonOb.put("result", "success");
		jsonList.add(jsonOb);
		
		writer.println(jsonList);
		writer.close();
	}

}
