package myshop.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import myshop.domain.ReservationVO;
import myshop.domain.ReviewVO;
import myshop.domain.RoomVO;

public class ReviewDAO_imple implements ReviewDAO
{
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ResultSet rs_2;
	
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
	
	//	페이징 처리를 위한 해당 숙소의 모든 리뷰 수
	@Override
	public int countAllReview(String stayno, String user_id) throws SQLException
	{
		int count = 0;
		try
	    {
	    	conn = ds.getConnection();
	    	
	        String sql = " select	count(*) "
	                   + " from		tbl_review A "
	                   + " join		tbl_reservation B ON A.fk_reserv_no = B.reserv_no "
	                   + " join		tbl_room C ON B.fk_room_no = C.room_no ";
	        if(stayno != null && !stayno.isEmpty())			{sql+=" 		where 	fk_stay_no = ? ";}
			else if(user_id != null && !user_id.isEmpty())	{sql+=" 		where	fk_user_id = ? ";}
	        
	        pstmt = conn.prepareStatement(sql);
	        if(stayno != null && !stayno.isEmpty())			{pstmt.setString(1, stayno);}
	        else if(user_id != null && !user_id.isEmpty())	{pstmt.setString(1, user_id);}
	        
	        rs = pstmt.executeQuery();
	        
	        if(rs.next())
	        {
	        	count = rs.getInt(1);
	        }
	    }
		finally { close(); }
	    return count;
	}

	//	페이징 처리를 위한 해당 숙소의 특정 객실 등급의 리뷰 수
	@Override
	public int countGradeReview(String stayNo, String roomGrade) throws SQLException
	{
		int count = 0;
	    try
	    {
	    	conn = ds.getConnection();
	    	
	        String sql = " select	COUNT(*) "
	                   + " from		tbl_review A "
	                   + " join		tbl_reservation B ON A.fk_reserv_no = B.reserv_no "
	                   + " join		tbl_room C ON B.fk_room_no = C.room_no "
	                   + " where	fk_stay_no = ? AND room_grade = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, stayNo);
	        pstmt.setString(2, roomGrade);
	        
	        rs = pstmt.executeQuery();
	        
	        if(rs.next())
	        {
	        	count = rs.getInt(1);
	        }
	    }
	    finally { close(); }
	    return count;
	}
	
