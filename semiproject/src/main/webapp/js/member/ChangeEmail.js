$(function(){   
   
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
    
   });   // end $(function(){
   
   
   
   
   

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
      

      
   }// end of function goEdit()-----------------------/**
