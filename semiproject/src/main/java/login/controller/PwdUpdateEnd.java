package login.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class PwdUpdateEnd extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String user_id = request.getParameter("user_id");
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			// "암호변경하기" 버튼을 클릭했을 경우
			
			String new_pwd = request.getParameter("user_pwd");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("new_pwd", new_pwd);
			paraMap.put("user_id", user_id);
			
			int n = 0;
					
			try {		
				n = mdao.pwdUpdate(paraMap);
			} catch(SQLException e) {
				
			}
			
			request.setAttribute("n", n);
			
		}// end of if("POST".equalsIgnoreCase(method))----------- 
		
		request.setAttribute("user_id", user_id);
		request.setAttribute("method", method);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/pwdUpdateEnd.jsp");

	}

}
