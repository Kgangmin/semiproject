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
		String stayNo		= request.getParameter("stay_no");		//	숙소 번호
		String roomGrade	= request.getParameter("room_grade");	//	객실 등급
		
        StayVO stay			= sdao.selectStay(stayNo);				//	숙소 정보
        //	추가 이미지
        List<StayimgVO> extraImgs = sdao.selectExtraImages(stayNo);
        
        ////////////////////////////////////////////////////////////////////

        // 페이지번호 받아오기 (기본값: 1)
        String pageStr = request.getParameter("page");
        int currentPage = 1;
        try
        {
            currentPage = Integer.parseInt(pageStr);
        }
        catch (Exception e){ }	//	아무값도 안가져오면 기본값 1(페이지)
        
        int sizePerPage = 1;	//	현재 리뷰데이터가 부족해서 1로 설정해뒀을 뿐 나중에 5로 수정해야 함
        int offset = (currentPage - 1) * sizePerPage;
        int totalReviewCount;
        
        List<ReviewVO> reviewList;
        if (roomGrade == null || roomGrade.equals("all"))
        {//	숙박업소 번호에 해당하는 모든 리뷰정보를 조회
    		reviewList = rvdao.selectAllReview(stayNo, null, offset, sizePerPage);
    		totalReviewCount = rvdao.countAllReview(stayNo, null);
    		roomGrade = "all"; // 선택 값 유지용
    	}
        else
        {//	숙박업소 번호에 해당하며, 특정 객실등급에 해당하는 리뷰정보를 조회
    		reviewList = rvdao.selectGradeReview(stayNo, roomGrade, offset, sizePerPage);
    		totalReviewCount = rvdao.countGradeReview(stayNo, roomGrade);
    	}
		
        int totalPage = (int)Math.ceil((double)totalReviewCount / sizePerPage);
        
        ////////////////////////////////////////////////////////////////////
        
		//	해당 숙소에 작성된 모든 리뷰의 평점 평균 구하기
		String stayScore = rvdao.averageScore(stayNo);
		
		//	해당 숙소가 갖춘 모든 room_grade 조회
		List<ReviewVO> roomGradeList = rvdao.selectRoomGrade(stayNo);
				
		request.setAttribute("stay", stay);
        request.setAttribute("extraImgList", extraImgs);
		request.setAttribute("stayScore", stayScore);
		request.setAttribute("roomGradeList", roomGradeList);
		request.setAttribute("selectedGrade", roomGrade);	// 선택 유지용
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("reviewList", reviewList);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewStay.jsp");
	}
}
