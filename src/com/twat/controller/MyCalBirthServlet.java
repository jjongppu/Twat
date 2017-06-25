package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.MemberDAO;
import com.twat.dto.MemberVO;

@WebServlet("/myCalBirth.do")
public class MyCalBirthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyCalBirthServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("groupCalendar.html");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");		
	    
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("loginUserId");	    
	    
	    JSONArray jsonArr = new JSONArray();
	    PrintWriter out = response.getWriter();
	    
	    MemberDAO memberDAO = MemberDAO.getInstance();
	    
	    ArrayList<MemberVO> memArr = memberDAO.getFriendList(userId);
	    
	    for(int i = 0; i < memArr.size(); i++){
	    	JSONObject jsonObj = new JSONObject();
	    	jsonObj.put("friendId", memArr.get(i).getMEMBER_ID());
	    	jsonObj.put("friendName", memArr.get(i).getMEMBER_NAME());	    	
	    	jsonObj.put("friendBirth", memArr.get(i).getMEMBER_BIRTH());	    	
	    	jsonArr.add(jsonObj);
	    	System.out.println(getServletName());
	    }
	    
	    System.out.println(jsonArr.toJSONString());
	    out.print(jsonArr);	}

}
