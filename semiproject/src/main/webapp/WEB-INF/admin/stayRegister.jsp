<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String ctxPath = request.getContextPath();
%>

<jsp:include page="/WEB-INF/header1.jsp"/>
<!-- 다음 우편번호 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- 카카오 맵 SDK (Geocoder 포함) -->
<script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=43e1bc9de3bc707bc039e3d3bbf88100&libraries=services"></script>

<!-- multipart/form-data, POST -->
<form id="stayForm" action="<%= ctxPath%>/admin/stayRegister.hb"
      method="post" enctype="multipart/form-data" class="container mt-5">

  <h2>숙소등록</h2>

  <div class="form-group">
    <label>카테고리</label>
    <select name="fk_stay_category_no" class="form-control" required>
      <option value="">:::선택하세요:::</option>
      <c:forEach var="c" items="${categoryList}">
        <option value="${c.stay_category_no}">
          ${c.stay_category_name}
        </option>
      </c:forEach>
    </select>
  </div>

  <div class="form-group">
    <label>숙소명</label>
    <input type="text" name="stay_name" class="form-control" required/>
  </div>

  <div class="form-group">
    <label>숙소 설명</label>
    <textarea name="stay_info" rows="4" class="form-control"></textarea>
  </div>

  <div class="form-group">
    <label>전화번호</label>
    <input type="tel" name="stay_tel" class="form-control"/>
  </div>

  <div class="form-group">
    <label>대표 썸네일 (PNG)</label>
    <input type="file" name="stay_thumbnail" accept="image/png" class="form-control-file" required/>
  </div>

  <div class="form-group">
    <label>추가 이미지 (최대 3장, PNG)</label>
    <input type="file" name="extra1" accept="image/png" class="form-control-file"/>
    <input type="file" name="extra2" accept="image/png" class="form-control-file"/>
    <input type="file" name="extra3" accept="image/png" class="form-control-file"/>
  </div>

   <!-- Daum 주소 API -->
  <div class="form-group">
    <label>주소 검색</label>
    <div class="input-group mb-2">
      <input id="postcode"    name="postcode"    class="form-control" placeholder="우편번호" readonly/>
      <div class="input-group-append">
        <button type="button" class="btn btn-outline-secondary" id="zipcodeSearch">
          주소찾기
        </button>
      </div>
    </div>
    <input id="address"      name="address"      class="form-control mb-2" placeholder="주소" readonly/>
    <input id="detailaddress" name="detailaddress" class="form-control mb-2" placeholder="상세주소"/>
    <input id="extraaddress"  name="extraaddress"  class="form-control" placeholder="참고항목(예: 동 호수)"/>
  </div>

  <input type="hidden" id="latitude"  name="latitude"/>
  <input type="hidden" id="longitude" name="longitude"/>

  <button type="submit" class="btn btn-primary">등록하기</button>
  <button type="reset"  class="btn btn-secondary">취소</button>
</form>

<jsp:include page="/WEB-INF/footer1.jsp"/>

<script>
  $(function(){
    // 읽기전용 설정
    $('#postcode, #address, #extraaddress').prop('readonly', true);

    $('#zipcodeSearch').on('click', function(){
      new daum.Postcode({
        oncomplete: function(data) {
          // 1) 주소 조합
          let addr = data.userSelectedType==='R'
                     ? data.roadAddress
                     : data.jibunAddress;
          let extraAddr = '';
          if (data.userSelectedType==='R') {
            if (data.bname && /[동|로|가]$/.test(data.bname)) {
              extraAddr += data.bname;
            }
            if (data.buildingName && data.apartment==='Y') {
              extraAddr += (extraAddr? ', '+data.buildingName : data.buildingName);
            }
            if (extraAddr) extraAddr = ' ('+extraAddr+')';
          }

          // 2) 필드에 값 넣기
          $('#postcode').val(data.zonecode);
          $('#address').val(addr);
          $('#extraaddress').val(extraAddr);
          $('#detailaddress').focus();

          // 3) 주소 → 위도/경도 변환 (addr 사용)
          var geocoder = new daum.maps.services.Geocoder();
          geocoder.addressSearch(addr, function(result, status) {
            if (status === daum.maps.services.Status.OK) {
              $('#latitude').val(result[0].y);
              $('#longitude').val(result[0].x);
            }
          });
        }
      }).open();
    });
  });
</script>
