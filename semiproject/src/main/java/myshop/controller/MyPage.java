package myshop.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;


public class MyPage extends AbstractController {
		
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(super.checkLogin(request)) {
		
			HttpSession session = request.getSession();
			String userid = request.getParameter("user_id");
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			
		
			request.setAttribute("loginUser", loginUser);
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
