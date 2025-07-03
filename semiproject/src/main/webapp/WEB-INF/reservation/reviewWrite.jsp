<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="../header1.jsp" />
<script type="text/javascript" src="<%= ctxPath%>/js/reviewWrite.js"></script>
<style>
	.review-container {max-width:700px;margin:60px auto;padding:30px;background:#fff;border-radius:12px;box-shadow:0 4px 16px rgba(0,0,0,0.05);font-family:'Noto Sans KR',sans-serif;}
	.review-container h2 {font-size:1.4rem;font-weight:700;margin-bottom:25px;color:#111827;}
	.form-group {margin-bottom:20px;}label {display:block;font-weight:600;margin-bottom:8px;color:#374151;}
	input[type="file"],textarea {width:100%;padding:12px;font-size:1rem;border:1px solid #d1d5db;border-radius:10px;background-color:#f9fafb;box-sizing:border-box;}
	textarea {height:120px;resize:vertical;}
	.preview-img {max-width:100%;max-height:200px;border-radius:8px;margin-top:10px;object-fit:cover;display:none;}
	.btn-submit {display:inline-block;background-color:#2563eb;color:white;border:none;padding:12px 24px;font-size:1rem;font-weight:600;border-radius:8px;cursor:pointer;margin-top:15px;transition:background-color 0.2s;}
	.btn-submit:hover {background-color:#1d4ed8;}
	.star-rating {display:flex;font-size:2.5rem;cursor:pointer;user-select:none;gap:5px;}
	.star-rating span {position:relative;width:2.5rem;height:2.5rem;color:#d1d5db;display:flex;justify-content:center;align-items:center;font-size:2.5rem;line-height:1;}
	.star-rating span::before {content:'★';position:absolute;left:0;top:0;width:100%;height:100%;color:#d1d5db;pointer-events:none;font-size:2.5rem;}
	.star-rating span.full::before {color:#facc15;}
	.star-rating span.half::before {color:#d1d5db;}
	.star-rating span.half::after {content:'★';position:absolute;left:0;top:0;width:50%;height:100%;overflow:hidden;color:#facc15;pointer-events:none;font-size:2.5rem;}

</style>

<script>

</script>

<div class="review-container">
    <h2>리뷰 작성</h2>

    <form action="${requestScope.referer}" method="post" >
        <input type="hidden" name="reserv_no" value="${reservation.reserv_no}" />
        <input type="hidden" name="rating" id="rating" required />

        <div class="form-group">
            <label for="rating">평점</label>
            <div class="star-rating" id="starRating" aria-label="별점 평가" role="radiogroup">
                <span data-index="1" role="radio" aria-checked="false" tabindex="0"></span>
                <span data-index="2" role="radio" aria-checked="false" tabindex="-1"></span>
                <span data-index="3" role="radio" aria-checked="false" tabindex="-1"></span>
                <span data-index="4" role="radio" aria-checked="false" tabindex="-1"></span>
                <span data-index="5" role="radio" aria-checked="false" tabindex="-1"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="content">리뷰 내용</label>
            <textarea name="content" id="content" placeholder="숙소에 대한 솔직한 후기를 남겨주세요." required></textarea>
        </div>
	

        <button type="submit" id= "review_register" class="btn-submit">리뷰 등록</button>
    </form>
</div>

<jsp:include page="../footer1.jsp" />

