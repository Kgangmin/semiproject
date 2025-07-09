package myshop.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import myshop.domain.ReviewVO;
import myshop.model.ReviewDAO;
import myshop.model.ReviewDAO_imple;

public class ReviewUser extends AbstractController
{
	private ReviewDAO rvdao	= null;
	private	MemberDAO mdao = null;
	
	public ReviewUser()
	{
		rvdao	= new ReviewDAO_imple();
		mdao	= new MemberDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
        MemberVO user = (MemberVO) session.getAttribute("loginUser");
        
        
        // 페이지번호 받아오기 (기본값: 1)
        String pageStr = request.getParameter("page");
        int currentPage = 1;
        try
        {
            currentPage = Integer.parseInt(pageStr);
        }
        catch (Exception e){ }	//	아무값도 안가져오면 기본값 1(페이지)
        
        int sizePerPage = 3;	//	현재 리뷰데이터가 부족해서 1로 설정해뒀을 뿐 나중에 5로 수정해야 함
        int offset = (currentPage - 1) * sizePerPage;
        int totalReviewCount;
		
        List<ReviewVO> reviewList;
        if (user == null)
        {//	로그인 안 됐으면 로그인 페이지로 리다이렉트
        	request.setAttribute("message", "로그인 정보가 없기때문에 로그인 화면으로 이동합니다.");
        	request.setAttribute("loc", request.getContextPath() + "/login/login.hb");
        	
        	super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
        }
        else
        {
        	String user_id = user.getUser_id();
        	String user_grade = mdao.selectuserGrade(user_id);
        	reviewList = rvdao.selectAllReview(null, user_id, offset, sizePerPage);
        	totalReviewCount = rvdao.countAllReview(null, user_id);
        	int totalPage = (int)Math.ceil((double)totalReviewCount / sizePerPage);
        	
            request.setAttribute("user_id", user_id);	//	로그인 중인 user_id 넘겨주기
            request.setAttribute("user_grade", user_grade);
    		request.setAttribute("currentPage", currentPage);
    		request.setAttribute("totalPage", totalPage);
            request.setAttribute("reviewList", reviewList);
        }

        
    	super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewUser.jsp");
	}
}
