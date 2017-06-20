package com.admin.controller;

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

import com.admin.dao.AdminDAO;

@WebServlet("/getinfo.do")
public class GetTwatDataInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetTwatDataInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
		
		AdminDAO adDao = AdminDAO.getInstance();
		ArrayList<Integer> info = adDao.getAllInfo();
		
		PrintWriter out = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
		
		jsonOb.put("memberCount", info.get(0));
		jsonOb.put("qnaCount", info.get(1));
		jsonOb.put("calendatCount", info.get(2));
		jsonOb.put("visitCount", info.get(3));
		jsonOb.put("calgtherCount", info.get(4));
		
		jsonList.add(jsonOb);
		
		out.println(jsonList);
		
		
	}

}
