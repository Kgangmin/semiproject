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

public class StayDAO_imple implements StayDAO {

	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public StayDAO_imple() {
		
		try {
			Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/semiproject");
		    
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



	 @Override
	    public List<StayVO> selectStayPage(int start, int len) throws SQLException {
	        List<StayVO> list = new ArrayList<>();
	        try {
	            conn = ds.getConnection();
	            String sql = " SELECT * FROM "
	            		   + " ( "
	            		   + "  SELECT ROWNUM AS rn, s.* FROM "
	            		   + " ( " 
	            		   + "    SELECT * FROM tbl_stay ORDER BY stay_no " 
	            		   + " ) s WHERE ROWNUM <= ? " 
	            		   + " ) WHERE rn >= ? ";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, start + len - 1);
	            pstmt.setInt(2, start);
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	                StayVO svo = new StayVO();
	                svo.setStay_no(rs.getString("stay_no"));
	                svo.setStay_name(rs.getString("stay_name"));
	                svo.setStay_info(rs.getString("stay_info"));
	                svo.setStay_thumbnail(rs.getString("stay_thumbnail"));
	                svo.setStay_score(rs.getInt("stay_score"));
	                svo.setViews(rs.getInt   ("views"));
	                // 필요 시 latitude, longitude 등 추가 세팅
	                list.add(svo);
	            }
	        } finally {
	            close();
	        }
	        return list;
	    }
	 
	    // 카테고리에 해당하는 객실정보를 가져온다.
		@Override
		public List<StayVO> getStaysByCategory(String category, int start, int len) throws SQLException
		{
			List<StayVO> list = new ArrayList<>();
			
		    try {
		        conn = ds.getConnection();

		        String sql = " SELECT * FROM "
	            		   + " ( "
	            		   + "  SELECT ROWNUM AS rn, s.* FROM "
	            		   + " ( " 
	            		   + "    SELECT * FROM tbl_stay ORDER BY stay_no " 
	            		   + " ) s  WHERE fk_stay_category_no = ? and ROWNUM <= ? " 
	            		   + " ) WHERE rn >= ? ";
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setString(1, category);
		        pstmt.setInt(2, start + len - 1);
		        pstmt.setInt(3, start);

		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		        	StayVO svo = new StayVO();
	                svo.setStay_no(rs.getString("stay_no"));
	                svo.setStay_name(rs.getString("stay_name"));
	                svo.setStay_info(rs.getString("stay_info"));
	                svo.setStay_thumbnail(rs.getString("stay_thumbnail"));
	                svo.setStay_score(rs.getInt("stay_score"));
	                svo.setViews(rs.getInt   ("views"));
	                // 필요 시 latitude, longitude 등 추가 세팅
	                list.add(svo);	                
		        }
		    } finally {
		        close();
		    }

		    return list;
		}

	    @Override
	    public int totalStayCount() throws SQLException {
	        int total = 0;
	        try {
	            conn = ds.getConnection();
	            String sql = "SELECT COUNT(*) AS cnt FROM tbl_stay";
	            pstmt = conn.prepareStatement(sql);
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	                total = rs.getInt("cnt");
	            }
	        } finally {
	            close();
	        }
	        return total;
	    }


}
