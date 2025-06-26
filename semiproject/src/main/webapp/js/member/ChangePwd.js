
$(function(){
	
	
	$('button[name="changePwd"]').on('click', function(){
		const pwd = document.getElementById("user_pwd").value;
	    const new_pwd = document.getElementById("new_user_pwd").value;
		const confirmPwd = document.getElementById("confirm_Pwd").value;
		if (pwd.trim() === "" || new_pwd.trim() === "" || confirmPwd.trim() === "") {
		      
			alert("모든 입력사항을 입력해주세요.");
			return;
		          
		}
		else{
			const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
			     // 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성 
			     
			const bool = regExp_pwd.test($(e.target).val());   
			
			
			
			
		}	
		
	});
	
	
	
	
	
})//end $(function()




// function declation