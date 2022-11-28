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
<title>회원정보 수정창</title>
</head>
<body>
	<form action="${contextPath}/member/modMember.do?id=${memberInfo.id}" method="post">
		<h2 align="center">회원 정보 수정창</h2>
		<table align="center">
			<tr>
				<td width="200"><p align="right">아이디</p></td>
				<td width="400"><input type="text" name="id" 
				value="${memberInfo.id}" disabled></td> <!-- disabled 입력창을 비활성화 -->
			</tr>
			<tr>
				<td width="200"><p align="right">비밀번호</p></td>
				<td width="400"><input type="password" name="pwd" value="${memberInfo.pwd}"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">이름</p></td>
				<td width="400"><input type="text" name="name" value="${memberInfo.name}"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">이메일</p></td>
				<td width="400"><input type="text" name="email" value="${memberInfo.email}"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">가입일</p></td>
				<td width="400"><input type="text" name="joinDate"
				 value="${memberInfo.joinDate}" disabled></td> <!-- disabled 입력창을 비활성화 -->
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="수정하기">
					<input type="reset" value="다시입력">
				</td>
			</tr>			
		</table>
	</form>
</body>
</html>