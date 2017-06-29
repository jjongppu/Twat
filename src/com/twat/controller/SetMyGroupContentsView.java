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

import com.twat.dao.CalgatherDAO;
import com.twat.dao.MemberJoinGroupDAO;
import com.twat.dto.CalgatherVO;
import com.twat.dto.MemberJoinGroupVO;

@WebServlet("/setMyGroupContentsView.do")
public class SetMyGroupContentsView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetMyGroupContentsView() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("UTF-8");
		JSONArray jarr = new JSONArray();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("loginUserId");
		int groupid = Integer.parseInt(request.getParameter("group_id"));

		MemberJoinGroupDAO IseeContens = MemberJoinGroupDAO.getInstance();
		IseeContens.updateMyCalView(groupid,userId);
		
		// 1일떄만 방정보까지 얻어옴 불필요한 정보 최소화
		
			JSONObject jsonObj = new JSONObject();
			jsonObj.put(" "," ");
			jarr.add(jsonObj);
		
		
			
		out.print(jarr);
		out.flush();
		out.close();
	
	
	
	
	
	
	//UPDATE member_join_group SET CALENDAR_VIEW = CALENDAR_READ_VIEW WHERE GROUP_ID=? AND MEMBER_ID=?
	}

}
