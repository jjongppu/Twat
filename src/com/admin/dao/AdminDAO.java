package com.admin.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.servlet.http.HttpSession;
//import javax.sql.DataSource;

import com.twat.dbpool.DBPool;
import com.twat.dto.CalendarVO;
import com.twat.dto.CalgatherVO;
import com.twat.dto.MemberVO;
import com.twat.dto.QnaVO;
import com.twat.dto.VisitVO;
import com.twat.mvconnection.MVConnection;

	

public class AdminDAO {
//	Connection con = null;

	
	
	// MemberDAO 의 싱글톤 -----------------------------------
	private static AdminDAO instance = new AdminDAO();
	
	private AdminDAO(){}
	
	public static AdminDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	   // DB연결을 위해 con을 반환하는 메서드 --------------------------------------------
//	   public Connection getConnection() throws Exception {
//	         Context initCtx = new InitialContext();
//	         DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
//	         
//	      return ds.getConnection();
//	   }


		// 관리자 로그인을 위한 메서드 ----------------------------------쫑길빵길
		public String adminlogin(String MEMBER_ID, String MEMBER_PW) {
			Connection con = null;
			PreparedStatement psmt= null;
			ResultSet rs= null;
			String result = "";
			
			String selectSql = "select * from ADMIN where ADMIN_ID=? and ADMIN_PW=?";
			
			try {
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
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
			Connection con = null;
			PreparedStatement psmt= null;
			ResultSet rs= null;
			ArrayList<Integer> info = new ArrayList<Integer>();
			
			String selectGetInfo = "SELECT"; 
			selectGetInfo +="(SELECT COUNT(*) FROM MEMBER) AS memC,";
			selectGetInfo +="(SELECT COUNT(*) FROM QNA) AS qnaC,";
			selectGetInfo +="(SELECT COUNT(*) FROM CALENDAR WHERE CAL_DEPTH=0) AS calC,";
			selectGetInfo +="(SELECT SUM(VISIT_COUNT) FROM VISIT) AS visC,";
			selectGetInfo +="(SELECT COUNT(*) FROM CALGATHER) AS calgC";	
			
			try {
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
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
			Connection con = null;
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
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
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
				
				if(psmt != null)
					psmt.close();
				
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
			Connection con = null;
			
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
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
				
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
				
				if(rs != null)
					rs.close();
				if(psmt != null)
					psmt.close();
				
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
			Connection con = null;
			
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
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
				
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
				
				if(rs != null)
					rs.close();
				if(psmt != null)
					psmt.close();
				
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
			Connection con = null;
			
			// 그릅수 얻어옴
			String selectGroupCount = "SELECT COUNT(*) FROM QNA";
			if(!val.equals("0")){
				selectGroupCount+= " WHERE QNA_CONTENTS LIKE ?";
			}
			selectGroupCount+= " ORDER BY QNA_REPLY ASC";
			
			//실질적인 내용
			String selectAllgroupSql = "SELECT * FROM QNA";
			if(!val.equals("0")){
				selectAllgroupSql+= " WHERE QNA_CONTENTS LIKE ?";
			}
			selectAllgroupSql +=  " ORDER BY QNA_REPLY ASC LIMIT "+ (page*10-10) +",10";
			
			try{
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
				
				psmt = con.prepareStatement(selectGroupCount);
				if(!val.equals("0")) {
					psmt.setString(1, "%"+val+"%");
				}
				rs = psmt.executeQuery();
				if(rs.next()) {
					int count = rs.getInt("COUNT(*)");
					QnaVO qv = new QnaVO();
					qv.setQNA_ID(count);
					qnaArry.add(qv);
				}
				
				if(rs != null)
					rs.close();
				if(psmt != null)
					psmt.close();
				
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
		
		// kind 가 1이면 게시글,댓글,그룹 삭제 2면 댓글,게시글 삭제,
		   public int outGroup(String group_ID, int kind){
				PreparedStatement psmt= null;
				Connection con = null;
				ResultSet rs= null;
				//그룸삭제 쿼리
			   String deleteGroupSql = "DELETE FROM CALGATHER WHERE GROUP_ID =?";
			   //게시글 댓글 삭제 쿼리
			   String deleteContentSql = "DELETE FROM CALENDAR WHERE GROUP_ID =?";
			   // 정규화테이블 삭제 쿼리
			   String deleteJoinMemSql = "DELETE FROM MEMBER_JOIN_GROUP WHERE GROUP_ID =?";
			   int result = 0;
			   
			   try {
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
				//게시글 댓글 삭제
				psmt = con.prepareStatement(deleteContentSql);
				psmt.setString(1, group_ID);
				psmt.executeUpdate();
				
				if(psmt!=null)
					psmt.close();
				
				//정규화 테이블 삭제
				result = 1;
				if( kind ==1){
					psmt = con.prepareStatement(deleteJoinMemSql);
					psmt.setString(1, group_ID);
					psmt.executeUpdate();
					
				// 그룹도 삭제 하기
					result = 2;
					psmt = con.prepareStatement(deleteGroupSql);
					psmt.setString(1, group_ID);
					int res3 = psmt.executeUpdate();
					
					if(res3 == 1){
						result = 3;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				e.printStackTrace();
			}finally {
	            try {
	                if(rs != null) rs.close();
	                if(psmt != null) psmt.close();
	                if(con != null) con.close();
	             } catch (SQLException e) {
	            	 e.printStackTrace();
	             }
	       }
			   
			   return result;
		   }
		
			// 관리자 답변달아주기 위한 메서드 ----------------------------------쫑길빵길
			public int setQnaReply(String qna_id, String reply) {
				PreparedStatement psmt= null;
				Connection con = null;
				String updateReplySql = "UPDATE QNA SET QNA_REPLY=? WHERE QNA_ID=?";
				int result =0;
				try {
//					con = getConnection();
					con = new MVConnection(DBPool.getInstance().getConnection());
					psmt = con.prepareStatement(updateReplySql);
					psmt.setString(1, reply);
					psmt.setString(2, qna_id);
					int res = psmt.executeUpdate();
					
					if(res == 1) {
						result = 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
						try {
							if(psmt != null) psmt.close();
							if(con != null) con.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					
				}
				return result;
			}
			
			
			// 방문자 수 얻어오기 업데이트 한방에 해결 ----------------------------------쫑길빵길 -- 잠시 묻음..
//						public int setGetVisit(int count, String state) {
//							PreparedStatement psmt= null;
//							ResultSet rs= null;
//							
//							String updateVisit = "UPDATE VISIT SET VISIT_COUNT=?";
//							String selectVisit = "SELECT SUM(VISIT_COUNT) FROM visit";
//							int result =0;
//							try {
//								con = getConnection();
//								if(state.equals("end")){
//									psmt = con.prepareStatement(updateVisit);
//									psmt.setInt(1, count);
//									psmt.executeUpdate();
//								}else{
//									psmt = con.prepareStatement(selectVisit);
//									rs = psmt.executeQuery();
//									if(rs.next()){
//										result = rs.getInt("VISIT_COUNT");
//									}
//								}
//								
//							} catch (Exception e) {
//								e.printStackTrace();
//							} finally {
//									try {
//										if(rs != null) rs.close();
//										if(psmt != null) psmt.close();
//										if(con != null) con.close();
//									} catch (SQLException e) {
//										e.printStackTrace();
//									}
//								
//							}
//							return result;
//						}
						
						
						
						
						

						public boolean visitups(String userturn, String userid, String strToday) {
							Connection con = null;
							PreparedStatement psmt= null;
							ResultSet rs= null;
							boolean result = false;
							String updateVisit = "UPDATE VISIT SET VISIT_COUNT=VISIT_COUNT+1 WHERE VISIT_KIND=?";
							//개인 방문 기록
							String updateVisituser = "INSERT INTO VISIT_MEMBER VALUES(?,CURRENT_TIMESTAMP)";
							// 쿠키!
							String selectVisit = "SELECT * FROM VISIT ORDER BY VISIT_KIND DESC LIMIT 1";
							String insertVisitToday = "INSERT INTO VISIT VALUES(?,1)";
							try {
//								con = getConnection();
								con = new MVConnection(DBPool.getInstance().getConnection());
								if(!userturn.equals("getCookie")){
									psmt = con.prepareStatement(selectVisit);
									rs = psmt.executeQuery();
									
									
									if(rs.next()){
		
										String date = rs.getString("VISIT_KIND");
										if(psmt != null)
											psmt.close();
										if(!date.equals(strToday)){
											psmt = con.prepareStatement(insertVisitToday);
											psmt.setString(1,strToday);
											psmt.executeUpdate();
										}else{
											psmt = con.prepareStatement(updateVisit);
											psmt.setString(1, strToday);
											psmt.executeUpdate();
										}
									}
								}
								
								
								psmt = con.prepareStatement(updateVisituser);
								psmt.setString(1, userid);
								psmt.executeUpdate();
								result = true;
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
								
							}
							return result;
						}

		public ArrayList<VisitVO> getVisits() {
			Connection con = null;
			ArrayList<VisitVO> array = new ArrayList<VisitVO>();
			PreparedStatement psmt= null;
			ResultSet rs= null;
			
			String selectVisit1 = "SELECT * FROM VISIT ORDER BY VISIT_KIND ASC LIMIT 10";
//			String selectVisit2 = "SELECT SUM(VISIT_COUNT) FROM VISIT";
			try {
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
				
//					psmt = con.prepareStatement(selectVisit2);
//					rs = psmt.executeQuery();
//					
//					if(rs.next()){
//						VisitVO vv = new VisitVO();
//						vv.setVISIT_KIND(rs.getString("SUM(VISIT_COUNT)"));
//						array.add(vv);
//					}
					
					psmt = con.prepareStatement(selectVisit1);
					rs = psmt.executeQuery();
					
					while(rs.next()){
						VisitVO vv = new VisitVO();
						vv.setVISIT_KIND(rs.getString("VISIT_KIND"));
						vv.setVISIT_COUNT(rs.getInt("VISIT_COUNT"));
						array.add(vv);
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
				
			}
			return array;
		}
			
			
		public Long getAndSetNoticeView(Long views) {
			PreparedStatement psmt= null;
			Connection con = null;
			ResultSet rs= null;
			Long result = null;
			String selectVisit1 = "SELECT * FROM NOTICE_BOARD WHERE NOTICE_ID=?";
			String updateVisit2 = "UPDATE NOTICE_BOARD SET NOTICE_VIEWS=NOTICE_VIEWS+1 WHERE NOTICE_ID=?";
			try {
//				con = getConnection();
				con = new MVConnection(DBPool.getInstance().getConnection());
				
					psmt = con.prepareStatement(updateVisit2);
					psmt.setLong(1, views);
					psmt.executeUpdate();
					
					if(psmt != null) psmt.close();
					
					psmt = con.prepareStatement(selectVisit1);
					psmt.setLong(1, views);
					rs = psmt.executeQuery();
					
					while(rs.next()){
						
						result = rs.getLong("NOTICE_VIEWS");
						
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
				
			}
			return result;
		}	
			
			
		
}