package member.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class Pwdcheck extends AbstractController {
	private MemberDAO mdao = new MemberDAO_imple();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		if("POST".equalsIgnoreCase(method)) {
			
			String user_pwd = request.getParameter("user_pwd");
			String user_id = request.getParameter("user_id");
			
			Map<String, String> paramap = new HashMap<String, String>();
			paramap.put("user_pwd", user_pwd);
			paramap.put("user_id", user_id);
			// 패스워드가 맞는지 확인 하는 메소드 
			boolean pwdbool = mdao.pwdcheck(paramap);
			System.out.println(pwdbool);
			
			if(pwdbool){ // 만약 패스워드 확인 번호가 맞다면 
				
				
				
				
				
				
				
			}
			else {// 패스워드 번호가 틀리다면 
				
				String message = "비밀번호가 틀렸습니다";
				String loc = request.getContextPath()+"/myPage.hb";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				request.setAttribute("popup_close", true);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
			
		}

	}

}
