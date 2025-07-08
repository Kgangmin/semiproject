package myshop.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class StayChart extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		// === 로그인 유무 검사하기 === //
	      if(loginUser == null || !(loginUser.getAccess_level() == 1)) {
	         // 로그인 하지 않았고 관리자 권한이 없을때
	         
	         request.setAttribute("message", "접근 권한이 없습니다");
	         request.setAttribute("loc", "javascript:history.back()"); 
	         
	      //   super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         return; // 종료
	      }
	      
	      else { // 로그인 한 경우이라면 
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/admin/stayChart.jsp");
	      }

	}

}
