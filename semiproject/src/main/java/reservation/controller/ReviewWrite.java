package reservation.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import myshop.domain.ReservationVO;
import myshop.domain.RoomVO;
import myshop.domain.StayVO;
import myshop.domain.WishVo;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;
import myshop.model.ReviewDAO;
import myshop.model.ReviewDAO_imple;

public class ReviewWrite extends AbstractController {
	private ReviewDAO rvdao = new ReviewDAO_imple();
	private ReservationDAO rdao = new ReservationDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(super.checkLogin(request)) {
			HttpSession session = request.getSession();
			String reserv_no = request.getParameter("reserv_no");
			String userid_check = request.getParameter("user_id"); // url 에 저장해둔 유저아이디
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser"); // 로그인시 유저정보
			String userid = loginUser.getUser_id();  // 로그인시 유저아이디
		
			if( userid_check == null || !userid.equals(userid_check)) {
				// 비정상적인 접근 
			 	String message = "비정상적인 접근입니다.";
	            String loc = request.getContextPath() + "/index.hb";
	
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
	            return;
	        
				
			}
			request.setAttribute("loginUser", loginUser);

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/reservation/reviewWrite.jsp");
		
		
		}
		else {
			// 로그인을 안 했으면
			String message = "후기작성을 보기 위해서는 로그인을 먼저해야 합니다";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}	
	
		
		
	
	}

}
