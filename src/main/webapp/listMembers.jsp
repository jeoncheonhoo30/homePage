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
<title>회원정보 출력창</title>
<c:choose>
	<c:when test='${msg=="addMember"}'>
		<script>
			alert("회원을 등록했습니다.");
		</script>
	</c:when>
	<c:when test='${msg=="modified"}'>
		<script>
			alert("회원정보를 수정했습니다.");
		</script>
	</c:when>
	<c:when test='${msg=="deleted"}'>
		<script>
			alert("회원정보를 삭제했습니다.");
		</script>
	</c:when>
</c:choose>
</head>
<body>
	<h2 align="center">회원정보</h2>
	<table align="center" border="1">
		<tr align="center" bgcolor="lightgreen">
			<th>아이디</th><th>비밀번호</th><th>이름</th>	<th>이메일</th>
		</tr>
		<c:choose>
			<c:when test="${empty memberList}"> <!-- 정보가 비워져 있을때 -->
				<tr>
					<td colspan="5" align="center">등록된 회원이 없습니다.</td>
				</tr>
			</c:when>
			<c:when test="${not empty memberList}"> <!-- 정보가 있을때 -->
				<c:forEach var="member" items="${memberList}">
					<tr align="center">
						<td>${member.id}</td>
						<td>${member.pwd}</td>
						<td>${member.name}</td>
						<td>${member.email}</td>
						<td><a href="${contextPath}/member/modMemberForm.do?id=${member.id}">수정</a></td>
						<td><a href="${contextPath}/member/delMember.do?id=${member.id}">삭제</a></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<h2 align="center">가입을 환영합니다.</h2>
	<p align="center"><a href="${contextPath}/index.jsp">홈페이지로</a></p>
</body>
</html>