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

import com.admin.dao.NoticeBoardDAO;
import com.admin.dto.NoticeBoardVO;

/**
 * Servlet implementation class HomeNoticeBoard
 */
@WebServlet("/HomeNoticeBoard.do")
public class HomeNoticeBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeNoticeBoard() {
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
		
	    String classification = request.getParameter("classification");
	    
	    JSONArray jsonArr = new JSONArray();
	    JSONObject jsonObj = null;
	    
	    PrintWriter out = response.getWriter();
		
	    NoticeBoardDAO noticeBoardDAO =  NoticeBoardDAO.getInstance();
	    
	    ArrayList<NoticeBoardVO> noticeArr = noticeBoardDAO.getBoardInfo(classification);
	    
	    for(int i = 0; i < noticeArr.size(); i++){
	    	jsonObj = new JSONObject();
	    	jsonObj.put("noticeId", noticeArr.get(i).getNotice_id());
	    	jsonObj.put("noticeKind", noticeArr.get(i).getNotice_classification());
	    	jsonObj.put("noticeTitle", noticeArr.get(i).getNotice_title());
	    	jsonObj.put("noticeContent", noticeArr.get(i).getNotice_content());
	    	jsonObj.put("noticeWriter", noticeArr.get(i).getNotice_writer());
	    	jsonObj.put("noticeDate", noticeArr.get(i).getNotice_date().split(" ")[0]);
	    	jsonObj.put("noticeViews", noticeArr.get(i).getNotice_views());
	    	jsonArr.add(jsonObj);
	    }
	    
	    out.println(jsonArr);
	    
		
	}

}
