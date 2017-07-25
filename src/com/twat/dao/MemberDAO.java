package com.twat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.Iterator;

import com.twat.mvconnection.*;



import com.twat.dbpool.DBPool;
import com.twat.dto.MemberVO;

public class MemberDAO {
//	Connection con = null;
//	PreparedStatement psmt = null;
//	ResultSet rs = null;

	private static MemberDAO instance = new MemberDAO();

	private MemberDAO() {
	}

	public static MemberDAO getInstance() {
		return instance;
	}


//	public Connection getConnection() throws Exception {
//		Context initCtx = new InitialContext();
//		DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/twhat");
//
//		return (Connection) ds.getConnection();
//	}

	public String searchID(String MEMBER_NAME, String MEMBER_PHONE) {

		String selectSql = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_NAME=? AND MEMBER_PHONE=?";
		String getID = "";
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(selectSql);
			psmt.setString(1, MEMBER_NAME);
			psmt.setString(2, MEMBER_PHONE);

			
			rs = psmt.executeQuery();

			while (rs.next()) {
				getID = rs.getString(1);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return getID;
	}

	public int searchPW(String id, String phone, String question, String answer) {
//		String selectSql = "select MEMBER_PW from MEMBER where MEMBER_ID=? and MEMBER_NAME=? and MEMBER_PHONE=?";
		String selectSql = "SELECT * FROM MEMBER WHERE MEMBER_ID=? AND MEMBER_PHONE=? AND MEMBER_QUESTION=? AND MEMBER_ANSWER=?";
		int result = 0;
		Connection con = null; 
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
//			con = getConnection();
			
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(selectSql);
			psmt.setString(1, id);
			psmt.setString(2, phone);
			psmt.setString(3, question);
			psmt.setString(4, answer);
			
			rs = psmt.executeQuery();
			
				while(rs.next()){
					result = 1;
				}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}

	public long loginMember(String MEMBER_ID, String MEMBER_PW) {
		long result = -1;

		long currentTime = System.currentTimeMillis();
		Connection con = null;
		PreparedStatement psmt2 = null;
		ResultSet rs2 = null;

		System.out.println(currentTime);

		// String selectSql = "select OUT_TIME from MEMBER where MEMBER_ID = ?
		// and MEMBER_PW = ?";
		String selectSql = "SELECT OUT_TIME FROM MEMBER WHERE MEMBER_ID = ? AND AES_DECRYPT(UNHEX(MEMBER_PW), 'memPW') = ?";

		try {
//			con = getConnection();
			
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt2 = con.prepareStatement(selectSql);
			psmt2.setString(1, MEMBER_ID);
			psmt2.setString(2, MEMBER_PW);
			rs2 = psmt2.executeQuery();
			if (rs2.next()) {
				result = rs2.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (rs2 != null)
//					rs2.close();
//				if (psmt2 != null)
//					psmt2.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	public ArrayList<Integer> getMyGroupList(String MEMBER_ID) {
		ArrayList<Integer> glList = new ArrayList<Integer>();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());

			String sql = "SELECT * FROM MEMBER_JOIN_GROUP WHERE MEMBER_ID =?";

			psmt = con.prepareStatement(sql);
			psmt.setString(1, MEMBER_ID);

			rs = psmt.executeQuery();
			while (rs.next()) {
				glList.add(rs.getInt("GROUP_ID"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return glList;
	}


	public int signUpMember(String MEMBER_ID, String MEMBER_PW, String MEMBER_NAME, String MEMBER_PHONE,
			String MEMBER_GENDER, String MEMBER_BIRTH, long OUT_TIME, String MEMBER_QUESTION, String MEMBER_ANSWER) {

		int result = 0;
		Connection con = null;
		PreparedStatement psmt = null;
//		int signUp = 0;

		// String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,?)";

		// String insertSql = "insert into MEMBER
		// values(?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";
		String insertSql = "INSERT INTO MEMBER VALUES(?,HEX(AES_ENCRYPT(?, 'memPW')),?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(insertSql);

			psmt.setString(1, MEMBER_ID);
			psmt.setString(2, MEMBER_PW);
			psmt.setString(3, MEMBER_NAME);
			psmt.setString(4, MEMBER_PHONE);
			psmt.setString(5, "img/member/basis_photo.png");
			psmt.setString(6, MEMBER_GENDER);
			psmt.setString(7, MEMBER_BIRTH);
			psmt.setString(8, "");
			psmt.setString(9, "");
			// psmt.setTimestamp(10, null);
			psmt.setLong(10, OUT_TIME);

			psmt.setString(11, MEMBER_QUESTION);
			psmt.setString(12, MEMBER_ANSWER);

			result = psmt.executeUpdate();
			// System.out.println(result);
			// rs = psmt.executeQuery();
			//

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(result);
		
		}

		return result;
	}

	public int checkID(String idCheck) {
		int result = -1;

		String sql = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ID=?";
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(sql);

			psmt.setString(1, idCheck);

			rs = psmt.executeQuery();
			System.out.println("!");
			if (rs.next()) {
				result = 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}

	public ArrayList<MemberVO> getFriendList(String MEMBER_ID) {

		String myFrinds = "";
		String findMyFriendsListSql = "SELECT FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID=?";
		String getMyFriendsSql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_PHONE, MEMBER_IMG, MEMBER_GENDER, MEMBER_BIRTH FROM MEMBER WHERE MEMBER_ID=?";
		ArrayList<MemberVO> myfriendsList = new ArrayList<MemberVO>();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(findMyFriendsListSql);
			psmt.setString(1, MEMBER_ID);
			rs = psmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("FRIENDS_LIST") != null) {

					myFrinds = rs.getString("FRIENDS_LIST");

					// try {
					// if(rs != null)
					// if(psmt != null) psmt.close();
					// if(con != null) con.close();
					// } catch (SQLException e) {
					// e.printStackTrace();
					// }
					String[] str = myFrinds.split(",");
					for (int i = 1; i < str.length; i++) {
						getMyFriendsSql += "or MEMBER_ID=?";
					}
					// con = getConnection();
					psmt = con.prepareStatement(getMyFriendsSql);
					for (int i = 0; i < str.length; i++) {
						psmt.setString(i + 1, str[i]);
					}
					rs = psmt.executeQuery();

					while (rs.next()) {
						MemberVO mv = new MemberVO();
						mv.setMEMBER_ID(rs.getString("MEMBER_ID"));
						mv.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
						mv.setMEMBER_PHONE(rs.getString("MEMBER_PHONE"));
						mv.setMEMBER_IMG(rs.getString("MEMBER_IMG"));
						mv.setMEMBER_GENDER(rs.getString("MEMBER_GENDER"));
						mv.setMEMBER_BIRTH(rs.getString("MEMBER_BIRTH"));
						myfriendsList.add(mv);
					}

				}
			}

		} catch (Exception e) {
		} finally {
			try {
//				if (rs != null)
//					if (psmt != null)
//						psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return myfriendsList;
	}

	public ArrayList<MemberVO> myInfo(String MEMBER_ID) {
		String sql = "SELECT MEMBER_IMG, MEMBER_NAME, MEMBER_PHONE, MEMBER_BIRTH FROM MEMBER WHERE MEMBER_ID = ? ";

		ArrayList<MemberVO> arList = new ArrayList<MemberVO>();
		
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
				

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(sql);
			psmt.setString(1, MEMBER_ID);
			rs = psmt.executeQuery();

			while (rs.next()) {
				MemberVO mvo = new MemberVO();
				mvo.setMEMBER_IMG(rs.getString("MEMBER_IMG"));
				mvo.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
				mvo.setMEMBER_PHONE(rs.getString("MEMBER_PHONE"));
				mvo.setMEMBER_BIRTH(rs.getString("MEMBER_BIRTH"));
				arList.add(mvo);
			}

		} catch (Exception e) {
		} finally {
			try {
//				if (rs != null)
//					if (psmt != null)
//						psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return arList;
	}


	public ArrayList<MemberVO> getMemberBirth(ArrayList<String> memberList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		ArrayList<MemberVO> arrList = new ArrayList<MemberVO>();
		String sql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_BIRTH, MEMBER_IMG FROM MEMBER";

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			pstmt = con.prepareStatement(sql);
			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				for (int i = 0; i < memberList.size(); i++) {
					if (resultSet.getString(1).equals(memberList.get(i))) {
						MemberVO member = new MemberVO();

						member.setMEMBER_ID(resultSet.getString(1));
						member.setMEMBER_NAME(resultSet.getString(2));
						member.setMEMBER_BIRTH(resultSet.getString(3));
						member.setMEMBER_IMG(resultSet.getString(4));

						arrList.add(member);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (resultSet != null)
//					resultSet.close();
//				if (pstmt != null)
//					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return arrList;
	}


	

	public int outUser(String MEMBER_ID, String state) {

		// Date date = new Date();
		// String today = outDate.format(date);
		Connection con = null;
		PreparedStatement psmt = null;
		

		long outTime = System.currentTimeMillis() + 60 * 60 * 24 * 1000 * 7;
		long resetTime = 0;

		String delUser = "UPDATE MEMBER SET OUT_TIME=? WHERE MEMBER_ID =?";

		int result = 0;
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(delUser);
			if (state.equals("out")) {
				psmt.setLong(1, outTime);
				System.out.println("1");
			} else {
				psmt.setLong(1, resetTime);
				System.out.println("2");
			}
			psmt.setString(2, MEMBER_ID);
			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null)
//					if (psmt != null)
//						psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}

		return result;
	}

	public int changeInfo(String MEMBER_NAME, String MEMBER_PHONE, String MEMBER_BIRTH, String MEMBER_ID) {
		String changeInfo = "UPDATE MEMBER SET MEMBER_NAME=?, MEMBER_PHONE=?,MEMBER_BIRTH=? WHERE MEMBER_ID =?";
		int result = 0;
		Connection con = null;
		PreparedStatement psmt = null;
		
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(changeInfo);

			psmt.setString(1, MEMBER_NAME);
			psmt.setString(2, MEMBER_PHONE);
			psmt.setString(3, MEMBER_BIRTH);
			psmt.setString(4, MEMBER_ID);
			result = psmt.executeUpdate();

			if (result != 1) {
				System.out.println("회占쏙옙 占쏙옙占쏙옙占실억옙占쏙옙.");
				System.out.println("占쏙옙");
			} else {
				System.out.println("회占쏙옙 占쏙옙占쏙옙占싫되억옙占쏙옙.");
				System.out.println("占쏙옙2");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null)
//					if (psmt != null)
//						psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return result;

	}

	public int changeImg(String MEMBER_ID, String MEMBER_IMG) {
		String changepwd = "UPDATE MEMBER SET MEMBER_IMG = ? WHERE MEMBER_ID = ?";
		int result = 0;
		Connection con = null;
		PreparedStatement psmt = null;
		
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());

			psmt = con.prepareStatement(changepwd);
			psmt.setString(1, "img/member/" + MEMBER_IMG);
			psmt.setString(2, MEMBER_ID);
			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return result;

	}

	public ArrayList<MemberVO> findFriends(String userPhone) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;				

		String addFriend = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_BIRTH, MEMBER_PHONE, MEMBER_IMG FROM MEMBER WHERE MEMBER_PHONE = ?";
		ArrayList<MemberVO> arList = new ArrayList<MemberVO>();
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(addFriend);
			psmt.setString(1, userPhone);
			rs = psmt.executeQuery();

			while (rs.next()) {
				MemberVO member = new MemberVO();
				member.setMEMBER_IMG(rs.getString(5));
				member.setMEMBER_NAME(rs.getString(2));
				member.setMEMBER_BIRTH(rs.getString(3));
				member.setMEMBER_PHONE(rs.getString(4));
				member.setMEMBER_ID(rs.getString(1));

				arList.add(member);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return arList;
	}

	

	public ArrayList<String> friendPhoneSearch(int phoneOrId, String phoneId) {
		
		Connection con = null;		
		ArrayList<String> memArr = new ArrayList<String>();
		PreparedStatement psmt2 = null;
		ResultSet rs2 = null;
		String sql = "";
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());

			if (phoneOrId == 0)
				sql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_PHONE, MEMBER_IMG, MEMBER_GENDER, MEMBER_BIRTH FROM MEMBER WHERE MEMBER_PHONE = ?";
			else
				sql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_PHONE, MEMBER_IMG, MEMBER_GENDER, MEMBER_BIRTH FROM MEMBER WHERE MEMBER_ID = ?";

			psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, phoneId);
			rs2 = psmt2.executeQuery();
			while (rs2.next()) {
				memArr.add(rs2.getString("MEMBER_ID"));
				memArr.add(rs2.getString("MEMBER_NAME"));
				memArr.add(rs2.getString("MEMBER_PHONE"));
				memArr.add(rs2.getString("MEMBER_IMG"));
				memArr.add(rs2.getString("MEMBER_GENDER"));
				memArr.add(rs2.getString("MEMBER_BIRTH"));

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
//				if (rs2 != null)
//					rs2.close();
//				if (psmt2 != null)
//					psmt2.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}

		return memArr;

	}

	public int requestFriend(String userId, String friendId) {

		String myFriendList = getfriendListForString(userId);
		String friendFriendsList = getfriendListForString(friendId);
		int result = 0;
		
		System.out.println(userId + " , " + friendId);
		
		if (userId.equals(friendId)) {
			result = 3; 
			return result;
		}	

			for (int i = 0; i < myFriendList.split(",").length; i++) {
				if (myFriendList.split(",")[i].equals(friendId)) {
					result = -1;
					return result;
				} else if (myFriendList.split(",")[i].equals("*" + friendId)) {
					result = 1; 
					return result;
				} else if (myFriendList.split(",")[i].equals("!" + friendId)) {
					result = 2; 
					return result;
				}

			}

			requestFriendsUpdate(userId, friendId, myFriendList, friendFriendsList);

		return result;

	}

	public void requestFriendsUpdate(String userId, String friendId, String myFriendList, String friendFriendsList) {
		Connection con = null;
		PreparedStatement psmt2 = null;

		System.out.println(myFriendList);

		try {
		
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
		 
			String sql = "UPDATE MEMBER SET FRIENDS_LIST = ? WHERE MEMBER_ID = ?";
			psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, myFriendList + "," + "*" + friendId);
			psmt2.setString(2, userId);
			psmt2.executeUpdate();

//			String sql2 = "UPDATE MEMBER SET FRIENDS_LIST = ? WHERE MEMBER_ID = ?";
			psmt2.setString(1, friendFriendsList + "," + "!" + userId);
			psmt2.setString(2, friendId);
			psmt2.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				if(con != null)
					con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public MemberVO friendInfo(String friendId) {
		Connection con = null;
		PreparedStatement psmt2 = null;
		ResultSet rs2 = null;
		MemberVO member = new MemberVO();
		try {
			
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
			psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, friendId);
			rs2 = psmt2.executeQuery();
			while (rs2.next()) {
				member.setMEMBER_ID(rs2.getString("MEMBER_ID"));
				member.setMEMBER_NAME(rs2.getString("MEMBER_NAME"));
				member.setMEMBER_IMG(rs2.getString("MEMBER_IMG"));
				member.setMEMBER_PHONE(rs2.getString("MEMBER_PHONE"));
				member.setMEMBER_GENDER(rs2.getString("MEMBER_GENDER"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
//				if (rs2 != null)
//					rs2.close();
//				if (psmt2 != null)
//					psmt2.close();
				if(con != null)
					con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return member;

	}

	public ArrayList<MemberVO> requestingFriendList(String userId, String tokenRes) {
		Connection con = null;
		PreparedStatement psmt2 = null;
		ResultSet rs2 = null;
		String friendsList = null;
		ArrayList<MemberVO> memberArr = new ArrayList<MemberVO>();
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "SELECT FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID = ?";
			psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, userId);
			rs2 = psmt2.executeQuery();
			// System.out.println(rs2);

			while (rs2.next())
				friendsList = rs2.getString("FRIENDS_LIST");

			// System.out.println("내아이디 : " +userId);
			// System.out.println("친구리스트 : " + friendsList);
			if (friendsList.equals("") || friendsList.equals(",")) {

				return null;
			} else {
				String[] eachFriend = friendsList.split(",");
				for (int i = 0; i < eachFriend.length; i++) {

					if (eachFriend[i].length() != 0) {
						if (eachFriend[i].substring(0, 1).equals(tokenRes)) {
							// System.out.println(friendInfo(eachFriend[i].substring(1,
							// eachFriend[i].length())).getMEMBER_NAME());
							memberArr.add(friendInfo(eachFriend[i].substring(1, eachFriend[i].length())));
						}

					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (rs2 != null)
//					rs2.close();
//				if (psmt2 != null)
//					psmt2.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return memberArr;
	}

	public String getfriendListForString(String userId) {
		Connection con = null;
		PreparedStatement psmt2 = null;
		ResultSet rs2 = null;
		String friendList = "";
		String sql = "SELECT FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID = ?";
		try {
			con = new MVConnection(DBPool.getInstance().getConnection());
		
			psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, userId);
			rs2 = psmt2.executeQuery();
			if (rs2.next()){
				friendList = rs2.getString("FRIENDS_LIST");
			}
		}catch(Exception e){			
		}finally {
			try {
//				if (rs2 != null)
//					rs2.close();
//				if (psmt2 != null)
//					psmt2.close();
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return friendList;
	}

	public void acceptFriend(String userId, String friendId) {
		Connection con = null;		
		PreparedStatement psmt2 = null;
		String[] myFriendList = null;
		String changeMyFriendList = "";
		String[] friendFriendList = null;
		String changeFriendFriendList = "";

		try {
			
			
			myFriendList = getfriendListForString(userId).split(",");

			for (int i = 0; i < myFriendList.length; i++) {
				if (myFriendList[i].length() != 0) {
					if (myFriendList[i].equals("!" + friendId)) {
						myFriendList[i] = myFriendList[i].substring(1, myFriendList[i].length());

					}

				}

			}

			for (int i = 0; i < myFriendList.length; i++) {
				if (i == myFriendList.length - 1) {
					changeMyFriendList += myFriendList[i];

				} else {
					changeMyFriendList += myFriendList[i] + ",";
				}

			}

			// System.out.println(changeFriendList);
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "UPDATE MEMBER SET FRIENDS_LIST = ? WHERE MEMBER_ID = ?";
			psmt2 = con.prepareStatement(sql);

			psmt2.setString(1, changeMyFriendList);
			psmt2.setString(2, userId);

			psmt2.executeUpdate();

			// System.out.println(changeMyFriendList);
			
			

			friendFriendList = getfriendListForString(friendId).split(",");
			for (int i = 0; i < friendFriendList.length; i++) {
				if (friendFriendList[i].length() != 0) {
					if (friendFriendList[i].equals("*" + userId)) {
						friendFriendList[i] = friendFriendList[i].substring(1, friendFriendList[i].length());

					}

				}

			}

			for (int i = 0; i < friendFriendList.length; i++) {
				if (i == friendFriendList.length - 1) {
					changeFriendFriendList += friendFriendList[i];

				} else {
					changeFriendFriendList += friendFriendList[i] + ",";
				}

			}
			// System.out.println(changeFriendFriendList);
//			con = getConnection();
//			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql2 = "UPDATE MEMBER SET FRIENDS_LIST = ? WHERE MEMBER_ID = ?";
			psmt2 = con.prepareStatement(sql2);
			psmt2.setString(1, changeFriendFriendList);
			psmt2.setString(2, friendId);

			psmt2.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
//				if (psmt2 != null)
//					psmt2.close();
//				if (psmt3 != null)
//					psmt3.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	public int searchangePW(String userId, String beforePw, String afterPW){
		
		
		int result = 0;
		Connection con = null;
		PreparedStatement psmt = null;
		
		try {
			String sql = "UPDATE MEMBER SET MEMBER_PW = HEX(AES_ENCRYPT(?, 'memPW')) WHERE MEMBER_ID = ? ";
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, afterPW);
			psmt.setString(2, userId);
			psmt.executeUpdate();
			result = psmt.executeUpdate();
			if(result == 1){
				result = 0;
			}
			
			
				
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return 0;
	}
	
	public int changePw(String userId, String beforePw, String afterPw) {
		Connection con = null;
		PreparedStatement psmt2 = null;
		ResultSet rs2 = null;
		try {
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ? AND MEMBER_PW = HEX(AES_ENCRYPT(?, 'memPW'))";
			psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, userId);
			psmt2.setString(2, beforePw);
			
//			if(psmt2 != null){
//				psmt2.close();
//			System.out.println("2");
			
			rs2 = psmt2.executeQuery();
//			}
			
			if (rs2.next()) { 
				String sql2 = "UPDATE MEMBER SET MEMBER_PW = HEX(AES_ENCRYPT(?, 'memPW')) WHERE MEMBER_ID = ?";
				psmt2 = con.prepareStatement(sql2);
				psmt2.setString(1, afterPw);
				psmt2.setString(2, userId);
				psmt2.executeUpdate();
				return 0;

			} else {
				return -1;

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {
				if (rs2 != null)
					rs2.close();
				if (psmt2 != null)
					psmt2.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return 1;
	}

	public void deleteFriend(String userId, String deleteFriend) { 
		Connection con = null;
		PreparedStatement psmt2 = null;
		
//		ResultSet rs2 = null;
		String friendsList = "";
		ArrayList<String> friendsListArr = new ArrayList<String>();
		String deletedFriendsList = "";

		try {
			

			friendsList = getfriendListForString(userId);

			for (int i = 0; i < friendsList.split(",").length; i++) {
				friendsListArr.add(friendsList.split(",")[i]);
			}

			for (int i = 0; i < friendsListArr.size(); i++) {
				if (friendsListArr.get(i).equals(deleteFriend))
					friendsListArr.remove(i);

			}

			for (int i = 0; i < friendsListArr.size(); i++) {
				if (friendsListArr.get(i).length() != 0)
					deletedFriendsList += "," + friendsListArr.get(i);

			}

			// System.out.println(deletedFriendsList);
			
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "UPDATE MEMBER SET FRIENDS_LIST = ? WHERE MEMBER_ID = ?";
			psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, deletedFriendsList);
			psmt2.setString(2, userId);
			psmt2.executeUpdate();

			friendsList = getfriendListForString(deleteFriend);
			friendsListArr.clear();
			deletedFriendsList = "";

			for (int i = 0; i < friendsList.split(",").length; i++) {
				friendsListArr.add(friendsList.split(",")[i]);
			}

			for (int i = 0; i < friendsListArr.size(); i++) {
				if (friendsListArr.get(i).equals(userId))
					friendsListArr.remove(i);
			}

			for (int i = 0; i < friendsListArr.size(); i++) {
				if (friendsListArr.get(i).length() != 0)
					deletedFriendsList += "," + friendsListArr.get(i);
			}
//			con = getConnection();
			
			String sql2 = "UPDATE MEMBER SET FRIENDS_LIST = ? WHERE MEMBER_ID = ?";
			
			psmt2 = con.prepareStatement(sql2);
			
			psmt2.setString(1, deletedFriendsList);
			psmt2.setString(2, deleteFriend);
			psmt2.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
//				if (rs2 != null)
//					rs2.close();
//				if (psmt2 != null)
//					psmt2.close();				
				if (con != null)
					con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList<MemberVO> getMemberList(ArrayList<String> memberList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		ArrayList<MemberVO> arrList = new ArrayList<MemberVO>();
		String sql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_IMG FROM MEMBER";

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			pstmt = con.prepareStatement(sql);
			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				for (int i = 0; i < memberList.size(); i++) {
					if (resultSet.getString(1).equals(memberList.get(i))) {
						MemberVO member = new MemberVO();

						member.setMEMBER_ID(resultSet.getString(1));
						member.setMEMBER_NAME(resultSet.getString(2));
						member.setMEMBER_IMG(resultSet.getString(3));

						arrList.add(member);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				if (resultSet != null)
//					resultSet.close();
//				if (pstmt != null)
//					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return arrList;
	}
	
	public ArrayList<String> friendsListForArrList(String userId){
		ArrayList<String> friendArrList = new ArrayList<String>();
		String friendsList = getfriendListForString(userId); 
//		getfriendListForString query is SELECT FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID = ?
		
		for(int i = 0; i < friendsList.split(",").length; i++){
			if(friendsList.split(",")[i].length() != 0){
				friendArrList.add(friendsList.split(",")[i]);	
			}	
		}
		return friendArrList; 
	}
	
	public void requestCancelRefuse(String userId, String friendId, String refuseOrCancel){
		Connection con = null;
		PreparedStatement psmt2 = null;
		String myFriendList = "";
		String friendFriendList = "";
		String myToken = "";
		String frToken = "";		
		ArrayList<String> myArr = friendsListForArrList(userId); // this method will get friends lists.
		ArrayList<String> frArr = friendsListForArrList(friendId);
//		System.out.println(friendId);
		
		if(refuseOrCancel.equals("refuse")){
			myToken = "!";	// recieved friend requesting 
			frToken = "*";  // requested friend 			
		}else{
			myToken = "*";  // requested friend
			frToken = "!";	// recieved friend requesting
		}
		
		System.out.println(myToken+friendId);
		System.out.println(myToken+friendId);
		System.out.println(myToken+friendId + "  시작!!  ");

		
		
		for(Iterator<String> it = myArr.iterator(); it.hasNext();){ // friends lists array by my ID
			String value = it.next();
			System.out.println(value);
//			if(value.equals(myToken+friendId)){ // myToken("!") + friendId(refused ID) 
//				it.remove();
//			}else{
////				myFriendList += "," + myArr.get(it.hashCode());
//				myFriendList += "," + value;
//			}
			if(!value.equals(myToken+friendId)){
				myFriendList += "," + value;
			}
			
//			System.out.println(myFriendList);
		}
		
		System.out.println(myFriendList);
		
		for(int i = 0; i < frArr.size(); i++){ // friends lists array by refused person ID
			if(frArr.get(i).equals(frToken+userId)){ // frToken("*") + friendId(refused ID) 
				frArr.remove(i);				
			}else if(frArr.get(i).length() != 0){
				friendFriendList += "," + frArr.get(i);
			}			
		}
		
		
		try {
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "UPDATE MEMBER SET FRIENDS_LIST = ? WHERE MEMBER_ID = ?";
			psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, myFriendList);
			psmt2.setString(2, userId);
			psmt2.executeUpdate();
			
			if(psmt2 != null){
				psmt2.close();
			}
			
			psmt2 = con.prepareStatement(sql);
			psmt2.setString(1, friendFriendList);
			psmt2.setString(2, friendId);
			psmt2.executeUpdate(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
	}
}