package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.CalgatherDAO;
import com.twat.dao.MemberDAO;
import com.twat.dao.MemberJoinGroupDAO;
import com.twat.dto.CalgatherVO;
import com.twat.dto.MemberJoinGroupVO;


//@WebServlet("/groupList.do")
public class GetGroupListProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetGroupListProcess() {
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
		
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("loginUserId");
		
		JSONArray jarr = new JSONArray();
		PrintWriter out = response.getWriter();
		ArrayList<CalgatherVO> groupInfo = null;
		
		// 종류 구분해버리깃
		int kinds = Integer.parseInt(request.getParameter("kind"));

		if(kinds ==3){
			userId = request.getParameter("login");
		}
		
		CalgatherDAO calDao = CalgatherDAO.getInstance();
		MemberJoinGroupDAO getGourpInfo = MemberJoinGroupDAO.getInstance();
		ArrayList<MemberJoinGroupVO> mjg = getGourpInfo.getViewCountCheck(userId);
		System.out.println(mjg.size());
		// 1일떄만 방정보까지 얻어옴 불필요한 정보 최소화
		if(kinds == 1 || kinds == 3){
			groupInfo = calDao.myGroupList(mjg);
			System.out.println(groupInfo.size());
		}
		
		if(mjg.size() > 0 ){
			for (int i = 0; i < mjg.size(); i++) {
				JSONObject jsonObj = new JSONObject();
				if(kinds == 1 || kinds == 3){
					CalgatherVO cao = (CalgatherVO)groupInfo.get(i);
					jsonObj.put("group_id", cao.getGroup_id());
					jsonObj.put("group_name", cao.getGroup_name());
					jsonObj.put("create_date", cao.getCreate_date());
					jsonObj.put("group_master", cao.getGroup_master());
					jsonObj.put("group_master_name", cao.getGroup_master_name());
					jsonObj.put("group_img", cao.getGroup_img());
					jsonObj.put("group_count", cao.getGroup_count());
				}
				MemberJoinGroupVO mj = mjg.get(i);
				jsonObj.put("view_me", mj.getCALENDAR_VIEW());
				jsonObj.put("view_group", mj.getCALENDAR_READ_VIEW());
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
