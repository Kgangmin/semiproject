package common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.domain.MemberVO;


public class TestLoginCommand extends AbstractController {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 세션에 로그인 사용자 정보 저장
        HttpSession session = request.getSession();

        // 임시 사용자 생성
        MemberVO mvo = new MemberVO();
        mvo.setUser_id("leess");
        mvo.setUser_pwd("qwer1234$");
        mvo.setUser_name("이순신");
        mvo.setEmail("leess@gmail.com");
        mvo.setMobile("01012345678");
        mvo.setBirthday("990101");
        mvo.setTotal_payment(0);
        mvo.setFk_grade_no("1");
        mvo.setPoint(100);
        

        session.setAttribute("loginuser", mvo);

        // 마이페이지로 이동
        super.setRedirect(true);
        super.setViewPage(request.getContextPath() + "/myPage.hb");
    }
}