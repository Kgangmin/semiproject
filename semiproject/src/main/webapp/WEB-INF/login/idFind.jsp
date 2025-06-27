<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
    
%>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" >

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= ctxPath%>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script> 

<script type="text/javascript">

	$(function(){
		
		const method = "${requestScope.method}";
		
		
		if(method == "GET") {
			$('div#div_findResult').hide();
		}
		
		if(method == "POST") {
			$('input:text[name="user_name"]').val("${requestScope.user_name}");
			$('input:text[name="email"]').val("${requestScope.email}");
		}
		
		$('button.btn-success').click(function(){
			goFind();
		});
		
		$('input:text[name="email"]').bind('keyup', function(e){
			if(e.keyCode == 13) {
			   goFind();
			}
		});
		
	});// end of $(function(){})-------------------------
	
	
	// Function Declaration
	function goFind() {
		
		const user_name = $('input:text[name="user_name"]').val().trim();
		
		if(user_name == "") {
			alert("성명을 입력하세요");
			return; // goFind()함수의 종료
		}
		
		const email = $('input:text[name="email"]').val();
		
		const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
		// 이메일 정규표현식 객체 생성
		
		if( !regExp_email.test(email) ) {
			// 이메일이 정규표현식에 위배된 경우
			alert("이메일이 올바르지 않습니다.");
			return; // goFind()함수의 종료
		}
		
		const frm = document.idFindFrm;
		frm.action = "<%= ctxPath%>/login/idFind.hb";
		frm.method = "post";
		frm.submit();
		
	}// end of function goFind()-------------------

	
	// 아이디 찾기 모달창에 입력한 input 태그 value 값 초기화 시켜주는 함수 생성하기
	function form_reset_empty(){
		document.querySelector('form[name="idFindFrm"]').reset();
		$('div#div_findResult').empty();
	}// end of function form_reset_empty(){}---------------
	
</script>

<form name="idFindFrm">

   <ul style="list-style-type: none;">
      <li style="margin: 25px 0">
          <label style="display: inline-block; width: 90px;">성명</label>
          <input type="text" name="user_name" size="25" autocomplete="off" /> 
      </li>
      <li style="margin: 25px 0">
          <label style="display: inline-block; width: 90px;">이메일</label>
          <input type="text" name="email" size="25" autocomplete="off" /> 
      </li>
   </ul> 

   <div class="my-3 text-center">
      <button type="button" class="btn btn-success">찾기</button>
   </div>
   
</form>

<div id="div_findResult" class="my-3 text-center">
     <span style="color: navy; font-size: 16pt; font-weight: bold;">${requestScope.user_name}님의 아이디는 ${requestScope.user_id} 입니다.</span> 
</div>




