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

public class CalendarDAO
{
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;
	Statement stmt;

	
	// CalendarDAO 의 싱글톤 ================================================================================================
	private static CalendarDAO instance = new CalendarDAO();
	
	private CalendarDAO() {	}
	
	public static CalendarDAO getInstance()
	{
		return instance;
	}
	
	// DB연결을 위해 con을 반환하는 메서드 ==================================================================================
	public Connection getConnection() throws Exception
	{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");   
			      
		return ds.getConnection();
	}

	// 각종 일정 받아오기 ===================================================================================================
	public ArrayList<CalendarVO> getInfo(String groupId)
	{
		ArrayList<CalendarVO> arrList = new ArrayList<CalendarVO>();
		
		try
		{
			con = getConnection();
			String sql = "select * from CALENDAR where CAL_DEPTH=0 and GROUP_ID=?";
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(groupId));
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				CalendarVO schedule = new CalendarVO();
				schedule.setCal_num(rs.getInt(1));
				schedule.setCal_date(rs.getString(3));
				schedule.setCal_group(rs.getInt(4));
				schedule.setCal_memo(rs.getString(5));
				schedule.setCal_writer(rs.getString(6));
				schedule.setStat_icon(rs.getString(7));
				schedule.setMember_choice(rs.getString(8));
				schedule.setCal_reference(rs.getInt(9));
				schedule.setCal_depth(rs.getInt(10));
				
				arrList.add(schedule);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null)rs.close();
				if(psmt != null)psmt.close();
				if(con != null)con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return arrList;
	}
	
	// 각종일정 받아오기(그룹 아이디, 일정 아이디)
	public ArrayList<CalendarVO> getInfo(String groupId, String calId)
	{
		ArrayList<CalendarVO> arrList = new ArrayList<CalendarVO>();
		
		try
		{
			con = getConnection();
			String sql = "select * from CALENDAR where CAL_DEPTH=0 and GROUP_ID=? and CAL_NUM=?";
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(groupId));
			psmt.setInt(2, Integer.parseInt(calId));
			
			rs = psmt.executeQuery();
				
			while(rs.next())
			{
				CalendarVO schedule = new CalendarVO();
				schedule.setCal_num(rs.getInt(1));
				schedule.setCal_date(rs.getString(3));
				schedule.setCal_group(rs.getInt(4));
				schedule.setCal_memo(rs.getString(5));
				schedule.setCal_writer(rs.getString(6));
				schedule.setStat_icon(rs.getString(7));
				schedule.setMember_choice(rs.getString(8));
				schedule.setCal_reference(rs.getInt(9));
				schedule.setCal_depth(rs.getInt(10));
				
				arrList.add(schedule);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs != null)rs.close();
				if(psmt != null)psmt.close();
				if(con != null)con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return arrList;
	}
	
	public int getLastCalNum(){
		String sql = "";
		int cal_num = 0;
		
		try {
			con = getConnection();
			sql = "SELECT * FROM calendar order by CAL_NUM desc limit 1";
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()){
				cal_num = rs.getInt("CAL_NUM"); 
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				if(rs != null)
					rs.close();
				if(psmt != null)
					psmt.close();
				if(con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return cal_num + 1; 
		
		
		
	}
	
	public void addGroupCal(int cal_num, String cal_date, int group_id, String cal_memo, String cal_writer ) {
		String sql = "";		
		
		
		try {
			con = getConnection();			
			sql = "insert into calendar VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			

			
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, cal_num);			
			psmt.setString(2, cal_date);
			psmt.setInt(3, group_id);
			psmt.setString(4, cal_memo);
			psmt.setString(5, cal_writer);
			psmt.setString(6, "");
			psmt.setString(7, "");
			psmt.setInt(8, cal_num);
			psmt.setInt(9, 0);
			
			psmt.executeUpdate();
//			psmt.executeQuery();
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		
				try {
					if(psmt != null)
					psmt.close();
					else if(con != null)
						con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		}
		
		
		
		
	}
}
