package myshop.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.domain.StayVO;
import myshop.domain.WishVo;
import myshop.model.WishDAO;
import myshop.model.WishDAO_imple;

public class LoadMoreWishlist extends AbstractController {

    private WishDAO wdao = new WishDAO_imple();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 if (super.checkLogin(request)) {
			 // 로그인이 되었다면
			  String userId = request.getParameter("user_id");
		        int offset = Integer.parseInt(request.getParameter("offset"));
		        int limit = Integer.parseInt(request.getParameter("limit"));

		        List<WishVo> wishList = wdao.selectWishWithPaging(userId, offset, limit);

		        // JSON 배열 생성
		        JSONArray jsonArr = new JSONArray();

		        for (WishVo wvo : wishList) {
		            JSONObject obj = new JSONObject();
		            obj.put("w_stay_no", wvo.getW_stay_no());
		            obj.put("w_user_id", wvo.getW_user_id());

		            StayVO svo = wvo.getStayVO();
		            JSONObject stayObj = new JSONObject();
		            stayObj.put("stay_name", svo.getStay_name());
		            stayObj.put("stay_thumbnail", svo.getStay_thumbnail());

		            obj.put("stayVO", stayObj); 

		            jsonArr.put(obj);
		        }

		        request.setAttribute("json", jsonArr.toString());

		        super.setRedirect(false);
		        super.setViewPage("/WEB-INF/jsonview.jsp");
	        }
		 else {
			  // 로그인 안 됨 처리
	            JSONObject errorJson = new JSONObject();
	            errorJson.put("error", "로그인이 된사용자만 이용가능합니다");
	            request.setAttribute("json", errorJson.toString());

	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/jsonview.jsp");
	            return;
		 }

	}

}
