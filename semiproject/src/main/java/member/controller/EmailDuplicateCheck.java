package member.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class EmailDuplicateCheck extends AbstractController {

	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("POST".equals(method)) {
			
			String email = request.getParameter("email");
			
			boolean isExists = mdao.emailDuplicateCheck(email);
			
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
