package myshop.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import member.domain.MemberVO;
import myshop.domain.RoomVO;
import myshop.domain.RoomimgVO;
import myshop.model.RoomDAO;
import myshop.model.RoomDAO_imple;
import org.json.JSONObject;

@MultipartConfig(
    maxFileSize    = 10 * 1024 * 1024,
    maxRequestSize = 30 * 1024 * 1024
)
public class RoomRegister extends AbstractController {

    private RoomDAO rdao = new RoomDAO_imple();

    private String extractFileName(String cd) {
        for (String token : cd.split(";")) {
            if (token.trim().startsWith("filename")) {
                String fn = token.substring(token.indexOf('=')+1).trim().replace("\"", "");
                return fn.substring(fn.lastIndexOf(File.separator)+1);
            }
        }
        return null;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 관리자 접근 제어
        MemberVO user = (MemberVO) request.getSession().getAttribute("loginUser");
        if (user == null || user.getAccess_level() != 1) {
            request.setAttribute("message", "관리자만 접근 가능합니다.");
            request.setAttribute("loc", "javascript:history.back()");
            setRedirect(false);
            setViewPage("/WEB-INF/msg.jsp");
            return;
        }

        String method = request.getMethod();
        if (!"POST".equalsIgnoreCase(method)) {
            // GET → stay_no 파라미터만 전달, 뷰로
            String stayNo = request.getParameter("stay_no");
            request.setAttribute("stay_no", stayNo);
            setRedirect(false);
            setViewPage("/WEB-INF/admin/roomRegister.jsp");
            return;
        }

        // POST
        String stayNo = request.getParameter("stay_no");
        // 1) 새로운 room_no 생성 (e.g. "45-1", "45-2", ...)
        String roomNo = rdao.getNextRoomNo(stayNo);

        // 2) 업로드 디렉토리
        ServletContext ctx = request.getSession().getServletContext();
        String uploadDir = ctx.getRealPath("/images");

        // 3) RoomVO 세팅
        RoomVO rvo = new RoomVO();
        rvo.setRoom_no(roomNo);
        rvo.setFk_stay_no(stayNo);
        rvo.setRoom_grade(request.getParameter("room_grade"));
        rvo.setRoom_info(request.getParameter("room_info"));
        rvo.setPrice_per_night(Integer.parseInt(request.getParameter("price_per_night")));

        // 4) 썸네일 처리 (name="thumbnail")
        Part thumb = request.getPart("thumbnail");
        if (thumb!=null && thumb.getSize()>0) {
            String orig = extractFileName(thumb.getHeader("Content-Disposition"));
            if (!orig.toLowerCase().endsWith(".png")) {
                throw new IllegalArgumentException("썸네일은 PNG 파일만 가능합니다.");
            }
            thumb.write(uploadDir + File.separator + orig);
            thumb.delete();
            rvo.setRoom_thumbnail(orig);
        }

        // 5) 추가 이미지 3장 (drag&drop name="extra1"..."extra3")
        RoomimgVO[] extras = new RoomimgVO[3];
        for (int i=1; i<=3; i++) {
            Part p = request.getPart("extra"+i);
            if (p!=null && p.getSize()>0) {
                String orig = extractFileName(p.getHeader("Content-Disposition"));
                if (!orig.toLowerCase().endsWith(".png")) {
                    throw new IllegalArgumentException("추가 이미지는 PNG 파일만 가능합니다.");
                }
                String fname = orig;
                p.write(uploadDir + File.separator + orig);
                p.delete();
                	
                RoomimgVO img = new RoomimgVO();
                img.setRoom_extraimg_no(roomNo + "_extraimg" + i);
                img.setFk_room_no(roomNo);
                img.setRoom_extraimg_filename(fname);
                extras[i - 1] = img;
            }
        }

        // 6) DAO 호출하여 DB에 INSERT
        int n1 = rdao.insertRoom(rvo);
        int extraCount = 0;
        for (RoomimgVO img : extras) {
            if (img != null) {
                extraCount += rdao.insertExtraImage(img);
            }
        }

        // 7) 결과에 따라 alert + 리다이렉트
        if (n1 == 1 && extraCount == 
                (int)java.util.Arrays.stream(extras).filter(e->e!=null).count()) {
            request.setAttribute("message", "객실 등록이 완료되었습니다.");
            request.setAttribute("loc",
                request.getContextPath()
                + "/stayDetail.hb?stay_no=" + stayNo
            );
        } else {
            request.setAttribute("message", "등록에 실패했습니다. 다시 시도해주세요.");
            request.setAttribute("loc", "javascript:history.back()");
        }
        setRedirect(false);
        setViewPage("/WEB-INF/msg.jsp");
    }
}
