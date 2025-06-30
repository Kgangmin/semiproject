package myshop.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import myshop.domain.ImageVO;
import myshop.domain.ReservationVO;
import myshop.domain.RoomVO;
import myshop.domain.StayVO;
import myshop.domain.WishVo;
import myshop.model.RoomDAO;
import myshop.model.RoomDAO_imple;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;
import myshop.model.WishDAO;
import myshop.model.WishDAO_imple;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;


public class MyPage extends AbstractController {
		
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
			
			
			
			request.setAttribute("loginUser", loginUser);
			int offset = 0;
			int limit = 6;
			// 사용자가 찜한 숙소들의 번호를 가져오는 메소드
			List<WishVo> wishList = wdao.selectWishWithPaging(userid, offset, limit);
			request.setAttribute("wishList", wishList);
			request.setAttribute("wishListCount", wishList.size());
			// 마이페이지에서 보여줄 가장 빠른시기에 가야할 예약
			ReservationVO nextReservation = rdao.selectNextReservation(userid);
			request.setAttribute("nextReservation", nextReservation);
			
			if (nextReservation != null) {
				// 방의 번호로 객실의 등급을 찾는 메소드 
		        RoomVO room = roomdao.search_rgrade(nextReservation.getFk_room_no());
		        // 방의 번호로 숙소 이름을 찾는 메소드 
		        StayVO stay = sdao.search_stay_name(nextReservation.getFk_room_no());
		        nextReservation.setRoomvo(room);
		        nextReservation.setStayvo(stay);
		    }
			
			
			request.setAttribute("nextReservation", nextReservation);
			
			
			
		   
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/mypage.jsp");
			
			
		}
		else {
			// 로그인을 안 했으면
			String message = "마이페이지를 보기 위해서는 로그인을 먼저해야 합니다";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}	
		

		
		
	}
}	
