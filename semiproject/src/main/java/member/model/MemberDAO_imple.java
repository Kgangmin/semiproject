package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			if(rs    != null) {rs.close();	  rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn  != null) {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close()---------------


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
	
	
	
	

}



