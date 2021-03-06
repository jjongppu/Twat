package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;

import com.twat.dbpool.DBPool;
import com.twat.dto.CalgatherVO;
import com.twat.dto.MemberJoinGroupVO;
//import com.twat.dto.MyCalendarVO;
import com.twat.mvconnection.MVConnection;

public class CalgatherDAO {
//	Connection con = null;
//	PreparedStatement psmt = null;
//	ResultSet rs = null;

	// MemberDAO �쓽 �떛湲��넠 -----------------------------------
	private static CalgatherDAO instance = new CalgatherDAO();

	private CalgatherDAO() {
	}

	public static CalgatherDAO getInstance() {
		return instance;
	}
	// -------------------------------------------------------

	// DB�뿰寃곗쓣 �쐞�빐 con�쓣 諛섑솚�븯�뒗 硫붿꽌�뱶
	// --------------------------------------------
//	public Connection getConnection() throws Exception {
//		Context initCtx = new InitialContext();
//		DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/twhat");
//
//		return ds.getConnection();
//	}

	// 李몄뿬以묒씤 罹섎┛�뜑瑜� 肉뚮젮二쇨린�쐞�빐 �옄�떊�씠 李몄뿬�븯怨좎엳�뒗 紐⑤뱺 罹섎┛�뜑�쓽 �젙蹂대��
	// �뼸�뼱�샃�땲�떎..
	public ArrayList<CalgatherVO> myGroupList(ArrayList<MemberJoinGroupVO> groupList) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		ArrayList<MemberJoinGroupVO> groupArray = groupList;
		
		ArrayList<CalgatherVO> calArry = new ArrayList<CalgatherVO>();
		String selectMygroupSql = "SELECT * FROM CALGATHER WHERE GROUP_ID=?";

		for (int i = 1; i < groupList.size(); i++) {
			selectMygroupSql += " OR GROUP_ID=?";
		}

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(selectMygroupSql);
			int groupId = Integer.parseInt(groupArray.get(0).getGROUP_ID());
			psmt.setInt(1, groupId);

			for (int i = 1; i < groupList.size(); i++) {
				groupId = Integer.parseInt(groupArray.get(i).getGROUP_ID());
				psmt.setInt(i + 1, groupId);
			}

			rs = psmt.executeQuery();

			while (rs.next()) {
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
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return calArry;

	}

	// 새로운 그룹을 생성과 동시에 정규화테이블에도 쮺쮺넣어줌

	public int makeGorup(String groupName, String[] members, String Today, String masterId, String GroupImg,
			int GroupPk) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int result = 0;
		int GroupCount = members.length;
		String masterName = "";

		String selectMasterName = "SELECT * FROM MEMBER WHERE MEMBER_ID=?";

		String selectMakeGroupSql = "INSERT INTO CALGATHER VALUES(?,?,?,?,?,?,?)";

		String selectaddGMSql = "INSERT INTO MEMBER_JOIN_GROUP VALUES";
		selectaddGMSql += "('" + masterId + "','" + GroupPk + "',0,0),";

		for (int i = 0; i < members.length; i++) {
			selectaddGMSql += "('" + members[i] + "','" + GroupPk + "',0,0)";

			if (i != members.length - 1) {
				selectaddGMSql += ",";
			}
		}

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			// 諛⑹옣 �씠由� �뼸�뼱�샂 �뀫�뀫
			psmt = con.prepareStatement(selectMasterName);
			psmt.setString(1, masterId);

			result = 1;
			rs = psmt.executeQuery();
	
			result = 2;
			if (rs.next()) {
				masterName = rs.getString("MEMBER_NAME");
			}
			if(psmt != null)
				psmt.close();
			result = 3;
			// 諛⑹젙蹂� �씤�꽌�듃 �꽦

			psmt = con.prepareStatement(selectMakeGroupSql);
			psmt.setInt(1, GroupPk);
			psmt.setString(2, groupName);
			psmt.setString(3, Today);
			psmt.setString(4, masterId);
			psmt.setString(5, masterName);
			psmt.setString(6, GroupImg);
			psmt.setInt(7, GroupCount);			

			int res = psmt.executeUpdate();
			
			

