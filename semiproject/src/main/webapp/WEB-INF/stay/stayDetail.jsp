<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

    <!-- 4. 날짜 선택 -->
    <div class="mb-3">
    <label for="stayDate">체크인 날짜 선택</label>
            <input type="text"
                   id="stayDate"
                   name="stayDate"
                   class="form-control"
                   placeholder="체크인 날짜 선택"
                   readonly
                   style="width: 100%;" >
                   <strong id="dateCount" class="form-text text-muted mt-1"></strong>
          </div>


   <!-- 5. 객실 목록 (한 줄에 한 개씩) -->
	<div class="mt-5">
	  <h5>객실 정보</h5>
	  <div class="list-group">
	    <c:forEach var="room" items="${roomList}">
	      <div class="list-group-item d-flex align-items-center mb-5">
	        
	        <!-- 5-1) 왼쪽: 객실 썸네일 -->
	        <img src="<%=ctxPath%>/images/${room.room_thumbnail}"
	             class="img-thumbnail img-modal"
	             style="width:200px; height:150px; object-fit:cover;"
	             alt="객실 사진">
	        
	        <!-- 5-2) 가운데: 등급, 가격 -->
	        <div class="ml-3 flex-grow-1">
	          <h6 class="mb-1">${room.room_grade}</h6>
	          <p class="mb-0">
	            1박당: 
	            <fmt:formatNumber value="${room.price_per_night}" pattern="#,##0"/>
	            원
	          </p>
	        </div>
	        
	        <!-- 5-3) 오른쪽: 예약 버튼 -->
		<!--	<a href="<%=ctxPath%>/room/roomDetail?stay_no=${stay.stay_no}&room_no=${room.room_no}&date=${param.stayDate}"
	           class="btn btn-primary btn-sm ml-3">
	          예약하기
	        </a>-->
	        <button type="button" class="btn btn-primary btn-sm ml-3 book-btn"
	        		data-stay-no="${stay.stay_no}"
	        		data-room-no="${room.room_no}">
	        	예약하기
	        </button>
	      
	      </div>
	    </c:forEach>
	  </div>
	</div>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

<!-- Image Preview Modal -->
<div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
    <div class="modal-content bg-transparent border-0">
      <img src="" id="modalImage" class="img-fluid" alt="확대 이미지" style="max-height:95vh; max-width:95vw;" />
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

    new kakao.maps.Marker({
      position: map.getCenter(),
      map: map
    });
    
  });

//daterangepicker 초기화
  $(function() {
    $('#stayDate').daterangepicker({
      locale: {
        format: 'YYYY-MM-DD',
        separator: ' ~ ',
        applyLabel: '적용',
        cancelLabel: '취소'
      },
      opens: 'center'
    }, function(start, end) {
        // 선택된 날짜 범위의 총 일수(포함) 계산
        var days = end.diff(start, 'days') + 1;
        $('#dateCount').text('총 ' + days + '박');
      });
    
 // 3) Image modal on click
    $('.img-modal').on('click', function() {
      var src = $(this).attr('src');
      $('#modalImage').attr('src', src);
      $('#imageModal').modal('show');
    });
    
  });
  
  	//	예약하기 버튼 클릭 시 발생 이벤트
  	$(document).on('click', '.book-btn', function()
	{
		const stayNo = $(this).data('stay-no');
		const roomNo = $(this).data('room-no');
		const dateRange = $('#stayDate').val(); // 사용자가 선택한 날짜 범위
		
		console.log("stayNo:", stayNo);
	    console.log("roomNo:", roomNo);
	    console.log("dateRange:", dateRange);
		
		if (!dateRange)
		{
	    	alert('날짜를 선택해주세요.');
	    	return;
		}
		
		const encodedRoomNo = encodeURIComponent(roomNo);
		const encodedDate = encodeURIComponent(dateRange);

		console.log(encodedRoomNo);
		console.log(encodedDate);
		const url = `/semiproject/roomDetail.hb?stay_no=${stayNo}&room_no=${encodedRoomNo}&date=${encodedDate}`;
		
	    console.log(url);
	   	window.location.href = url;
	});
</script>