package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Login extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("get".equalsIgnoreCase(method)) {
			
			
			// 페이지 이동을 시킨다.
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/login.jsp");
		}
		

	}

}
