package reservation.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import myshop.domain.ReservationVO;
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
	public ReservationVO selectNextReservation(String userid) throws SQLException {
		ReservationVO rvo = new ReservationVO();
        
    	try {
             conn = ds.getConnection();
             
             String sql = " SELECT * "
             		+ " FROM (\r\n"
             		+ "    SELECT * "
             		+ "    FROM tbl_reservation "
             		+ "    WHERE fk_user_id = ? "
             		+ "      AND checkin_date >= SYSDATE "
             		+ "    ORDER BY checkin_date ASC "
             		+ " ) "
             		+ "WHERE ROWNUM = 1 ";
             
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
             }
             
         } finally {
             close();
         }
         return rvo;
     
	}



}


