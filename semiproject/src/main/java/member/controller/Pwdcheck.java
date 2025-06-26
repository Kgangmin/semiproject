package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
			String new_email = request.getParameter("email");
			
			Map<String, String> paramap = new HashMap<String, String>();
			paramap.put("user_pwd", user_pwd);
			paramap.put("user_id", user_id);
			paramap.put("new_email", new_email);
			// 패스워드가 맞는지 확인 하는 메소드 
			boolean pwdbool = mdao.pwdcheck(paramap);
			
			
			if(pwdbool){ // 만약 패스워드 확인 번호가 맞다면 
				
				 // === 이메일수정이 성공되어지면 "이메일 수정완료" 이라는 alert 를 띄우고 시작페이지로 이동한다. === //
		         try {
		            int n = mdao.changeEmailMember(paramap); 
		            
		            if(n==1) {
		               
		                // !!!! session 에 저장된 loginuser 를 변경된 사용자의 정보값으로 변경해주어야 한다. !!!!
		               HttpSession session = request.getSession();
		               MemberVO loginuser = (MemberVO) session.getAttribute("loginUser");
		               
		               loginuser.setEmail(new_email);

		               String message = "이메일 수정완료";
		               String loc = request.getContextPath()+"/myPage.hb"; // 시작페이지로 이동한다.
		               
		               request.setAttribute("message", message);
		               request.setAttribute("loc", loc);
		               
		              request.setAttribute("popup_close", true);
		               // 부모창 새로고침 및 팝업창 닫기를 하기 위한 용도
		               
		               super.setRedirect(false); 
		               super.setViewPage("/WEB-INF/msg.jsp");
		            }
		            
		         } catch(SQLException e) {
		            e.printStackTrace();
		            
		            super.setRedirect(true);
		            super.setViewPage(request.getContextPath()+"/error.up"); 
		         }
		         
		      }
				
				
				
				
				
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


