<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctxPath = request.getContextPath();
%>
<%
  String certStep = (String) session.getAttribute("certStep");
  boolean showCertInput = "verify".equals(certStep);
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
<script>


</script>  
  <style>
        body {font-family: 'Noto Sans KR', sans-serif;background-color: #f8f8f8;margin: 0;padding: 0;}
		.container {width: 800px; height: 500px;  margin: 50px auto; background-color: white; padding: 30px; box-shadow: 0 0 10px rgba(0,0,0,0.1); border-radius: 12px;}
		form {margin-left: 140px;}
		h2 {margin-bottom: 25px; text-align: center;}
		label { display: block; margin-top: 15px; font-weight: bold;}
		input[type="text"] { width: 70%;padding: 10px;margin-top: 10px;border: 1px solid #ccc;border-radius: 6px;}
		
		 .button-group {margin-top: 30px; justify-content: space-between; }
		 button { width: 30%;padding: 10px; border: none; border-radius: 6px; font-weight: bold;cursor: pointer;}
		.btn-ok {background-color: #007bff;color: white;}
		.btn-certificate {background-color: #ccc;color: #333;}
        .btn-certificate:hover {background: gray;}
		
    
    </style>
</head>
<body>

<div class="container">
    <h2>휴면 해제</h2>

    <form name="DormantFrm" method="post" action="<%= ctxPath %>/dormant.hb">  
        <label for="user_name">성명</label>
        <input type="text" id="user_name" name="user_name" >		
        <label for="mobile">휴대폰번호</label>
        <input type="text" id="mobile" name="mobile" >
        <div class="button-group">
        	<button type="submit" class="btn-certificate" value="send">인증번호 받기</button>
		</div>
        <label for="input_number" style="<%= showCertInput ? "" : "display:none;" %>">인증번호입력</label>
		<input type="text" id="input_number" name="input_number" style="<%= showCertInput ? "" : "display:none;" %>">

		<div class="button-group" style="<%= showCertInput ? "" : "display:none;" %>">
		    <button type="submit" class="btn-ok" name="btn-ok" value="verify">확인</button>          
		</div>
    </form>
</div>
</body>
</html>