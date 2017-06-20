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
	         DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
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
			
			String selectSql = "select OUT_TIME from MEMBER where MEMBER_ID = ? and MEMBER_PW = ?";
			
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				psmt.setString(1, MEMBER_ID);
				psmt.setString(2, MEMBER_PW);
//				System.out.println("!");
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					// out_time이 0이면 로그인 성공 result에 1넣어줌
					if(rs.getInt(1) == 0) {
						// 로그인 성공
						result = 1;
					} else {
						result = rs.getInt(1);
						System.out.println(result);
					}
				}
			} catch (Exception e) {
				System.out.print("접속 에러");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
					try {
						if(rs != null)rs.close();
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
	   public int signUpMember(String MEMBER_ID, String MEMBER_PW, String MEMBER_NAME, String MEMBER_PHONE, String MEMBER_GENDER, String MEMBER_BIRTH, int OUT_TIME) {
	      
	      int result = 0;
	      int signUp = 0;
	      
//	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,?)";
	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?)";
	      
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
	         psmt.setInt(10, OUT_TIME);
	         
	         result = psmt.executeUpdate();
	         
//	         rs = psmt.executeQuery();
//	         

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
//	         System.out.println(result);
	         return result;
	      }    
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
	   
	 //회원정보를 가져오고싶어함---------------현우--------------------------------
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
	   
	   
//////////////////////////////////////////////////////////////////////////////////////////////	   
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

// 회원정보 배열을 통해 회원의 생일 반환하는 함수, 배열로 반환하며 형태는 생일+이름으로 저장
	   public ArrayList<MemberVO> getMemberBirth(ArrayList<String> memberList)
	   {
		   ArrayList<MemberVO> arrList = new ArrayList<MemberVO>();
		   String sql = "select MEMBER_ID, MEMBER_NAME, MEMBER_BIRTH from MEMBER";
		   
		   try
		   {
			   con = getConnection();
			   psmt = con.prepareStatement(sql);
			   rs = psmt.executeQuery();
			   
			   while(rs.next())
			   {
				   for(int i = 0; i < memberList.size(); i++)
				   {
					   // memberList의 ID와 member_id값이 같다면 member 객체 생성해서 arrList에 추가
					   if(rs.getString(1).equals(memberList.get(i)))
					   {
						   MemberVO member = new MemberVO();
						   
						   member.setMEMBER_ID(rs.getString(1));
						   member.setMEMBER_NAME(rs.getString(2));
						   member.setMEMBER_BIRTH(rs.getString(3));
						   
						   arrList.add(member);
					   }
				   }
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
				   if(rs != null) rs.close();
				   if(psmt != null) psmt.close();
				   if(con != null) con.close();
			   }
			   catch (SQLException e)
			   {
				   e.printStackTrace();
			   }
		   }
		   
		   return arrList;
	   }

///////////////////////////////////////////////////////////////////현우 비밀번호 변경.
	   
	   public int changePw(String MEMBER_ID, String nowpwd, String chpwd, String chkpwd){
		   
		   
		   
		   String chkpw = "select MEMBER_PW from MEMBER where MEMBER_ID = ?";
		   // ?에는 세션으로 받아온 아이디를 넣고 비밀번호를 받아온다음에 받아온 비밀번호랑 현재비밀번호 입력란이랑 비교해서 맞으면 두번쨰 쿼리문으로 넘어가서 비밀번호를 바꿔준다.
		   String changepwd = "update MEMBER set MEMBER_PW = ? where MEMBER_ID = ?";
		   //1번쨰 ?에는 바뀐 비밀번호를 넣고  2번쨰 ? 에는 아이디값 세션으로 받아넣는다.
		   int result = 0;
		   
		   try {
			   
			con = getConnection();
			
			psmt = con.prepareStatement(chkpw);
			psmt.setString(1, MEMBER_ID);
			rs = psmt.executeQuery();
			
			if(chpwd.equals(chkpwd)){
				
			while(rs.next()){
				
			if(rs.getString("MEMBER_PW").equals(nowpwd)){
				
				
				psmt = con.prepareStatement(changepwd);
				psmt.setString(1, chpwd);
				psmt.setString(2, MEMBER_ID);
				result = psmt.executeUpdate();
				
				if(result ==1 ){
					System.out.println("비밀번호가 일치합니다");
				}else{
					System.out.println("비밀번호가 일치하지 않습니다.");
				}
				
			
		}else{
			System.out.println("비밀번호가 서로 맞지 않습니다.");
		}
		}//while 끝나는 곳.
		}else{
			System.out.println("변경할 비밀번호가 서로 맞지 않습니다.");
		}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            try {
                if(rs != null)rs.close();
                if(psmt != null) psmt.close();
                if(con != null) con.close();
             } catch (SQLException e) {
             }
       }
		return result;
 }
///////////////////////////현우 회원탈퇴.////////////////////////
		   public int outUser(String MEMBER_ID){
			   
//			   SimpleDateFormat outDate = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분");
//			   Date date = new Date();
//			   String today = outDate.format(date);
			   
			   long outTime = System.currentTimeMillis()+60*60*24*1000*7;
			   
			   System.out.println(outTime * 60*60*24*1000*7);
			   
			   
			   String delUser = "UPDATE member SET OUT_TIME=? WHERE MEMBER_ID =?";
			   int result = 0;
			   try {
				con = getConnection();
				psmt = con.prepareStatement(delUser);
				psmt.setLong(1, outTime);
				psmt.setString(2, MEMBER_ID);
				result = psmt.executeUpdate();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}finally {
	            try {
	                if(rs != null)
	                if(psmt != null) psmt.close();
	                if(con != null) con.close();
	             } catch (SQLException e) {
	             }
	       }
			   
			   return result;
		   }
/////////////////////////////////회원정보 변 경////////////////////////////////////////
		   public int changeInfo(String MEMBER_NAME, String MEMBER_PHONE, String MEMBER_BIRTH, String MEMBER_ID){
			   String changeInfo = "UPDATE MEMBER set MEMBER_NAME=?, MEMBER_PHONE=?,MEMBER_BIRTH=? where MEMBER_ID =?";
			   int result = 0;
			   try {
				con = getConnection();
				psmt = con.prepareStatement(changeInfo);
				
				psmt.setString(1, MEMBER_NAME);
				psmt.setString(2, MEMBER_PHONE);
				psmt.setString(3, MEMBER_BIRTH);
				psmt.setString(4, MEMBER_ID);
				result = psmt.executeUpdate();
				
				if(result != 1){
					System.out.println("회원 수정되엇다.");
					System.out.println("왜");
				}else{
					System.out.println("회원 수정안되엇다.");
					System.out.println("왜2");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
	            try {
	                if(rs != null)
	                if(psmt != null) psmt.close();
	                if(con != null) con.close();
	             } catch (SQLException e) {
	             }
	       }
			   
			   return result;
			   
			   
		   }
		  		   
///////////////////////////////////////////////////////////////////////////
		public int changeImg(String MEMBER_ID, String MEMBER_IMG){
			String changepwd = "update MEMBER set MEMBER_IMG = ? where MEMBER_ID = ?";
			int result = 0;
			
			 try {
					con = getConnection();
					
					psmt = con.prepareStatement(changepwd);
					psmt.setString(1, "img/" + MEMBER_IMG);
					psmt.setString(2, MEMBER_ID);
					result = psmt.executeUpdate();
						
					

				} catch (Exception e) {
					e.printStackTrace();
				}finally {
		            try {
		                if(rs != null)rs.close();
		                if(psmt != null) psmt.close();
		                if(con != null) con.close();
		             } catch (SQLException e) {
		             }
		       }
				return result;
			
		}
	
	
}