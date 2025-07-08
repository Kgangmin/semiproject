package reservation.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import myshop.domain.ReservationVO;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;
import myshop.model.RoomDAO;
import myshop.model.RoomDAO_imple;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;
import myshop.model.WishDAO;
import myshop.model.WishDAO_imple;

public class ReservationDetail extends AbstractController {
	private	WishDAO wdao = new WishDAO_imple();	
	private ReservationDAO rdao = new ReservationDAO_imple();
	private RoomDAO roomdao = new RoomDAO_imple();
	private StayDAO sdao = new StayDAO_imple();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			if(super.checkLogin(request)) {
			
				HttpSession session = request.getSession();
				String userid_check = request.getParameter("user_id"); // url 에 저장해둔 유저아이디
				MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
				String userid = loginUser.getUser_id();  // 로그인시 유저아이디
				String reserv_no = request.getParameter("reserv_no");// url 에 저장해둔 예약번호
				if(!userid_check.equals(userid)) {
					// 로그인시 유저아이디와 값이 다르다면
				 	String message = "본인의 예약만 접근할 수 있습니다.";
		            String loc = request.getContextPath() + "/index.hb";

		            request.setAttribute("message", message);
		            request.setAttribute("loc", loc);

		            super.setRedirect(false);
		            super.setViewPage("/WEB-INF/msg.jsp");
		            return;
		        
					
				}
				if(reserv_no == null || reserv_no.trim().isEmpty()) {
		            // 예약번호 없으면 목록 페이지로 리다이렉트 또는 에러 페이지
		            response.sendRedirect(request.getContextPath() + "/index.hb");
		            return;
		        }
				
				
				request.setAttribute("loginUser", loginUser);
				// 모든 예약정보와 객실 숙소 정보를 가져오는 메소드
				ReservationVO reservation = rdao.getReservationDetail(reserv_no);	
				request.setAttribute("reservation", reservation);
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/reservation/reservationDetail.jsp");
				
				
			}
			else {
				// 로그인을 안 했으면
				String message = "예약을 보기 위해서는 로그인을 먼저해야 합니다";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}	
			


	}

}
