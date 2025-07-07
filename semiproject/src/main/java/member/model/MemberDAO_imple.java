package member.model;


import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberVO;
import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;


public class MemberDAO_imple implements MemberDAO {

   private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
   private Connection conn;
   private PreparedStatement pstmt;
   private ResultSet rs;
   
   private AES256 aes;
   
   // 생성자
   public MemberDAO_imple() {
      
      try {
         Context initContext = new InitialContext();
          Context envContext  = (Context)initContext.lookup("java:/comp/env");
          ds = (DataSource)envContext.lookup("jdbc/semiproject");
          
          aes = new AES256(SecretMyKey.KEY);
          // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
          
      } catch(NamingException e) {
         e.printStackTrace();
      } catch(UnsupportedEncodingException e) {
         e.printStackTrace();
      }
   }
   
   
   // 사용한 자원을 반납하는 close() 메소드 생성하기
   private void close() {
      try {
         if(rs    != null) {rs.close();     rs=null;}
         if(pstmt != null) {pstmt.close(); pstmt=null;}
         if(conn  != null) {conn.close();  conn=null;}
      } catch(SQLException e) {
         e.printStackTrace();
      }
   }// end of private void close()---------------      

   // 이메일 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다) 
      @Override
      public boolean emailDuplicateCheck(String email) throws SQLException {

         boolean isExists = false;
         
         try {
              conn = ds.getConnection();
              
              String sql = " select email "
                         + " from tbl_user "
                         + " where email = ? ";
              
              pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, aes.encrypt(email));
              
              rs = pstmt.executeQuery();
              
              isExists = rs.next(); // 행이 있으면 true  (중복된 email) 
                                    // 행이 없으면 false (사용가능한 email) 
            
         } catch(GeneralSecurityException | UnsupportedEncodingException e) {
              e.printStackTrace();
         } finally {
              close();
         }
         
