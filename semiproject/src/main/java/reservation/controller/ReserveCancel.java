package reservation.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import myshop.domain.PaymentVO;
import myshop.model.ReservationDAO;
import myshop.model.ReservationDAO_imple;

public class ReserveCancel extends AbstractController
{
	private ReservationDAO rsvdao = new ReservationDAO_imple();
	private MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
        MemberVO user = (MemberVO) session.getAttribute("loginUser");
        if (user == null)
        {
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/login/login.hb");
            return;
        }
        
		String imp_uid = request.getParameter("imp_uid");
		String reserv_no = request.getParameter("reserv_no");

		// 1. Iamport 토큰 요청
		String tokenUrl = "https://api.iamport.kr/users/getToken";
		String apiKey = "6778531371211454";
		String apiSecret = "pzYjj1gmxCiITMzzPpiG6deagQEP7N8ndBT09YhQKfIbyWfUnnsLP2SVaFsyb3pt2RTkMGYlxbvalMJJ";
		
		JSONObject jsonResponse = new JSONObject();
		
		try {
            // 토큰 요청 JSON 생성
            JSONObject tokenReq = new JSONObject();
            tokenReq.put("imp_key", apiKey);
            tokenReq.put("imp_secret", apiSecret);

            URL url = new URL(tokenUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(tokenReq.toJSONString().getBytes("UTF-8"));
            }

            String result;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                result = br.lines().collect(Collectors.joining());
            }

            JSONObject tokenRes = (JSONObject) new JSONParser().parse(result);
            String access_token = ((JSONObject) tokenRes.get("response")).get("access_token").toString();

            // 2. 결제취소 요청
            URL cancelUrl = new URL("https://api.iamport.kr/payments/cancel");
            HttpURLConnection cancelConn = (HttpURLConnection) cancelUrl.openConnection();
            cancelConn.setRequestMethod("POST");
            cancelConn.setRequestProperty("Content-Type", "application/json");
            cancelConn.setRequestProperty("Authorization", access_token);
            cancelConn.setDoOutput(true);

            JSONObject cancelReq = new JSONObject();
            cancelReq.put("imp_uid", imp_uid);
            cancelReq.put("reason", "사용자 예약 취소");

            try (OutputStream os = cancelConn.getOutputStream()) {
                os.write(cancelReq.toJSONString().getBytes("UTF-8"));
            }

            String cancelResult;
            try (BufferedReader cancelReader = new BufferedReader(new InputStreamReader(cancelConn.getInputStream()))) {
                cancelResult = cancelReader.lines().collect(Collectors.joining());
            }

            JSONObject cancelRes = (JSONObject) new JSONParser().parse(cancelResult);
            long code = (Long) cancelRes.get("code");

            if (code == 0) {
                // 결제 취소 성공

                // 3. DB에서 결제내역 조회 (paid_amount, used_point, earned_point, fk_user_id 등)
                // 여기서 결제내역 조회 메서드 필요 (예: selectPaymentByImpUid)
                PaymentVO pmvo = rsvdao.selectPaymentByImpUid(imp_uid);
                if (pmvo == null) {
                    jsonResponse.put("status", "fail");
                    jsonResponse.put("message", "결제내역을 찾을 수 없습니다.");
                } else {
                    // 4. 결제내역 상태 'cancelled' 및 취소시간 업데이트
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date now = new Date();
                    rsvdao.updatePaymentStatusToCancelled(imp_uid, sdf.format(now));

                    // 5. 사용자 포인트 원복 및 누적 결제금액 감소, 등급 재계산
                    int used_point = pmvo.getUsed_point();
                    int earned_point = pmvo.getEarned_point();
                    int paid_amount = pmvo.getPaid_amount();
                    int total_payment_stamp = pmvo.getTotal_payment_stamp();
                    String user_id = pmvo.getFk_user_id();

                    mdao.rollbackUserPointsAndTotalPayment(user_id, used_point, earned_point, paid_amount, total_payment_stamp, imp_uid);
                    mdao.recalcUserGrade(user_id);

                    jsonResponse.put("status", "success");
                }
            } else {
                // 취소 실패
                jsonResponse.put("status", "fail");
                jsonResponse.put("message", cancelRes.get("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "서버 처리 중 오류가 발생했습니다.");
        }

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse.toJSONString());
        }
    }
}
