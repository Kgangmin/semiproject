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
			
			
		}
		
		
		
	}

}
