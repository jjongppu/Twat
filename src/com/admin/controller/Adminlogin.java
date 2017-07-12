package com.admin.controller;

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

import com.admin.dao.AdminDAO;


//@WebServlet("/adminlogin.do")
public class Adminlogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Adminlogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
		
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		
		AdminDAO adDao = AdminDAO.getInstance();
		String result = adDao.adminlogin(userid, userpw);
		
		PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
		
		// 로그인 성공/실패 
		if(result != ""){
			HttpSession session = request.getSession();
			
			session.setAttribute("loginAdmin", userid);
			session.setAttribute("adminGrade", result);
			
			jsonOb.put("result", "success");
		} else {
			jsonOb.put("result", "fail");
		}
		
		jsonList.add(jsonOb);
		
		writer.println(jsonList);
		
		
		
		
		
		
		
	}

}
