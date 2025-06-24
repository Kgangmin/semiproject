package reservation.controller;

import java.util.Arrays;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReservationSearch extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
		// 1) 상세 검색어 리스트 생성 (예: 하드코딩 or DB 조회)
        List<String> detailKeywords = Arrays.asList(
            "강릉", "부산", "경주", "속초", "서울",
            "여수", "가평", "제주도", "제주", "글램핑"
        );
        request.setAttribute("detailKeywords", detailKeywords);
        
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/reservation/reservationSearch.jsp");
		
	}

}
