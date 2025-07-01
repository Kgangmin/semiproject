package myshop.controller;

import java.io.File;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import member.domain.MemberVO;
import myshop.domain.StayVO;
import myshop.domain.StayimgVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

@MultipartConfig(
    maxFileSize       = 10 * 1024 * 1024,       // 단일 파일 최대 10MB
    maxRequestSize    = 30 * 1024 * 1024        // 요청 전체 최대 30MB
)
public class StayRegister extends AbstractController {

    private StayDAO sdao = new StayDAO_imple();

    // request.getHeader("Content-Disposition") 에서 filename 을 추출
    private String extractFileName(String cd) {
        for (String token : cd.split(";")) {
            if ( token.trim().startsWith("filename") ) {
                String fn = token.substring(token.indexOf('=')+1).trim().replace("\"", "");
                return fn.substring(fn.lastIndexOf(File.separator)+1);
            }
        }
        return null;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 관리자 권한 체크
        MemberVO loginUser = (MemberVO) request.getSession().getAttribute("loginUser");
        if (loginUser == null || loginUser.getAccess_level() != 1) {
            request.setAttribute("message", "관리자만 접근 가능합니다.");
            request.setAttribute("loc", "javascript:history.back()");
            setRedirect(false);
            setViewPage("/WEB-INF/msg.jsp");
            return;
        }

        String method = request.getMethod();
        if (!"POST".equalsIgnoreCase(method)) {
            // GET → 카테고리 목록 조회 후 폼 보여주기
            request.setAttribute("categoryList", sdao.getCategoryList());
            setRedirect(false);
            setViewPage("/WEB-INF/admin/stayRegister.jsp");
            return;
        }

        // === POST 처리 ===
        // 1) next stay_no 시퀀스 값을 채번 (DAO에 seq 읽는 메서드가 있다고 가정)
        String stayNo = sdao.getNextStayNo();  // 예: "15"

        // 2) 업로드 디렉토리 결정
        ServletContext ctx = request.getSession().getServletContext();
        String uploadDir = ctx.getRealPath("/images");

        // 3) StayVO 에 기본 정보 담기
        StayVO svo = new StayVO();
        svo.setStay_no(stayNo);
        svo.setFk_stay_category_no(request.getParameter("fk_stay_category_no"));
        svo.setStay_name(request.getParameter("stay_name"));
        svo.setStay_info(request.getParameter("stay_info"));
        svo.setStay_tel(request.getParameter("stay_tel"));
        svo.setPostcode(request.getParameter("postcode"));
        svo.setAddress(request.getParameter("address"));
        svo.setDetailaddres(request.getParameter("detailaddress"));
        svo.setExtraaddress(request.getParameter("extraaddress"));
        // 위도/경도 파라미터가 넘어온다고 가정
        svo.setLatitude(Double.parseDouble(request.getParameter("latitude")));
        svo.setLongitude(Double.parseDouble(request.getParameter("longitude")));

        // 4) 대표 썸네일 업로드 (name="stay_thumbnail")
        Part thumbPart = request.getPart("stay_thumbnail");
        if (thumbPart != null && thumbPart.getSize()>0) {
            String orig = extractFileName(thumbPart.getHeader("Content-Disposition"));
            if (!orig.toLowerCase().endsWith(".png")) {
                throw new IllegalArgumentException("썸네일은 PNG 파일만 가능합니다.");
            }
            // 새로운 파일명: stayNo_YYYYMMddHHmmss.png
            thumbPart.write(uploadDir + File.separator + orig);
            thumbPart.delete();
            svo.setStay_thumbnail(orig);
        }

        // 5) 추가 이미지 up to 3개 (name="extra1","extra2","extra3")
        StayimgVO[] extras = new StayimgVO[3];
        for (int i=1; i<=3; i++) {
            Part p = request.getPart("extra"+i);
            if (p!=null && p.getSize()>0) {
                String orig = extractFileName(p.getHeader("Content-Disposition"));
                if (!orig.toLowerCase().endsWith(".png")) {
                    throw new IllegalArgumentException("추가 이미지는 PNG 파일만 가능합니다.");
                }
                String fname = orig;
                // 업로드 디렉토리에 원본 이름으로 저장
                p.write(uploadDir + File.separator + fname);
                p.delete();
                StayimgVO img = new StayimgVO();
                img.setFk_stay_no(stayNo);
                img.setStay_extraimg_no_filename(fname);
                img.setStay_extraimg_no(stayNo + "_extraimg" + i);
                extras[i-1] = img;
            }
        }

        // 6) DAO 에 insert
        int n1 = sdao.insertStay(svo);    // insert into tbl_stay ...
        int cnt = n1==1 ? 1 : 0;
        for (StayimgVO img : extras) {
            if (img!=null) {
                cnt += sdao.insertExtraImage(img);
            }
        }

        int expected = 1
        	    + (extras[0] != null ? 1 : 0)
        	    + (extras[1] != null ? 1 : 0)
        	    + (extras[2] != null ? 1 : 0);

        	// 성공 여부 판단
        	if(cnt == expected) {
        	    request.setAttribute("message", "숙소 등록이 완료되었습니다.");
        	    // 메인페이지 URL로 바꿔주세요
        	    request.setAttribute("loc", request.getContextPath() + "/index.hb");
        	} else {
        	    request.setAttribute("message", "숙소 등록에 실패했습니다.");
        	    request.setAttribute("loc", "javascript:history.back()");
        	}

        	setRedirect(false);
        	setViewPage("/WEB-INF/msg.jsp");
    }
}
