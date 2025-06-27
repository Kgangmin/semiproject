<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath   = request.getContextPath();
%>

<jsp:include page="/WEB-INF/header1.jsp" />

<div class="container my-4">
	<!-- 1. 숙소 이미지 캐러셀 -->
	<p>stay_thumbnail: ${stay.stay_thumbnail}</p>
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
    
	<!-- 2. 숙소 명칭 및 리뷰목록 -->
	<div class="mt-4 d-flex align-items-center">
		<h2 class="mb-0">${stay.stay_name}</h2>
	</div>
    
</div>
<jsp:include page="/WEB-INF/footer1.jsp" />