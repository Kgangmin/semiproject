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
import myshop.domain.RoomimgVO;

public class RoomDAO_imple implements RoomDAO
{
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public RoomDAO_imple()
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

	//	캐러셀에 사용할 객실 추가 이미지 리스트
	@Override
	public List<RoomimgVO> selectExtraImages(String roomNo) throws SQLException
	{
		List<RoomimgVO> roomImgList = new ArrayList<>();
        
        try
        {
        	conn = ds.getConnection();

        	String sql = " SELECT	room_extraimg_no, fk_room_no, room_extraimg_filename "
	                   + " FROM		tbl_room_extraimg "
	                   + " WHERE	fk_room_no = ? "
	                   + " ORDER BY room_extraimg_no ";
            
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, roomNo);
        	rs = pstmt.executeQuery();

        	while(rs.next())
        	{
        		RoomimgVO img = new RoomimgVO();
        		img.setRoom_extraimg_no(rs.getString("room_extraimg_no"));
        		img.setFk_room_no(rs.getString("fk_room_no"));
        		img.setRoom_extraimg_filename(rs.getString("room_extraimg_filename"));
        		roomImgList.add(img);
        	}
        }
        finally
        {
            close();
        }
        
        return roomImgList;
	}
	

	//	객실 정보를 조회하는 메소드
	@Override
	public RoomVO selectRoom(String roomNo) throws SQLException
	{
    	RoomVO rvo = new RoomVO();
        
    	try
    	{
    		conn = ds.getConnection();
    		
    		String sql	= " SELECT	* "
    					+ " FROM	tbl_room "
             			+ " WHERE	room_no = ? ";
    		
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, roomNo);
    		
    		rs = pstmt.executeQuery();

    		if(rs.next())
    		{     
    			rvo.setRoom_no(rs.getString("room_no"));
    			rvo.setFk_stay_no(rs.getString("fk_stay_no"));
    			rvo.setRoom_grade(rs.getString("room_grade"));
    			rvo.setRoom_thumbnail(rs.getString("room_thumbnail"));
    			rvo.setPrice_per_night(rs.getInt("price_per_night"));
    			rvo.setRoom_info(rs.getString("room_info"));
    		}
    	}
    	finally
    	{
    		close();
    	}
    	return rvo;
	}
	// 방의 번호로 객실의 등급을 찾는 메소드 
	@Override
	public RoomVO search_rgrade(String fk_room_no) throws SQLException {
		 RoomVO room = null;
		
		 try
	        {
	        	conn = ds.getConnection();

	        	  String sql = "SELECT room_no, room_grade, fk_stay_no " +
	                      " FROM tbl_room " +
	                      " WHERE room_no = ?";
	            
	        	pstmt = conn.prepareStatement(sql);
	        	pstmt.setString(1, fk_room_no);
	        	rs = pstmt.executeQuery();

	        	  if (rs.next()) {
	                  room = new RoomVO();
	                  room.setRoom_no(rs.getString("room_no"));
	                  room.setRoom_grade(rs.getString("room_grade"));
	                  room.setFk_stay_no(rs.getString("fk_stay_no"));
	        	  }
	        }finally{
	            close();
	        }
	        
	        return room;



	}
	
	
	// stay_no별 다음 순서의 room_no 반환하는 메소드 (예: "45-1","45-2",…)
    @Override
    public String getNextRoomNo(String stayNo) throws SQLException {
        
        try {
            conn = ds.getConnection();
            
            String sql = " SELECT COUNT(*)+1 AS next_seq FROM tbl_room WHERE fk_stay_no = ? ";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stayNo);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                int seq = rs.getInt("next_seq");
                return stayNo + "-" + seq;
            }
            return stayNo + "-1";
        } finally {
            close();
        }
    }

 // tbl_room에 객실 정보 insert 하는 메소드
    @Override
    public int insertRoom(RoomVO rvo) throws SQLException {
        
        try {
            conn = ds.getConnection();
            
            String sql = " INSERT INTO tbl_room (room_no, fk_stay_no, room_grade, room_thumbnail, price_per_night, room_info) " +
                    	 " VALUES (?, ?, ?, ?, ?, ?) ";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, rvo.getRoom_no());
            pstmt.setString(2, rvo.getFk_stay_no());
            pstmt.setString(3, rvo.getRoom_grade());
            pstmt.setString(4, rvo.getRoom_thumbnail());
            pstmt.setInt(5, rvo.getPrice_per_night());
            pstmt.setString(6, rvo.getRoom_info());
            return pstmt.executeUpdate();
        } finally {
            close();
        }
    }

 // tbl_room_extraimg에 추가 이미지 insert 하는 메소드
    @Override
    public int insertExtraImage(RoomimgVO evo) throws SQLException {
        
        try {
            conn = ds.getConnection();
            
            String sql = " INSERT INTO tbl_room_extraimg (room_extraimg_no, fk_room_no, room_extraimg_filename) " +
       		 	 " VALUES (?, ?, ?) ";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, evo.getRoom_extraimg_no());
            pstmt.setString(2, evo.getFk_room_no());
            pstmt.setString(3, evo.getRoom_extraimg_filename());
            return pstmt.executeUpdate();
        } finally {
            close();
        }
    }
	
	
}
