window.onload = function() {
    const starContainer = document.getElementById('starRating');
    const stars = starContainer.querySelectorAll('span');
    const ratingInput = document.getElementById('rating');
    let selectedRating = 0;
    const gap = 5; // CSS에서 지정한 gap 값(px)

    // 별 채우기 함수
    function updateStars(rating) {
        stars.forEach((star, idx) => {
            star.classList.remove('full', 'half');
            const starNum = idx + 1;
            if (rating >= starNum) {
                star.classList.add('full');
            } else if (rating >= starNum - 0.5) {
                star.classList.add('half');
            }
        });
    }

    function getHoverRating(offsetX) {
        const starWidth = stars[0].offsetWidth; // 별 하나 너비
        const unit = starWidth + gap; // 별 + 간격 너비 단위

        let starIndex = Math.floor(offsetX / unit);
        if (starIndex >= stars.length) starIndex = stars.length - 1;
        if (starIndex < 0) starIndex = 0;

        let intraStarX = offsetX - unit * starIndex;

        let hoverRating = 0;
        if (intraStarX <= starWidth / 2) {
            hoverRating = starIndex + 0.5;
        } else {
            hoverRating = starIndex + 1;
        }
        if (hoverRating > stars.length) hoverRating = stars.length;

        return hoverRating;
    }

    starContainer.addEventListener('mousemove', e => {
        const rect = starContainer.getBoundingClientRect();
        const offsetX = e.clientX - rect.left;

        const hoverRating = getHoverRating(offsetX);
        updateStars(hoverRating);
    });

    starContainer.addEventListener('mouseleave', () => {
        updateStars(selectedRating);
    });

    starContainer.addEventListener('click', e => {
        const rect = starContainer.getBoundingClientRect();
        const offsetX = e.clientX - rect.left;

        const clickedRating = getHoverRating(offsetX);
        selectedRating = clickedRating;
        ratingInput.value = selectedRating;
        updateStars(selectedRating);
    });
	
	$('button#review_register').on('click',function(){
		
		if(selectedRating == 0){
			alert("리뷰를 등록할려면 별점을 먼저 체크 해주세요 ")
			return false;
		}
		
		const text = $('#content').val().trim();
		//alert(text.length);
		if(text != "") { 
   			
			if(text.length > 200){
				alert(" 후기는 200 글자 이내로만 입력 가능합니다  ")
				return false;
			}
			
			
			
		}
		else{
			alert(" 후기는 1글자 이상 입력해야 합니다 ")
			return false;	
		}

	});
	
	
	
	
};//end window.onload




