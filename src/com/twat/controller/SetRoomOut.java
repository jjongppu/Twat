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

import com.twat.dao.CalendarDAO;
import com.twat.dao.CalgatherDAO;
import com.twat.dao.MemberJoinGroupDAO;

/**
 * Servlet implementation class SetRoomOut
 */
@WebServlet("/setRoomOut.do")
public class SetRoomOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetRoomOut() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		
		JSONArray result = new JSONArray();
		
		/*
		 	groupID: group_id,
			beforeMaster: group_master,
			afterMaster: radio.value.split(",")[1],
			afterMasterName: radio.value.split(",")[0]
		 */
		MemberJoinGroupDAO mDao = MemberJoinGroupDAO.getInstance();
		mDao.roomOut(request.getParameter("groupID"), request.getParameter("userID"));
		
		CalendarDAO calendarDAO = CalendarDAO.getInstance();
//		calendarDAO.changeGM(Integer.parseInt(request.getParameter("groupID")), request.getParameter("beforeMaster"), request.getParameter("afterMaster"));
		
		out.print(result.toJSONString());
		out.close();
	}

}
