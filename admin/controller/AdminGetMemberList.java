package com.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.admin.dao.AdminDAO;
import com.twat.dto.MemberVO;

@WebServlet("/getmemberInfo.do")
public class AdminGetMemberList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminGetMemberList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
		
		int page = Integer.parseInt(request.getParameter("page"));
		
		AdminDAO adDao = AdminDAO.getInstance();
		ArrayList<MemberVO> result = adDao.adminlogin(page);
		
		
		PrintWriter out = response.getWriter();
		JSONArray jsonList = new JSONArray();
		
		
		// 로그인 성공/실패 
		if(result.size() !=0 ){
			for (int i = 1; i < result.size(); i++) {
				JSONObject jsonOb = new JSONObject();
				jsonOb.put("memberCount", result.get(0).getMEMBER_ID());
				jsonOb.put("memberId", result.get(i).getMEMBER_ID());
				jsonOb.put("memberImg",result.get(i).getMEMBER_IMG() );
				jsonOb.put("memberName",result.get(i).getMEMBER_NAME() );
				jsonOb.put("memberPhone", result.get(i).getMEMBER_PHONE());
				jsonOb.put("memberGender", result.get(i).getMEMBER_GENDER());
				jsonOb.put("memberBir", result.get(i).getMEMBER_BIRTH());
				jsonOb.put("memberStar", result.get(i).getSTART_DATE().toString());
				
				jsonList.add(jsonOb);
			}
			
		} else {
			jsonList.add(new JSONObject().put("memberCount", "-1"));
		}
		out.println(jsonList);
		
	}

}
