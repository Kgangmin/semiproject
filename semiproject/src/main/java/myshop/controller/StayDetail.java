package myshop.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        // 4) 객실 리스트
        List<RoomVO> rooms = sdao.selectRooms(stayNo);

        // 속성 등록
        request.setAttribute("stay", stay);
        request.setAttribute("extraImgList", extraImgs);
        request.setAttribute("roomList", rooms);

        // forward to JSP
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/stay/stayDetail.jsp");
    }
}
