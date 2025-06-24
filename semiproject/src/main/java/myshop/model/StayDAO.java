package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.StayVO;

public interface StayDAO {

    // start번째(1-based)부터 len개의 객실 정보를 가져온다.
    List<StayVO> selectStayPage(int start, int len) throws SQLException;

    // tbl_stay 전체 객실 수를 반환한다.
    int totalStayCount() throws SQLException;

	
	
}
