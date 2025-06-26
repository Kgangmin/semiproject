package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.RoomVO;
import myshop.domain.StayVO;
import myshop.domain.StayimgVO;

public interface StayDAO {

    // start번째(1-based)부터 len개의 객실 정보를 가져온다.
    List<StayVO> selectStayPage(int start, int len) throws SQLException;

    // 카테고리에 해당하는 객실정보를 가져온다.
    List<StayVO> getStaysByCategory(String category, int start, int len) throws SQLException;
    
    // tbl_stay 전체 객실 수를 반환한다.
    int totalStayCount() throws SQLException;
    
    // 객실 조회시 조회수를  1증가 시킨다.  
    void increaseViews(String stayNo) throws Exception;
    
    // 숙소 정보를 조회하는 메소드
    StayVO selectStay(String stayNo) throws Exception;
    
    // 캐러셀에 사용할 추가 이미지 리스트
    List<StayimgVO> selectExtraImages(String stayNo) throws Exception;
    
    // 객실 리스트
    List<RoomVO> selectRooms(String stayNo) throws Exception;
    
    // 위시리스트 추가 유무 검사
    boolean isWished(String userId, String stayNo) throws SQLException;
    
    // 찜 하기
    void insertWishlist(String userId, String stayNo) throws SQLException;
    
    // 찜 삭제하기
    void deleteWishlist(String userId, String stayNo) throws SQLException;

	

	
	
}
