package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class QnaDAO {
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;
	
	// MemberDAO 의 싱글톤 -----------------------------------
	private static QnaDAO instance = new QnaDAO();
		
	private QnaDAO(){}
		
	public static QnaDAO getInstance(){
		return instance;
	}
		
	
	// DB연결을 위해 con을 반환하는 메서드 --------------------------------------------
	public Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
	    DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	    return ds.getConnection();
	}
	
	
	// 건의사항 글쓰기 --------------------------최승우-------------------
	public int insertQnA(String qnaId, String qnaCategory, int qnaPW, String qnaTitle, String qnaContents) {
		int result = -1;
//		int qna_number = 0;
		
		
		String insertSql = "insert into qna values(?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
		
		try {
			con = getConnection();
			psmt = con.prepareStatement(insertSql);
			
			psmt.setInt(1, getMaxNum());
			psmt.setString(2, qnaId);
			psmt.setString(3, qnaCategory);
			psmt.setInt(4, qnaPW);
			psmt.setString(5, qnaTitle);
			psmt.setString(6, qnaContents);
			psmt.setString(7, null);
			
			result = psmt.executeUpdate();			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        try {
	        	if(rs != null) rs.close();
		        if(psmt != null) psmt.close();
		        if(con != null) con.close();
	        } catch (SQLException e) {
	        	// TODO Auto-generated catch block
		        e.printStackTrace();
	        }
//	        System.out.println(result);
	        return result;
		}    
		
	}
	
	
	// qna_number 최대값 받아오는 메서드 -------------------- 최승우----------------------------
	public int getMaxNum() {
		int result = -1;
		int cal_num = 0;
		
		String selectSql = "select * from calendar order by QNA_ID desc limit 1";
		
		try {
			con = getConnection();
			psmt = con.prepareStatement(selectSql);
			
			rs = psmt.executeQuery();
			while(rs.next()){
				cal_num = rs.getInt("QNA_ID");
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        try {
	        	if(rs != null) rs.close();
		        if(psmt != null) psmt.close();
		        if(con != null) con.close();
	        } catch (SQLException e) {
	        	// TODO Auto-generated catch block
		        e.printStackTrace();
	        }
	        return cal_num + 1;
		}    
		
	}
}
