package myshop.controller;

import java.io.PrintWriter;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.domain.StayVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

public class SearchScroll extends AbstractController {

	private StayDAO sdao = null;
	
	public SearchScroll() {
		sdao = new StayDAO_imple();
	}

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 요청 파라미터
        String keyword  = request.getParameter("keyword");
        String checkin  = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        int start       = Integer.parseInt(request.getParameter("start"));
        int len         = Integer.parseInt(request.getParameter("len"));

        // DAO 호출
        List<StayVO> list = sdao.searchAvailableStays(
            keyword, checkin, checkout, start, len);

        // JSON 응답 설정
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // JSON Array 시작
        out.print("[");

        for (int i = 0; i < list.size(); i++) {
            StayVO s = list.get(i);
            out.print("{");
            out.print("\"stay_no\":\""      + escapeJson(s.getStay_no())      + "\",");
            out.print("\"stay_name\":\""    + escapeJson(s.getStay_name())    + "\",");
            out.print("\"stay_info\":\""    + escapeJson(s.getStay_info())    + "\",");
            out.print("\"stay_thumbnail\":\""+ escapeJson(s.getStay_thumbnail())+ "\",");
            out.print("\"stay_score\":"    + s.getStay_score()               + ",");
            out.print("\"views\":"         + s.getViews());
            out.print("}");
            if (i < list.size() - 1) {
                out.print(",");
            }
        }

        // JSON Array 끝
        out.print("]");
        out.flush();
    }

    /**
     * JSON 문자열 내에서 특수문자 이스케이프 처리
     */
    private String escapeJson(String s) {
        if (s == null) return "";
        return s
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\b", "\\b")
            .replace("\f", "\\f")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }
}
