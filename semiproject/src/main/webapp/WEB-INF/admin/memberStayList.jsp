<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/header1.jsp" />



<script>
$(document).ready(function() {
    // data-userid 속성이 있는 모든 tr에 클릭 이벤트 바인딩
    $("tr[data-userid]").click(function() {
    	var user_id = $(this).attr("data-userid");
        if(user_id) {
            window.location.href = "<%=request.getContextPath()%>/admin/memberOneDetail.hb?user_id=" + user_id;
        } else {
            alert("회원 ID가 없습니다.");
        }
    });
});
</script>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>관리자 페이지 - 회원 및 호텔 목록</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container mt-4">

    <h2 class="mb-3">회원 목록</h2>

    <form method="get" action="manage.hb" class="form-inline mb-3">
        <label for="memberSearchType" class="mr-2">검색:</label>
        <select name="memberSearchType" id="memberSearchType" class="form-control mr-2">
            <option value="user_name">회원명</option>
            <option value="email">이메일</option>
            
        </select>
        <input type="text" name="memberSearchWord" class="form-control mr-2" placeholder="검색어 입력" />
        <button type="submit" class="btn btn-primary">검색</button>
    </form>

    <table class="table table-bordered table-hover">
        <thead class="thead-light">
            <tr>
                <th>회원명</th>
                <th>이메일</th>
                <th>전화번호</th>
                <th>회원등급</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="member" items="${memberList}">
                <tr data-userid="${member.user_id}" >
                    <td>${member.user_name}</td>
                    <td>${member.email}</td>
                    <td>${member.mobile}</td>
                    <td>${member.fk_grade_no}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

        <!-- 회원목록 페이징 -->
		<c:if test="${totalPage != null && totalPage > 1}">
		    <ul class="pagination justify-content-center ">
		      <c:forEach var="i" begin="1" end="${totalPage}">
		        <li class="page-item ${i == currentPage ? 'active' : ''}">
		          <a class="page-link" href="manage.hb?page=${i}&memberSearchType=${param.memberSearchType}&memberSearchWord=${param.memberSearchWord}">${i}</a>
		        </li>
		      </c:forEach>
		    </ul>

		</c:if>
		

    <h2 class="my-4">숙소 목록</h2>

    <form method="get" action="manage.hb" class="form-inline mb-3">
        <label for="staySearchWord" class="mr-2">검색:</label>
        <input type="text" name="staySearchWord" id="staySearchWord" class="form-control mr-2" placeholder="호텔명으로 검색" />
        <button type="submit" class="btn btn-primary">검색</button>
    </form>

    <table class="table table-bordered table-hover">
        <thead class="thead-light">
            <tr>
                <th>숙소번호</th>
                <th>숙소이름</th>
                <th>전화번호</th>
                <th>평점</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="stay" items="${stayList}">
                <tr>
                    <td>${stay.stay_no}</td>
                    <td>${stay.stay_name}</td>
                    <td>${stay.stay_tel}</td>
                    <td>${stay.stay_score}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

<!-- 호텔 페이징 -->
<c:if test="${stayTotalPage != null && stayTotalPage > 1}">
  <ul class="pagination justify-content-center">
    <c:forEach var="i" begin="1" end="${stayTotalPage}">
      <li class="page-item ${i == stayCurrentPage ? 'active' : ''}">
        <a class="page-link" href="manage.hb?stayPage=${i}&staySearchWord=${staySearchWord}">${i}</a>
      </li>
    </c:forEach>
  </ul>
</c:if>


</div>

<script src="<%=request.getContextPath()%>/js/jquery-3.7.1.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

