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

import com.twat.dao.CalgatherDAO;
import com.twat.dao.MemberDAO;
import com.twat.dto.CalgatherVO;


//@WebServlet("/GetViewCountCheck.do")
public class GetViewCountCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public GetViewCountCheck() {
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
		JSONArray jarr = new JSONArray();
		PrintWriter out = response.getWriter();
		
		
		String userId = (String)session.getAttribute("loginUserId");
			
			
			
		out.print(jarr);
		out.flush();
		out.close();
		
	}

}
