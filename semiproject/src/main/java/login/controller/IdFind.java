package login.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class IdFind extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equalsIgnoreCase(method)) {
			// 아이디 찾기 모달창에서 "찾기" 버튼을 클릭했을 경우
			
			String user_name = request.getParameter("user_name"); 
			String email = request.getParameter("email"); 
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("user_name", user_name);
			paraMap.put("email", email);
			
			String user_id = mdao.findUserid(paraMap);
			
			if(user_id != null) {
				request.setAttribute("user_id", user_id);
			}
			else {
				request.setAttribute("user_id", "존재하지 않는 아이디입니다.");
			}
			
			request.setAttribute("user_name", user_name);
			request.setAttribute("email", email);
			
		}// end of if("POST".equalsIgnoreCase(method)) {}-------------------
		
		request.setAttribute("method", method);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/idFind.jsp");
		
	}

}
