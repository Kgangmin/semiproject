package myshop.controller;

import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import myshop.domain.RegionStatsVO;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;

public class RegionChart extends AbstractController {

	ReservationDAO dao = new ReservationDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        List<RegionStatsVO> list = dao.getRegionReservationStats();

        JSONArray arr = new JSONArray();
        for (RegionStatsVO vo : list) {
            JSONObject o = new JSONObject();
            o.put("region",   vo.getRegion());
            o.put("cnt",      vo.getCnt());
            o.put("sumpay",   vo.getSumpay());
            arr.put(o);
        }

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(arr.toString());
        out.close();

	}

}
