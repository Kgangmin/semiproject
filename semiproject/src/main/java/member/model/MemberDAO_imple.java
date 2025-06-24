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
			if(rs    != null) {rs.close();	  rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn  != null) {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close()---------------



	@Override
	public boolean emailDuplicateCheck(Map<String, String> paraMap) throws SQLException {
	    boolean isExists = false;

	    try {
	        conn = ds.getConnection();

	        String sql = " select email "
	                   + " from tbl_user "
	                   + " where email = ? ";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, aes.encrypt(paraMap.get("new_email")));

	        rs = pstmt.executeQuery();

	        isExists = rs.next(); // 행이 있으면 true (중복된 email)
	                              // 행이 없으면 false (사용가능한 email)

	     } catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
	    } finally {
	        close();
	    }

	    return isExists;
	}


	
	
	

	
}



