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


public class CalgatherDAO {
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;

	
	// MemberDAO �� �̱��� -----------------------------------
	private static CalgatherDAO instance = new CalgatherDAO();
	
	private CalgatherDAO(){}
	
	public static CalgatherDAO getInstance(){
		return instance;
	}
	// -------------------------------------------------------
	
	
	
	// DB������ ���� con�� ��ȯ�ϴ� �޼��� --------------------------------------------
	public Connection getConnection() throws Exception {
		   Context initCtx = new InitialContext();
		   DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/aclass0201");      
		      
		return ds.getConnection();
	}
	
	
	// �������� Ķ������ �ѷ��ֱ����� �ڽ��� �����ϰ��ִ� ��� Ķ������ ������ ���ɴϴ�..
	public ArrayList<CalgatherVO> myGroupList(ArrayList<Integer> groupList){
		
		ArrayList<CalgatherVO> calArry = new ArrayList<CalgatherVO>();
		String selectMygroupSql = "select * from CALGATHER where GROUP_ID=?";
		
		for(int i = 1; i <groupList.size();i++){
			selectMygroupSql += " or GROUP_ID=?";
		}
		
		try{
			con = getConnection();
			psmt = con.prepareStatement(selectMygroupSql);
			
			psmt.setInt(1, groupList.get(0));
			
			for(int i = 1; i <groupList.size();i++){
				psmt.setInt(i+1, groupList.get(i));
			}		
			
			rs = psmt.executeQuery();
				
			while(rs.next()){
				CalgatherVO cv = new CalgatherVO();
				cv.setGroup_id(rs.getInt("GROUP_ID"));
				cv.setGroup_name(rs.getString("GROUP_NAME"));
				cv.setCreate_date(rs.getString("CREATE_DATE"));
				cv.setGroup_master(rs.getString("GROUP_MASTER"));
				cv.setGroup_master_name(rs.getString("GROUP_MASTER_NAME"));
				cv.setGroup_img(rs.getString("GROUP_IMG"));
				cv.setGroup_count(rs.getInt("GROUP_COUNT"));
				calArry.add(cv);
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
		
		return calArry;
		
	}
	

	// ���ο� �׷��� ������ ���ÿ� ����ȭ���̺��� �����־���
	public int makeGorup(String groupName, String[] members, String Today, String masterId, int GroupPk){
		int result = 0;
		int GroupCount = members.length;
		String masterName="�ҷ����� ����";
		
		String selectMasterName = "SELECT * FROM MEMBER WHERE MEMBER_ID=?";
		
		String selectMakeGroupSql = "INSERT INTO CALGATHER VALUES(?,?,?,?,?,'img/group/default.png',?)";
		
		String selectaddGMSql = "INSERT INTO MEMBER_JOIN_GROUP VALUES";
		selectaddGMSql += "('" + masterId + "','" + GroupPk + "')," ;
				
				
		for (int i = 0; i < members.length; i++) {
			selectaddGMSql += "('" + members[i]  + "','" +GroupPk + "')" ;
			
			if(i != members.length-1){
				selectaddGMSql +=",";
			}
		}
		
		try{
			con = getConnection();
			// ���� �̸� ���� �Ѥ�
			psmt = con.prepareStatement(selectMasterName);
			psmt.setString(1,masterId);
			
			result =1 ;
			rs = psmt.executeQuery();
			
			result = 2 ;
			if(rs.next()){
				masterName = rs.getString("MEMBER_NAME");
			}
			result =3 ;
			// ������ �μ�Ʈ ��
			
			psmt = con.prepareStatement(selectMakeGroupSql);
			psmt.setInt(1,GroupPk);
			psmt.setString(2,groupName);
			psmt.setString(3,Today);
			psmt.setString(4,masterId);
			psmt.setString(5,masterName);
			psmt.setInt(6,GroupCount);
			
			
			int res = psmt.executeUpdate();
			
			if(res >0){
				result = 5;
				

				psmt = con.prepareStatement(selectaddGMSql);
				
				int res2 = psmt.executeUpdate();
				result = 6;
				
				if(res2 > 0){
					result = 7;
				}	
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
		
		
		return  result;
	}
	
	
	
	// ���� �� ���۽�.... ���� �׷� Ķ������ ������ pk ���� ��ȯ���ݴϴ�
	public int getLastGroupId(){
		int count = 0;
		String selectMygroupSql = "SELECT * FROM CALGATHER order by GROUP_ID desc limit 1";
		
		
		try{
			con = getConnection();
			psmt = con.prepareStatement(selectMygroupSql);
			
			
			rs = psmt.executeQuery();
				
			if(rs.next()){
				count = rs.getInt("GROUP_ID")+1;
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
		
		return count;
		
	}
	
	
	// �� ����� �ѷ��� ������ ������ ===============================================================
	   public CalgatherVO getGroupInfo(String grouId)
	   {
	      CalgatherVO cv = new CalgatherVO();
	      
	      try
	      {
	         con = getConnection();
	         String sql = "select * from CALGATHER where GROUP_ID=?";
	         psmt = con.prepareStatement(sql);
	         psmt.setInt(1, Integer.parseInt(grouId));
	         
	         rs = psmt.executeQuery();
	            
	         while(rs.next()){
	            cv = new CalgatherVO();
	            cv.setGroup_id(rs.getInt("GROUP_ID"));
	            cv.setGroup_name(rs.getString("GROUP_NAME"));
	            cv.setCreate_date(rs.getString("CREATE_DATE"));
	            cv.setGroup_master(rs.getString("GROUP_MASTER"));
	            cv.setGroup_master_name(rs.getString("GROUP_MASTER_NAME"));
	            cv.setGroup_img(rs.getString("GROUP_IMG"));
	            cv.setGroup_count(rs.getInt("GROUP_COUNT"));
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
	      
	      return cv;
	   }
	
	
	
	
	
}