			if (res > 0) {
				result = 5;
				if(psmt != null)
					psmt.close();
				psmt = con.prepareStatement(selectaddGMSql);

				int res2 = psmt.executeUpdate();
				result = 6;

				if (res2 > 0) {
					result = 7;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// �꽌踰� �옱 �떆�옉�떆.... �쁽�옱 洹몃９ 罹섎┛�뜑�쓽 留덉�留� pk 媛믪쓣 諛섑솚�빐以띾땲�떎
	public int getLastGroupId() {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		String selectMygroupSql = "SELECT * FROM CALGATHER ORDER BY GROUP_ID DESC LIMIT 1";

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(selectMygroupSql);

			rs = psmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt("GROUP_ID") + 1;
			}else{
				count =1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;

	}

	// 諛� �엯�옣�떆 肉뚮젮以� 諛⑹젙蹂� �뼸�뼱�삤湲�
	// ===============================================================
	public CalgatherVO getGroupInfo(String groupId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		CalgatherVO cv = new CalgatherVO();

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "SELECT * FROM CALGATHER WHERE GROUP_ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(groupId));

			rSet = pstmt.executeQuery();

			while (rSet.next()) {
				cv = new CalgatherVO();
				cv.setGroup_id(rSet.getInt("GROUP_ID"));
				cv.setGroup_name(rSet.getString("GROUP_NAME"));
				cv.setCreate_date(rSet.getString("CREATE_DATE"));
				cv.setGroup_master(rSet.getString("GROUP_MASTER"));
				cv.setGroup_master_name(rSet.getString("GROUP_MASTER_NAME"));
				cv.setGroup_img(rSet.getString("GROUP_IMG"));
				cv.setGroup_count(rSet.getInt("GROUP_COUNT"));
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

		return cv;
	}

	public String getGroupMaster(int groupId) {// 현재 그룹방의 그룹 마스터 알아오기 // 나동주 추가
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "";
		String groupMaster = "";

		try {
			sql = "SELECT GROUP_MASTER FROM CALGATHER WHERE GROUP_ID = ?";
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, groupId);
			rs = psmt.executeQuery();
			while (rs.next()) {
				groupMaster = rs.getString("GROUP_MASTER");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return groupMaster;

	}

	public void changeGM(int groupId, String gmId, String gmName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "UPDATE CALGATHER SET GROUP_MASTER=?, GROUP_MASTER_NAME=? WHERE GROUP_ID=?";
			// UPDATE `calgather` SET `GROUP_ID`=[value-1],`GROUP_NAME`=[value-2],`CREATE_DATE`=[value-3],`GROUP_MASTER`=[value-4],`GROUP_MASTER_NAME`=[value-5],`GROUP_IMG`=[value-6],`GROUP_COUNT`=[value-7] WHERE 1
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, gmId);
			pstmt.setString(2, gmName);
			pstmt.setInt(3, groupId);

			int result = pstmt.executeUpdate();
			
			if(result == 1)
			{
				System.out.println("방장 넘김");
			}
			else
			{
				System.out.println("방장 넘기기 실패");
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

	public boolean setRoomOut(int groupId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		boolean isDel = false;
		int count = -1;

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "SELECT GROUP_COUNT FROM CALGATHER WHERE GROUP_ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, groupId);
			
			rSet = pstmt.executeQuery();
			
//			if(pstmt != null)
//				pstmt.close();
			
//			int count = -1;
			
			while(rSet.next())
			{
				count = rSet.getInt(1) - 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (rSet != null)
//					rSet.close();
//				if (pstmt != null)
//					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "";
			
			if(count < 0)
			{
				sql = "DELETE FROM CALGATHER WHERE GROUP_ID=?";
//				pstmt = con.prepareStatement(sql);
			}
			else
			{
				sql = "UPDATE CALGATHER SET GROUP_COUNT=? WHERE GROUP_ID=?";
			}
			
			pstmt = con.prepareStatement(sql);
			
			if(count < 0)
			{
				pstmt.setInt(1, groupId);
				isDel = true;
			}
			else
			{
				pstmt.setInt(1, count);
				pstmt.setInt(2, groupId);
			}

			int result = pstmt.executeUpdate();
			
			if(result == 1)
			{
				System.out.println("성공");
			}
			else
			{
				System.out.println("실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (rSet != null)
//					rSet.close();
//				if (pstmt != null)
//					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return isDel;
	}
}
