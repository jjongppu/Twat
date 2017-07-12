package com.admin.controller;

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

import com.admin.dao.NoticeBoardDAO;

/**
 * Servlet implementation class WriteNoticeBoard
 */
//@WebServlet("/WriteNoticeBoard.do")
public class WriteNoticeBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteNoticeBoard() {
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
	    HttpSession session = request.getSession();
	    
//	    System.out.println(session.getAttribute("loginAdmin"));
//	    System.out.println(request.getParameter("noticeTitle"));
//	    System.out.println(request.getParameter("noticeContent"));
//	    System.out.println(request.getParameter("noticeClassification"));
	    
	    NoticeBoardDAO noticeBoardDAO = NoticeBoardDAO.getInstance();
	    int result = noticeBoardDAO.inputNotice((String)session.getAttribute("loginAdmin"), request.getParameter("noticeTitle"), request.getParameter("noticeContent"), request.getParameter("noticeClassification"));
	    
	    
	    jsonObj.put("result", result);
	    jsonArr.add(jsonObj);
	    out.println(jsonArr);
	    out.close();
	    
	    
	    
		
	}

}
