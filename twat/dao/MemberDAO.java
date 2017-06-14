package com.twat.dao;
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

import com.twat.dto.MemberVO;

	

public class MemberDAO {
	Connection con = null;
	PreparedStatement psmt= null;
	ResultSet rs= null;
	
	
	// MemberDAO 의 싱글톤 -----------------------------------
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO(){}
	
	public static MemberDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	   // DB연결을 위해 con을 반환하는 메서드 --------------------------------------------
	   public Connection getConnection() throws Exception {
	         Context initCtx = new InitialContext();
	         DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/aclass0201");      
	         
	      return ds.getConnection();
	   }

	
	
	  // 아이디 찾는 메서드 (이름,전화번호로 찾기)-----승우-------------------------
	   public String searchID(String MEMBER_NAME, String MEMBER_PHONE) {
	      
	      String selectSql = "select MEMBER_ID from MEMBER where MEMBER_NAME=? and MEMBER_PHONE=?";
	      String getID = ""; 
	      
	      try {
	         con = getConnection();
	         psmt = con.prepareStatement(selectSql);
	         psmt.setString(1, MEMBER_NAME);
	         psmt.setString(2, MEMBER_PHONE);
	         
	         rs = psmt.executeQuery();
	         
	         while(rs.next()) {
	            getID = rs.getString(1);
	         }
	         
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } finally {
	         try {
	            if(rs != null)   rs.close();
	            if(psmt != null) psmt.close();
	            if(con != null) con.close();
	         } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	      
	   }
	      return getID;
	   }
	   
	   
	   
// 비밀번호 찾는 메서드 ---------승우-------------------------------
	   public String searchPW(String MEMBER_ID, String MEMBER_NAME, String MEMBER_PHONE) {
		   String selectSql = "select MEMBER_PW from MEMBER where MEMBER_ID=? and MEMBER_NAME=? and MEMBER_PHONE=?";
		   String getPW = "";
		   
		   try {
			con = getConnection();
			psmt = con.prepareStatement(selectSql);
			psmt.setString(1, MEMBER_ID);
			psmt.setString(2, MEMBER_NAME);
			psmt.setString(3, MEMBER_PHONE);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				getPW = rs.getString(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	         try {
		            if(rs != null)   rs.close();
		            if(psmt != null) psmt.close();
		            if(con != null) con.close();
		         } catch (SQLException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		         }
		      
		   }
		   return getPW;
	   }	   
	   

		// 회원 로그인을 위한 메서드 ----------------------------------
		public int loginMember(String MEMBER_ID, String MEMBER_PW) {
			int result = -1;
			
			
			String selectSql = "select * from MEMBER where MEMBER_ID = ? and MEMBER_PW = ?";
			
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				psmt.setString(1, MEMBER_ID);
				psmt.setString(2, MEMBER_PW);
				System.out.println("!");
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					// 로그인 성공
					result = 1;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
					try {
						if(rs != null)
						if(psmt != null) psmt.close();
						if(con != null) con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			return result;
		}
	   
	   
	   
	// 로그인 한사람의 그룹 얻어오기 쫑기리
			public ArrayList<Integer> getMyGroupList(String MEMBER_ID) {
				ArrayList<Integer> glList = new ArrayList<Integer>();
				
				
				try {
					con = getConnection();
					
					// 아이디로 그룹목록 불러오기
					String sql = "select GROUP_ID from MEMBER_JOIN_GROUP where MEMBER_ID =?";
					
					psmt = con.prepareStatement(sql);
					psmt.setString(1, MEMBER_ID);
					
					rs = psmt.executeQuery();
					while(rs.next()) {
						glList.add(rs.getInt("GROUP_ID"));
					}
						
						
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
						try {
							if(rs != null)	rs.close();
							if(psmt != null) psmt.close();
							if(con != null) con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
				return glList;
			}


	   
	   
// 회원 가입을 위한 메서드 ------승우----------------------------
	   public int signUpMember(String MEMBER_ID, String MEMBER_PW, String MEMBER_NAME, String MEMBER_PHONE, String MEMBER_GENDER, String MEMBER_BIRTH ) {
	      
	      int result = -1;
	      int signUp = 0;
	      
//	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,?)";
	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
	      
	      try {
	         con = getConnection();
	         psmt = con.prepareStatement(insertSql);
	      
	         psmt.setString(1, MEMBER_ID);
	         psmt.setString(2, MEMBER_PW);
	         psmt.setString(3, MEMBER_NAME);
	         psmt.setString(4, MEMBER_PHONE);
	         psmt.setString(5, "img/member/basis_photo.png");
	         psmt.setString(6, MEMBER_GENDER);
	         psmt.setString(7, MEMBER_BIRTH);
	         psmt.setString(8, null);
	         psmt.setString(9, null);
//	         psmt.setTimestamp(10, null);
//	         System.out.println(psmt.executeUpdate());
//	         return result;
	         result = psmt.executeUpdate();
//	         if(psmt.executeUpdate() > 0)
//	         {
//	        	 result = 1;
//	         }
	      
//	         rs = psmt.executeQuery();
//	         
//	         if(rs.next()) {
//	            // 회원가입이 되었다면 ?
//	            result = 1;
//	         } else {
//	            // 안됨
//	            result = -1;
//	         }
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } finally {
	         try {
	            if(rs != null)   rs.close();
	            if(psmt != null) psmt.close();
	            if(con != null) con.close();
	         } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	         System.out.println(result);
	         
//	         if(signUp != 1)
//	        	 return result;
//	         else
//	        	 return 1;
	         
	         return result;
	         
	   }
//	      System.out.println(result);
//	      return result;      
	                     
	   }	   
	   
	   
	   
// 회원가입시 아이디 실시간 검사...---------승우------------------------------
	   public int checkID(String idCheck) {
		   int result = -1;
		   
		   String sql = "select MEMBER_ID from MEMBER where MEMBER_ID=?";
		   
		   try {
			con = getConnection();
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, idCheck);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				result = 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	         try {
		            if(rs != null) rs.close();
		            if(psmt != null) psmt.close();
		            if(con != null) con.close();
		         } catch (SQLException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		         }
		   }
	       return result;
		  
	   }	   
	   
	   
	   

	
	// 친구 목록을 뽑아오고싶어하는 메서드 -----현우 ---------------------
	public ArrayList printFriendList(String MEMBER_ID, String MEMBER_NAME, String MEMBER_BIRTH, String MEMBER_PHONE, String MEMBER_IMG){
		 

		String sql = "SELECT MEMBER_IMG, MEMBER_NAME, MEMBER_BIRTH, MEMBER_PHONE, FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID = 'asdfasdf'";
		//로그인세션 아이디 받아와서 넣기.
		ArrayList arList = new ArrayList();
		
		HttpSession session = null;
	    session.getAttribute(MEMBER_ID);
		
		try {
			con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				// 친구리스트를 쪼개서 str에 집어넣고
				String[] str = rs.getString(9).split(",");
				System.out.println(str);
				
				// str에서 b가 있는지 알아보고
				for(int i=0; i<arList.size(); i++){
					// 만약 b가 있다면 memberVO객체를 만들어서 rs값을 넣은뒤 arList에 add
					if(str[i] == MEMBER_ID) {	
						MemberVO mdo = new MemberVO();
					arList.add(mdo.getMEMBER_NAME());					
					
				}else{
					System.out.println("친구가 없습니다.");
				}	
			}								
		}
			
			rs.close();
			stmt.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return arList;	
	}
	
	   // 친구목록 얻어와서 친구들정보 넘겨주기 종길ver....
	   public ArrayList<MemberVO> getFriendList(String MEMBER_ID){
	       
	      String myFrinds ="";
	      String findMyFriendsListSql = "SELECT FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID=?";
	      String getMyFriendsSql = "SELECT MEMBER_ID, MEMBER_NAME, MEMBER_PHONE, MEMBER_IMG, MEMBER_GENDER, MEMBER_BIRTH FROM MEMBER WHERE MEMBER_ID=?";
	      ArrayList<MemberVO> myfriendsList = new ArrayList<MemberVO>();
	      
	      try {
	         con = getConnection();
	         psmt = con.prepareStatement(findMyFriendsListSql);
	         psmt.setString(1, MEMBER_ID);
	         rs = psmt.executeQuery();
	         
	         if(rs.next()){
	            if(rs.getString("FRIENDS_LIST") != null){
	               
	               myFrinds = rs.getString("FRIENDS_LIST");
	               
	               // 친구목록 만들고 쿼리작성하는게 느려저 db를 오래 잡고있을수도 있으므로,,,닫아버렷.,,
//	               try {
//	                  if(rs != null)
//	                  if(psmt != null) psmt.close();
//	                  if(con != null) con.close();
//	               } catch (SQLException e) {
//	                  e.printStackTrace();
//	               }
	               //친구 수에따라 쿼리문 늘려버리깃..
	               String[] str = myFrinds.split(",");
	               for(int i = 1; i < str.length; i++){
	                  getMyFriendsSql+= "or MEMBER_ID=?";
	               }
//	               con = getConnection();
	               psmt = con.prepareStatement(getMyFriendsSql);
	               for(int i = 0; i < str.length; i++){
	                  psmt.setString(i+1, str[i]);
	               }
	               rs = psmt.executeQuery();
	               
	               while(rs.next()){
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
	            if(rs != null)
	            if(psmt != null) psmt.close();
	            if(con != null) con.close();
	         } catch (SQLException e) {
	         }
	   }
	      return myfriendsList;   
	   }
	   
	 //회원정보를 가져오고싶어함------------------------------------------------
	   public ArrayList<MemberVO> myInfo(String MEMBER_ID){
	      String sql = "select MEMBER_IMG, MEMBER_NAME, MEMBER_PHONE, MEMBER_BIRTH from MEMBER where MEMBER_ID = ? ";
	      
	      ArrayList<MemberVO> arList = new ArrayList<MemberVO>(); 
	      
	      try {
	         con = getConnection();
	          psmt = con.prepareStatement(sql);
	            psmt.setString(1, MEMBER_ID);
	            rs = psmt.executeQuery();
	            
	            //이렇게하면은 rs.getstring(1)에 이미지 2에 이름 3에 전화번호 4에 생일이 들어온다.
	            while(rs.next()){
	               MemberVO mvo = new MemberVO();
	               mvo.setMEMBER_IMG(rs.getString("MEMBER_IMG"));
	               mvo.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
	               mvo.setMEMBER_PHONE(rs.getString("MEMBER_PHONE"));
	               mvo.setMEMBER_BIRTH(rs.getString("MEMBER_BIRTH"));
	                 arList.add(mvo);
	            }
	            //arList에 이미지 이름 폰번호 생일이 나온다.
	         
	      } catch (Exception e) {
	      }finally {
	            try {
	                  if(rs != null)
	                  if(psmt != null) psmt.close();
	                  if(con != null) con.close();
	               } catch (SQLException e) {
	               }
	         }
	      return arList;
	   }
	   
	   
	   
	   public ArrayList<String> test(){
		      ArrayList<String> str = new ArrayList<String>(); 
		      String sql = "select * from test";
		      int a=0;
		      
		      try {
		         a=4;
		            con = getConnection();
		            a=5;
		             psmt = con.prepareStatement(sql);
		             a=1;  
		             rs = psmt.executeQuery();
		               a=2;    
		               while(rs.next()){
		                  str.add(rs.getString(1));
		                  str.add(rs.getString(2));
		               }
		               a=3;
		         } catch (Exception e) {
		            e.printStackTrace();
		         }finally {
		               try {
		                     if(rs != null)
		                     if(psmt != null) psmt.close();
		                     if(con != null) con.close();
		                  } catch (SQLException e) {
		                     e.printStackTrace();
		                  }
		            }
		      return str;
		   }

	
	
}