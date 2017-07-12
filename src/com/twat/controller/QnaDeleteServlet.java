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
import com.twat.dao.QnaDAO;

/**
 * Servlet implementation class QnaDeleteServlet
 */
//@WebServlet("/QnaDelete.do")
public class QnaDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnaDeleteServlet() {
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
	    
	    JSONArray jsonArr = new JSONArray();
	    JSONObject jsonObj = new JSONObject();
	    PrintWriter out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    String userId = (String)session.getAttribute("loginUserId");
	    int val = Integer.parseInt(request.getParameter("val"));
	    int qnaPw = Integer.parseInt(request.getParameter("pw"));
	    
	    
	    
	    QnaDAO qnaDao = QnaDAO.getInstance();
	    int result = qnaDao.deleteQna(userId, val, qnaPw);
	    System.out.println(val);
	    System.out.println(qnaPw);
	    
	    if(result == 1) {
	    	jsonObj.put("result", "success");
	    } else {
	    	jsonObj.put("result", "fail");
	    }
	    
	    jsonArr.add(jsonObj);
		
	    out.println(jsonArr);
	    out.close();
	}

}
