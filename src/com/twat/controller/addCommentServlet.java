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

import com.twat.dao.CalendarDAO;

/**
 * Servlet implementation class addCommentServlet
 */
//@WebServlet("/addComment.do")
public class addCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
		
	    
	    JSONArray jsonArr = new JSONArray();
	    JSONObject jsonObj = new JSONObject();
	    PrintWriter out = response.getWriter();
//	    HttpSession session = request.getSession();
//	    String cal_writer = (String) session.getAttribute("loginUserId");
	    
	    CalendarDAO calendarDAO = CalendarDAO.getInstance();
	    
//	    String cal_memo = request.getParameter("cal_memo");
	    String groupId = request.getParameter("group_id");
	    String userId = request.getParameter("user_id");
	    String calNum = request.getParameter("cal_num");
//	    String first_cal = request.getParameter("first_cal");
	    String memo = request.getParameter("new_memo");
	    
//	    group_id: group_id,
//		user_id: login_userId,
//		cal_num: nowCalNum,
//		new_memo: new_memo
	    
//	    System.out.println(cal_memo);
//	    System.out.println(group_id);
//	    System.out.println(first_cal);
//	    System.out.println(new_memo);
	    
//	    calendarDAO.addCalComment(Integer.parseInt(group_id), cal_memo, first_cal, new_memo, cal_writer);
	    
	    calendarDAO.addCalComment(Integer.parseInt(groupId), userId, Integer.parseInt(calNum), memo);
	    jsonObj.put("userId", userId);
	    jsonArr.add(jsonObj);
	    out.print(jsonArr);
	    out.close();
	    if(calendarDAO != null)
	    {
	    	calendarDAO = null;
	    }

	}

}
