package reservation.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.ReservationVO;

public interface ReservationDAO {
	// 마이페이지에서 보여줄 가장 빠른시기에 가야할 예약
	ReservationVO selectNextReservation(String userid) throws SQLException;

}
