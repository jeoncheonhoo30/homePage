<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="homePage.ex01.MemberDAO" %> <!-- login 함수를 사용하여 로그인 처리를 하기 위해 user패키지의 UserDAO를 불러온다 -->
<%@ page import="java.io.PrintWriter" %> <!-- 자바 스크립트 문장을 작성하기 위해 사용-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="memberVO" class="homePage.ex01.MemberVO" scope="page"/> <!-- 한명의 회원 정보를 담는 MemberVO클래스를 자바 빈즈로 사용하며 현재 page안에서만 빈즈를 사용 -->
<jsp:setProperty name="memberVO" property="id" />
<jsp:setProperty name="memberVO" property="pwd" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 처리 jsp</title>
</head> 
<body>
	<%  // 로그인 페이지에서 넘겨준 userID와 userPassword를 받아서 로그인 판별
		String id= request.getParameter("id");
		String pwd= request.getParameter("pwd");
		MemberDAO memberDAO =new MemberDAO();
		int result=memberDAO.login(id, pwd);
		String msg= "";
		if (result == 1){ // 로그인 정보가 맞으면 자바스크립트를 실행하여 페이지를 이동시킴
			session.setAttribute("sessionID", id);
			msg="/homePage/main.jsp";
		            
		}
		else if (result == 0){ 
			msg = "/homePage/popup.jsp?msg=0";
		}else    // 아이디가 틀릴경우
        {
            msg = "/homePage/popup.jsp?msg=-1";
        }
         
        // sendRedirect(String URL) : 해당 URL로 이동
        // URL뒤에 get방식 처럼 데이터를 전달가능
        response.sendRedirect(msg);
    %>
</body>
</html>