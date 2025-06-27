package myshop.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

public class WishlistToggle extends AbstractController {

    private StayDAO sdao;

    public WishlistToggle() {
        sdao = new StayDAO_imple();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession(false);
        MemberVO loginUser = (session != null)
                          ? (MemberVO) session.getAttribute("loginUser")
                          : null;
        // 1) 로그인 체크
        if (loginUser == null) {
        	String message = "로그인을 해주세요.";
            String loc = "javascript:history.back()";

            request.setAttribute("message", message);
            request.setAttribute("loc", loc);
              
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
        }

        String userId = loginUser.getUser_id();  
        String stayNo = request.getParameter("stay_no");

        // 2) 현재 찜 상태 확인 후 토글
        boolean is_wished = sdao.isWished(userId, stayNo);
        
        if (is_wished) {
            sdao.deleteWishlist(userId, stayNo);
        } else {
            sdao.insertWishlist(userId, stayNo);
        }

        // 3) 상세 페이지로 리다이렉트
        super.setRedirect(true);
        super.setViewPage(request.getContextPath() + "/stayDetail.hb?stay_no=" + stayNo);
    }
}
