package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.MyCalendarDAO;

@WebServlet("/scheDelete.do")
public class MyScheDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyScheDeleteServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;");
		response.setHeader("Cashe-control", "no-cashe");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		JSONArray jsonArr = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		PrintWriter out = response.getWriter();
		
		MyCalendarDAO mycalendarDAO = MyCalendarDAO.getInstance();
		
		int deleteSche = Integer.parseInt(request.getParameter("deleteNum"));
		
		mycalendarDAO.deleteMySchedule(deleteSche);
		
		jsonArr.add(jsonObj);
		
//		String url = "myCalendar.html";
//		RequestDispatcher rd = request.getRequestDispatcher(url); //페이지를 넘기는것!!!
//		rd.forward(request, response);// 페이지를 넘기는것!!!
		
		System.out.println(jsonArr);
		out.print(jsonArr);
		out.close();
		
	}

}
