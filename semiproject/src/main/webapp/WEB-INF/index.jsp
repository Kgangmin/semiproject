<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
    String ctxPath = request.getContextPath();
%>

<jsp:include page="header1.jsp" />

  <!-- 2) 날짜 지정 바 -->
  <div class="py-3" style="margin:10px auto;; width:800px; ">
    <div class="container-fluid">
      <div class="row align-items-center">
        <!-- 클릭 시 예약 페이지로 이동 -->
        <div class="col-md-10">
          <input
            type="text"
            id="datepickerRange"
            class="form-control" 
            placeholder="예약 하기"
            readonly
            style="cursor:pointer; height: 70px; width: 800px; margin: 0 auto;"
            onclick="location.href='<%=ctxPath%>/reservation/reservationSearch.hb';"
          >
        </div>
        <div class="col-md-2 text-right">
          <button type="button" class="btn btn-link" 
                  onclick="location.href='<%=ctxPath%>/reservation/reservationSearch.hb';">
            <i class="fas fa-search fa-2x mx-4"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
  
  <!-- 광고 배너 위치 -->
	<div class="container">

		<div id="carouselExampleIndicators" class="carousel slide mx-auto" style="width: 900px;" data-ride="carousel">
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
	 
	</div>
	   <!-- 객실 사진 위치 -->
  <div class="container mt-4 mb-5" style="margin: 0 auto;">
    <div class="row text-center">
      <div class="col-md-4 mb-3">
        <div class="room-placeholder">객실 정보</div>
      </div>
      <div class="col-md-4 mb-3">
        <div class="room-placeholder">객실 정보</div>
      </div>
      <div class="col-md-4 mb-3">
        <div class="room-placeholder">객실 정보</div>
      </div>
    </div>
  </div>




<jsp:include page="footer1.jsp" />