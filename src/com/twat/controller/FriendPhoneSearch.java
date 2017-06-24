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

/**
 * Servlet implementation class friendPhoneSearch
 */
@WebServlet("/friendPhoneSearch.do")
public class FriendPhoneSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendPhoneSearch() {
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
	    String friendPhone = request.getParameter("friendPhone");
	    MemberDAO memberDAO = MemberDAO.getInstance();
	    ArrayList<String> memArr = memberDAO.friendPhoneSearch(friendPhone);
	    
//	    for(int i = 0; i < memArr.size(); i++){
//	    	System.out.println(memArr.get(i));
//	    	
//	    }
	    if(memArr.size()!=0){
	    	jsonObj.put("friendId", memArr.get(0));
		    jsonObj.put("friendName", memArr.get(1));
		    jsonObj.put("friendPhone", memArr.get(2));
		    jsonObj.put("friendImg", memArr.get(3));
		    jsonObj.put("friendGender", memArr.get(4));
		    jsonObj.put("friendBirth", memArr.get(5));
		    jsonObj.put("userExist", "Exist");
	    }else{
	    	jsonObj.put("userExist", "notExist");
	    }
	    
	    
	    
	    jsonArr.add(jsonObj);
	    
	    
	    
	    
//	    System.out.println(jsonArr);
	    out.print(jsonArr);
	    

	}

}
