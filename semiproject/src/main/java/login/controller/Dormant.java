package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

import java.util.Random;

public class Dormant extends AbstractController {

    private MemberDAO mdao = new MemberDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        String action = request.getParameter("action"); // "send" or "verify"
        String user_name = request.getParameter("user_name");
        String mobile = request.getParameter("mobile");

        if (user_name == null) user_name = "";
        if (mobile == null) mobile = "";

        if ("send".equals(action)) {  // 인증번호 발송

            //  입력값 체크
            if (user_name.trim().isEmpty() || mobile.trim().isEmpty()) {
                request.setAttribute("message", "성명과 전화번호 모두 입력하세요.");
                session.removeAttribute("certStep");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/login/dormant.jsp");
                return;
            }

            // 회원 존재 여부 확인
            boolean isUserExist = mdao.isUserExists(user_name , mobile);

            if (!isUserExist) {
                request.setAttribute("message", "잘못된 회원정보를 입력하셨습니다.");
                session.removeAttribute("certStep");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/login/dormant.jsp");
                return;
            }

            //  인증번호 생성 (5자리 랜덤 숫자)
            String certCode = generateRandomCode(5);

            // TODO: 아임포트 API 또는 SMS API를 이용해 certCode를 mobile로 전송

            //  세션에 인증 상태 저장
            session.setAttribute("certStep", "verify");
            session.setAttribute("certCode", certCode);
            session.setAttribute("certuser_name", user_name.trim());
            session.setAttribute("certMobile", mobile.trim());

            request.setAttribute("message", "인증번호가 전송되었습니다.");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/login/dormant.jsp");

        } else if ("verify".equals(action)) {  // 인증번호 검증

            String inputCode = request.getParameter("input_number");
            if (inputCode == null) inputCode = "";

            // 세션에서 저장된 인증번호 및 사용자 정보 조회
            String sessionCertCode = (String) session.getAttribute("certCode");
            String sessionUser_name = (String) session.getAttribute("certuser_name");
            String sessionMobile = (String) session.getAttribute("certMobile");

            if (sessionCertCode == null || sessionUser_name == null || sessionMobile == null) {
                request.setAttribute("message", "인증번호를 다시 받아주세요.");
                session.removeAttribute("certStep");
                session.removeAttribute("certCode");
                session.removeAttribute("certuser_name");
                session.removeAttribute("certMobile");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/login/dormant.jsp");
                return;
            }

            if (!inputCode.trim().equals(sessionCertCode)) {
                request.setAttribute("message", "인증번호가 틀렸습니다.");
                session.removeAttribute("certStep");
                session.removeAttribute("certCode");
                session.removeAttribute("certuser_name");
                session.removeAttribute("certMobile");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/login/dormant.jsp");
                return;
            }

            // 인증번호 일치 시 휴면 해제 처리(is_active=0)
            boolean updateResult = mdao.updateUserIsActive(sessionUser_name, sessionMobile);

            if (!updateResult) {
                request.setAttribute("message", "휴면 해제 중 오류가 발생했습니다. 다시 시도해주세요.");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/login/dormant.jsp");
                return;
            }

            // 세션에서 인증 관련 데이터 삭제
            session.removeAttribute("certStep");
            session.removeAttribute("certCode");
            session.removeAttribute("certuser_name");
            session.removeAttribute("certMobile");

            // 로그인 페이지로 리다이렉트
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/login.hb");

        } else {
            // 아무 action 없으면 그냥 페이지 보여주기 (초기 상태)
            session.removeAttribute("certStep");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/login/dormant.jsp");
        }
    }

    private String generateRandomCode(int length) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }
}
