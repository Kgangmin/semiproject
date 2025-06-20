<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
    String ctxPath = request.getContextPath();
%>
    
<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" >

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script>

<%-- jQueryUI CSS 및 JS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script>

<jsp:include page="../header1.jsp"/>

<div class="container">

	<div class="mx-auto mt-0">
		<%------------------------- 상단 숙박업소 사진 carousel START -------------------------%>	
		<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<c:if test="${not empty requestScope.imgList}">
					<c:forEach items="${requestScope.imgList}" varStatus="status">
						<c:if test="${status.index == 0}">
							<li data-target="#carouselExampleIndicators" data-slide-to="${status.index}" class="active"></li>
						</c:if>
						<c:if test="${status.index > 0}">
							<li data-target="#carouselExampleIndicators" data-slide-to="${status.index}"></li>
						</c:if>
					</c:forEach>
				</c:if>
			</ol>
			<div class="carousel-inner">
				<c:if test="${not empty requestScope.imgList}">
					<c:forEach var="imageVO" items="${requestScope.imgList}" varStatus="status">
						<c:if test="${status.index == 0}">
							<div class="carousel-item active">
								<img src="<%= ctxPath%>/images/${imageVO.imgfilename}" class="d-block w-100" alt="...">
								<div class="carousel-caption d-none d-md-block">
									<h5>${imageVO.imgname}</h5>
								</div>            
							</div>
						</c:if>                   
						<c:if test="${status.index > 0}">
							<div class="carousel-item">
								<img src="<%= ctxPath%>/images/${imageVO.imgfilename}" class="d-block w-100" alt="...">
								<div class="carousel-caption d-none d-md-block">
									<h5>${imageVO.imgname}</h5>
								</div>            
							</div>
						</c:if>
					</c:forEach>
				</c:if>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
		<%------------------------- 상단 숙박업소 사진 carousel END -------------------------%>
		
		
	</div>
		
</div>

<jsp:include page="../footer1.jsp"/>