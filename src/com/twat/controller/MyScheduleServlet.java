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

import com.twat.dao.MyCalendarDAO;

//@WebServlet("/mySchedule.do")
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
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		JSONArray jsonArr = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		PrintWriter out = response.getWriter();
		
		MyCalendarDAO mycalendarDAO = MyCalendarDAO.getInstance();
		
		String member_id = (String) session.getAttribute("loginUserId");
		String my_cal_contents = (String) request.getParameter("title");
		String my_cal_date = (String) request.getParameter("calDate");		
		String my_cal_time = (String) request.getParameter("calTime");		
		
//		System.out.println(member_id);
//		System.out.println(my_cal_contents);
//		System.out.println(my_cal_date);
		
		mycalendarDAO.addMySchedule(member_id, my_cal_contents, my_cal_date, my_cal_time);
		
		jsonObj.put("result", "succesCalAdd");
		jsonArr.add(jsonObj);		

		System.out.println(jsonArr);		
		out.print(jsonArr);
		out.close();
		
	}

}
