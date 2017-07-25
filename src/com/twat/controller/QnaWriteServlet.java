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

import com.twat.dao.QnaDAO;

/**
 * Servlet implementation class QnaWriteServlet
 */
//@WebServlet("/QnaWrite.do")
public class QnaWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnaWriteServlet() {
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
	    
	    HttpSession session = request.getSession();

	    
	    
	    String qnaId = (String)session.getAttribute("loginUserId");
	    
	    String mobile = request.getParameter("mobile");
	    if(mobile != null){
	    	qnaId=mobile;
	    }
	    
	    String qnaCategory = request.getParameter("qnaCategory");
	    
	    int qnaPw = Integer.parseInt(request.getParameter("qnaPw"));
	    String qnaTitle = request.getParameter("qnaTitle");
	    String qnaContents = request.getParameter("qnaContents");
	    System.out.println(qnaCategory);
	    System.out.println(qnaPw);
	    System.out.println(qnaTitle);
	    System.out.println(qnaContents);
	    
	    
	    QnaDAO qnaDao = QnaDAO.getInstance();
	    int result = qnaDao.insertQnA(qnaId, qnaCategory, qnaPw, qnaTitle, qnaContents);
	    
	    PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
		
		if(result == 1) {
			jsonOb.put("result", "success");
		} else {
			jsonOb.put("result", "fail");
		}
		
		jsonList.add(jsonOb);
	    writer.println(jsonList);
	    writer.close();
	    
	    
	}

}
