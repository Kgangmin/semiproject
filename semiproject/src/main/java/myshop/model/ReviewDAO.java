package myshop.model;

import java.sql.SQLException;
import java.util.List;

import myshop.domain.ReviewVO;

public interface ReviewDAO
{
	//	숙박업소 번호에 해당하는 모든 리뷰정보를 조회
	List<ReviewVO> selectAllReview(String reviewNo) throws SQLException;
	//	숙박업소 번호에 해당하며, 특정 객실등급에 해당하는 리뷰정보를 조회
	List<ReviewVO> selectGradeReview(String stayNo, String roomGrade) throws SQLException;

	//	해당 숙소에 작성된 모든 리뷰의 평점 평균 구하기
	String averageScore(String stayNo) throws SQLException;

	//	해당 숙소가 갖춘 모든 room_grade 조회
	List<ReviewVO> selectRoomGrade(String stayNo) throws SQLException;
}
