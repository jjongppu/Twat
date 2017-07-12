package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

/**
 * Servlet implementation class LoginServlet
 */
//@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
	
		
		MemberDAO memDao = MemberDAO.getInstance();
		long result = memDao.loginMember(userid, userpw);
		long currentTime = System.currentTimeMillis();
		
		PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
		
		
		// 占싸깍옙占쏙옙 占쏙옙占쏙옙/占쏙옙占쏙옙
		if(result == -1) { // 占싣듸옙, 占쏙옙占� 틀占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙
			jsonOb.put("result", "fail");
		}else if(result == 0){
			HttpSession session = request.getSession();
			session.setAttribute("loginUserId", userid);
			jsonOb.put("result", "success");
		}else if(currentTime < result) { // 탈占쏙옙占쏙옙占쏙옙 회占쏙옙
			jsonOb.put("result", "outIng");
		} else if(currentTime > result) { // 탈占쏙옙占� 회占쏙옙
			jsonOb.put("result", "out");
		}
		
		
		
		jsonList.add(jsonOb);
		
		writer.println(jsonList);
		writer.close();
	}
}
