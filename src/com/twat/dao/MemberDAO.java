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
	
	
	// MemberDAO �� �̱��� -----------------------------------
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO(){}
	
	public static MemberDAO getInstance(){
		return instance;
	}
	 
	// -------------------------------------------------------
	
	
	
	   // DB������ ���� con�� ��ȯ�ϴ� �޼��� --------------------------------------------
	   public Connection getConnection() throws Exception {
	         Context initCtx = new InitialContext();
	         DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/twhat");      
	         
	      return ds.getConnection();
	   }

	
	
	  // ���̵� ã�� �޼��� (�̸�,��ȭ��ȣ�� ã��)-----�¿�-------------------------
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
	   
	   
	   
// ��й�ȣ ã�� �޼��� ---------�¿�-------------------------------
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
	   

		// ȸ�� �α����� ���� �޼��� ----------------------------------
		public int loginMember(String MEMBER_ID, String MEMBER_PW) {
			int result = -1;
			
			long currentTime = System.currentTimeMillis();
			   
			System.out.println(currentTime);
			
			String selectSql = "select OUT_TIME from MEMBER where MEMBER_ID = ? and MEMBER_PW = ?";
			
			
			try {
				con = getConnection();
				psmt = con.prepareStatement(selectSql);
				psmt.setString(1, MEMBER_ID);
				psmt.setString(2, MEMBER_PW);
//				System.out.println("!");
				rs = psmt.executeQuery();
				
//				if(rs.next()) {
//					// out_time�� 0�̸� �α��� ���� result�� 1�־���
//					if(rs.getInt(1) == 0) {
//						// �α��� ����
//						result = 1;
//					} else {
//						result = rs.getInt(1);
//						System.out.println(result);
//					}
//				}
				
				if(rs.next()) {
					// out_time�� 0�̸� �α��� ���� result�� 1�־���
					if(rs.getLong(1) == 0) {
						// �α��� ����
						result = 1;
					} else if(rs.getLong(1) > currentTime) { // Ż�� �������� ȸ��
						result = -1;
//						System.out.println(result);
					} else if(rs.getLong(1) < currentTime) { // Ż��� ȸ��
						result = -2;
					} else { // ���̵�, ��� Ʋ���� ����
						result = 0;
					}
				}
			} catch (Exception e) {
				System.out.print("���� ����");
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
	   
	   
	   
	// �α��� �ѻ���� �׷� ������ �б⸮
			public ArrayList<Integer> getMyGroupList(String MEMBER_ID) {
				ArrayList<Integer> glList = new ArrayList<Integer>();
				
				
				try {
					con = getConnection();
					
					// ���̵�� �׷��� �ҷ�����
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


	   
	   
// ȸ�� ������ ���� �޼��� ------�¿�----------------------------

	   public int signUpMember(String MEMBER_ID, String MEMBER_PW, String MEMBER_NAME, String MEMBER_PHONE, 
			   String MEMBER_GENDER, String MEMBER_BIRTH, int OUT_TIME, String MEMBER_QUESTION, String MEMBER_ANSWER) {

	      
	      int result = 0;
	      int signUp = 0;
	      
//	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,?)";

	      String insertSql = "insert into MEMBER values(?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?)";

	      
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

	         psmt.setString(11, MEMBER_QUESTION);
	         psmt.setString(12, MEMBER_ANSWER);

	         
	         result = psmt.executeUpdate();
//	         System.out.println(result);
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
	   

	   
	   
// ȸ�����Խ� ���̵� �ǽð� �˻�...---------�¿�------------------------------
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
	   
	   
	   

	
	// ģ�� ����� �̾ƿ���;��ϴ� �޼��� -----���� ---------------------
	public ArrayList printFriendList(String MEMBER_ID, String MEMBER_NAME, String MEMBER_BIRTH, String MEMBER_PHONE, String MEMBER_IMG){
		 

		String sql = "SELECT MEMBER_IMG, MEMBER_NAME, MEMBER_BIRTH, MEMBER_PHONE, FRIENDS_LIST FROM MEMBER WHERE MEMBER_ID = 'asdfasdf'";
		//�α��μ��� ���̵� �޾ƿͼ� �ֱ�.
		ArrayList arList = new ArrayList();
		
		HttpSession session = null;
	    session.getAttribute(MEMBER_ID);
		
		try {
			con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				// ģ������Ʈ�� �ɰ��� str�� ����ְ�
				String[] str = rs.getString(9).split(",");
				System.out.println(str);
				
				// str���� b�� �ִ��� �˾ƺ���
				for(int i=0; i<arList.size(); i++){
					// ���� b�� �ִٸ� memberVO��ü�� ���� rs���� ������ arList�� add
					if(str[i] == MEMBER_ID) {	
						MemberVO mdo = new MemberVO();
					arList.add(mdo.getMEMBER_NAME());					
					
				}else{
					System.out.println("ģ���� �����ϴ�.");
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
	
	   // ģ����� ���ͼ� ģ�������� �Ѱ��ֱ� ����ver....
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
	               
	               // ģ����� ����� �����ۼ��ϴ°� ������ db�� ���� ����������� �����Ƿ�,,,�ݾƹ���.,,
//	               try {
//	                  if(rs != null)
//	                  if(psmt != null) psmt.close();
//	                  if(con != null) con.close();
//	               } catch (SQLException e) {
//	                  e.printStackTrace();
//	               }
	               //ģ�� �������� ������ �÷�������..
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
	   
	 //ȸ�������� ��������;���---------------����--------------------------------
	   public ArrayList<MemberVO> myInfo(String MEMBER_ID){
	      String sql = "select MEMBER_IMG, MEMBER_NAME, MEMBER_PHONE, MEMBER_BIRTH from MEMBER where MEMBER_ID = ? ";
	      
	      ArrayList<MemberVO> arList = new ArrayList<MemberVO>(); 
	      
	      try {
	         con = getConnection();
	          psmt = con.prepareStatement(sql);
	            psmt.setString(1, MEMBER_ID);
	            rs = psmt.executeQuery();
	            
	            //�̷����ϸ��� rs.getstring(1)�� �̹��� 2�� �̸� 3�� ��ȭ��ȣ 4�� ������ ���´�.
	            while(rs.next()){
	               MemberVO mvo = new MemberVO();
	               mvo.setMEMBER_IMG(rs.getString("MEMBER_IMG"));
	               mvo.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
	               mvo.setMEMBER_PHONE(rs.getString("MEMBER_PHONE"));
	               mvo.setMEMBER_BIRTH(rs.getString("MEMBER_BIRTH"));
	                 arList.add(mvo);
	            }
	            //arList�� �̹��� �̸� ����ȣ ������ ���´�.
	         
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

// ȸ������ �迭�� ���� ȸ���� ���� ��ȯ�ϴ� �Լ�, �迭�� ��ȯ�ϸ� ���´� ����+�̸����� ����
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
					   // memberList�� ID�� member_id���� ���ٸ� member ��ü �����ؼ� arrList�� �߰�
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

///////////////////////////////////////////////////////////////////���� ��й�ȣ ����.
	   
	   public int changePw(String MEMBER_ID, String nowpwd, String chpwd, String chkpwd){
		   
		   
		   
		   String chkpw = "select MEMBER_PW from MEMBER where MEMBER_ID = ?";
		   // ?���� �������� �޾ƿ� ���̵� �ְ� ��й�ȣ�� �޾ƿ´����� �޾ƿ� ��й�ȣ�� �����й�ȣ �Է¶��̶� ���ؼ� ������ �ι��� ���������� �Ѿ�� ��й�ȣ�� �ٲ��ش�.
		   String changepwd = "update MEMBER set MEMBER_PW = ? where MEMBER_ID = ?";
		   //1���� ?���� �ٲ� ��й�ȣ�� �ְ�  2���� ? ���� ���̵� �������� �޾Ƴִ´�.
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
					System.out.println("��й�ȣ�� ��ġ�մϴ�");
				}else{
					System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				}
				
			
		}else{
			System.out.println("��й�ȣ�� ���� ���� �ʽ��ϴ�.");
		}
		}//while ������ ��.
		}else{
			System.out.println("������ ��й�ȣ�� ���� ���� �ʽ��ϴ�.");
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
///////////////////////////���� ȸ��Ż��.////////////////////////
		   public int outUser(String MEMBER_ID){
			   
//			   SimpleDateFormat outDate = new SimpleDateFormat("yyyy�� MM��dd�� HH��mm��");
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
/////////////////////////////////ȸ������ �� ��////////////////////////////////////////
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
					System.out.println("ȸ�� �����Ǿ���.");
					System.out.println("��");
				}else{
					System.out.println("ȸ�� �����ȵǾ���.");
					System.out.println("��2");
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
		  		   
////////////////////////////////�̹��� ������ ���躸�� ���� �ϱ�.//////////////////////////////////////
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
///////////////////ģ����Ͽ��� ģ�� �߰��ϱ�/////////////////////////////////////////
		public ArrayList<MemberVO> addFriends(String userPhone){
			//ģ�� ��ȭ��ȣ�� ������ ģ���� �����´�.
			String addFriend = "select MEMBER_ID, MEMBER_NAME, MEMBER_BIRTH, MEMBER_PHONE, MEMBER_IMG from MEMBER where MEMBER_PHONE = ?";
			ArrayList<MemberVO> arList = new ArrayList<MemberVO>();
			try {
				con =getConnection();
				psmt = con.prepareStatement(addFriend);
				psmt.setString(1, userPhone);
				rs = psmt.executeQuery();
				
				while(rs.next()){
					MemberVO member = new MemberVO();
					member.setMEMBER_IMG(rs.getString(5));
					member.setMEMBER_NAME(rs.getString(2));
					member.setMEMBER_BIRTH(rs.getString(3));
					member.setMEMBER_PHONE(rs.getString(4));
					member.setMEMBER_ID(rs.getString(1));
					
					arList.add(member);
					System.out.println(rs.getString(1));
					System.out.println(rs.getString(2));
					System.out.println(rs.getString(3));
					System.out.println(rs.getString(4));
					System.out.println(rs.getString(5));
			
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
	            try {
	                if(rs != null)rs.close();
	                if(psmt != null) psmt.close();
	                if(con != null) con.close();
	             } catch (SQLException e) {
	             }
	       }
			
			return arList;
		}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		