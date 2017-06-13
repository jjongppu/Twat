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

		PrintWriter writer = response.getWriter();
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json;");
	    response.setHeader("Cache-Control", "no-cache");
	    request.setCharacterEncoding("UTF-8");
	    
	    String signUpId = request.getParameter("signUpId");
	    String signUpPw = request.getParameter("signUpPw");
	    
	    // ���� ���ε� �ذ�x----------------------------------------------------------
	    String savePath = "img/member";
	    int uploadFileSize = 5 * 1024 * 1024; // ������ ũ�⸦ 5MB�� ���� (1024 = 2^10)
//	    String encType = "UTF-8";
	    
	    ServletContext context = getServletContext(); // ���� ���ؽ�Ʈ ��ü ����
	    String uploadFilePath = context.getContextPath(); // ���� ������ ���� �����θ� ����
	    System.out.println("�������� ������ : " + uploadFilePath);
	    
	    MultipartRequest multi = new MultipartRequest(
				request
				, uploadFilePath
				, uploadFileSize
				, new DefaultFileRenamePolicy());
	    
	    // ���ε� �� ���� �̸� ���
	 	String fileName = multi.getFilesystemName("uploadFile");
	    // ------���� �ٿ��ֱ� �´��� �ȸ´��� ����----------------------------------------------------------------------
	    String signUpImgName = request.getParameter("signUpImg");
	    String signUpImg = savePath + fileName;
	    String signUpName = request.getParameter("signUpName");
	    String signUpGender = request.getParameter("signUpGender");
	    String signUpBirth = request.getParameter("signUpBirth");
	    String signUpPhone = request.getParameter("signUpPhone");
	    
	    MemberDAO memdao = MemberDAO.getInstance();
	    int result = memdao.signUpMember(signUpId, signUpPw, signUpName, signUpPhone, signUpImg, signUpGender, signUpBirth);
	    
	    
		JSONArray jsonList = new JSONArray();
		JSONObject jsonOb = new JSONObject();
	    
	    if(result == 1) {
	    	jsonOb.put("result", "success");
	    } else {
	    	jsonOb.put("result", "fail");
	    	
	    	jsonOb.put("signUpId", signUpId);
	    	jsonOb.put("signUpPw", signUpPw);
	    	jsonOb.put("signUpImg", signUpImg);
	    	jsonOb.put("signUpName", signUpName);
	    	jsonOb.put("signUpGender", signUpGender);
	    	jsonOb.put("signUpBirth", signUpBirth);
	    	jsonOb.put("signUpPhone", signUpPhone);
	    }
	    
	    jsonList.add(jsonOb);
	    writer.println(jsonList.toJSONString());
	}
}
