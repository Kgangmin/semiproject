
$(function(){
		 $("button[name='changePwd']").click(function() {

	        const currentPwd = $("#user_pwd").val().trim();
	        const newPwd = $("#new_user_pwd").val().trim();
	        const confirmPwd = $("#confirm_Pwd").val().trim();

	        // 필수입력 확인
	        if (currentPwd === "" || newPwd === "" || confirmPwd === "") {
	            alert("모든 항목을 입력해주세요.");
	            return;
	        }

	        // 새 비밀번호 일치 확인
	        if (newPwd !== confirmPwd) {
	            alert("새 비밀번호가 일치하지 않습니다.");
	            return;
	        }

	        // (선택) 비밀번호 유효성 검사
	        const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
	        if (!regExp_pwd.test(newPwd)) {
	            alert("숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호로 입력해주세요")
	            return;
	        }

	        // 제출
	        $("form[name='pwdChangeFrm']").submit();
	    });
	
		
				
				
				
		
	
	
	
	
})//end $(function()




// function declation