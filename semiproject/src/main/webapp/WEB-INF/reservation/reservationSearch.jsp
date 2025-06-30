<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
    String ctxPath = request.getContextPath();
%>

<jsp:include page="/WEB-INF/header1.jsp" />

<!-- daterangepicker CSS & JS -->
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.4/moment.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/daterangepicker@3.1/daterangepicker.min.js"></script>

<div class="container content-center">
  <!-- 1) 검색 폼 -->
  <div class="row mt-5 mb-4">
    <div class="col-md-10 offset-md-3">
      <form id="searchForm"
            action="<%=ctxPath%>/reservation/searchResult.hb"
            method="get"
            class="d-flex justify-content-center align-items-center">
        
        <!-- 입력부: 세로 스택 -->
         <div class="d-flex flex-column mr-4"  style="width: 700px;">
          <div class="mb-3" >
            <input type="text"
                   id="keyword"
                   name="keyword"
                   class="form-control"
                   placeholder="숙소명 또는 지역명"
                   style="width: 100%;">
          </div>

          <div class="mb-3">
            <input type="text"
                   id="dateRangePicker"
                   name="period"
                   class="form-control"
                   placeholder="기간 선택"
                   readonly
                   style="width: 100%;" >
                   <strong id="dateCount" class="form-text text-muted mt-1"></strong>
          </div>
        </div>
        
        <!-- 버튼: 입력부 오른쪽, 수직 중앙 정렬 -->
        <button type="submit" class="btn btn-primary align-self-center">검색</button>
      </form>
    </div>
  </div>

  <!-- 2) 추천 검색어 -->
		<div class="row justify-content-center align-items-start flex-nowrap">
		  <!-- 좌측 1~5 -->
		  <div class="col-auto text-right" style="margin-right: 128px;">
		    	<h5 class="mb-3" style="height: 16px;">추천검색어</h5>
		    <ol class="list-unstyled mb-1">
		      <c:forEach var="kw" items="${requestScope.detailKeywords}" varStatus="st">
		        <c:if test="${st.index < 5}">
		          <li style="line-height:1.8;">
		            <span class="text-primary">${st.index + 1}</span> – 
		            <a href="javascript:void(0)"
		               onclick="onRecSearch('${kw}')">
		              ${kw}
		            </a>
		          </li>
		        </c:if>
		      </c:forEach>
		    </ol>
		  </div>
		  <!-- 우측 6~10 -->
		  <div class="col-auto text-left" style="margin-left: 128px;">
		  <div class="mb-3" style="visibility:hidden; height:16px;">x</div>
		    <ol class="list-unstyled mb-1" start="6">
		      <c:forEach var="kw" items="${requestScope.detailKeywords}" varStatus="st">
		        <c:if test="${st.index >= 5}">
		          <li style="line-height:1.8;">
		            <span class="text-primary">${st.index + 1}</span> – 
		            <a href="javascript:void(0)"
		               onclick="onRecSearch('${kw}')">
		              ${kw}
		            </a>
		          </li>
		        </c:if>
		      </c:forEach>
		    </ol>
		  </div>
		</div>
		
    </div>


<script>
  // daterangepicker 초기화
  $(function() {
    $('#dateRangePicker').daterangepicker({
      locale: {
        format: 'YYYY-MM-DD',
        separator: ' ~ ',
        applyLabel: '적용',
        cancelLabel: '취소'
      },
      opens: 'center',
      minDate:  moment().startOf('day')
    }, function(start, end) {
        // 선택된 날짜 범위의 총 일수(포함) 계산
        var days = end.diff(start, 'days') + 1;
        $('#dateCount').text('총 ' + days + '박');
      });
  });

//추천 검색어/키워드 클릭 핸들러
  function onRecSearch(keyword) {
    var period = $('#dateRangePicker').val() || '';
    var url = '<%=ctxPath%>/reservation/searchResult.hb?keyword=' 
              + encodeURIComponent(keyword);
    if (period) {
      url += '&period=' + encodeURIComponent(period);
    }
    location.href = url;
  }
</script>

<jsp:include page="/WEB-INF/footer1.jsp" />