package com.admin.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.twat.dto.MemberVO;

	

public class AdminDAO {
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;
	
	
	// MemberDAO 의 싱글톤 -----------------------------------
	private static AdminDAO instance = new AdminDAO();
	
	private AdminDAO(){}
	
	public static AdminDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	   // DB연결을 위해 con을 반환하는 메서드 --------------------------------------------
	   public Connection getConnection() throws Exception {
	         Context initCtx = new InitialContext();
	         DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	      return ds.getConnection();
	   }

	
	   


		// 관리자 로그인을 위한 메서드 ----------------------------------쫑길빵길
		public String adminlogin(String MEMBER_ID, String MEMBER_PW) {
			String result = "";
			
			String selectSql = "select * from ADMIN where ADMIN_ID=? and ADMIN_PW=?";
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				psmt.setString(1, MEMBER_ID);
				psmt.setString(2, MEMBER_PW);
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					// 로그인 성공
					result = rs.getString("ADMIN_GRADE");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
					try {
						if(rs != null)
						if(psmt != null) psmt.close();
						if(con != null) con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
			}
			return result;
		}
	   
		
	   // 회원 가입을 위한 메서드 ------승우----------------------------
	   public int signUpMember(String MEMBER_ID, String MEMBER_PW, String MEMBER_NAME, String MEMBER_PHONE, String MEMBER_IMG, String MEMBER_GENDER, String MEMBER_BIRTH ) {
	      
	      int result = -1;
	      
	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,?)";
	      
	      try {
	         con = getConnection();
	         psmt = con.prepareStatement(insertSql);
	      
	         psmt.setString(1, MEMBER_ID);
	         psmt.setString(2, MEMBER_PW);
	         psmt.setString(3, MEMBER_NAME);
	         psmt.setString(4, MEMBER_PHONE);
	         psmt.setString(5, MEMBER_IMG);
	         psmt.setString(6, MEMBER_GENDER);
	         psmt.setString(7, MEMBER_BIRTH);
	         psmt.setString(8, null);
	         psmt.setString(9, null);
	         psmt.setTimestamp(10, null);
	         
	         result = psmt.executeUpdate();
	      

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if(rs != null)   rs.close();
	            if(psmt != null) psmt.close();
	            if(con != null) con.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	   }
	      return result;      
	   }
	   
	   
	 //회원정보를 가져오고싶어함------------------------------------------------
	   public ArrayList<MemberVO> myInfo(String MEMBER_ID){
	      String sql = "select MEMBER_IMG, MEMBER_NAME, MEMBER_PHONE, MEMBER_BIRTH from MEMBER where MEMBER_ID = ? ";
	      
	      ArrayList<MemberVO> arList = new ArrayList<MemberVO>(); 
	      
	      try {
	         con = getConnection();
	          psmt = con.prepareStatement(sql);
	            psmt.setString(1, MEMBER_ID);
	            rs = psmt.executeQuery();
	            
	            //이렇게하면은 rs.getstring(1)에 이미지 2에 이름 3에 전화번호 4에 생일이 들어온다.
	            while(rs.next()){
	               MemberVO mvo = new MemberVO();
	               mvo.setMEMBER_IMG(rs.getString("MEMBER_IMG"));
	               mvo.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
	               mvo.setMEMBER_PHONE(rs.getString("MEMBER_PHONE"));
	               mvo.setMEMBER_BIRTH(rs.getString("MEMBER_BIRTH"));
	                 arList.add(mvo);
	            }
	            //arList에 이미지 이름 폰번호 생일이 나온다.
	         
	      } catch (Exception e) {
	      }finally {
	            try {
	                  if(rs != null)
	                  if(psmt != null) psmt.close();
	                  if(con != null) con.close();
	               } catch (SQLException e) {
	               }
	         }
	      return arList;
	   }
	   
	   

	
	
}