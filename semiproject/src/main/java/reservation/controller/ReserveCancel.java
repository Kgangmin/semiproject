package reservation.controller;

import java.io.PrintWriter;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;

public class ReserveCancel extends AbstractController
{
	private ReservationDAO rsvdao	= null;
	private MemberDAO mdao			= null;
	
	public ReserveCancel()
	{
		rsvdao	= new ReservationDAO_imple();
		mdao	= new MemberDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        HttpSession session = request.getSession();
        MemberVO user = (MemberVO) session.getAttribute("loginUser");
        if (user == null)
        {// 로그인 안 됐으면 로그인 페이지로 리다이렉트
        	super.setRedirect(true);
        	super.setViewPage(request.getContextPath() + "/login/login.hb");
        	return;
        }
        
        String reserv_no	= request.getParameter("reserv_no");	//	해당 예약 번호
        
        //	예약 정보에서 imp_uid 조회하기
        String imp_uid	= rsvdao.selectImpUid(reserv_no);
        if(imp_uid == null)
        {
		 	String message = "예약 정보가 없습니다";
            String loc = request.getContextPath() + "/reservation/reservationList.jsp";

            request.setAttribute("message", message);
            request.setAttribute("loc", loc);

            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
        }
        
        //	예약 상태 변경: 1 (취소됨)
        boolean isUpdated = rsvdao.updateReservStatus(reserv_no, 1); // 1은 예약취소

        //	해당 회원정보에서 총 결제금액 변경 + 소모했던 포인트 및 적립된 포인트 원복
        
        
        //	JSON 응답
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (isUpdated)
        {
            out.print("{\"status\":\"success\"}");
        }
        else
        {
            out.print("{\"status\":\"fail\"}");
        }
	}
}
