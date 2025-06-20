<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<% String ctxPath = request.getContextPath(); %>

<!DOCTYPE html>
<html>
<head>
    <title>이메일 변경</title>
<script type="text/javascript" src="<%= ctxPath%>/js/member/member.js"></script>
    <style>
        body {
            font-family: 'Noto Sans KR', sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding: 0;
        }

        .container {
        	
            width: 420px;
            margin: 30px auto;
            background-color: white;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 12px;
        }

        h2 {
            margin-bottom: 25px;
            text-align: center;
        }

        label {
       		
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"] {
        	
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .button-group {
            margin-top: 30px;
            display: flex;
            justify-content: space-between;
        }

        button {
        
            width: 48%;
            padding: 10px;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
        }

        .btn-update {
            background-color: #007bff;
            color: white;
        }

        .btn-cancel {
            background-color: #ccc;
            color: #333;
        }
    </style>
    
</head>
<body>
<div class="container">
    <h2>이메일 변경</h2>
    <form action="#" method="post" onsubmit="return validateForm()">
        <label for="currentEmail">현재 이메일</label>
        <input type="text" id="currentEmail" name="currentEmail" value="${sessionScope.loginUser.email}" readonly />

        <label for="newEmail">새 이메일</label>
        <input type="text" id="newEmail" name="newEmail" />
		<span class="error">이메일 형식에 맞지 않습니다.</span>
        <label for="password">비밀번호 확인</label>
        <input type="password" id="password" name="password"/>

        <div class="button-group">
            <button type="submit" class="btn-update" onclick="goEmailEdit()">변경하기</button>
            <button type="button" class="btn-cancel" onclick="history.back();">취소</button>
        </div>
    </form>
</div>

<script>
</script>
</body>
</html>