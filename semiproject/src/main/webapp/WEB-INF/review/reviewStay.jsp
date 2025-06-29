<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String ctxPath		= request.getContextPath();
	
	String currentUri	= request.getRequestURI();	// 예: /semiproject/stayDetail.jsp
	String currentQuery	= request.getQueryString();	// 예: stay_no=1
	
	// 기본 탭 설정: 처음 진입했거나 stayDetail일 경우 Home을 active
	boolean isHomeActive	= currentUri.contains("stayDetail.jsp") || currentQuery == null;
	boolean isReviewActive	= currentUri.contains("reviewStay.jsp");
%>

<c:set var="score" value="${review.reserv_score}" />
<script>
//	리뷰 하나에 대한 별점을 0.5점 단위로 아이콘의 별 채우기
function renderStars(score)
{	
	const fullStars = Math.floor(score);	//	소수점 이하 버리기
	const hasHalfStar = (score - fullStars) >= 0.5;
	const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
	let html = '';

	for(let i=0; i<fullStars; i++)
	{
        html += '<i class="fas fa-star"></i>';
    }
    if(hasHalfStar)
    {
        html += '<i class="fas fa-star-half-alt"></i>';
    }
    for(let i=0; i<emptyStars; i++)
    {
        html += '<i class="far fa-star"></i>';
    }
    return html + ' <span>' + score + '점</span>';
}


</script>

<jsp:include page="/WEB-INF/header1.jsp" />

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
    
	<!-- 2. 숙소 명칭 -->
	<div class="mt-4 d-flex justify-content-between align-items-center">
		<h2 class="mb-0">${stay.stay_name}</h2>
		
	<!-- 3. 숙소 평균별점 -->
		<div class="text-center d-flex align-items-center">
			<c:choose>
				<c:when test="${stayScore == '0'}">
					<!-- 빈 별 아이콘 (0점) -->
					<i class="far fa-star text-primary" style="font-size:18px; color:gray;"></i>
				</c:when>
				<c:otherwise>
					<!-- 채워진 별 아이콘 -->
					<i class="fas fa-star text-primary" style="font-size:18px; color:gold;"></i>
				</c:otherwise>
			</c:choose>
			<!-- 평균 평점 출력 -->
			<span style="font-size:25px; margin-left:8px;">${stayScore}</span>
		</div>
	</div>
	
		

	
	<!-- 4. 숙박업소 정보 / 해당 숙박업소 리뷰목록 뷰 선택 탭 -->
	<div>
	  	<ul class="nav nav-pills nav-justified nav-tabs">
			<li class="nav-item">
				<a class="nav-link <%= isHomeActive ? "active" : "" %>" href="<%=ctxPath%>/stayDetail.hb?stay_no=${stay.stay_no}">
					Info
				</a>
			</li>
			<li class="nav-item">
				<a class="nav-link <%= isReviewActive ? "active" : "" %>" href="<%=ctxPath%>/reviewStay.hb?stay_no=${stay.stay_no}">
					Review
				</a>
			</li>
		</ul>
	</div>

	<!-- 5-1. room_grade 선택 필터 -->
	<div class="container">
		<form method="get" action="reviewStay.hb">
			<input type="hidden" name="stay_no" value="${stay.stay_no}" />
			<select name="room_grade" class="form-control" onchange="this.form.submit();">
				<option value="all" ${selectedGrade == 'all' ? 'selected' : ''}>객실 전체</option>
				<c:forEach var="grade" items="${roomGradeList}">
					<c:set var="value" value="${grade.rvo.room_grade}" />
					<option value="${value}" ${value == selectedGrade ? 'selected' : ''}>${value}</option>
				</c:forEach>
			</select>
		</form>
	</div>
    
    <!-- 5-2. 리뷰 목록 -->
    <div class="mt-4">
    	<h4>리뷰 목록</h4>
    	<c:if test="${empty reviewList}">
    		<p>아직 작성된 리뷰가 없습니다.</p>
    	</c:if>
		
		<c:forEach var="review" items="${reviewList}">
			<div class="mb-4 border-bottom pb-3" data-room-grade="${review.rvo.room_grade}">
				<!-- 별점 -->
				<div class="text-primary" id="star-container-${review.review_no}"></div>
				<script>
					document.getElementById("star-container-${review.review_no}").innerHTML = renderStars(${review.review_score});
				</script>

				<!-- 리뷰 내용 -->
				<p>${review.review_contents}</p>

				<!-- 작성자 아이디 (앞 3글자 + * 나머지) -->
				<p>
					작성자:${fn:substring(review.rsvvo.fk_user_id, 0, 3)}
					<c:forEach var="i" begin="4" end="${fn:length(review.rsvvo.fk_user_id)}">
						*
					</c:forEach>
				</p>

				<!-- 작성일자 -->
				<p>작성일자: ${review.review_writedate}</p>

				<!-- room_grade -->
				<p>객실 등급: ${review.rvo.room_grade}</p>
			</div>
		</c:forEach>
		
    </div>
    
    
</div>
<jsp:include page="/WEB-INF/footer1.jsp" />