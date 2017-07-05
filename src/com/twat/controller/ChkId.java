package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.MemberJoinGroupDAO;

/**
 * Servlet implementation class ChkId
 */
@WebServlet("/chkId.do")
public class ChkId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChkId() {
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
		
//		Date date = new Date();
//		Timestamp timestamp = new Timestamp(date.getTime());
//		System.out.println(timestamp.getTime() + "/" + timestamp.getNanos());
		
		String MEMBER_ID = (String)session.getAttribute("loginUserId");
		String groupId = request.getParameter("groupId");
		
		JSONArray group = new JSONArray();
		
		MemberJoinGroupDAO mDao = MemberJoinGroupDAO.getInstance();
		boolean result = mDao.chkId(MEMBER_ID, groupId);
		
		JSONObject resultOBJ = new JSONObject();
		resultOBJ.put("result", result);
		
		group.add(resultOBJ);
		
		out.print(group.toJSONString());
		out.close();
		
		if(group != null)
		{
			group = null;
		}
		if(mDao != null)
		{
			mDao = null;
		}
		if(resultOBJ != null)
		{
			resultOBJ = null;
		}
	}

}
