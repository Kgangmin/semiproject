package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Login extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if(!"get".equalsIgnoreCase(method)) {
						
			// 페이지 이동을 시킨다.
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/login.jsp");
		}
		
		else {
			String message = "비정상적인 경로로 들어왔습니다.";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(true);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		

	}

}
