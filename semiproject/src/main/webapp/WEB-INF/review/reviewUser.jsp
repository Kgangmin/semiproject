
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<% String ctx_Path = request.getContextPath(); %>

<c:set var="score" value="${review.reserv_score}" />
<script type="text/javascript">
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

$(function()
{
	//	수정 버튼 클릭 시
	$('.btn-edit').click(function()
	{
		const reviewItem = $(this).closest('div.mb-4'); // 리뷰 전체 컨테이너
		reviewItem.find('.review-content').hide(); // 기존 <p> 숨김
		reviewItem.find('.review-edit-form').removeClass('d-none'); // 수정 폼 보이기
	});
	
	//	삭제 버튼 클릭 시
	$('.btn-delete').click(function()
	{
		const reviewNo		= $(this).data('review-no');	// 리뷰 번호

		if (!confirm('정말 이 리뷰를 삭제하시겠습니까?'))
		{
			return;
		}

		$.ajax
		({
			url: '<%= ctx_Path %>/reviewDelete.hb',
			type: 'POST',
			data: { review_no: reviewNo },
			dataType: 'json',
			success: function(json)
			{
				if (json.n == 1)
				{
					// 현재 페이지 번호 가져오기
					const urlParams = new URLSearchParams(window.location.search);
					let currentPage = parseInt(urlParams.get('page')) || 1;

					// 현재 리뷰 개수 파악 (삭제 직전이므로 삭제 후면 하나 줄어듦)
					let reviewCount = $('.mb-4.border-bottom.pb-3').length;

					// 삭제된 후 페이지가 비게 될 경우 한 페이지 앞으로
					if (reviewCount <= 1 && currentPage > 1)
					{
						currentPage -= 1;
					}

					// 해당 페이지로 이동
					location.href = '<%= ctx_Path%>/reviewUser.hb?user_id=${loginUser.user_id}&page=' + currentPage;
				}
				else
				{
					alert('리뷰 삭제에 실패했습니다.');
				}
			},
			error:function(request, status, error)
			{
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
    });
});
	
	//	저장 버튼 클릭 시
	$('.btn-save').click(function()
	{
		const reviewItem	= $(this).closest('div.mb-4');							// 이 리뷰 전체 div
		const reviewNo		= $(this).data('review-no');							// 버튼에 달린 리뷰 번호
		const newContent	= reviewItem.find('.review-edit-text').val().trim();	// 수정한 내용
		
		if (newContent.length === 0)
		{//	수정 버튼 클릭을 통해 나타난 testared 가 비어있을 경우
			alert('리뷰 내용을 입력하세요.');
			return;
        }
		
        $.ajax
        ({
			url:	'<%= ctx_Path%>/reviewUpdate.hb',
			type:	'POST',
			data:	{
						review_no: reviewNo,
						review_contents: newContent
					},
			dataType: 'json',
			success: function(json)
			{
				if (json.n == 1)
				{// 성공 시 UI 업데이트
					reviewItem.find('.review-content').text(newContent).data('original', newContent).show();
					reviewItem.find('.review-edit-form').addClass('d-none');
					alert('리뷰가 성공적으로 수정되었습니다.');
                }
				else
                {
					alert('리뷰 수정에 실패했습니다. 다시 시도하세요.');
				}
			},
			error:function(request, status, error)
			{
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	});
	
	//	취소 버튼 클릭 시
	$('.btn-cancel').click(function()
	{
		const reviewItem = $(this).closest('div.mb-4');
		const originalText = reviewItem.find('.review-content').data('original');
		reviewItem.find('.review-edit-text').val(originalText); // 텍스트 복원
		reviewItem.find('.review-edit-form').addClass('d-none'); // 수정 폼 숨김
		reviewItem.find('.review-content').show(); // 원래 <p> 보이기
	});
});
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
	
	<%-- 1페이지 또는 마지막페이지일 경우 버튼 비활성화 가시성 강조 --%>
	.pagination .page-item.disabled .page-link
	{
		pointer-events: none;
		background-color: #e9ecef;
		color: #6c757d !important;
		border-color: #dee2e6;
		cursor: not-allowed;
		opacity: 0.6;
	}
	
	.points-reviews .item a
	{
		color: inherit;
		font-size: 14px;
		display: flex;
		justify-content: space-between;
		width: 100%;
	}

	.arrow-icon
	{
		font-size: 14px;
		color: #555555;
		vertical-align: middle;
		margin-left: 5px;
		display: inline-block;
		line-height: 1;
	}

	.points-reviews .item a:hover .arrow-icon
	{
		color: #007bff;
	}
	
</style>

<body>

	<div class="container">
	<%-- mypage.jsp 상단 컨텐츠 위치를 그대로 사용하고자 컨텐츠를 그대로 가져오고 hidden 처리 --%>
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
                	<span><i class="fas fa-chevron-right arrow-icon"></i></span>	
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
					<div class="mb-4 border-bottom pb-3">
						<div class="d-flex justify-content-between align-items-center mb-2">
							<!-- 별점 -->
							<div class="text-primary" id="star-container-${review.review_no}"></div>
							<script>
								document.getElementById("star-container-${review.review_no}").innerHTML = renderStars(${review.review_score});
							</script>
							
							<!-- 수정 / 삭제 버튼 -->
							<div class="btn-group" role="group">
								<button type="button" 
										class="btn btn-sm btn-outline-secondary btn-edit" 
										data-review-no="${review.review_no}">
									<i class="fas fa-edit"></i> 수정
								</button>
								<button type="button" 
										class="btn btn-sm btn-outline-danger btn-delete" 
										data-review-no="${review.review_no}">
									<i class="fas fa-trash"></i> 삭제
								</button>
							</div>
						</div>
						<!-- 리뷰 내용 -->
						<p class="review-content" data-original="${review.review_contents}">
							${review.review_contents}
						</p>
						
						<!-- 숨겨진 textarea (수정용) -->
						<div class="review-edit-form d-none">
							<textarea class="form-control mb-2 review-edit-text" rows="3">${review.review_contents}</textarea>
							<div style="text-align: right;">
								<button class="btn btn-sm btn-primary btn-save" data-review-no="${review.review_no}">저장</button>
								<button class="btn btn-sm btn-secondary btn-cancel">취소</button>
							</div>
						</div>
		
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
		    <c:set var="currentPage" value="${currentPage}" />
			<c:set var="prevPage" value="${currentPage - 1 < 1 ? 1 : currentPage - 1}" />
			<c:set var="nextPage" value="${currentPage + 1 > totalPage ? totalPage : currentPage + 1}" />
			
			<c:if test="${totalPage > 1}">	<!-- 페이징처리 할 만큼 데이터가 없을경우 페이지바 비활성화 -->
				<div class="mt-4 d-flex justify-content-center">
					<ul class="pagination">
						<!-- 처음(First) 버튼 -->
						<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
							<a class="page-link" href="reviewUser.hb?user_id=${loginUser.user_id}&page=1">
								<i class="fas fa-angle-double-left"></i>
							</a>
						</li>
			
						<!-- 이전(Previous) 버튼 -->
						<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
							<a class="page-link" href="reviewUser.hb?user_id=${loginUser.user_id}&page=${prevPage}">
								<i class="fas fa-angle-left"></i>
							</a>
						</li>
			
						<!-- 페이지 번호 -->
						<c:forEach var="page" begin="1" end="${totalPage}">
							<li class="page-item ${page == currentPage ? 'active' : ''}">
								<a class="page-link" href="reviewUser.hb?user_id=${loginUser.user_id}&page=${page}">${page}</a>
							</li>
						</c:forEach>
			
						<!-- 다음(Next) 버튼 -->
						<li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
							<a class="page-link" href="reviewUser.hb?user_id=${loginUser.user_id}&page=${nextPage}">
								<i class="fas fa-angle-right"></i>
							</a>
						</li>
			
						<!-- 마지막(Last) 버튼 -->
						<li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
							<a class="page-link" href="reviewUser.hb?user_id=${loginUser.user_id}&page=${totalPage}">
								<i class="fas fa-angle-double-right"></i>
							</a>
						</li>
					</ul>
				</div>
		    </c:if>
		</div>
		
    </div>

<jsp:include page="../footer1.jsp" />
