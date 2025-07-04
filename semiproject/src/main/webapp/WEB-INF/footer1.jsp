<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
%>
<style>
  .price-bubble {
    background: #fff; border:1px solid #666; border-radius:4px;
    padding:4px 8px; font-size:0.9em; cursor:pointer;
  }
  #stayCard {
    width:250px;
    box-shadow:0 2px 8px rgba(0,0,0,0.2);
    display:none;
  }
  #stayCard img { width:100%; height:120px; object-fit:cover; }
  
  #viewMapBtn {
    position: fixed;                /* 화면 기준 고정 */
    bottom: 60px;                   /* 스크롤 컨트롤 바로 위 */
    left: 68%;                      /* 화면 가로 중앙 */
	top: 68%;
    cursor: pointer;
    width: 115px;
    height: 95px;
    z-index: 1000;                  /* 다른 요소 위에 노출 */
</style>
<!-- CSS 연결 -->
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/footer/scroll_control.css" />
<!--  지도보기 버튼 -->

      <img id="viewMapBtn"
       src="<%=ctxPath%>/images/지도보기 버튼.png"
       alt="지도에서 보기"
       data-toggle="modal"
       data-target="#mapModal"
       style="cursor:pointer; width:80px; height:75px; margin-left:25%;" />



  <!-- 말풍선 클릭 시 나타날 카드 -->

 <div id="stayCardTemplate" style="display:none;">
  <div class="card" style="width:250px;">
    <img class="card-img-top" alt="썸네일" style="height:120px; object-fit:cover;" />
    <div class="card-body p-2">
      <h5 class="card-title mb-1"></h5>
      <p class="card-text mb-1 text-danger font-weight-bold"></p>
      <a class="btn btn-sm btn-block btn-primary" href="#" >상세보기</a>
    </div>
  </div>
</div>
<!-- scroll control buttons -->
<div id="scroll-control">
	<button class="scroll-btn" id="btn-up" title="맨 위로">↑</button>
	<div style="
		height: 4px;               /* 선 두께: 4px */
		background-color: rgba(13, 110, 253, 0.15); /* primary 색상 + 투명도 15% */
		width: 100%;
		margin: 0;
		padding: 0;">
	</div>
	<button class="scroll-btn" id="btn-down" title="맨 아래로">↓</button>
</div>

  <footer class="text-center py-3">
    <p>&copy; NOLJA Company 2025 &nbsp; 대표전화: 1577-1588</p>
    <p>(주)NOLJA</p>
  </footer>

<!-- JS 연결 -->
<script src="<%=ctxPath%>/js/scroll_control.js"></script>
<script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=43e1bc9de3bc707bc039e3d3bbf88100&libraries=services"></script>
<script>
$(function(){
	
	  var map, cardOverlay;

	    // 모달이 열리면 한 번만 지도 초기화
	    $('#mapModal').on('shown.bs.modal', function(){
	      if (map) return;
	      kakao.maps.load(function(){
	        var center = new kakao.maps.LatLng(37.5665,126.9780);
	        map = new kakao.maps.Map(
	          document.getElementById('mapContainer'),
	          { center:center, level:6 }
	        );
	        kakao.maps.event.trigger(map,'resize');
	        map.setCenter(center);


	        // 서버에서 위치+가격+썸네일 받아오기
	        $.getJSON('<%=ctxPath%>/reservation/mapData.hb')
	         .done(function(data){
	           data.forEach(function(stay){
	             var pos = new kakao.maps.LatLng(stay.latitude, stay.longitude);
	             // 말풍선에 stay 객체를 data-stay 속성으로 JSON.stringify
	             new kakao.maps.CustomOverlay({
	               map: map,
	               position: pos,
	               content:
	                 '<div class="price-bubble" data-stay=\'' 
	                   + JSON.stringify(stay)
	                 + '\'>₩'+ stay.minPrice.toLocaleString() +'</div>',
	               yAnchor:1
	             });
	           });
	         })
	         .fail(function(){
	           alert('지도 데이터를 불러올 수 없습니다.');
	         });
	      });
	    });

	    // 말풍선(.price-bubble) 클릭 시 카드 오버레이 생성
	    $(document).on('click', '.price-bubble', function(){
	      var stay = JSON.parse(this.getAttribute('data-stay'));

	      // 맵 컨테이너의 카드를 제외한 공간 클릭시 카드 제거
	      $('#mapContainer').on('click', function(e){
	    	    if (!$(e.target).closest('.price-bubble, .card').length) {
	    	      if (cardOverlay) cardOverlay.setMap(null);
	    	    }
	    	  });
	      
	      // 이미 존재하는 카드 제거
	      if (cardOverlay) cardOverlay.setMap(null);

	      // 템플릿 복제
	      var $tpl = $('#stayCardTemplate .card').clone();
	      $tpl.find('img').attr('src','<%=ctxPath%>/images/'+ stay.stay_thumbnail);
	      $tpl.find('.card-title').text(stay.stay_name);
	      $tpl.find('.card-text').text('₩'+ stay.minPrice.toLocaleString() +' /박');
	      $tpl.find('a').attr('href','<%=ctxPath%>/stayDetail.hb?stay_no='+ stay.stay_no);
	      

	      // 카드 오버레이
	      cardOverlay = new kakao.maps.CustomOverlay({
	        map: map,
	        position: new kakao.maps.LatLng(stay.latitude, stay.longitude),
	        content: $tpl.get(0),
	        yAnchor: 1.2,
	        xAnchor: 0.5
	      });
	    });

	    // 모달이 닫힐 때 카드 정리
	    $('#mapModal').on('hidden.bs.modal', function(){
	      if (cardOverlay) cardOverlay.setMap(null);
	    });

	});


</script>


<!-- mapModal -->
<div class="modal fade" id="mapModal" tabindex="-1" aria-labelledby="mapModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl modal-dialog-centered"> 
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="mapModalLabel">숙소 지도 보기</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="닫기">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body p-0">
        <!-- 실제 지도가 이 DIV 에 그려집니다 -->
        <div id="mapContainer" style="width:100%; height:600px;"></div>
      </div>
    </div>
  </div>
</div>
</body>
</html>