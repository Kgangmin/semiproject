package myshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import myshop.domain.ReservationVO;

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
	        				   " (reserv_no, fk_user_id, fk_room_no, reserv_payment, spent_point, checkin_date, checkout_date) " +
	        				   " VALUES (?, ?, ?, ?, ?, TO_DATE(?,'YYYY-MM-DD'), TO_DATE(?,'YYYY-MM-DD')) ";
	        pstmt = conn.prepareStatement(insertSql);
	        pstmt.setString(1, newNo);
	        pstmt.setString(2, rv.getFk_user_id());
	        pstmt.setString(3, rv.getFk_room_no());
	        pstmt.setInt   (4, rv.getReserv_payment());
	        pstmt.setInt   (5, rv.getSpent_point());
	        pstmt.setString(6, rv.getCheckin_date());
	        pstmt.setString(7, rv.getCheckout_date());
	        pstmt.executeUpdate();

	    } finally {
	        // 사용한 자원 모두 반납
	        close();
	    }
	    return newNo;
	}

	
}
