<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
    String ctxPath = request.getContextPath();
%>

<jsp:include page="header1.jsp" />

<script>
	window.addEventListener('load', function()
	{//	페이지가 강제로 로드됐을 때 한번 새로고침 시도
		if (!performance || performance.navigation.type !== 1)
		{
			location.reload(true);
		}
	});
</script>

<style>
.card-img-top {
  width: 100%;        /* 가로는 카드 너비에 딱 맞추고 */
  height: 200px;      /* 원하는 높이로 고정 */
  object-fit: cover;  /* 이미지 비율을 유지하며 잘려서 채워줌 */
}
</style>



   <!-- 날짜 지정 바 -->
  <div class="py-3" style="margin:10px auto;; width:800px; ">
    <div class="container-fluid">
      <div class="row align-items-center">
        <!-- 클릭 시 예약 페이지로 이동 -->
        <div class="col-md-10">
          <input
            type="text"
            id="datepickerRange"
            class="form-control" 
            placeholder="예약하기"
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
	
	
	   <!-- 객실 컨테이너 무한스크롤 -->
		<div class="container mt-4 mb-5">
		  <div id="stayContainer" class="row" style="margin-left: 10%;"></div>
		  <div id="endMessage"   class="text-center mt-3 text-muted"></div>
		</div>
	<script type="text/javascript">
	  var ctxPath = '<%= ctxPath %>';
	</script>
	
	<!-- stayScroll.js 로드 -->
	<script src="<%= ctxPath %>/js/stayScroll.js"></script>
	





<jsp:include page="/WEB-INF/footer1.jsp" />

<!-- 비밀번호 변경 모달 -->
<c:if test="${sessionScope.showPwdModal}">
  <% session.removeAttribute("showPwdModal"); %>

  <div class="modal fade" id="pwdChangeModal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">비밀번호 변경 안내</h5>
        </div>
        <div class="modal-body">
          <p>비밀번호를 마지막으로 변경한 지 3개월(90일) 이상 경과하였습니다.<br/>
             지금 바로 변경하시겠습니까?</p>
        </div>
        <div class="modal-footer">
          <!-- 즉시 변경 -->
          <button type="button" class="btn btn-primary" id="changeNowBtn">
            지금 변경하기
          </button>
          <!-- 90일 뒤 알림 -->
          <button type="button" class="btn btn-secondary" id="changeLaterBtn">
            90일 뒤에 변경하기
          </button>
        </div>
      </div>
    </div>
  </div>

  <script>
    $(document).ready(function(){
      $('#pwdChangeModal').modal('show');

      $('#changeNowBtn').click(function(){
        window.open(
          '<%=ctxPath%>/changePwd.hb?user_id=${sessionScope.loginUser.user_id}',
          '비밀번호변경',
          'width=500,height=600,resizable=no'
        );
        $('#pwdChangeModal').modal('hide');
      });

      $('#changeLaterBtn').click(function(){
        $.post('<%=ctxPath%>/login/remindPwdChange.hb', {}, function(){
          $('#pwdChangeModal').modal('hide');
          window.location.href = '<%=ctxPath%>/index.hb';
        });
      });
    });
  </script>
</c:if>