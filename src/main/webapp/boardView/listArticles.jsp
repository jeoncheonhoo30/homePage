<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="articleList" value="${articleMap.articleList}"/>
<c:set var="totArticles" value="${articleMap.totArticles}"/>
<c:set var="section" value="${articleMap.section}"/>
<c:set var="pageNum" value="${articleMap.pageNum}"/>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 목록창</title>
<style type="text/css">
	.sel_page {
		color:red;
	}
	a {
		text-decoration: none;
		color:black;
	}
	.board {
		position: relative;
		top: 100px;
	}
	th {
		padding: 10px;
	}
	td{
		padding: 5px;
		border-bottom: 1px solid #ccc;
	}
	p {
		text-align: center;
		margin-bottom: 50px;
	}
	.btns {
		font-weight: bold;
	}
	
	footer {
	    width: 100%;
	    height: 150px;
	    background-color: #dad9ff;
	    color: black;
	    margin-top: 240px;
	    padding: 20px;
	    text-align: center;
}
	footer ul {
	    margin-top: 20px;
	    float: left;
	    margin-left: 620px;
	    list-style: none;
	}
	footer ul li {
	    float: left;
	    padding: 10px;
	    margin: 0 10px;
	}
	footer ul li a {
	    color: black;
	    font-weight: bold;
	}
	footer address {
	    font-style: normal;
	    font-size: 1em;
	    margin-top: 80px;
	}
</style>
</head>
<body>
	
        <article class="board">
			<table align="center" width="50%">
				<tr align="center" bgcolor="#dad9ff">
					<th>번호</th><th>작성자</th><th>제목</th><th>작성일</th>
				</tr>
				<c:choose>
					<c:when test="${empty articleList}">
						<tr>
							<td align="center" colspan="4">등록된 글이 없습니다.</td>
						</tr>
					</c:when>
					<c:when test="${not empty articleList}">
						<c:forEach var="article" items="${articleList}" varStatus="articleNum">
							<tr align="center">
								<td width="5%">${articleNum.count}</td>
								<td width="7%">${article.id}</td>
								<td width="20%" align="left">
									<c:choose>
										<c:when test="${article.level > 1}"> <%-- 1보다 크면 댓글이 있다는 뜻 --%>
											<c:forEach begin="1" end="${article.level}" step="1">
												<span style="padding-left:20px"></span>
											</c:forEach>
											[답변]<a href="${contextPath}/board/viewArticle.do?
											articleNo=${article.articleNo}">${article.title}</a><%-- 답변글 --%>
										</c:when>
										<c:otherwise>
											<a href="${contextPath}/board/viewArticle.do?
											articleNo=${article.articleNo}">${article.title}</a><%-- 본글 --%>
										</c:otherwise>
									</c:choose>
								</td>
								<td width="10%"><fmt:formatDate value="${article.writeDate}"/></td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
			</table>
			<br>
			<div align="center">
				<c:if test="${totArticles != null}">
					<c:choose><%-- 전체 글이 100개 초과했을때 --%>
						<c:when test="${totArticles > 100}"> 
							<c:forEach var="page" begin="1" end="10" step="1">
								<c:if test="${section > 1 && page==1}">
									<a href="${contextPath}/board/listArticles.do?section=
									${section-1}&pageNum=${(section-1)*10+1}"> prev </a>
								</c:if>
								<a href="${contextPath}/board/listArticles.do?section=
								${section}&pageNum=${page}">${(section-1)*10+page}</a>
								<c:if test="${page==10}"> <%-- 10페이지 왔을때 다음 페이지로 이동--%>
									<a href="${contextPath}/board/listArticles.do?section=
									${section+1}&pageNum=${section*10+1}"> next </a>
								</c:if>
							</c:forEach>
						</c:when>
						<c:when test="${totArticles == 100}">
							<c:forEach var="page" begin="1" end="10" step="1">
								<a href="#">${page}</a>
							</c:forEach>
						</c:when>
						<c:when test="${totArticles == 100}">
							<c:forEach var="page" begin="1" end="${totArticles/10+1}" step="1">
								<c:choose>
									<c:when test="${page==pageNum}">
										<a class="sel_page" href="${contextPath}/board/listArticles.do?section=
										${section}&pageNum=${page}">${page}</a>
									</c:when>
									<c:otherwise>
										<a class="noSel_page" href="${contextPath}/board/listArticles.do?section=
										${section}&pageNum=${page}">${page}</a>								
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
					</c:choose>
				</c:if>
			</div>
			<br>
			<div align="center" >
				<p class="btns"><a href="${contextPath}/board/articleForm.do">글쓰기</a></p>
				<p class="btns"><a href="${contextPath}/index.jsp">홈페이지로</a></p>
			</div>
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
</body>
</html>