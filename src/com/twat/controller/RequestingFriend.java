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

/**
 * Servlet implementation class RequestingFriend
 */
@WebServlet("/RequestingFriend.do")
public class RequestingFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestingFriend() {
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
	    
	    JSONArray jsonArr = new JSONArray();
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(); 
	    String loginUserId = (String) session.getAttribute("loginUserId");
	    
	    MemberDAO memberDAO = MemberDAO.getInstance();
	    
	    ArrayList<MemberVO> member = memberDAO.requestingFriendList(loginUserId);
//	    System.out.println(member.size());
	    
	    if(member != null){
	    	for(int i = 0; i < member.size(); i++){
	    		JSONObject jsonObj = new JSONObject();
		    	jsonObj.put("friendImg", member.get(i).getMEMBER_IMG());
		    	jsonObj.put("friendId", member.get(i).getMEMBER_ID());
		    	jsonObj.put("friendName", member.get(i).getMEMBER_NAME());
		    	jsonObj.put("friendPhone", member.get(i).getMEMBER_PHONE());		    	
		    	jsonArr.add(jsonObj);	    	
		    	
		    }
	    	
	    }
	    
	    
	    
//	    System.out.println(jsonArr);
	    out.println(jsonArr);
	    
	    
	    
	    
	}

}
