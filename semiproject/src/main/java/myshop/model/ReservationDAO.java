package myshop.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import myshop.domain.PaymentVO;
import myshop.domain.CategoryStatsVO;
import myshop.domain.RegionStatsVO;
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
	
	//	결제내역번호 채번 및 insert
	void insertPaymentHistory(PaymentVO pmvo) throws SQLException;

	//	DB에서 결제내역 조회
	PaymentVO selectPaymentByImpUid(String imp_uid) throws SQLException;

	//	결제내역 상태 'cancelled' 및 취소시간 업데이트
	int updatePaymentStatusToCancelled(String imp_uid, String cancel_time) throws SQLException;


    // 각 카테고리별 예약 건수·총 결제액 집계 
    List<CategoryStatsVO> getCategoryReservationStats() throws SQLException;
    
    // 지역별(서울, 경기, …, 기타) 예약 건수·총 결제액 집계
    List<RegionStatsVO>   getRegionReservationStats()   throws SQLException;
	

}
