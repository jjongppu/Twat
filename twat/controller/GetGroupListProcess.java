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
import com.twat.dto.CalgatherVO;


@WebServlet("/groupList.do")
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
		JSONArray jarr = new JSONArray();
		PrintWriter out = response.getWriter();
		
		
		String userId = (String)session.getAttribute("loginUserId");
		MemberDAO mdao = MemberDAO.getInstance();
		ArrayList<Integer> group_List = mdao.getMyGroupList(userId);

		CalgatherDAO calDao = CalgatherDAO.getInstance();
		ArrayList<CalgatherVO> groupInfo = calDao.myGroupList(group_List);
		
		if(group_List.size() > 0 ){
			
			if(groupInfo.size() > 0 ){
				for (int i = 0; i < groupInfo.size(); i++) {
					JSONObject jsonObj = new JSONObject();
					CalgatherVO cao = (CalgatherVO)groupInfo.get(i);
					jsonObj.put("group_id", cao.getGroup_id());
					jsonObj.put("group_name", cao.getGroup_name());
					jsonObj.put("create_date", cao.getCreate_date());
					jsonObj.put("group_master", cao.getGroup_master());
					jsonObj.put("group_master_name", cao.getGroup_master_name());
					jsonObj.put("group_img", cao.getGroup_img());
					jsonObj.put("group_count", cao.getGroup_count());
					
					jarr.add(jsonObj);
				}
			}else{
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("group_id","noGroup");
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
