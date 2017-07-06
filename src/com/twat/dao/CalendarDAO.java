package com.twat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.sql.DataSource;

import com.twat.dto.CalendarVO;
//import com.twat.dto.CalgatherVO;
import com.twat.mvconnection.MVConnection;
import com.twat.dbpool.DBPool;

public class CalendarDAO {
//	Connection con = null;
//	PreparedStatement psmt = null;
//	ResultSet rs = null;
//	Statement stmt;

	// CalendarDAO �뜝�룞�삕 �뜝�떛源띿삕�뜝�룞�삕
	// ================================================================================================
	private static CalendarDAO instance = new CalendarDAO();

	private CalendarDAO() {
	}

	public static CalendarDAO getInstance() {
		return instance;
	}

	// DB�뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕 con�뜝�룞�삕 �뜝�룞�삕�솚�뜝�떦�뙋�삕 �뜝�뙣�눦�삕�뜝�룞�삕
	// ==================================================================================
//	public Connection getConnection() throws Exception {
//		Context initCtx = new InitialContext();
//		DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/twhat");
//
//		return ds.getConnection();
//	}

	// �뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕 �뜝�뙣�븘�슱�삕�뜝�룞�삕
	// ===================================================================================================
	public ArrayList<CalendarVO> getInfo(String groupId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		ArrayList<CalendarVO> arrList = new ArrayList<CalendarVO>();

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
//			String sql = "select * from CALENDAR where CAL_DEPTH=0 and GROUP_ID=?";
			String sql = "SELECT * FROM CALENDAR WHERE CAL_DEPTH=0 AND GROUP_ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(groupId));

			rSet = pstmt.executeQuery();

			while (rSet.next()) {
				CalendarVO schedule = new CalendarVO();
				schedule.setCal_num(rSet.getInt(1));
//				schedule.setCal_time(rSet.getTimestamp(2));
				Date date = rSet.getTimestamp(2);
				Timestamp timestamp = new Timestamp(date.getTime());
//				System.out.println(timestamp.getTime());
				schedule.setCal_time(timestamp);
				
				schedule.setCal_date(rSet.getString(3));
				schedule.setGroup_id(rSet.getInt(4));
				schedule.setCal_memo(rSet.getString(5));
				schedule.setCal_writer(rSet.getString(6));
				schedule.setState_icon(rSet.getString(7));
				schedule.setMember_choice(rSet.getString(8));
				schedule.setCal_reference(rSet.getInt(9));
				schedule.setCal_depth(rSet.getInt(10));

				arrList.add(schedule);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rSet != null)
//					rSet.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			// con 만 close() 해준다.
	        if (con != null)
	        {
	        	try
	        	{
	        		con.close();
	        	}
	        	catch (SQLException ex) {
					ex.printStackTrace();
	    		}
	        }
		}

		return arrList;
	}

	// �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�뙣�븘�슱�삕�뜝�룞�삕(�뜝�뙎琉꾩삕 �뜝�룞�삕�뜝�떛�벝�삕, �뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�떛�벝�삕)
	public ArrayList<CalendarVO> getInfo(String groupId, String calId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
		ArrayList<CalendarVO> arrList = new ArrayList<CalendarVO>();

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
//			String sql = "select * from CALENDAR where GROUP_ID=? and CAL_REFERENCE=? ORDER BY CAL_NUM";
			String sql = "SELECT * FROM CALENDAR WHERE GROUP_ID=? AND CAL_REFERENCE=? ORDER BY CAL_NUM";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(groupId));
			pstmt.setInt(2, Integer.parseInt(calId));

			rSet = pstmt.executeQuery();

			while (rSet.next()) {
				CalendarVO schedule = new CalendarVO();

				schedule.setCal_num(rSet.getInt(1));
//				schedule.setCal_time(rSet.getTimestamp(2));
				
				Date date = rSet.getTimestamp(2);
				Timestamp timestamp = new Timestamp(date.getTime());
//				System.out.println(timestamp.getTime());
				schedule.setCal_time(timestamp);
				
				schedule.setCal_date(rSet.getString(3));
				schedule.setGroup_id(rSet.getInt(4));
				schedule.setCal_memo(rSet.getString(5));
				schedule.setCal_writer(rSet.getString(6));
				schedule.setState_icon(rSet.getString(7));
				schedule.setMember_choice(rSet.getString(8));
				schedule.setCal_reference(rSet.getInt(9));
				schedule.setCal_depth(rSet.getInt(10));

				// System.out.println(schedule.toString());

				arrList.add(schedule);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rSet != null)
