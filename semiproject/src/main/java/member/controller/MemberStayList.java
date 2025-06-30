package member.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		String searchType = request.getParameter("memberSearchType");  // 예: "user_name" or "email"
		String searchWord = request.getParameter("memberSearchWord");  // 검색어
		String pageStr = request.getParameter("page");           // 현재 페이지 번호 (문자열)
		
		
		if (searchType == null) searchType = "";  // 기본값 처리
		if (searchWord == null) searchWord = "";
		if (pageStr == null || !pageStr.matches("\\d+")) pageStr = "1";  // 기본 1페이지
							// pageStr.matches("\\d+") 의 의미 : \d : 숫자(digit), "\\d+" 는 "1개 이상의 숫자만으로 이루어진 문자열" (try-catch 대신 사용)

		int page = Integer.parseInt(pageStr);
		int limit = 5;       // 한 페이지당 보여줄 회원 수
		int offset = (page - 1) * limit;  // 시작 위치 계산
		
		MemberDAO mdao = new MemberDAO_imple();
        StayDAO sdao = new StayDAO_imple();

        // 회원 목록 조회
        List<MemberVO> memberList = mdao.getMemberList(searchType, searchWord, offset, limit);

        int totalCount = mdao.getMemberTotalCount(searchType, searchWord);
        int totalPage = (int)Math.ceil((double)totalCount / limit);

        
        
        // 호텔 검색 관련 파라미터
        String staySearchWord = request.getParameter("staySearchWord");
        String stayPageStr = request.getParameter("stayPage");
        if (stayPageStr == null || !stayPageStr.matches("\\d+")) {
            stayPageStr = "1";  // 기본 1페이지 설정
        }
        int stayPage = Integer.parseInt(stayPageStr);
        
        
        int stayLimit = 5;
        int stayOffset = (stayPage - 1) * stayLimit;
        
        // 호텔 목록 조회
        List<StayVO> stayList = sdao.getStayList(staySearchWord, stayOffset, stayLimit);
        int stayTotalCount = sdao.getStayTotalCount(staySearchWord);
        int stayTotalPage = (int) Math.ceil((double) stayTotalCount / stayLimit);
        
        
        request.setAttribute("memberList", memberList);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("searchType", searchType);
        request.setAttribute("searchWord", searchWord);

        
        request.setAttribute("stayList", stayList);
        request.setAttribute("stayTotalPage", stayTotalPage);
        request.setAttribute("stayCurrentPage", stayPage);
        request.setAttribute("staySearchWord", staySearchWord);
        
        
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/admin/memberStayList.jsp");

	}

}
