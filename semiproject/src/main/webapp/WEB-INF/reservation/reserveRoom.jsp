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
    <!-- 1. 객실 이미지 캐러셀 -->
    <div id="roomCarousel" class="carousel slide" data-ride="carousel">
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
	        <a href="<%=ctxPath%>/reserveRoom?stay_no=${stay.stay_no}"
	               + "&room_no=${room.room_no}&date=${param.stayDate}"
	           class="btn btn-primary btn-sm ml-3">
	          예약하기
	        </a>
	      
	      </div>
	    </c:forEach>
	  </div>
	</div>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />