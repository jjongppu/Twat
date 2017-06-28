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
		PreparedStatement psmt= null;
		ResultSet rs= null;
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
		PreparedStatement psmt= null;
		ResultSet rs= null;
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
	
	
	// 건의사항 제목으로 찾기 ----------------승우---------------------------- 쫑길수정
	public ArrayList<QnaVO> searchQnA(int page, String val, int kind) {
		PreparedStatement psmt= null;
		ResultSet rs= null;
		ArrayList<QnaVO> qnaArry = new ArrayList<QnaVO>();
		
		
		
		// 그릅수 얻어옴
		String selectQnaCount = "SELECT COUNT(*) FROM QNA ";
		if(kind ==1){
			selectQnaCount += " WHERE QNA_TITLE LIKE ?";
		}else if(kind ==2){
			selectQnaCount += " WHERE QNA_CONTENTS LIKE ?";
		}else if(kind ==3){
			selectQnaCount +=" WHERE MEMBER_ID LIKE ?";
		}else{
			
		}
		selectQnaCount+= "  ORDER BY QNA_DATE DESC";
		
		//실질적인 내용
		//제목순
		String selectKindQnaSql = "SELECT * FROM QNA";
		
		if(kind ==1){
			selectKindQnaSql += " WHERE QNA_TITLE LIKE ?";
		}else if(kind ==2){
			selectKindQnaSql += " WHERE QNA_CONTENTS LIKE ?";
		}else if(kind ==3){
			selectKindQnaSql +=" WHERE MEMBER_ID LIKE ?";
		}else{
			
		}
		selectKindQnaSql += " ORDER BY QNA_DATE DESC LIMIT "+ (page*10-10) +",10";
		
		try{
			con = getConnection();
			
			psmt = con.prepareStatement(selectQnaCount);
			if(kind != 4) {
				psmt.setString(1, "%"+val+"%");
			}
			rs = psmt.executeQuery();
			if(rs.next()) {
				int count = rs.getInt("COUNT(*)");
				QnaVO qv = new QnaVO();
				qv.setQNA_ID(count);
				qnaArry.add(qv);
			}
			
			psmt = con.prepareStatement(selectKindQnaSql);
			if(kind != 4) {
				psmt.setString(1,"%"+val+"%");
			}
			rs = psmt.executeQuery();
				
			while(rs.next()){
				QnaVO qv = new QnaVO();
				qv.setQNA_ID(rs.getInt("QNA_ID"));
				qv.setMEMBER_ID(rs.getString("MEMBER_ID"));
				qv.setQNA_CATEGORY(rs.getString("QNA_CATEGORY"));
				qv.setQNA_PW(rs.getInt("QNA_PW"));
				qv.setQNA_TITLE(rs.getString("QNA_TITLE"));
				qv.setQNA_CONTENTS(rs.getString("QNA_CONTENTS"));
				qv.setQNA_DATE(rs.getTimestamp("QNA_DATE"));
				qv.setQNA_REPLY(rs.getString("QNA_REPLY"));
				qnaArry.add(qv);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)rs.close();
				if(psmt != null)psmt.close();
				if(con != null)con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
        return qnaArry;
	} 
	
	
	
	// 문의사항 리스트 불러오기 -----------최승우-----------------------
	public ArrayList<QnaVO> getList(int searchCategory, int page, String val) {
		PreparedStatement psmt= null;
		ResultSet rs= null;
		
		ArrayList<QnaVO> arList = new ArrayList<QnaVO>();
		QnaVO qnaVo = new QnaVO();
		// searchCategry = 문의사항 종류(카테고리)
		// val = 검색어
		
		String countSql = "SELECT COUNT(*) FROM QNA";
		if(!val.equals("0")) {
			countSql += "WHERE QNA_TITLE LIKE ?";
		}
		countSql += "ORDER BY QNA_ID asc";
		
		String AllSql = "SELECT * FROM QNA";
		if(!val.equals("0")) {
			AllSql += "WHERE QNA_TITLE LIKE ?";
		}
		AllSql += "ORDER BY QNA_ID asc LIMIT " + (page*10-10) + " ,10";
		
		// 1 = 제목 / 제목으로 검색하기
		if(searchCategory == 1) {
			try {
				con = getConnection();
				
				psmt = con.prepareStatement(countSql);
				if(!val.equals("0")) {
					psmt.setString(1, "%"+val+"%");
				}
				rs = psmt.executeQuery();
				if(rs.next()) {
					int count = rs.getInt("COUNT(*)");
					QnaVO qv = new QnaVO();
					qv.setQNA_ID(count);
					arList.add(qv);
				}
				
				psmt = con.prepareStatement(AllSql);
				if(!val.equals("0")) {
					psmt.setString(1, "%" + val + "%");
				}
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					QnaVO qv = new QnaVO();
					qv.setQNA_ID(rs.getInt("QNA_ID"));
					qv.setQNA_CATEGORY(rs.getString("QNA_CATEGORY"));
					qv.setQNA_TITLE(rs.getString("QNA_TITLE"));
					qv.setMEMBER_ID(rs.getString("MEMBER_ID"));
					qv.setQNA_DATE(rs.getTimestamp("QNA_DATE"));
					// 조회수 어떻게 처리할지 생각해야되
					
					arList.add(qv);
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
		
		// 2 = 글 내용 / 글 내용으로 검색하기
		} else if(searchCategory == 2) {
			
			
			
			
		}
		
		
		
		
		return arList;
	}
	
	
	public ArrayList<QnaVO> detailQnA(int val) {
		PreparedStatement psmt= null;
		ResultSet rs= null;
		
		ArrayList<QnaVO> arList = new ArrayList<QnaVO>(); 
		
		int result = -1;
		
		String selectSql = "select * from qna where qna_id=?";
		
		try {
			con = getConnection();
			psmt = con.prepareStatement(selectSql);
			psmt.setInt(1, val);
			
			rs = psmt.executeQuery();
			while(rs.next()) {
				QnaVO qvo = new QnaVO();
				qvo.setMEMBER_ID(rs.getString("MEMBER_ID"));
				qvo.setQNA_CATEGORY(rs.getString("QNA_CATEGORY"));
				qvo.setQNA_PW(rs.getInt("QNA_PW"));
				qvo.setQNA_TITLE(rs.getString("QNA_TITLE"));
				qvo.setQNA_CONTENTS(rs.getString("QNA_CONTENTS"));
				qvo.setQNA_DATE(rs.getTimestamp("QNA_DATE"));
				qvo.setQNA_REPLY(rs.getString("QNA_REPLY"));
				
				arList.add(qvo);
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
             }
       }
		return arList;
		
	}
	
}
