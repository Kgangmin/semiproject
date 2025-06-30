package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.CategoryVO;
import myshop.domain.RoomVO;
import myshop.domain.StayVO;
import myshop.domain.StayimgVO;

public interface StayDAO {

    //	start번째(1-based)부터 len개의 객실 정보를 가져온다.
    List<StayVO> selectStayPage(int start, int len) throws SQLException;

    //	카테고리에 해당하는 객실정보를 가져온다.
    List<StayVO> getStaysByCategory(String category, int start, int len) throws SQLException;
    
    //	카테고리 리스트를 알아온다.
    List<CategoryVO> getCategoryList() throws SQLException;
    
    //	tbl_stay 전체 객실 수를 반환한다.
    int totalStayCount() throws SQLException;
    
    //	객실 조회시 조회수를  1증가 시킨다.  
    void increaseViews(String stayNo) throws Exception;
    
    //	숙소 정보를 조회하는 메소드
    StayVO selectStay(String stayNo) throws Exception;
    
    //	캐러셀에 사용할 추가 이미지 리스트
    List<StayimgVO> selectExtraImages(String stayNo) throws Exception;
    
    //	객실 리스트
    List<RoomVO> selectRooms(String stayNo) throws Exception;
    
    //	위시리스트 추가 유무 검사
    boolean isWished(String userId, String stayNo) throws SQLException;
    
    //	찜 하기
    void insertWishlist(String userId, String stayNo) throws SQLException;
    
    //	찜 삭제하기
    void deleteWishlist(String userId, String stayNo) throws SQLException;	
    
    //	키워드+기간으로 이용 가능한 숙소 검색
    List<StayVO> searchAvailableStays(
        String keyword,
        String checkinDate,     // "YYYY-MM-DD"
        String checkoutDate,    // "YYYY-MM-DD"
        int start,
        int len
    ) throws SQLException;

    //	키워드+기간으로 이용 가능한 숙소 총 개수
    int totalAvailableCount(
        String keyword,
        String checkinDate,
        String checkoutDate
    ) throws SQLException;
    
    //	기간에 예약이 겹치지 않는 객실만 조회
    List<RoomVO> selectAvailableRooms(
        String stayNo,
        String checkinDate,    // "YYYY-MM-DD"
        String checkoutDate    // "YYYY-MM-DD"
    ) throws SQLException;

    
    //  숙소 정보 가져오는 메서드(관리자페이지에서 조회)
    List<StayVO> getStayList(String searchWord, int offset, int limit) throws SQLException;
    
    // 호텔 총 개수 조회 (검색어)
    int getStayTotalCount(String searchWord) throws SQLException;
    


    // 방의 번호로 숙소 이름을 찾는 메소드 
	StayVO search_stay_name(String fk_room_no) throws SQLException;

}
