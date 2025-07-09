package myshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import myshop.domain.PaymentVO;
import myshop.domain.CategoryStatsVO;
import myshop.domain.RegionStatsVO;
import myshop.domain.ReservationVO;
import myshop.domain.RoomVO;
import myshop.domain.StayVO;

public class ReservationDAO_imple implements ReservationDAO {

	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public ReservationDAO_imple() {
		
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
	public String insertReservation(ReservationVO rv) throws Exception {
		
		String newNo = null;
	    try {
	        // 1) 커넥션 가져오기
	        conn = ds.getConnection();

	        // 2) 시퀀스에서 새 예약번호 조회
	        String seqSql = " SELECT seq_reservationNo.nextval FROM dual ";
	        pstmt = conn.prepareStatement(seqSql);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            newNo = "R" + String.format("%06d", rs.getInt(1));
	        }
	        // 시퀀스용 리소스 해제
	        rs.close();
	        pstmt.close();

	        // 3) 예약 정보 INSERT
	        String insertSql = " INSERT INTO tbl_reservation " +
	        				   " (reserv_no, fk_user_id, fk_room_no, checkin_date, checkout_date) " +
	        				   " VALUES (?, ?, ?, TO_DATE(?,'YYYY-MM-DD'), TO_DATE(?,'YYYY-MM-DD')) ";
	        pstmt = conn.prepareStatement(insertSql);
	        pstmt.setString(1, newNo);
	        pstmt.setString(2, rv.getFk_user_id());
	        pstmt.setString(3, rv.getFk_room_no());
	        pstmt.setString(4, rv.getCheckin_date());
	        pstmt.setString(5, rv.getCheckout_date());
	        pstmt.executeUpdate();

	    } finally {
	        // 사용한 자원 모두 반납
	        close();
	    }
	    return newNo;
	}
		
	// 예약 제일 빠른 예약 한건을 가져오는 메소드 
	@Override
	public ReservationVO selectNextReservation(String userid) throws SQLException {
			ReservationVO rvo = null;
	        StayVO svo = null;
	    	try {
	             conn = ds.getConnection();
	             
	             String sql = "SELECT * "
	                     + "FROM ( "
	                     + "    SELECT r.*, s.stay_no "
	                     + "    FROM tbl_reservation r "
	                     + "    JOIN tbl_room rm ON r.fk_room_no = rm.room_no "
	                     + "    JOIN tbl_stay s ON rm.fk_stay_no = s.stay_no "
	                     + "	JOIN tbl_payment p on r.reserv_no = fk_reserv_no "
	                     + "    WHERE r.fk_user_id = ? and p.STATUS = 'paid' "
	                     + "      AND r.checkout_date >= SYSDATE "
	                     + "    ORDER BY r.checkin_date ASC "
	                     + ") "
	                     + "WHERE ROWNUM = 1";

	          pstmt = conn.prepareStatement(sql);
	          pstmt.setString(1, userid);
	             
	             
	             rs = pstmt.executeQuery();

	             if (rs.next()) {
	            	 rvo = new ReservationVO();
	            	 rvo.setReserv_no(rs.getString("reserv_no"));
	            	 rvo.setFk_user_id(rs.getString("fk_user_id"));
	            	 rvo.setFk_room_no(rs.getString("fk_room_no"));
	            	 rvo.setCheckin_date(rs.getString("checkin_date"));
	            	 rvo.setCheckout_date(rs.getString("checkout_date"));
	            	 rvo.setReserv_date(rs.getString("reserv_date"));
	            	 
	            	 svo = new StayVO();
	            	 svo.setStay_no(rs.getString("stay_no"));
	            	 
	            	 rvo.setStayvo(svo);
	             }
	             
	         } finally {
	             close();
	         }
	         return rvo;
	     
		}

