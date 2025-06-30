<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String ctxPath  = request.getContextPath();
    String keyword  = request.getAttribute("keyword") == null ? ""
                      : (String)request.getAttribute("keyword");
    String period   = request.getAttribute("period")  == null ? ""
                      : (String)request.getAttribute("period");
    // "YYYY-MM-DD ~ YYYY-MM-DD" 분리
    String[] dates  = period.split("~");
    String checkin  = dates.length > 0 ? dates[0].trim() : "";
    String checkout = dates.length > 1 ? dates[1].trim() : "";
%>

<jsp:include page="/WEB-INF/header1.jsp" />

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css"/>

<div class="container mt-5">
  <!-- 검색어 + 기간 표시 -->
  <h4>'<c:out value="${keyword}"/>' 검색 결과 :</h4>

  <!-- 검색 폼 -->
  <form id="searchForm"
        action="<%=ctxPath%>/reservation/searchResult.hb"
        method="get"
        class="form-inline mb-4">
    <input type="text" name="keyword"
           value=<c:out value="${keyword}"/>
           class="form-control mr-2"
           placeholder="숙소명 또는 지역명"
           style="width:250px;" />

    <div class="input-group mr-2">
      <input type="text"
             id="dateRangePicker"
             name="period"
             class="form-control"
             placeholder="기간 선택"
             readonly
             value=<c:out value="${period}"/>
             style="width:200px;" />
      <div class="input-group-append">
        <span class="input-group-text"><i class="far fa-calendar-alt"></i></span>
      </div>
    </div>

    <small id="dateCount" class="form-text text-muted mr-2"></small>

    <button type="submit" class="btn btn-primary">검색</button>
  </form>

  <!-- 결과 리스트 & 무한스크롤 컨테이너 -->
  <div class="container mt-4 mb-5">
    <div id="stayContainer" class="row"></div>
    <div id="endMessage" class="text-center mt-3 text-muted"></div>
  </div>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

<!-- 필수 스크립트 로드 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.4/moment.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/daterangepicker@3.1/daterangepicker.min.js"></script>

<script>
  // JSP에서 분리한 날짜값을 JS 변수로
  var ctxPath  = '<%=ctxPath%>';
  var keyword  = '<%=keyword.replace("'", "\\'")%>';
  var checkin  = '<%=checkin%>';
  var checkout = '<%=checkout%>';

  $(function(){
    // 1) DateRangePicker 초기화
    $('#dateRangePicker').daterangepicker({
      locale: {
        format:     'YYYY-MM-DD',
        separator:  ' ~ ',
        applyLabel: '적용',
        cancelLabel:'취소'
      },
      opens:     'center',
      minDate:   moment().startOf('day'),
      startDate: checkin  ? moment(checkin,  'YYYY-MM-DD') : moment(),
      endDate:   checkout ? moment(checkout, 'YYYY-MM-DD') : moment().add(1,'days')
    }, function(start, end){
      // 선택된 기간이 바뀌면 input 값과 카운트 업데이트
      var formatted = start.format('YYYY-MM-DD') + ' ~ ' + end.format('YYYY-MM-DD');
      $('#dateRangePicker').val(formatted);
      var days = end.diff(start,'days') + 1;
      $('#dateCount').text('총 ' + days + '박');
    });

    // 2) 초기 '총 x박' 표시
    if (checkin && checkout) {
      var s = moment(checkin, 'YYYY-MM-DD'),
          e = moment(checkout,'YYYY-MM-DD'),
          d = e.diff(s,'days') + 1;
      $('#dateCount').text('총 ' + d + '박');
    }

    // 3) AJAX 무한스크롤 로직은 외부 파일에서
    //    searchScroll.js 에서 loadStays() 호출
    $.getScript(ctxPath + '/js/searchScroll.js');
  });
</script>
