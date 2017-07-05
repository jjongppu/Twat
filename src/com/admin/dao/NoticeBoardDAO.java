package com.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.admin.dto.NoticeBoardVO;

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
	
	
	public ArrayList<NoticeBoardVO> getBoardInfo(String noticeEvent){
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<NoticeBoardVO> noticeArr = new ArrayList<NoticeBoardVO>();
				
		try {
			con = getConnection();
			String sql = "SELECT * FROM `notice_board` WHERE NOTICE_CLASSIFICATION = ? ORDER BY NOTICE_ID DESC LIMIT 7";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, noticeEvent);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				noticeArr.add(new NoticeBoardVO(0, rs.getString("NOTICE_TITLE"), rs.getString("NOTICE_CONTENT"),
						rs.getString("NOTICE_WRITER"), rs.getString("NOTICE_DATE"), rs.getInt("NOTICE_VIEWS"), rs.getString("NOTICE_CLASSIFICATION")));
				
				
			}
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
				try {
					if(rs != null)
						rs.close();
					if(psmt != null)
						rs.close();
					if(con != null)
						con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		}
		
		return noticeArr;
		
		
	}
	
	
	
	
	
	
	
	

}
