package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

import java.util.HashMap;
import java.util.Random;

import net.nurigo.java_sdk.api.Message;

import org.json.simple.JSONObject;

public class Dormant extends AbstractController {

    private MemberDAO mdao = new MemberDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        String action = request.getParameter("action"); // "send" or "verify"
        String user_name = request.getParameter("user_name");
        String mobile = request.getParameter("mobile");
        String user_id = request.getParameter("user_id");
        String login_ip = request.getRemoteAddr();
        

        if ("send".equals(action)) {  // 인증번호 발송버튼을 눌렀을 때

     
        	
            //  입력값 체크
            if (user_name.isEmpty() || mobile.isEmpty() || user_id.isEmpty()) {
                request.setAttribute("message", "아이디, 성명, 전화번호를 모두 입력하세요.");
                request.setAttribute("loc", "javascript:history.back()");
                session.removeAttribute("certStep");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
                return;
            }
            else {
            	// 회원 존재 여부 확인
                boolean isUserExist = mdao.isUserExists(user_name , mobile);

                if (!isUserExist) {
                    request.setAttribute("message", "잘못된 회원정보를 입력하셨습니다.");
                    request.setAttribute("loc", "javascript:history.back()");
                    session.removeAttribute("certStep");
                    super.setRedirect(false);
                    super.setViewPage("/WEB-INF/msg.jsp");
                    return;
                }
            }

            //  인증번호 생성 (5자리 랜덤 숫자)
            String certCode = generateRandomCode(5);

            String apiKey = "NCSJ6QXRVERU20OJ";
            String apiSecret = "WMURKQAHWPPBXYA5SKBIJZR7BWEXLSD5";
            
            Message coolsms = new Message(apiKey, apiSecret);
            
            // 보내는 메시지 정보
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("to", mobile); // 수신자 번호
            params.put("from", "01064276213"); // 등록된 발신번호
            params.put("type", "SMS");
            params.put("text", "[HBShop]인증번호는 [" + certCode + "] 입니다.");
            
            try {
                 coolsms.send(params);
            } catch (CoolsmsException e) {
                request.setAttribute("message", "인증번호 발송에 실패했습니다. 다시 시도해주세요.");
                request.setAttribute("loc", "javascript:history.back()");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
                return;
            }

            //  세션에 인증 상태 저장
            session.setAttribute("certStep", "verify");
            session.setAttribute("certCode", certCode);
            session.setAttribute("certuser_id", user_id.trim());
            session.setAttribute("certuser_name", user_name.trim());
            session.setAttribute("certMobile", mobile.trim());


        } else if ("verify".equals(action)) {  // 인증번호 검증

            String inputCode = request.getParameter("input_number");
            if (inputCode == null) inputCode = "";

            // 세션에서 저장된 인증번호 및 사용자 정보 조회
            String sessionCertCode = (String) session.getAttribute("certCode");
            String sessionUser_id = (String) session.getAttribute("certuser_id");
            String sessionUser_name = (String) session.getAttribute("certuser_name");
            String sessionMobile = (String) session.getAttribute("certMobile");

            if (sessionCertCode == null || sessionUser_name == null || sessionMobile == null) {
                request.setAttribute("message", "인증번호를 다시 받아주세요.");
                request.setAttribute("loc", request.getContextPath() + "/login/dormant.hb");
                session.removeAttribute("certStep");
                session.removeAttribute("certCode");
                session.removeAttribute("certuser_name");
                session.removeAttribute("certMobile");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
                return;
            }

            if (!inputCode.trim().equals(sessionCertCode)) {
                request.setAttribute("message", "인증번호가 틀렸습니다.");
                request.setAttribute("loc", request.getContextPath() + "/login/dormant.hb");
                session.removeAttribute("certStep");
                session.removeAttribute("certCode");
                session.removeAttribute("certuser_name");
                session.removeAttribute("certMobile");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
                return;
            }

            // 인증번호 일치 시 휴면 해제 처리(is_active=0)
            boolean updateResult = mdao.updateUserIsActive(login_ip, sessionUser_id, sessionUser_name, sessionMobile);

            if (!updateResult) {
                request.setAttribute("message", "휴면 해제 중 오류가 발생했습니다. 다시 시도해주세요.");
                request.setAttribute("loc", "javascript:history.back()");
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
                return;
            }

            // 세션에서 인증 관련 데이터 삭제
            session.removeAttribute("certStep");
            session.removeAttribute("certCode");
            session.removeAttribute("certuser_name");
            session.removeAttribute("certMobile");

            // 로그인 페이지로 리다이렉트
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/login/login.hb");

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