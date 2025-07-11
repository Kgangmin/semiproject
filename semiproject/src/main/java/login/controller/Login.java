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
         super.setViewPage("/WEB-INF/login/login.jsp");
         
      } else {
         
            // POST 방식일 경우 로그인 시도

            // 사용자로부터 입력받은 아이디, 비밀번호, 접속 IP 주소를 가져옴
            String user_id = request.getParameter("user_id");
            String user_pwd = request.getParameter("user_pwd");
            String login_ip = request.getRemoteAddr();
            
            boolean isWithdrawn = mdao.isWithdrawnUser(user_id, user_pwd);
            
        	if(isWithdrawn) {
        	    // 탈퇴회원 메시지 출력
        	    String message = "탈퇴한 회원입니다. 회원가입 후 이용해주세요.";
        	    String loc = "javascript:history.back()";

        	    request.setAttribute("message", message);
        	    request.setAttribute("loc", loc);

        	    super.setRedirect(false);
        	    super.setViewPage("/WEB-INF/msg.jsp");
        	    return;
        	}
            
            
            // 입력받은 값을 Map 형태로 저장
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("user_id", user_id);
            paraMap.put("user_pwd", user_pwd);
            paraMap.put("login_ip", login_ip);

            MemberVO loginUser = mdao.login(paraMap);
            int access_level = mdao.getAccessLevelByUserId(user_id);

            if (loginUser != null) {
            	
            	loginUser.setAccess_level(access_level);
           	
                               
                if (1 == loginUser.getIs_active()) {            	    
            		
            	    String message = "로그인을 한지 1년이 지나서 휴면상태로 되었습니다.휴면을 풀어주는 페이지로 이동합니다.";
            	    String loc = request.getContextPath()+"/login/dormant.hb";  // 휴면을 풀어주는 페이지 만들어야함
            	              	    
            	    request.setAttribute("message", message);
            	    request.setAttribute("loc", loc);
            	    
            	    super.setRedirect(false);
                    super.setViewPage("/WEB-INF/msg.jsp");
            	    
            	    return; // 메소드 종료 
            	}
               
                else {
                
	                // 로그인 성공 시 세션에 사용자 정보 저장
	                HttpSession session = request.getSession();
	                session.setAttribute("loginUser", loginUser);
	                
	                if(loginUser.isRequirePwdChange()) { // 휴면이 아니면서 비밀번호를 변경한지 3개월 이상된 경우
	                							 
	                    // JSP에서 모달 띄우라고 flag 전달
	                	session.setAttribute("showPwdModal", true);

	                	super.setRedirect(true);
	                    super.setViewPage(request.getContextPath() + "/index.hb");
	                    return;
					}
	                // 메인 페이지로 이동
	                super.setRedirect(true);
	                super.setViewPage(request.getContextPath() + "/index.hb");
                }
            }
            else{
                // 로그인 실패 시 메시지와 함께 다시 로그인 페이지로 이동
                String message = "아이디 또는 비밀번호가 틀렸습니다.";
                String loc = "javascript:history.back()";

                request.setAttribute("message", message);
                request.setAttribute("loc", loc);

                super.setRedirect(false);
                super.setViewPage("/WEB-INF/msg.jsp");
            }
        }
    }// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception----------
}
