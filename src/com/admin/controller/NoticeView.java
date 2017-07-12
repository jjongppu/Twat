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

//@WebServlet("/NoticeView.do")
public class NoticeView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NoticeView() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    
		PrintWriter out = response.getWriter();
		// 댓글이달릴 글 번호
	    Long views = Long.parseLong(request.getParameter("id"));
	    
	    AdminDAO ado = AdminDAO.getInstance();
	    Long viewss = ado.getAndSetNoticeView(views);
	    JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
	    
		
	    	jsonOb.put("result", viewss+"");
		
		jsonList.add(jsonOb);
		
		out.println(jsonList);
	}

}
