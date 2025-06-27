package myshop.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import myshop.domain.RoomVO;
import myshop.domain.RoomimgVO;
import myshop.model.RoomDAO;
import myshop.model.RoomDAO_imple;

public class RoomDetail extends AbstractController {
	private RoomDAO rdao = null;

	public RoomDetail() {
		rdao = new RoomDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		/*
		 * String roomNo = request.getParameter("room_no");
		 * 
		 * if (roomNo == null || roomNo.isEmpty()) { // 파라미터 오류 시 메인으로 리다이렉트
		 * super.setRedirect(true); super.setViewPage(request.getContextPath() +
		 * "/index.jsp"); return; }
		 * 
		 * // 2) 객실 정보 RoomVO room = rdao.selectRoom(roomNo);
		 * 
		 * // 3) 추가 이미지 List<RoomimgVO> extraImgs = rdao.selectExtraImages(roomNo);
		 * 
		 * 
		 * // 로그인 세션에서 찜 여부 확인 HttpSession session = request.getSession(false); boolean
		 * wishlistExists = false; if (session != null) { MemberVO loginUser =
		 * (MemberVO) session.getAttribute("loginUser"); if (loginUser != null) {
		 * wishlistExists = sdao.isWished(loginUser.getUser_id(), stayNo); } }
		 * 
		 * 
		 * // JSP 속성으로 등록 request.setAttribute("room", room);
		 * request.setAttribute("extraImgList", extraImgs);
		 * 
		 * // forward to JSP super.setRedirect(false);
		 * super.setViewPage("/WEB-INF/room/roomDetail.jsp");
		 */
	}
}
