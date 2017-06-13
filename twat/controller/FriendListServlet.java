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
import org.json.simple.JSONObject;

import com.twat.dto.FriendMemberVO;
import com.twat.dao.MemberDAO;

@WebServlet("/friend.do")
public class FriendListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FriendListServlet() {
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
	    
	    HttpSession session = request.getSession();
	    Object userid = session.getAttribute("loginUserId");
		
		MemberDAO mDao = MemberDAO.getInstance();
		Object MEMBER_ID = userid;
		mDao.getMyGroupList((String) MEMBER_ID);
		PrintWriter out = response.getWriter();
		
		JSONArray jsonList = new JSONArray();
		
		JSONObject jsonOb = new JSONObject();
		
		FriendMemberVO fvo = new FriendMemberVO();
		
		jsonOb.put("MEMBER_NAME", fvo.getMEMBER_NAME());
		jsonOb.put("MEMBER_BIRTH", fvo.getMEMBER_BIRTH());
		jsonOb.put("MEMBER_PHONE", fvo.getMEMBER_PHONE());
		jsonOb.put("MEMBER_IMG", fvo.getMEMBER_IMG());
		
		jsonList.add(jsonOb);
		out.println(jsonList);
		
		
		//int result = fvo.printFriendList(fvo.getMEMBER_NAME(), fvo.getMEMBER_BIRTH(), fvo.getMEMBER_PHONE(), fvo.getMEMBER_IMG());
		
		doGet(request, response);
	}

}
