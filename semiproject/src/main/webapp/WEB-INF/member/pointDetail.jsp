<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String ctx_Path = request.getContextPath();
%>

<jsp:include page="../header1.jsp" />

<!-- 폰트어썸 아이콘용 CDN (아이콘이 안 보이면 이거 꼭 필요!) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />

<style>
    body { font-family: sans-serif; color: #333; }
    .container { width: 800px; margin: 40px auto; padding: 20px; }
    .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; padding-bottom: 10px; }
    .header h1 { font-size: 24px; }

    .points-reviews {
        border-top: 1px solid #eee;
        border-bottom: 1px solid #eee;
        margin-top: 30px;
        padding: 20px 0;
    }

    .points-reviews .item {
        display: flex;
        justify-content: space-between;
        padding: 10px 0;
    }

    .points-reviews .item a {
        display: flex;
        justify-content: space-between;
        width: 100%;
        color: inherit;
        text-decoration: none;
    }

    .points-reviews .item a:hover {
        color: #007bff;
    }

    #point {
        font-weight: bold;
    }

    .user-name img {
        height: 60px;
        vertical-align: middle;
    }

    .user-links a {
        margin-left: 15px;
        color: #555;
        text-decoration: none;
        font-size: 14px;
    }

    .user-links a:first-child {
        margin-left: 0;
    }

    .arrow-icon {
        font-size: 12px;
        color: #888;
    }
    
</style>

<body>
    <div class="container">
        <div class="header">
            <h1>마이페이지</h1>
        </div>

        <h3 class="user-name">
            ${loginUser.user_name}님
            <img src="<%= ctx_Path %>/images/grade${requestScope.fk_grade_no}.png" alt="등급">
        </h3>

        <div class="user-links" style="visibility: hidden;">
            <a href="javascript:goEmailChange('${loginUser.user_id}','<%= ctx_Path %>')">이메일 변경</a>
            <a href="javascript:goPwdChange('${loginUser.user_id}','${loginUser.user_pwd}','<%= ctx_Path %>')">비밀번호 변경</a>
        </div>

        <div class="points-reviews">
            <div class="item">
                <a href="<%= ctx_Path %>/myPage.hb?user_id=${loginUser.user_id}&fk_grade_no=${requestScope.user_grade}">
                    <span>포인트</span>
                    <span id="point">${loginUser.point}pt &nbsp;&nbsp;<i class="fas fa-chevron-right arrow-icon"></i></span>
                </a>
            </div>
        </div>
  <!-- 제목 -->
	<h2 class="mb-4  border-primary pb-2">포인트 적립/차감 내역</h2>

	<c:choose>
	    <c:when test="${not empty requestScope.pointList}">
	        <!-- 테이블 영역 -->
	        <div class="table-responsive">
	            <table class="table table-bordered table-hover align-middle text-center">
	                <thead class="table-light">
	                    <tr>
	                        <th>예약번호</th>
	                        <th>예약일</th>
	                        <th class="text-end">결제금액</th>
	                        <th class="text-end">사용포인트</th>
	                        <th class="text-end">적립포인트</th>
	                        <th>회원등급</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach var="point" items="${pointList}">
	                        <tr>
	                            <td>${point.reserv_no}</td>
	                            <td>
	                                <fmt:formatDate value="${point.reserv_date}" pattern="yyyy-MM-dd" />
	                            </td>
	                            <td class="text-end">
	                                <fmt:formatNumber value="${point.reserv_payment}" type="number" />
	                            </td>
	                            <td class="text-end">
	                                <c:choose>
	                                    <c:when test="${point.spent_point > 0}">
	                                        -<fmt:formatNumber value="${point.spent_point}" type="number" />
	                                    </c:when>
	                                    <c:otherwise>0</c:otherwise>
	                                </c:choose>
	                            </td>
	                            <td class="text-end">
	                                <fmt:formatNumber value="${point.earned_point}" type="number" />
	                            </td>
	                            <td>${point.grade_name}</td>
	                        </tr>
	                    </c:forEach>
	                </tbody>
	            </table>
	        </div>
	
	        <!-- 페이징 예시 -->
	        <nav aria-label="Page navigation" class="mt-4">
	            <ul class="pagination justify-content-center">
	                <li class="page-item active" aria-current="page">
	                    <a class="page-link" href="#">1</a>
	                </li>
	                <li class="page-item"><a class="page-link" href="#">2</a></li>
	                <li class="page-item"><a class="page-link" href="#">3</a></li>
	            </ul>
	        </nav>
	    </c:when>
	    <c:otherwise>
	        <div class="text-center fst-italic text-muted py-4">
	            포인트 사용내역이 없습니다
	        </div>
	    </c:otherwise>
	</c:choose>

    </div>
	
<jsp:include page="../footer1.jsp" />
</body>
