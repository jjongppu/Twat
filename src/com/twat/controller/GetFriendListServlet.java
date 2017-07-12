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


//@WebServlet("/friends.do")
public class GetFriendListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetFriendListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    
	    HttpSession session = request.getSession();
	    String userId = (String)session.getAttribute("loginUserId");
	    
		
	    MemberDAO md = MemberDAO.getInstance();
		ArrayList<MemberVO> friends = md.getFriendList(userId);
		
		PrintWriter out = response.getWriter();
		
		JSONArray jarr = new JSONArray();
		if(friends.size() > 0){
			for (int i = 0; i < friends.size(); i++) {
				JSONObject job = new JSONObject();
				MemberVO fr = friends.get(i);
				job.put("MEMBER_ID", fr.getMEMBER_ID());
				job.put("MEMBER_NAME", fr.getMEMBER_NAME());
				job.put("MEMBER_PHONE", fr.getMEMBER_PHONE());
				job.put("MEMBER_IMG", fr.getMEMBER_IMG());
				job.put("MEMBER_GENDER", fr.getMEMBER_GENDER());
				job.put("MEMBER_BIRTH", fr.getMEMBER_BIRTH());
				jarr.add(job);
			}
		}else{
			JSONObject job = new JSONObject();
			job.put("MEMBER_ID", "");
			jarr.add(job);
		}
		out.println(jarr);
		 out.close();
		
	}

}