	// 모든 예약정보를 가져오는 메소드 
	@Override
	public List<ReservationVO> getReservationList(String userid, String status) throws SQLException{
		 List<ReservationVO> list = new ArrayList<>();
		 String sql = "";
		 ReservationVO rvo = null;
		 StayVO svo =null;
		 RoomVO rmvo = null;
	    	try {
	             conn = ds.getConnection();
	             
	              sql += " SELECT r.*, s.stay_name, s.stay_thumbnail, rm.room_grade, rv.review_no, s.stay_no "
	             		+ " FROM TBL_RESERVATION r JOIN TBL_ROOM rm ON r.fk_room_no = rm.room_no "
	             		+ " JOIN TBL_STAY s ON rm.fk_stay_no = s.stay_no "
	             		+ " LEFT JOIN TBL_REVIEW rv ON r.reserv_no = rv.fk_reserv_no "
	             		+ " WHERE r.fk_user_id = ? ";
	              
	              // 상태 필터 추가
	              if ("진행중".equals(status)) {
	                  sql += " AND r.checkout_date > CURRENT_DATE ";
	              } else if ("완료".equals(status)) {
	                  sql += " AND r.checkout_date <= CURRENT_DATE ";
	              }
	              
	              sql += " ORDER BY r.checkin_date DESC " ;
	              
	              pstmt = conn.prepareStatement(sql);
	              pstmt.setString(1, userid);
	             
	             
	             rs = pstmt.executeQuery();
	             Date today = new Date(); 
	             while (rs.next()) {
	            	 rvo = new ReservationVO();

	                 rvo.setReserv_no(rs.getString("reserv_no"));
	                 rvo.setCheckin_date(rs.getString("checkin_date"));
	                 rvo.setCheckout_date(rs.getString("checkout_date"));
	                 rvo.setReview_written(rs.getString("review_no") != null); // 후기 작성 여부
	                 
	                 Date checkout = rs.getDate("checkout_date");
	                 String c_status = checkout.after(today) ? "진행중" : "완료";
	                 rvo.setReserv_status(c_status);
	                 
	                 svo = new StayVO();
	                 svo.setStay_name(rs.getString("stay_name"));
	                 svo.setStay_thumbnail(rs.getString("stay_thumbnail"));
	                 svo.setStay_no(rs.getString("stay_no"));
	                 rvo.setStayvo(svo);
	                 
	                 rmvo = new RoomVO();
	                 rmvo.setRoom_grade(rs.getString("room_grade"));
	                 rvo.setRoomvo(rmvo);
	                 
	                 list.add(rvo); 
	             }
	             
	         } finally {
	             close();
	         }

		
		 
		 return list;
		 
	}

