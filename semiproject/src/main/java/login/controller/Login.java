package login.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class Login extends AbstractController {

    private MemberDAO mdao = new MemberDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String method = request.getMethod(); // "GET" or "POST"

        if(!"POST".equalsIgnoreCase(method)) {
         // POST 방식으로 넘어온 것이 아니라면
         
         super.setRedirect(false);
         super.setViewPage("/WEB-INF/login.jsp");
         
      } else {
         
            // POST 방식일 경우 로그인 시도

            // 사용자로부터 입력받은 아이디, 비밀번호, 접속 IP 주소를 가져옴
            String user_id = request.getParameter("user_id");
            String user_pwd = request.getParameter("user_pwd");
            String login_ip = request.getRemoteAddr();

            // 입력받은 값을 Map 형태로 저장
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("user_id", user_id);
            paraMap.put("user_pwd", user_pwd);
            paraMap.put("login_ip", login_ip);

            MemberVO loginUser = mdao.login(paraMap);

            if (loginUser != null) {
               
                // System.out.println("로그인 성공");
                // 로그인 성공 시 세션에 사용자 정보 저장
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", loginUser);

                // 메인 페이지로 이동
                super.setRedirect(true);
                super.setViewPage(request.getContextPath() + "/index.hb");

            } else {
                // 로그인 실패 시 메시지와 함께 다시 로그인 페이지로 이동
                String message = "아이디 또는 비밀번호가 틀렸습니다.";
                String loc = "javascript:history.back()";

                request.setAttribute("message", message);
                request.setAttribute("loc", loc);

                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
            }
        }
    }
}
