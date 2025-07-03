package member.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MemberOneDetail extends AbstractController {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	HttpSession session = request.getSession(false);  // 세션 없으면 null 반환

	    if (session == null) {
	        // 로그인 안 함 -> 로그인 페이지로 이동
	        request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	        return;
	    }

	    MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

	    if (loginUser == null) {
	        // 로그인 안 함 -> 로그인 페이지로 이동
	    	request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	        return;
	    }

	    if (loginUser.getAccess_level() != 1) {
	        // 로그인 했지만 admin 아님 -> 접근 거부 페이지 또는 로그인 페이지로 이동
	        request.setAttribute("message", "관리자만 접근 가능합니다.");
	        request.setAttribute("loc", "javascript:history.back()");
	        request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	        return;
	    }
	    
        // 파라미터로 넘어온 user_id 받기
        String user_id = request.getParameter("user_id");

        //  유효성 검사 - user_id가 없으면 에러 메시지 처리
        if(user_id == null || user_id.trim().isEmpty()) {
            request.setAttribute("message", "회원 아이디가 전달되지 않았습니다.");
            request.setAttribute("loc", "javascript:history.back()");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return; // 더 이상 진행하지 않음
        }

        
        MemberDAO mdao = new MemberDAO_imple();

        //  user_id에 해당하는 회원 상세 정보 가져오기
        MemberVO member = mdao.getMemberByUserId(user_id);

        // 회원 정보가 없으면 에러 처리
        if(member == null) {
            request.setAttribute("message", "해당 회원 정보를 찾을 수 없습니다.");
            request.setAttribute("loc", "javascript:history.back()");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
        }

        //  회원 정보를 request에 저장
        request.setAttribute("member", member);

        
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/admin/memberOneDetail.jsp");
    }
}
