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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/index.css" type="text/css">
    <link rel="icon" href="favicon.ico" type="image/x-icon" sizes="16x16">
    <script src="js/jquery-3.6.0.min.js"></script>
<title>메인 페이지</title>
</head>
<div id="page">
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
                    <li id="joy"><a href="#">즐길거리</a></li>
                    <li><a href="food.jsp">음식점</a></li>
                    <li><a href="#">항공</a></li>
                    <li id="rr"><a href="#">렌터카</a></li>
                </ul>
            </nav>

        </header>
        <article id="top3">
            <ul>
                <li id="korea">
                    <a href="korea.html"><img src="images/south-korea.jpg" alt="south-korea" width="600" height="430"></a>
                </li>
                <li id="photo">
                    <a href="korea.html"><img src="images/photo4jpg.jpg" alt="south-korea" width="550" height="215"></a>
                    <a href="korea.html"><img src="images/photo5jpg.jpg" alt="south-korea" width="550" height="215"></a>
                </li>

            </ul>
        </article>

        <article id="best">
            <section id="top4">
                <h1>여행지</h1>
                <div>                    
                    <a href="#"><img src="images/seoul.jpg" alt="seoul"></a>
                    <h2>서울</h2>
                    <p>아시아, 대한민국</p>
                </div>
                
                <div>
                    <a href="#"><img src="images/busan.jpg" alt="busan"></a>
                    <h2>부산</h2>
                    <p>아시아, 대한민국</p>
                </div>
                <div>
                    <a href="#"><img src="images/jeju.jpg" alt="jeju"></a>
                    <h2>제주</h2>
                    <p>대한민국, 제주도</p>
                </div>
                <div>
                    <a href="#"><img src="images/incheon.jpg" alt="incheon"></a>
                    <h2>인천</h2>
                    <p>아시아, 대한민국</p>
                </div>
            </section>
        </article>
        
        <article id="top12">
            <h1>대한민국 소재 최고 인기 관광 명소</h1>
            <h2>모두 보기</h2>            
            <section class="img 123">
                <div>
                    <a href="#"><img src="images/images1/경복궁.jpg" alt="경복궁"></a>                    
                    <h2>1. 경복궁</h2>
                    <p>역사적인 장소 · 역사박물관</p>
                    <h2 class="hov 1">작성자: George_Korean<br>대한민국의 역사가 잠들어 있는 곳. 서울을 방문 했다면 꼭 방문해야 되는곳. 경복궁은 우리의 역사다. 넓은 경복궁을 산책할 수 있는 것은 언제나 행복이다.</h2>
                </div>

                <div>
                    <a href="#"><img src="images/images1/감천문화마을.jpg" alt="문화마을"></a>
                    <h2>2. 감천 문화마을</h2>
                    <p>인근 지역 · 벼룩시장 & 재래시장</p>
                    <h2 class="hov 2">작성자: Footprints672217<br>감천문화마을에서 친구들과 여유롭게 다니면서 주말을 보냈다.
                        요즘처럼 우울한 시절에 칼라플한 마을의 독특한 느낌이 마음의 위로가 되었다. </h2>
                </div>

                <div>
                    <a href="#"><img src="images/images1/앞산공원.jpg" alt="앞산공원"></a>
                    <h2>3. 앞산공원</h2>
                    <p>공원</p>
                    <h2 class="hov 3">작성자: Expedition732467<br>등산 겸 구경하러 가봤는데 날이 쌀쌀해서 밝을 때 가면 좋을것같아요 케이블카도 있고 근처에 예쁜 카페가 많아서 종종 가요.</h2>
                </div>
            </section>

            <section class="img 456">
                <div>
                    <a href="#"><img src="images/images1/우도.jpg" alt="우도"></a>
                    <h2>4. 우도</h2>
                    <p>섬</p>
                    <h2 class="hov 4">작성자: gogoga<br>제주관광지를 다 둘러 봤으면 좀 색다른곳인 우도를 방문해보는게 어떨까? 제주 쓰러우면서 일반 섬 분위기를 풍기는그곳 우도이다.</h2>
                </div>
                
                <div>
                    <a href="#"><img src="images/images1/송도센트럴파크.jpg" alt="송도센트럴파크"></a>
                    <h2>5. 송도 센트럴 파크</h2>
                    <p>공원</p>
                    <h2 class="hov 5">작성자: lejabo0227<br>공원도 크게 있고, 배도 탈수 있어서 주기적으로 방문하여 쉬는 곳입니다. 호텔에 부대시설이 부족하긴 합니다만 넓고, 깔끔합니다. 배달음식도 가성비좋은 맛집이 많습니다. </h2>
                </div>
                
                <div>
                    <a href="#"><img src="images/images1/선산일출봉.jpg" alt="성산일출봉"></a>
                    <h2>6. 성산 일출봉</h2>
                    <p>산 · 지층</p>
                    <h2 class="hov 6">작성자: Go_suhong<br>정상을 꼭 올라가 보세요손님들과 같이.꼭 정상에 올라가 보시라고 애기 하고 싶어요계단이 많아서 올라갈때 힘은 들지만 그만큼 아름다운 정상 전망을 보여 줍니다.</h2>
                </div>
            </section>

            <button id="btn1">더보기</button>

            <section class="img 789">
                <div>
                    <a href="#"><img src="images/images1/제주올레길.jpg" alt="제주올레길"></a>
                    <h2>7. 제주 올레길</h2>
                    <p>하이킹 트레일</p>
                    <h2 class="hov 7">작성자: MIJINJ25<br>예전에는 박물관이나 관광지 위주로 다녀서올래길이 예쁜줄몰랐는데아무생각없이 바다를 보며 걸으니 너무힐링되고좋네요.</h2>
                </div>

                <div>
                    <a href="#"><img src="images/images1/함덕서우봉해변.jpg" alt="서우봉해변"></a>
                    <h2>8. 함덕 서우봉 해변</h2>
                    <p>해변</p>
                    <h2 class="hov 8">작성자: BillyChoi63<br>서우봉의 작은 산책로를 따라 올라가면 멋진 뷰를 감상할 수 있어요. 해변가의 카페에서 차한잔 하면서 바다를 보는 것도 좋았습니다. 제주에 다시 가면 꼭 들리고 싶은 멋진 바닷가</h2>
                </div>

                <div>
                    <a href="#"><img src="images/images1/해운대.jpg" alt="해운대"></a>
                    <h2>9. 해운대</h2>
                    <p>해변</p>
                    <h2 class="hov 9">작성자: jsshin2018<br>오랜만에 해변 즐기기를 했습니다. 바다가 가깝지 않은 곳에 사는 사람들에게는 바다를 보는 것 만으로 힐링이 되곤 합니다. 해운대는 우리나라에서 가장 대표적인 해변이라고 할 수 있습니다.</h2>
                </div>
            </section>
            
            <section class="img 101112">
                <div>
                    <a href="#"><img src="images/images1/에버랜드.jpg" alt="에버랜드"></a>
                    <h2>10. 에버랜드</h2>
                    <p>놀이공원 & 테마파크</p>
                    <h2 class="hov 10">작성자: kemmi<br>놀이기구도 다 재밌고 넓고 아이들이랑 같이 가면 좋은곳 먹거리 많고 맛있고 한번가면 또 가고 싶음 아이들 있으면 한번 가는것도 좋을것 같음.</h2>
                </div>

                <div>
                    <a href="#"><img src="images/images1/남이섬.jpg" alt="남이섬"></a>
                    <h2>11. 남이섬</h2>
                    <p>섬</p>
                    <h2 class="hov 11">작성자: micookin<br>남이섬 들어갈 때 짚라인을 타고 들어가서 배를 타고 나올 수 있습니다.한적하고 힐링되는 데이트 코스로는 좋습니다.</h2>
                </div>

                <div>
                    <a href="#"><img src="images/images1/dmz.jpg" alt="DMZ"></a>
                    <h2>12. DMZ</h2>
                    <p>역사적인 장소</p>
                    <h2 class="hov 12">작성자: bkmoon1<br>임진각 DMZ 관광안내소는 대한민국이 분단국가라는 사실을 실제로 느낄 수 있는 곳으로 하번쯤 방문해야 할 곳입니다. </h2>
                </div>
            </section>
            <button id="btn2">줄이기</button>
        </article>    
        <footer>
            <ul>
                <li><a href="#">이용 약관</a></li>
                <li><a href="#">개인정보 취급방침</a></li>
                <li><a href="#">사이트맵</a></li>
                <li><a href="#">사이트 운영방식</a></li>
            </ul>
            <address>
                대한민국의 한국어 사용자를 대상으로 하는 트립어드바이저 웹사이트 버전입니다.<br> 다른 국가 또는 지역에 거주하는 경우 드롭다운 메뉴에서 해당 국가 또는 지역에 적합한 트립어드바이저 버전을 선택하세요.
            </address>
        </footer>
    </div>
    <script>
        $('#btn1').click(function () {
            $('#top12').animate({
                height:1950
            },500);
            $('#btn1').hide(200);
        });
        $('#btn2').click(function () {
            $('#top12').animate({
                height:1000
            },500);
            $('#btn1').show(200);
        });
        $('#joy').click(function () {
            $('html,body').stop().animate({
                scrollTop :'+=1300'
            },500);
        });
        
    </script>
</body>
</html>