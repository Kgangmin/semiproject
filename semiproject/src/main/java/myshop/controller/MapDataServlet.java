package myshop.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.domain.StayLocationVO;
import myshop.model.StayDAO;
import myshop.model.StayDAO_imple;

public class MapDataServlet extends AbstractController {

    private StayDAO sdao = new StayDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            List<StayLocationVO> list = sdao.selectAllStayLocations();
            JSONArray arr = new JSONArray();
            for (StayLocationVO vo : list) {
                JSONObject o = new JSONObject();
                o.put("stay_no",    vo.getStay_no());
                o.put("stay_name",  vo.getStay_name());
                // 프런트 코드가 기대하는 키 이름으로 내보냅니다
                o.put("latitude",        vo.getLatitude());
                o.put("longitude",        vo.getLongitude());
                o.put("minPrice",      vo.getMinPrice());
                o.put("stay_thumbnail",  vo.getStay_thumbnail());
                arr.put(o);
            }
            out.print(arr.toString());
        } catch(SQLException sqle) {
            sqle.printStackTrace();
            response.setStatus(500);
            out.print("[]");
        } catch(Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            out.print("[]");
        } finally {
            out.flush();
        }
    }
}
