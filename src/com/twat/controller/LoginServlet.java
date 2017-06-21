package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
		
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
	
//		String date = request.getParameter("date");
		
		MemberDAO memDao = MemberDAO.getInstance();
		int result = memDao.loginMember(userid, userpw);
		
		// ��¥ �غ��� ����
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, 15);
		
		PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
		
		System.out.println(userid);
		System.out.println(userpw);
		System.out.println(result);
//		
		// �α��� ����/���� 
//		if(result == 1){
//			HttpSession session = request.getSession();
//			session.setAttribute("loginUserId", userid);
//			
//			jsonOb.put("result", "success");
//		} else if(result == -1) {
//			jsonOb.put("result", "fail");
//		} else {
//			jsonOb.put("result", result);
//		}
		
		// �α��� ����/���� 
//		if(result == -1) {
//			jsonOb.put("result", "fail");
//		} else if(result > 0) {
//			if(result == 1) {
//				HttpSession session = request.getSession();
//				session.setAttribute("loginUserId", userid);
//				
//				jsonOb.put("result", "success");
//			} else {
//				jsonOb.put("result", result);
//			}
//		}
		
		// �α��� ����/����
		if(result == 0) { // �Ƶ�, ��� Ʋ���� ������
			jsonOb.put("result", "fail");
		} else if(result == -1) { // Ż������ ȸ��
			jsonOb.put("result", "outIng");
		} else if(result == -2) { // Ż��� ȸ��
			jsonOb.put("result", "out");
		} else { // �α��� ����
			HttpSession session = request.getSession();
			session.setAttribute("loginUserId", userid);
			
			jsonOb.put("result", "success");
		}
		
		
		
		jsonList.add(jsonOb);
		
		writer.println(jsonList);
	}
}
