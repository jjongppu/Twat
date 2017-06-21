package com.twat.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.twat.dao.QnaDAO;

/**
 * Servlet implementation class SearchQnaServlet
 */
@WebServlet("/SearchQna.do")
public class SearchQnaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchQnaServlet() {
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
	    String searchCategory = request.getParameter("searchCategory");
	    String searchBox = request.getParameter("searchBox");
	    
	    QnaDAO qnaDao = QnaDAO.getInstance();
	    int result = qnaDao.searchQnA(searchCategory, searchBox);
	}

}