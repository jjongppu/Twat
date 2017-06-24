package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.admin.dao.AdminDAO;
import com.twat.dto.QnaVO;

@WebServlet("/getQnaList.do")
public class GetQnaList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetQnaList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		
		AdminDAO qnaDao = AdminDAO.getInstance();
		ArrayList<QnaVO> groupInfo = qnaDao.getQnaList(page,val);
		
			
			if(groupInfo.size() > 0 ){
				for (int i = 1; i < groupInfo.size(); i++) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("COUNT", groupInfo.get(0).getQNA_ID());
					jsonObj.put("QNA_ID", groupInfo.get(i).getQNA_ID());
					jsonObj.put("MEMBER_ID", groupInfo.get(i).getMEMBER_ID());
					jsonObj.put("QNA_CATEGORY", groupInfo.get(i).getQNA_CATEGORY());
					jsonObj.put("QNA_PW", groupInfo.get(i).getQNA_PW());
					jsonObj.put("QNA_TITLE", groupInfo.get(i).getQNA_TITLE());
					jsonObj.put("QNA_CONTENTS", groupInfo.get(i).getQNA_CONTENTS());
					jsonObj.put("QNA_DATE", groupInfo.get(i).getQNA_DATE().toString());
					jsonObj.put("QNA_REPLY", groupInfo.get(i).getQNA_REPLY());
					
					jarr.add(jsonObj);
				}
			}else{
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("group_id","noGroup");
				jarr.add(jsonObj);
			}
		
			
			
			
		out.print(jarr);
		out.flush();
		out.close();
	}

}
