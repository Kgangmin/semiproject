
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSTL(JSP Standard Tag Library)을 사용하기 위한 선언. 목록(예약내역) 표시에 유용합니다. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctx_Path = request.getContextPath(); %>

<jsp:include page="../header1.jsp" />

<script type="text/javascript" src="<%= ctx_Path%>/js/member/member.js"></script>

    <style>
        
        body { font-family: sans-serif; color: #333; }
        .container { width: 800px; margin: 40px auto; padding: 20px; }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; padding-bottom: 10px; }
        .header h1 { font-size: 24px; }
        .user-info { margin-top: 30px; }
        .user-info a { color: #555; text-decoration: none; margin-left: 15px; font-size: 14px; }
        .points-reviews { border-top: 1px solid #eee; border-bottom: 1px solid #eee; margin-top: 30px; padding: 20px 0; }
        .points-reviews .item { display: flex; justify-content: space-between; padding: 10px 0; }
        .reservation-history { margin-top: 30px; }
        .reservation-list { border: 1px solid #ccc; min-height: 150px; padding: 20px; }
        .reservation-item { padding: 10px; border-bottom: 1px solid #f0f0f0; }
        .reservation-item:last-child { border-bottom: none; }
        .user-links a {margin-left: 15px;color: #555;text-decoration: none;font-size: 14px;}
        .user-links a:first-child {margin-left: 0;}
        #point { font-weight: bold;}
    </style>
    
    
</head>




<body>


    <div class="container">
        <div class="header">
            <h1>마이페이지</h1>
            <div>
                <span>🔔</span>
                <span>🛒</span>
            </div>
        </div>

        <%-- 
            서블릿에서 다음과 같이 사용자 정보를 request 객체에 담아 전달했다고 가정합니다.
            UserDTO user = new UserDTO("홍길동", 0);
            request.setAttribute("user", user);
        --%>
	    
	   	<h3 class="user-name">${loginUser.user_name} 님</h3>
	   
	
		
		<div class="user-links">
		    <a href="javascript:goEmailChange('${loginUser.user_id}','<%= ctx_Path%>')">이메일 변경</a>
		    &nbsp;
		    <a href="/changePassword">비밀번호 변경</a>
		</div>

        <div class="points-reviews">
            <div class="item">
                <span>포인트</span>
                <span id="point">${loginUser.point}pt &nbsp;&nbsp;></span>
            </div>
            <div class="item">
                <span>내 후기</span>
                <span>></span>
            </div>
        </div>

        <div class="reservation-history">
            <h3>예약내역</h3>
            <%-- 
                서블릿에서 예약 목록(List<ReservationDTO>)을 request 객체에 담아 전달했다고 가정합니다.
                List<ReservationDTO> reservationList = ...;
                request.setAttribute("reservationList", reservationList);
            --%>
            <div class="reservation-list">
                <c:choose>
                    <c:when test="${not empty reservationList}">
                        <%-- JSTL의 forEach를 사용하여 목록을 반복 출력 --%>
                        <c:forEach var="reservation" items="${reservationList}">
                            <div class="reservation-item">
                                <p><strong>예약 번호:</strong> ${reservation.id}</p>
                                <p><strong>예약 내용:</strong> ${reservation.serviceName}</p>
                                <p><strong>예약 날짜:</strong> ${reservation.reservationDate}</p>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>예약 내역이 없습니다.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>
     

<jsp:include page="../footer1.jsp" />
