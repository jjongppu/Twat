package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mysql.fabric.Server;
import com.twat.dao.CalendarDAO;
import com.twat.dao.CalgatherDAO;
import com.twat.dao.MemberJoinGroupDAO;

// 나동주 추가

/**
 * Servlet implementation class ScheduleAdd
 */
@WebServlet("/ScheduleAdd.do")
public class ScheduleAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleAdd() {
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
	    
	    CalgatherDAO calgatherDAO = CalgatherDAO.getInstance();
		CalendarDAO calendarDao = CalendarDAO.getInstance();
		int count = calendarDao.getLastCalNum();		
//		HttpSession session = request.getSession();

		
		String cal_date = request.getParameter("calDate");
		String cal_date2 = cal_date.substring(0, cal_date.length() - 1); 
		String cal_memo = request.getParameter("title");
		int groupId = Integer.parseInt(request.getParameter("groupId"));
		String cal_writer = calgatherDAO.getGroupMaster(groupId);
//		System.out.println(cal_memo);
//		System.out.println(cal_date2);
//		System.out.println(cal_writer);
//		System.out.println(count);		
		
//		System.out.println(request.getRequestURL().toString());
	
		calendarDao.addGroupCal(count, cal_date2, groupId, cal_memo, cal_writer);

		MemberJoinGroupDAO mDao = MemberJoinGroupDAO.getInstance();
		mDao.updateCalView(groupId);
		
//		response.sendRedirect(request.getRequestURL().toString());
		
		
		jsonObj.put("result", "succesCalAdd");
		jsonArr.add(jsonObj);		

		System.out.println(jsonArr);		
		out.print(jsonArr);
		 out.close();

		
	
	}

}
