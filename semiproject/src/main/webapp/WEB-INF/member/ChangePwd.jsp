<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctxPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <title>비밀번호 변경</title>
       <!-- Optional JavaScript -->
  <script src="<%=ctxPath%>/js/jquery-3.7.1.min.js"></script>
  <script src="<%=ctxPath%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>
  
  <%-- jQueryUI CSS 및 JS --%>
  <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
  <script src="<%=ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script>
  <script type="text/javascript" src="<%= ctxPath%>/js/member/ChangePwd.js"></script>  
    <style >
        body {font-family: 'Noto Sans KR', sans-serif;background-color: #f8f8f8;margin: 0;padding: 0;}
		.container {width: 420px;margin: 30px auto;background-color: white;padding: 30px;box-shadow: 0 0 10px rgba(0,0,0,0.1);border-radius: 12px;}
		h2 {margin-bottom: 25px;text-align: center;}
		label { display: block;margin-top: 15px;font-weight: bold;}
		input[type="text"],input[type="password"] { width: 100%;padding: 10px;margin-top: 5px;border: 1px solid #ccc;border-radius: 6px;}
		 .button-group {margin-top: 30px;display: flex;justify-content: space-between;}
		 button { width: 48%;padding: 10px;border: none;border-radius: 6px;font-weight: bold;cursor: pointer;}
		.btn-update {background-color: #007bff;color: white;}
		.btn-cancel {background-color: #ccc;color: #333;}
        #emailcheck:hover {background: #12cfc0;color: #fff;}
		#emailcheck {display: inline-block; margin-top: 10px;padding: 8px 10px;border: solid 1.5px gray;border-radius: 6px;cursor: pointer;font-size: 8pt;font-weight : bold;}
		.error {color: red;}
      

    
    </style>
</head>
<body>

<div class="container">
    <h2>비밀번호 변경</h2>

    <form name="pwdChangeFrm" method="post" action="<%= ctxPath %>/changePasswordEnd.hb">
        
        <label for="currentPwd">현재 비밀번호</label>
        <input type="password" id="user_pwd" name="user_pwd" >

        <label for="newPwd">새 비밀번호</label>
        <input type="password" id="new_user_pwd" name="new_user_pwd" >

        <label for="confirmPwd">새 비밀번호 확인</label>
        <input type="password" id="confirm_Pwd" name="confirm_Pwd" >

        <div class="button-group">
            <button type="submit" class="btn-update" >변경하기</button>
            <button type="button" class="btn-cancel" onclick="window.close();">취소</button>
        </div>
    </form>
</div>
</body>
</html>