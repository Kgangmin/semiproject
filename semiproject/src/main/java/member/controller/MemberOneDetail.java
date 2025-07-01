package member.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MemberOneDetail extends AbstractController {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 1. 파라미터로 넘어온 user_id 받기
        String user_id = request.getParameter("user_id");

        // 2. 유효성 검사 - user_id가 없으면 에러 메시지 처리
        if(user_id == null || user_id.trim().isEmpty()) {
            request.setAttribute("message", "회원 아이디가 전달되지 않았습니다.");
            request.setAttribute("loc", "javascript:history.back()");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return; // 더 이상 진행하지 않음
        }

        // 3. DAO 객체 생성
        MemberDAO mdao = new MemberDAO_imple();

        // 4. user_id에 해당하는 회원 상세 정보 가져오기
        MemberVO member = mdao.getMemberByUserId(user_id);

        // 5. 회원 정보가 없으면 에러 처리
        if(member == null) {
            request.setAttribute("message", "해당 회원 정보를 찾을 수 없습니다.");
            request.setAttribute("loc", "javascript:history.back()");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
        }

        // 6. 회원 정보를 request에 저장
        request.setAttribute("member", member);

        // 7. 상세정보 페이지로 포워딩
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/admin/memberOneDetail.jsp");
    }
}
