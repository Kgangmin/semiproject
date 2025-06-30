package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.RoomimgVO;
import myshop.domain.RoomVO;

public interface RoomDAO
{
	//	캐러셀에 사용할 객실 추가 이미지 리스트
	public List<RoomimgVO> selectExtraImages(String roomNo) throws SQLException;
	
	//	객실 정보를 조회하는 메소드
	public RoomVO selectRoom(String roomNo) throws SQLException;
}
