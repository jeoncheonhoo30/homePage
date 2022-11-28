<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
#wrap {
    width: 500px;
    height: 350px;
    border: 1px solid lightskyblue;
    border-radius: 5px;
    background-color: #dad9ff;
    margin: auto;
    display: block;
    margin-top: 300px;
}
 
.login_form {
    width: 500px;
    height: 350px;
    padding: 20px 50px;
}
 
input {
    margin-bottom: 14px;
    width: 290px;
    height: 30px;
    font-size: 16px;
    border: lightgray;
}
 
#loginBtn {
    width: 100px;
    height: 80px;
    font-size: 16px;
    float: right;
    position: relative;
    top: -93px;
    right: 90px;
    padding: 10px 20px;
    background-color: #fff;
    border: 1px solid whitesmoke;
    cursor: pointer;
}
#joinBtn {
    width: 140px;
    height: 50px;
    font-size: 16px;
    float: left;
    padding: 10px 20px;
    background-color: #fff;
    border: 1px solid whitesmoke;
    cursor: pointer;
}

#closeBtn {
    width: 140px;
    height: 50px;
    float: left;
    margin-left: 15px;
    font-size: 14px;
    background-color: #fff;
    border: 1px solid whitesmoke;
    cursor: pointer;
}
#closeBtn:hover , #joinBtn:hover, #loginBtn:hover {
    background-color: #c3c2fd;
}
    </style>
    
    </head>
    <body>
        <div id="wrap">
            <form class="login_form" name="Login" action="loginAction.jsp" method="post">
                <h1>로그인</h1>
                <input id="id" name="id" type="text" placeholder="아이디를 입력하세요"><br>
                <input id="password" name="pwd" type="password" placeholder="비밀번호를 입력하세요"><br>
                <input id="loginBtn" type="submit" value="로그인" />
                <input id="joinBtn" type="button" value="회원가입" onclick="location.href='memberForm.jsp'" />
                <input id="closeBtn" type="button" value="닫기" onclick="history.go(-1)"/>
            </form>
        <% 
            // 아이디, 비밀번호가 틀릴경우 화면에 메시지 표시
            // LoginPro.jsp에서 로그인 처리 결과에 따른 메시지를 보낸다.
            String msg=request.getParameter("msg");
            
            if(msg!=null && msg.equals("0")) 
            {
                out.println("<br>");
                out.println("<font color='red' size='5'>비밀번호를 확인해 주세요.</font>");
            }
            else if(msg!=null && msg.equals("-1"))
            {    
                out.println("<br>");
                out.println("<font color='red' size='5'>아이디를 확인해 주세요.</font>");
            }
        %>    
        </div>
    </body>
    </html>