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

import myshop.domain.RoomimgVO;
import myshop.domain.StayimgVO;

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

        	String sql = " SELECT	room_extraimg_no, fk_room_no, room_extraimg_no_filename "
	                   + " FROM		tbl_room_extraimg "
	                   + " WHERE	fk_room_no = ? ORDER BY stay_extraimg_no ";
            
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, roomNo);
        	rs = pstmt.executeQuery();

        	while(rs.next())
        	{
        		RoomimgVO img = new RoomimgVO();
        		img.setRoom_extraimg_no(rs.getString("room_extraimg_no"));
        		img.setFk_room_no(rs.getString("fk_room_no"));
        		img.setRoom_extraimg_filename(rs.getString("stay_extraimg_filename"));
        		roomImgList.add(img);
        	}
        }
        finally
        {
            close();
        }
        
        return roomImgList;
	}
}