//					rSet.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			if (con != null)
	        {
	        	try
	        	{
	        		con.close();
	        	}
	        	catch (SQLException ex) {
					ex.printStackTrace();
	    		}
	        }
		}

		return arrList;
	}

	public int getLastCalNum() {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "";
		int cal_num = 0;

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
//			sql = "SELECT * FROM calendar order by CAL_NUM desc limit 1";
			sql = "SELECT * FROM CALENDAR ORDER BY CAL_NUM DESC LIMIT 1";
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				cal_num = rs.getInt("CAL_NUM");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			if (con != null)
	        {
	        	try
	        	{
	        		con.close();
	        	}
	        	catch (SQLException ex) {
					ex.printStackTrace();
	    		}
	        }
		}

		return cal_num + 1;

	}

	public void addGroupCal(int cal_num, String cal_date, int group_id, String cal_memo, String cal_writer) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
//			sql = "insert into calendar VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?)";
			sql = "INSERT INTO CALENDAR VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?)";
			String[] dateStr = cal_date.split(",");
			String writer_id = "";
			for (int i = 0; i < dateStr.length; i++) {
				writer_id += dateStr[i] + "-" + cal_writer + ",";
			}

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cal_num);
			pstmt.setString(2, cal_date);
			pstmt.setInt(3, group_id);
			pstmt.setString(4, cal_memo);
			pstmt.setString(5, cal_writer);
			// psmt.setString(5, "111");
			pstmt.setString(6, "");
			pstmt.setString(7, writer_id.substring(0, writer_id.length() - 1));
			pstmt.setInt(8, cal_num);
			pstmt.setInt(9, 0);

			pstmt.executeUpdate();
			// psmt.executeQuery();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

//			try {
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			if (con != null)
	        {
	        	try
	        	{
	        		con.close();
	        	}
	        	catch (SQLException ex) {
					ex.printStackTrace();
	    		}
	        }
		}

	}

//	public void addCalComment(int group_id, String cal_memo, String first_cal, String new_memo, String cal_writer) {
//		PreparedStatement psmt2 = null;
//
//		try {
////			con = getConnection();
//			con = new MVConnection(DBPool.getInstance().getConnection());
////			String sql = "SELECT CAL_NUM FROM calendar where CAL_DATE LIKE ? AND GROUP_ID = ? AND CAL_MEMO = ?";
//			String sql = "SELECT CAL_NUM FROM CALENDAR WHERE CAL_DATE LIKE ? AND GROUP_ID = ? AND CAL_MEMO = ?";
//			psmt = con.prepareStatement(sql);
//			psmt.setString(1, "%" + first_cal + "%");
//			psmt.setInt(2, group_id);
//			psmt.setString(3, cal_memo);
//
//			rs = psmt.executeQuery();
//
//			int cal_num = 0;
//			while (rs.next()) {
//				cal_num = rs.getInt("CAL_NUM");
//			}
//
////			String sql2 = "insert into calendar VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?)";
//			String sql2 = "INSERT INTO CALENDAR VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?)";
//			psmt2 = con.prepareStatement(sql2);
//
//			psmt2.setInt(1, getLastCalNum());
//			// System.out.println(psmt2.isClosed());
//			psmt2.setString(2, "");
//			// System.out.println(psmt2.isClosed());
//			psmt2.setInt(3, group_id);
//			psmt2.setString(4, new_memo);
//			psmt2.setString(5, cal_writer);
//			psmt2.setString(6, " ");
//			psmt2.setString(7, " ");
//			psmt2.setInt(8, cal_num);
//			psmt2.setInt(9, 1);
//
//			psmt2.executeUpdate();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//
////			try {
////				if (rs != null)
////					rs.close();
////				if (psmt != null)
////					psmt.close();
////				if (psmt2 != null)
////					psmt2.close();
////				if (con != null)
////					con.close();
////			} catch (SQLException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//
//			if (con != null)
//	        {
//	        	try
//	        	{
//	        		con.close();
//	        	}
//	        	catch (SQLException ex) {
//					ex.printStackTrace();
//	    		}
//	        }
//		}
//
//	}