         return isExists;      
      }// end of public boolean emailDuplicateCheck(String email) throws SQLException-------


      
      // 로그인 처리 
      @Override
      public MemberVO login(Map<String, String> paraMap) throws SQLException {

          MemberVO member = null;

          try {
              conn = ds.getConnection();

              String sql = " SELECT user_id, user_name, point, register_date, "
                         + "        pwdchangegap, is_active, email, mobile, lastlogingap "
                         + " FROM "
                         + "  ( "
                         + "   SELECT user_id, user_name, point, register_date, "
                         + "          to_number(TRUNC( months_between(sysdate, last_pwd_update) )) AS pwdchangegap, "
                         + "          is_active, email, mobile "
                         + "   FROM tbl_user "
                         + "   WHERE is_withdrawn = 0 AND user_id = ? and user_pwd = ? "
                         + " ) M "
                         + " CROSS JOIN "
                         + " ( "
                         + "   SELECT to_number(TRUNC( months_between(sysdate, MAX(login_records)) )) AS lastlogingap "
                         + "   FROM tbl_login_history "
                         + "   WHERE fk_user_id = ? "
                         + " ) H ";

              pstmt = conn.prepareStatement(sql);

              pstmt.setString(1, paraMap.get("user_id"));
              pstmt.setString(2, Sha256.encrypt(paraMap.get("user_pwd")));
              pstmt.setString(3, paraMap.get("user_id"));

              rs = pstmt.executeQuery();

              if (rs.next()) {

                  member = new MemberVO();

                  member.setUser_id(rs.getString("user_id"));
                  member.setUser_name(rs.getString("user_name"));
                  member.setPoint(rs.getInt("point"));
                  member.setRegister_date(rs.getString("register_date"));
                  member.setIs_active(rs.getInt("is_active")); // getInt 로 수정하기 !!!!!!!!!!!!!!!

                  if (rs.getInt("pwdchangegap") >= 3) {
                      // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true
                      // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false

                      member.setRequirePwdChange(true); // 로그인시 암호를 변경해라는 alert 를 띄우도록 할 때 사용한다.
                  }

                  member.setEmail(aes.decrypt(rs.getString("email")));
                  member.setMobile(aes.decrypt(rs.getString("mobile")));

                  // ==== 휴면이 아닌 회원만 tbl_login_history(로그인기록) 테이블에 insert 하기 시작 ==== //
                  if (rs.getInt("lastlogingap") < 12) {
                      sql = " insert into tbl_login_history(fk_user_id, login_records, login_ip) "
                          + " values(?, sysdate, ?) ";

                      pstmt = conn.prepareStatement(sql);
                      pstmt.setString(1, paraMap.get("user_id"));
                      pstmt.setString(2, paraMap.get("login_ip"));

                      pstmt.executeUpdate();
                  } 
                  // ==== 휴면이 아닌 회원만 tbl_login_history(로그인기록) 테이블에 insert 하기 끝 ==== //
                  else {
                      // 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지났으면 휴면으로 지정 
                      member.setIs_active(1);  // "1" => 1 로 수정하기 !!!!!!!!!!!!!!!!

                      if (rs.getInt("is_active") == 0) {
                          // === tbl_user 테이블의 is_active 컬럼의 값을 1로 변경하기 === //
                          sql = " update tbl_user set is_active = 1 "
                              + " where user_id = ? ";

                          pstmt = conn.prepareStatement(sql);
                          pstmt.setString(1, paraMap.get("user_id"));
                          pstmt.executeUpdate();
                      }
                  }

              } // end of if(rs.next())---------------------------------------

          } catch (GeneralSecurityException | UnsupportedEncodingException e) {
              e.printStackTrace();
          } finally {
              close();
          }

          // System.out.println("[DEBUG] VO 최종 is_active 값: " + member.getIs_active());
          return member;
      } // end of public MemberVO login(Map<String, String> paraMap) throws SQLException-----


      
      
   // ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다) 
         @Override
         public boolean idDuplicateCheck(String userid) throws SQLException {
            
            boolean isExists = false;
            
            try {
                 conn = ds.getConnection();
                 
                 String sql = " select user_id "
                            + " from tbl_user "
                            + " where user_id = ? ";
                 
                 pstmt = conn.prepareStatement(sql);
                 pstmt.setString(1, userid);
                 
                 rs = pstmt.executeQuery();
                 
                 isExists = rs.next(); // 행이 있으면 true  (중복된 userid) 
                                       // 행이 없으면 false (사용가능한 userid) 
               
            } finally {
               close();
            }
            
            return isExists;
         }// end of public boolean idDuplicateCheck(String userid) throws SQLException------

      // 회원가입을 해주는 메소드 (tbl_member 테이블에 insert)
      @Override
      public int registerMember(MemberVO member) throws SQLException {
         
         int result = 0;
         
         try {
              conn = ds.getConnection();
             
              String sql = " insert into tbl_user(user_name, user_id, user_pwd, mobile, email, birthday, fk_grade_no) " 
                         + " values(?, ?, ?, ?, ?, to_date(?, 'yyyy-mm-dd'), ? )";
              
              pstmt = conn.prepareStatement(sql);
              
              pstmt.setString(1, member.getUser_name());
              pstmt.setString(2, member.getUser_id());
              pstmt.setString(3, Sha256.encrypt(member.getUser_pwd()) ); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다. 
              pstmt.setString(4, aes.encrypt(member.getMobile()) ); // 휴대폰을 AES256 알고리즘으로 양방향 암호화 시킨다.         
              pstmt.setString(5, aes.encrypt(member.getEmail()) );
              pstmt.setString(6, member.getBirthday());
              pstmt.setString(7, "6");
              result = pstmt.executeUpdate();
              
         } catch(GeneralSecurityException | UnsupportedEncodingException e) {
              e.printStackTrace();
         } finally {
              close();
         }
         
         return result;

         
      }// end of public int registerMember(MemberVO member) throws SQLException-----------

      // 패스워드가 맞는지 확인 하는 메소드 
      @Override
      public boolean pwdcheck(Map<String, String> paramap) throws SQLException {
         
         boolean isExists = false;
            
            try {
                 conn = ds.getConnection();
                 
                 String sql = " select * from tbl_user "
                       + " where user_id = ? and user_pwd = ? ";
                 
                 pstmt = conn.prepareStatement(sql);
                 pstmt.setString(1, paramap.get("user_id"));
                 pstmt.setString(2, Sha256.encrypt(paramap.get("user_pwd")));
                 rs = pstmt.executeQuery();
                 
                 isExists = rs.next(); // 행이 있으면 true  (중복된 userid) 
                                       // 행이 없으면 false (사용가능한 userid) 
               
            } finally {
               close();
            }
            
            return isExists;
      }// end public boolean pwdcheck(Map<String, String> paramap) throws SQLException

      //유저의 이메일을 변경하는 메소드 
      @Override
      public int changeEmailMember(Map<String, String> paramap) throws SQLException {
         int result = 0;

         try {
            conn = ds.getConnection();
 
            String sql = " update tbl_user set email = ? where user_id = ? "; 

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, aes.encrypt(paramap.get("new_email")) );
            pstmt.setString(2, paramap.get("user_id"));

            result = pstmt.executeUpdate();

         } catch(GeneralSecurityException | UnsupportedEncodingException e) {
              e.printStackTrace();
         } finally {
            close();
         }

         return result;
      }


      // 아이디 찾기
      @Override
      public String findUserid(Map<String, String> paraMap) throws SQLException {
         
         String user_id = null;
         
         try {
              conn = ds.getConnection();
              
              String sql = " select user_id "
                         + " from tbl_user "
                         + " where is_withdrawn = 0 and user_name = ? and email = ? ";
              
              pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, paraMap.get("user_name"));
              pstmt.setString(2, aes.encrypt(paraMap.get("email")) );
              
              rs = pstmt.executeQuery();
              
              if(rs.next()) {
                 user_id = rs.getString("user_id");
              }
            
         } catch(GeneralSecurityException | UnsupportedEncodingException e) {
              e.printStackTrace();
         } finally {
              close();
         }
         
         return user_id;   
         
      }// end of public String findUserid(Map<String, String> paraMap) throws SQLException------------------


      
      // 비밀번호 찾기
      @Override
      public boolean isUserExist(Map<String, String> paraMap) throws SQLException {
         
         boolean isUserExist = false;
         
         try {
              conn = ds.getConnection();
              
              String sql = " select user_id "
                         + " from tbl_user "
                         + " where is_withdrawn = 0 and user_id = ? and email = ? ";
              
              pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, paraMap.get("user_id"));
              pstmt.setString(2, aes.encrypt(paraMap.get("email")) );
              
              rs = pstmt.executeQuery();
              
              isUserExist = rs.next();
            
         } catch(GeneralSecurityException | UnsupportedEncodingException e) {
              e.printStackTrace();
         } finally {
              close();
         }      
         
         return isUserExist;
      }// end of public boolean isUserExist(Map<String, String> paraMap) throws SQLException-----------


      // 비밀번호 변경하기
      @Override
      public int pwdUpdate(Map<String, String> paraMap) throws SQLException {
         
         int result = 0;
         
         try {
              conn = ds.getConnection();
             
              String sql = " update tbl_user set user_pwd = ? "
                         + "                     , last_pwd_update = sysdate " 
                         + " where user_id = ? ";
              
              pstmt = conn.prepareStatement(sql);
              
              pstmt.setString(1, Sha256.encrypt(paraMap.get("new_pwd")) ); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.  
              pstmt.setString(2, paraMap.get("user_id"));
                         
              result = pstmt.executeUpdate();
              
         } finally {
              close();
         }
         
         return result;
         
      }// end of public int pwdUpdate(Map<String, String> paraMap) throws SQLException----------------


      //입력한 비밀번호가 맞는지 확인하는 메소드
      @Override
      public boolean checkPassword(String user_id, String currentPwd) throws SQLException {
         boolean isExists = false;

         try {
            conn = ds.getConnection();

            String sql = " select   * " + " from      tbl_user " + " where      user_id = ? and user_pwd = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,   user_id );
            pstmt.setString(2, Sha256.encrypt(currentPwd));

            rs = pstmt.executeQuery();

            isExists = rs.next(); // 행이 있으면 true (기존과 동일한 pwd)
            // 행이 없으면 false (기존과 상이한 pwd > 사용가능한 pwd)
         } finally {
            close();
         }

         return isExists;
      }





      //로그인시 access_level 이 0인지 1인지 알아오는 메소드(관리자인지 일반회원인지 확인)
      @Override
      public int getAccessLevelByUserId(String user_id) throws SQLException {

          int access_level = -1; // 기본값 (존재하지 않거나 오류 시)
          
         try {
              conn = ds.getConnection();

              String sql = " select access_level from tbl_user where user_id = ? and is_withdrawn = 0";

              pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, user_id);

              rs = pstmt.executeQuery();

              if (rs.next()) {
                  access_level = rs.getInt("access_level");
              }

          } finally {
              close();
          }

          return access_level;
      }





      
      // 회원 존재 여부를 확인하는 메소드
      @Override
      public boolean isUserExists(String user_name, String mobile) throws SQLException {
         
         boolean isUserExist = false;
         
         try {
            conn = ds.getConnection();
            
            String sql = " select * "
                     + " from tbl_user "
                     + " where is_active = 1 and user_name= ? and mobile = ? ";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_name);
            pstmt.setString(2, aes.encrypt(mobile));
            
            rs = pstmt.executeQuery();
            
            isUserExist = rs.next();
            
            
         } catch (UnsupportedEncodingException | GeneralSecurityException e) {
            e.printStackTrace();
         } finally {
            close();
         }
         
         return isUserExist;
      }// end of public boolean isUserExists(String user_name, String mobile) throws SQLException------------------


      
      // 인증번호 일치 시 휴면 해제 처리(is_active=0)
      @Override
      public boolean updateUserIsActive(String login_ip, String sessionUser_id, String sessionuser_name, String sessionMobile) throws SQLException {
         
         boolean result = false;
          
          try {
              conn = ds.getConnection();
              
              String sql = "UPDATE tbl_user SET is_active = 0 WHERE user_id = ? and user_name = ? AND mobile = ? ";
              
              pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, sessionUser_id);
              pstmt.setString(2, sessionuser_name);
              pstmt.setString(3, aes.encrypt(sessionMobile));  // AES 암호화된 값으로 전달
              
              int n = pstmt.executeUpdate();
              
              if(n == 1) {
                 
                 sql = " insert into tbl_login_history(fk_user_id, login_records, login_ip) "
                    + " values(? ,sysdate, ?) ";
                 
                  pstmt = conn.prepareStatement(sql);
                  
                  pstmt.setString(1, sessionUser_id);
                  pstmt.setString(2, login_ip);
                  
                  pstmt.executeUpdate();
                  
              }
              
              result = n > 0;  // 한 건 이상 업데이트 되었으면 성공
              
          } catch (Exception e) {
              e.printStackTrace();
          } finally {
              close();
          }
          
          return result;
         
      }// end of public boolean updateUserIsActive(String sessionuser_name, String sessionMobile) throws SQLException------------






      @Override
      public void processPostPayment(String user_id, int finalPay, int used_point, int earned_point) throws Exception {
         try {
              // 1) 커넥션 가져오기
              conn = ds.getConnection();

              // 2) tbl_user 총 결제금액·포인트 업데이트
              String updateSql   = " update   tbl_user "
                             + " set      total_payment = total_payment + ?, "
                             + "       point = point - ? + ? "
                             + " where   user_id = ? ";
              pstmt = conn.prepareStatement(updateSql);
              pstmt.setInt(1, finalPay);      // 결제금액만큼 total_payment 증가
              pstmt.setInt(2, used_point);   // 사용 포인트 차감
              pstmt.setInt(3, earned_point);   // 보너스 포인트 = finalPay * pointrate
              pstmt.setString(4, user_id);
              pstmt.executeUpdate();
              pstmt.close();

              // 3) 등급 커트라인에 따라 fk_grade_no 갱신
              String gradeSql =
                  " UPDATE tbl_user u SET fk_grade_no = ( " +
                  "  SELECT grade_no FROM (" +
                  "    SELECT grade_no, ROW_NUMBER() OVER (ORDER BY grade_cutoff DESC) rn " +
                  "    FROM tbl_user_grade " +
                  "    WHERE grade_cutoff <= u.total_payment " +
                  "  ) WHERE rn = 1 " +
                  " ) WHERE user_id = ? ";
              pstmt = conn.prepareStatement(gradeSql);
              pstmt.setString(1, user_id);
              pstmt.executeUpdate();

          } finally {
              // 사용한 자원 모두 반납
              close();
          }
         
      }
      


      // 회원 목록 조회 메서드
      @Override
      public List<MemberVO> getMemberList(String searchType, String searchWord, int offset, int limit) throws SQLException {
          List<MemberVO> memberList = new ArrayList<>();  // 결과를 담을 리스트 생성

          try {
              
              conn = ds.getConnection();

              
              String sql = " select user_name, mobile, email, fk_grade_no, user_id "
                         + " from tbl_user "
                         + " where user_id != 'admin' ";

              // 검색어가 있으면 조건 추가
              if (searchWord != null && !searchWord.trim().isEmpty()) {
                  if ("user_name".equals(searchType)) {  
                      // 이름 검색일 경우, 부분일치(Like) 조건 추가
                      sql += " and user_name like ? ";
                  } else if ("email".equals(searchType)) {  
                      // 이메일 검색일 경우, 암호화 처리 후 정확히 일치하는 조건 추가
                      try {
                          searchWord = aes.encrypt(searchWord);  
                      } catch (GeneralSecurityException | UnsupportedEncodingException e) {
                          e.printStackTrace();
                          searchWord = ""; // 암호화 실패시 빈 문자열 처리
                      }
                      sql += " and email = ? ";  // 정확한 일치 검색 조건
                  }
              }

              //  페이징 처리 (이름 오름차순 정렬)
              sql += " order by user_name ASC "
                   + " offset ? rows fetch next ? rows only ";

              pstmt = conn.prepareStatement(sql);

              
              if (searchWord != null && !searchWord.trim().isEmpty()) {
                  if ("email".equals(searchType)) {
                      pstmt.setString(1, searchWord);  // 암호화된 이메일로 정확 일치
                      pstmt.setInt(2, offset);          // 페이징 시작 위치
                      pstmt.setInt(3, limit);           // 페이징 조회 개수
                  } else {
                      pstmt.setString(1, "%" + searchWord + "%");  // 이름은 like 검색이므로 % 사용
                      pstmt.setInt(2, offset);
                      pstmt.setInt(3, limit);
                  }
              } else {
                  // 검색어 없으면 페이징 파라미터만 세팅
                  pstmt.setInt(1, offset);
                  pstmt.setInt(2, limit);
              }

              rs = pstmt.executeQuery();

             
              while (rs.next()) {
                  MemberVO member = new MemberVO();  

                  member.setUser_name(rs.getString("user_name"));

                  try {
                      // 암호화된 데이터는 복호화해서 저장
                      member.setMobile(aes.decrypt(rs.getString("mobile")));
                      member.setEmail(aes.decrypt(rs.getString("email")));
                  } catch (Exception e) {
                      e.printStackTrace();
                      // 복호화 실패 시 DB에 있는 원본 값을 그대로 저장 (예외 처리)
                      member.setMobile(rs.getString("mobile"));
                      member.setEmail(rs.getString("email"));
                  }

                  member.setFk_grade_no(rs.getString("fk_grade_no"));
                  member.setUser_id(rs.getString("user_id"));
                  
                  memberList.add(member);
              }

          } finally {
              close();
          }
          return memberList;
      }


         
      
      // 회원 총 개수 조회 메서드
      @Override
      public int getMemberTotalCount(String searchType, String searchWord) throws SQLException {
          int totalCount = 0;  // 회원 수를 저장할 변수

          try {
              conn = ds.getConnection();

              String sql = " select count(*) as totalCount from tbl_user where user_id != 'admin' ";

              //  검색어가 있으면 조건 추가
              if(searchWord != null && !searchWord.trim().isEmpty()) {
                  if("user_name".equals(searchType)) {
                      // 이름 검색일 때 부분일치 조건 추가
                      sql += " and user_name like ? ";
                  } else if("email".equals(searchType)) {
                      // 이메일 검색일 때 부분일치 조건 추가
                      sql += " and email = ? ";
                  }
              }
              
              pstmt = conn.prepareStatement(sql);

              if(searchWord != null && !searchWord.trim().isEmpty()) {
                  pstmt.setString(1, "%" + searchWord + "%");
              }
       
              rs = pstmt.executeQuery();
           
              if(rs.next()) {
                  totalCount = rs.getInt("totalCount");  // count(*) 결과를 읽음
              }

          } finally {
              close();
          }
    
          return totalCount;
      }


      
      
      
      
      
      
      
      
      
      


      // 90일 뒤에 비밀번호 변경
      @Override
      public void updateLastPwdUpdate(String user_id) throws SQLException {
         
         try {
            
            conn = ds.getConnection();

             String sql = "UPDATE tbl_user SET last_pwd_update = SYSDATE WHERE user_id = ?";
             
             pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, user_id);
             
             pstmt.executeUpdate();
            
         } finally {
            close();
         }
         
      }// end of public void updateLastPwdUpdate(String user_id) throws SQLException------------


      // user_id로 회원상세정보 가져오는 메서드
      @Override
      public MemberVO getMemberByUserId(String user_id) throws SQLException {
         
          MemberVO member = null;

          try {
              conn = ds.getConnection();

              String sql = " SELECT user_id, user_name, email, mobile, birthday, total_payment, fk_grade_no, "
                         + " point, register_date, last_pwd_update, is_withdrawn, is_active, access_level "
                         + " FROM tbl_user "
                         + " WHERE user_id = ? ";

              pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, user_id);
              rs = pstmt.executeQuery();

              if(rs.next()) {
                  member = new MemberVO();

                  member.setUser_id(rs.getString("user_id"));
                  member.setUser_name(rs.getString("user_name"));

                  // 이메일, 전화번호는 복호화 처리
                  try {
                      member.setEmail(aes.decrypt(rs.getString("email")));
                      member.setMobile(aes.decrypt(rs.getString("mobile")));
                  } catch (Exception e) {
                      e.printStackTrace();
                      member.setEmail(rs.getString("email"));
                      member.setMobile(rs.getString("mobile"));
                  }

                  member.setBirthday(rs.getString("birthday"));
                  member.setTotal_payment(rs.getInt("total_payment"));
                  member.setFk_grade_no(rs.getString("fk_grade_no"));
                  member.setPoint(rs.getInt("point"));
                  member.setRegister_date(rs.getString("register_date"));
                  member.setLast_pwd_update(rs.getString("last_pwd_update"));
                  member.setIs_withdrawn(rs.getInt("is_withdrawn"));
                  member.setIs_active(rs.getInt("is_active"));
                  member.setAccess_level(rs.getInt("access_level"));
              }
          } finally {
              close();
          }

          return member;
      }


      @Override
      public int getEarnedPoint(String user_id, int finalPay) throws SQLException
      {
         int result = 0;
         
         try
         {
            conn = ds.getConnection();

              String sql	= " select	floor(? * (select pointrate from tbl_user_grade where grade_no = u.fk_grade_no)) "
            		  		+ " from	tbl_user u"
            		  		+ " where   u.user_id = ? ";
              
              pstmt = conn.prepareStatement(sql);
              pstmt.setInt(1, finalPay);
              pstmt.setString(2, user_id);
              rs = pstmt.executeQuery();
              
              if(rs.next())
              {
                 result = rs.getInt(1);
              }
         }
         finally
         {
              close();
          }
         return result;
      }

      //   결제 시점의 누적 결제금액 조회
      @Override
      public int getCurrentTotalPayment(String user_id) throws SQLException
      {
          int result = 0;

          try
          {
              conn = ds.getConnection();
              
              String sql = " select total_payment from tbl_user where user_id = ? ";
              
              pstmt = conn.prepareStatement(sql);
              pstmt.setString(1, user_id);
              rs = pstmt.executeQuery();
              
              if (rs.next())
              {
                  result = rs.getInt(1);
              }
          }
          finally
          {
              close();
          }

          return result;
      }

    //	유저 정보 결제전으로 원복
	@Override
	public void rollbackUserPointsAndTotalPayment(String user_id, int used_point, int earned_point, int paid_amount, int total_payment_stamp) throws SQLException
	{
		try
		{
	        conn = ds.getConnection();

	        String sql = "UPDATE tbl_user " +
	                "SET point = point + ? - ?, " +
	                "    total_payment = total_payment - ? " +
	                "WHERE user_id = ?";

	   pstmt = conn.prepareStatement(sql);
	   pstmt.setInt(1, used_point);
	   pstmt.setInt(2, earned_point);
	   pstmt.setInt(3, paid_amount);
	   pstmt.setString(4, user_id);

	        pstmt.executeUpdate();
	    } finally {
	        close();
	    }
	}


	//	유저 등급 원복
	@Override
	public void recalcUserGrade(String user_id) throws SQLException
	{
	    try
	    {
	    	conn = ds.getConnection();

	        // 1. 해당 유저의 누적 결제 금액 조회
	        String sql1 = "SELECT total_payment FROM tbl_user WHERE user_id = ?";
	        pstmt = conn.prepareStatement(sql1);
	        pstmt.setString(1, user_id);
	        rs = pstmt.executeQuery();

	        int totalPayment = 0;
	        if (rs.next()) {
	            totalPayment = rs.getInt("total_payment");
	        }
	        rs.close();
	        pstmt.close();

	        // 2. 누적결제금액 기준으로 가장 높은 등급 번호 가져오기
	        String sql2 = "SELECT grade_no FROM tbl_user_grade WHERE min_payment <= ? " +
	                      "ORDER BY min_payment DESC FETCH FIRST 1 ROWS ONLY";
	        pstmt = conn.prepareStatement(sql2);
	        pstmt.setInt(1, totalPayment);
	        rs = pstmt.executeQuery();

	        int newGradeNo = 1;  // 기본등급 (예: 브론즈)
	        if (rs.next()) {
	            newGradeNo = rs.getInt("grade_no");
	        }
	        rs.close();
	        pstmt.close();

	        // 3. 회원 테이블에 등급 업데이트
	        String sql3 = "UPDATE tbl_user SET fk_grade_no = ? WHERE user_id = ?";
	        pstmt = conn.prepareStatement(sql3);
	        pstmt.setInt(1, newGradeNo);
	        pstmt.setString(2, user_id);
	        pstmt.executeUpdate();
	    }
	    finally {
	        close();
	    }
	}
}




