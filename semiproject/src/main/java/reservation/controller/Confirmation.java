package reservation.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;

public class Confirmation extends AbstractController {
    private ReservationDAO resDao = new ReservationDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String reservNo = request.getParameter("reserv_no");
        // DAO에 조회 메서드가 있다면 그걸 쓰고, 없다면 간단히 JSP로 예약번호만 전달
        // 예: ReservationVO rv = resDao.selectReservation(reservNo);
        request.setAttribute("reserv_no", reservNo);
        // 필요하다면 rv도 세팅
        // request.setAttribute("reservation", rv);

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/reservation/confirmation.jsp");
    }
}
