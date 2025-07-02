package myshop.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.model.ReviewDAO;
import myshop.model.ReviewDAO_imple;

public class ReviewDelete extends AbstractController
{
	private ReviewDAO rvdao = null;
	
	public ReviewDelete()
	{
		rvdao = new ReviewDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String method = request.getMethod();
		
		if(!"POST".equalsIgnoreCase(method))
		{//	GET 방식이라면
			String message = "비정상적인 경로로 들어왔습니다";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else if("POST".equalsIgnoreCase(method) && super.checkLogin(request))
		{//	POST 방식이고, 로그인 중이라면
			String review_no = request.getParameter("review_no");
			
			//	리뷰 테이블에서 특정 리뷰를 지우기
			int n = rvdao.deleteReview(review_no);
			
			JSONObject jsobj = new JSONObject();	//	{}
			jsobj.put("n",n);	//	{"n":1}
			
			String json = jsobj.toString();	//	"{"n":1}" << 문자열로
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}
	}
}
