package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.CalgatherDAO;

/**
 * Servlet implementation class groupMasterServlet
 */
@WebServlet("/groupMaster.do")
public class groupMasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public groupMasterServlet() {
        super();
        // TODO Auto-generated constructor stub
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
	    
	    JSONArray jsonArr = new JSONArray();
	    JSONObject jsonObj = new JSONObject();
	    PrintWriter out = response.getWriter();
	    String groupId = request.getParameter("groupId");
	    CalgatherDAO calgatherDAO = CalgatherDAO.getInstance();
	    
	    if(session.getAttribute("loginUserId").equals(calgatherDAO.getGroupMaster(Integer.parseInt(groupId)))){
	    	jsonObj.put("groupMaster", "yes");
		    jsonArr.add(jsonObj);
	    	
	    } else{
	    	jsonObj.put("groupMaster", "no");
		    jsonArr.add(jsonObj);
	    	
	    }
	    
	    out.print(jsonArr);
	    
	    
	    
	}

}
