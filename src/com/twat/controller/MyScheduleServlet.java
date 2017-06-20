package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.MyCalendarDAO;

@WebServlet("/MyScheduleServlet")
public class MyScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyScheduleServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;");
		response.setHeader("Cache-Control", "no-cache");
		
		JSONArray jsonArr = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		PrintWriter out = response.getWriter();
		
		MyCalendarDAO mycalendarDAO = MyCalendarDAO.getInstance();
		
	}

}