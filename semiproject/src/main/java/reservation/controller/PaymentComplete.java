package reservation.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        //	전달된 결제관련 파라미터 추출
        String roomNo   = request.getParameter("room_no");
        String checkin   = request.getParameter("checkin");
        String checkout   = request.getParameter("checkout");
        int paid_amount   = Integer.parseInt(request.getParameter("paid_amount"));
        int used_point   = Integer.parseInt(request.getParameter("used_point"));
        int finalPay   = Integer.parseInt(request.getParameter("finalPay"));
        String imp_uid   = request.getParameter("imp_uid");
        String pay_method = request.getParameter("pay_method");

        //	예약 테이블에 insert
        ReservationVO rv = new ReservationVO();
        rv.setFk_user_id(user.getUser_id());
        rv.setFk_room_no(roomNo);
        rv.setCheckin_date(checkin);
        rv.setCheckout_date(checkout);
        rv.setImp_uid(imp_uid);
        //   예약번호 생성 및 insert
        String newReservNo = rdao.insertReservation(rv);

        //   등급 기반으로 적립율 계산
        int earned_point = mdao.getEarnedPoint(user.getUser_id(), finalPay);
        
        //	결제 시 소모한 포인트만 즉시 반영
        mdao.spentPoint(user.getUser_id(), used_point);
        
        ////////////////////////////////////////////////////////////////

        //   결제내역 테이블에 insert
        PaymentVO pmvo = new PaymentVO();	
        pmvo.setImp_uid(imp_uid);
        pmvo.setFk_reserv_no(newReservNo);
        pmvo.setFk_user_id(user.getUser_id());
        pmvo.setPaid_amount(finalPay);
        pmvo.setUsed_point(used_point);
        pmvo.setEarned_point(earned_point);
        pmvo.setPay_method(pay_method);
        pmvo.setStatus("paid");
        
        //   현재 시각을 결제 시각으로 저장
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pmvo.setPay_time(sdf.format(new Date()));
        
        //   결제 당시의 total_payment 값
        int total_payment_stamp = mdao.getCurrentTotalPayment(user.getUser_id());
        pmvo.setTotal_payment_stamp(total_payment_stamp);

    	//	결제내역번호 채번 및 insert
        rdao.insertPaymentHistory(pmvo);	// payment_id 자동 채번 포함
        
        // JSON 응답
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"status\":\"success\",\"reserv_no\":\"" + newReservNo + "\"}");
        }
    }
}
