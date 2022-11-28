<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/food.css" type="text/css">
    <title>음식점</title>
</head>
<body>
    <header>
            
        <div id="logo">
            <a href="index.jsp" >
            <img src="images/logo.jpg" alt="Logo" width="80" height="80">
            </a>
            <a href="index.jsp"><h1 id="Tripadvisor">Tripadvisor</h1></a>
        </div>

        <div id="top_menu">
            <a href="#">리뷰</a>
            <a href="#">여행</a>
            <a href="#">알림</a>
            <div id="wrap">
                <form class="login_form">
                    <input id="loginBtn" type="button" value="로그인" onclick="location.href='popup.jsp'" />
                </form>
                <div id="sessionID">
	     				<c:choose>
	     					<c:when test="${sessionID != null}">
		     					<h2>
						        <font color="red"><%=session.getAttribute("sessionID") %></font>
						        님 환영합니다.
			   					 </h2>
	     					</c:when>
	     					<c:otherwise>
	     						<h2>
							        <font color="red">사용자</font>
							        님 환영합니다.<br> 로그인 해주세요
			   					 </h2>
	     					</c:otherwise>
	     				</c:choose>	                 
	                </div>
            </div>            
            <script src="login.js"></script>
        </div>

        <nav>
            <ul>
                <h1>대한민국 둘러보기</h1>
                <li><a href="${contextPath}/board/listArticles.do">게시판</a></li>
                <li id="joy"><a href="index.jsp">즐길거리</a></li>
                <li><a href="#" id="food1">음식점</a></li>
                <li><a href="#">항공</a></li>
                <li id="rr"><a href="#">렌터카</a></li>
            </ul>
        </nav>

    </header>
    <h2>대한민국 음식점</h2>
    <div id="food_list">
        <ul class="food">
            <li><a href="images/food/seoul.jpg"><img src="images/food/seoul.jpg" alt="seoul"><h3>서울 음식점</a></li>
            <li><a href="images/food/busan.jpg"><img src="images/food/busan.jpg" alt="seoul"><h3>부산 음식점</h3></a></li>
            <li><a href="images/food/incheon.jpg"><img src="images/food/incheon.jpg" alt="seoul"><h3>인천 음식점</h3></a></li>
            <li><a href="images/food/daejeon.jpg"><img src="images/food/daejeon.jpg" alt="seoul"><h3>대전 음식점</h3></a></li>
            <li><a href="images/food/daegu.jpg"><img src="images/food/daegu.jpg" alt="seoul"><h3>대구 음식점</h3></a></li>
            <li><a href="images/food/sungnam.jpg"><img src="images/food/sungnam.jpg" alt="seoul"><h3>성남 음식점</h3></a></li>
            <li><a href="images/food/jejudo.jpg"><img src="images/food/jejudo.jpg" alt="seoul"><h3>제주도 음식점</h3></a></li>
            <li><a href="images/food/suwon.jpg"><img src="images/food/suwon.jpg" alt="seoul"><h3>수원 음식점</h3></a></li>
            <li><a href="images/food/gwangju.jpg"><img src="images/food/gwangju.jpg" alt="seoul"><h3>광주 음식점</h3></a></li>
            <li><a href="images/food/jeju.jpg"><img src="images/food/jeju.jpg" alt="seoul"><h3>제주 음식점</h3></a></li>
            <li><a href="images/food/youngin.jpg"><img src="images/food/youngin.jpg" alt="seoul"><h3>용인 음식점</h3></a></li>
            <li><a href="images/food/ulsan.jpg"><img src="images/food/ulsan.jpg" alt="seoul"><h3>울산 음식점</h3></a></li>
            <li><a href="images/food/bucheon.jpg"><img src="images/food/bucheon.jpg" alt="seoul"><h3>부천 음식점</h3></a></li>
            <li><a href="images/food/anyang.jpg"><img src="images/food/anyang.jpg" alt="seoul"><h3>안양 음식점</h3></a></li>
            <li><a href="images/food/goyang.jpg"><img src="images/food/goyang.jpg" alt="seoul"><h3>고양 음식점</h3></a></li>
            <li><a href="images/food/jeonju.jpg"><img src="images/food/jeonju.jpg" alt="seoul"><h3>전주 음식점</h3></a></li>
            <li><a href="images/food/changwon.jpg"><img src="images/food/changwon.jpg" alt="seoul"><h3>창원 음식점</h3></a></li>
            <li><a href="images/food/gwangju2.jpg"><img src="images/food/gwangju2.jpg" alt="seoul"><h3>광주 음식점</h3></a></li>
            <li><a href="images/food/seoguipo.jpg"><img src="images/food/seoguipo.jpg" alt="seoul"><h3>서귀포 음식점</h3></a></li>
            <li><a href="images/food/cheonan.jpg"><img src="images/food/cheonan.jpg" alt="seoul"><h3>천안 음식점</h3></a></li>
        </ul>
    </div>
    <script>
        function showPopup() { window.open("popup.html", "a", "width=420, height=330, left=100, top=50"); }
        
    </script>
</body>
</html>