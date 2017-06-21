package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.twat.dto.QnaVO;

public class QnaDAO {
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;
	
	// MemberDAO �� �̱��� -----------------------------------
	private static QnaDAO instance = new QnaDAO();
		
	private QnaDAO(){}
		
	public static QnaDAO getInstance(){
		return instance;
	}
		
	
	// DB������ ���� con�� ��ȯ�ϴ� �޼��� --------------------------------------------
	public Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
	    DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	    return ds.getConnection();
	}
	
	
	// qna_number �ִ밪 �޾ƿ��� �޼��� -------------------- �ֽ¿�----------------------------
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
	
	
	// ���ǻ��� �۾��� --------------------------�ֽ¿�-------------------
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
	
	
	// ���ǻ��� �������� ã�� ----------------�¿�----------------------------
	public ArrayList<QnaVO> searchQnA(String searchCategory, String searchBox) {
		
		ArrayList<QnaVO> arList = new ArrayList<QnaVO>();
		QnaVO qnaVo = new QnaVO();
		// 1 = ���� / �������� �˻��ϱ�
		if(searchCategory == "1") {
			String selectSql = "select * from qna where QNA_TITLE = ?";
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				
				psmt.setString(1, searchBox);
				
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					// ���� ����
					qnaVo.setQNA_ID(rs.getInt(1));
//					System.out.println(rs.getInt(1));
					qnaVo.setMEMBER_ID(rs.getString(2));
					qnaVo.setQNA_CATEGORY(rs.getString(3));
					qnaVo.setQNA_PW(rs.getInt(4));
					qnaVo.setQNA_TITLE(rs.getString(5));
					qnaVo.setQNA_CONTENTS(rs.getString(6));
					qnaVo.setQNA_DATE(rs.getTimestamp(7));
					qnaVo.setQNA_REPLY(rs.getString(8));
					
					arList.add(qnaVo);
//					System.out.println(qnaVo);
//					System.out.println(arList);
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
		        return arList;
			}   
			
		// 2 = �� ���� / �� �������� �˻��ϱ�
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
		        return arList;
			} 
			
		// 3 = �� �ۼ��� / �� �ۼ��ڷ� �˻��ϱ�
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
		        return arList;
			} 
		}
	}
}
