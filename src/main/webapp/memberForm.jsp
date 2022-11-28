<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입창</title>
<style>

      div.create{
      width: 800px;
      text-align: center;
      padding: 30px;
      border-bottom: 1px solid black;
      margin: auto;
      }
   
      table{
      height: 300px;
      width: 900px;
      border-top: 3px solid black;
      margin-right: auto;
      margin-left: auto;
      }
   
      td{
      border-bottom: 1px dotted black;
      }
   
      caption{
      text-align: left;
      }
   
      .col1 {
      background-color: #c3c2fd;
      padding: 10px;
      text-align: right;
      font-weight: bold;
      font-size: 0.8em;
      }
   
      .col2 {
      text-align: left;
      padding: 5px;
      }
   
      .but1 {
      height: 25px;
      width: 80px;
      color: black;
      background-color: white;
      border-color: black;
      }
   
   
    
      .but2{
      height: 35px;
      width: 150px;
      background-color: white;
      border: 2px solid black;
      }
      .but3{
      height: 35px;
      width: 150px;
      background-color: white;
      border: 2px solid black;
      }
      
      
      .but1:hover {
      background-color: #c3c2fd;
      color: black;
      border: 2px solid #c3c2fd;
      }
   
   
      .but2:hover {
      background-color: #c3c2fd;
      color: white;
      border: 2px solid #c3c2fd;
      }
   
      .but3:hover {
      background-color: #c3c2fd;
      color: white;
      border: 2px solid #c3c2fd;
      }
      
      p{
      font-size: 0.7em;
      }
   
      .num{
      color: red;
      }
   
    </style>
    <script>
        function formCheck(form) {
            
            if(form.name.value == "") {
                alert('이름을 입력하세요.');
                form.name.focus() ;
                return ;
            }else if(form.id.value == "") {
                alert('아이디를 입력하세요.');
                form.id.focus() ;
                return ;
            }else if(form.pwd.value == "") {
                alert('비밀번호를 입력하세요.');
                form.pwd.focus() ;
                return ;
            }else if(form.pwd.value.length < 10 || form.pwd.value.length > 16) {
                alert('비밀번호는 10 ~ 16자 사이로 입력하세요.');
                form.pwd.focus() ;
                return ;
            }else if(form.pwdCheck.value == "") {
                alert('비밀번호 확인을 입력하세요.');
                form.pwdCheck.focus() ;
                return ;
            }else if(form.email.value == "") {
                alert('e-mail을 입력하세요.');
                form.email.focus() ;
                return ;
            } 
            alert('회원가입이 완료되었습니다.') ;
            	form.submit();
        }
        
     
      </script>
</head>
<body>
	<form action="${contextPath}/member/addMember.do" method="post">
		<div class="container">
	    	<div class="insert">
	          <table>
			      <caption><h2>회원가입 양식</h2></caption>
			      <tr>
			          <td class="col1">이름</td>
			          <td class="col2"><input type="text" name="name" maxlength="5"></td>
			      </tr>
			      <tr>
			          <td class="col1">아이디</td>
			          <td class="col2">
			              <input type="text" name="id" maxlength="10">
			              <input class='but1' type="button" value="중복확인" onclick="">
			          </td>
			      </tr>
			      <tr>
			          <td class="col1">비밀번호</td>
			          <td class="col2">
			              <input type="password" name="pwd" maxlength="16">
			              <p>※비밀번호는 <span class="num">문자, 숫자, 특수문자(~!@#$%^&*)의 조합
			              10 ~ 16자리</span>로 입력이 가능합니다.</p>
			          </td>
			      </tr>
			      <tr>
			          <td class="col1">비밀번호 확인</td>
			          <td class="col2"><input type="password" name="pwdCheck" maxlength="16"></td>
			      </tr>
			      <tr>
			        <td class="col1">이메일</td>
			        <td class="col2">
			            <input type="text" name="email">
			        </td>
				  </tr>
	    	  </table>
	      </div>
	    </div>
	   
	    <div class="create">
	      
	          <input class="but2" type="button" value="회원가입" onclick="formCheck(form)" >
	          <input class="but3" type="button" value="가입취소" onclick="history.go(-1)">
	      
	    </div>
   
	</form>
</body>
</html>