<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
    <p>&copy; Company 2024 &nbsp; 대표전화: 1577-1588</p>
    <p>쌍용교육센터 우수한 형제자매들</p>
  </footer>

</body>
</html>

<style>
	#scroll-control
	{
		position: fixed;
		bottom: 40px;
		right: 30px;
		z-index: 9999;
		display: flex;
		flex-direction: column;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
		border-radius: 12px;
		overflow: hidden;
		backdrop-filter: blur(2px);
	}

	.scroll-btn
	{
		width: 48px;
		height: 40px;
		background-color: rgba(13, 110, 253, 0.09); /* primary 색상 기반 */
		color: rgba(13, 110, 253, 0.3);
		border: none;
		font-size: 20px;
		cursor: pointer;
		transition: all 0.25s ease;
	}

	.scroll-btn:hover
	{
		background-color: rgba(13, 110, 253, 0.9);
		color: white;
	}

	.scroll-btn-top
	{
		border-radius: 12px 12px 0 0;
		box-shadow: inset 0 -6px 8px -6px rgba(13, 110, 253, 0.2);
	}

	.scroll-btn-bottom
	{
		border-radius: 0 0 12px 12px;
	}
	
</style>

<!-- 스크립트 -->
<script>
  document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('btn-up').addEventListener('click', function () {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });

    document.getElementById('btn-down').addEventListener('click', function () {
      window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
    });
  });
</script>
