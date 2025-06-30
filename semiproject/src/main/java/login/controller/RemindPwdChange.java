package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class RemindPwdChange extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MemberVO mvo = (MemberVO) request.getSession().getAttribute("loginUser");
        if (mvo != null) {
            mdao.updateLastPwdUpdate(mvo.getUser_id());  // DAO에 구현
            // 메인 페이지로 이동
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/index.hb");
            return;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
		

	}

}
