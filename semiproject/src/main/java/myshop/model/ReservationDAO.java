package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.ReservationVO;

public interface ReservationDAO {
    // 예약을 insert하고 신규 reserv_no 반환
    String insertReservation(ReservationVO rv) throws Exception;
    
    
 // 마이페이지에서 보여줄 가장 빠른시기에 가야할 예약
 	ReservationVO selectNextReservation(String userid) throws SQLException;

 	// 모든 예약정보를 가져오는 메소드 
	List<ReservationVO> getReservationList(String userid, String status) throws SQLException;

	// 모든 예약정보와 객실 숙소 정보를 가져오는 메소드
	ReservationVO getReservationDetail(String reserv_no) throws SQLException;
	//페이징 처리 한 모든 예약보기
	public List<ReservationVO> getReservationListByPaging(String userid, String status, int offset, int size) throws SQLException;
	// 모든예약의 개수를 구하는 메소드
	public int getReservationCount(String userid, String status) throws SQLException;
}
