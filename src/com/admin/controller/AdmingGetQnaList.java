package com.admin.controller;

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
import com.twat.dao.QnaDAO;
import com.twat.dto.CalgatherVO;
import com.twat.dto.QnaVO;

@WebServlet("/admingGetQnaList.do")
public class AdmingGetQnaList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdmingGetQnaList() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					jsonObj.put("groupCount", groupInfo.get(0).getGroup_id());
					jsonObj.put("group_id", groupInfo.get(i).getGroup_id());
					jsonObj.put("group_name", groupInfo.get(i).getGroup_name());
					jsonObj.put("create_date", groupInfo.get(i).getCreate_date());
					jsonObj.put("group_master", groupInfo.get(i).getGroup_master());
					jsonObj.put("group_master_name", groupInfo.get(i).getGroup_master_name());
					jsonObj.put("group_img", groupInfo.get(i).getGroup_img());
					jsonObj.put("group_count", groupInfo.get(i).getGroup_count());
					
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
