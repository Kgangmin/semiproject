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
		String reviewno = request.getParameter("reviewno");
		
		// 숙소 정보
        StayVO stay = sdao.selectStay(stayNo);
        // 추가 이미지
        List<StayimgVO> extraImgs = sdao.selectExtraImages(stayNo);
		
		//	리뷰 번호에 해당되는 예약이 어느 숙소에 대한 것인지 조회하기
		ReviewVO reviewvo = rdao.selectReview(reviewno);
		
		System.out.println(">> stayNo = " + stayNo);
		System.out.println(">> stay = " + stay);
		System.out.println(">> stay.getStay_thumbnail() = " + (stay != null ? stay.getStay_thumbnail() : "null"));
		
		request.setAttribute("stay", stay);
        request.setAttribute("extraImgList", extraImgs);
		request.setAttribute("reviewvo", reviewvo);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewStay.jsp");
	}
}
