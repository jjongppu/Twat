package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/SessionCheck.do")
public class SessionCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SessionCheck() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonObj = new JSONObject();
		JSONArray jarr = new JSONArray();
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
			
		if(session.getAttribute("loginUserId") == null){
			jsonObj.put("result", "");
		}else{
			jsonObj.put("result", "s");
		}
			
	
		jarr.add(jsonObj);
		out.print(jarr);
		 out.close();
	}

}