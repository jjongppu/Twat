package com.twat.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.twat.dao.MemberDAO;

/**
 * Servlet implementation class imageFileUpload
 */
//@WebServlet("/imageFileUpload.do")
public class imageFileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public imageFileUpload() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setCharacterEncoding("UTF-8");
//	    response.setContentType("application/json;");
//	    response.setHeader("Cache-Control", "no-cache");
//	    request.setCharacterEncoding("UTF-8");
//		
//		
		
		HttpSession session = request.getSession();	
		String userId = (String) session.getAttribute("loginUserId");
		String root = request.getSession().getServletContext().getRealPath("/");
        String pathname = root + "//img//member";
        //C:\Twat\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\Twat\main1.png
        File f = new File(pathname);
        if (!f.exists()) {
            // 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙
            f.mkdirs();
        }

        String encType = "UTF-8";
        int maxFilesize = 5 * 1024 * 1024;

        // MultipartRequest(request, 占쏙옙占쏙옙占쏙옙[, 占쌍댐옙占쏙옙占신⑼옙占�, 占쏙옙占쌘듸옙占심몌옙占싶쇽옙, 占쏙옙占쏙옙占쏙옙 占쏙옙占싹몌옙 占쏙옙호 占쏙옙占쏙옙])
        MultipartRequest mr = new MultipartRequest(request, pathname, maxFilesize,
                encType, new DefaultFileRenamePolicy());        

        File file = mr.getFile("attachFile");
        
        Enumeration<String> formNames=mr.getFileNames();
        
        String formName=(String)formNames.nextElement(); 

        
        String fileName = mr.getFilesystemName(formName);
        if(fileName == null) {   // 占쏙옙占쏙옙占쏙옙 占쏙옙占싸듸옙 占쏙옙占쏙옙 占십억옙占쏙옙占쏙옙
        	System.out.println("占쏙옙占쏙옙 占쏙옙占싸듸옙 占쏙옙占쏙옙 占십억옙占쏙옙");
        	} else {  // 占쏙옙占쏙옙占쏙옙 占쏙옙占싸듸옙 占실억옙占쏙옙占쏙옙
        	fileName=new String(fileName.getBytes("8859_1"),"UTF-8"); // 占싼깍옙占쏙옙占쌘듸옙 - 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占�        	
        	System.out.println("Form Name : " + formName + "<BR>");
        	System.out.println("File Name  : " + fileName);
        	} // end 

        MemberDAO memberDao = MemberDAO.getInstance();
        memberDao.changeImg(userId, fileName);
        
        System.out.println(file); // 첨占싸듸옙 占쏙옙占쏙옙占쏙옙 占쏙옙체占쏙옙占�
        response.sendRedirect("myInfo.html");
//        
//		PrintWriter writer = response.getWriter();
//	    JSONArray jsonList = new JSONArray();
//		JSONObject jsonOb = new JSONObject();
//		
//			jsonOb.put("result", root.toString());
//		
//		jsonList.add(jsonOb);
//	    writer.println(jsonList);
//	    writer.close();

        

	}

}
