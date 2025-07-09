package myshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import myshop.domain.StayVO;
import myshop.domain.WishVo;

public class WishDAO_imple implements WishDAO {
	
	
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	
	
	// 생성자
		public WishDAO_imple() {
			
			try {
				Context initContext = new InitialContext();
			    Context envContext  = (Context)initContext.lookup("java:/comp/env");
			    ds = (DataSource)envContext.lookup("jdbc/local_semiproject");
			    
			} catch(NamingException e) {
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
	
	
	

	// 사용자가 찜한 숙소 페이징 처리 메소드
	public List<WishVo> selectWishWithPaging(String userid, int offset, int limit) throws SQLException {
	    List<WishVo> list = new ArrayList<>();
	    try {
	        conn = ds.getConnection();
	        String sql = 
	            " SELECT w_user_id, w_stay_no, stay_name, stay_thumbnail " +
	            " FROM TBL_WISHLIST a JOIN TBL_STAY b ON a.w_stay_no = b.stay_no " +
	            " WHERE w_user_id = ? " +
	            " ORDER BY w_stay_no " +
	            " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY " ; // Oracle 12c+ 방식

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, userid);
	        pstmt.setInt(2, offset);  // ex. 6
	        pstmt.setInt(3, limit);   // ex. 6

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            WishVo wvo = new WishVo();
	            wvo.setW_user_id(rs.getString("w_user_id"));
	            wvo.setW_stay_no(rs.getString("w_stay_no"));

	            StayVO svo = new StayVO();
	            svo.setStay_name(rs.getString("stay_name"));
	            svo.setStay_thumbnail(rs.getString("stay_thumbnail")); // ❗ null 아닌 실제 값
	            wvo.setStayVO(svo);
	            
	            list.add(wvo);
	        }
	    } finally {
	        close();
	    }
	    return list;
	}

}
