package member.controller;

import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO_imple;
import myshop.domain.ReservationVO;
import member.model.MemberDAO;

public class PointDetail extends AbstractController {
	private MemberDAO mdao = new MemberDAO_imple();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(super.checkLogin(request)) {
			HttpSession session = request.getSession();
			String userid_check = request.getParameter("user_id"); // url 에 저장해둔 유저아이디
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			String userid = loginUser.getUser_id();  // 로그인시 유저아이디
			String fk_grade_no = request.getParameter("fk_grade_no");
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
			// 유저의 포인트 증감내역을 보여주는 메소드 
			List<Map<String, Object>> pointList = mdao.getPointHistory(userid);
			
			request.setAttribute("fk_grade_no", fk_grade_no);
			request.setAttribute("pointList", pointList);
			
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/pointDetail.jsp");
			
			
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
