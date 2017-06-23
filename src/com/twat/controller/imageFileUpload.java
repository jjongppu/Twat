package com.twat.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.twat.dao.MemberDAO;

/**
 * Servlet implementation class imageFileUpload
 */
@WebServlet("/imageFileUpload.do")
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
		HttpSession session = request.getSession();	
		String userId = (String) session.getAttribute("loginUserId");
		String root = request.getSession().getServletContext().getRealPath("/");
        String pathname = root + "//..//..//..//..//..//..//Twat//WebContent//img//member";
        //C:\Twat\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\Twat\main1.png
        File f = new File(pathname);
        if (!f.exists()) {
            // 폴더가 존재하지 않으면 폴더 생성
            f.mkdirs();
        }

        String encType = "UTF-8";
        int maxFilesize = 5 * 1024 * 1024;

        // MultipartRequest(request, 저장경로[, 최대허용크기, 인코딩케릭터셋, 동일한 파일명 보호 여부])
        MultipartRequest mr = new MultipartRequest(request, pathname, maxFilesize,
                encType, new DefaultFileRenamePolicy());        

        File file = mr.getFile("attachFile");
        
        Enumeration<String> formNames=mr.getFileNames();
        
        String formName=(String)formNames.nextElement(); 

        
        String fileName = mr.getFilesystemName(formName);
        if(fileName == null) {   // 파일이 업로드 되지 않았을때
        	System.out.println("파일 업로드 되지 않았음");
        	} else {  // 파일이 업로드 되었을때
        	fileName=new String(fileName.getBytes("8859_1"),"UTF-8"); // 한글인코딩 - 브라우져에 출력        	
        	System.out.println("Form Name : " + formName + "<BR>");
        	System.out.println("File Name  : " + fileName);
        	} // end 

        MemberDAO memberDao = MemberDAO.getInstance();
        memberDao.changeImg(userId, fileName);
        
        System.out.println(file); // 첨부된 파일의 전체경로
        response.sendRedirect("myInfo.html");
        
        
        

        

	}

}
