<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
%>

<!-- CSS 연결 -->
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/footer/scroll_control.css" />

<!-- scroll control buttons -->
<div id="scroll-control">
	<button class="scroll-btn" id="btn-up" title="맨 위로">↑</button>
	<div style="
		height: 4px;               /* 선 두께: 4px */
		background-color: rgba(13, 110, 253, 0.15); /* primary 색상 + 투명도 15% */
		width: 100%;
		margin: 0;
		padding: 0;">
	</div>
	<button class="scroll-btn" id="btn-down" title="맨 아래로">↓</button>
</div>

  <footer class="text-center py-3">
    <p>&copy; NOLJA Company 2025 &nbsp; 대표전화: 1577-1588</p>
    <p>(주)NOLJA</p>
  </footer>

<!-- JS 연결 -->
<script src="<%=ctxPath%>/js/scroll_control.js"></script>

</body>
</html>