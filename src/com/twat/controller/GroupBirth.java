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
import com.twat.dao.MemberDAO;
import com.twat.dao.MemberJoinGroupDAO;
import com.twat.dto.CalgatherVO;
import com.twat.dto.MemberVO;

/**
 * Servlet implementation class GroupBirth
 */
@WebServlet("/groupBirth.do")
public class GroupBirth extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupBirth()
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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

		MemberJoinGroupDAO mjgDAO = MemberJoinGroupDAO.getInstance();
		ArrayList<String> memberIdList = mjgDAO.getMemberId(request.getParameter("group"));
		
		MemberDAO memDAO = MemberDAO.getInstance();
		ArrayList<MemberVO> memberList = memDAO.getMemberBirth(memberIdList);
		
		for(int i = 0; i < memberList.size(); i ++)
		{
			JSONObject memberInfo = new JSONObject();
			
			memberInfo.put("memberId", memberList.get(i).getMEMBER_ID());
			memberInfo.put("memberName", memberList.get(i).getMEMBER_NAME());
			memberInfo.put("memberBirth", memberList.get(i).getMEMBER_BIRTH());
			
			group.add(memberInfo);
		}
		
		out.print(group.toJSONString());
		out.close();
	}

}
