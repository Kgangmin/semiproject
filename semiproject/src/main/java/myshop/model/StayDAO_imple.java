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

import myshop.domain.RoomVO;
import myshop.domain.StayVO;
import myshop.domain.StayimgVO;

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


	 // start번째(1-based)부터 len개의 객실 정보를 가져온다.
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
	 // tbl_stay 전체 객실 수를 반환한다.
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
	    
	 // 객실 조회시 조회수를  1증가 시킨다. 
	    @Override
	    public void increaseViews(String stayNo) throws Exception {
	    	
	        String sql = " UPDATE tbl_stay SET views = views + 1 WHERE stay_no = ? ";
	        
	        try {
	            conn = ds.getConnection();
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, stayNo);
	            pstmt.executeUpdate();
	        } finally {
	            close();
	        }
	    }

	 // 숙소 정보를 조회하는 메소드
	    @Override
	    public StayVO selectStay(String stayNo) throws Exception {
	    	 
	    	StayVO svo = new StayVO();
	        
	    	try {
	             conn = ds.getConnection();
	             
	             String sql = " SELECT * "
	             			+ " FROM tbl_stay "
	             			+ " WHERE stay_no = ? ";
	             
	             pstmt = conn.prepareStatement(sql);
	             pstmt.setString(1, stayNo);
	             
	             
	             rs = pstmt.executeQuery();

	             if(rs.next()) {
	                 
	            	 svo.setStay_no(rs.getString("stay_no"));
	            	 svo.setStay_name(rs.getString("stay_name"));
	            	 svo.setFk_stay_category_no(rs.getString("fk_stay_category_no"));
	            	 svo.setStay_thumbnail(rs.getString("stay_thumbnail"));
	            	 svo.setStay_info(rs.getString("stay_info"));
	            	 svo.setStay_tel(rs.getString("stay_tel"));
	            	 svo.setLatitude((rs.getDouble("latitude")));
	            	 svo.setLongitude((rs.getDouble("longitude")));
	            	 svo.setStay_score(rs.getInt("stay_score"));
	            	 svo.setViews(rs.getInt("views"));
	            	 svo.setPostcode(rs.getString("postcode"));
	            	 svo.setAddress(rs.getString("address"));
	            	 svo.setDetailaddres(rs.getString("detailaddress"));
	            	 svo.setExtraaddress(rs.getString("extraaddress"));
               
	             }
	             
	         } finally {
	             close();
	         }
	         return svo;
	     
	    }

	    // 캐러셀에 사용할 추가 이미지 리스트
	    @Override
	    public List<StayimgVO> selectExtraImages(String stayNo) throws Exception {
	    	
	        List<StayimgVO> stayImgList = new ArrayList<>();
	        
	        try {
	            conn = ds.getConnection();
	            
	        	String sql = " SELECT stay_extraimg_no, fk_stay_no, stay_extraimg_no_filename "
		                   + " FROM tbl_stay_extraimg "
		                   + " WHERE fk_stay_no = ? ORDER BY stay_extraimg_no ";
	            
	        	pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, stayNo);
	            rs = pstmt.executeQuery();

	            while(rs.next()) {
	                StayimgVO img = new StayimgVO();
	                img.setStay_extraimg_no(rs.getString("stay_extraimg_no"));
	                img.setFk_stay_no(rs.getString("fk_stay_no"));
	                img.setStay_extraimg_no_filename(rs.getString("stay_extraimg_no_filename"));
	                stayImgList.add(img);
	            }
	        } finally {
	            close();
	        }
	        return stayImgList;
	    }

	 // 객실 리스트
	    @Override
	    public List<RoomVO> selectRooms(String stayNo) throws Exception {
	    	
	        List<RoomVO> roomList = new ArrayList<>();
	        try {
	            conn = ds.getConnection();
	            
	            String sql = " SELECT room_no, fk_stay_no, room_grade, room_thumbnail, price_per_night, room_info "
		                   + " FROM tbl_room "
		                   + " WHERE fk_stay_no = ? ORDER BY room_no ";
	            
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, stayNo);
	            rs = pstmt.executeQuery();

	            while(rs.next()) {
	                RoomVO room = new RoomVO();
	                room.setRoom_no(rs.getString("room_no"));
	                room.setFk_stay_no(rs.getString("fk_stay_no"));
	                room.setRoom_grade(rs.getString("room_grade"));
	                room.setRoom_thumbnail(rs.getString("room_thumbnail"));
	                room.setPrice_per_night(rs.getInt("price_per_night"));
	                room.setRoom_info(rs.getString("room_info"));
	                roomList.add(room);
	            }
	        } finally {
	            close();
	        }
	        return roomList;
	    
	    }
	    
	    
	    // 위시리스트 추가 유무 검사
	    @Override
	    public boolean isWished(String userId, String stayNo) throws SQLException {
	        
	    	boolean result = false;
	        
	    	try {
	            conn = ds.getConnection();
	            
	            String sql = " SELECT COUNT(*) "
	            		   + " FROM tbl_wishlist "
	            		   + " WHERE w_user_id = ? AND w_stay_no = ? ";
	            
	            pstmt = conn.prepareStatement(sql); 
	            pstmt.setString(1, userId);
	            pstmt.setString(2, stayNo);
	            rs = pstmt.executeQuery();
	            if(rs.next()) {
	                result = rs.getInt(1) > 0;
	            }
	        } finally {
	            close();
	        }
	        return result;
	    }

	    // 찜 하기
	    @Override
	    public void insertWishlist(String userId, String stayNo) throws SQLException {
	        
	    	try {
	            conn = ds.getConnection();
	            
	            String sql = " INSERT INTO tbl_wishlist (w_user_id, w_stay_no) VALUES (?, ?) ";
	            
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, userId);
	            pstmt.setString(2, stayNo);
	            pstmt.executeUpdate();
	        } finally {
	            close();
	        }
	    }

	    // 찜 삭제하기
	    @Override
	    public void deleteWishlist(String userId, String stayNo) throws SQLException {
	        
	    	try {
	            conn = ds.getConnection();
	           
	            String sql = " DELETE FROM tbl_wishlist WHERE w_user_id = ? AND w_stay_no = ? ";
	           
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, userId);
	            pstmt.setString(2, stayNo);
	            pstmt.executeUpdate();
	        } finally {
	            close();
	        }
	    }
	    
	    
}
