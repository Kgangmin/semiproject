package myshop.controller;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import myshop.domain.CategoryStatsVO;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;

public class CategoryChart extends AbstractController {
	
	ReservationDAO dao = new ReservationDAO_imple();
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List<CategoryStatsVO> list = dao.getCategoryReservationStats();

        JSONArray arr = new JSONArray();
        for (CategoryStatsVO vo : list) {
            JSONObject o = new JSONObject();
            o.put("cname",       vo.getCname());
            o.put("cnt",         vo.getCnt());
            o.put("sumpay",      vo.getSumpay());
            o.put("sumpay_pct",  vo.getSumpayPct());
            arr.put(o);
        }

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(arr.toString());
        out.flush();
    }
}
