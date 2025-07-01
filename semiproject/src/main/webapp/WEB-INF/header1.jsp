<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="myshop.model.StayDAO_imple, myshop.model.StayDAO" %>
<%@ page import="myshop.domain.CategoryVO" %>
<%@ page import="java.util.List" %>

<%
    String ctxPath = request.getContextPath();

	if (request.getAttribute("categoryList") == null)
	{
		StayDAO sdao = new StayDAO_imple();
    	try
    	{
			List<CategoryVO> categoryList = sdao.getCategoryList();
			request.setAttribute("categoryList", categoryList);
		}
    	catch (Exception e)
    	{
			e.printStackTrace();  // 에러 로그 출력
		}
}
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>::: 예약 메인:::</title>
  
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="<%=ctxPath%>/bootstrap-4.6.2-dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">
  <!-- 커스텀 CSS -->
  <link rel="stylesheet" href="<%=ctxPath%>/css/template/template.css">
  <link rel="stylesheet" href="<%=ctxPath%>/css/index/index.css">
  
  <!-- Optional JavaScript -->
  <script src="<%=ctxPath%>/js/jquery-3.7.1.min.js"></script>
  <script src="<%=ctxPath%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>
  
  <%-- jQueryUI CSS 및 JS --%>
  <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
  <script src="<%=ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script>
  
    <!-- 사이드바 전용 스타일 -->
  <style>
    /* 사이드 네비게이션 감추기/보이기 */
    #sidebar-wrapper {
      position: fixed;
      top: 0;
      left: -250px;
      width: 250px;
      height: 100vh;
      background: #f8f9fa;
      border-right: 1px solid #dee2e6;
      z-index: 1040;
      transition: left 0.3s ease;
    }
    #sidebar-wrapper.active {
      left: 0;
    }
    /* 본문 밀림 효과 (선택) */
    body.sidebar-open {
      padding-left: 250px;
      transition: padding-left 0.3s ease;
    }
  </style>
   <!-- 토글 스크립트 -->
<script>
  $(function(){
    // 기존 사이드바 열기/닫기
    $("#sidebarToggle").on("click", function(){
      $("#sidebar-wrapper").toggleClass("active");
      $("body").toggleClass("sidebar-open");
    });

    // 닫기 버튼 클릭 시
    $("#sidebarClose").on("click", function(){
      $("#sidebar-wrapper").removeClass("active");
      $("body").removeClass("sidebar-open");
    });
  });
  
  
</script>

</head>
<body>
  
 
  <!-- 사이드 네비게이션 -->
<div id="sidebar-wrapper">
  <!-- 헤더에 닫기 버튼 추가 -->
  <div class="sidebar-heading d-flex justify-content-between align-items-center p-3">
    <span>카테고리</span>
    <button id="sidebarClose" class="btn btn-sm btn-light">
      <i class="fas fa-times"></i>
    </button>
  </div>
  <div class="list-group list-group-flush">
    <a href="<%=ctxPath%>/index.hb" class="list-group-item list-group-item-action">전체</a>
  	<c:forEach var="cvo" items="${requestScope.categoryList}">
  		<a	href="<%=ctxPath%>/index.hb?category=${cvo.stay_category_no}"
   			class="list-group-item list-group-item-action"
   			data-value="${cvo.stay_category_no}">
   			${cvo.stay_category_name}
		</a>
  	</c:forEach>
  </div>
</div>

  <!-- 1) 상단 네비게이션 -->
  <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top px-4 py-2">
    <!-- 사이드바 토글 버튼 -->
    <button id="sidebarToggle" class="btn btn-outline-secondary mr-3">
      <i class="fas fa-bars"></i>
    </button>

    <a class="navbar-brand" href="<%=ctxPath%>/index.hb">
      <img src="<%=ctxPath%>/images/logo2.png" alt="예약 로고" height="40">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarMain">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarMain">
      <ul class="navbar-nav ml-auto align-items-center">
        <!-- 로그인 전 -->
        <c:if test="${empty sessionScope.loginUser}">
          <li class="nav-item">
            <a class="nav-link" href="<%=ctxPath%>/member/memberRegister.hb">회원가입</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=ctxPath%>/login/login.hb">로그인</a>
          </li>
        </c:if>
        <!-- 로그인 후 -->
        <c:if test="${not empty sessionScope.loginUser}">
        
        <!-- 일반회원일때 -->
          <c:if test="${sessionScope.loginUser.access_level == 0}">
          <li class="nav-item">
            <a class="nav-link" href="<%=ctxPath%>/myPage.hb?user_id=${loginUser.user_id}">마이페이지</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=ctxPath%>/login/logout.hb">로그아웃</a>
          </li>
          </c:if>
         <!-- 관리자일때(access_level == 1) -->
          <c:if test="${sessionScope.loginUser.access_level == 1}">
          	<li class="nav-item">
	     		 <a class="nav-link" href="<%=ctxPath%>/admin/stayRegister.hb">숙소등록하기</a>
	 		 </li>
         	 <li class="nav-item">
	     		 <a class="nav-link" href="<%=ctxPath%>/admin/manage.hb">관리자페이지</a>
	 		 </li>
	   		 <li class="nav-item">
	   		   <a class="nav-link" href="<%=ctxPath%>/login/logout.hb">로그아웃</a>
	   		 </li>
          </c:if>
       </c:if>
      </ul>
    </div>
  </nav>
  <hr style="margin-top:70px;">