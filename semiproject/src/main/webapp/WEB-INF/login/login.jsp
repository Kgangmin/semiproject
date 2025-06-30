<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<%
   String ctx_Path = request.getContextPath();
%>  
    
<jsp:include page="/WEB-INF/header1.jsp" />

<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/css/login/login.css" />

<script type="text/javascript" src="<%= ctx_Path%>/js/login/login.js"></script>

<script type="text/javascript">

$(function(){
	
	// === 아이디 찾기에서 close 버튼을 클릭하면 iframe 의 form 태그에 입력된 값을 지우기 === //
	$('button.idFindClose').click(function(){
		
		const iframe_idFind = document.getElementById("iframe_idFind");
		
	    const iframe_window = iframe_idFind.contentWindow;
    	 
	    iframe_window.form_reset_empty();
	    
	});// end of $('button.idFindClose').click(function(){})--------------
	
	
	// === 비밀번호 찾기에서 close 버튼을 클릭하면 새로고침을 해주겠다. === //
	$('button.passwdFindClose').click(function(){
		
		javascript:history.go(0);

	});
	
	
});// end of $(function(){})----------------------------------




</script>

  <div class="container">
    <div class="row justify-content-center mt-5" style="margin: 0 auto;">
      <div class="col-md-8">
        <div class="p-4 ">
          <h3 class="text-center mb-4">LOGIN</h3>
          <form id="loginFrm" name="loginFrm" action="<%= ctx_Path %>/login/login.hb" method="post">
            <!-- 아이디 입력 -->
            <div class="mb-3">
              <label for="user_id" class="form-label">아이디</label>
              <input type="text" class="form-control" id="user_id" name="user_id"  autocomplete="off" size="20" placeholder="아이디 입력" >
            </div>
            <!-- 비밀번호 입력 -->
            <div class="mb-3">
              <label for="user_pwd" class="form-label">비밀번호</label>
              <input type="password" class="form-control" id="user_pwd" name="user_pwd" size="20" placeholder="비밀번호 입력">
            </div>
            <!-- 아이디 저장 + 비밀번호 찾기 -->
            <div class="d-flex justify-content-between align-items-center mb-3">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" id="saveid">
                <label class="form-check-label" for="saveid">아이디 저장</label>               
              </div>
              <a href="<%= ctx_Path%>/login/idFind.hb" class="text-decoration-none small" data-toggle="modal" style="margin-left: 40%;" data-target="#userIdfind" data-dismiss="modal">아이디 찾기</a>
              <a href="<%= ctx_Path%>/login/pwdFind.hb" class="text-decoration-none small" data-toggle="modal" data-target="#passwdFind" data-dismiss="modal" data-backdrop="static">비밀번호 찾기</a>
            </div>
            <!-- 로그인 버튼 -->          
              <button type="button" id="btnSubmit" class="btn btn-primary btn-lg btn-block" onclick="javascript:goLogin()">로그인</button>
              <button type="button" id="goRegister" class="btn btn-secondary btn-lg btn-block" onclick="window.location.href='<%= ctx_Path %>/member/memberRegister.hb';">회원가입</button>          
          </form>
        </div>
      </div>
    </div>
  </div>
  
  <%-- ****** 아이디 찾기 Modal 시작 ****** --%>	
  <div class="modal fade" id="userIdfind" data-backdrop="static">
	<div class="modal-dialog">
      <div class="modal-content">
      
        <!-- Modal header -->
        <div class="modal-header">
          <h4 class="modal-title">아이디 찾기</h4>
          <button type="button" class="close idFindClose" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <div id="idFind">
             <iframe id="iframe_idFind" style="border: none; width: 100%; height: 350px;" src="<%= ctx_Path%>/login/idFind.hb"> 
             </iframe>
          </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-primary idFindClose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  <%-- ****** 아이디 찾기 Modal 끝 ****** --%>
  
  
  <%-- ****** 비밀번호 찾기 Modal 시작 ****** --%>
  <div class="modal fade" id="passwdFind">
	 <div class="modal-dialog">
	      <div class="modal-content" style="margin: auto;">
	      
	        <!-- Modal header -->
	        <div class="modal-header">
	          <h4 class="modal-title">비밀번호 찾기</h4>
	          <button type="button" class="close passwdFindClose" data-dismiss="modal">&times;</button>
	        </div>
	        
	        <!-- Modal body -->
	        <div class="modal-body">
	          <div id="pwFind">
	          	<iframe style="border: none; width: 100%; height: 350px;" src="<%= ctx_Path%>/login/pwdFind.hb">  
	          	</iframe>
	          </div>
	        </div>
	        
	        <!-- Modal footer -->
	        <div class="modal-footer">
	          <button type="button" class="btn btn-primary passwdFindClose" data-dismiss="modal">Close</button>
	        </div>
	      </div>
	      
	 </div>
  </div>
  <%-- ****** 비밀번호 찾기 Modal 끝 ****** --%>
  
  
  <%-- 비밀번호 변경권고 Modal 시작 --%>
  <div id="pwdModal" style="display:none;">
 	 <p>비밀번호를 변경한지 3개월이 지났습니다.</p>
  		<button id="btnYes">지금 변경하기</button>
 		<button id="btnNo">90일 뒤에 변경하기</button>
  </div> 
  <%-- 비밀번호 변경권고 Modal 끝 --%>




<jsp:include page="/WEB-INF/footer1.jsp" />

<!-- 비밀번호 변경 모달 -->
<c:if test="${showPwdModal}">
  <div class="modal fade" id="pwdChangeModal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">비밀번호 변경 안내</h5>
        </div>
        <div class="modal-body">
          <p>비밀번호를 마지막으로 변경한 지 3개월(90일) 이상 경과하였습니다.<br/>
             지금 바로 변경하시겠습니까?</p>
        </div>
        <div class="modal-footer">
          <!-- 즉시 변경 -->
          <button type="button" class="btn btn-primary" id="changeNowBtn">
            지금 변경하기
          </button>
          <!-- 90일 뒤 알림 -->
          <button type="button" class="btn btn-secondary" id="changeLaterBtn">
            90일 뒤에 변경하기
          </button>
        </div>
      </div>
    </div>
  </div>

  <script>
    $(document).ready(function(){
      // 모달 자동 오픈
      $('#pwdChangeModal').modal('show');

      // 즉시 변경 클릭 → 비밀번호 변경 페이지로 이동
      $('#changeNowBtn').click(function(){
        window.location.href = '<%=ctx_Path%>/changePwd.hb?userid=${requestScope.user_id}';
      });

      // 나중에 변경 클릭 → 모달 닫고, DB에 마지막 변경일을 현재로 업데이트(90일 후 다시 노출되지 않도록)
      $('#changeLaterBtn').click(function(){
        $.post('<%=ctx_Path%>/login/remindPwdChange.hb', {}, function(){
          $('#pwdChangeModal').modal('hide');
          window.location.href = '<%=ctx_Path%>/index.hb';
        })
        
      });
    });
  </script>
</c:if>

