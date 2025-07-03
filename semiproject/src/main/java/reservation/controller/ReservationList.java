package reservation.controller;

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
import myshop.model.RoomDAO;
import myshop.model.RoomDAO_imple;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
public class ReservationList extends AbstractController {
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
			
			if(userid_check == null || !userid_check.equals(userid)) {
				// 로그인시 유저아이디와 값이 다르다면
			 	String message = "본인의 예약만 접근할 수 있습니다.";
	            String loc = request.getContextPath() + "/index.hb";

	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);

	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
	            return;
	        
				
			}

			
		    String status = request.getParameter("status"); // 예약 상태 필터
		    // 모든 예약정보를 가져오는 메소드 
		    List<ReservationVO> reservationList = rdao.getReservationList(userid, status);
		    
		   
		    
		    for (ReservationVO rvo : reservationList) {
		        String checkoutStr = rvo.getCheckout_date(); // "2025-07-01 00:00:00"
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        Date checkoutDate = sdf.parse(checkoutStr);
		        Date today = new Date();

		        boolean canWriteReview = checkoutDate.before(today);
		        rvo.setCanWriteReview(canWriteReview); // VO에 boolean 필드 추가
		    }
		    
		    request.setAttribute("reservationList", reservationList);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/reservation/reservationList.jsp");
			
			
			
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


