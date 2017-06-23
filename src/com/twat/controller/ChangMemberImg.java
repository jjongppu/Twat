package com.twat.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;



@WebServlet("/changMemberImg.do")
public class ChangMemberImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ChangMemberImg() {
        super();
    }


    	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	}

    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		request.setCharacterEncoding("utf-8");
    		response.setContentType("text/html; charset=UTF-8");
    		PrintWriter out = response.getWriter();
    		
    		String savePath="img/member/"; // 파일이 저장될 경로명
    		int uploadFileSize = 20 * 1024 * 1024 ; // 파일의 크기를 5MB로 제한 (1024 = 2^10)
    		String encType = "UTF-8";
    		
    		ServletContext context = getServletContext(); // 서블릿 컨텍스 캨체 생성
    		String uploadFilePath = context.getRealPath(savePath); // 서블릿 서버에 실제 저장경로를 얻어옴
    		
    		
    		MultipartRequest multi = new MultipartRequest(request, uploadFilePath, uploadFileSize, encType , new DefaultFileRenamePolicy());
    		
    		Enumeration em = multi.getFileNames();
    		
    		
    		while(em.hasMoreElements()){
    			String fn = (String)em.nextElement();
    			System.out.println(fn);
    		}
    		
    		
    		
    		String fileName = multi.getFilesystemName("uploadFile");
    		
    		System.out.println(fileName);
    		
    		if(fileName == null){ // 파일이 업로드 되지 않았을 떄.
    			out.println("{\"test\":\"no\" }");
    		}else{
    			out.println("{\"test\":\"ok\" }");
    			
    			
    		}
    		
    		
    	}

}
