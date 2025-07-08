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
import util.security.Sha256;

public class WithdrawComplete extends AbstractController
{
	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{	
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginUser");
		String user_id = loginuser.getUser_id(); 
		
		//	폼에서 입력한 비밀번호를 암호화
		String currentPwd = request.getParameter("userpwd");
		currentPwd = Sha256.encrypt(currentPwd);
		
		//	암호화된 입력받은 비밀번호를 유저테이블의 비밀번호와 비교
		boolean isMatch = mdao.checkPassword(user_id, currentPwd);

		if(isMatch)
		{
			//	입력한 비밀번호가 해당 유저의 비밀번호와 일치할 경우 탈퇴여부 변경
			int n = mdao.deletecomple(user_id);
			
			if(n == 1)
			{
				session.invalidate();	//	자동 로그아웃
				String message = "유저 탈퇴성공";
				String loc = request.getContextPath() + "/index.hb";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				request.setAttribute("popup_close", true);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
			else
			{
				String message = "탈퇴 처리 중 문제가 발생하여 탈퇴하지 못하였습니다.";
				String loc = request.getContextPath()+"/myPage.hb?user_id="+user_id;
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				request.setAttribute("popup_close", true);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
		}
		else
		{//	현재비밀번호가 db 유저에 비밀번호와 같지않다
			String message = "비밀번호가 일치하지 않습니다.";
			String loc = request.getContextPath() + "/memberWithdraw.hb?user_id=" + user_id;
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}
}
