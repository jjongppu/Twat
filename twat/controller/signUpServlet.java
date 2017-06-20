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
@WebServlet("/signUp.do")
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
	    
//	    // 사진 업로드 해결x----------------------------------------------------------
//	    String savePath = "img/member";
//	    int uploadFileSize = 5 * 1024 * 1024; // 파일의 크기를 5MB로 제한 (1024 = 2^10)
////	    String encType = "UTF-8";
//	    
//	    ServletContext context = getServletContext(); // 서블릿 컨텍스트 객체 생성
//	    String uploadFilePath = context.getContextPath(); // 서블릿 서버에 실제 저장경로를 얻어옴
//	    System.out.println("서버상의 저장경로 : " + uploadFilePath);
//	    
//	    MultipartRequest multi = new MultipartRequest(
//				request
//				, uploadFilePath
//				, uploadFileSize
//				, new DefaultFileRenamePolicy());
//	    
//	    // 업로드 된 파일 이름 얻기
//	 	String fileName = multi.getFilesystemName("uploadFile");
//	    // ------예제 붙여넣기 맞는지 안맞는지 몰라연----------------------------------------------------------------------
//	    String signUpImgName = request.getParameter("signUpImg");
//	    String signUpImg = savePath + fileName;
	    String signUpName = request.getParameter("signUpName");
	    String signUpGender = request.getParameter("signUpGender");
	    
	    String signUpBirthYear = request.getParameter("signUpBirthYear");
	    String signUpBirthMonth = request.getParameter("signUpBirthMonth");
	    String signUpBirthDay = request.getParameter("signUpBirthDay");
	    String signUpBirth = signUpBirthYear.substring(2) + signUpBirthMonth + signUpBirthDay;
	    
	    String signUpPhone = request.getParameter("signUpPhone");
	    int signUpOutTime = 0;
<<<<<<< HEAD
	    
	    MemberDAO memdao = MemberDAO.getInstance();
	    int result = memdao.signUpMember(signUpId, signUpPw, signUpName, signUpPhone,  signUpGender, signUpBirth, signUpOutTime);
=======
	    String signUpQuestion = request.getParameter("signUpQuestion");
	    String signUpAnswer = request.getParameter("signUpAnswer");
	    
	    MemberDAO memdao = MemberDAO.getInstance();
	    int result = memdao.signUpMember(signUpId, signUpPw, signUpName, signUpPhone,  signUpGender, 
	    		signUpBirth, signUpOutTime, signUpQuestion, signUpAnswer);
>>>>>>> dea6cf49c5bbd913d17f55f99845b6b26b2a48c0
	    
	    PrintWriter writer = response.getWriter();
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
	    
	    if(result == 1) {
	    	jsonOb.put("result", "success");
	    	jsonOb.put("signUpOutTime", signUpOutTime);
	    } else {
	    	jsonOb.put("result", "fail");
	    	
	    	jsonOb.put("signUpId", signUpId);
	    	jsonOb.put("signUpPw", signUpPw);
//	    	jsonOb.put("signUpImg", signUpImg);
	    	jsonOb.put("signUpName", signUpName);
	    	jsonOb.put("signUpGender", signUpGender);
	    	jsonOb.put("signUpBirth", signUpBirth);
	    	jsonOb.put("signUpPhone", signUpPhone);
	    	jsonOb.put("signUpOutTime", signUpOutTime);
	    }
	    
	    jsonList.add(jsonOb);
	    writer.println(jsonList.toJSONString());
	}
}
