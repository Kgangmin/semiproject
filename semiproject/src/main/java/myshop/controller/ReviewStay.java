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
	private ReviewDAO rdao	= null;
	private StayDAO sdao	= null;
	
	public ReviewStay()
	{
		rdao = new ReviewDAO_imple();
		sdao = new StayDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//	숙소 번호
		String stayNo = request.getParameter("stay_no");
		//	리뷰 번호
		String reviewNo = request.getParameter("reviewno");
		
		//	숙소 정보
        StayVO stay = sdao.selectStay(stayNo);
        //	추가 이미지
        List<StayimgVO> extraImgs = sdao.selectExtraImages(stayNo);
		
    	//	숙박업소 번호에 해당하는 모든 리뷰정보를 조회
		List<ReviewVO> reviewList = rdao.selectReview(stayNo);
		
		//	해당 숙소에 작성된 모든 리뷰의 평점 평균 구하기
		String stayScore = rdao.averageScore(stayNo);
		
		request.setAttribute("stay", stay);
        request.setAttribute("extraImgList", extraImgs);
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("stayScore", stayScore);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewStay.jsp");
	}
}
