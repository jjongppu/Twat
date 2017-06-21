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

import com.twat.dto.CalendarVO;
import com.twat.dto.CalgatherVO;
import com.twat.dto.MemberVO;
import com.twat.dto.QnaVO;

	

public class AdminDAO {
	Connection con = null;

	
	
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
			PreparedStatement psmt= null;
			ResultSet rs= null;
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
		
		
		// 쫑길빵길의 어드민 홈 db정보 뿌려주기
		public ArrayList<Integer> getAllInfo(){
			PreparedStatement psmt= null;
			ResultSet rs= null;
			ArrayList<Integer> info = new ArrayList<Integer>();
			
			String selectGetInfo = "SELECT"; 
			selectGetInfo +="(SELECT COUNT(*) FROM member) as memC,";
			selectGetInfo +="(SELECT COUNT(*) FROM qna) as qnaC,";
			selectGetInfo +="(SELECT COUNT(*) FROM calendar where CAL_DEPTH=0) as calC,";
			selectGetInfo +="(SELECT VISIT_COUNT FROM visit where VISIT_KIND='TOTAL') as visC,";
			selectGetInfo +="(SELECT COUNT(*) FROM calgather) as calgC";	
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectGetInfo);
				rs = psmt.executeQuery();
				if(rs.next()) {
					info.add(rs.getInt("memC"));
					info.add(rs.getInt("qnaC"));
					info.add(rs.getInt("calC"));
					info.add(rs.getInt("visC"));
					info.add(rs.getInt("calgC"));
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
		
		
		
		// 어드민 페이지 현재 정상 로그인 되는 회원들만 얻어옴 + 검색
		public ArrayList<MemberVO> adminlogin(int page, String val){
			PreparedStatement psmt= null;
			ResultSet rs= null;
			ArrayList<MemberVO> members = new ArrayList<MemberVO>();
			
			String selectGetAllMemeber = "SELECT * FROM MEMBER WHERE OUT_TIME=0 ";
			if(!val.equals("0")){
				selectGetAllMemeber+= "AND MEMBER_ID LIKE ? ";
			}
			selectGetAllMemeber += "LIMIT "+ (page*10-10) +",10";
			
			String selectMemberCount = "SELECT COUNT(*) FROM MEMBER WHERE OUT_TIME=0";
			if(!val.equals("0")){
				selectMemberCount+= " AND MEMBER_ID LIKE ?";
			}
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectMemberCount);
				if(!val.equals("0")){
					psmt.setString(1, val+"%");
				}
				rs = psmt.executeQuery();
				if(rs.next()) {
					String count = rs.getInt("COUNT(*)")+"";
					MemberVO countMem = new MemberVO();
					countMem.setMEMBER_ID(count);
					members.add(countMem);
				}
				
				psmt = con.prepareStatement(selectGetAllMemeber);
				if(!val.equals("0")){
					psmt.setString(1, val+"%");
				}
				rs = psmt.executeQuery();
				while(rs.next()){
					MemberVO Mem = new MemberVO();
					Mem.setMEMBER_ID(rs.getString("MEMBER_ID"));
					Mem.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
					Mem.setMEMBER_PHONE(rs.getString("MEMBER_PHONE"));
					Mem.setMEMBER_IMG(rs.getString("MEMBER_IMG"));
					Mem.setMEMBER_GENDER(rs.getString("MEMBER_GENDER"));
					Mem.setMEMBER_BIRTH(rs.getString("MEMBER_BIRTH"));
					Mem.setSTART_DATE(rs.getTimestamp("START_DATE"));
					members.add(Mem);
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
			return members;
		}
		
		
		
		
		// 그룹리스트 다뽑아옴 ㅋㅋ val이 0이면 select* 검색값이 있으면 방이름으로 라이크검색
		public ArrayList<CalgatherVO> getGroupList(int page, String val){
			PreparedStatement psmt= null;
			ResultSet rs= null;
			ArrayList<CalgatherVO> calArry = new ArrayList<CalgatherVO>();	
			
			
			// 그릅수 얻어옴
			String selectGroupCount = "SELECT COUNT(*) FROM CALGATHER";
			if(!val.equals("0")){
				selectGroupCount+= " WHERE GROUP_NAME LIKE ?";
			}
			
			String selectAllgroupSql = "SELECT * FROM CALGATHER";
			if(!val.equals("0")){
				selectAllgroupSql+= " WHERE GROUP_NAME LIKE ?";
			}
			selectAllgroupSql +=  " LIMIT "+ (page*10-10) +",10";
			
			try{
				con = getConnection();
				
				psmt = con.prepareStatement(selectGroupCount);
				if(!val.equals("0")){
					psmt.setString(1, val+"%");
				}
				rs = psmt.executeQuery();
				if(rs.next()) {
					int count = rs.getInt("COUNT(*)");
					CalgatherVO cv = new CalgatherVO();
					cv.setGroup_id(count);
					calArry.add(cv);
				}
				
				psmt = con.prepareStatement(selectAllgroupSql);
				if(!val.equals("0")){
					psmt.setString(1,val+"%");
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
		
		
		
		// 게시글 싸그리 다뽑아옴 ㅋㅋ val이 0이면 select* 검색값이 있으면 방이름으로 라이크검색
		public ArrayList<CalendarVO> getCalenarList(int page, String val){
			PreparedStatement psmt= null;
			ResultSet rs= null;
			ArrayList<CalendarVO> calArry = new ArrayList<CalendarVO>();	
			
			
			// 그릅수 얻어옴
			String selectGroupCount = "SELECT COUNT(*) FROM CALENDAR WHERE CAL_DEPTH=0";
			if(!val.equals("0")){
				selectGroupCount+= " AND CAL_MEMO LIKE ?";
			}
			
			String selectAllgroupSql = "SELECT * FROM CALENDAR WHERE CAL_DEPTH=0";
			if(!val.equals("0")){
				selectAllgroupSql+= " AND CAL_MEMO LIKE ?";
			}
			selectAllgroupSql +=  " LIMIT "+ (page*10-10) +",10";
			
			try{
				con = getConnection();
				
				psmt = con.prepareStatement(selectGroupCount);
				if(!val.equals("0")){
					psmt.setString(1, "%"+val+"%");
				}
				rs = psmt.executeQuery();
				if(rs.next()) {
					int count = rs.getInt("COUNT(*)");
					CalendarVO cv = new CalendarVO();
					cv.setGroup_id(count);
					calArry.add(cv);
				}
				
				psmt = con.prepareStatement(selectAllgroupSql);
				if(!val.equals("0")){
					psmt.setString(1,"%"+val+"%");
				}
				rs = psmt.executeQuery();
					
				while(rs.next()){
					CalendarVO cv = new CalendarVO();
					cv.setCal_num(rs.getInt("CAL_NUM"));
					cv.setCal_time(rs.getTimestamp("CAL_TIME"));
					cv.setCal_date(rs.getString("CAL_DATE"));
					cv.setGroup_id(rs.getInt("GROUP_ID"));
					cv.setCal_memo(rs.getString("CAL_MEMO"));
					cv.setCal_writer(rs.getString("CAL_WRITER"));
					cv.setState_icon(rs.getString("STATE_ICON"));
					cv.setMember_choice(rs.getString("MEMBER_CHOICE"));
					cv.setCal_reference(rs.getInt("CAL_REFERENCE"));
					cv.setCal_depth(rs.getInt("CAL_DEPTH"));
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
		
		
		
		
		
		// 문의글 싸그리 다뽑아옴 ㅋㅋ val이 0이면 select* 검색값이 있으면 방이름으로 라이크검색
		public ArrayList<QnaVO> getQnaList(int page, String val){
			PreparedStatement psmt= null;
			ResultSet rs= null;
			ArrayList<QnaVO> qnaArry = new ArrayList<QnaVO>();	
			
			
			// 그릅수 얻어옴
			String selectGroupCount = "SELECT COUNT(*) FROM QNA";
			if(!val.equals("0")){
				selectGroupCount+= " WHERE QNA_CONTENTS LIKE ?";
			}
			selectGroupCount+= " ORDER BY QNA_REPLY asc";
			
			//실질적인 내용
			String selectAllgroupSql = "SELECT * FROM QNA";
			if(!val.equals("0")){
				selectAllgroupSql+= " WHERE QNA_CONTENTS LIKE ?";
			}
			selectAllgroupSql +=  " ORDER BY QNA_REPLY asc LIMIT "+ (page*10-10) +",10";
			
			try{
				con = getConnection();
				
				psmt = con.prepareStatement(selectGroupCount);
				if(!val.equals("0")){
					psmt.setString(1, "%"+val+"%");
				}
				rs = psmt.executeQuery();
				if(rs.next()) {
					int count = rs.getInt("COUNT(*)");
					QnaVO qv = new QnaVO();
					qv.setQNA_ID(count);
					qnaArry.add(qv);
				}
				
				psmt = con.prepareStatement(selectAllgroupSql);
				if(!val.equals("0")){
					psmt.setString(1,"%"+val+"%");
				}
				rs = psmt.executeQuery();
					
				while(rs.next()){
					QnaVO qv = new QnaVO();
					qv.setQNA_ID(rs.getInt("QNA_ID"));
					qv.setMEMBER_ID(rs.getString("MEMBER_ID"));
					qv.setQNA_CATEGORY(rs.getString("QNA_CATEGORY"));
					qv.setQNA_PW(rs.getInt("QNA_PW"));
					qv.setQNA_TITLE(rs.getString("QNA_TITLE"));
					qv.setQNA_CONTENTS(rs.getString("QNA_CONTENTS"));
					qv.setQNA_DATE(rs.getTimestamp("QNA_DATE"));
					qv.setQNA_REPLY(rs.getString("QNA_REPLY"));
					qnaArry.add(qv);
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
			
			return qnaArry;
			
		}
		
		
}