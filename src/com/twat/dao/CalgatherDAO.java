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

	
	// MemberDAO 의 싱글톤 -----------------------------------
	private static CalgatherDAO instance = new CalgatherDAO();
	
	private CalgatherDAO(){}
	
	public static CalgatherDAO getInstance(){
		return instance;
	}
	// -------------------------------------------------------
	
	
	
	// DB연결을 위해 con을 반환하는 메서드 --------------------------------------------
	public Connection getConnection() throws Exception {
		   Context initCtx = new InitialContext();
		   DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
		      
		return ds.getConnection();
	}
	
	
	// 참여중인 캘린더를 뿌려주기위해 자신이 참여하고있는 모든 캘린더의 정보를 얻어옵니다..
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
	

<<<<<<< HEAD
	// ���ο� �׷��� ������ ���ÿ� ����ȭ���̺����� �����־���
=======
	// 새로운 그룹을 생성과 동시에 정규화테이블에도 쮺쮺넣어줌
>>>>>>> f7505ad46ec1f70fb726bea35131e073c366e797
	public int makeGorup(String groupName, String[] members, String Today, String masterId,String GroupImg, int GroupPk){
		int result = 0;
		int GroupCount = members.length;
		String masterName="불러오지 못함";
		
		String selectMasterName = "SELECT * FROM MEMBER WHERE MEMBER_ID=?";
		
		String selectMakeGroupSql = "INSERT INTO CALGATHER VALUES(?,?,?,?,?,?,?)";
		
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
			// 방장 이름 얻어옴 ㅡㅡ
			psmt = con.prepareStatement(selectMasterName);
			psmt.setString(1,masterId);
			
			result =1 ;
			rs = psmt.executeQuery();
			
			result = 2 ;
			if(rs.next()){
				masterName = rs.getString("MEMBER_NAME");
			}
			result =3 ;
			// 방정보 인서트 ㄱ
			
			psmt = con.prepareStatement(selectMakeGroupSql);
			psmt.setInt(1,GroupPk);
			psmt.setString(2,groupName);
			psmt.setString(3,Today);
			psmt.setString(4,masterId);
			psmt.setString(5,masterName);
			psmt.setString(6,GroupImg);
			psmt.setInt(7,GroupCount);
			
			
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
	
	
	
	// 서버 재 시작시.... 현재 그룹 캘린더의 마지막 pk 값을 반환해줍니다
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
	
	
	// 방 입장시 뿌려줄 방정보 얻어오기 ===============================================================
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

<<<<<<< HEAD
	      public String getGroupMaster(int groupId){//���� �׷���� �׷� ������ �˾ƿ��� // ������ �߰�
=======
	      public String getGroupMaster(int groupId){//현재 그룹방의 그룹 마스터 알아오기 // 나동주 추가
>>>>>>> f7505ad46ec1f70fb726bea35131e073c366e797
		String sql = "";
		String groupMaster = "";
		   
		try {
			sql = "select GROUP_MASTER from calgather where GROUP_ID = ?";
			con = getConnection();
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, groupId);
			rs = psmt.executeQuery();
			while(rs.next()){
				groupMaster = rs.getString("GROUP_MASTER");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
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
	      
		return groupMaster;
		   
		   
		   
	   }
	
	
	
	
	
	
}
