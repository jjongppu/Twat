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
import com.twat.dto.CalendarVO;
import com.twat.dto.CalgatherVO;

//@WebServlet("/adminGetCalenarList.do")
public class AdminGetCalenarList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminGetCalenarList() {
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
		
		AdminDAO admDao = AdminDAO.getInstance();
		ArrayList<CalendarVO> groupInfo = admDao.getCalenarList(page,val);
		
			
			if(groupInfo.size() > 0 ){
				for (int i = 1; i < groupInfo.size(); i++) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("COUNT", groupInfo.get(0).getGroup_id());
					jsonObj.put("CAL_NUM", groupInfo.get(i).getCal_num());
					jsonObj.put("CAL_TIME", groupInfo.get(i).getCal_time().toString());
					jsonObj.put("CAL_DATE", groupInfo.get(i).getCal_date());
					jsonObj.put("GROUP_ID", groupInfo.get(i).getGroup_id());
					jsonObj.put("CAL_MEMO", groupInfo.get(i).getCal_memo());
					jsonObj.put("CAL_WRITER", groupInfo.get(i).getCal_writer());
					jsonObj.put("STATE_ICON", groupInfo.get(i).getState_icon());
					jsonObj.put("MEMBER_CHOICE", groupInfo.get(i).getMember_choice());
					jsonObj.put("CAL_REFERENCE", groupInfo.get(i).getCal_reference());
					jsonObj.put("CAL_DEPTH", groupInfo.get(i).getCal_depth());
					
					jarr.add(jsonObj);
				}
			}else{
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("GROUP_ID","-1");
				jarr.add(jsonObj);
			}
		
			
			
			
		out.print(jarr);
		out.flush();
		out.close();
	}

}
