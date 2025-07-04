<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
   String ctxPath = request.getContextPath();
   //     
%>  

<jsp:include page="../header1.jsp" />

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/memberRegister.css" />


<script type="text/javascript" src="<%= ctxPath%>/js/member/memberRegister.js"></script>

<div class="row" id="divRegisterFrm">
   <div class="col-md-12">
      <form name="registerFrm">
          <table id="tblMemberRegister">
             <thead>
                <tr>
                   <th colspan="2">::: 회원가입 <span style="font-size: 10pt; font-style: italic;">(<span class="star">*</span>표시는 필수입력사항)</span> :::</th>
                </tr>
             </thead>
             
             <tbody>
                <tr>
                    <td colspan="2" style="line-height: 50%;">&nbsp;</td>
                </tr>
                
                <tr>
                    <td>성명&nbsp;<span class="star">*</span></td>
                    <td>
                       <input type="text" name="name" id="name" maxlength="30" class="requiredInfo" />
                       <span class="error">성명은 필수입력 사항입니다.</span>
                    </td>
                </tr>
                
                <tr>
                    <td>아이디&nbsp;<span class="star">*</span></td>
                    <td>
                       <input type="text" name="userid" id="userid" maxlength="40" class="requiredInfo" />&nbsp;&nbsp;  
                      <%-- 아이디 --%>
                       <span id="idcheck">아이디중복확인</span>
                       <span id="idCheckResult"></span>
                    </td>
                </tr>
                
                <tr>
                    <td>비밀번호&nbsp;<span class="star">*</span></td>
                    <td>
                       <input type="password" name="pwd" id="pwd" maxlength="15" class="requiredInfo" />
                       <span class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>
                    </td>
                </tr>
                
                <tr>
                    <td>비밀번호확인&nbsp;<span class="star">*</span></td>
                    <td>
                       <input type="password" id="pwdcheck" maxlength="15" class="requiredInfo" />
                       <span class="error">암호가 일치하지 않습니다.</span>
                    </td>
                </tr>
                
                 <tr>
                    <td>연락처&nbsp;</td>
                    <td>
                       <input type="text" name="hp1" id="hp1" size="6" maxlength="3" value="010" readonly />&nbsp;-&nbsp; 
                       <input type="text" name="hp2" id="hp2" size="6" maxlength="4" />&nbsp;-&nbsp;
                       <input type="text" name="hp3" id="hp3" size="6" maxlength="4" />    
                       <span class="error">휴대폰 형식이 아닙니다.</span>
                    </td>
                </tr>              
               
               <tr>
                    <td>이메일&nbsp;<span class="star">*</span></td>
                    <td>
                       <input type="text" name="email" id="email" maxlength="60" class="requiredInfo" />
                       <span class="error">이메일 형식에 맞지 않습니다.</span>
                       <%-- 이메일중복체크 --%>
                       <span id="emailcheck">이메일중복확인</span>
                       <span id="emailCheckResult"></span>
                    </td>
                </tr>
                
                
                <tr>
                    <td>생년월일</td>
                    <td>
                       <input type="text" name="birthday" id="datepicker" maxlength="10" />
                       <span class="error">생년월일은 마우스로만 클릭하세요.</span>
                    </td>
                </tr>
                
             
                
                <tr>
                    <td colspan="2">
                       <label for="agree">이용약관에 동의합니다</label>&nbsp;&nbsp;<input type="checkbox" id="agree" />
                    </td>
                </tr>
                
                <tr>
                    <td colspan="2">
                       <iframe src="<%= ctxPath%>/iframe_agree/agree.html" width="100%" height="150px" style="border: solid 1px navy;"></iframe>
                    </td>
                </tr>
                
                <tr>
                    <td colspan="2" class="text-center">
                       <input type="button" class="btn btn-primary btn-lg mr-5" value="가입하기" onclick="goRegister()" />
                       <input type="button"  class="btn btn-danger btn-lg" value="취소하기" onclick="goReset()" />
                    </td>
                </tr>
                 
             </tbody>
          </table>
        
      </form>
   </div>
</div>


