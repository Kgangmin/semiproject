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
			String userid = request.getParameter("userid");
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		
			request.setAttribute("loginUser", loginUser);
		
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/mypage.jsp");
		if(loginUser.getUser_id().equals(userid)) {
			// 로그인한 유저가 마이페이지를 보는 경우 
			
		}
		 
		
		

		
		}
	}
}