	// 모든 예약정보와 객실 숙소 정보를 가져오는 메소드
		@Override
		public ReservationVO getReservationDetail(String reserv_no) throws SQLException {
			
			 ReservationVO rvo = null;

		        try {
		            conn = ds.getConnection(); // 커넥션은 프로젝트에 맞게 설정하세요

		            String sql = "SELECT r.reserv_no, " +
		                    "       r.fk_user_id, " +
		                    "       r.reserv_date, " +
		                    "       r.checkin_date, " +
		                    "       r.checkout_date, " +
		                    "       s.stay_name, " +
		                    "       s.stay_thumbnail, " +
		                    "       s.stay_tel, " +
		                    "       s.address, " +
		                    "       s.detailaddress, " +
		                    "       s.extraaddress, " +
		                    "       s.stay_score, " +
		                    "       ro.room_grade, " +
		                    "       ro.price_per_night, " +
		                    "       ro.room_thumbnail, " + 
		                    "       rv.review_no, " +
		                    " 		p.imp_uid, "+
		                    " 		p.paid_amount, "+
		                    " 		p.used_point "+
		                    " FROM tbl_reservation r " +
		                    " JOIN tbl_room ro ON r.fk_room_no = ro.room_no " +
		                    " JOIN tbl_stay s ON s.stay_no = ro.fk_stay_no " + 
		                    " LEFT JOIN TBL_REVIEW rv ON r.reserv_no = rv.fk_reserv_no " +
		                    " LEFT JOIN tbl_payment p ON r.reserv_no = p.fk_reserv_no " +
		                    " WHERE r.reserv_no = ? ";

		            pstmt = conn.prepareStatement(sql);
		            pstmt.setString(1, reserv_no);
		            rs = pstmt.executeQuery();

		            if (rs.next()) {
		                rvo = new ReservationVO();

		                // 예약 정보
		                rvo.setReserv_no(rs.getString("reserv_no"));
		                rvo.setFk_user_id(rs.getString("fk_user_id"));
		                rvo.setReserv_date(rs.getString("reserv_date"));
		                rvo.setCheckin_date(rs.getString("checkin_date"));
		                rvo.setCheckout_date(rs.getString("checkout_date"));
		                rvo.setReview_written(rs.getString("review_no") != null);  // boolean 처리
		                rvo.setImp_uid(rs.getString("imp_uid"));
		                rvo.setPaid_amount(rs.getInt("paid_amount"));
		                rvo.setUsed_point(rs.getInt("used_point"));

		                // 숙소 정보
		                StayVO svo = new StayVO();
		                svo.setStay_name(rs.getString("stay_name"));
		                svo.setStay_thumbnail(rs.getString("stay_thumbnail"));
		                svo.setStay_tel(rs.getString("stay_tel"));
		                svo.setAddress(rs.getString("address"));
		                svo.setDetailaddres(rs.getString("detailaddress"));
		                svo.setExtraaddress(rs.getString("extraaddress"));
		                svo.setStay_score(rs.getInt("stay_score"));

		                rvo.setStayvo(svo);

		                // 객실 정보
		                RoomVO roomvo = new RoomVO();
		                roomvo.setRoom_grade(rs.getString("room_grade"));
		                roomvo.setPrice_per_night(rs.getInt("price_per_night"));
		                roomvo.setRoom_thumbnail(rs.getString("room_thumbnail"));

		                rvo.setRoomvo(roomvo);
		            }

		        } finally {
		            close();
		        }

		        return rvo;
		}
	
	//	페이징 처리 한 모든 예약보기
	   
	   @Override
	   public List<ReservationVO> getReservationListByPaging(String userid, String status, int offset, int size) throws SQLException {
	       List<ReservationVO> list = new ArrayList<>();
	       try {
	           conn = ds.getConnection();

	           StringBuilder sql = new StringBuilder();
	           sql.append("SELECT r.*, s.stay_name, s.stay_thumbnail, rm.room_grade, rv.review_no, s.stay_no, p.imp_uid, p.status AS payment_status, p.paid_amount, p.used_point ")
	              .append("FROM TBL_RESERVATION r ")
	              .append("JOIN TBL_ROOM rm ON r.fk_room_no = rm.room_no ")
	              .append("JOIN TBL_STAY s ON rm.fk_stay_no = s.stay_no ")
	              .append("LEFT JOIN TBL_REVIEW rv ON r.reserv_no = rv.fk_reserv_no ")
	              .append("LEFT JOIN tbl_payment p ON r.reserv_no = p.fk_reserv_no ")
	              .append("WHERE r.fk_user_id = ? ");

	           // 상태별 필터링 조건
	           if ("진행중".equals(status)) {
	               sql.append("AND r.checkout_date > CURRENT_DATE ");
	               sql.append("AND NVL(p.status, 'paid') != 'cancelled' "); // 취소된 건 제외
	           } else if ("완료".equals(status)) {
	               sql.append("AND r.checkout_date <= CURRENT_DATE ");
	               sql.append("AND NVL(p.status, 'paid') != 'cancelled' "); // 취소된 건 제외
	           } else if ("취소".equals(status)) {
	               sql.append("AND p.status = 'cancelled' ");
	           }

	           sql.append("ORDER BY r.checkin_date desc ")
	              .append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

	           pstmt = conn.prepareStatement(sql.toString());
	           pstmt.setString(1, userid);
	           pstmt.setInt(2, offset); // (page-1) * size
	           pstmt.setInt(3, size);

	           rs = pstmt.executeQuery();
	           Date today = new Date();

	           while (rs.next()) {
	               ReservationVO rvo = new ReservationVO();
	               StayVO svo = new StayVO();
	               RoomVO rmvo = new RoomVO();

	               rvo.setReserv_no(rs.getString("reserv_no"));
	               rvo.setCheckin_date(rs.getString("checkin_date"));
	               rvo.setCheckout_date(rs.getString("checkout_date"));
	               rvo.setReview_written(rs.getString("review_no") != null);
	               rvo.setImp_uid(rs.getString("imp_uid"));
	               rvo.setPaid_amount(rs.getInt("paid_amount"));
	               rvo.setUsed_point(rs.getInt("used_point"));

	               // 상태 설정
	               String paymentStatus = rs.getString("payment_status");
	               String c_status = "취소됨";

	               if (!"cancelled".equalsIgnoreCase(paymentStatus)) {
	                   Date checkout = rs.getDate("checkout_date");
	                   c_status = checkout.after(today) ? "진행중" : "완료";
	               }

	               rvo.setReserv_status(c_status);

	               svo.setStay_name(rs.getString("stay_name"));
	               svo.setStay_thumbnail(rs.getString("stay_thumbnail"));
	               svo.setStay_no(rs.getString("stay_no"));
	               rvo.setStayvo(svo);

	               rmvo.setRoom_grade(rs.getString("room_grade"));
	               rvo.setRoomvo(rmvo);

	               list.add(rvo);
	           }
	       } finally {
	           close();
	       }

	       return list;
	   }



