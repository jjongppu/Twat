package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.CalendarDAO;
import com.twat.dto.CalendarVO;

/**
 * Servlet implementation class GroupSchedule
 */
@WebServlet("/groupSchedule.do")
public class GroupSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupSchedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		JSONArray group = new JSONArray();
		
		// Calendar에서 그룹의 일정 가져옴
		CalendarDAO calDao = CalendarDAO.getInstance();
		ArrayList<CalendarVO> arrList = calDao.getInfo(request.getParameter("group"), request.getParameter("calNum"));
//		ArrayList<CalendarVO> arrList = calDao.getInfo((String)session.getAttribute("loginUserId"));
//		ArrayList<CalendarVO> arrList = calDao.getInfo("2");
		
		JSONObject groupSchedule = new JSONObject();
					
		groupSchedule.put("cal_num", arrList.get(0).getCal_num());
		groupSchedule.put("cal_date", arrList.get(0).getCal_date());
//		groupSchedule.put("cal_group", arrList.get(0).getCal_group());
		groupSchedule.put("cal_group", arrList.get(0).getCal_group());
		//getCal_group
		groupSchedule.put("cal_memo", arrList.get(0).getCal_memo());
		groupSchedule.put("cal_writer", arrList.get(0).getCal_writer());
		groupSchedule.put("state_icon", arrList.get(0).getState_icon());
		groupSchedule.put("member_choice", arrList.get(0).getMember_choice());
		groupSchedule.put("cal_reference", arrList.get(0).getCal_reference());
		groupSchedule.put("cal_depth", arrList.get(0).getCal_depth());
			
		group.add(groupSchedule);
		
		// JSON 전송
		
		out.print(group.toJSONString());
		out.close();
	}

}
