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
	
	
	// MemberDAO �� �̱��� -----------------------------------
	private static AdminDAO instance = new AdminDAO();
	
	private AdminDAO(){}
	
	public static AdminDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	   // DB������ ���� con�� ��ȯ�ϴ� �޼��� --------------------------------------------
	   public Connection getConnection() throws Exception {
	         Context initCtx = new InitialContext();
	         DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	      return ds.getConnection();
	   }

	
	   


		// ������ �α����� ���� �޼��� ----------------------------------�б滧��
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
					// �α��� ����
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
	   
		
		// �б滧���� ���� Ȩ db���� �ѷ��ֱ�
		public ArrayList<Integer> getAllInfo(){
			ArrayList<Integer> info = new ArrayList<Integer>();
			String selectGetInfo = "SELECT"; 
			selectGetInfo +="(SELECT COUNT(*) FROM member) as memC,";
			selectGetInfo +="(SELECT COUNT(*) FROM qna) as qnaC,";
			selectGetInfo +="(SELECT COUNT(*) FROM calendar where CAL_DEPTH=0) as calC,";
			selectGetInfo +="(SELECT VISIT_COUNT FROM visit where VISIT_KIND='TOTAL') as visC";						
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectGetInfo);
				rs = psmt.executeQuery();
				if(rs.next()) {
					info.add(rs.getInt("memC"));
					info.add(rs.getInt("qnaC"));
					info.add(rs.getInt("calC"));
					info.add(rs.getInt("visC"));
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
			return info;
		}
	   
	
}