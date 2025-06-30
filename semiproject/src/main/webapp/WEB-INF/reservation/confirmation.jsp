<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container my-5 text-center">
  <h2>예약이 완료되었습니다!</h2>
  <p>예약번호: <strong>${reserv_no}</strong></p>
  <a href="<%=ctxPath%>/index.hb" class="btn btn-primary mt-4">
    메인으로 돌아가기
  </a>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />
