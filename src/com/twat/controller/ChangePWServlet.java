package com.twat.controller;

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

/**
 * Servlet implementation class ChangePWServlet
 */
//@WebServlet("/ChangePW.do")
public class ChangePWServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePWServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
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
	    String beforePw = request.getParameter("beforePw");
		String afterPw = request.getParameter("afterPw");
		
		System.out.println(userId);
	    System.out.println(beforePw);
	    System.out.println(afterPw);
	    MemberDAO mdo = MemberDAO.getInstance();
	    
	    int result = mdo.searchangePW(userId, beforePw, afterPw);
	    
	    
	    System.out.println(result);
	    
	    
	    jsonObj.put("result", result);
	    jsonArr.add(jsonObj);
	    
	    out.println(jsonArr);
	    out.close();
		
	}

}
