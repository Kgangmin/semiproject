<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="stayDetailUrl" value="${pageContext.request.contextPath}/stayDetail.hb" />
<%
    String ctxPath  = request.getContextPath();

	// 1) searchScroll.js 에서 넘긴 checkin/checkout
	String paramCheckin  = request.getParameter("checkin");
	String paramCheckout = request.getParameter("checkout");

	// 1-1) URL 파라미터로 넘어온 period 읽기
    String period   = request.getParameter("period");
    // 3) 우선순위: checkin+checkout 이 있으면 period 갱신
    if (paramCheckin != null && !paramCheckin.isEmpty()
     && paramCheckout!= null && !paramCheckout.isEmpty()) {
        period = paramCheckin + "~" + paramCheckout;
    }

    // 4) period 가 여전히 비어 있으면 오늘~내일로 기본
    if (period == null || period.trim().isEmpty()) {
        java.time.LocalDate today = java.time.LocalDate.now();
        period    = today.toString() + "~" + today.plusDays(1).toString();
    }

    // 5) "YYYY-MM-DD~YYYY-MM-DD" 분리
    String[] parts   = period.split("~");
    String checkin  = parts[0].trim();
    String checkout = parts.length>1 ? parts[1].trim() : "";

    // 3) view data already in request: stay, extraImgList, roomList, nights, wishlistExists, stay.latitude, stay.longitude

    String currentUri = request.getRequestURI();	// 예: /semiproject/stayDetail.jsp
    String currentQuery	= request.getQueryString();	// 예: stay_no=1
    // 기본 탭 설정: 처음 진입했거나 stayDetail일 경우 Home을 active
    boolean isHomeActive	= currentUri.contains("stayDetail.jsp") || currentQuery == null;
    boolean isReviewActive	= currentUri.contains("reviewStay.jsp");
%>
<jsp:include page="/WEB-INF/header1.jsp" />

<!-- daterangepicker CSS & JS -->
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.4/moment.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/daterangepicker@3.1/daterangepicker.min.js"></script>

<div class="container my-4">
    <!-- 1. 숙소 이미지 캐러셀 -->
    <div id="stayCarousel" class="carousel slide" data-ride="carousel">
        <div class="carousel-inner">
            <!-- 메인 썸네일 -->
            <div class="carousel-item active">
                <img src="<%=ctxPath%>/images/${stay.stay_thumbnail}" class="d-block w-100 img-modal" alt="메인 이미지">
            </div>
            <!-- 추가 이미지 -->
            <c:forEach var="img" items="${extraImgList}">
                <div class="carousel-item">
                    <img src="<%=ctxPath%>/images/${img.stay_extraimg_no_filename}" class="d-block w-100 img-modal" alt="추가 이미지">
                </div>
            </c:forEach>
        </div>
        <a class="carousel-control-prev" href="#stayCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#stayCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <!-- 2. 숙소 기본 정보 -->
   <div class="mt-4 d-flex align-items-center">
  <h2 class="mb-0">${stay.stay_name}</h2>
  <div class="ml-auto">
    <a href="<%=ctxPath%>/wishlistToggle.hb?stay_no=${stay.stay_no}">
      <img
        src="<%=ctxPath%>/images/${wishlistExists ? '찜한버튼.png' : '찜버튼.png'}"
        style="width:32px; height:32px; cursor:pointer;"
        alt="찜 버튼"/>
    </a>
  </div>
