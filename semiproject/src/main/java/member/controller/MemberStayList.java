package member.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import myshop.domain.StayVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

public class MemberStayList extends AbstractController {
	 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		// 회원 검색 관련 파라미터
		// 클라이언트(사용자)로부터 검색 조건과 페이지 번호를 받아옴
		String searchType = request.getParameter("memberSearchType");  // 예를 들면 "user_name" 혹은 "email" 같은 검색 기준
		String searchWord = request.getParameter("memberSearchWord");  // 사용자가 입력한 검색어 (예: "이순신")
		String pageStr = request.getParameter("page");                 // 사용자가 보고자 하는 페이지 번호 

		// 만약 클라이언트가 해당 파라미터를 안 넘겼으면 기본값을 설정해줌 (null 체크)
		if (searchType == null) searchType = "";   // 검색기준이 없으면 빈 문자열로 초기화 (검색 안함 의미)
		if (searchWord == null) searchWord = "";   // 검색어가 없으면 빈 문자열로 초기화 (검색어 없음)
		if (pageStr == null || !pageStr.matches("\\d+")) pageStr = "1";  
		// pageStr.matches("\\d+")는 문자열이 오직 숫자로만 이루어졌는지 확인하는 정규식 검사
		// \d는 숫자를 의미하고, +는 1개 이상을 의미함
		// 즉, pageStr이 "3", "10" 같이 숫자로만 되어있으면 true, 아니면 false임
		// 이 검사를 통해서 잘못된 값이 들어오면 1페이지로 기본 설정

		int page = Integer.parseInt(pageStr);  

		// 한페이지에 5명씩 
		int limit = 5;

		//  데이터베이스에서 어느 위치부터 회원 데이터를 가져올지 계산함
		int offset = (page - 1) * limit;
		// 예를 들어 1페이지면 offset = 0, 2페이지면 offset = 5, 3페이지면 offset = 10
		// SQL에서 데이터를 어디서부터 가져올지 결정하는 시작 위치

		 
		MemberDAO mdao = new MemberDAO_imple();
		StayDAO sdao = new StayDAO_imple();  

		List<MemberVO> memberList = mdao.getMemberList(searchType, searchWord, offset, limit);
		// 파라미터 : 검색조건(searchType), 검색어(searchWord), 데이터 시작 위치(offset), 한 번에 가져올 데이터 개수(limit)

		//  전체 회원 수(검색조건에 맞는)를 가져옴 (페이지 나누기 계산에 필요)
		int totalCount = mdao.getMemberTotalCount(searchType, searchWord);

		//  전체 페이지 수(올림처리)
		// 예: 회원이 12명이고, 한 페이지에 5명씩 보여주면 페이지는 3페이지가 필요함
		int totalPage = (int)Math.ceil((double)totalCount / limit);


        
        
        // 호텔 검색 관련 파라미터
		// 클라이언트(사용자)로부터 호텔 검색어와 페이지 번호를 받아옴
		String staySearchWord = request.getParameter("staySearchWord");  // 사용자가 호텔 검색어 입력 (예: "서울")
		String stayPageStr = request.getParameter("stayPage");          // 호텔 목록 페이지 번호

		// 페이지 번호가 없거나 숫자가 아니면 기본값 1로 설정 
		if (stayPageStr == null || !stayPageStr.matches("\\d+")) {
		    stayPageStr = "1";  // 기본 1페이지로 설정
		}

		int stayPage = Integer.parseInt(stayPageStr);

		// 한 페이지에 보여줄 호텔 데이터 수(5개)
		int stayLimit = 5;

		//  데이터베이스에서 어느 위치부터 호텔 데이터를 가져올지 시작 위치(offset)를 계산
		int stayOffset = (stayPage - 1) * stayLimit;
		// 예: 1페이지면 0, 2페이지면 5, 3페이지면 10

		//  DAO를 통해 DB에서 가져오기
		List<StayVO> stayList = sdao.getStayList(staySearchWord, stayOffset, stayLimit);
		// 파라미터 : 									검색어, 			시작 위치, 	개수

		//  검색어에 맞는 호텔의 총 개수 갖고오기
		int stayTotalCount = sdao.getStayTotalCount(staySearchWord);

		//  전체 호텔 페이지 수를 계산함 (올림 처리)
		int stayTotalPage = (int) Math.ceil((double) stayTotalCount / stayLimit);
		// 예: 12개 호텔이고 5개씩 보여주면 3페이지

		
		request.setAttribute("memberList", memberList);           // 회원 목록
		request.setAttribute("totalPage", totalPage);             // 회원 전체 페이지 수
		request.setAttribute("currentPage", page);                // 현재 회원 페이지 번호
		request.setAttribute("searchType", searchType);           // 회원 검색 기준 (ex: 이름, 이메일)
		request.setAttribute("searchWord", searchWord);           // 회원 검색어

		
		request.setAttribute("stayList", stayList);               // 호텔 목록
		request.setAttribute("stayTotalPage", stayTotalPage);     // 호텔 전체 페이지 수
		request.setAttribute("stayCurrentPage", stayPage);        // 현재 호텔 페이지 번호
		request.setAttribute("staySearchWord", staySearchWord);   // 호텔 검색어

		
		super.setRedirect(false);  
		super.setViewPage("/WEB-INF/admin/memberStayList.jsp");  


	}

}
