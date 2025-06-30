<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath  = request.getContextPath();
    String keyword  = request.getAttribute("keyword")  == null ? "" 
                      : (String)request.getAttribute("keyword");
    String period   = request.getAttribute("period")   == null ? "" 
                      : (String)request.getAttribute("period");
    // "YYYY-MM-DD ~ YYYY-MM-DD" 분리
    String[] dates  = period.split("~");
    String checkin  = dates.length > 0 ? dates[0].trim() : "";
    String checkout = dates.length > 1 ? dates[1].trim() : "";
%>

<jsp:include page="/WEB-INF/header1.jsp" />
<style>
.card-img-top {
  width: 100%;        /* 가로는 카드 너비에 딱 맞추고 */
  height: 200px;      /* 원하는 높이로 고정 */
  object-fit: cover;  /* 이미지 비율을 유지하며 잘려서 채워줌 */
}
 #stayContainer .col-md-6 {
    padding-left: 1.5rem;
    padding-right: 1.5rem;
 }
</style>

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css"/>

<div class="container mt-5">
  <!-- 검색어 + 기간 표시 -->
  <h4>'<c:out value="${keyword}"/>' 검색 결과 :</h4>

  <!-- 검색 폼 -->
  <form id="searchForm"
        action="<%= ctxPath %>/reservation/searchResult.hb"
        method="get"
        class="form-inline mb-4">
    <input type="text" name="keyword"
           value="<c:out value='${keyword}'/>"
           class="form-control mr-2"
           placeholder="숙소명 또는 지역명"
           style="width:250px;" />
    <input type="text" id="dateRangePicker" name="period"
           value="<c:out value='${period}'/>"
           class="form-control mr-2"
           placeholder="기간 선택" readonly
           style="width:200px;" />
    <button type="submit" class="btn btn-primary">검색</button>
  </form>

  <!-- 결과 리스트 & 무한스크롤 컨테이너 -->
  <div class="container mt-4 mb-5">
    <div id="stayContainer" class="row"></div>
    <div id="endMessage" class="text-center mt-3 text-muted"></div>
  </div>
</div>

<!-- 필수 스크립트 로드 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.4/moment.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/daterangepicker@3.1/daterangepicker.min.js"></script>

<!-- 전역 변수 설정 -->
<script>
  var ctxPath  = '<%= ctxPath %>';
  var keyword  = '<%= keyword.replace("'", "\\'") %>';
  var checkin  = '<%= checkin %>';
  var checkout = '<%= checkout %>';
</script>

<!-- AJAX 무한스크롤 로직 로드 -->
<script src="<%= ctxPath %>/js/searchScroll.js"></script>

<jsp:include page="/WEB-INF/footer1.jsp" />
