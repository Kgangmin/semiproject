$(function(){
	
	// ==== localStorage 에 저장된 아이디가 있다면 input#user_id 에 자동 입력하기 ==== //
	if (localStorage.getItem("savedUserId")) {
		$('input#user_id').val(localStorage.getItem("savedUserId"));
		$('input#saveId').prop("checked", true);
	}

	$('button#btnSubmit').click(()=>{
		goLogin();  // 로그인 시도(아이디저장 처리 포함)
	});
	
	$('input#user_pwd').bind("keyup", (e)=>{
		if(e.which == 13) { // 암호입력란에 엔터 입력 시 
			goLogin();  // 로그인 시도(아이디저장 처리 포함)
		}
	});
	
});// end of $(function(){})------------------------------------


// ==== 로그인 처리 함수(아이디저장은 LocalStorage 를 사용함) ==== //
function goLogin() {
	if($('input#user_id').val().trim() == "") {
		alert("아이디를 입력하세요");
		$('input#user_id').val("").focus();
		return;  // goLogin() 함수 종료
	}

	if($('input#user_pwd').val().trim() == "") {
		alert("비밀번호를 입력하세요");
		$('input#user_pwd').val("").focus();
		return;  // goLogin() 함수 종료
	}

	// ==== 아이디 저장 체크박스 처리 ==== //
	if( $('input#saveid').is(":checked") ) {
		localStorage.setItem("savedUserId", $('input#user_id').val().trim());
	}
	else {
		localStorage.removeItem("savedUserId");
	}

	const frm = document.loginFrm;
	frm.submit();
}// end of function goLogin(){}------------------------------


// ==== 로그아웃 처리 함수 ==== //
function goLogOut(ctx_Path) {
	location.href=`${ctx_Path}/login/logout.hb`; 
}// end of function goLogOut()----------------
