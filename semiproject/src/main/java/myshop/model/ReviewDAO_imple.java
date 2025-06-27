package myshop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import myshop.domain.ReviewVO;
import myshop.domain.RoomVO;

public class ReviewDAO_imple implements ReviewDAO
{
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public ReviewDAO_imple()
	{
		try
		{
			Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/semiproject");
		}
		catch(NamingException e)
		{
			e.printStackTrace();
		}
	}
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close()
	{
		try
		{
			if(rs    != null) {rs.close();	  rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn  != null) {conn.close();  conn=null;}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}	//	end of private void close()---------------------------------------------------
	
	
	//	리뷰 번호에 해당되는 리뷰정보 조회
	@Override
	public ReviewVO selectReview(String reviewno) throws SQLException
	{
		ReviewVO reviewvo = null;
		
		try
		{
			conn = ds.getConnection();
			
			String sql	= " select	fk_stay_no, room_no, review_no, reserv_score, review_contents, review_writedate, fk_reserv_no "
					+ " from "
					+ " ( "
					+ " 	select	* "
					+ " 	from "
					+ " 	( "
					+ " 		select	* "
					+ " 		from	tbl_reservation	A "
					+ " 		join	tbl_review		B "
					+ " 		on		A.reserv_no =	B.fk_reserv_no "
					+ " 		where	review_no =		? "
					+ " 	) "
					+ " )	C "
					+ " join	tbl_room	D "
					+ " on		C.fk_room_no = D.room_no ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reviewno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				reviewvo = new ReviewVO();
				
				reviewvo.setReview_no(rs.getString("review_no"));
				
				RoomVO rvo = new RoomVO();
				rvo.setFk_stay_no(rs.getString("fk_stay_no"));
				rvo.setRoom_no(rs.getString("room_no"));
				reviewvo.setRvo(rvo);
				
				reviewvo.setReview_score(rs.getDouble("review_score"));
				reviewvo.setReview_contents(rs.getString("review_contents"));
				reviewvo.setFk_reserv_no(rs.getString("fk_reserv_no"));
			}
		}
		finally
		{
			close();
		}
		return reviewvo;
	}
}
