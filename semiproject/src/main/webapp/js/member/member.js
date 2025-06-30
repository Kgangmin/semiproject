$(function(){

$('#loadMoreBtn').on('click', function () {
	
    const $btn = $(this);
    $.ajax({
        url: `${ctxPath}/loadMoreWishlist.hb`,
        type: 'GET',
        data: {
            
            offset: offset,
            limit: limit
        },
        dataType: 'json',
        success: function (data) {
            if (data.length === 0) {
                alert('더 이상 찜한 숙소가 없습니다.');
                $btn.hide();
                return;
            }

            let html = '';
            $.each(data, function (index, wish) {
                html += `
                    <a href="${ctxPath}/stayDetail.hb?stay_no=${wish.w_stay_no}" class="wishlist-link">
                        <div class="wishlist-card">
                            <img src="${ctxPath}/images/${wish.stayVO.stay_thumbnail}" alt="${wish.stayVO.stay_name}" class="wishlist-img">
                            <div class="wishlist-info">
                                <h4 class="wishlist-title">${wish.stayVO.stay_name}</h4>
                            </div>
                        </div>
                    </a>
                `;
            });

            $('#wishlistGrid').append(html);
            offset += data.length;
        },
        error: function () {
            alert('데이터를 불러오는데 실패했습니다.');
        }
    });
});//END $('#loadMoreBtn').on('click', function ()
	




});	//END $(function()

















// Function Declaration
// 이메일변경하기 창을 뛰우는 함수
function goEmailChange(user_id,ctx_Path){
	
	// 나의정보 수정하기 팝업창 띄우기
	   const url = `${ctx_Path}/changeEmail.hb?user_id=${user_id}`;
	//  또는
	//  const url = ctx_Path+"/member/memberEdit.up?userid="+userid;   
	   
	   // 너비 800, 높이 680 인 팝업창을 화면 가운데 위치시키기
	   const width = 600;
	   const height = 500;
	/*   
	   console.log("모니터의 넓이 : ",window.screen.width);
	   // 모니터의 넓이 :  1440
	   
	   console.log("모니터의 높이 : ",window.screen.height);
	   // 모니터의 높이 :  900
	   */   
       const left = Math.ceil((window.screen.width - width)/2);  // 정수로 만듬 
       const top = Math.ceil((window.screen.height - height)/2); // 정수로 만듬
      window.open(url, "goEmailChange", 
                  `left=${left}, top=${top}, width=${width}, height=${height}`);
	
}//function goEditMyInfo(userid, ctx_Path)

// 패스워드 변경 창뛰우기
function goPwdChange(user_id,user_pwd,ctx_Path){
	
	// 나의정보 수정하기 팝업창 띄우기
	   const url = `${ctx_Path}/changePwd.hb?user_id=${user_id}`;
	//  또는
	//  const url = ctx_Path+"/member/memberEdit.up?userid="+userid;   
	   
	   // 너비 800, 높이 680 인 팝업창을 화면 가운데 위치시키기
	   const width = 600;
	   const height = 500;
	/*   
	   console.log("모니터의 넓이 : ",window.screen.width);
	   // 모니터의 넓이 :  1440
	   
	   console.log("모니터의 높이 : ",window.screen.height);
	   // 모니터의 높이 :  900
	   */   
       const left = Math.ceil((window.screen.width - width)/2);  // 정수로 만듬 
       const top = Math.ceil((window.screen.height - height)/2); // 정수로 만듬
      window.open(url, "goPwdChange", 
                  `left=${left}, top=${top}, width=${width}, height=${height}`);
	
}//function goEditMyInfo(userid, ctx_Path)