package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.twat.dto.QnaVO;

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
	
	
	// qna_number 최대값 받아오는 메서드 -------------------- 최승우----------------------------
	public int getMaxNum() {
		int result = -1;
		int cal_num = 0;
		
		String selectSql = "SELECT * FROM `qna` ORDER BY QNA_ID DESC LIMIT 1";
		
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
	
	
	// 건의사항 글쓰기 --------------------------최승우-------------------
	public int insertQnA(String qnaId, String qnaCategory, int qnaPW, String qnaTitle, String qnaContents) {
		int result = -1;
//		int qna_number = 0;
		PreparedStatement psmt2= null;
		
		String insertSql = "insert into qna values(?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
		
		try {
			con = getConnection();
			psmt2 = con.prepareStatement(insertSql);
			
			psmt2.setInt(1, getMaxNum());
			psmt2.setString(2, qnaId);
			psmt2.setString(3, qnaCategory);
			psmt2.setInt(4, qnaPW);
			psmt2.setString(5, qnaTitle);
			psmt2.setString(6, qnaContents);
			psmt2.setString(7, null);
			
			result = psmt2.executeUpdate();		
			
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
	
	
	// 건의사항 제목으로 찾기 ----------------승우----------------------------
	public int searchQnA(String searchCategory, String searchBox) {
		int result = -1;
		ArrayList<QnaVO> arList = new ArrayList<QnaVO>();
		QnaVO qnaVo = new QnaVO();
		// 1 = 제목 / 제목으로 검색하기
		if(searchCategory == "1") {
			String selectSql = "select * from qna where QNA_TITLE = ?";
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				
				psmt.setString(1, searchBox);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
<<<<<<< HEAD
//					arList.add(arg0)
=======
					// 여기 수정
					qnaVo.setQNA_ID(qNA_ID);
					qnaVo.setMEMBER_ID();
					qnaVo.setQNA_CATEGORY();
					qnaVo.getQNA_PW();
					qnaVo.getQNA_TITLE();
					qnaVo.getQNA_CONTENTS();
					qnaVo.getQNA_DATE();
					qnaVo.getQNA_REPLY();
					
					arList.add(qnaVo);
>>>>>>> 7db9bc71ee7897199cce0cf7d4084b74e2789449
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
//		        System.out.println(result);
		        return result;
			}   
			
		// 2 = 글 내용 / 글 내용으로 검색하기
		} else if(searchCategory == "2") {
			String selectSql = "select * from qna where QNA_CONTENTS = ?";
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				
				psmt.setString(1, searchBox);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					
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
//		        System.out.println(result);
		        return result;
			} 
			
		// 3 = 글 작성자 / 글 작성자로 검색하기
		} else {
			String selectSql = "select * from qna where MEMBER_ID = ?";
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				
				psmt.setString(1, searchBox);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					
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
//		        System.out.println(result);
		        return result;
			} 
		}
	}
}
