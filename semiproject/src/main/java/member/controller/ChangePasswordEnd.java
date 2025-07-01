package member.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class ChangePasswordEnd extends AbstractController {
	private MemberDAO mdao = new MemberDAO_imple();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String currentPwd = request.getParameter("user_pwd");
		String newPwd = request.getParameter("new_user_pwd");
		//System.out.println("currentPwd     " + currentPwd);
		//System.out.println("newPwd    " + newPwd);
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginUser");
		String user_id = loginuser.getUser_id(); 
		
		boolean isMatch = mdao.checkPassword(user_id, currentPwd);
		
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("new_pwd", newPwd);
		paraMap.put("user_id", user_id);
		
		
		
		
		if(isMatch) {
			//System.out.println("있다");
			// 현재비밀번호가 db 유저에 비밀번호와 같다
			
			if(currentPwd.equals(newPwd)) {
				// 현재 비밀번호가 새로입력한 비밀번호와 같다면 
				//System.out.println("비밀번호가같다");
				String message = "현재 비밀번호와 새로운 비밀번호가 같습니다 다른 비밀번호를 입력해주세요";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				request.setAttribute("popup_close", true);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
			else {
				//System.out.println("비밀번호가 같지않다");
				int update_pwd = mdao.pwdUpdate(paraMap);

				
				if(update_pwd == 1) {
					String message = "비밀번호 번경성공";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					request.setAttribute("popup_close", true);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				else {
					String message = "비밀번호 변경실패";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					request.setAttribute("popup_close", true);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
			}
		}
		else {
			//System.out.println("없다");
			// 현재비밀번호가 db 유저에 비밀번호와 같지않다
			String message = "현재 비밀번호가 아닙니다";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			request.setAttribute("popup_close", true);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
		
	}

}
