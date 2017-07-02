package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.twat.dto.CalgatherVO;
import com.twat.dto.MemberJoinGroupVO;

public class MemberJoinGroupDAO
{
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;

	
	// MemberJoinGroupDAO �� �̱��� -----------------------------------
	private static MemberJoinGroupDAO instance = new MemberJoinGroupDAO();
	
	private MemberJoinGroupDAO(){}
	
	public static MemberJoinGroupDAO getInstance()
	{
		return instance;
	}
	
	// DB������ ���� con�� ��ȯ�ϴ� �޼��� --------------------------------------------
	public Connection getConnection() throws Exception
	{
		Context initCtx = new InitialContext();
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
		return ds.getConnection();
	}
	
	// GROUP_ID�� ���� MEMBER_ID�� �޾ƿ��� �Լ�, MEMBER_ID�� ArrayList<String>���� ��ȯ
	public ArrayList<String> getMemberId(String GROUP_ID)
	{
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		ArrayList<String> arrList = new ArrayList<String>();
		String sql = "select MEMBER_ID from MEMBER_JOIN_GROUP where GROUP_ID=?";
		
		try
		{
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, GROUP_ID);
			
			resultSet = pstmt.executeQuery();
			
			while(resultSet.next())
			{
				arrList.add(resultSet.getString(1));
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
				if(resultSet != null)resultSet.close();
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return arrList;
	}
	
	// MEMBER_ID�� ���� GROUP_ID�� �޾ƿ��� �Լ�, GROUP_ID�� ArrayList<String>���� ��ȯ
	public ArrayList<String> getGroupId(String MEMBER_ID)
	{
		PreparedStatement psmt5 = null;
		ResultSet rs5 = null;
		
		ArrayList<String> arrList = new ArrayList<String>();
		String sql = "select GROUP_ID from MEMBER_JOIN_GROUP where MEMBER_ID=?";
		
		try
		{
			con = getConnection();
			psmt5 = con.prepareStatement(sql);
			psmt5.setString(1, MEMBER_ID);
			
			rs5 = psmt5.executeQuery();
			
			while(rs5.next())
			{
				arrList.add(rs5.getString(1));
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
				if(rs5 != null)rs5.close();
				if(psmt5 != null)psmt5.close();
				if(con != null)con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return arrList;
	}

	// ��Ż��
	public void roomOut(String groupId, String userId)
	{
		PreparedStatement pstmt = null;
		ResultSet rSet = null;

		try {
			con = getConnection();
			String sql = "DELETE FROM MEMBER_JOIN_GROUP WHERE MEMBER_ID=? and GROUP_ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, Integer.parseInt(groupId));

			int result = pstmt.executeUpdate();
			
			if(result == 1)
			{
				System.out.println("��Ż�� ����");
			}
			else
			{
				System.out.println("��Ż�� ����");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rSet != null)
					rSet.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateCalView(int groupId)
	{
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			String sql = "UPDATE MEMBER_JOIN_GROUP SET CALENDAR_READ_VIEW=CALENDAR_READ_VIEW+1 WHERE GROUP_ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, groupId);

			int result = pstmt.executeUpdate();
			
			if(result == 1)
			{
				System.out.println("+1");
			}
			else
			{
				System.out.println("--");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	// ���� �Խù��� ������ �Խù��� ǥ�����ֱ����ؿ�
	public ArrayList<MemberJoinGroupVO> getViewCountCheck(String MEMBER_ID){
		ArrayList<MemberJoinGroupVO> arrList = new ArrayList<MemberJoinGroupVO>();
		String sql = "select * from MEMBER_JOIN_GROUP where MEMBER_ID=?";
		PreparedStatement psmt10 = null;
		ResultSet rs10 = null;
		try{
			con = getConnection();
			psmt10 = con.prepareStatement(sql);
			psmt10.setString(1, MEMBER_ID);
			
			rs10 = psmt10.executeQuery();
			
			while(rs10.next()){
				MemberJoinGroupVO mj = new MemberJoinGroupVO();
				mj.setMEMBER_ID(rs10.getString("MEMBER_ID"));
				mj.setGROUP_ID(rs10.getString("GROUP_ID"));
				mj.setCALENDAR_READ_VIEW(rs10.getInt("CALENDAR_READ_VIEW"));
				mj.setCALENDAR_VIEW(rs10.getInt("CALENDAR_VIEW"));
				arrList.add(mj);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs10 != null)rs10.close();
				if(psmt10 != null)psmt10.close();
				if(con != null)con.close();
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		return arrList;
	}
	
	
	// �Խù��� �о��ٰ� ǥ�����ֱ� ���� �޼���...
	public void updateMyCalView(int groupId, String userName){
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			String sql = "UPDATE member_join_group SET CALENDAR_VIEW = CALENDAR_READ_VIEW WHERE GROUP_ID=? AND MEMBER_ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, groupId);
			pstmt.setString(2, userName);

			int result = pstmt.executeUpdate();
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
	
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
