$(function(){
  // 한 번에 로드할 개수, 시작 인덱스, 로딩 상태
  const lenStay = 6;
  let startIndex = 1;
  let loading    = false;

/*  // dateRangePicker 초기화 (검색폼 아래)
  $('#dateRangePicker').daterangepicker({
    locale: {
      format:    'YYYY-MM-DD',
      separator: ' ~ ',
      applyLabel:'적용',
      cancelLabel:'취소'
    },
    opens: 'center'
  }, function(startDate, endDate){
    // 선택 일수 표시 (선택 직후)
    const days = endDate.diff(startDate, 'days') + 1;
    $('#dateCount').remove();
    $('#dateRangePicker')
      .after(`<small id="dateCount" class="text-muted ml-2">총 ${days}박</small>`);
  });
*/
  // 최초 로드
  loadStays();

  // 스크롤 이벤트
  $(window).on('scroll', function(){
    if (loading) return;
    if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
      loading = true;
      loadStays();
    }
  });

  // AJAX로 데이터 불러와서 카드 추가
  function loadStays(){
    $.ajax({
      url:      ctxPath + '/reservation/searchScroll.hb',
      data: {
        keyword:  keyword,
        checkin:  checkin,
        checkout: checkout,
        start:    startIndex,
        len:      lenStay
      },
      dataType: 'json',
      success: function(list){
        if (!list || list.length === 0) {
          $('#endMessage').text('더 이상 결과가 없습니다.');
        } else {
          let cards = '';
          list.forEach(function(s){
            cards += `
              <div class="col-md-6 mb-4">
			  <div class="card h-100 position-relative">
			                    <img src="${ctxPath}/images/${s.stay_thumbnail}"
			                         class="card-img-top" alt="${s.stay_name}">
			                    <div class="card-body">
			                      <h5 class="card-title">${s.stay_name}</h5>
			                      <p class="card-text">
			                        평점: ${s.stay_score.toFixed(1)}  
			                        · 조회수: ${s.views}
			                      </p>
			                      <a href="${ctxPath}/stayDetail.hb?stay_no=${s.stay_no}&checkin=${encodeURIComponent(checkin)}&checkout=${encodeURIComponent(checkout)}"
			                         class="btn btn-sm btn-primary">상세보기</a>
			  					<a href="${ctxPath}/stayDetail.hb?stay_no=${s.stay_no}&checkin=${encodeURIComponent(checkin)}&checkout=${encodeURIComponent(checkout)}"
			  					   class="stretched-link"></a>
			                    </div>
			                  </div>
              </div>`;
          });
          $('#stayContainer').append(cards);
          startIndex += lenStay;
        }
      },
      error: function(xhr){
        console.error('검색 결과 로드 실패:', xhr.responseText);
      },
      complete: function(){
        loading = false;
      }
    });
  }
});
