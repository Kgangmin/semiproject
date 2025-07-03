<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="../header1.jsp" />

<style>
body { font-family: 'Noto Sans KR', sans-serif; background-color: #f9fafb; line-height: 1.6; } 
.container { width: 800px; margin: 40px auto; padding: 20px; } 
.section { background-color: #ffffff; border-radius: 12px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); padding: 25px 30px; margin-bottom: 30px; } 
.section-title { font-size: 1.3em; font-weight: bold; color: #111827; margin-bottom: 20px; border-bottom: 1px solid #e5e7eb; padding-bottom: 8px; } 
.label { display: inline-block; min-width: 100px; font-weight: 600; color: #374151; } 
.value { margin-bottom: 12px; color: #1f2937; } 
.image-preview { width: 100%; max-height: 280px; object-fit: cover; border-radius: 10px; margin-bottom: 15px; box-shadow: 0 1px 5px rgba(0,0,0,0.1); } 
.btn-action { background-color: #2563eb; color: white !important; border: none; padding: 10px 16px; border-radius: 8px; font-size: 0.95em; font-weight: 500; text-decoration: none; margin-left: 10px; transition: 0.2s; } 
.btn-action:hover { background-color: #1d4ed8; }


</style>

<div class="container">
    <h2>예약 상세 정보</h2>

    <!-- 숙소 정보 -->
    <div class="section">
        <div class="section-title">${reservation.stayvo.stay_name}</div>
        <img src="<%= ctxPath %>/images/${reservation.stayvo.stay_thumbnail}" style="width:100%; max-height:300px; object-fit:cover; border-radius: 10px;" />

        <div class="value"><span class="label">평점:</span> ${reservation.stayvo.stay_score}점</div>
        <div class="value"><span class="label">전화:</span> ${reservation.stayvo.stay_tel}</div>
        <div class="value">
            <span class="label">주소:</span> 
            ${reservation.stayvo.address} 
            ${reservation.stayvo.detailaddres} 
            ${reservation.stayvo.extraaddress}
        </div>
    </div>

    <!-- 객실 정보 -->
    <div class="section">
        <div class="section-title">${reservation.roomvo.room_grade} 객실</div>
        <img src="<%= ctxPath %>/images/${reservation.roomvo.room_thumbnail}" style="width:100%; max-height:300px; object-fit:cover; border-radius: 10px;" />

        <div class="value"><span class="label">1박 요금:</span> <fmt:formatNumber value="${reservation.roomvo.price_per_night}" pattern="#,###" /> 원</div>
        
    </div>

    <!-- 예약 정보 -->
    <div class="section">
        <div class="section-title">예약 정보</div>
        <div class="value"><span class="label">예약번호:</span> ${reservation.reserv_no}</div>
        <div class="value"><span class="label">예약자 ID:</span> ${reservation.fk_user_id}</div>
        <div class="value"><span class="label">예약일자:</span> ${reservation.reserv_date}</div>
        <div class="value">
            <span class="label">체크인:</span> ${reservation.checkin_date}
            <span style="margin-left: 20px;" class="label">체크아웃:</span> ${reservation.checkout_date}
        </div>
        <div class="value"><span class="label">총 결제액:</span> <fmt:formatNumber value="${reservation.reserv_payment}" pattern="#,###" /> 원</div>
        <div class="value"><span class="label">사용 포인트:</span> <fmt:formatNumber value="${reservation.spent_point}" pattern="#,###" /> P</div>
        <div class="value">
            <span class="label">리뷰:</span>
            <c:choose>
                <c:when test="${reservation.review_written}">✔ 작성 완료</c:when>
                <c:otherwise>❌ 미작성</c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- 하단 버튼 -->
    <div class="section" style="text-align: right;">
        <a href="<%= ctxPath %>/reservationList.hb?user_id=${loginUser.user_id}" class="btn-action">목록으로</a>
        <a href="<%= ctxPath %>/reservationCancel.hb?reserv_no=${reservation.reserv_no}"
           class="btn-action" >예약 취소</a>
    </div>
</div>

<jsp:include page="../footer1.jsp" />