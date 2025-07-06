<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/header1.jsp" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>회원 상세정보</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-4">

    <h2 class="mb-4 ">회원 상세정보</h2>

    <table class="table table-bordered">
        <tbody>
            <tr>
                <th scope="row">회원 아이디</th>
                <td>${member.user_id}</td>
            </tr>
            <tr>
                <th scope="row">회원명</th>
                <td>${member.user_name}</td>
            </tr>
            <tr>
                <th scope="row">이메일</th>
                <td>${member.email}</td>
            </tr>
            <tr>
                <th scope="row">전화번호</th>
                <td>${member.formattedMobile}</td>
            </tr>
            <tr>
                <th scope="row">생년월일</th>
                <td><c:out value="${member.birthday}"/></td>
            </tr>
            <tr>
                <th scope="row">회원등급</th>
                <td><c:choose>
                    <c:when test="${member.fk_grade_no == 6}">WHITE</c:when>
                    <c:when test="${member.fk_grade_no == 5}">SILVER</c:when>
                    <c:when test="${member.fk_grade_no == 4}">GOLD</c:when>
                    <c:when test="${member.fk_grade_no == 3}">PLATINUNM</c:when>
                    <c:when test="${member.fk_grade_no == 2}">VIP</c:when>
                    <c:when test="${member.fk_grade_no == 1}">VVIP</c:when>
                    
                    </c:choose></td>
            </tr>
            <tr>
			    <th scope="row">누적 결제금액</th>
			    <td>
			        <fmt:formatNumber value="${member.total_payment}" type="number" groupingUsed="true" var="formattedTotalPayment"/>
			        ₩<c:out value="${formattedTotalPayment}"/>
			    </td>
			</tr>
            <tr>
			    <th scope="row">포인트</th>
			    <td>
			        <fmt:formatNumber value="${member.point}" type="number" groupingUsed="true" var="formattedPoint"/>
			        <c:out value="${formattedPoint}"/>P
			    </td>
			</tr>
            <tr>
                <th scope="row">가입일자</th>
                <td><c:out value="${member.register_date}"/></td>
            </tr>
            <tr>
                <th scope="row">마지막 비밀번호 변경일</th>
                <td><c:out value="${member.last_pwd_update}"/></td>
            </tr>
            <tr>
                <th scope="row">회원 탈퇴 여부</th>
                <td><c:choose>
                    <c:when test="${member.is_withdrawn == 1}">예</c:when>
                    <c:otherwise>아니오</c:otherwise>
                </c:choose></td>
            </tr>
            <tr>
                <th scope="row">회원 휴면 여부</th>
                <td><c:choose>
                    <c:when test="${member.is_active == 1}">예</c:when>
                    <c:otherwise>아니오</c:otherwise>
                </c:choose></td>
            </tr>
            <tr>
                <th scope="row">관리자 권한 여부</th>
                <td><c:choose>
                    <c:when test="${member.access_level == 1}">예</c:when>
                    <c:otherwise>아니오</c:otherwise>
                </c:choose></td>
            </tr>
        </tbody>
    </table>

    <a href="javascript:history.back()" class="btn btn-primary text-white">목록으로 돌아가기</a>

</div>

<script src="<%=request.getContextPath()%>/js/jquery-3.7.1.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
