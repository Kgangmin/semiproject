<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="../header1.jsp" />

<style>
    .reservation-card {
        border: 1px solid #ddd;
        border-radius: 10px;
        padding: 20px;
        margin-bottom: 20px;
        background-color: #fff;
        box-shadow: 0 2px 6px rgba(0,0,0,0.05);
    }

    .reservation-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 10px;
        font-weight: bold;
        color: #333;
    }

    .reservation-info {
        display: flex;
        gap: 20px;
        align-items: center;
    }

    .reservation-info img {
        width: 120px;
        height: 90px;
        object-fit: cover;
        border-radius: 6px;
    }

    .reservation-details {
        flex: 1;
    }

    .reservation-actions {
        text-align: right;
        margin-top: 10px;
    }

    .btn-action {
        background-color: #0369a1;
        color: white;
        border: none;
        padding: 6px 12px;
        border-radius: 6px;
        font-size: 0.9em;
        margin-left: 5px;
    }

    .btn-action.disabled {
        background-color: #ccc;
        cursor: default;
    }

    .status-complete {
        color: green;
        font-weight: bold;
        font-size: 0.9em;
    }

    .no-data {
        padding: 30px;
        text-align: center;
        font-size: 1.1em;
        color: gray;
    }

    .reservation-date {
        font-size: 0.9em;
        color: #666;
    }
</style>

<div class="container mt-5">
    <h2 class="mb-4">예약내역</h2>

    <c:if test="${not empty requestScope.reservationList}">
        <c:forEach var="rvo" items="${requestScope.reservationList}" varStatus="status">
            <div class="reservation-card">
                <div class="reservation-header">
                    <div>ID ${rvo.reserv_id}</div>
                    <div class="status-complete">${rvo.reserv_status}</div>
                </div>

                <div class="reservation-info">
                    <img src="${rvo.stayvo.image_url}" alt="숙소 이미지" />

                    <div class="reservation-details">
                        <div style="font-weight: bold; font-size: 1.1em;">${rvo.stayvo.stay_name}</div>
                        <div>${rvo.roomvo.room_grade}</div>
                        <div class="reservation-date">
                            체크인: <fmt:formatDate value="${rvo.checkin_date}" pattern="yyyy-MM-dd" /> ~
                            체크아웃: <fmt:formatDate value="${rvo.checkout_date}" pattern="yyyy-MM-dd" />
                        </div>
                        <div>총 결제액: <fmt:formatNumber value="${rvo.reserv_payment}" pattern="#,###" />원</div>
                    </div>
                </div>

                <div class="reservation-actions">
                    <button class="btn-action">예약 확인/변경하기</button>

                    <c:choose>
                        <c:when test="${rvo.review_written}">
                            <button class="btn-action disabled">✔ 후기 작성 완료</button>
                        </c:when>
                        <c:otherwise>
                            <form method="post" action="${ctxPath}/review/write">
                                <input type="hidden" name="reserv_id" value="${rvo.reserv_id}" />
                                <button type="submit" class="btn-action">이용후기 작성하기</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </c:if>

    <c:if test="${empty requestScope.reservationList}">
        <div class="no-data">예약 내역이 없습니다.</div>
    </c:if>

    <div id="pageBar" class="mt-4 text-center">
        <ul class="pagination justify-content-center">${requestScope.pageBar}</ul>
    </div>
</div>

<jsp:include page="../footer1.jsp" />
