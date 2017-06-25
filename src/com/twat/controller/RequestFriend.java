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

import com.twat.dao.MemberDAO;

/**
 * Servlet implementation class RequestFriend
 */
@WebServlet("/RequestFriend.do")
public class RequestFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestFriend() {
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
		    JSONObject jsonObj = new JSONObject();
		    PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession(); 
		    String loginUserId = (String) session.getAttribute("loginUserId");
		    String friendId = request.getParameter("friendId");
		    //*로시작 : 친구요청을 함
		    //!로시작 : 친구요청을 받음
		    
		    System.out.println(loginUserId);
		    System.out.println(friendId);
		    
		    MemberDAO memberDao = MemberDAO.getInstance();
		    int result = memberDao.requestFriend(loginUserId, friendId); 
		    if(result == -1){
		    	jsonObj.put("friendExist", "Exist");		    	
		    }else if(result == 0){
		    	jsonObj.put("friendExist", "notExist");
		    }else if(result == 1){
		    	jsonObj.put("friendExist", "requestExist");
		    }else if(result == 2){
		    	jsonObj.put("friendExist", "responseExist");
		    }else if(result == 3){
		    	jsonObj.put("friendExist", "me");
		    }
		    	
		    jsonArr.add(jsonObj);
		    
		    out.print(jsonArr);
		    
		    
		    
	}

}
