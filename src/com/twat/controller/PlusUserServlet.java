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
 * Servlet implementation class PlusUserServlet
 */
@WebServlet("/PlusUserServlet.do")
public class PlusUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlusUserServlet() {
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
	    String userPhone = request.getParameter("findPhoneNumber");
	    
	    MemberDAO mdo = MemberDAO.getInstance();
	    ArrayList<MemberVO> arList = mdo.plusFriend(userPhone, MEMBER_ID);

	    JSONArray jsonList = new JSONArray();
	    if(arList.size() > 0){
	    	for(int i = 0 ; i < arList.size(); i++){
	    		JSONObject jsonOb = new JSONObject();
	    		MemberVO mvo = arList.get(i);
	    		
	    		jsonOb.put("MEMBER_ID",mvo.getMEMBER_ID());
	    		jsonOb.put("MEMBER_IMG", mvo.getMEMBER_IMG());
	    		jsonOb.put("MEMBER_NAME", mvo.getMEMBER_NAME());
	    		jsonOb.put("MEMBER_PHONE", mvo.getMEMBER_PHONE());
	    		jsonOb.put("MEMBER_BIRTH", mvo.getMEMBER_BIRTH());
	    		
	    		jsonList.add(jsonOb);
	    		
	    	}
	    	
	    }else{
	    	
	    	JSONObject jsonob = new JSONObject();
	    	jsonob.put("MEMBER_ID", "");
	    	jsonList.add(jsonob);
	    }
	    
	    out.print(jsonList.toJSONString());
	    out.close();
	    
	}

}
