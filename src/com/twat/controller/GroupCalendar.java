package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import com.twat.dto.CalendarVO;
import com.twat.dto.CalgatherVO;

/**
 * Servlet implementation class GroupCalendar
 */
@WebServlet("/groupCal.do")
public class GroupCalendar extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupCalendar()
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("groupCalendar.html");
		dispatcher.forward(request, response);
//		
//		String id = request.getParameter("group");
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

		// Calgather에서 그룹 정보 가져옴
		CalgatherDAO calgDao = CalgatherDAO.getInstance();
		CalgatherVO groupTitle = calgDao.getGroupInfo(request.getParameter("group"));
//		CalgatherVO groupTitle = calgDao.getGroupInfo((String)session.getAttribute("loginUserId"));
//		CalgatherVO groupTitle = calgDao.getGroupInfo("2");
		
		if(groupTitle.getGroup_name() != null)
		{
			JSONObject groupInfo = new JSONObject();
			
			groupInfo.put("group_id", groupTitle.getGroup_id());
			groupInfo.put("group_name", groupTitle.getGroup_name());
			groupInfo.put("group_date", groupTitle.getCreate_date());
			groupInfo.put("group_master", groupTitle.getGroup_master());
			groupInfo.put("group_master_name", groupTitle.getGroup_master_name());
			groupInfo.put("group_img", groupTitle.getGroup_img());
			groupInfo.put("group_count", groupTitle.getGroup_count());

			group.add(groupInfo);
		}
		else
		{
//			out.print("<script>console.log('groupTitle값 안들어옴');</script>");
		}
		
		// Calendar에서 그룹의 일정 가져옴
		CalendarDAO calDao = CalendarDAO.getInstance();
		ArrayList<CalendarVO> arrList = calDao.getInfo(request.getParameter("group"));
//		ArrayList<CalendarVO> arrList = calDao.getInfo((String)session.getAttribute("loginUserId"));
//		ArrayList<CalendarVO> arrList = calDao.getInfo("2");
		
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
		
		if(arrList.size() > 0)
		{
			for(int i = 0; i < arrList.size(); i++)
			{
				JSONObject groupSchedule = new JSONObject();
				
				groupSchedule.put("cal_num", arrList.get(i).getCal_num());
				groupSchedule.put("cal_time", simple.format(arrList.get(i).getCal_time()));
				groupSchedule.put("cal_date", arrList.get(i).getCal_date());
//				groupSchedule.put("cal_group", arrList.get(i).getCal_group());
				groupSchedule.put("cal_group", arrList.get(i).getGroup_id());
				//getCal_group
				groupSchedule.put("cal_memo", arrList.get(i).getCal_memo());
				groupSchedule.put("cal_writer", arrList.get(i).getCal_writer());
				groupSchedule.put("state_icon", arrList.get(i).getState_icon());
				groupSchedule.put("member_choice", arrList.get(i).getMember_choice());
				groupSchedule.put("cal_reference", arrList.get(i).getCal_reference());
				groupSchedule.put("cal_depth", arrList.get(i).getCal_depth());
				
				group.add(groupSchedule);
			}
		}
		else
		{
//			out.print("<script>console.log('groupSchedule값 안들어옴');</script>");
		}
		
		// JSON 전송
		
		out.print(group.toJSONString());
		out.close();
		
		if(groupTitle != null)
			groupTitle = null;
		if(calgDao != null)
			calgDao = null;
		if(calDao != null)
			calDao = null;
		if(arrList != null)
			arrList = null;
		if(simple != null)
			simple = null;
		
	}
}
