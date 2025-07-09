package myshop.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import myshop.domain.ReviewVO;

public interface ReviewDAO
{
	//	페이징 처리를 위한 해당 숙소의 모든 리뷰 수
	int countAllReview(String stayNo, String user_id) throws SQLException;
	//	페이징 처리를 위한 해당 숙소의 특정 객실 등급의 리뷰 수
	int countGradeReview(String stayNo, String roomGrade) throws SQLException;
	//	숙박업소 번호에 해당하는 모든 리뷰정보를 조회
	List<ReviewVO> selectAllReview(String stayNo, String user_id, int offset, int sizePerPage) throws SQLException;
	//	숙박업소 번호에 해당하며, 특정 객실등급에 해당하는 리뷰정보를 조회
	List<ReviewVO> selectGradeReview(String stayNo, String roomGrade, int offset, int sizePerPage) throws SQLException;

	//	해당 숙소에 작성된 모든 리뷰의 평점 평균 구하기
	String averageScore(String stayNo) throws SQLException;

	//	해당 숙소가 갖춘 모든 room_grade 조회
	List<ReviewVO> selectRoomGrade(String stayNo) throws SQLException;
	
	// 리뷰 테이블에 있는 특정 리뷰의 내용 변경하기
	int updateReview(Map<String, String> paraMap) throws SQLException;
	
	//	리뷰 테이블에서 특정 리뷰를 지우기
	int deleteReview(String review_no) throws SQLException;
	
	// 리뷰를 등록하는 메소드 
	int insertReview(String content, Double rating, String reserv_no) throws SQLException;
	
	//	작성된 리뷰에 해당하는 숙소의 평균평점 업데이트
	void updateAvgScore(String reserv_no) throws SQLException;
}
