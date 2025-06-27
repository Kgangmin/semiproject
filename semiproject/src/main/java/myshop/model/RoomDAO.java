package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.RoomimgVO;

public interface RoomDAO
{
	//	캐러셀에 사용할 객실 추가 이미지 리스트
	public List<RoomimgVO> selectExtraImages(String roomNo) throws SQLException;
}