	//	숙박업소 번호에 해당하는 모든 리뷰정보를 조회
	@Override
	public List<ReviewVO> selectAllReview(String stayno, String user_id, int offset, int sizePerPage) throws SQLException
	{
		List<ReviewVO> reviewList = new ArrayList<>();
		
		try
		{
			conn = ds.getConnection();
			
			String sql	= " select	* from "
						+ " ( "
						+ " 	select	rownum as rn, data.* "
						+ " 	from "
						+ " 	( "
						+ " 		select	fk_stay_no, fk_room_no, room_grade, fk_user_id, reserv_no, review_no, reserv_score, review_contents, review_writedate "
						+ " 		from "
						+ " 		( "
						+ " 			select  fk_user_id, fk_room_no, reserv_no, review_no, reserv_score, review_contents, review_writedate "
						+ " 			from	tbl_review A "
						+ " 			join	tbl_reservation B "
						+ " 			on		A.fk_reserv_no = B.reserv_no "
						+ " 		)	C "
						+ " 		join	tbl_room	D "
						+ " 		on		C.fk_room_no = D.room_no ";

			
			if(stayno != null && !stayno.isEmpty())			{sql+=" 		where 	fk_stay_no = ? ";}
			else if(user_id != null && !user_id.isEmpty())	{sql+=" 		where	fk_user_id = ? ";}
						
					sql	+=" 	) data "
						+ " ) "
						+ " where rn > ? and rn <= ? ";
			
			pstmt = conn.prepareStatement(sql);
			if(stayno != null && !stayno.isEmpty())			{pstmt.setString(1, stayno);}
			else if(user_id != null && !user_id.isEmpty())	{pstmt.setString(1, user_id);}
	        pstmt.setInt(2, offset);
	        pstmt.setInt(3, offset + sizePerPage);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				ReviewVO reviewvo = new ReviewVO();
				
				RoomVO rvo = new RoomVO();
				rvo.setFk_stay_no(rs.getString("fk_stay_no"));
				rvo.setRoom_no(rs.getString("fk_room_no"));
				rvo.setRoom_grade(rs.getString("room_grade"));
				reviewvo.setRvo(rvo);
				
				ReservationVO rsvvo = new ReservationVO();
				String rawId = rs.getString("fk_user_id");
			    int visibleLength = Math.min(3, rawId.length()); // 최대 3글자까지 보이기
			    String visible = rawId.substring(0, visibleLength);
			    int hiddenCount = rawId.length() - visibleLength;
			    String stars = "*".repeat(hiddenCount);

			    String maskedId = visible + stars;
				
				
				rsvvo.setFk_user_id(maskedId);
				reviewvo.setRsvvo(rsvvo);
				
				reviewvo.setFk_reserv_no(rs.getString("reserv_no"));
				reviewvo.setReview_no(rs.getString("review_no"));
				reviewvo.setReview_score(rs.getDouble("reserv_score"));
				reviewvo.setReview_contents(rs.getString("review_contents"));
				
				Date rawDate = rs.getDate("review_writedate");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
				String writedate = sdf.format(rawDate);
				reviewvo.setReview_writedate(writedate);
				
				reviewList.add(reviewvo);
			}
		}
		finally
		{
			close();
		}
		return reviewList;
	}
	
	//	숙박업소 번호에 해당하며, 특정 객실등급에 해당하는 리뷰정보를 조회
	@Override
	public List<ReviewVO> selectGradeReview(String stayNo, String roomGrade, int offset, int sizePerPage) throws SQLException {
		List<ReviewVO> reviewList = new ArrayList<>();
		
		try
		{
			conn = ds.getConnection();
			
			String sql	= " select	* from "
						+ " ( "
						+ " 	select	rownum as rn, data.* "
						+ " 	from "
						+ " 	( "
						+ " 		select	fk_stay_no, fk_room_no, room_grade, fk_user_id, reserv_no, review_no, reserv_score, review_contents, review_writedate "
						+ " 		from "
						+ " 		( "
						+ " 			select  fk_user_id, fk_room_no, reserv_no, review_no, reserv_score, review_contents, review_writedate "
						+ " 			from	tbl_review A "
						+ " 			join	tbl_reservation B "
						+ " 			on		A.fk_reserv_no = B.reserv_no "
						+ " 		)	C "
						+ " 		join	tbl_room	D "
						+ " 		on		C.fk_room_no = D.room_no "
						+ " 		where 	fk_stay_no = ? and room_grade = ? "
						+ " 	) data "
						+ " ) "
						+ " where rn > ? and rn <= ? ";
			
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, stayNo);
	        pstmt.setString(2, roomGrade);
	        pstmt.setInt(3, offset);
	        pstmt.setInt(4, offset + sizePerPage);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				ReviewVO reviewvo = new ReviewVO();
				
				RoomVO rvo = new RoomVO();
				rvo.setFk_stay_no(rs.getString("fk_stay_no"));
				rvo.setRoom_no(rs.getString("fk_room_no"));
				rvo.setRoom_grade(rs.getString("room_grade"));
				reviewvo.setRvo(rvo);
				
				ReservationVO rsvvo = new ReservationVO();
				String rawId = rs.getString("fk_user_id");
			    int visibleLength = Math.min(3, rawId.length()); // 최대 3글자까지 보이기
			    String visible = rawId.substring(0, visibleLength);
			    int hiddenCount = rawId.length() - visibleLength;
			    String stars = "*".repeat(hiddenCount);

			    String maskedId = visible + stars;
				
				
				rsvvo.setFk_user_id(maskedId);
				reviewvo.setRsvvo(rsvvo);
				
				reviewvo.setFk_reserv_no(rs.getString("reserv_no"));
				reviewvo.setReview_no(rs.getString("review_no"));
				reviewvo.setReview_score(rs.getDouble("reserv_score"));
				reviewvo.setReview_contents(rs.getString("review_contents"));
				
				Date rawDate = rs.getDate("review_writedate");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
				String writedate = sdf.format(rawDate);
				reviewvo.setReview_writedate(writedate);
				
				reviewList.add(reviewvo);
			}
		}
		finally
		{
			close();
		}
		return reviewList;
	}

	//	해당 숙소에 작성된 모든 리뷰의 평점 평균 구하기
	@Override
	public String averageScore(String stayNo) throws SQLException
	{
		String averageScore = "0";	//	기본값은 0(리뷰가 하나도 없을 경우)
		
		try
		{
			conn = ds.getConnection();
			
			String sql	= " select      fk_stay_no, avg(reserv_score) "
						+ " from "
						+ " ( "
						+ " 	select  fk_room_no, reserv_score "
						+ " 	from    tbl_review A "
						+ " 	join    tbl_reservation B "
						+ " 	on      A.fk_reserv_no = B.reserv_no "
						+ " ) C "
						+ " join        tbl_room D "
						+ " on          C.fk_room_no = D.room_no "
						+ " where       fk_stay_no = ? "
						+ " group by    fk_stay_no ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stayNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				double avg = rs.getDouble(2);
				
				//	평점 평균을 소숫점 첫 째자리 까지만 보여주기
				averageScore = String.format("%.1f", avg);
			}
		}
		finally
		{
			close();
		}
		
		return averageScore;
	}

	//	해당 숙소가 갖춘 모든 room_grade를 중복없이 조회
	@Override
	public List<ReviewVO> selectRoomGrade(String stayNo) throws SQLException
	{
		List<ReviewVO> roomGradeList = new ArrayList<>();
		
		try
		{
			conn = ds.getConnection();
			
			String sql	= " select distinct	room_grade "
						+ " from			tbl_room "
						+ " where			fk_stay_no = ? "
						+ " order by		room_grade ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stayNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				ReviewVO reviewvo = new ReviewVO();
				
				RoomVO rvo = new RoomVO();
				
				rvo.setRoom_grade(rs.getString("room_grade"));
				reviewvo.setRvo(rvo);
				
				roomGradeList.add(reviewvo);
			}
		}
		finally
		{
			close();
		}
		
		return roomGradeList;
	}

	// 리뷰 테이블에 있는 특정 리뷰의 내용 변경하기
	@Override
	public int updateReview(Map<String, String> paraMap) throws SQLException
	{
		int n = 0;
		
		try
		{
			conn = ds.getConnection();
			
			String sql	= " update tbl_review set review_contents = ? "
						+ " where review_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("review_contents"));
			pstmt.setString(2, paraMap.get("review_no"));
			
			n = pstmt.executeUpdate();
		}
		finally
		{
			close();
		}
		return n;
	}

	//	리뷰 테이블에서 특정 리뷰를 지우기
	@Override
	public int deleteReview(String review_no) throws SQLException
	{
		int n = 0;
		
		try
		{
			conn = ds.getConnection();
			
			String sql	= " delete from tbl_review "
						+ " where review_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, review_no);
			
			n = pstmt.executeUpdate();
		}
		finally
		{
			close();
		}
		return n;
	}
	// 리뷰를 등록하는 메소드 
	@Override
	public int insertReview(String content, Double rating, String reserv_no) throws SQLException {
		String newNo = null;
		int insert_result = 0;
	    try {
	        // 1) 커넥션 가져오기
	        conn = ds.getConnection();

	        String seqSql = " SELECT seq_reviewno.nextval FROM dual ";
	        pstmt = conn.prepareStatement(seqSql);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            newNo = "RV" + String.format("%06d", rs.getInt(1));
	        }
	        
	        rs.close();
	        pstmt.close();
	        // 3) 예약 정보 INSERT
	        String insertSql = " insert into tbl_review(REVIEW_NO,RESERV_SCORE,REVIEW_CONTENTS ,REVIEW_WRITEDATE,FK_RESERV_NO) "
	        					+ " values ( ? , ? , ? , sysdate , ?)  ";
	        pstmt = conn.prepareStatement(insertSql);
	        pstmt.setString(1, newNo);
	        pstmt.setDouble(2, rating);
	        pstmt.setString(3, content);
	        pstmt.setString(4, reserv_no);
	       
	        insert_result = pstmt.executeUpdate();
	    } finally {
	        // 사용한 자원 모두 반납
	        close();
	    }
	    return insert_result;
	}

	@Override
	public void updateAvgScore(String reserv_no) throws SQLException
	{
		String stayno = null;
		double avgscore = 0.0; 
		try
		{
			//	받아온 reserv_no 로 해당하는 숙소번호 구하기
			conn = ds.getConnection();
			
			String sql	= " select  C.fk_stay_no "
						+ " from    tbl_reservation B "
						+ " join    tbl_room C "
						+ " on      B.fk_room_no = C.room_no "
						+ " where	B.reserv_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reserv_no);
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				stayno = rs.getString("fk_stay_no");
			}
			
			close();
			
			/////////////////////////////////////////////////////////

			conn = ds.getConnection();
			
			sql	= " select	avg(A.reserv_score) as avg_score "
				+ " from	tbl_review A "
				+ " join	tbl_reservation B "
				+ " on		A.fk_reserv_no = B.reserv_no "
				+ " join	tbl_room C "
				+ " on		B.fk_room_no = C.room_no "
				+ " where	c.fk_stay_no = "+stayno+" ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				avgscore = rs.getDouble("avg_score");
			}
			
			close();
			
			/////////////////////////////////////////////////////////

			conn = ds.getConnection();
			
			sql = " update	tbl_stay "
				+ " set		stay_score = "+avgscore+" "
				+ " where	stay_no = "+stayno+" ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		}
		finally
		{
			close();
		}
	}
}
