package myshop.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.domain.StayVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

public class SearchResult extends AbstractController {

	private StayDAO sdao = null;
	
	public SearchResult() {
		sdao = new StayDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String keyword = request.getParameter("keyword");
        String period  = request.getParameter("period");  // "YYYY-MM-DD ~ YYYY-MM-DD"
        String[] dates = period != null ? period.split("~") : new String[]{"",""};
        String checkin  = dates[0].trim();
        String checkout = dates.length > 1 ? dates[1].trim() : "";

        int start = 1, len = 6;
        List<StayVO> stays     = sdao.searchAvailableStays(keyword, checkin, checkout, start, len);
        int totalCount         = sdao.totalAvailableCount(keyword, checkin, checkout);

        request.setAttribute("keyword",    keyword);
        request.setAttribute("period",     period);
        request.setAttribute("stays",      stays);
        request.setAttribute("totalCount", totalCount);

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/reservation/searchResult.jsp");

	}

}
