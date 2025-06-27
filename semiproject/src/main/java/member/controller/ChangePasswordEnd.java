package member.controller;

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

		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginUser");
		String user_id = loginuser.getUser_id(); 
		
		boolean isMatch = mdao.checkPassword(user_id, currentPwd);
		
		
		if(isMatch) {
			System.out.println("있다");
			// 현재비밀번호가 db 유저에 비밀번호와 같다
			
			if(currentPwd == newPwd) {
				// 현재 비밀번호가 새로입력한 비밀번호와 같다면 
				String message = "현재 비밀번호와 새로운 비밀번호가 같습니다 다른 비밀번호를 입력해주세요";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				request.setAttribute("popup_close", true);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
			else {
				//int update_pwd = mdao.changePwd(newPwd,user_id);
			}
		}
		else {
			System.out.println("없다");
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