</div>
   <!-- 숙박업소 정보 / 해당 숙박업소 리뷰목록 뷰 선택 탭 -->
   <div>
      <ul class="nav nav-pills nav-justified nav-tabs">
         <li class="nav-item">
            <a class="nav-link <%= isHomeActive ? "active" : "" %>" href="<%=ctxPath%>/stayDetail.hb?stay_no=${stay.stay_no}">
               Room Info
            </a>
         </li>
         <li class="nav-item">
            <a class="nav-link <%= isReviewActive ? "active" : "" %>" href="<%=ctxPath%>/reviewStay.hb?stay_no=${stay.stay_no}">
               Review
            </a>
         </li>
      </ul>
   </div>
    <div class="mt-2">
        <p>${stay.stay_info}</p>
        <p>연락처: ${stay.stay_tel}</p>
        <p>평점: <strong>${stay.stay_score}</strong> · 조회수: <strong>${stay.views}</strong></p>
    </div>

    <!-- 3. 지도 영역 -->
    <div class="mt-4">
        <h5>찾아오시는 길</h5>
        <div id="map" style="width: 500px; height: 400px;"></div>
    </div>

 <!-- 3. 날짜 선택 & 적용 -->
  <div class="mt-4 mb-3" style="width:300px;">
    <label for="stayDate">체크인–체크아웃</label>
    <input type="text"
           id="stayDate"
           class="form-control"
           placeholder="기간 선택"
           readonly
           value="<%= period %>" />
    <small id="dateCount" class="form-text text-muted mt-1">
      <c:if test="${nights > 0}">
  		<p>총 ${nights}박</p>
		</c:if>
    </small>
  </div>


   <!-- 4. 객실 목록 -->
  <div class="mt-5">
    <div class="d-flex justify-content-between align-items-center mb-2">
    <h5 class="mb-0">객실 정보</h5>
    <%-- 로그인한 사용자가 관리자일 경우 객실등록하기 버튼 생성 --%>
    <c:if test="${sessionScope.loginUser.access_level == 1}">
      <a href="<%=ctxPath%>/admin/roomRegister.hb?stay_no=${stay.stay_no}"
         class="btn btn-sm btn-success text-dark">
        객실등록하기
      </a>
    </c:if>
  </div>
    <div class="list-group">
      <c:forEach var="room" items="${roomList}">
        <div class="list-group-item d-flex align-items-center mb-4">
          <!-- 썸네일 -->
          <img src="<%=ctxPath%>/images/${room.room_thumbnail}"
               class="img-thumbnail img-modal"
               style="width:200px; height:150px; object-fit:cover;" />
          <!-- 등급·가격 -->
          <div class="ml-3 flex-grow-1">
            <h6>${room.room_grade}</h6>
            <p>
              1박당 
              <fmt:formatNumber value="${room.price_per_night}" pattern="#,##0"/> 원
            </p>
          </div>
          <!-- 예약하기 -->
          <c:choose>
      <%-- 1) 로그인 안 된 경우 --%>
      <c:when test="${empty sessionScope.loginUser}">
        <a href="javascript:void(0);"
           class="btn btn-primary btn-sm ml-3"
           onclick="if (confirm('로그인이 필요합니다. 로그인 하시겠습니까?')) { window.location.href='<%=ctxPath%>/login/login.hb'; } else { history.back(); }">
          예약하기
        </a>
      </c:when>
      <%--  2) 로그인 된 경우 --%>
      <c:otherwise>
        <a href="<%=ctxPath%>/reservation/reserveRoom.hb?stay_no=${param.stay_no}&room_no=${room.room_no}&checkin=<%=checkin%>&checkout=<%=checkout%>"
           class="btn btn-primary btn-sm ml-3 text-dark">
          예약하기
        </a>
      </c:otherwise>
    </c:choose>
        </div>
      </c:forEach>
      <c:if test="${empty roomList}">
        <p class="text-muted">선택한 기간에 이용 가능한 객실이 없습니다.</p>
      </c:if>
    </div>
  </div>
</div>


<jsp:include page="/WEB-INF/footer1.jsp" />

<!-- Image Preview Modal -->
<div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content bg-transparent border-0">
      <img src="#" id="modalImage" class="img-fluid" alt="확대 이미지" style="max-height:95vh; max-width:95vw;" />
    </div>
  </div>
</div>


<!-- 1) kakao SDK 불러오기 (JS키 + libraries) -->
<script
  type="text/javascript"
  src="//dapi.kakao.com/v2/maps/sdk.js?appkey=43e1bc9de3bc707bc039e3d3bbf88100&libraries=services">
</script>

<!-- 2) DOM 준비 후 지도 초기화 -->
<script>

  // 2-1) SDK가 완전히 로드된 뒤에 init 함수 실행
  kakao.maps.load(function() {
    var container = document.getElementById('map');
    var option = {
      center: new kakao.maps.LatLng(${stay.latitude}, ${stay.longitude}),
      level: 3
    };
    var map = new kakao.maps.Map(container, option);
    
    var mapTypeControl = new kakao.maps.MapTypeControl();
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
    
    var zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

    new kakao.maps.Marker({
      position: map.getCenter(),
      map: map
    });
    
  });

//daterangepicker
  $(function() {
	  var stayDetailUrl = '<%=ctxPath%>/stayDetail.hb';
	   var stayNo   = '<%=request.getParameter("stay_no")%>';
	    var checkin = '<%=checkin%>';
	    var checkout = '<%=checkout%>';

	    // input에도 초기값 다시 설정
	    $('#stayDate').val(checkin + '~' + checkout);
	    
	    $('#stayDate').daterangepicker({
	      locale: {
	        format:     'YYYY-MM-DD',
	        separator:  '~',
	        applyLabel: '적용',
	        cancelLabel:'취소',
	        
	      },
	      opens: 'center',
	      minDate:  moment(), 
	      startDate: moment(checkin,  'YYYY-MM-DD'),
	      endDate:   moment(checkout, 'YYYY-MM-DD')
	    },function(start, end){
	    	// 선택한 체크인 날짜가 오늘 이전이면 경고
	        if (start.isBefore(moment(), 'day')) {
	          alert('오늘 이후 날짜만 선택 가능합니다.');

	          return;
	        }
	      var period = start.format('YYYY-MM-DD') + '~' + end.format('YYYY-MM-DD');
	      	window.location.href = stayDetailUrl
          	+ '?stay_no=' + stayNo
          	+ '&period='   + encodeURIComponent(period);
	    });


	    
    
 // 3) Image modal on click
    $('.img-modal').on('click', function() {
      var src = $(this).attr('src');
      $('#modalImage').attr('src', src);
      $('#imageModal').modal('show');
    });
    
  });
</script>