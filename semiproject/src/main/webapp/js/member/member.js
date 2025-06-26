

	
	$("span.error").hide();
	$("input#newEmail").blur( (e) => {
	    
	 //   const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;  
	 //  또는
	     const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);  
	     // 이메일 정규표현식 객체 생성 
	     
	     const bool = regExp_email.test($(e.target).val());   
	    
	    if(!bool) {
	       // 이메일이 정규표현식에 위배된 경우
	      	
	       $(e.target).parent().find("span.error").show();
	            
	       $(e.target).val("").focus(); 
	    }
	    else {
	       // 이메일이 정규표현식에 맞는 경우 
	       $("table#tblMemberEdit :input").prop("disabled", false);
	       
	       //   $(e.target).next().hide();
	        //  또는
	        $(e.target).parent().find("span.error").hide();
	    }
	    
	 });// 아이디가 email 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
	 
	
	
	

















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



// "수정하기" 버튼 클릭시 호출되는 함수 
function goEmailEdit(){
   
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 시작 *** //
   
	const newEmail = document.getElementById("newEmail").value;
    const password = document.getElementById("password").value;

   if (newEmail.trim() === "" || password.trim() === "") {
		
	     alert("모든 입력사항을 입력해주세요.");
	     return;
          
    }
   
   
   // *** 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭했는지 검사하기 시작 *** //
   if(b_email_change && !b_emailcheck_click) {
      // 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭 안 했을 경우
      alert("이메일 중복확인을 클릭하셔야 합니다.");
      return; // goEdit() 함수를 종료한다.
   }
   // *** 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭했는지 검사하기 끝 *** //
   

   
}// end of function goEdit()-----------------------