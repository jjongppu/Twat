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

import com.twat.dao.CalendarDAO;
import com.twat.dao.CalgatherDAO;
import com.twat.dao.MyCalendarDAO;
import com.twat.dto.CalendarVO;
import com.twat.dto.CalgatherVO;
import com.twat.dto.MyCalendarVO;

//@WebServlet("/myCal.do")
public class MyCalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;       

    public MyCalendarServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("myCalendar.html");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("loginUserId");
		System.out.println(session.getAttribute("loginUserId"));
		JSONArray jsonArr = new JSONArray();
		PrintWriter out = response.getWriter();

		MyCalendarDAO myCalDao = MyCalendarDAO.getInstance();
		
		ArrayList<MyCalendarVO> myCalArr = myCalDao.getInfo(userId);
		
		for(int i=0; i<myCalArr.size(); i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("myCalIndex", myCalArr.get(i).getMy_cal_index());
			jsonObj.put("memberId", myCalArr.get(i).getMember_id());
//			jsonObj.put("myWriteTime", myCalArr.get(i).getMy_write_time());
			jsonObj.put("myCalContents", myCalArr.get(i).getMy_cal_contents());
			jsonObj.put("myCalDate", myCalArr.get(i).getMy_cal_date());
			jsonObj.put("myCalTime", myCalArr.get(i).getMy_cal_time());
			jsonArr.add(jsonObj);
		}
		
		System.out.println(myCalArr);
//		System.out.println(myCalArr);
//		System.out.println(myCalArr);
		
		System.out.println(jsonArr.toJSONString());
		out.println(jsonArr);
		out.close();
	}

}
