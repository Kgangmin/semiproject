package member.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import myshop.domain.StayVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

public class MemberStayList extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		MemberDAO mdao = new MemberDAO_imple();
        StayDAO sdao = new StayDAO_imple();

        // 회원 목록 조회
        List<MemberVO> memberList = mdao.getMemberList();

        // 호텔 목록 조회
        List<StayVO> stayList = sdao.getStayList();

        // 데이터 request에 저장
        request.setAttribute("memberList", memberList);
        request.setAttribute("stayList", stayList);
        
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/memberStayList.jsp");
	}

}
