<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
   String ctxPath = request.getContextPath(); 
        // MyMVC
%>

<jsp:include page="/WEB-INF/header1.jsp"/>

<style type="text/css">
.highcharts-figure,
.highcharts-data-table table {
    min-width: 320px;
    max-width: 800px;
    margin: 1em auto;
}

.highcharts-data-table table {
    font-family: Verdana, sans-serif;
    border-collapse: collapse;
    border: 1px solid #ebebeb;
    margin: 10px auto;
    text-align: center;
    width: 100%;
    max-width: 500px;
}

.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}

.highcharts-data-table th {
    font-weight: 600;
    padding: 0.5em;
}

.highcharts-data-table td,
.highcharts-data-table th,
.highcharts-data-table caption {
    padding: 0.5em;
}

.highcharts-data-table thead tr,
.highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}

.highcharts-data-table tr:hover {
    background: #f1f7ff;
}

input[type="number"] {
    min-width: 50px;
}

div#table_container table {width: 100%}
div#table_container th, div#table_container td {border: solid 1px gray; text-align: center;} 
div#table_container th {background-color: #595959; color: white;}

</style>

<script src="<%= ctxPath%>/Highcharts-10.3.1/code/highcharts.js"></script>
<script src="<%= ctxPath%>/Highcharts-10.3.1/code/modules/exporting.js"></script>
<script src="<%= ctxPath%>/Highcharts-10.3.1/code/modules/export-data.js"></script>
<script src="<%= ctxPath%>/Highcharts-10.3.1/code/modules/accessibility.js"></script>
<script src="<%= ctxPath%>/Highcharts-10.3.1/code/modules/series-label.js"></script>


<div style="display: flex;">
  <div style="width: 80%; margin: auto;">

    <h2 style="margin: 50px 0;">사용자 예약 통계 차트</h2>

    <form style="margin: 20px 0 50px 0;">
      <select id="searchType" style="height: 40px;">
        <option value="">통계선택하세요</option>
        <option value="category">숙소 분류별 예약 통계</option>
        <option value="region">지역별 예약 통계</option>
      </select>
    </form>

    <div id="chart_container"></div>
    <div id="table_container" style="margin-top: 40px;"></div>

  </div>
</div>

<script type="text/javascript">
  $(function(){
    // <select> 변경 시
    $('#searchType').change(function(){
      func_choice($(this).val());
    });
    // 페이지 로드 시 기본값: category
    $('#searchType').val('category').trigger('change');
  });

  function func_choice(key) {
    // 초기화
    $('div#chart_container, div#table_container').empty();

    switch (key) {
      case "":
        // 아무 것도 선택 안 하면 숨김
        return;

      case "category":
        // 숙소 분류별 예약 통계
        $.ajax({
          url: '<%=ctxPath%>/admin/categoryChart.hb',
          dataType: 'json'
        })
        .done(function(json){
          // 1) 파이차트 #1: 예약 건수 비율
          Highcharts.chart('chart_container', {
            chart: { type: 'pie' },
            title: { text: '분류별 예약 건수 비율' },
            series: [{
              name: '건수비율',
              data: json.map(function(o,i){
                return {
                  name: o.cname,
                  y: Number(o.cnt),
                  sliced: i===0,
                  selected: i===0
                };
              })
            }]
          });

          // 2) 테이블: 카테고리별 예약 건수
          var html = '<table class="data-table">'
                   + '<thead><tr><th>카테고리</th><th>예약건수</th></tr></thead><tbody>';
          json.forEach(function(o){
            html += '<tr><td>'+o.cname+'</td><td>'+o.cnt+'</td></tr>';
          });
          html += '</tbody></table>';
          $('#table_container').html(html);
        })
        .fail(function(){
          alert('분류별 통계 로드 실패');
        });
        break;

      case "region":
        // 지역별 예약 통계
        $.ajax({
          url: '<%=ctxPath%>/admin/regionChart.hb',
          dataType: 'json'
        })
        .done(function(json){
          // 1) 컬럼차트 #1: 지역별 예약 건수
          Highcharts.chart('chart_container', {
            chart: { type: 'column' },
            title: { text: '지역별 예약 건수' },
            xAxis: { categories: json.map(o=>o.region) },
            yAxis: { title: { text: '건수' } },
            series: [{ name: '건수', data: json.map(o=>Number(o.cnt)) }]
          });

          // 2) 테이블: 지역별 예약 건수
          var html = '<table class="data-table">'
                   + '<thead><tr><th>지역</th><th>예약건수</th></tr></thead><tbody>';
          json.forEach(function(o){
            html += '<tr><td>'+o.region+'</td><td>'+o.cnt+'</td></tr>';
          });
          html += '</tbody></table>';
          $('#table_container').html(html);
        })
        .fail(function(){
          alert('지역별 통계 로드 실패');
        });
        break;
    }
  }
</script>

<jsp:include page="/WEB-INF/footer1.jsp"/>