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
 * Servlet implementation class QnaDetailServlet
 */
@WebServlet("/QnaDetail.do")
public class QnaDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnaDetailServlet() {
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
	    
	    JSONArray jarr = new JSONArray();
		PrintWriter out = response.getWriter();
		
	    System.out.println("!");
		HttpSession session = request.getSession();
	    String userId = (String)session.getAttribute("loginUserId");
	    int val = Integer.parseInt(request.getParameter("val"));
	    System.out.println(val);
	    QnaDAO qnaDao = QnaDAO.getInstance();
		ArrayList<QnaVO> detailList = qnaDao.detailQnA(val);
		
		if(detailList.size() > 0) {
			for(int i = 0; i < detailList.size(); i++) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("MEMBER_ID", detailList.get(i).getMEMBER_ID());
				jsonObj.put("QNA_CATEGORY", detailList.get(i).getQNA_CATEGORY());
				jsonObj.put("QNA_PW", detailList.get(i).getQNA_PW());
				jsonObj.put("QNA_TITLE", detailList.get(i).getQNA_TITLE());
				jsonObj.put("QNA_CONTENTS", detailList.get(i).getQNA_CONTENTS());
				jsonObj.put("QNA_DATE", detailList.get(i).getQNA_DATE().toString());
				jsonObj.put("QNA_REPLY", detailList.get(i).getQNA_REPLY());
				
				// 세션 아이디 가져오기
				jsonObj.put("sessionId", userId);
				jarr.add(jsonObj);
				
			}
		} else {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("result","fail");
			jarr.add(jsonObj);
		}
		out.print(jarr);
		out.flush();
		out.close();
	}

}
