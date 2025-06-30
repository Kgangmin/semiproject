<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../header1.jsp" />

<div class="reservation-filter">
    <button onclick="filterReservations('all')">전체</button>
    <button onclick="filterReservations('upcoming')">진행중</button>
    <button onclick="filterReservations('completed')">완료</button>
    <button onclick="filterReservations('cancelled')">취소</button>
</div>

<div id="reservationListContainer">
    <c:forEach var="res" items="${reservationList}">
        <div class="reservation-card" data-status="${res.status}">
            <p><strong>숙소:</strong> ${res.stayVO.stay_name}</p>
            <p><strong>객실:</strong> ${res.roomVO.room_name}</p>
            <p><strong>예약일:</strong> ${res.reservationDate}</p>
            <p><strong>상태:</strong> 
                <c:choose>
                    <c:when test="${res.cancelled == true}">취소</c:when>
                    <c:when test="${res.reservationDate lt today}">완료</c:when>
                    <c:otherwise>진행중</c:otherwise>
                </c:choose>
            </p>
        </div>
    </c:forEach>
</div>

<jsp:include page="../footer1.jsp" />