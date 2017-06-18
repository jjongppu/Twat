package com.twat.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.fabric.Server;
import com.twat.dao.CalendarDAO;
import com.twat.dao.CalgatherDAO;

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
		
		CalendarDAO calendarDao = CalendarDAO.getInstance();
		int count = calendarDao.getLastCalNum();		
		HttpSession session = request.getSession();

		
		String cal_date = request.getParameter("calDate");
		String cal_date2 = cal_date.substring(0, cal_date.length() - 1); 
		String cal_memo = request.getParameter("title");
		String cal_writer = (String) session.getAttribute("loginUserId");
		System.out.println(cal_memo);
		System.out.println(cal_date2);
		System.out.println(cal_writer);
		System.out.println(count);		
		
		System.out.println(request.getRequestURL().toString());
	
		calendarDao.addGroupCal(count, cal_date2, 1, cal_memo, cal_writer);
		
//		response.sendRedirect(request.getRequestURL().toString());
		
	
	}

}
