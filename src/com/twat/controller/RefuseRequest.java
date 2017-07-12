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

import com.mysql.fabric.xmlrpc.base.Member;
import com.twat.dao.MemberDAO;

/**
 * Servlet implementation class RefuseRequest
 */
//@WebServlet("/RefuseRequest.do")
public class RefuseRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefuseRequest() {
        super();
        // TODO Auto-generated constructor stub
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
	    String loginUserId = (String) session.getAttribute("loginUserId");
	    String friendId = request.getParameter("friendId");
	    String tok = request.getParameter("tok");
	    
	    MemberDAO memberDAO = MemberDAO.getInstance();
	    
	    
	    memberDAO.requestCancelRefuse(loginUserId, friendId, tok);
	    	
	    
	    
	    
	    jsonObj.put("result", "refuseSuccess");
	    jsonArr.add(jsonObj);
	    
	    out.println(jsonArr);
	    
	    
	    
	
	}

}
