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
import com.twat.dto.MemberVO;

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
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    session.getAttribute("loginUserId");
		
		MemberDAO mDao = MemberDAO.getInstance();
		ArrayList<MemberVO> arList = mDao.getMyGroupList(MEMBER_ID);
		
		
		
		JSONArray jsonList = new JSONArray();	
		if(arList.size() > 0){
			for(int i=0; i < arList.size(); i++){
				JSONObject jsonOb = new JSONObject();
				MemberVO fvo = arList.get(i);
				jsonOb.put("MEMBER_NAME", fvo.getMEMBER_NAME());
				jsonOb.put("MEMBER_BIRTH", fvo.getMEMBER_BIRTH());
				jsonOb.put("MEMBER_PHONE", fvo.getMEMBER_PHONE());
				jsonOb.put("MEMBER_IMG", fvo.getMEMBER_IMG());
				jsonList.add(jsonOb);
			}
		}else{
			jsonList = new JSONObject();
			jsonList.put("MEMBER_ID", "");
			arList.add(jsonList);
		}
		
		
		out.println(arList);
		
		
		
	}

}
