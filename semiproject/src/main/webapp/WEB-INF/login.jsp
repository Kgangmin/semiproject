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
              <a href="#" class="text-decoration-none small" style="margin-left: 40%;">아이디 찾기</a>
              <a href="#" class="text-decoration-none small">비밀번호 찾기</a>
            </div>
            <!-- 로그인 버튼 -->          
              <button type="button" id="btnSubmit" class="btn btn-primary btn-lg btn-block" onclick="javascript:goLogin()">로그인</button>
              <button type="button" id="goRegister" class="btn btn-secondary btn-lg btn-block" onclick="window.location.href='<%= ctx_Path %>/member/memberRegister.hb';">회원가입</button>          
          </form>
        </div>
      </div>
    </div>
  </div>




<jsp:include page="/WEB-INF/footer1.jsp" />

