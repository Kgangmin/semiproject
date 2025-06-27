package myshop.model;

import java.sql.SQLException;

import myshop.domain.ReviewVO;

public interface ReviewDAO
{
	//	리뷰 번호에 해당되는 리뷰정보 조회
	ReviewVO selectReview(String reviewno) throws SQLException;
	
}
