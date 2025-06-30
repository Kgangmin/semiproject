package myshop.model;

import myshop.domain.ReservationVO;

public interface ReservationDAO {
    // 예약을 insert하고 신규 reserv_no 반환
    String insertReservation(ReservationVO rv) throws Exception;
}
