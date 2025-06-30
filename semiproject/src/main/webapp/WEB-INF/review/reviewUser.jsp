
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% String ctx_Path = request.getContextPath(); %>

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

<jsp:include page="../header1.jsp" />

<style>
	body { font-family: sans-serif; color: #333; }
	.container { width: 800px; margin: 40px auto; padding: 20px; }
	.header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #eee; padding-bottom: 10px; }
	.header h1 { font-size: 24px; }
	.points-reviews { border-top: 1px solid #eee; border-bottom: 1px solid #eee; margin-top: 30px; padding: 20px 0; }
	.points-reviews .item { display: flex; justify-content: space-between; padding: 10px 0; }
	.points-reviews .item a { display: flex; justify-content: space-between; width: 100%; color: inherit; }
	.points-reviews .item a:hover { color: #007bff; }
	.user-links a {margin-left: 15px;color: #555;text-decoration: none;font-size: 14px;}
	.user-links a:first-child {margin-left: 0;}
	#point { font-weight: bold;}
	.item > a > span:last-child { display: inline-block; transform: rotate(90deg); }
	.hidden-space { visibility: hidden; }
	
	<%-- 폰트어썸 아이콘과 번호 버튼 높이 맞춤 --%>
	.pagination .page-link
	{
		display: flex;
		align-items: center;
		justify-content: center;
		height: 38px;
		padding: 0 12px;
		font-size: 1rem;
	}
	
	.pagination .page-link i
	{
		font-size: 1rem;
		line-height: 1;
	}
</style>

<body>

	<div class="container">
	<%-- mypage.jsp 상단 포메이션을 따름 --%>
		<div class="header">
			<h1>마이페이지</h1>
			<div>
				<span>🔔</span>
				<span>🛒</span>
			</div>
		</div>	    
	   	<h3 class="user-name">${loginUser.user_name} 님</h3>
	   	
		<div class="user-links hidden-space">
			<a href="javascript:goEmailChange('${loginUser.user_id}','<%= ctx_Path%>')">이메일 변경</a>
				&nbsp;
			<a href="javascript:goPwdChange('${loginUser.user_id}','${loginUser.user_pwd}','<%= ctx_Path%>')">비밀번호 변경</a>
		</div>

		<div class="points-reviews">
            <div class="item hidden-space">
                <span>포인트</span>
                <span id="point">${loginUser.point}pt &nbsp;&nbsp;></span>
            </div>
            <div class="item">
            	<a href="${pageContext.request.contextPath}/myPage.hb?user_id=${loginUser.user_id}">
            		<span>내 후기</span>
                	<span>></span>	
				</a>
			</div>
		</div>
		
		<div>
			<!-- 5-2. 리뷰 목록 (각 리뷰에 들어가있는 컨텐츠 순서는 나중에 수정/보완) -->
		    <div class="mt-4">
		    	<h4>리뷰 목록</h4>
		    	<c:if test="${empty reviewList}">
		    		<p>아직 작성하신 리뷰가 없습니다.</p>
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
		    
		    <!-- 5-3 페이징 -->
		    <c:set var="currentPage" value="${currentShowPageNo}" />
			<c:set var="totalPage" value="${totalPage}" />
			
			<c:if test="${totalPage > 1}">	<!-- 페이징처리 할 만큼 데이터가 없을경우 페이지바 비활성화 -->
				<div class="mt-4 d-flex justify-content-center">
					<ul class="pagination">
						<!-- 처음(First) 버튼 -->
						<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
							<a class="page-link" href="reviewStay.hb?stay_no=${stay.stay_no}&room_grade=${selectedGrade}&page=1">
								<i class="fas fa-angle-double-left"></i>
							</a>
						</li>
			
						<!-- 이전(Previous) 버튼 -->
						<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
							<a class="page-link" href="reviewStay.hb?stay_no=${stay.stay_no}&room_grade=${selectedGrade}&page=${currentPage - 1}">
								<i class="fas fa-angle-left"></i>
							</a>
						</li>
			
						<!-- 페이지 번호 -->
						<c:forEach var="page" begin="1" end="${totalPage}">
							<li class="page-item ${page == currentPage ? 'active' : ''}">
								<a class="page-link" href="reviewStay.hb?stay_no=${stay.stay_no}&room_grade=${selectedGrade}&page=${page}">${page}</a>
							</li>
						</c:forEach>
			
						<!-- 다음(Next) 버튼 -->
						<li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
							<a class="page-link" href="reviewStay.hb?stay_no=${stay.stay_no}&room_grade=${selectedGrade}&page=${currentPage + 1}">
								<i class="fas fa-angle-right"></i>
							</a>
						</li>
			
						<!-- 마지막(Last) 버튼 -->
						<li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
							<a class="page-link" href="reviewStay.hb?stay_no=${stay.stay_no}&room_grade=${selectedGrade}&page=${totalPage}">
								<i class="fas fa-angle-double-right"></i>
							</a>
						</li>
					</ul>
				</div>
		    </c:if>
		</div>
		
    </div>

<jsp:include page="../footer1.jsp" />
