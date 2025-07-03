package myshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
	        				   " (reserv_no, fk_user_id, fk_room_no, reserv_payment, spent_point, checkin_date, checkout_date, imp_uid) " +
	        				   " VALUES (?, ?, ?, ?, ?, TO_DATE(?,'YYYY-MM-DD'), TO_DATE(?,'YYYY-MM-DD'), ?) ";
	        pstmt = conn.prepareStatement(insertSql);
	        pstmt.setString(1, newNo);
	        pstmt.setString(2, rv.getFk_user_id());
	        pstmt.setString(3, rv.getFk_room_no());
	        pstmt.setInt   (4, rv.getReserv_payment());
	        pstmt.setInt   (5, rv.getSpent_point());
	        pstmt.setString(6, rv.getCheckin_date());
	        pstmt.setString(7, rv.getCheckout_date());
	        pstmt.setString(8, rv.getImp_uid());
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
	                     + "    WHERE r.fk_user_id = ? "
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
	            	 rvo.setReserv_payment(rs.getInt("reserv_payment"));
	            	 rvo.setSpent_point(rs.getInt("spent_point"));
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
	                 rvo.setReserv_payment(rs.getInt("reserv_payment"));
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
	                    "       r.reserv_payment, " +
	                    "       r.spent_point, " +
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
	                    "       rv.review_no " +
	                    " FROM tbl_reservation r " +
	                    " JOIN tbl_room ro ON r.fk_room_no = ro.room_no " +
	                    " JOIN tbl_stay s ON s.stay_no = ro.fk_stay_no " + 
	                    " LEFT JOIN TBL_REVIEW rv ON r.reserv_no = rv.fk_reserv_no " +
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
	                rvo.setReserv_payment(rs.getInt("reserv_payment"));
	                rvo.setSpent_point(rs.getInt("spent_point"));
	                rvo.setReview_written(rs.getString("review_no") != null);  // boolean 처리

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

	


	
}
