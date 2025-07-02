$(function(){
  const lenStay = 6;     // 한 번에 로드할 개수 (3열*2행)
  let start   = 1;
  let loading = false;
  let selectedCategory = new URLSearchParams(window.location.search).get('category') || ''; // 선택된 카테고리명 저장

  loadStays();

  $(window).on('scroll', function(){
    if(loading) return;
    if($(window).scrollTop() + $(window).height() 
       >= $(document).height() - 100) {
      loading = true;
	  //console.log(start, lenStay, selectedCategory);
      loadStays();
    }
  });

  //	좌측 사이드바 내의 카테고리 클릭시 발생하는 이벤트
  $(document).on('click', '.list-group-item', function(e)
  {
	window.scrollTo({ top: 0 });	//	해당 카테고리를 보여주는 페이지를 띄울 때 항상 스크롤이 맨 위로가게 설정
	e.preventDefault();
	
	loading = true;
	start=1;
	$('#stayContainer').empty(); // 기존 숙소 목록 초기화
	$('#endMessage').text('');
	
	const value = $(this).data('value');
	//	console.log(value);
	//	카테고리 값 설정
	if($(this).text().trim() === '전체')
	{	//	사이드바 목록에서 전체를 선택하였을 경우
		selectedCategory = '';
	}
	else
	{	//	전체를 제외한 나머지 특정 카테고리를 선택한 경우
		selectedCategory = value;
	}
	
	//	console.log(start, lenStay, selectedCategory);
	loadStays();
  });
  
  function loadStays(){
    $.ajax({
      url: ctxPath + '/stayDisplayJSON.hb',
      data: { start: start, len: lenStay, category: selectedCategory },
      dataType: 'json',
      success: function(data){
        if(!data || data.length === 0) {
			console.log(start, lenStay, selectedCategory);
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
                    <a href="${ctxPath}/stayDetail.hb?stay_no=${s.stay_no}"
                       class="btn btn-sm btn-primary">상세보기</a>
					<a href="${ctxPath}/stayDetail.hb?stay_no=${s.stay_no}"
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
		console.log(start, lenStay, selectedCategory);
        console.error('숙소 로드 실패:', xhr.responseText);
      },
      complete: function(){
        loading = false;
      }
    });
  }
});
