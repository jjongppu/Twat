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

import com.mysql.fabric.xmlrpc.base.Member;
import com.twat.dao.MemberDAO;
import com.twat.dto.MemberVO;

/**
 * Servlet implementation class inviteFriends
 */
@WebServlet("/inviteFriends.do")
public class inviteFriends extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public inviteFriends() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");		
	    
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("loginUserId");	    
	    
	    JSONArray jsonArr = new JSONArray();
	    PrintWriter out = response.getWriter();
	    
	    MemberDAO memberDAO = MemberDAO.getInstance();
	    
	    ArrayList<MemberVO> memArr = memberDAO.getFriendList(userId);
	    
	    for(int i = 0; i < memArr.size(); i++){
	    	JSONObject jsonObj = new JSONObject();
	    	jsonObj.put("friendId", memArr.get(i).getMEMBER_ID());
	    	jsonObj.put("friendName", memArr.get(i).getMEMBER_NAME());	    	
	    	jsonArr.add(jsonObj);
	    	
	    }
	    
	    System.out.println(jsonArr.toJSONString());
	    out.print(jsonArr);
	    
	    
	    
	    
	    
	    
		
	}

}