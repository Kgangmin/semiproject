package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class Dormant extends AbstractController {
	
	 private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String referer = request.getHeader("Referer");

		if(referer == null) {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/index.up");
			return; // 종료
		}
		
		
		
		
		
		
		

		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/dormant.jsp");
		
	}

}
