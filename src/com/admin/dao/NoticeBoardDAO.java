package com.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class NoticeBoardDAO {
	Connection con = null;

	
	
	// MemberDAO 의 싱글톤 -----------------------------------
	private static NoticeBoardDAO instance = new NoticeBoardDAO();
	
	private NoticeBoardDAO(){}
	
	public static NoticeBoardDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	// DB연결을 위해 con을 반환하는 메서드 --------------------------------------------
	public Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
	    DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	    return ds.getConnection();
	}
	
	
	public int inputNotice(String adminId, String title, String content, String classification){
		PreparedStatement psmt = null;
		int result = 0;
		try {
			con = getConnection();
			String sql = "INSERT INTO notice_board VALUES(?, ?, ?, ?, CURRENT_TIMESTAMP, 0, ?)";
			psmt = con.prepareStatement(sql);
			System.out.println(System.currentTimeMillis());
			
			psmt.setString(1, String.valueOf(System.currentTimeMillis()));
			psmt.setString(2, title);
			psmt.setString(3, content);
			psmt.setString(4, adminId);
			psmt.setString(5, classification);
			
			result = psmt.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
				try {
					if(psmt != null)
						psmt.close();
					if(con != null)
						con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		
		return result;
		
	}
	
	
	
	
	
	
	
	

}
