package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.MemberDAO;

/**
 * Servlet implementation class ChangePwd
 */
//@WebServlet("/ChangePwd.do")
public class ChangePwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePwd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    
	    JSONArray jsonArr = new JSONArray();
	    JSONObject jsonObj = new JSONObject();
	    PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
			
		
	    String userId = (String)session.getAttribute("loginUserId");
	    
	    String mobile = request.getParameter("mobile");
	       if(mobile != null){
	    	   userId=mobile;
	       }
	    
	    String beforePw = request.getParameter("beforePw");
		String afterPw = request.getParameter("afterPw");

		
		
	    MemberDAO mdo = MemberDAO.getInstance();
	    
	    int result = mdo.changePw(userId, beforePw, afterPw);
	    
	    
	    System.out.println(beforePw);
	    System.out.println(afterPw);
	    System.out.println("321321");
	    
	    jsonObj.put("result", result); 
	    jsonArr.add(jsonObj);
	    
	    out.println(jsonArr);
	    out.close();
		
		
		
	}

}
