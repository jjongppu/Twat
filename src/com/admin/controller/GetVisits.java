package com.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.twat.dto.VisitVO;

//@WebServlet("/GetVisits.do")
public class GetVisits extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetVisits() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession();

	    AdminDAO ado = AdminDAO.getInstance();
		ArrayList<VisitVO> result = ado.getVisits();
		
		PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		
		if(result.size()>0){
//			JSONObject jsonOb1 = new JSONObject();
//			jsonOb1.put("VISIT_KIND", result.get(0).getVISIT_KIND());
//			jsonOb1.put("VISIT_COUNT", result.get(0).getVISIT_COUNT());
//			jsonList.add(jsonOb1);
			for(int i=0;i < result.size();i++){
				JSONObject jsonOb = new JSONObject();
				jsonOb.put("VISIT_KIND", result.get(i).getVISIT_KIND());
				jsonOb.put("VISIT_COUNT", result.get(i).getVISIT_COUNT());
				jsonList.add(jsonOb);
			}
		}else{
			JSONObject jsonOb = new JSONObject();
			jsonOb.put("VISIT_KIND", "-1");
			jsonList.add(jsonOb);
		}
		
		writer.println(jsonList);
	}

}
