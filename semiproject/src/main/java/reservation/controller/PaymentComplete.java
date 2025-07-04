package reservation.controller;

import java.io.PrintWriter;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import myshop.domain.PaymentVO;
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
        
        if (user == null)
        {
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/login/login.hb");
            return;
        }

        String roomNo	= request.getParameter("room_no");
        String checkin	= request.getParameter("checkin");
        String checkout	= request.getParameter("checkout");
        int paid_amount	= Integer.parseInt(request.getParameter("paid_amount"));
        int used_point	= Integer.parseInt(request.getParameter("used_point"));
        int finalPay	= Integer.parseInt(request.getParameter("finalPay"));
        String imp_uid	= request.getParameter("imp_uid");
        
        //	등급 기반으로 적립율 계산
        int earned_point = mdao.getEarnedPoint(user.getUser_id(), finalPay);

        // 1) 예약 INSERT
        ReservationVO rv = new ReservationVO();
        rv.setFk_user_id(user.getUser_id());
        rv.setFk_room_no(roomNo);
        rv.setReserv_payment(finalPay);
        rv.setSpent_point(used_point);
        rv.setCheckin_date(checkin);
        rv.setCheckout_date(checkout);
        String newReservNo = rdao.insertReservation(rv);

        // 2) 유저 총 결제금액·포인트 보정 및 등급 업데이트
        mdao.processPostPayment(user.getUser_id(), finalPay, used_point);
        
        ////////////////////////////////////////////////////////////////

        PaymentVO pmvo = new PaymentVO();
        pmvo.setImp_uid(imp_uid);
        pmvo.setFk_reserv_no(newReservNo);
        pmvo.setFk_user_id(user.getUser_id());
        pmvo.setPaid_amount(finalPay);
        pmvo.setUsed_point(used_point);

        // JSON 응답
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"status\":\"success\",\"reserv_no\":\"" + newReservNo + "\"}");
        }
    }
}