	// 모든예약의 개수를 구하는 메소드
	@Override
	public int getReservationCount(String userid, String status) throws SQLException {
		 int totalCount = 0;

		    try {
		        conn = ds.getConnection();

		        StringBuilder sql = new StringBuilder();
		        sql.append("SELECT COUNT(*) AS CNT ");
		        sql.append("FROM TBL_RESERVATION R ");
		        sql.append("JOIN TBL_PAYMENT P ON R.RESERV_NO = P.FK_RESERV_NO ");
		        sql.append("WHERE P.FK_USER_ID = ? ");

	       
	           if ("진행중".equals(status)) {
	               sql.append("AND r.checkout_date > CURRENT_DATE ");
	               sql.append("AND NVL(p.status, 'paid') != 'cancelled' "); // 취소된 건 제외
	           } else if ("완료".equals(status)) {
	               sql.append("AND r.checkout_date <= CURRENT_DATE ");
	               sql.append("AND NVL(p.status, 'paid') != 'cancelled' "); // 취소된 건 제외
	           } else if ("취소".equals(status)) {
	               sql.append("AND p.status = 'cancelled' ");
	           }

		        pstmt = conn.prepareStatement(sql.toString());
		        pstmt.setString(1, userid);

		        rs = pstmt.executeQuery();

		        if (rs.next()) {
		            totalCount = rs.getInt("CNT");
		       
		        }

		    } finally {
		        close();
		    }

		    return totalCount;
		}


