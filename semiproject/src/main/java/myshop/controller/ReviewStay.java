package myshop.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.domain.ReviewVO;
import myshop.domain.StayVO;
import myshop.domain.StayimgVO;
import myshop.model.ReviewDAO;
import myshop.model.ReviewDAO_imple;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

public class ReviewStay extends AbstractController
{
	private ReviewDAO rvdao	= null;
	private StayDAO sdao	= null;
	
	public ReviewStay()
	{
		rvdao = new ReviewDAO_imple();
		sdao = new StayDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//	숙소 번호
		String stayNo = request.getParameter("stay_no");
		//	리뷰 번호
		String reviewNo = request.getParameter("reviewno");
		//	객실 등급
		String roomGrade = request.getParameter("room_grade");	
		
		//	숙소 정보
        StayVO stay = sdao.selectStay(stayNo);
        //	추가 이미지
        List<StayimgVO> extraImgs = sdao.selectExtraImages(stayNo);
		
        
        List<ReviewVO> reviewList;
        if (roomGrade == null || roomGrade.equals("all"))
        {//	숙박업소 번호에 해당하는 모든 리뷰정보를 조회
    		reviewList = rvdao.selectAllReview(stayNo);
    		roomGrade = "all"; // 선택 값 유지용
    	}
        else
        {//	숙박업소 번호에 해당하며, 특정 객실등급에 해당하는 리뷰정보를 조회
    		reviewList = rvdao.selectGradeReview(stayNo, roomGrade);
    	}
		
		//	해당 숙소에 작성된 모든 리뷰의 평점 평균 구하기
		String stayScore = rvdao.averageScore(stayNo);
		
		//	해당 숙소가 갖춘 모든 room_grade 조회
		List<ReviewVO> roomGradeList = rvdao.selectRoomGrade(stayNo);
		
		//	해당 숙소에 작성된 모든 리뷰의 수 구하기
		int reviewCount = rvdao.howManyReview(stayNo);
		
		request.setAttribute("stay", stay);
        request.setAttribute("extraImgList", extraImgs);
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("stayScore", stayScore);
		request.setAttribute("roomGradeList", roomGradeList);
		request.setAttribute("reviewCount", reviewCount);
		request.setAttribute("selectedGrade", roomGrade); // 선택 유지용
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewStay.jsp");
	}
}
