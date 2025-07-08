<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    String ctxPath	= request.getContextPath();
%>

<!DOCTYPE html>
<html lang="ko">
<head>

    <meta charset="UTF-8" />
    
    <title>회원 탈퇴</title>
    
	<!-- Optional JavaScript -->
  <script src="<%=ctxPath%>/js/jquery-3.7.1.min.js"></script>
  <script src="<%=ctxPath%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>
  
  <%-- jQueryUI CSS 및 JS --%>
  <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
  <script src="<%=ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script>
    <style>
        html, body {
            margin: 0;
            padding: 0;
            height: 100vh;
            background-color: #f5f5f5;
            font-family: 'Noto Sans KR', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .withdraw-container {
            width: 90vw;          /* 화면 너비 90% */
            max-width: 420px;     /* 최대 너비 */
            padding: 30px 40px;   /* 좌우 padding 늘림 */
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.08);
            box-sizing: border-box;
        }

        .withdraw-container h2 {
            text-align: center;
            margin-bottom: 30px;
            font-size: 26px;      /* 제목 크기 증가 */
            font-weight: 700;
        }

        .withdraw-container label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            font-size: 15px;
        }

        .withdraw-container input[type="text"],
        .withdraw-container input[type="password"] {
            width: 100%;
            padding: 14px 16px;  /* padding 키움 */
            margin-bottom: 22px;  /* margin 간격 키움 */
            font-size: 16px;      /* 글자 크기 키움 */
            border: 1px solid #ccc;
            border-radius: 8px;   /* 둥근 모서리 좀 더 키움 */
            box-sizing: border-box;
            background-color: #fff;
            outline: none;
            transition: border 0.2s;
        }

        .withdraw-container input[type="text"]:focus,
        .withdraw-container input[type="password"]:focus {
            border-color: #007bff;
        }

        .withdraw-container input[readonly] {
            background-color: #f9f9f9;
            color: #555;
        }

        .btn-group {
            display: flex;
            justify-content: space-between;
            gap: 14px;
        }

        .btn-group button {
            flex: 1;
            padding: 14px;
            font-size: 16px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.2s ease-in-out;
        }

        .btn-delete {
            background-color: #007bff;
            color: white;
        }

        .btn-delete:hover {
            background-color: #0056b3;
        }

        .btn-cancel {
            background-color: #e0e0e0;
            color: #333;
        }

        .btn-cancel:hover {
            background-color: #c2c2c2;
        }
    </style>
</head>
<body>

<div class="withdraw-container">
    <h2>회원 탈퇴</h2>
    <form name="mbwithdraw" action="<%= ctxPath %>/withdrawComplete.hb" method="post" >
        <label for="userid">아이디</label>
        <input type="text" id="userid" name="userid" value="${loginUser.user_id}" readonly />

        <label for="userpwd">비밀번호</label>
        <input type="password" id="userpwd" name="userpwd" placeholder="비밀번호를 입력하세요"  />

        <div class="btn-group">
            <button type="button" name='withdraw' class="btn-delete">탈퇴하기</button>
            <button type="button" class="btn-cancel" onclick="history.back()">취소</button>
        </div>
    </form>
</div>

<script type="text/javascript">

$(function()
{
	//	탈퇴하기 버튼을 눌렀을 경우
	$("button[name='withdraw']").click(function()
	{
		const pwd = $("#userpwd").val().trim();
    	//	필수입력 확인
		if (pwd === "")
		{
			alert("모든 항목을 입력해주세요.");
			return;
		}

		//	제출
		$("form[name='mbwithdraw']").submit();
	});
});
   
</script>

</body>
</html>
