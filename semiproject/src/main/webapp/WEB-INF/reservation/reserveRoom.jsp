<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String ctxPath = request.getContextPath();
%>
<jsp:include page="/WEB-INF/header1.jsp" />
<style>
  /* roomCarousel 의 모든 이미지에 동일한 크기와 object-fit 적용 */
  #roomCarousel .carousel-item img {
    width: 100%;
    height: 400px;       /* 원하는 높이로 설정 */
    object-fit: cover;   /* 이미지 비율을 유지하면서 중앙에서 잘라서 채움 */
  }
</style>

<div class="container my-5">
  <!-- 1. 이미지 캐러셀 -->
  <div id="roomCarousel" class="carousel slide mb-4" data-ride="carousel">
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="<%=ctxPath%>/images/${room.room_thumbnail}"
             class="d-block w-100 img-modal" alt="메인 이미지">
      </div>
      <c:forEach var="img" items="${extraImgs}">
        <div class="carousel-item">
          <img src="<%=ctxPath%>/images/${img.room_extraimg_filename}"
               class="d-block w-100 img-modal" alt="추가 이미지">
        </div>
      </c:forEach>
    </div>
    <a class="carousel-control-prev" href="#roomCarousel" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon"></span>
    </a>
    <a class="carousel-control-next" href="#roomCarousel" role="button" data-slide="next">
      <span class="carousel-control-next-icon"></span>
    </a>
  </div>

  <!-- 2. 숙소명·객실 등급 -->
  <h2>${stay.stay_name}</h2>
  <h4 class="text-secondary">${room.room_grade}</h4>
  <small>${room.room_info}</small>
  <hr/>

  <!-- 3. 날짜·요금 -->
  <p>체크인: <strong>${checkin}</strong>　체크아웃: <strong>${checkout}</strong></p>
  <p class="text-right">
    총 <strong>${nights}</strong>박　
    상품 금액: <strong><fmt:formatNumber value="${productAmount}" pattern="#,##0"/>원</strong>
  </p>
  <hr/>

  <!-- 4. 예약자 정보 -->
  <h5>예약자 정보</h5>
  <p>이름: ${sessionScope.loginUser.user_name}</p>
  <p>연락처: ${sessionScope.loginUser.mobile}</p>
  <hr/>

  <!-- 5. 포인트 사용 -->
  <h5>포인트 사용</h5>
  <p>보유 포인트: <strong id="balancePoint">${sessionScope.loginUser.point}</strong>원</p>
  <div class="form-inline mb-3">
    <input type="number" id="usePointInput" class="form-control mr-2" min="0"
           placeholder="사용할 포인트">
    <button class="btn btn-outline-primary" id="applyPointBtn">적용</button>
  </div>
  <hr/>

  <!-- 6. 결제하기 -->
  <h5>결제하기</h5>
  <p>상품 금액: <fmt:formatNumber value="${productAmount}" pattern="#,##0"/>원</p>
  <p>포인트 사용액: <strong id="usedPointDisplay">0</strong>원</p>
  <p class="font-weight-bold">
    최종 결제금액: <span id="finalPayDisplay"><fmt:formatNumber value="${productAmount}" pattern="#,##0"/></span>원
  </p>
  <button class="btn btn-primary btn-lg btn-block" id="payBtn">결제하기</button>
</div>

<jsp:include page="/WEB-INF/footer1.jsp" />

 <!-- Image Preview Modal -->
  <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
      <div class="modal-content bg-transparent border-0 p-0">
        <button type="button" class="close position-absolute text-white" 
                style="right: 1rem; top: 1rem; z-index:1050;" 
                data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <img src="#" id="modalImage" class="img-fluid w-100"
             alt="확대 이미지" style="max-height:90vh; object-fit:contain;" />
      </div>
    </div>
  </div>


<!-- Iamport JS -->
<script src="https://cdn.iamport.kr/js/iamport.payment-1.1.8.js"></script>
<script>
$(function(){
  // 캐러셀 이미지 클릭 시 모달 오픈
  $('.img-modal').on('click', function(){
    const src = $(this).attr('src');
    $('#modalImage').attr('src', src);
    $('#imageModal').modal('show');
  });
});
</script>
<script>
	const productAmount = ${productAmount};

	function updateFinal(usedPoint){
	  const finalPay = productAmount - usedPoint;
	  $('#finalPayDisplay').text(finalPay.toLocaleString());
	}
	
	$('#applyPointBtn').click(function(e){
	  e.preventDefault();
	  // 1) 입력값(쉼표 없는 순수 숫자)
	  let usePoint = Number($('#usePointInput').val()) || 0;
	  // 2) 보유포인트 검사 (balancePoint도 쉼표 제거)
	  const balancePoint = Number($('#balancePoint').text().replace(/,/g, ''));
	  if (usePoint > balancePoint) {
	    alert('보유 포인트가 부족합니다');
	    return;
	  }
	  // 3) 화면에 표시
	  $('#usedPointDisplay').text(usePoint.toLocaleString());
	  // 4) 바로 계산
	  updateFinal(usePoint);
	});

  $('#payBtn').click(function(){
    IMP.init('imp87127207');  // PortOne(Iamport) 가맹점 식별코드
    const usedPoint = Number($('#usedPointDisplay').text().replace(/,/g,''));
    const finalPay  = productAmount - usedPoint;

    IMP.request_pay({
      pg:      'html5_inicis',
      pay_method: 'card',
      merchant_uid: 'resv_' + new Date().getTime(),
      name:       '${stay.stay_name} ${room.room_grade}',
      amount:     100,  // 실제 결제 금액은 무조건100원으로 설정
      buyer_name: '${sessionScope.loginUser.user_name}',
      buyer_email: '${sessionScope.loginUser.email}',
      buyer_tel:  '${sessionScope.loginUser.mobile}'
    }, function(rsp) {
    	console.log('▶ IMP callback:', rsp);
      if (rsp.success) {
    	  const usedPoint = Number($('#usedPointDisplay').text().replace(/,/g,''));
    	    const finalPay  = productAmount - usedPoint;
    	    console.log('▶ About to send AJAX:', {
    	      usedPoint, finalPay, room_no: '${room.room_no}'
    	    });
        // 서버에 결제 완료 통보
        $.post('${pageContext.request.contextPath}/reservation/paymentComplete.hb', {
          room_no:    '${room.room_no}',
          checkin:    '${checkin}',
          checkout:   '${checkout}',
          paid_amount: finalPay,
          used_point:  usedPoint,
          finalPay:   finalPay,
          merchant_uid: rsp.merchant_uid,
          pay_method: rsp.pay_method,
          imp_uid:    rsp.imp_uid
        }, function(res) {
          if (res.status === 'success') {
            alert('결제 및 예약이 완료되었습니다.');
            location.href = '${pageContext.request.contextPath}/reservation/confirmation.hb?reserv_no=' + res.reserv_no;
          } else {
            alert('결제는 완료되었으나 서버 처리 중 오류가 발생했습니다.');
          }
        }, 'json');
      } else {
        alert('결제에 실패했습니다: ' + rsp.error_msg);
      }
    });
  });
</script>
