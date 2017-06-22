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
import com.twat.dto.MemberVO;
import com.twat.dto.QnaVO;

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
	    int searchCategory = Integer.parseInt(request.getParameter("searchCategory"));
	    String searchBox = request.getParameter("searchBox");
	    
	    QnaDAO qnaDao = QnaDAO.getInstance();
	    ArrayList<QnaVO> arList = qnaDao.searchQnA(searchCategory, searchBox);
	    
	    PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
		System.out.println(arList.size());
		if(arList.size() > 0) {
			for(int i = 0; i < arList.size(); i++) {
				QnaVO qvo = arList.get(i);
				jsonOb.put("QNA_ID", qnaId);
				jsonOb.put("MEMBER_ID", qvo.getMEMBER_ID());
				jsonOb.put("QNA_CATEGORY", qvo.getQNA_CATEGORY());
				jsonOb.put("QNA_PW", qvo.getQNA_PW());
				jsonOb.put("QNA_TITLE", qvo.getQNA_TITLE());
				jsonOb.put("QNA_CONTENTS", qvo.getQNA_CONTENTS());
				jsonOb.put("QNA_DATE", qvo.getQNA_DATE());
				jsonOb.put("QNA_REPLY", qvo.getQNA_REPLY());
				jsonList.add(jsonOb);
			}
		} else {
			jsonOb.put("result", "fail");
			jsonList.add(jsonOb);
		}
		writer.println(jsonList.toJSONString());
	}
}
