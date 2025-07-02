$(function()
{
	$('#btn-up').on('click', function()
	{// 부드럽게 문서 최상단으로 스크롤
		$('html, body').animate({ scrollTop: 0 }, 600);
	});

	$('#btn-down').on('click', function()
	{// 부드럽게 문서 최하단으로 스크롤
		$('html, body').animate({ scrollTop: $(document).height() }, 600);
	});
});