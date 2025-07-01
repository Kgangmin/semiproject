<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="../header1.jsp" />

<style>
	.reservation-card {border: 1px solid #ddd; border-radius: 10px; padding: 20px; margin-bottom: 20px; background-color: #fff;box-shadow: 0 2px 6px rgba(0,0,0,0.05);}
	.reservation-header {display: flex;justify-content: space-between;margin-bottom: 10px;font-weight: bold;color: #333;}
	.reservation-info {display: flex; gap: 20px;align-items: center;}
	.reservation-info img { width: 120px; height: 90px;object-fit: cover;border-radius: 6px; }
	.reservation-details { flex: 1;}
	.reservation-actions { text-align: right;margin-top: 10px;}
	.btn-action { background-color: #0369a1;color: white;border: none;padding: 6px 12px;border-radius: 6px;font-size: 0.9em; margin-left: 5px;}
	.btn-action.disabled { background-color: #ccc; cursor: default; }
	.status-complete {color: green;font-weight: bold;font-size: 0.9em;}
	.no-data {padding: 30px;text-align: center; font-size: 1.1em;color: gray;}
    .reservation-date { font-size: 0.9em;color: #666;}
    .status-box {padding: 5px 10px;border-radius: 20px;font-size: 0.9em;font-weight: bold;text-align: center;min-width: 80px;}
	.status-progress {background-color: #dbeafe; /* 연한 파랑 */color: #1d4ed8; /* 진한 파랑 */border-radius: 20% }
	.status-complete {background-color: #ecfdf5; /* 연한 초록 */color: #059669; /* 진한 초록 */border-radius: 20% }
	
	.reservation-card:hover {  transform: translateY(-5px); box-shadow: 0 6px 12px rgba(0,0,0,0.1);}

</style>

<div class="container mt-5">
	<form method="get" action="<%= ctxPath%>/reservationList.hb">
	  <input type="hidden" name="user_id" value="${loginUser.user_id}" />
	  
	  <label for="statusFilter"><strong>예약 상태</strong></label>
	  <select name="status" onchange="this.form.submit()">
	    <option value="" <c:if test="${empty param.status}">selected</c:if>>전체</option>
	    <option value="진행중" <c:if test="${param.status == '진행중'}">selected</c:if>>진행중</option>
	    <option value="완료" <c:if test="${param.status == '완료'}">selected</c:if>>완료</option>
	  </select>
	</form>
	    
	
    <h2 class="mb-4">예약내역</h2>

    <c:if test="${not empty requestScope.reservationList}">
        <c:forEach var="rvo" items="${requestScope.reservationList}" varStatus="status">
            <div class="reservation-card">
            	<a class="clean-link" href="<%= ctxPath %>/stayDetail.hb?stay_no=${rvo.stayvo.stay_no}">
                <div class="reservation-header">
				    <div>ID ${rvo.reserv_no}</div>
				    <div class="status-box">
				        <c:choose>
				            <c:when test="${rvo.reserv_status == '진행중'}">
				                <span class="status-progress">진행중</span>
				            </c:when>
				            <c:otherwise>
				                <span class="status-complete">완료</span>
				            </c:otherwise>
				        </c:choose>
				    </div>
				</div>

                <div class="reservation-info">
                 
                    <img src="${pageContext.request.contextPath}/images/${rvo.stayvo.stay_thumbnail}" />

                    <div class="reservation-details">
                        <div style="font-weight: bold; font-size: 1.1em;">${rvo.stayvo.stay_name}</div>
                        <div>${rvo.roomvo.room_grade}</div>
                        <div class="reservation-date">
						    체크인: ${fn:substring(rvo.checkin_date, 0, 10)} ~
						    체크아웃: ${fn:substring(rvo.checkout_date, 0, 10)}
						</div>
                        <div>총 결제액: <fmt:formatNumber value="${rvo.reserv_payment}" pattern="#,###" />원</div>
                    </div>
                  
                </div>
				</a>	
               <div class="reservation-actions">
				   <button class="btn-action" onclick="location.href='<%= ctxPath%>/reservationDetail.hb?reserv_no=${rvo.reserv_no}&user_id=${loginUser.user_id}'">예약 확인/변경하기</button>
				
				   <c:choose>
				       <c:when test="${rvo.review_written}">
				           <button class="btn-action disabled">✔ 후기 작성 완료</button>
				       </c:when>
				       <c:otherwise>
				           <form method="post" action="<%= ctxPath%>/reviewWrite.hb" style="display: inline;">
				               <input type="hidden" name="reserv_id" value="${rvo.reserv_no}" />
				                <input type="hidden" name="user_id" value="${loginUser.user_id}" />
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

  
</div>

<jsp:include page="../footer1.jsp" />
