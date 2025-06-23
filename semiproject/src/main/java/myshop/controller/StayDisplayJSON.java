package myshop.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.domain.StayVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

public class StayDisplayJSON extends AbstractController {

	private StayDAO sdao = null;
	
	public StayDisplayJSON() {
		sdao = new StayDAO_imple();
	}
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String strStart = request.getParameter("start");
        String strLen   = request.getParameter("len");
        int start = 1;
        int len   = 6;
        
        
        try {
            if (strStart != null) start = Integer.parseInt(strStart);
            if (strLen   != null) len   = Integer.parseInt(strLen);
        } catch (NumberFormatException e) {
            // 파라미터가 숫자가 아닐 경우 기본값 유지
        }

        List<StayVO> stayList = sdao.selectStayPage(start, len);

        JSONArray jsonArr = new JSONArray();
        if (stayList != null) {
            for (StayVO svo : stayList) {
                JSONObject obj = new JSONObject();
                obj.put("stay_no",        svo.getStay_no());
                obj.put("stay_name",      svo.getStay_name());
                obj.put("stay_info",      svo.getStay_info());
                obj.put("stay_thumbnail", svo.getStay_thumbnail());
                obj.put("stay_score", 	  svo.getStay_score());
                obj.put("views",      	  svo.getViews());
                // 필요 시 위도·경도 등 추가 필드 삽입 가능
                jsonArr.put(obj);
            }
        }


        String json = jsonArr.toString();
        request.setAttribute("json", json);

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/jsonview.jsp");
    }
}
