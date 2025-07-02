package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.RoomVO;
import myshop.domain.RoomimgVO;
import myshop.domain.RoomVO;

public interface RoomDAO
{
	//	캐러셀에 사용할 객실 추가 이미지 리스트
	public List<RoomimgVO> selectExtraImages(String roomNo) throws SQLException;

	//	객실 정보를 조회하는 메소드
	public RoomVO selectRoom(String roomNo) throws SQLException;

	// 방의 번호로 객실의 등급을 찾는 메소드 
	public RoomVO search_rgrade(String fk_room_no) throws SQLException;
	
    // stay_no별 다음 순서의 room_no 반환하는 메소드 (예: "45-1","45-2",…)
    String getNextRoomNo(String stayNo) throws SQLException;

    // tbl_room에 객실 정보 insert 하는 메소드
    int insertRoom(RoomVO rvo) throws SQLException;

    // tbl_room_extraimg에 추가 이미지 insert 하는 메소드
    int insertExtraImage(RoomimgVO evo) throws SQLException;
}
