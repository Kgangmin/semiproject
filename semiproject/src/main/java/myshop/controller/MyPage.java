package myshop.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class MyPage extends AbstractController {
		
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		
		
		String userid = request.getParameter("userid");
		
		request.setAttribute("userid", userid);
		
		
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/member/mypage.jsp");

	}

}
