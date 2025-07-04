<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
    String ctxPath = request.getContextPath();
%>

<jsp:include page="/WEB-INF/header1.jsp" />


<!-- daterangepicker CSS & JS -->
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.4/moment.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/daterangepicker@3.1/daterangepicker.min.js"></script>

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
</style>


<div class="container content-center">
  <!-- 1) 검색 폼 -->
  <div class="row mt-5 mb-4">
    <div class="col-md-10 offset-md-3">
      <form id="searchForm"
            action="<%=ctxPath%>/reservation/searchResult.hb"
            method="get"
            class="d-flex justify-content-center align-items-center">
        
        <!-- 입력부: 세로 스택 -->
         <div class="d-flex flex-column mr-4"  style="width: 700px;">
          <div class="mb-3" >
            <input type="text"
                   id="keyword"
                   name="keyword"
                   class="form-control"
                   placeholder="숙소명 또는 지역명"
                   style="width: 100%;">
          </div>

          <div class="mb-3">
            <input type="text"
                   id="dateRangePicker"
                   name="period"
                   class="form-control"
                   placeholder="기간 선택"
                   readonly
                   style="width: 100%;" >
                   <strong id="dateCount" class="form-text text-muted mt-1"></strong>
          </div>
        </div>
        
        <!-- 버튼: 입력부 오른쪽, 수직 중앙 정렬 -->
        <button type="submit" class="btn btn-primary align-self-center" style="height: 90px; margin-bottom: 20px">검색</button>
      </form>
    </div>
  </div>
<!--  지도보기 버튼 -->
 <div class="row justify-content-center my-4">
      <img id="viewMapBtn"
       src="<%=ctxPath%>/images/지도보기 버튼.png"
       alt="지도에서 보기"
       data-toggle="modal"
       data-target="#mapModal"
       style="cursor:pointer; width:115px; height:70px; margin-left:25%;" />
  </div>


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

<!-- 2) 추천 검색어 -->
		<div class="row justify-content-center align-items-start flex-nowrap" style="background-color: #F5F5F5; width: 600px; padding: 15px; padding-right: 100px; margin-left: 250px;">
		  <!-- 좌측 1~5 -->
		  <div class="col-auto text-right" style="margin-right: 128px;">
		    	<h5 class="mb-3" style="height: 16px; margin-left: 100px;">추천검색어</h5>
		    <ol class="list-unstyled mb-1">
		      <c:forEach var="kw" items="${requestScope.detailKeywords}" varStatus="st">
		        <c:if test="${st.index < 5}">
		          <li style="line-height:1.8;">
		            <span class="text-primary">${st.index + 1}</span> – 
		            <a href="javascript:void(0)"
		               onclick="onRecSearch('${kw}')">
		              ${kw}
		            </a>
		          </li>
		        </c:if>
		      </c:forEach>
		    </ol>
		  </div>
		  <!-- 우측 6~10 -->
		  <div class="col-auto text-left" style="margin-left: 128px;">
		  <div class="mb-3" style="visibility:hidden; height:16px;">x</div>
		    <ol class="list-unstyled mb-1" start="6">
		      <c:forEach var="kw" items="${requestScope.detailKeywords}" varStatus="st">
		        <c:if test="${st.index >= 5}">
		          <li style="line-height:1.8;">
		            <span class="text-primary">${st.index + 1}</span> – 
		            <a href="javascript:void(0)"
		               onclick="onRecSearch('${kw}')">
		              ${kw}
		            </a>
		          </li>
		        </c:if>
		      </c:forEach>
		    </ol>
		  </div>
		</div>
		
</div>

  


<script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=43e1bc9de3bc707bc039e3d3bbf88100&libraries=services"></script>
<script>
$(function(){
  // 1) daterangepicker 초기화
  $('#dateRangePicker').daterangepicker({
    locale: { format:'YYYY-MM-DD', separator:' ~ ', applyLabel:'적용', cancelLabel:'취소' },
    opens:'center', minDate: moment().startOf('day')
  }, function(start,end){
    $('#dateCount').text('총 ' + (end.diff(start,'days')+1) + '박');
  });

  // 2) 지도 & 마커 로직
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


//추천 검색어/키워드 클릭 핸들러
function onRecSearch(keyword) {
  var period = $('#dateRangePicker').val() || '';
  var url = '<%=ctxPath%>/reservation/searchResult.hb?keyword=' 
            + encodeURIComponent(keyword);
  if (period) {
    url += '&period=' + encodeURIComponent(period);
  }
  location.href = url;
}


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
<jsp:include page="/WEB-INF/footer1.jsp" />