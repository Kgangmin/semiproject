<%@ page contentType="text/html;charset=UTF-8" %>

<%
    String ctxPath = request.getContextPath();    
%>

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" >

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<html>
<head>
    <title>휴면 계정 해제</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding-top: 100px;
        }
        .form-group {
            margin: 10px auto;
        }
        input {
            padding: 8px;
            width: 250px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }
        button {
            padding: 8px 16px;
            border: none;
            border-radius: 6px;
            background-color: #007BFF;
            color: white;
            cursor: pointer;
            margin-left: 5px;
        }
        #authSection {
            display: none;
        }
    </style>
    <script>
        let code = "";

        function showAuthSection() {
            const name = document.getElementById("name").value.trim();
            const phone = document.getElementById("phone").value.trim();

            if (!name || !phone) {
                alert("이름과 전화번호를 모두 입력하세요.");
                return;
            }

            document.getElementById("authSection").style.display = "block";
        }

        function sendCode() {
            // 5자리 랜덤 숫자
            code = Math.floor(10000 + Math.random() * 90000).toString();
            alert("인증번호 전송됨: " + code);  // 실제 구현은 SMS API
        }

        function verifyCode() {
            const userCode = document.getElementById("inputCode").value;
            if (userCode === code) {
                document.getElementById("dormantForm").submit();
            } else {
                alert("인증번호가 일치하지 않습니다.");
            }
        }
    </script>
</head>
<body>
    <h2>휴면 계정 해제</h2>
    <form id="dormantForm" action="dormantRelease.do" method="post">
        <div class="form-group">
            이름: <input type="text" name="name" id="name" />
        </div>
        <div class="form-group">
            전화번호: <input type="text" name="phone" id="phone" />
        </div>
        <div class="form-group">
            <button type="button" onclick="showAuthSection()">인증하기</button>
        </div>

        <div id="authSection">
            <div class="form-group">
                인증번호: <input type="text" id="inputCode" />
                <button type="button" onclick="sendCode()">인증번호 받기</button>
            </div>
            <div class="form-group">
                <button type="button" onclick="verifyCode()">휴면 해제</button>
            </div>
        </div>
    </form>
</body>
</html>
