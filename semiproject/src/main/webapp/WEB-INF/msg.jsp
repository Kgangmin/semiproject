<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String message = (String) request.getAttribute("message");
    String loc = (String) request.getAttribute("loc");
    Boolean popupClose = (Boolean) request.getAttribute("popup_close");
    if (popupClose == null) popupClose = false;
%>

<script type="text/javascript">

	const message = "<%= message.replace("\"", "\\\"") %>";  // 메시지 이스케이프
	const loc = "<%= loc %>";
	const popupClose = <%= popupClose %>;  // true or false (boolean)

	alert(message);  // 메시지 출력

	if (popupClose)
	{// 팝업창이면 부모창 이동 후 팝업 닫기
		if (window.opener && !window.opener.closed)
		{
			window.opener.location.href = loc;
		}
		window.close();
	}
	else
	{// 일반창이면 그냥 현재창에서 이동
		location.href = loc;
	}
	
</script>