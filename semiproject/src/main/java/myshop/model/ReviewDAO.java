package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.ReviewVO;

public interface ReviewDAO
{
	//	숙박업소 번호에 해당하는 모든 리뷰정보를 조회
	List<ReviewVO> selectReview(String reviewNo) throws SQLException;

	//	해당 숙소에 작성된 모든 리뷰의 평점 평균 구하기
	String averageScore(String stayNo) throws SQLException;
	
}
