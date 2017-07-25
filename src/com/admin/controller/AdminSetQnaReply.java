package com.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.admin.dao.AdminDAO;

//@WebServlet("/adminSetQnaReply.do")
public class AdminSetQnaReply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminSetQnaReply() {
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
	    
		PrintWriter out = response.getWriter();
		
	    String qna_id = request.getParameter("id");
	    String textval = request.getParameter("textVal");
	    
	    AdminDAO ado = AdminDAO.getInstance();
		
	    JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
	    
		int result = ado.setQnaReply(qna_id,textval);
		
	    if(result != 0){
	    	jsonOb.put("result", "1");
	    }else{
	    	jsonOb.put("result", "0");
	    }
		
		jsonList.add(jsonOb);
		
		out.println(jsonList);
		
	}

}
