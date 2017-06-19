package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.twat.dao.CalgatherDAO;

@WebServlet("/makeGourp.do")
public class MakeGroupProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MakeGroupProcess() {
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
	    PrintWriter out = response.getWriter();
	    JSONObject job = new JSONObject();
	    JSONArray jarr = new JSONArray();
	    HttpSession session = request.getSession();
	    ServletContext application = request.getServletContext();
	    
	    CalgatherDAO md = CalgatherDAO.getInstance();
	    String groupName = request.getParameter("make_group_title");
	    String[] friends = request.getParameterValues("frined_id");
	    
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Calendar c1 = Calendar.getInstance();
        String today = sdf.format(c1.getTime());

        String masterId = (String)session.getAttribute("loginUserId");
        int groupCount = (int)application.getAttribute("groupCount");
        
        //이미지 랜덤 생성..
        int rand = (int)(Math.random()*10+1);
        String GroupImg = "img/group/groupImg"+rand+".png";

        application.setAttribute("groupCount", groupCount+1);
//        
//	    Boolean result = md.makeGorup(groupName,friends,today,masterId,groupCount);
        int result = md.makeGorup(groupName,friends,today,masterId,GroupImg,groupCount);
//        String result = md.makeGorup(groupName,friends,today,masterId,groupCount);
        
        
		if(result == 7){
			job.put("result", "s");
			
		}else{
			job.put("result", result);
		}
	    
		jarr.add(job);
		out.println(jarr);
	    
	    
	}

}
