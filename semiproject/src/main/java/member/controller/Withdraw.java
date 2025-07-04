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

public class Withdraw extends AbstractController {
	private MemberDAO mdao = new MemberDAO_imple();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String currentPwd = request.getParameter("userpwd");
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginUser");
		String user_id = loginuser.getUser_id(); 
		
		boolean isMatch = mdao.checkPassword(user_id, currentPwd);

		if(isMatch) {

				int n = mdao.deletecomple(user_id);
				if(유저정보 변경완료시 == 1) {
					String message = "유저 탈퇴성공";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					request.setAttribute("popup_close", true);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				else {
					String message = "탈퇴실패";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					request.setAttribute("popup_close", true);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
		}
		else {
			//System.out.println("없다");
			// 현재비밀번호가 db 유저에 비밀번호와 같지않다
			String message = "비밀번호가 맞지 않습니다 ";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			request.setAttribute("popup_close", true);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		

	}

}
