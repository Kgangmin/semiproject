package myshop.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import myshop.domain.ImageVO;
import myshop.domain.WishVo;
import myshop.model.WishDAO;
import myshop.model.WishDAO_imple;


public class MyPage extends AbstractController {
		
	private	WishDAO wdao = new WishDAO_imple();		
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(super.checkLogin(request)) {
		
			HttpSession session = request.getSession();
			
			String userid = request.getParameter("user_id");
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			request.setAttribute("loginUser", loginUser);
			int offset = 0;
			int limit = 6;
			// 사용자가 찜한 숙소들의 번호를 가져오는 메소드
			List<WishVo> wishList = wdao.selectWishWithPaging(userid, offset, limit);
			request.setAttribute("wishList", wishList);
			request.setAttribute("wishListCount", wishList.size());
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/mypage.jsp");
			
			
		}
		else {
			// 로그인을 안 했으면
			String message = "마이페이지를 보기 위해서는 로그인을 먼저해야 합니다";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}	
		

		
		
	}
}	
