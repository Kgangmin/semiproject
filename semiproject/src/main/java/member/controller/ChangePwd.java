package member.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class ChangePwd extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 내정보를 수정하기 위해 로그인 확인
		if(super.checkLogin(request)) {
			// 로그인을 했으면 
			
			String userid = request.getParameter("user_id");
			
			
			HttpSession session = request.getSession();
			
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			
		   if(loginUser.getUser_id().equals(userid)) {
			   // 로그인한 사용자가 정보를 수정하는 경우 
			   
				

				super.setRedirect(false); 
		        super.setViewPage("/WEB-INF/member/ChangePwd.jsp");

		   }
		   else {
			   // 로그인한 사용자가 다른 사용자의 정보를 수정하려는 경우 
			    String message = "다른 사용자의 정보수정은 불가합니다";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
		   }
			
		}
		else {
			// 로그인을 안 했으면
			String message = "내정보를 수정하기 위해서는 로그인을 먼저해야 합니다";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}

		
	}

}
