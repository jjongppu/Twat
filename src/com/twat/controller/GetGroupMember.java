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

import com.twat.dao.MemberDAO;
import com.twat.dao.MemberJoinGroupDAO;
import com.twat.dto.MemberVO;

/**
 * Servlet implementation class GetGroupMember
 */
@WebServlet("/getGroupMember.do")
public class GetGroupMember extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGroupMember()
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		
		JSONArray arrayMember = new JSONArray();
		
		MemberJoinGroupDAO joinGroup = MemberJoinGroupDAO.getInstance();
		ArrayList<String> memberList = joinGroup.getMemberId(request.getParameter("groupId"));
		
		MemberDAO memDAO = MemberDAO.getInstance();
		ArrayList<MemberVO> memberInfoList = memDAO.getMemberList(memberList);
		
		if(!memberInfoList.isEmpty())
		{
			for(int i = 0; i < memberInfoList.size(); i++)
			{
				JSONObject member = new JSONObject();
				
				member.put("MEMBER_ID", memberInfoList.get(i).getMEMBER_ID());
				member.put("MEMBER_NAME", memberInfoList.get(i).getMEMBER_NAME());
				member.put("MEMBER_IMG", memberInfoList.get(i).getMEMBER_IMG());
				
				arrayMember.add(member);
			}
		}
		
		out.print(arrayMember.toJSONString());
		out.close();
	}

}