	@Override
	public void insertPaymentHistory(PaymentVO pvo) throws SQLException {
	    try {
	    	conn = ds.getConnection();

	        // 결제 ID 채번
	        String sql_seq = "SELECT 'PM' || LPAD(seq_paymentid.nextval, 5, '0') FROM dual";
	        pstmt = conn.prepareStatement(sql_seq);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            pvo.setPayment_id(rs.getString(1));
	        }
	        rs.close();
	        pstmt.close();

	        // INSERT
	        String sql = "INSERT INTO tbl_payment " +
	                     "(payment_id, imp_uid, fk_reserv_no, fk_user_id, paid_amount, used_point, earned_point, pay_method, status, pay_time, total_payment_stamp) " +
	                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'), ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, pvo.getPayment_id());
	        pstmt.setString(2, pvo.getImp_uid());
	        pstmt.setString(3, pvo.getFk_reserv_no());
	        pstmt.setString(4, pvo.getFk_user_id());
	        pstmt.setInt   (5, pvo.getPaid_amount());
	        pstmt.setInt   (6, pvo.getUsed_point());
	        pstmt.setInt   (7, pvo.getEarned_point());
	        pstmt.setString(8, pvo.getPay_method());
	        pstmt.setString(9, pvo.getStatus());
	        pstmt.setString(10, pvo.getPay_time());
	        pstmt.setInt   (11, pvo.getTotal_payment_stamp());

	        pstmt.executeUpdate();

	    } finally {
	        close();
	    }
	}

	//	DB에서 결제내역 조회
	@Override
	public PaymentVO selectPaymentByImpUid(String imp_uid) throws SQLException
	{
		PaymentVO pmvo = null;
		
		try
		{
			conn = ds.getConnection();
			
			String sql = " select * from tbl_payment where imp_uid = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, imp_uid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				pmvo = new PaymentVO();
                pmvo.setPayment_id(rs.getString("payment_id"));
                pmvo.setImp_uid(rs.getString("imp_uid"));
                pmvo.setFk_reserv_no(rs.getString("fk_reserv_no"));
                pmvo.setFk_user_id(rs.getString("fk_user_id"));
                pmvo.setPaid_amount(rs.getInt("paid_amount"));
                pmvo.setUsed_point(rs.getInt("used_point"));
                pmvo.setEarned_point(rs.getInt("earned_point"));
                pmvo.setPay_method(rs.getString("pay_method"));
                pmvo.setStatus(rs.getString("status"));
                pmvo.setPay_time(rs.getString("pay_time"));
                pmvo.setTotal_payment_stamp(rs.getInt("total_payment_stamp"));
			}
		}
		finally
		{
			close();
		}
        return pmvo;
	}

	//	결제내역 상태 'cancelled' 및 취소시간 업데이트
	@Override
	public int updatePaymentStatusToCancelled(String imp_uid, String cancel_time) throws SQLException
	{
	    int result = 0;
	    
	    try {
	        conn = ds.getConnection();
	        String sql = "UPDATE tbl_payment SET status = 'cancelled', cancel_time = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') WHERE imp_uid = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, cancel_time);
	        pstmt.setString(2, imp_uid);
	        result = pstmt.executeUpdate();
	    } finally {
	        close();
	    }
	    return result;
	}

	 // 각 카테고리별 예약 건수·총 결제액 집계 
	@Override
   public List<CategoryStatsVO> getCategoryReservationStats() throws SQLException {
      
		List<CategoryStatsVO> list = new ArrayList<>();
       
       try {
           conn = ds.getConnection();
           
           String sql = " SELECT "
           		   + "  c.stay_category_name  AS cname, "
           		   + "  COUNT(*)         AS cnt "
           		   + " FROM tbl_reservation r "
           		   + " JOIN tbl_room            rm ON r.fk_room_no = rm.room_no "
           		   + " JOIN tbl_stay            s  ON rm.fk_stay_no = s.stay_no "
           		   + " JOIN tbl_stay_category   c  ON s.fk_stay_category_no = c.stay_category_no "
           		   + " GROUP BY c.stay_category_name ";
           		
           
           pstmt = conn.prepareStatement(sql);
           rs = pstmt.executeQuery();
           while(rs.next()) {
           	CategoryStatsVO vo = new CategoryStatsVO();
               vo.setCname(rs.getString("cname"));
               vo.setCnt(rs.getInt("cnt"));
               list.add(vo);
           }
           return list;
       } finally {
           close();
       }
   }

	// 지역별(서울, 경기, …, 기타) 예약 건수·총 결제액 집계
   @Override
   public List<RegionStatsVO> getRegionReservationStats() throws SQLException {

   	List<RegionStatsVO> list = new ArrayList<>();
       
       try {
           conn = ds.getConnection();
           
           String sql = " SELECT "
           		+ "    r.region, "
           		+ "    NVL(t.cnt, 0)    AS cnt "
           		+ " FROM ( "
           		+ "    SELECT '서울'     AS region FROM DUAL UNION ALL "
           		+ "    SELECT '경기'     FROM DUAL UNION ALL "
           		+ "    SELECT '강원'     FROM DUAL UNION ALL "
           		+ "    SELECT '충청북도'  FROM DUAL UNION ALL "
           		+ "    SELECT '충청남도'  FROM DUAL UNION ALL "
           		+ "    SELECT '경상북도'  FROM DUAL UNION ALL "
           		+ "    SELECT '경상남도'  FROM DUAL UNION ALL "
           		+ "    SELECT '부산'     FROM DUAL UNION ALL "
           		+ "    SELECT '제주'     FROM DUAL UNION ALL "
           		+ "    SELECT '전북'     FROM DUAL UNION ALL "
           		+ "    SELECT '전라남도'  FROM DUAL UNION ALL "
           		+ "    SELECT '기타'     FROM DUAL "
           		+ " ) r "
           		+ " LEFT JOIN ( "
           		+ "    SELECT "
           		+ "        CASE "
           		+ "            WHEN s.address LIKE '서울%'    THEN '서울' "
           		+ "            WHEN s.address LIKE '경기%'    THEN '경기' "
           		+ "            WHEN s.address LIKE '강원%'    THEN '강원' "
           		+ "            WHEN s.address LIKE '충청북도%' THEN '충청북도' "
           		+ "            WHEN s.address LIKE '충청남도%' THEN '충청남도' "
           		+ "            WHEN s.address LIKE '경상북도%' THEN '경상북도' "
           		+ "            WHEN s.address LIKE '경상남도%' THEN '경상남도' "
           		+ "            WHEN s.address LIKE '부산%'    THEN '부산' "
           		+ "            WHEN s.address LIKE '제주%'    THEN '제주' "
           		+ "            WHEN s.address LIKE '전북%'    THEN '전북' "
           		+ "            WHEN s.address LIKE '전라남도%' THEN '전라남도' "
           		+ "            ELSE '기타' "
           		+ "        END AS region, "
           		+ "        COUNT(*)               AS cnt "
           		+ "    FROM tbl_reservation r "
           		+ "    JOIN tbl_room       rm ON r.fk_room_no = rm.room_no "
           		+ "    JOIN tbl_stay       s  ON rm.fk_stay_no = s.stay_no "
           		+ "    GROUP BY "
           		+ "        CASE "
           		+ "            WHEN s.address LIKE '서울%'    THEN '서울' "
           		+ "            WHEN s.address LIKE '경기%'    THEN '경기' "
           		+ "            WHEN s.address LIKE '강원%'    THEN '강원' "
           		+ "            WHEN s.address LIKE '충청북도%' THEN '충청북도' "
           		+ "            WHEN s.address LIKE '충청남도%' THEN '충청남도' "
           		+ "            WHEN s.address LIKE '경상북도%' THEN '경상북도' "
           		+ "            WHEN s.address LIKE '경상남도%' THEN '경상남도' "
           		+ "            WHEN s.address LIKE '부산%'    THEN '부산' "
           		+ "            WHEN s.address LIKE '제주%'    THEN '제주' "
           		+ "            WHEN s.address LIKE '전북%'    THEN '전북' "
           		+ "            WHEN s.address LIKE '전라남도%' THEN '전라남도' "
           		+ "            ELSE '기타' "
           		+ "        END "
           		+ " ) t "
           		+ "  ON r.region = t.region "
           		+ " ORDER BY "
           		+ "    DECODE( "
           		+ "        r.region, "
           		+ "        '서울', 1, '경기', 2, '강원', 3, '충청북도', 4, '충청남도', 5, "
           		+ "        '경상북도', 6, '경상남도', 7, '부산', 8, '제주', 9, "
           		+ "        '전북', 10, '전라남도', 11, '기타', 12 "
           		+ "    ) ";
           
           pstmt = conn.prepareStatement(sql);
           rs = pstmt.executeQuery();
           while(rs.next()) {
               RegionStatsVO vo = new RegionStatsVO();
               vo.setRegion(rs.getString("region"));
               vo.setCnt(rs.getInt   ("cnt"));
               list.add(vo);
           }
           return list;
       } finally {
           close();
       }
   }

}
