package com.twat.controller;

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

import com.twat.dao.QnaDAO;
import com.twat.dto.QnaVO;

/**
 * Servlet implementation class QnaUpdateServlet
 */
@WebServlet("/QnaUpdate.do")
public class QnaUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnaUpdateServlet() {
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
	    String cate = request.getParameter("category");
	    int pw = Integer.parseInt(request.getParameter("pw"));
//	    String pw = request.getParameter("pw");
	    String qnaTitle = request.getParameter("qnaTitle");
	    String qnaCont = request.getParameter("qnaCont");
	    
	    System.out.println(userId);
	    System.out.println(val);
	    System.out.println(cate);
	    System.out.println(pw);
	    System.out.println(qnaTitle);
	    System.out.println(qnaCont);
	    
	    
	    QnaDAO qnaDao = QnaDAO.getInstance();
		int result = qnaDao.updateQna(userId, val, cate, pw, qnaTitle, qnaCont);
		
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
