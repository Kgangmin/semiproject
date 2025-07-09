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

import myshop.domain.CategoryVO;
import myshop.domain.RoomVO;
import myshop.domain.StayLocationVO;
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
	                svo.setStay_score(rs.getDouble("stay_score"));
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
	            		   + "    SELECT * FROM tbl_stay"
	            		   + "    WHERE fk_stay_category_no = ? "
	            		   + "    ORDER BY stay_no " 
	            		   + " ) s WHERE ROWNUM <= ? " 
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
	                svo.setStay_score(rs.getDouble("stay_score"));
	                svo.setViews(rs.getInt   ("views"));
	                // 필요 시 latitude, longitude 등 추가 세팅
	                list.add(svo);	                
		        }
		    } finally {
		        close();
		    }

		    return list;
		}
		
		//	카테고리 리스트를 알아온다.
		@Override
		public List<CategoryVO> getCategoryList() throws SQLException
		{
			List<CategoryVO> categoryList = new ArrayList<>();
		    
		    try
		    {
		    	conn = ds.getConnection();
		    	
		    	String sql = "SELECT stay_category_no, stay_category_name FROM tbl_stay_category ORDER BY stay_category_no ASC";
		    	pstmt = conn.prepareStatement(sql);
		    	rs = pstmt.executeQuery();
		        
		    	while (rs.next())
		    	{
		    		CategoryVO cvo = new CategoryVO();
		    		cvo.setStay_category_no(rs.getString("stay_category_no"));
		    		cvo.setStay_category_name(rs.getString("stay_category_name"));
		    		categoryList.add(cvo);
		        }
		    }
		    finally
		    {
		    	close();
		    }
		    return categoryList;
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
	            	 svo.setStay_score(rs.getDouble("stay_score"));
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
	    
	    
	 // 키워드+기간으로 이용 가능한 숙소 검색
	    @Override
	    public List<StayVO> searchAvailableStays(String keyword,
	                                             String checkinDate,
	                                             String checkoutDate,
	                                             int start,
	                                             int len) throws SQLException {
	        List<StayVO> list = new ArrayList<>();
	        
	        String kw = "%" + keyword + "%";
	        
	        try {
	            conn = ds.getConnection();
	            String sql = " SELECT * FROM ( " +
	            			 "  SELECT ROWNUM rn, t.* FROM ( " +
	            			 "    SELECT DISTINCT st.* " +
	            			 "    FROM tbl_stay st " +
	            			 "    JOIN tbl_room r ON r.fk_stay_no = st.stay_no " +
	            			 "    WHERE (st.stay_name    LIKE ? OR " +
	            			 "           st.address       LIKE ? OR " +
	            			 "           st.detailaddress LIKE ? OR " +
	            			 "           st.extraaddress  LIKE ?) " +
	            			 "      AND NOT EXISTS ( " +
	            			 "        SELECT 1 FROM tbl_reservation rv " +
	            			 "        WHERE rv.fk_room_no   = r.room_no " +
	            			 "          AND rv.checkin_date  <= TO_DATE(?,'YYYY-MM-DD') " +
	            			 "          AND rv.checkout_date >= TO_DATE(?,'YYYY-MM-DD') " +
	            			 "      ) " +
	            			 "    ORDER BY st.stay_no " +
	            			 "  ) t WHERE ROWNUM <= ? " +
	            			 " ) WHERE rn >= ?";
	            
	            pstmt = conn.prepareStatement(sql);
	            
	            // 1~4: 키워드
	            pstmt.setString(1, kw);
	            pstmt.setString(2, kw);
	            pstmt.setString(3, kw);
	            pstmt.setString(4, kw);
	            // 5: 예약 시작일 ≤ 내가 체크아웃일
	            pstmt.setString(5, checkoutDate);
	            // 6: 예약 종료일 ≥ 내가 체크인일
	            pstmt.setString(6, checkinDate);
	            // 7: 페이징 상한
	            pstmt.setInt(7, start + len - 1);
	            // 8: 페이징 하한
	            pstmt.setInt(8, start);
	            
	            rs = pstmt.executeQuery();
	            
	            while (rs.next()) {
	                StayVO svo = new StayVO();
	                svo.setStay_no(rs.getString("stay_no"));
	                svo.setStay_name(rs.getString("stay_name"));
	                svo.setStay_info(rs.getString("stay_info"));
	                svo.setStay_thumbnail(rs.getString("stay_thumbnail"));
	                svo.setStay_score(rs.getDouble("stay_score"));
	                svo.setViews(rs.getInt("views"));
	                list.add(svo);
	            }
	        } finally {
	            close();
	        }
	        return list;
	    }

	    
	 // 키워드+기간으로 이용 가능한 숙소 총 개수
	    @Override
	    public int totalAvailableCount(String keyword,
	                                   String checkinDate,
	                                   String checkoutDate) throws SQLException {
	    	
	        int total = 0;
	        
	        String kw = "%" + keyword + "%";
	        
	        try {
	            conn = ds.getConnection();
	            
	            String sql = " SELECT COUNT(DISTINCT st.stay_no) AS cnt " +
	            			 " FROM tbl_stay st " +
	            			 " JOIN tbl_room r ON r.fk_stay_no = st.stay_no " +
	            			 " WHERE (st.stay_name    LIKE ? OR " +
	            			 "       st.address       LIKE ? OR " +
	            			 "       st.detailaddress LIKE ? OR " +
	            			 "       st.extraaddress  LIKE ?) " +
	            			 "  AND NOT EXISTS ( " +
	            			 "    SELECT 1 FROM tbl_reservation rv " +
	            			 "    WHERE rv.fk_room_no   = r.room_no " +
	            			 "      AND rv.checkin_date  <= TO_DATE(?,'YYYY-MM-DD') " +
	            			 "      AND rv.checkout_date >= TO_DATE(?,'YYYY-MM-DD') " +
	            			 "  ) ";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, kw);
	            pstmt.setString(2, kw);
	            pstmt.setString(3, kw);
	            pstmt.setString(4, kw);
	            pstmt.setString(5, checkoutDate);
	            pstmt.setString(6, checkinDate);
	            
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	            	total = rs.getInt("cnt");
	            }
	        } finally {
	            close();
	        }
	        return total;
	    }
	    
	    
	 // 기간에 예약이 겹치지 않는 객실만 조회
	    @Override
	    public List<RoomVO> selectAvailableRooms(String stayNo,
	                                             String checkinDate,
	                                             String checkoutDate) throws SQLException {
	    	
	        List<RoomVO> roomList = new ArrayList<>();
	        
	        try {
	            conn = ds.getConnection();
	            
	            String sql = " SELECT r.room_no, r.fk_stay_no, r.room_grade, r.room_thumbnail, r.price_per_night, r.room_info " +
	            			 " FROM tbl_room r " +
	            			 " WHERE r.fk_stay_no = ? " +
	            			 "  AND NOT EXISTS ( " +
	            			 "    SELECT 1 FROM tbl_reservation rv " +
	            			 "    WHERE rv.fk_room_no   = r.room_no " +
	            			 "      AND rv.checkin_date  <= TO_DATE(?,'YYYY-MM-DD') " +
	            			 "      AND rv.checkout_date >= TO_DATE(?,'YYYY-MM-DD') " +
	            			 "  ) " +
	            			 " ORDER BY r.room_no";
	            
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, stayNo);
	            pstmt.setString(2, checkoutDate);
	            pstmt.setString(3, checkinDate);
	            
	            rs = pstmt.executeQuery();
	            
	            while (rs.next()) {
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



	    //  숙소 정보 가져오는 메서드(관리자페이지에서 조회)
		@Override
		public List<StayVO> getStayList(String searchWord, int offset, int limit) throws SQLException {
			
			  List<StayVO> stayList = new ArrayList<>();
			    
			    try {
			        conn = ds.getConnection();

			        String sql = " select stay_no, stay_name, stay_tel, stay_score "
			                   + " from tbl_stay ";
			                   

			        if (searchWord != null && !searchWord.trim().isEmpty()) {
			            sql += " where stay_name like ? ";
			        }

			        sql += " order by to_number(stay_no) asc "
			             + " offset ? rows fetch next ? rows only ";

			        pstmt = conn.prepareStatement(sql);
			        

					if (searchWord != null && !searchWord.trim().isEmpty()) {
					    pstmt.setString(1, "%" + searchWord + "%");
					    pstmt.setInt(2, offset);
					    pstmt.setInt(3, limit);
					} else {
					    pstmt.setInt(1, offset);
					    pstmt.setInt(2, limit);
					}
					 rs = pstmt.executeQuery();

				        while (rs.next()) {
				            StayVO stay = new StayVO();
				            stay.setStay_no(rs.getString("stay_no"));
				            stay.setStay_name(rs.getString("stay_name"));
				            stay.setStay_tel(rs.getString("stay_tel"));
				            stay.setStay_score(rs.getDouble("stay_score"));                               

				            stayList.add(stay);
				        }
				    } finally {
				        close(); // 자원 반납 메서드 호출 (ResultSet, PreparedStatement, Connection)
				    }

				    return stayList;
		}


		// 호텔 총 개수 조회 (검색어)
		@Override
		public int getStayTotalCount(String searchWord) throws SQLException {
		    int totalCount = 0;

		    try {
		        conn = ds.getConnection();

		        String sql = " select count(*) as totalCount from tbl_stay ";

		        if (searchWord != null && !searchWord.trim().isEmpty()) {
		            sql += " WHERE stay_name LIKE ? ";
		        }

		        pstmt = conn.prepareStatement(sql);

		        if (searchWord != null && !searchWord.trim().isEmpty()) {
		            pstmt.setString(1, "%" + searchWord + "%");
		        }

		        rs = pstmt.executeQuery();

		        if (rs.next()) {
		            totalCount = rs.getInt("totalCount");
		        }
		    } finally {
		        close();
		    }

		    return totalCount;
		}



	    


	    // 방의 번호로 숙소 이름을 찾는 메소드 
		@Override
		public StayVO search_stay_name(String fk_room_no) throws SQLException {
	        StayVO stay = null;
	        
	        try {
	            conn = ds.getConnection();
	            
	            String sql = " SELECT s.stay_no, s.stay_name " +
	                    	 " FROM tbl_stay s JOIN tbl_room r ON s.stay_no = r.fk_stay_no " +
	                    	 " WHERE r.room_no = ? ";
	            
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, fk_room_no);
	            
	            rs = pstmt.executeQuery();
	            
	            if (rs.next()) {
	                stay = new StayVO();
	                stay.setStay_no(rs.getString("stay_no"));
	                stay.setStay_name(rs.getString("stay_name"));
	            }
	        } finally {
	            close();
	        }
	        return stay;
	    
		}
		
		
		// stayNo값 읽어오기
		@Override
		public String getNextStayNo() throws SQLException {
			
			try {
				conn = ds.getConnection();
				
				String sql = " SELECT seq_stayNo.nextval AS no FROM dual ";
			  
				pstmt = conn.prepareStatement(sql);
				rs   = pstmt.executeQuery();
			  
				rs.next();
				return rs.getString("no");
			  
				} finally {
					close();
				}
		}

		   
		  
		// tbl_stay 에 새 숙소 정보를 insert하는 메소드
	    @Override
	    public int insertStay(StayVO svo) throws SQLException {
	        String sql =
	            "INSERT INTO tbl_stay (" +
	            "  stay_no, stay_name, fk_stay_category_no, stay_thumbnail," +
	            "  stay_info, stay_tel, latitude, longitude," +
	            "  postcode, address, detailaddress, extraaddress" +
	            ") VALUES (" +
	            "  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
	            ")";
	        try {
	            conn = ds.getConnection();
	            pstmt = conn.prepareStatement(sql);
	          
	            pstmt.setString(1, svo.getStay_no());
	            pstmt.setString(2, svo.getStay_name());
	            pstmt.setString(3, svo.getFk_stay_category_no());
	            pstmt.setString(4, svo.getStay_thumbnail());
	            pstmt.setString(5, svo.getStay_info());
	            pstmt.setString(6, svo.getStay_tel());
	            pstmt.setDouble(7, svo.getLatitude());
	            pstmt.setDouble(8, svo.getLongitude());
	            pstmt.setString(9, svo.getPostcode());
	            pstmt.setString(10, svo.getAddress());
	            pstmt.setString(11, svo.getDetailaddres());
	            pstmt.setString(12, svo.getExtraaddress());
	            return pstmt.executeUpdate();
	        } finally {
	            close();
	        }
	    }

		    
		// tbl_stay_extraimg 에 추가 이미지 정보 insert 하는 메소드
	    @Override
	    public int insertExtraImage(StayimgVO img) throws SQLException {
	        String sql =
	            " INSERT INTO tbl_stay_extraimg (" +
	            "  stay_extraimg_no, fk_stay_no, stay_extraimg_no_filename" +
	            " ) VALUES (?,?,?)";
	        try {
	            conn = ds.getConnection();
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, img.getStay_extraimg_no());
	            pstmt.setString(2, img.getFk_stay_no());
	            pstmt.setString(3, img.getStay_extraimg_no_filename());
	            return pstmt.executeUpdate();
	        } finally {
	            close();
	        }
	    }

		
	 // 지도 구현 정보 메소드
	    @Override
	    public List<StayLocationVO> selectAllStayLocations() throws SQLException {
	        List<StayLocationVO> list = new ArrayList<>();
	        String sql = 
	          "SELECT s.stay_no, s.stay_name, s.latitude, s.longitude, "
	        + "       MIN(r.price_per_night) AS minPrice, s.stay_thumbnail "
	        + "FROM tbl_stay s "
	        + "JOIN tbl_room r ON s.stay_no = r.fk_stay_no "
	        + "GROUP BY s.stay_no, s.stay_name, s.latitude, s.longitude, s.stay_thumbnail";
	        try {
	          conn = ds.getConnection();
	          pstmt = conn.prepareStatement(sql);
	          rs = pstmt.executeQuery();
	          while(rs.next()) {
	            StayLocationVO vo = new StayLocationVO();
	            vo.setStay_no(rs.getString("stay_no"));
	            vo.setStay_name(rs.getString("stay_name"));
	            vo.setLatitude(rs.getDouble("latitude"));
	            vo.setLongitude(rs.getDouble("longitude"));
	            vo.setMinPrice(rs.getInt("minPrice"));
	            vo.setStay_thumbnail(rs.getString("stay_thumbnail"));
	            list.add(vo);
	          }
	          return list;
	        } finally {
	          close();
	        }
	    }

		

}
