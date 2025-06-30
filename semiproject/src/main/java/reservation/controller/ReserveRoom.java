package reservation.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import myshop.domain.RoomVO;
import myshop.domain.StayVO;
import myshop.domain.RoomimgVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;
import myshop.model.RoomDAO;
import myshop.model.RoomDAO_imple;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReserveRoom extends AbstractController {
    private StayDAO stayDao = new StayDAO_imple();
    private RoomDAO roomDao = new RoomDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        MemberVO user = (MemberVO) session.getAttribute("loginUser");
        if (user == null) {
            // 로그인 안 됐으면 로그인 페이지로 리다이렉트
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/login/login.hb");
            return;
        }

        // 파라미터
        String stayNo   = request.getParameter("stay_no");
        String roomNo   = request.getParameter("room_no");
        String checkinS = request.getParameter("checkin");
        String checkoutS= request.getParameter("checkout");

        // DAO 호출
        StayVO stay      = stayDao.selectStay(stayNo);
        List<RoomimgVO> extraImgs = roomDao.selectExtraImages(roomNo);
        
        
        // 룸 정보는 StayDAO.selectRooms에서 필터링
        RoomVO room = stayDao.selectRooms(stayNo)
                              .stream()
                              .filter(r -> r.getRoom_no().equals(roomNo))
                              .findFirst()
                              .orElseThrow(() -> new IllegalArgumentException("Invalid room_no"));
        System.out.println("▶ ReserveRoom: stayNo=" + stayNo + ", roomNo=" + roomNo);

        // 날짜 계산
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkin  = LocalDate.parse(checkinS, fmt);
        LocalDate checkout = LocalDate.parse(checkoutS, fmt);
        long nights = ChronoUnit.DAYS.between(checkin, checkout);
        int productAmount = room.getPrice_per_night() * (int)nights;

        // JSP에 전달
        request.setAttribute("stay", stay);
        request.setAttribute("room", room);
        request.setAttribute("extraImgs", extraImgs);
        request.setAttribute("checkin", checkinS);
        request.setAttribute("checkout", checkoutS);
        request.setAttribute("nights", nights);
        request.setAttribute("productAmount", productAmount);  // 실제 결제금액 임시로
        // user는 sessionScope.loginuser로 JSP에서 바로 사용

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/reservation/reserveRoom.jsp");
    }
}
