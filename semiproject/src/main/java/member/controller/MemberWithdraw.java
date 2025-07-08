package member.controller;

import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import myshop.domain.ReservationVO;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;

public class MemberWithdraw extends AbstractController
{
	private ReservationDAO rdao = new ReservationDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(super.checkLogin(request)) {
			HttpSession session = request.getSession();
			String userid_check = request.getParameter("user_id"); // url 에 저장해둔 유저아이디
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			String userid = loginUser.getUser_id();  // 로그인시 유저아이디
		
			if(!userid_check.equals(userid)) {
				// 로그인시 유저아이디와 값이 다르다면
			 	String message = "본인의 마이페이지만 접근할 수 있습니다.";
	            String loc = request.getContextPath() + "/index.hb";
	
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
	            return;
			}	
		
			if(!userid_check.equals(userid)) {
				// 로그인시 유저아이디와 값이 다르다면
			 	String message = "본인의 마이페이지만 접근할 수 있습니다.";
	            String loc = request.getContextPath() + "/index.hb";
	
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	            
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
	            return;
			}	
			 // 마이페이지에서 보여줄 가장 빠른시기에 가야할 예약
			ReservationVO rvo = rdao.selectNextReservation(userid);
			
			if(rvo == null) {
				String user_pwd = request.getParameter("user_pwd");
				
				
				request.setAttribute("userid", userid);
				
				
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/member/memberWithdraw.jsp");
			}
			else {
				String message = "아직 완료되지 않은 예약이 있는 경우 회원 탈퇴 불가합니다.";
	            String loc = request.getContextPath() + "/myPage.hb";
	
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	            
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
	            return;

			}
			
			
			
			
			
		}
		else {
			// 로그인을 안 했으면
			String message = "페이지를 보기 위해서는 로그인을 먼저해야 합니다";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}	
	}
}