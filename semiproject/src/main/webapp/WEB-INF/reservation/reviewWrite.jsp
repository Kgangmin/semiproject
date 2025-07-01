<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String ctxPath = request.getContextPath(); %>

<jsp:include page="../header1.jsp" />

<style>
.review-container {
    max-width: 700px;
    margin: 60px auto;
    padding: 30px;
    background-color: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 16px rgba(0,0,0,0.05);
    font-family: 'Noto Sans KR', sans-serif;
}
.review-container h2 {
    font-size: 1.4rem;
    font-weight: 700;
    margin-bottom: 25px;
    color: #111827;
}
.form-group {
    margin-bottom: 20px;
}
label {
    display: block;
    font-weight: 600;
    margin-bottom: 8px;
    color: #374151;
}
select, textarea, input[type="file"] {
    width: 100%;
    padding: 12px;
    font-size: 1rem;
    border: 1px solid #d1d5db;
    border-radius: 10px;
    background-color: #f9fafb;
    box-sizing: border-box;
}
textarea {
    height: 120px;
    resize: vertical;
}
.preview-img {
    max-width: 100%;
    max-height: 200px;
    border-radius: 8px;
    margin-top: 10px;
    object-fit: cover;
    display: none;
}
.btn-submit {
    display: inline-block;
    background-color: #2563eb;
    color: white;
    border: none;
    padding: 12px 24px;
    font-size: 1rem;
    font-weight: 600;
    border-radius: 8px;
    cursor: pointer;
    margin-top: 15px;
    transition: background-color 0.2s;
}
.btn-submit:hover {
    background-color: #1d4ed8;
}
</style>

<div class="review-container">
    <h2>리뷰 작성</h2>

    <form action="${ctxPath}/reviewWrite.hb" method="post" enctype="multipart/form-data">
        <input type="hidden" name="reserv_no" value="${reservation.reserv_no}" />

        <div class="form-group">
            <label for="rating">평점</label>
            <select name="rating" id="rating" required>
                <option value="">별점을 선택하세요</option>
                <option value="5">★★★★★ (5점)</option>
                <option value="4">★★★★☆ (4점)</option>
                <option value="3">★★★☆☆ (3점)</option>
                <option value="2">★★☆☆☆ (2점)</option>
                <option value="1">★☆☆☆☆ (1점)</option>
            </select>
        </div>

        <div class="form-group">
            <label for="content">리뷰 내용</label>
            <textarea name="content" id="content" placeholder="숙소에 대한 솔직한 후기를 남겨주세요." required></textarea>
        </div>

        <div class="form-group">
            <label for="photo">사진 업로드 (선택)</label>
            <input type="file" name="photo" id="photo" accept="image/*" onchange="previewImage(event)">
            <img id="imgPreview" class="preview-img" alt="미리보기 이미지" />
        </div>

        <button type="submit" class="btn-submit">리뷰 등록</button>
    </form>
</div>

<jsp:include page="../footer1.jsp" />

<script>
function previewImage(event) {
    const imgPreview = document.getElementById('imgPreview');
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = e => {
            imgPreview.src = e.target.result;
            imgPreview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    } else {
        imgPreview.style.display = 'none';
    }
}
</script>