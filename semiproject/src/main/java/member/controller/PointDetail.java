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
			int currentPage = 1;
			try {
			    String page = request.getParameter("page");
			    if (page != null && !page.trim().isEmpty()) {
			        currentPage = Integer.parseInt(page);
			    }
			} catch (NumberFormatException e) {
			    currentPage = 1;  // 잘못된 입력이 들어온 경우 기본값으로 fallback
			}
			 int pageSize = 10;  // 한 페이지에 보여줄 항목 수
			 int blockSize = 5;      // 페이지 번호 블럭 크기 (예: 1~5, 6~10)
			 int offset = (currentPage - 1) * pageSize;
			if(!userid_check.equals(userid)) {
				// 로그인시 유저아이디와 값이 다르다면
			 	String message = "본인의 페이지만 접근할 수 있습니다.";
	            String loc = request.getContextPath() + "/index.hb";
	
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
	            return;
			}	
			
			
			// 유저의 포인트 증감내역을 보여주는 메소드 
			List<Map<String, Object>> pointList = mdao.getPointHistory(userid,pageSize,offset);
			int totalCount = mdao.getPointListTotalCount(userid);
			int totalPage = (int)Math.ceil((double)totalCount / pageSize);
			String user_grade = mdao.selectuserGrade(userid);

			if(!fk_grade_no.equals(user_grade)) {
				
			 	String message = "유저의 등급이 다릅니다";
	            String loc = "javascript:history.back()";
	
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
	            return;
			}
			
			
			int startPage = ((currentPage - 1) / blockSize) * blockSize + 1;
			int endPage = startPage + blockSize - 1;
			if (endPage > totalPage) {
				endPage = totalPage;
			}
			request.setAttribute("fk_grade_no", fk_grade_no);
			request.setAttribute("pointList", pointList);
	        request.setAttribute("currentPage", currentPage);
	        request.setAttribute("totalPage", totalPage);
	        request.setAttribute("startPage", startPage);
	        request.setAttribute("endPage", endPage);
			
			
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
