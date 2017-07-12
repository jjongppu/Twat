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

import com.admin.dao.AdminDAO;
import com.twat.dao.MemberDAO;
import com.twat.dao.QnaDAO;
import com.twat.dto.MemberVO;
import com.twat.dto.QnaVO;

@WebServlet("/SearchQna.do")
public class SearchQnaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchQnaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("UTF-8");
		
		JSONArray jarr = new JSONArray();
		PrintWriter out = response.getWriter();
		
		int page = Integer.parseInt(request.getParameter("page"));
		String val = request.getParameter("val");
		int kind = Integer.parseInt(request.getParameter("kind"));
		
		QnaDAO qnaDao = QnaDAO.getInstance();
		ArrayList<QnaVO> qnaList = qnaDao.searchQnA(page,val,kind);
		
			if(qnaList.size() > 0 ){
				for (int i = 1; i < qnaList.size(); i++) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("COUNT", qnaList.get(0).getQNA_ID());
					jsonObj.put("QNA_ID", qnaList.get(i).getQNA_ID());
					jsonObj.put("MEMBER_ID", qnaList.get(i).getMEMBER_ID());
					jsonObj.put("QNA_CATEGORY", qnaList.get(i).getQNA_CATEGORY());
					jsonObj.put("QNA_PW", qnaList.get(i).getQNA_PW());
					jsonObj.put("QNA_TITLE", qnaList.get(i).getQNA_TITLE());
					jsonObj.put("QNA_CONTENTS", qnaList.get(i).getQNA_CONTENTS());
					jsonObj.put("QNA_DATE", qnaList.get(i).getQNA_DATE().toString());
					jsonObj.put("QNA_REPLY", qnaList.get(i).getQNA_REPLY());
					
					jarr.add(jsonObj);
				}
			}else{
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("COUNT","noGroup");
				jarr.add(jsonObj);
			}
		out.print(jarr);
		out.flush();
		out.close();
	}
}
