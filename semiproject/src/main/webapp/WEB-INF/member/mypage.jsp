
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- JSTL(JSP Standard Tag Library)을 사용하기 위한 선언. 목록(예약내역) 표시에 유용합니다. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctx_Path = request.getContextPath(); %>

<jsp:include page="../header1.jsp" />
<script type="text/javascript">
    const ctxPath = '<%= ctx_Path %>';
    const userId = '${loginUser.user_id}';
    let offset = 6;
    const limit = 6;
    
    document.getElementById("goReservationPageBtn")?.addEventListener("click", function() {
        location.href = ctxPath + "/reservationList.hb?user_id=" + userId;
    });	
</script>
<script type="text/javascript" src="<%= ctx_Path%>/js/member/member.js"></script>

    <style>
        
        body { font-family: sans-serif; color: #333; }
        .container { width: 800px; margin: 40px auto; padding: 20px; }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; padding-bottom: 10px; }
        .header h1 { font-size: 24px; }
        .user-info { margin-top: 30px; }
        .user-info a { color: #555; text-decoration: none; margin-left: 15px; font-size: 14px; }
        .points-reviews { border-top: 1px solid #eee; border-bottom: 1px solid #eee; margin-top: 30px; padding: 20px 0; }
        .points-reviews .item { display: flex; justify-content: space-between; padding: 10px 0; }
        .points-reviews .item a { display: flex; justify-content: space-between; width: 100%; color: inherit; }
        .points-reviews .item a:hover { color: #007bff; }
		.user-links a {margin-left: 15px;color: #555;text-decoration: none;font-size: 14px;}
        .user-links a:first-child {margin-left: 0;}
        #point { font-weight: bold;}
       	.wishlist-section {margin-top: 40px;}
		.wishlist-grid {display: grid;grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));gap: 20px;margin-top: 20px;}
		.wishlist-card {border: 1px solid #ddd;border-radius: 10px;overflow: hidden;background: #fff;box-shadow: 0 2px 5px rgba(0,0,0,0.05);transition: transform 0.2s;}
		.wishlist-card:hover {transform: translateY(-5px);}
		.wishlist-img {width: 100%;height: 140px;object-fit: cover;}
		.wishlist-info {padding: 10px;}
		.wishlist-title {font-size: 16px;font-weight: bold;margin: 0 0 5px;}
		.wishlist-price {color: #e91e63;font-weight: bold;margin: 0 0 5px;}
		.wishlist-date {font-size: 12px;color: #777;}
		#loadMoreBtn { display: block; width: 150px; margin: 30px auto 0; padding: 12px 0; font-size: 16px; font-weight: 600; color: white; background-color: #007BFF; border: none; border-radius: 6px; cursor: pointer; box-shadow: 0 3px 6px rgba(0, 123, 255, 0.5); transition: background-color 0.3s ease; } 
		#loadMoreBtn:hover { background-color: #0056b3; } 
		#loadMoreBtn:disabled { background-color: #cccccc; cursor: not-allowed; box-shadow: none; } 
		.better-card { border: 1px solid #ddd; padding: 20px; border-radius: 12px; background: #fafafa; margin-top: 20px; box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05); } 
		.stay-name { font-size: 18px; font-weight: 700; margin-bottom: 12px; color: #333; } 
		.dates { display: flex; align-items: center; gap: 10px; font-size: 14px; color: #555; } 
		.label { font-weight: 500; margin-right: 6px; color: #777; } 
		.dot { font-size: 20px; color: #bbb; margin: 0 10px; } 
		.wishlist-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(180px, 1fr)); gap: 20px; margin-top: 20px; }
		.reservation-history-header {display: flex; justify-content: space-between; align-items: center;}
		.more-link {font-size: 15px;text-decoration: none;color: #777;font-weight: bold;transition: color 0.2s ease;}
		.more-link:hover {color: #000;}
    </style>

<body>


    <div class="container">
        <div class="header">
            <h1>마이페이지</h1>
            <div>
                <span>🔔</span>
                <span>🛒</span>
            </div>
        </div>

        <%-- 
            서블릿에서 다음과 같이 사용자 정보를 request 객체에 담아 전달했다고 가정합니다.
            UserDTO user = new UserDTO("홍길동", 0);
            request.setAttribute("user", user);
        --%>
	    
	   	<h3 class="user-name">${loginUser.user_name} 님</h3>
	   
	
		
		<div class="user-links">
		    <a href="javascript:goEmailChange('${loginUser.user_id}','<%= ctx_Path%>')">이메일 변경</a>
		    &nbsp;
		    <a href="javascript:goPwdChange('${loginUser.user_id}','${loginUser.user_pwd}','<%= ctx_Path%>')">비밀번호 변경</a>
		</div>

        <div class="points-reviews">
            <div class="item">
                <span>포인트</span>
                <span id="point">${loginUser.point}pt &nbsp;&nbsp;></span>
            </div>
            <div class="item">
            	<a href="${pageContext.request.contextPath}/reviewUser.hb?user_id=${loginUser.user_id}">
            		<span>내 후기</span>
                	<span>></span>	
            	</a>
            </div>
        </div>


<div class="reservation-history">
		    <div class="reservation-history-header">
			    <h3>예약내역</h3>
			    <a href="${pageContext.request.contextPath}/reservationList.hb?user_id=${loginUser.user_id}" class="more-link">></a>
			</div> 
		    <div class="reservation-list">
		        <c:choose>
		            <c:when test="${not empty nextReservation}">
					    <div class="reservation-item better-card">
					        <h3 class="stay-name">${nextReservation.stayvo.stay_name}</h3>
					        <h6 class="stay-name" >${nextReservation.roomvo.room_grade}</h6>
					        <div class="dates">
					            <div>
					                <span class="label">체크인</span>
					                <span class="value">${nextReservation.checkin_date}</span>
					            </div>
					            <div class="dot">•</div>
					            <div>
					                <span class="label">체크아웃</span>
					                <span class="value">${nextReservation.checkout_date}</span>
					            </div>
					        </div>
					    </div>
					</c:when>
		            <c:otherwise>
		                <p class="better-card	">예약 내역이 없습니다.</p>
		            </c:otherwise>
		        </c:choose>
		    </div>
		</div>

		<div class="wishlist-section">
		    <h3>찜한 숙소</h3>
		    <div id="wishlistGrid" class="wishlist-grid">
		        <c:choose>
		            <c:when test="${not empty wishList}">
		                <c:forEach var="wish" items="${wishList}">
		                    <a href="${pageContext.request.contextPath}/stayDetail.hb?stay_no=${wish.w_stay_no}" class="wishlist-link">
		                        <div class="wishlist-card">
		                            <img src="${pageContext.request.contextPath}/images/${wish.stayVO.stay_thumbnail}" 
		                                 alt="${wish.stayVO.stay_name}" class="wishlist-img">
		                            <div class="wishlist-info">
		                                <h4 class="wishlist-title">${wish.stayVO.stay_name}</h4>
		                            </div>
		                        </div>
		                    </a>
		                </c:forEach>
		            </c:when>
		            <c:otherwise>
		                <p>찜한 숙소가 없습니다.</p>
		            </c:otherwise>
		        </c:choose>
		    </div>
		    <button id="loadMoreBtn">찜 더보기</button>
		</div>
		


    </div>
     

<jsp:include page="../footer1.jsp" />
