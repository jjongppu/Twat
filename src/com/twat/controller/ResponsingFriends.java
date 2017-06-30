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
import javax.sql.rowset.RowSetProvider;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.MemberDAO;
import com.twat.dto.MemberVO;

/**
 * Servlet implementation class ResponsingFriends
 */
@WebServlet("/ResponsingFriends.do")
public class ResponsingFriends extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResponsingFriends() {
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
	    JSONObject jsonObj= null;
	    
		PrintWriter out = response.getWriter();	    
	    MemberDAO mdo = MemberDAO.getInstance();
	    ArrayList<MemberVO> responsingFriends = new ArrayList<MemberVO>();
	    
	    HttpSession session = request.getSession();	    
	    String userId = (String) session.getAttribute("loginUserId");
	    
	    
	    
	    
	    String friendsList = mdo.getfriendListForString(userId);
//	    System.out.println(friendsList);
	    
	    
	    responsingFriends = mdo.requestingFriendList(userId, "*");
	    
	    
	    if(responsingFriends != null){
	    	for(int i = 0; i < responsingFriends.size(); i++){
		    	jsonObj = new JSONObject();
		    	jsonObj.put("userId" , responsingFriends.get(i).getMEMBER_ID());
		    	jsonObj.put("userImg", responsingFriends.get(i).getMEMBER_IMG());
		    	jsonObj.put("userName",responsingFriends.get(i).getMEMBER_NAME());
		    	jsonObj.put("userPhone", responsingFriends.get(i).getMEMBER_PHONE());
		    	jsonObj.put("userGender", responsingFriends.get(i).getMEMBER_GENDER());		    	
		    	jsonArr.add(jsonObj);
		    }
		    
		    out.println(jsonArr);
		    out.close();	    	
	    }else{
	    	jsonObj = new JSONObject();
	    	jsonObj.put("result", "fail");
	    	jsonArr.add(jsonObj);
	    	out.println(jsonArr);
		    out.close();	    
	    	
	    }
	    	    
	    
//	    for(int i = 1; i < friendsList.split(",").length; i++){
//	    	if(friendsList.split(",")[i].substring(0, 1).equals("*")){
//	    		System.out.println(friendsList.split(",")[i].substring(1, friendsList.split(",")[i].length()));
//	    		
//	    		responsingFriends.add(mdo.)
//	    		
//	    		
//	    	}
//	    	
//	    	
//	    }
	    
	    
	    
	    
		
		
	}

}
