package com.admin.controller;

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
import com.twat.dao.CalgatherDAO;
import com.twat.dao.MemberDAO;
import com.twat.dto.CalgatherVO;

@WebServlet("/adminGroupOut.do")
public class AdminGroupOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminGroupOut() {
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
	    
		PrintWriter out = response.getWriter();
		// 삭제될 그룹 번호
	    String Group_Id = request.getParameter("out");
	    // 삭제할 종류 1=그룹 게시글 댓글 삭제  2=게시글 댓글 삭제
	    int kind = Integer.parseInt(request.getParameter("kind"));
	    
	    AdminDAO ado = AdminDAO.getInstance();
		
	    JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
	    
		int result = ado.outGroup(Group_Id,kind);
		
	    if(result ==1){
	    	jsonOb.put("result", "1");
	    }else if(result ==2){
	    	jsonOb.put("result", "2");
	    }else if(result ==3){
	    	jsonOb.put("result", "3");
	    }else{
	    	jsonOb.put("result", "0");
	    }
		
		jsonList.add(jsonOb);
		
		out.println(jsonList);
		
	}

}
