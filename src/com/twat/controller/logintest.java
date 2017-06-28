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

import com.twat.dao.MemberDAO;
import com.twat.dto.MemberVO;

/**
 * Servlet implementation class logintest
 */
@WebServlet("/logintest.do")
public class logintest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logintest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=UTF-8");
		 
//		
//		String jsonStr = "[{\"Product\" : \"Mouse\", \"Maker\":\"Samsung\", \"Price\":23000},"
//	               + "{\"Product\" : \"KeyBoard\", \"Maker\":\"LG\", \"Price\":12000},"
//	               + "{\"Product\":\"HDD\", \"Maker\":\"Western Digital\", \"Price\":156000}]";
		
		MemberDAO mem = MemberDAO.getInstance();
		
		JSONArray jarr = new JSONArray();
		
		PrintWriter out = response.getWriter();		
		String id = request.getParameter("Mid");		
		ArrayList<MemberVO> memArrList = mem.getFriendList(id);
		String jsonStr = "";
		for(int i = 0; i < memArrList.size(); i++){
			JSONObject memObj = new JSONObject();
			memObj.put("id", memArrList.get(i).getMEMBER_ID());
			memObj.put("name", memArrList.get(i).getMEMBER_NAME());
			jarr.add(memObj);
			
		}
		
		
		
		

		
		
		
		
		
		
		out.print(jarr.toJSONString());
		out.flush();
		 out.close();
		
	}

}
