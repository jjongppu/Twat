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
 * Servlet implementation class ChangeInfo
 */
@WebServlet("/ChangeInfo.do")
public class ChangeInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    
	    PrintWriter out = response.getWriter();
	     
	    HttpSession session = request.getSession();
	    String MEMBER_ID = (String)session.getAttribute("loginUserId");
	    String MEMBER_NAME = request.getParameter("userName");
	    String MEMBER_PHONE = request.getParameter("userPhone");
		String MEMBER_BIRTH = request.getParameter("userBirth");
	    
	    MemberDAO mdo = MemberDAO.getInstance();
	    JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
	    
	    int result = mdo.changeInfo(MEMBER_NAME, MEMBER_PHONE, MEMBER_BIRTH, MEMBER_ID);
	    
	    if(result ==1){
	    	jsonOb.put("result", "success");
	    }else{
	    	jsonOb.put("result", "fail");
	    }
		
		jsonList.add(jsonOb);
		
		out.println(jsonList);
	}

}
