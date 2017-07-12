package com.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.admin.dao.AdminDAO;

//@WebServlet("/AdminSetVisitToday.do")
public class AdminSetVisitToday extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminSetVisitToday() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//CURRENT_TIMESTAMP
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession();
	    
	    
	    String userturn = request.getParameter("turn");
		String userid = request.getParameter("userid");
		//오늘 정각까지의 시간
		long dates = Long.parseLong(request.getParameter("dates"));
		//현재시간
		long nowtimes = System.currentTimeMillis();


		if(!userturn.equals("getCookie")){
			Cookie chokoCook = new Cookie(userid,"visitUser");  // cookie name : id ,  value : kjg
			chokoCook.setMaxAge((int) (dates-nowtimes));
			response.addCookie(chokoCook);
		}
	
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();

        String strToday = sdf.format(c1.getTime());
        System.out.println(userturn);
	  
	    AdminDAO ado = AdminDAO.getInstance();
		boolean result = ado.visitups(userturn,userid,strToday);
	
		
		
		
		PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
		
		// 로그인 성공/실패 
		if(!result){
			jsonOb.put("result", "fail");
		} else {
			jsonOb.put("result", "success");
		}
		jsonList.add(jsonOb);
		writer.println(jsonList);
	}

}
