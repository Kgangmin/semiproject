package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerifyCertification extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			
			String userCertificationCode = request.getParameter("userCertificationCode");
			String user_id = request.getParameter("user_id");
			
			HttpSession session = request.getSession(); // 세션불러오기
			String certification_code = (String) session.getAttribute("certification_code");
			
			String message = "";
			String loc = "";
			
			if(certification_code.equals(userCertificationCode)) {
				message = "인증성공 되었습니다.";
				loc = request.getContextPath()+"/login/pwdUpdateEnd.hb?user_id="+user_id;
			}
			else {
				message = "발급된 인증코드가 아닙니다.\\n인증코드를 다시 발급받으세요!!";
				loc = request.getContextPath()+"/login/pwdFind.hb";
			}
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
			// !!!! 중요 !!!! //
			// !!!! 세션에 저장된 인증코드 삭제하기 !!!! //
			session.removeAttribute("certification_code");
			
		}// end of if("POST".equalsIgnoreCase(method)) {}-----------------

	}

}
