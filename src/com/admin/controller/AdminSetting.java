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

//@WebServlet("/adminSetting.do")
public class AdminSetting extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminSetting() {
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
	    HttpSession session = request.getSession();
	    
	    String adminGrade = (String) session.getAttribute("adminGrade");
		String loginAdmin = (String) session.getAttribute("loginAdmin");
		AdminDAO adDao = AdminDAO.getInstance();
		
		PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
		
		// 로그인 성공/실패 
		if(loginAdmin == null ){
			
			jsonOb.put("result", "fail");
		} else {
			jsonOb.put("result", "success");
		}
		
		jsonList.add(jsonOb);
		
		writer.println(jsonList);
		
		
		
		
		
	}

}
