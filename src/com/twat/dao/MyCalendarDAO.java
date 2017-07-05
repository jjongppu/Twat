package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import com.twat.dto.CalendarVO;
import com.twat.dto.CalgatherVO;
import com.twat.dto.MyCalendarVO;

public class MyCalendarDAO{
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;
	Statement stmt;
	
	private static MyCalendarDAO instance = new MyCalendarDAO();
	
	private MyCalendarDAO() {	}
	
	public static MyCalendarDAO getInstance(){
		return instance;
	}
	
	public Connection getConnection() throws Exception{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");   
			      
		return ds.getConnection();
	}

	public void addMySchedule( String member_id, String my_cal_contents, String my_cal_date, String my_cal_time ) {
		PreparedStatement psmt2= null;
		String sql ="";
		try {
			con = getConnection();			
			sql = "INSERT INTO MY_CALENDAR VALUES(?, ?, CURRENT_TIMESTAMP, ?, ?, ?)";
			psmt2 = con.prepareStatement(sql);			
			psmt2.setInt(1, getMaxNum());
			System.out.println(getMaxNum());
			psmt2.setString(2, member_id);
			psmt2.setString(3, my_cal_contents);
			psmt2.setString(4, my_cal_date);
			psmt2.setString(5,  my_cal_time);
			
			psmt2.executeUpdate();
//			psmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(psmt2 != null)
					psmt2.close();
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void deleteMySchedule(int deleteSche){
		PreparedStatement psmt3= null;
		String sql ="";
		try {
			con = getConnection();			
			sql = "DELETE FROM MY_CALENDAR WHERE MY_CAL_INDEX = "+deleteSche;
			psmt3 = con.prepareStatement(sql);			
//			psmt3.setInt(1, deleteSche);
			
			psmt3.executeUpdate();
//			psmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(psmt3 != null)
					psmt3.close();
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// �뜑蹂닿린 踰꾪듉 援ы쁽 �븯�젮�떎 �떎�뙣... 
//	int i = -10;
//	public ArrayList<MyCalendarVO> getInfo(String MEMBER_ID){
//		ArrayList<MyCalendarVO> myCalList = new ArrayList<MyCalendarVO>();
//		int j = i+= 10;
//		
//		if(j == -10 && myCalList.size() == 10){
//			j += 10;
//		}  else if(myCalList.size()<10){
//			j += 0;
//		}
//		PreparedStatement pstmt3 = null;
//		ResultSet rs3 = null;
//		String sql = "SELECT * FROM MY_CALENDAR WHERE MEMBER_ID = ? LIMIT "+j+", 10";
//		try{
//			con = getConnection();
//			pstmt3 = con.prepareCall(sql);
//			pstmt3.setString(1, MEMBER_ID);
//			rs3 = pstmt3.executeQuery();
//			
//			while(rs3.next()){
//				myCalList.add(new MyCalendarVO(rs3.getInt(1), rs3.getString(2), rs3.getTimestamp(3), rs3.getString(4), rs3.getString(5), rs3.getString(6)));
//				
//			}
//			
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		} finally {
//			try{
//				if(rs3 != null)rs3.close();
//				if(pstmt3 != null)pstmt3.close();
//				if(con != null)con.close();
//			}	catch (SQLException e){
//				e.printStackTrace();
//			}
//		}
//		System.out.println(myCalList.size()+"由ъ뒪�듃 �궗�씠利�");
//		System.out.println(j +"J");
//		System.out.println(myCalList.size()+"由ъ뒪�듃 �궗�씠利�");
//		System.out.println(j +"J");
//		System.out.println(myCalList.size()+"由ъ뒪�듃 �궗�씠利�");
//		System.out.println(j +"J");
//		System.out.println(myCalList.size()+"由ъ뒪�듃 �궗�씠利�");
//		System.out.println(j +"J");
//		return myCalList;
//	}
	
	public ArrayList<MyCalendarVO> getInfo(String MEMBER_ID){
		ArrayList<MyCalendarVO> myCalList = new ArrayList<MyCalendarVO>();
		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;
		String sql = "SELECT * FROM MY_CALENDAR WHERE MEMBER_ID = ?";
		try{
			con = getConnection();
			pstmt3 = con.prepareCall(sql);
			pstmt3.setString(1, MEMBER_ID);
			rs3 = pstmt3.executeQuery();
			
			while(rs3.next()){
				myCalList.add(new MyCalendarVO(rs3.getInt(1), rs3.getString(2), rs3.getTimestamp(3), rs3.getString(4), rs3.getString(5), rs3.getString(6)));
				
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
		} finally {
			try{
				if(rs3 != null)rs3.close();
				if(pstmt3 != null)pstmt3.close();
				if(con != null)con.close();
			}	catch (SQLException e){
				e.printStackTrace();
			}
		}
		return myCalList;
	}
	
	
	
	
	public int getMaxNum() {
		int result = -1;
		int cal_num = 0;
		
		String selectSql = "SELECT * FROM `MY_CALENDAR` ORDER BY MY_CAL_INDEX DESC LIMIT 1";
		
		try {
			con = getConnection();
			psmt = con.prepareStatement(selectSql);
			
			rs = psmt.executeQuery();
			while(rs.next()){
				cal_num = rs.getInt("MY_CAL_INDEX");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        try {
	        	if(rs != null) rs.close();
		        if(psmt != null) psmt.close();
		        if(con != null) con.close();
	        } catch (SQLException e) {
		        e.printStackTrace();
	        }
	        return cal_num + 1;
		}    
		
	}

	
}
