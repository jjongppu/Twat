package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.MemberDAO;
import com.twat.dto.MemberVO;

/**
 * Servlet implementation class personalServlet
 */
@WebServlet("/PersonalServlet.do")
public class PersonalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public PersonalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
		// TODO Auto-generated method stub
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    
	    HttpSession session = request.getSession();
	    String MEMBER_ID = (String)session.getAttribute("loginUserId");
	    
	    MemberDAO mdo = MemberDAO.getInstance();
	    
	    ArrayList<MemberVO> arList = mdo.myInfo(MEMBER_ID);
	    
	    JSONArray jsonArr = new JSONArray();
	    
	    if(arList.size() > 0){
	    	for(int i = 0 ; i < arList.size(); i++){
	    		JSONObject jsonOb = new JSONObject();
	    		MemberVO mvo = arList.get(i);
	    		jsonOb.put("MEMBER_ID",MEMBER_ID);
	    		jsonOb.put("MEMBER_IMG", mvo.getMEMBER_IMG());
	    		jsonOb.put("MEMBER_NAME", mvo.getMEMBER_NAME());
	    		jsonOb.put("MEMBER_PHONE", mvo.getMEMBER_PHONE());
	    		jsonOb.put("MEMBER_BIRTH", mvo.getMEMBER_BIRTH());
	    		jsonArr.add(jsonOb);
	    		
	    	}
	    }else{
	    	JSONObject jsonob = new JSONObject();
	    	jsonob.put("MEMBER_ID", "");
	    	jsonArr.add(jsonob);
	    }
	    out.print(jsonArr);
		
	}

}
