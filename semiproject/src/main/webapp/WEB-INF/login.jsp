<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<%
   String ctx_Path = request.getContextPath();
%>  

<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/css/login/login.css" />

<script type="text/javascript" src="<%= ctx_Path%>/js/login/login.js"></script>
    
<jsp:include page="/WEB-INF/header1.jsp" />


  <div class="container">
    <div class="row justify-content-center mt-5" style="margin: 0 auto;">
      <div class="col-md-8">
        <div class="p-4 ">
          <h3 class="text-center mb-4">LOGIN</h3>
          <form name="loginFrm">
            <!-- 아이디 입력 -->
            <div class="mb-3">
              <label for="log_userid" class="form-label">아이디</label>
              <input type="text" class="form-control" id="log_userid" autocomplete="off" size="20" placeholder="아이디 입력" >
            </div>
            <!-- 비밀번호 입력 -->
            <div class="mb-3">
              <label for="log_password" class="form-label">비밀번호</label>
              <input type="password" class="form-control" id="log_password" size="20" placeholder="비밀번호 입력">
            </div>
            <!-- 아이디 저장 + 비밀번호 찾기 -->
            <div class="d-flex justify-content-between align-items-center mb-3">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" id="saveid">
                <label class="form-check-label" for="saveid">아이디 저장</label>
              </div>
              <a href="#" class="text-decoration-none small">비밀번호 찾기</a>
            </div>
            <!-- 로그인 버튼 -->          
              <button type="button" id="btnSubmit" class="btn btn-primary btn-lg btn-block" onclick="javascript:goLogin()" >로그인</button>
              <button type="submit" id="goRegister" class="btn btn-secondary btn-lg btn-block" href="#">회원가입</button>          
          </form>
        </div>
      </div>
    </div>
  </div>




<jsp:include page="/WEB-INF/footer1.jsp" />

