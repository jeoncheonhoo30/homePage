<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="logo">
    <img src="./img/logo.PNG">
    </div>
 
    <div id="loginForm">
    <h2 style="color: red">${param.msg}</h2>
    <c:choose>
    <c:when test="${sessionScope.login ne 1}">
    <form action="memLogin.do" method="post">
        <p>
            <label for="uId">I D : </label>
            <input type="text" name="id" value="">
        </p>
        <p>
            <label for="uPwd">pwd :</label>
            <input type="password" id="uPwd" name="pwd" size="20" value="">
        </p>
        <input type ="submit" value="로그인">
    </form>
    <p><a href="memAddForm.do">회원가입</a></p>
    </c:when>
    <c:when test="${sessionScope.login eq 1}">
    <p>${sessionScope.id}님 환영합니다!</p>
    <p><a href="memLogout.do">로그아웃</a>
    </c:when>
    </c:choose>
    </div>
    
    <div id="header">
        <ul class="menu_bar">
            <li><a href="boardList.do">게시판 리스트</a></li>
            <li><a href="memList.do">회원 관리</a></li>
            <li><a href="memList.do">빈 페이지</a></li>
        </ul>
    </div>
	
</body>
</html>