package myshop.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import myshop.domain.RoomVO;
import myshop.domain.StayVO;
import myshop.domain.StayimgVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

public class StayDetail extends AbstractController {
	
	private StayDAO sdao = null;
	
	public StayDetail() {
		sdao = new StayDAO_imple();
	}

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String stayNo = request.getParameter("stay_no");
        if (stayNo == null || stayNo.isEmpty()) {
            // 파라미터 오류 시 메인으로 리다이렉트
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/index.jsp");
            return;
        }


        // 1) 조회수 증가
        sdao.increaseViews(stayNo);

        // 2) 숙소 정보
        StayVO stay = sdao.selectStay(stayNo);

        // 3) 추가 이미지
        List<StayimgVO> extraImgs = sdao.selectExtraImages(stayNo);

        String period = request.getParameter("period"); // "YYYY-MM-DD~YYYY-MM-DD"
        List<RoomVO> rooms;
        String checkin="", checkout="";
        if (period != null && period.contains("~")) {
            String[] dates = period.split("~");
            checkin  = dates[0].trim();
            checkout = dates[1].trim();
            // 4) 기간 겹치지 않는 객실만 조회
            rooms = sdao.selectAvailableRooms(stayNo, checkin, checkout);
        } else {
            // 기간 미선택 시 모든 객실
            rooms = sdao.selectRooms(stayNo);
        }

        // 로그인 세션에서 찜 여부 확인
        HttpSession session = request.getSession(false);
        boolean wishlistExists = false;
        if (session != null) {
            MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
            if (loginUser != null) {
                wishlistExists = sdao.isWished(loginUser.getUser_id(), stayNo);
            }
        }

        // JSP 속성으로 등록
        request.setAttribute("stay",            stay);
        request.setAttribute("extraImgList",     extraImgs);
        request.setAttribute("roomList",         rooms);
        request.setAttribute("wishlistExists",   wishlistExists);
        request.setAttribute("period",           period);
        request.setAttribute("checkin",          checkin);
        request.setAttribute("checkout",         checkout);
        

        // 몇박인지 계산후 넘겨주기
//        long nights = 0;
//        if (period != null && period.contains("~")) {
//            String[] dates = period.split("~");
//            LocalDate ci = LocalDate.parse(dates[0].trim());
//            LocalDate co = LocalDate.parse(dates[1].trim());
//            nights = ChronoUnit.DAYS.between(ci, co) + 1;
//        }
//        request.setAttribute("nights", nights);


        // forward to JSP
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/stay/stayDetail.jsp");
    }
}
