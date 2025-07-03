package reservation.controller;

import java.io.PrintWriter;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import myshop.domain.ReservationVO;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;

public class PaymentComplete extends AbstractController {
    private ReservationDAO rdao = new ReservationDAO_imple();
    private MemberDAO mdao = new MemberDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        MemberVO user = (MemberVO) session.getAttribute("loginUser");

        String roomNo  = request.getParameter("room_no");
        String checkin = request.getParameter("checkin");
        String checkout= request.getParameter("checkout");
        int prodAmt    = Integer.parseInt(request.getParameter("productAmount"));
        int usedPoint  = Integer.parseInt(request.getParameter("usedPoint"));
        int finalPay   = Integer.parseInt(request.getParameter("finalPay"));
        String impUid  = request.getParameter("imp_uid");

        // 1) 예약 INSERT
        ReservationVO rv = new ReservationVO();
        rv.setFk_user_id(user.getUser_id());
        rv.setFk_room_no(roomNo);
        rv.setReserv_payment(finalPay);
        rv.setSpent_point(usedPoint);
        rv.setCheckin_date(checkin);
        rv.setCheckout_date(checkout);
        rv.setImp_uid(impUid);
        String newReservNo = rdao.insertReservation(rv);

        // 2) 유저 총 결제금액·포인트 보정 및 등급 업데이트
        mdao.processPostPayment(user.getUser_id(), finalPay, usedPoint);

        // JSON 응답
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"status\":\"success\",\"reserv_no\":\"" + newReservNo + "\"}");
        }
    }
}
