let b_emailcheck_click = false;
// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let b_email_change = false;
// 이메일값을 변경했는지 여부를 알아오기 위한 용도

const contextPath = '<%= request.getContextPath() %>';

$(function(){
	$("span.error").hide();
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기
	$("span#emailcheck").click(function(){
		$("span#emailCheckResult").hide()
		
		const emailVal = $("input#newEmail").val().trim();
		const currentEmail = $("#currentEmail").val().trim();
		const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);  
	    
		if (!regExp_email.test(emailVal)) {
				
		          $("span.error").show();
		          $("input#newEmail").focus();
		          return;
		      }
		$("span.error").hide();
		b_emailcheck_click = true;
		$("span#emailCheckResult").show()

	    $.ajax({
	        url: contextPath + "/member/emailDuplicateCheck.hb",
	        type: "POST",
	        data: {	email: emailVal	},
	        dataType: "json",
	        success: function(json) {
	            if(json.isExists) {
					if(emailVal === currentEmail){
						$("span#emailCheckResult").html("기존 이메일과 동일하므로 변경할 수 없습니다.")
													.css({ "color": "orange" });
					}
					else{
						$("span#emailCheckResult").html(emailVal + " 은 현재 다른 사용자가 사용 중입니다.").css({"color":"red"});
						$("input#newEmail").val("");
						//	console.log(json.isExists);
					}
					$("input#newEmail").val("");
	            } else {
	                $("span#emailCheckResult").html(emailVal + " 은 사용 가능합니다.").css({"color":"navy"});
					//	console.log(json.isExists);
	            }
	        },
	        error: function(request, status, error) {
	            alert("code: " + request.status + "\nmessage: " + request.responseText + "\nerror: " + error);
	        }
	    });

	});
	
	
	// 이메일값이 변경되면 수정하기 버튼을 클릭시 "이메일중복확인" 을 클릭했는지 클릭안했는지를 알아보기위한 용도 초기화 시키기  
	$("input#newEmail").bind("change", function(){
		
	   b_emailcheck_click = false;
	   // "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도  
	   
	   b_email_change = true;
	     // 이메일값을 변경했는지 여부를 알아오기 위한 용도
	});
	
	
   });   // end $(function(){
   
   
   
	// "수정하기" 버튼 클릭시 호출되는 함수 
		function goEmailEdit(){
		   
		   // *** 필수입력사항에 모두 입력이 되었는지 검사하기 시작 *** //
		   
		   const newEmail = document.getElementById("newEmail").value;
		    const password = document.getElementById("user_pwd").value;

		   if (newEmail.trim() === "" || password.trim() === "") {
		      
		        alert("모든 입력사항을 입력해주세요.");
		        return;
		          
		    }
		   else{
			   // *** 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭했는지 검사하기 시작 *** //
			   if(b_email_change && !b_emailcheck_click) {
			      // 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭 안 했을 경우
			      alert("이메일 중복확인을 클릭하셔야 합니다.");
			      return; // goEdit() 함수를 종료한다.
			  	}

			   }
			   
			   document.getElementById("email_hidden").value = newEmail;

			   // *** 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭했는지 검사하기 끝 *** //
		   	    const frm = document.changeemail;
			    frm.action = "pwdcheck.hb";
		 		frm.method = "post";
		 		frm.submit();
		   }// end  goEmailEdit()
		   
		
   
   


