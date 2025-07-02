<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
         
<%
    String ctxPath = request.getContextPath();
%>

<jsp:include page="/WEB-INF/header1.jsp"/>

<div class="container mt-5">
  <h2>객실 등록</h2>
  <form id="roomForm"
        action="<%= ctxPath%>/admin/roomRegister.hb"
        method="post" enctype="multipart/form-data">
    <!-- 숙소번호 -->
    <input type="hidden" name="stay_no" value="${param.stay_no}" />

    <!-- 등급 -->
    <div class="form-group">
      <label>객실 등급</label>
      <input type="text" name="room_grade"
             class="form-control" required/>
    </div>

    <!-- 설명 -->
    <div class="form-group">
      <label>객실 설명</label>
      <textarea name="room_info" rows="3"
                class="form-control"></textarea>
    </div>

    <!-- 가격 -->
    <div class="form-group">
      <label>1박당 가격</label>
      <input type="number" name="price_per_night" min="0"
             class="form-control" required/>
    </div>

    <!-- 대표 썸네일 -->
    <div class="form-group">
      <label>대표 이미지 (PNG, 1장)</label>
      <input type="file" name="thumbnail" accept="image/png"
             class="form-control-file" required/>
    </div>

       <!-- 추가 이미지 -->
    <div class="form-group">
      <label>추가 이미지 (PNG, 최대 3장)</label>
      <!-- 드롭존 -->
      <div id="fileDrop" class="fileDrop border border-secondary mb-2">
        파일을 드래그하거나 클릭해 선택하세요.
      </div>
      <!-- 파일명 리스트 + 삭제 버튼 -->
      <div id="fileListContainer" class="mb-2"></div>
      <!-- 썸네일 미리보기 -->
      <div id="previewContainer" class="d-flex"></div>
    </div>

    <button type="submit" class="btn btn-primary">등록하기</button>
  </form>
</div>

<jsp:include page="/WEB-INF/footer1.jsp"/>
<style>
  /* 드롭존 스타일 */
  .fileDrop {
    width: 100%; height: 120px;
    background: #fafafa;
    text-align: center; line-height: 120px;
    color: #666; cursor: pointer;
  }
  .fileDrop.dragover {
    background: #e0f7fa; border-color: #007bff; color: #007bff;
  }
  /* 파일 리스트 */
  #fileListContainer .fileList {
    display: inline-block; position: relative;
    padding: 4px 8px; margin-right: 8px; margin-bottom: 8px;
    border: 1px solid #ddd; background: #fff;
  }
  #fileListContainer .fileList .delete {
    position: absolute; top: 2px; right: 4px;
    cursor: pointer; color: #999;
  }
  #fileListContainer .fileList .delete:hover {
    color: #f00;
  }
  #fileListContainer .fileList .fileName {
    max-width: 120px; white-space: nowrap;
    overflow: hidden; text-overflow: ellipsis;
  }
  /* 미리보기 썸네일 */
  #previewContainer img {
    width: 80px; height: 80px; object-fit: cover;
    margin-right: 8px; border: 1px solid #ccc;
  }
</style>

<script>
$(function(){
  const MAX_FILES = 3;
  let filesArr = [];

  const $drop = $('#fileDrop');
  const $list = $('#fileListContainer');
  const $preview = $('#previewContainer');

  // 클릭 → 파일 다이얼로그
  $drop.on('click', () => {
    $('<input type="file" accept="image/png" multiple>')
      .on('change', function(){
        handleFiles(this.files);
      })
      .click();
  });

  // 드래그 오버/리브
  $drop.on('dragover', e => {
    e.preventDefault(); $drop.addClass('dragover');
  });
  $drop.on('dragleave', e => {
    e.preventDefault(); $drop.removeClass('dragover');
  });

  // 드롭
  $drop.on('drop', e => {
    e.preventDefault(); $drop.removeClass('dragover');
    handleFiles(e.originalEvent.dataTransfer.files);
  });

  function handleFiles(fileList){
    const newFiles = Array.from(fileList)
      .filter(f => f.type==='image/png')
      .slice(0, MAX_FILES - filesArr.length);
    newFiles.forEach(f => filesArr.push(f));
    renderAll();
  }

  function renderAll(){
    // 리스트
    $list.empty();
    filesArr.forEach((file, idx) => {
      const $item = $(`
        <div class="fileList">
          <span class="delete">&times;</span>
          <span class="fileName"></span>
        </div>`);
      $item.find('.fileName').text(file.name);
      $item.find('.delete').on('click', ()=> {
        filesArr.splice(idx,1);
        renderAll();
      });
      $list.append($item);
    });
    // 미리보기
    $preview.empty();
    filesArr.forEach(f => {
      const img = document.createElement('img');
      img.src = URL.createObjectURL(f);
      $preview.append(img);
    });
  }

  // 폼 제출 → AJAX
  $('#roomForm').on('submit', function(e){
    e.preventDefault();
    const form = this;
    const fd = new FormData(form);
    // 파일 추가
    filesArr.forEach((file,i) => {
      fd.append('extra'+(i+1), file);
    });
    // XMLHttpRequest 전송
    const xhr = new XMLHttpRequest();
    xhr.open(form.method, form.action);
    xhr.onload = () => {
      alert('객실 등록이 완료되었습니다.');
      // 상세 페이지로 리다이렉트
      location.href = '<%=ctxPath%>/stayDetail.hb?stay_no=${param.stay_no}';
    };
    xhr.send(fd);
  });
});
</script>
