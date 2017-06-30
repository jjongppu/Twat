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
import com.twat.dao.MemberJoinGroupDAO;
import com.twat.dao.MyCalendarDAO;
import com.twat.dto.CalendarVO;
import com.twat.dto.CalgatherVO;
import com.twat.dto.MyCalendarVO;

@WebServlet("/myGroupSchedule.do")
public class MyGroupScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyGroupScheduleServlet() {
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
		JSONArray jsonArr = new JSONArray();
		PrintWriter out = response.getWriter();
		
		String userId = (String) session.getAttribute("loginUserId");
		
		MemberJoinGroupDAO mjgDAO = MemberJoinGroupDAO.getInstance();
		ArrayList<String> groupIdArr = mjgDAO.getGroupId(userId);
		
		System.out.println(groupIdArr);

		CalendarDAO calendarDao = CalendarDAO.getInstance();
		ArrayList<CalendarVO> calendarArr = calendarDao.groupCalInfo(groupIdArr);
		
		if(groupIdArr.size() > 0){
			
			if(calendarArr.size()>0){
				for(int i=0; i<calendarArr.size(); i++){
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("calNum", calendarArr.get(i).getCal_num());
					jsonObj.put("calDate", calendarArr.get(i).getCal_date());
					jsonObj.put("groupId", calendarArr.get(i).getGroup_id());
					jsonObj.put("calMemo", calendarArr.get(i).getCal_memo());
					jsonObj.put("calWriter", calendarArr.get(i).getCal_writer());
					jsonObj.put("calIcon", calendarArr.get(i).getState_icon());
					jsonObj.put("calMemChoice", calendarArr.get(i).getMember_choice());
					jsonObj.put("calDecideDate", calendarArr.get(i).getDecide_date());
					jsonObj.put("calReference", calendarArr.get(i).getCal_reference());
					jsonObj.put("calDepth", calendarArr.get(i).getCal_depth());
					jsonArr.add(jsonObj);
				}
			}
		}
		
		System.out.println(calendarArr);
				
		System.out.println(jsonArr.toJSONString());
		out.println(jsonArr);
		 out.close();
	}

}
