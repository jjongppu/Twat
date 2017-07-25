package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Timestamp;
import java.util.ArrayList;

//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;

import com.twat.dbpool.DBPool;
import com.twat.dto.QnaVO;
import com.twat.mvconnection.MVConnection;

public class QnaDAO {
//	Connection con = null;
	
	
	
	private static QnaDAO instance = new QnaDAO();
		
	private QnaDAO(){}
		
	public static QnaDAO getInstance(){
		return instance;
	}
		
	
	
//	public Connection getConnection() throws Exception {
//		Context initCtx = new InitialContext();
//	    DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
//	         
//	    return ds.getConnection();
//	}
//	
	
	
	public int getMaxNum() {
		Connection con = null;
		PreparedStatement psmt= null;
		ResultSet rs= null;
//		int result = -1;
		int cal_num = 0;
		
		String selectSql = "SELECT * FROM `QNA` ORDER BY QNA_ID DESC LIMIT 1";
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
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
	       
		}    
		 return cal_num + 1;
	}
	
	
	
	public int insertQnA(String qnaId, String qnaCategory, int qnaPW, String qnaTitle, String qnaContents) {
//		PreparedStatement psmt= null;
		ResultSet rs= null;
		int result = -1;
//		int qna_number = 0;
		PreparedStatement psmt2= null;
		Connection con = null;
		String insertSql = "INSERT INTO QNA VALUES(?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
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
		        if(psmt2 != null) psmt2.close();
		        if(con != null) con.close();
	        } catch (SQLException e) {
	        	// TODO Auto-generated catch block
		        e.printStackTrace();
	        }
//	        System.out.println(result);
	      
		}    
		  return result;
	}
	
	
	
	public ArrayList<QnaVO> searchQnA(int page, String val, int kind) {
		PreparedStatement psmt= null;
		ResultSet rs= null;
		ArrayList<QnaVO> qnaArry = new ArrayList<QnaVO>();
		Connection con = null;
		
		
		
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
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			
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
			
			if(rs != null)
				rs.close();
			
			if(psmt != null)
				psmt.close();
			
			
			
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
	
	
	

	public ArrayList<QnaVO> getList(int searchCategory, int page, String val) {
		PreparedStatement psmt= null;
		ResultSet rs= null;
		Connection con = null;
		ArrayList<QnaVO> arList = new ArrayList<QnaVO>();
//		QnaVO qnaVo = new QnaVO();
		
		
		
		String countSql = "SELECT COUNT(*) FROM QNA";
		if(!val.equals("0")) {
			countSql += "WHERE QNA_TITLE LIKE ?";
		}
		countSql += "ORDER BY QNA_ID asc";
		
		String AllSql = "SELECT * FROM QNA";
		if(!val.equals("0")) {
			AllSql += "WHERE QNA_TITLE LIKE ?";
		}
		AllSql += "ORDER BY QNA_ID ASC LIMIT " + (page*10-10) + " ,10";
		
		
		if(searchCategory == 1) {
			try {
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
				
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
				if(rs != null)
					rs.close();
				
				if(psmt!=null)
					psmt.close();
				
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
		        
			}
		
		} else if(searchCategory == 2) {
			
			
			
			
		}
		
		
		
		
		
		
		return arList;
	}
	
	
	public ArrayList<QnaVO> detailQnA(int val) {
		PreparedStatement psmt= null;
		ResultSet rs= null;
		Connection con = null;
		ArrayList<QnaVO> arList = new ArrayList<QnaVO>(); 
		
//		int result = -1;
		
		String selectSql = "SELECT * FROM QNA WHERE QNA_ID = ?";
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
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
	
	public int deleteQna(String userId, int val, int qnaPw) {
		PreparedStatement psmt= null;
		ResultSet rs= null;
		int result = -1;
		Connection con = null;
		String deleteSql = "DELETE FROM QNA where MEMBER_ID=? and QNA_ID=? and QNA_PW=?";
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(deleteSql);
			psmt.setString(1, userId);
			psmt.setInt(2, val);
			psmt.setInt(3, qnaPw);
			
			result = psmt.executeUpdate();
			System.out.println(result);
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
	        
		}    
		return result;
	}
	
	
	public int updateQna(String userId, int val, String cate, int pw, String qnaTitle, String qnaCont) {
		PreparedStatement psmt= null;
		ResultSet rs= null;
		int result = -1;
		Connection con = null;
		String updateSql = "UPDATE QNA SET QNA_CATEGORY=?, QNA_PW=?, QNA_TITLE=?, QNA_CONTENTS=? WHERE MEMBER_ID=? AND QNA_ID=?";
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(updateSql);
			psmt.setString(1, cate);
			psmt.setInt(2, pw);
			psmt.setString(3, qnaTitle);
			psmt.setString(4, qnaCont);
			psmt.setString(5, userId);
			psmt.setInt(6, val);
			
			result = psmt.executeUpdate();
			System.out.println(result);
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
	        
		}    
		return result;
	}

	
}
