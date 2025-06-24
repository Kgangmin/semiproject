$(function(){
  const lenStay = 6;     // 한 번에 로드할 개수 (3열*2행)
  let start   = 1;
  let loading = false;

  loadStays();

  $(window).on('scroll', function(){
    if(loading) return;
    if($(window).scrollTop() + $(window).height() 
       >= $(document).height() - 100) {
      loading = true;
      loadStays();
    }
  });

  function loadStays(){
    $.ajax({
      url: ctxPath + '/stayDisplayJSON.hb',
      data: { start: start, len: lenStay },
      dataType: 'json',
      success: function(data){
        if(!data || data.length === 0) {
          $('#endMessage').text('더 이상 숙소가 없습니다.');
        } else {
          let cards = '';
          data.forEach(function(s){
            cards += `
              <div class="col-md-4 mb-4">
                <div class="card h-100 position-relative">
                  <img src="${ctxPath}/images/${s.stay_thumbnail}"
                       class="card-img-top" alt="${s.stay_name}">
                  <div class="card-body">
                    <h5 class="card-title">${s.stay_name}</h5>
                    <p class="card-text">
                      평점: ${s.stay_score.toFixed(1)}  
                      · 조회수: ${s.views}
                    </p>
                    <a href="${ctxPath}/stayDetail.up?stay_no=${s.stay_no}"
                       class="btn btn-sm btn-primary">상세보기</a>
					<a href="${ctxPath}/stayDetail.up?stay_no=${s.stay_no}"
					   class="stretched-link"></a>
                  </div>
                </div>
              </div>`;
          });
          $('#stayContainer').append(cards);
          start += lenStay;
        }
      },
      error: function(xhr){
        console.error(		'숙소 로드 실패:',
		   'status=' + jqXHR.status,
		   'statusText=' + jqXHR.statusText,
		   'textStatus=' + textStatus,
		   'errorThrown=' + errorThrown,
		   'responseText=' + jqXHR.responseText);
      },
      complete: function(){
        loading = false;
      }
    });
  }
});
