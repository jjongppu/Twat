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
import com.twat.dao.CalgatherDAO;
import com.twat.dao.MemberDAO;
import com.twat.dao.MemberJoinGroupDAO;
import com.twat.dto.MemberVO;

/**
 * Servlet implementation class ChangeGroupMaster
 */
@WebServlet("/changeGroupMaster.do")
public class ChangeGroupMaster extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeGroupMaster() {
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
		CalgatherDAO calgatherDAO = CalgatherDAO.getInstance();
		calgatherDAO.changeGM(Integer.parseInt(request.getParameter("groupID")), request.getParameter("afterMaster"), request.getParameter("afterMasterName"));
		
		CalendarDAO calendarDAO = CalendarDAO.getInstance();
		calendarDAO.changeGM(Integer.parseInt(request.getParameter("groupID")), request.getParameter("beforeMaster"), request.getParameter("afterMaster"));
		
		out.print(result.toJSONString());
		out.close();
	}

}
