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

import com.twat.dao.CalgatherDAO;
import com.twat.dao.MyCalendarDAO;
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
		String userId = (String) session.getAttribute("loginUserId");
		
		JSONArray jsonArr = new JSONArray();
		PrintWriter out = response.getWriter();

		CalgatherDAO calgatherDao = CalgatherDAO.getInstance();
		
		ArrayList<CalgatherVO> calGatherArr = calgatherDao.getGatherInfo(userId);
		
		for(int i=0; i<calGatherArr.size(); i++){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("groupId", calGatherArr.get(i).getGroup_id());
			jsonObj.put("groupName", calGatherArr.get(i).getGroup_name());
			jsonObj.put("groupCreateDate", calGatherArr.get(i).getCreate_date());
			jsonObj.put("groupMaster", calGatherArr.get(i).getGroup_master());
			jsonObj.put("groupMasterName", calGatherArr.get(i).getGroup_master_name());
			jsonObj.put("groupImg", calGatherArr.get(i).getGroup_img());
			jsonObj.put("groupCount", calGatherArr.get(i).getGroup_count());
			jsonArr.add(jsonObj);
		}
		
		
		System.out.println(jsonArr.toJSONString());
		out.println(jsonArr);
		 out.close();
	}

}
