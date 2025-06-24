.
$(function(){
   
   $('button#btnSubmit').click(()=>{
      goLogin();  // 로그인 시도한다(아이디저장은 LocalStorage 를 사용함).   
   });
   
   $('input#user_pwd').bind("keyup", (e)=>{
      if(e.which == 13) { // 암호입력란에 엔터를 했을 경우 
         goLogin();  // 로그인 시도한다(아이디저장은 LocalStorage 를 사용함).   
      }
   });
   
});// end of $(function(){})------------------------------------


// Function Declaration

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
      
   const frm = document.loginFrm;
   frm.submit();
   
}// end of function goLogin(){}------------------------------








