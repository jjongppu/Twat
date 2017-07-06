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

import com.twat.dao.MemberJoinGroupDAO;

/**
 * Servlet implementation class MyFriendsComeOn
 */
//@WebServlet("/MyFriendsComeOn.do")
public class MyFriendsComeOn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyFriendsComeOn() {
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
	    
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("loginUserId");	    
	    
	    JSONObject jsonObj = new JSONObject();
	    JSONArray jsonArr = new JSONArray();
	    PrintWriter out = response.getWriter();
	    
	    String groupMem = request.getParameter("groupMem");
	    String groupId = request.getParameter("groupId");
	    MemberJoinGroupDAO memberJoinGroupDAO = MemberJoinGroupDAO.getInstance();
	    
	    
	    
	    
	    for(int i = 0; i < groupMem.split(",").length; i++){
	    	
	    	if(groupMem.split(",")[i].length() != 0){
	    		
	    		memberJoinGroupDAO.inviteFriends(groupMem.split(",")[i], Integer.parseInt(groupId));
	    	}
	    	
	    }
	    
	    jsonObj.put("1", "1");
	    jsonArr.add(jsonObj);
	    out.println(jsonArr);
	    
	    
//	    System.out.println(request.getParameter("groupMem"));
//	    System.out.println(request.getParameter("groupId"));
	    
	}

}
