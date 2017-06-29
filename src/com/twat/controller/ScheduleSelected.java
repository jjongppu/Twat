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
import com.twat.dao.MemberJoinGroupDAO;

/**
 * Servlet implementation class ScheduleSelected
 */
@WebServlet("/scheduleSelected.do")
public class ScheduleSelected extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleSelected() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		JSONArray group = new JSONArray();
		JSONObject test = new JSONObject();
		
		String calNum = request.getParameter("calNum");
		String calDate = request.getParameter("calDate");
		String groupId = request.getParameter("groupId");
		
		CalendarDAO calDAO = CalendarDAO.getInstance();
		calDAO.scheduleSelected(calNum, calDate);
		
		MemberJoinGroupDAO mDao = MemberJoinGroupDAO.getInstance();
		mDao.updateCalView(Integer.parseInt(groupId));
		
		test.put("groupId", groupId);
		test.put("cal_num", calNum);
		test.put("calDate", calDate);
		group.add(test);
		
		out.print(group.toJSONString());
		out.close();
	}

}