//	public void addCalComment(int groupId, String userId, int calNum, String memo)
	public void addCalComment(int groupId, String userId, int calNum, String memo)
	{
		Connection con = null;
		PreparedStatement psmt2 = null;

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());

//			String sql2 = "insert into calendar VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?)";
			String sql2 = "INSERT INTO CALENDAR VALUES(?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?)";
			psmt2 = con.prepareStatement(sql2);

			psmt2.setInt(1, getLastCalNum());
			// System.out.println(psmt2.isClosed());
			psmt2.setString(2, "");
			// System.out.println(psmt2.isClosed());
			psmt2.setInt(3, groupId);
			psmt2.setString(4, memo);
			psmt2.setString(5, userId);
			psmt2.setString(6, " ");
			psmt2.setString(7, " ");
			psmt2.setInt(8, calNum);
			psmt2.setInt(9, 1);

			psmt2.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

//			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
//				if (psmt2 != null)
//					psmt2.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			if (con != null)
	        {
	        	try
	        	{
	        		con.close();
	        	}
	        	catch (SQLException ex) {
					ex.printStackTrace();
	    		}
	        }
		}
	}

	// �뜝�룞�삕�몴 �뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�듃
	public void updateMemberChoice(String calNum, String voteList, String user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
//		ArrayList<CalendarVO> arrList = new ArrayList<CalendarVO>();
		String memberChoice = "";

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
//			String sql = "select MEMBER_CHOICE from CALENDAR where CAL_NUM=?";
			String sql = "SELECT MEMBER_CHOICE FROM CALENDAR WHERE CAL_NUM=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(calNum));

			rSet = pstmt.executeQuery();

//			String memberChoice = "";

			while (rSet.next()) {
				// System.out.println(rSet.getString(1));
				memberChoice = rSet.getString(1);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

//			try {
//				if (rs != null)
//					rs.close();
//				if (psmt != null)
//					psmt.close();
//				if (psmt2 != null)
//					psmt2.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			if (con != null)
	        {
	        	try
	        	{
	        		con.close();
	        	}
	        	catch (SQLException ex) {
					ex.printStackTrace();
	    		}
	        }
		}

		TreeSet<String> memberSet = new TreeSet<String>();

		if (memberChoice.contains(",")) {
			String[] result = memberChoice.split(",");
			for (String string : result) {
				// System.out.println(string);
				if (string.split("-")[1].equals(user)) {
//					System.out.println("string : " + string + ", user : " + user);
					// memberSet.add(string);
				} else {
					memberSet.add(string);
				}
			}
		} else {
			// System.out.println(memberChoice);
			// if(memberChoice.contains("-"))
			// {
			// memberSet.add(memberChoice.split("-")[1]);
			// }
			// else if(memberChoice.contains("-") &&
			// memberChoice.split("-")[0].equals("00000000"))
			// {
			// System.out.println(memberChoice);
			// memberSet.add(memberChoice);
			// }
			memberSet.add(memberChoice);
			// System.out.println(memberChoice);
		}
		String[] result = voteList.split(",");
		for (String string : result) {
			// System.out.println(string);
			memberSet.add(string);
		}
		String updateList = "";
		for (String string : memberSet) {
			// System.out.println(string);
			updateList += string + ",";
		}
		updateList = updateList.substring(0, updateList.length() - 1);
		// System.out.println(updateList);

		try
		{
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());

			String updateSql = "UPDATE CALENDAR SET MEMBER_CHOICE=? WHERE CAL_NUM=?";
			// UPDATE calendar SET MEMBER_CHOICE='hi' WHERE CAL_NUM=5

			pstmt = con.prepareStatement(updateSql);
			pstmt.setString(1, updateList);
			pstmt.setInt(2, Integer.parseInt(calNum));

			if (pstmt.executeUpdate() == 1) {
//				System.out.println("�뜝�룞�삕�뜝�룞�삕 : " + updateList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rSet != null)
//					rSet.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try
        	{
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
	}

	// �뜝�룞�삕�뜝�룞�삕 �솗�뜝�룞�삕�뜝�떦�뙋�삕 �뜝�뙃�눦�삕
	public void scheduleSelected(String calNum, String calDate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rSet = null;
//		ArrayList<CalendarVO> arrList = new ArrayList<CalendarVO>();
		String memberChoice = "";

		try
		{
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
//			String sql = "select MEMBER_CHOICE from CALENDAR where CAL_NUM=?";
			String sql = "SELECT MEMBER_CHOICE FROM CALENDAR WHERE CAL_NUM=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(calNum));

			rSet = pstmt.executeQuery();

//			String memberChoice = "";

			while (rSet.next()) {
				// System.out.println(rSet.getString(1));
				memberChoice = rSet.getString(1);
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
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}

		TreeSet<String> memberSet = new TreeSet<String>();
		
		if (memberChoice.contains(",")) {
			String[] result = memberChoice.split(",");
			for (String string : result) {
				// System.out.println(string);
				if (string.split("-")[0].equals(calDate)) {
					// System.out.println("string : " + string + ", calDate
					// : " + calDate);
					// memberSet.add(string);
					memberSet.add(string.split("-")[1]);
				} else if (string.split("-")[0].equals("00000000")) {
					// System.out.println(string);
					memberSet.add(string);
				} else {
					// memberSet.add(string);
					System.out.println(string);
				}
			}
		} else {
			memberSet.add(memberChoice);
			// System.out.println(memberChoice);
		}
		// String[] result = voteList.split(",");
		// for (String string : result)
		// {
		//// System.out.println(string);
		// memberSet.add(string);
		// }
		//
		String updateList = "";
		for (String string : memberSet) {
			// System.out.println(string);
			updateList += string + ",";
		}
		updateList = updateList.substring(0, updateList.length() - 1);
//		System.out.println(updateList);

		try
		{
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			
			String updateSql = "UPDATE CALENDAR SET MEMBER_CHOICE=?, CAL_DATE=? WHERE CAL_NUM=?";
			// UPDATE calendar SET MEMBER_CHOICE='hi' WHERE CAL_NUM=5

			pstmt = con.prepareStatement(updateSql);
			pstmt.setString(1, updateList);
			pstmt.setString(2, calDate);
			pstmt.setInt(3, Integer.parseInt(calNum));

			if (pstmt.executeUpdate() == 1) {
//				System.out.println("�뜝�룞�삕�뜝�룞�삕 : " + updateList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rSet != null)
//					rSet.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try
        	{
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
	}

	public ArrayList<CalendarVO> groupCalInfo(ArrayList<String> groupIdArr) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CalendarVO> myCalGatherList = new ArrayList<CalendarVO>();
		String sql = "SELECT * FROM CALENDAR WHERE GROUP_ID=?";
		// String sql = "SELECT * FROM CALENDAR WHERE GROUP_ID=?";

		for (int i = 1; i < groupIdArr.size(); i++) {
//			sql += " or GROUP_ID=? ";
			sql += " OR GROUP_ID=? ";
		}

//		sql += "order by CAL_DATE desc limit 8";
		sql += "ORDER BY CAL_DATE DESC LIMIT 8";

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, groupIdArr.get(0));

			for (int i = 1; i < groupIdArr.size(); i++) {
				pstmt.setString(i + 1, groupIdArr.get(i));
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				CalendarVO cv = new CalendarVO();
				cv.setCal_num(rs.getInt("CAL_NUM"));
				// cv.setCal_time(rs.getTimestamp("CAL_TIME"));
				Date date = rs.getTimestamp("CAL_TIME");
				Timestamp timestamp = new Timestamp(date.getTime());
//				System.out.println(timestamp.getTime());
				cv.setCal_time(timestamp);
				
				
				cv.setCal_date(rs.getString("CAL_DATE"));
				cv.setGroup_id(rs.getInt("GROUP_ID"));
				cv.setCal_memo(rs.getString("CAL_MEMO"));
				cv.setCal_writer(rs.getString("CAL_WRITER"));
				cv.setState_icon(rs.getString("STATE_ICON"));
				cv.setMember_choice(rs.getString("MEMBER_CHOICE"));
				cv.setCal_reference(rs.getInt("CAL_REFERENCE"));
				cv.setCal_depth(rs.getInt("CAL_DEPTH"));

				myCalGatherList.add(cv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try
        	{
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
		return myCalGatherList;
	}

	// 諛⑹옣 �쟾�솚 : �돩肉�,,, �씠嫄�,,, �븘�땲�떎,,, 吏꾩쭨,,, �걫李랁븯�꽕
	public void changeGM(int groupId, String before, String after) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<CalendarVO> calList = new ArrayList<CalendarVO>();

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
//			String sql = "select CAL_NUM, CAL_DATE, MEMBER_CHOICE FROM CALENDAR WHERE GROUP_ID=? and CAL_DEPTH=0";
			String sql = "SELECT CAL_NUM, CAL_DATE, MEMBER_CHOICE FROM CALENDAR WHERE GROUP_ID=? AND CAL_DEPTH=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, groupId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				CalendarVO calendarVO = new CalendarVO();
				
				calendarVO.setCal_num(rs.getInt(1));
				calendarVO.setCal_date(rs.getString(2));
				calendarVO.setMember_choice(rs.getString(3));
				
				calList.add(calendarVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try
        	{
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
		
		for(int i = 0; i < calList.size(); i++)
		{
			boolean voted = true;
			
			CalendarVO calendarVO = calList.get(i);
			String memberChoice = calendarVO.getMember_choice();
			
			if(!memberChoice.contains(","))
			{
//				System.out.println(memberChoice);
				return;
			}
			
			TreeSet<String> list = new TreeSet<String>();
			String[] memSplit = memberChoice.split(",");
				
			for(int j = 0; j < memSplit.length; j++)
			{
				list.add(memSplit[j]);
				
				if(memSplit[j].contains("-") && !memSplit[j].split("-")[0].equals("00000000"))
				{
					voted = false;
				}
			}
				
			if(!voted)
			{
				if(calendarVO.getCal_date().contains(","))
				{
					String[] dateList = calendarVO.getCal_date().split(",");
					
					for(int k = 0; k < dateList.length; k++)
					{
						list.add(dateList[k] + "-" + after);
					}
				}
				else
				{
					list.add(calendarVO.getCal_date() + "-" + after);
				}
			}
			
			String memChoice = "";
			
			for (String string : list) {
				if(string.contains("-") && string.equals("00000000-" + after))
				{
					
				}
				else
				{
					memChoice += string + ",";
				}
			}
			
			memChoice = memChoice.substring(0, memChoice.length() -1);
			
			calendarVO.setMember_choice(memChoice);
			
			calList.set(i, calendarVO);
		}
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			for(int i = 0; i < calList.size(); i++)
			{
				String sql = "UPDATE CALENDAR SET MEMBER_CHOICE=? WHERE CAL_NUM=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, calList.get(i).getMember_choice());
				pstmt.setInt(2, calList.get(i).getCal_num());
				
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try
        	{
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
	}

	// 방나가기 처리
	public void setRoomOut(int groupId, String userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<CalendarVO> calList = new ArrayList<CalendarVO>();

		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
//			String sql = "select CAL_NUM, CAL_DATE, MEMBER_CHOICE FROM CALENDAR WHERE GROUP_ID=? and CAL_DEPTH=0";
			String sql = "SELECT CAL_NUM, CAL_DATE, MEMBER_CHOICE FROM CALENDAR WHERE GROUP_ID=? AND CAL_DEPTH=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, groupId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				CalendarVO calendarVO = new CalendarVO();
				
				calendarVO.setCal_num(rs.getInt(1));
				calendarVO.setCal_date(rs.getString(2));
				calendarVO.setMember_choice(rs.getString(3));
				
				calList.add(calendarVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try
        	{
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
		
		for(int i = 0; i < calList.size(); i++)
		{
			boolean voted = true;
			
			CalendarVO calendarVO = calList.get(i);
			String memberChoice = calendarVO.getMember_choice();
			
			if(!memberChoice.contains(","))
			{
				System.out.println(memberChoice);
				return;
			}
			
			TreeSet<String> list = new TreeSet<String>();
			String[] memSplit = memberChoice.split(",");
				
			for(int j = 0; j < memSplit.length; j++)
			{
				list.add(memSplit[j]);
				
				if(memSplit[j].contains("-") && !memSplit[j].split("-")[0].equals("00000000"))
				{
					voted = false;
				}
			}
				
			if(!voted)
			{
				if(calendarVO.getCal_date().contains(","))
				{
					String[] dateList = calendarVO.getCal_date().split(",");
					
					for(int k = 0; k < dateList.length; k++)
					{
						list.add(dateList[k] + "-" + userId);
					}
				}
				else
				{
					list.add(calendarVO.getCal_date() + "-" + userId);
				}
			}
			
			String memChoice = "";
			
			for (String string : list) {
//				if(string.contains("-") && string.equals("00000000-" + userId))
				if(string.contains("-") && string.split("-")[1].equals(userId))
				{
					
				}
				else
				{
					memChoice += string + ",";
				}
			}
			
			memChoice = memChoice.substring(0, memChoice.length() -1);
			
			calendarVO.setMember_choice(memChoice);
			
			calList.set(i, calendarVO);
		}
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			for(int i = 0; i < calList.size(); i++)
			{
				String sql = "UPDATE CALENDAR SET MEMBER_CHOICE=? WHERE CAL_NUM=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, calList.get(i).getMember_choice());
				pstmt.setInt(2, calList.get(i).getCal_num());
				
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try
        	{
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
	}

	public void delCal(int groupId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "DELETE FROM CALENDAR WHERE GROUP_ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, groupId);

			int result = pstmt.executeUpdate();
			
			if(result == 1)
			{
//				System.out.println("calendar에서 groupId가 " + groupId + "인 row 삭제");
			}
			else
			{
//				System.out.println("삭제 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try
        	{
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
	}

	public void delCal2(int calNum)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
//		ResultSet rs = null;
		
		
		try {
//			con = getConnection();
			con = new MVConnection(DBPool.getInstance().getConnection());
			String sql = "DELETE FROM CALENDAR WHERE CAL_REFERENCE=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, calNum);

			int result = pstmt.executeUpdate();
			
			if(result == 1)
			{
//				System.out.println("calendar에서 groupId가 " + groupId + "인 row 삭제");
			}
			else
			{
//				System.out.println("삭제 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			try
        	{
        		con.close();
        	}
        	catch (SQLException ex) {
				ex.printStackTrace();
    		}
		}
	}

}
