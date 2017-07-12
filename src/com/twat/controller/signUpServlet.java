package com.twat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.twat.dao.MemberDAO;

/**
 * Servlet implementation class signUpServlet
 */
//@WebServlet("/signUp.do")
public class signUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    
	    String signUpId = request.getParameter("signUpId");
	    String signUpPw = request.getParameter("signUpPw");
	    
//	    // 占쏙옙占쏙옙 占쏙옙占싸듸옙 占쌔곤옙x----------------------------------------------------------
//	    String savePath = "img/member";
//	    int uploadFileSize = 5 * 1024 * 1024; // 占쏙옙占쏙옙占쏙옙 크占썩를 5MB占쏙옙 占쏙옙占쏙옙 (1024 = 2^10)
////	    String encType = "UTF-8";
//	    
//	    ServletContext context = getServletContext(); // 占쏙옙占쏙옙 占쏙옙占쌔쏙옙트 占쏙옙체 占쏙옙占쏙옙
//	    String uploadFilePath = context.getContextPath(); // 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占싸몌옙 占쏙옙占쏙옙
//	    System.out.println("占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 : " + uploadFilePath);
//	    
//	    MultipartRequest multi = new MultipartRequest(
//				request
//				, uploadFilePath
//				, uploadFileSize
//				, new DefaultFileRenamePolicy());
//	    
//	    // 占쏙옙占싸듸옙 占쏙옙 占쏙옙占쏙옙 占싱몌옙 占쏙옙占�
//	 	String fileName = multi.getFilesystemName("uploadFile");
//	    // ------占쏙옙占쏙옙 占쌕울옙占쌍깍옙 占승댐옙占쏙옙 占싫맞댐옙占쏙옙 占쏙옙占쏙옙----------------------------------------------------------------------
//	    String signUpImgName = request.getParameter("signUpImg");
//	    String signUpImg = savePath + fileName;
	    String signUpName = request.getParameter("signUpName");
	    String signUpGender = request.getParameter("signUpGender");
	    
	    String signUpBirthYear = request.getParameter("signUpBirthYear");
	    String signUpBirthMonth = request.getParameter("signUpBirthMonth");
	    String signUpBirthDay = request.getParameter("signUpBirthDay");
	    String signUpBirth = signUpBirthYear.substring(2) + signUpBirthMonth + signUpBirthDay;
	    
	    String signUpPhone = request.getParameter("signUpPhone");
	    long signUpOutTime = 0;

	    String signUpQuestion = request.getParameter("signUpQuestion");
	    String signUpAnswer = request.getParameter("signUpAnswer");
	    
	    
	    MemberDAO memdao = MemberDAO.getInstance();
	    int result = memdao.signUpMember(signUpId, signUpPw, signUpName, signUpPhone,  signUpGender, signUpBirth, signUpOutTime, signUpQuestion, signUpAnswer);
	    

	    
	    PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
	    
	    if(result == 1) {
	    	jsonOb.put("result", "success");
	    } else {
	    	jsonOb.put("result", "fail");
	    	

	    }
	    
	    jsonList.add(jsonOb);
	    writer.println(jsonList.toJSONString());
	}
}
