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
		if(super.checkLogin(request))
		{
			HttpSession session = request.getSession();
			String userid_check = request.getParameter("user_id"); // url 에 저장해둔 유저아이디
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			String user_id = loginUser.getUser_id();  // 로그인시 유저아이디
			
			String referer = request.getHeader("Referer");
		
			if(!userid_check.equals(user_id))
			{
				// 로그인시 유저아이디와 값이 다르다면
			 	String message = "본인의 마이페이지만 접근할 수 있습니다.";
			 	if(referer == null)	referer = request.getContextPath() + "/index.hb";
	            String loc = referer;
	
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
	            return;
			}	

			// 마이페이지에서 보여줄 가장 빠른시기에 가야할 예약
			ReservationVO rvo = rdao.selectNextReservation(user_id);
			
			if(rvo == null)
			{					
				request.setAttribute("user_id", user_id);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/member/memberWithdraw.jsp");
			}
			else
			{
				String message = "아직 완료되지 않은 예약이 있는 경우 회원 탈퇴 불가합니다.";
				String loc = request.getContextPath() + "/reservationList.hb?user_id="+loginUser.getUser_id();
	
				request.setAttribute("popup_close", true);
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	            
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
	            return;
			}
		}
		else
		{
			// 로그인을 안 했으면
			String message = "페이지를 보기 위해서는 로그인을 먼저해야 합니다";
			String loc = request.getContextPath() + "/login/login.hb";
			
			request.setAttribute("popup_close", true);
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}	
	}
}