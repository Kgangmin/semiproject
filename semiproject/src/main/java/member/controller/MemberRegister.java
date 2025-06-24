package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.*;


public class MemberRegister extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("GET".equals(method)) {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberRegister.jsp");
		}
		
		else {
			
			String name = request.getParameter("name");
			String userid = request.getParameter("userid");
			String pwd = request.getParameter("pwd");
			String hp1 = request.getParameter("hp1");
			String hp2 = request.getParameter("hp2");
			String hp3 = request.getParameter("hp3");
			String email = request.getParameter("email");
			String birthday = request.getParameter("birthday");
			
			String mobile = hp1 + hp2 + hp3;
			
			MemberVO member = new MemberVO();
			member.setUser_name(name);
			member.setUser_id(userid);
			member.setUser_pwd(pwd);
			member.setEmail(email);
			member.setMobile(mobile);
			member.setBirthday(birthday);
			
			// === 회원가입이 성공되어지면 "회원가입 성공" 이라는 alert 를 띄우고 시작페이지로 이동한다. === //
			  
					String message = "";
					String loc = "";
					
					try {
						int n = mdao.registerMember(member);
						
						if(n==1) {
							
							message = "회원가입 성공^^";
							loc = request.getContextPath()+"/index.hb"; // 시작페이지로 이동한다.
						}
						
					} catch(SQLException e) {
						    e.printStackTrace();
						
						    message = "회원가입 실패ㅜㅜ";
						    loc = "javascript:history.back()"; // 자바스크립트를 이용한 이전페이지로 이동하는 것.
					}
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
			    
			
			
		} // end of else------------------------------------
	 	
	}

}




