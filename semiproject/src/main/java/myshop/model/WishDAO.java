package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.WishVo;

public interface WishDAO {
	
	// 사용자가 찜한 숙소 페이징 처리 메소드
	List<WishVo> selectWishWithPaging(String userId, int offset, int limit) throws SQLException;

}
