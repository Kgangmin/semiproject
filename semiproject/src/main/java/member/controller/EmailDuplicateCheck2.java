package member.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class EmailDuplicateCheck2 extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equals(method)) {
			
			String new_email = request.getParameter("email");
			
			String userid = request.getParameter("userid");
			
			
			Map<String, String> paraMap = new HashMap<>();
	        paraMap.put("new_email", new_email);
	        paraMap.put("userid", userid);
			
			boolean isExists = mdao.emailDuplicateCheck2(paraMap);
			// 회원정보 수정시 변경하고자 하는 이메일이 다른 사용자가 현재 사용중인지 아닌지 여부 알아오기
			
			JSONObject jsonObj = new JSONObject(); // {}
			jsonObj.put("isExists", isExists);     // {"isExists":true} 또는 {"isExists":false} 으로 만들어준다. 
			
			String json = jsonObj.toString(); // 문자열 형태인 "{"isExists":true}" 또는 "{"isExists":false}" 으로 만들어준다. 
		//	System.out.println(">>> 확인용 json => " + json);
			//  >>> 확인용 json => {"isExists":false}
			//  >>> 확인용 json => {"isExists":true}
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
			
		}// end of if("POST".equals(method))-------------------------
	

	}

}
