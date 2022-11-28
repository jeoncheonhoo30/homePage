<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세 보기</title>
<style type="text/css">
	#form {
		top:100px;
	}
	
	#btn_modify {
		display: none;
	}
	.btn {
		background-color: white;
        font-weight: bold;
        padding: 8px 10px;
        cursor: pointer;
        margin-top: 30px;
	}
	.btn:hover {
		background-color: #a4a1ee;
        font-weight: bold;
	}
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function backToList(obj) {//리스트로 돌아가는 함수
		obj.action="${contextPath}/board/listArticles.do";
		obj.submit();
	}
	function fn_enable(obj) {//비활성화를 활성화로 
		document.getElementById("id_title").disabled=false;
		document.getElementById("id_content").disabled=false;
		let imgName=document.getElementById("id_imageFileName");
		if(imgName != null) {
			imgName.disabled=false;
		}
		document.getElementById("btn_modify").style.display="block";
		document.getElementById("all_btn").style.display="none";
	}
	function fn_modify_article(obj) { //수정 반영하기
		obj.action="${contextPath}/board/modArticle.do";
		obj.submit();
	}
	//글 삭제
	function fn_remove_article(url, articleNo) {
		let newForm=document.createElement("form");
		newForm.setAttribute("method","post");
		newForm.setAttribute("action",url);
		let articleNoInput=document.createElement("input");
		articleNoInput.setAttribute("type","hidden");
		articleNoInput.setAttribute("name","articleNo");
		articleNoInput.setAttribute("value",articleNo);
		newForm.appendChild(articleNoInput);
		document.body.appendChild(newForm);
		newForm.submit();
	}
	//답글쓰기
	function fn_reply_form(url, parentNo) {
		let reForm=document.createElement("form"); //리폼이라는 폼을 생성
		reForm.setAttribute("method","post");
		reForm.setAttribute("action",url);
		let parentNoInput=document.createElement("input");
		parentNoInput.setAttribute("type","hidden");
		parentNoInput.setAttribute("name","parentNo");
		parentNoInput.setAttribute("value",parentNo);
		reForm.appendChild(parentNoInput);
		document.body.appendChild(reForm);
		reForm.submit();
	}
	//제이쿼리로 이미지파일 첨부시 미리보기 기능 구현
	function readURL(input) {
		if(input.files && input.files[0]) {
			let reader=new FileReader();
			reader.onload=function (event) {
				$('#preview').attr('src',event.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>
</head>
<body>
	<form id="form" name="frmArticle" method="post" enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td width="150" align="center" bgcolor="#dad9ff">글번호</td>
				<td>
					<input type="text" value="${article.articleNo}" disabled>
					<input type="hidden" name="articleNo" value="${article.articleNo}"> 
				</td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#dad9ff">작성자 아이디</td>
				<td><input type="text" name="id" 
				value="${article.id}" disabled></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#dad9ff">제목</td>
				<td><input type="text" name="title" id="id_title"
				value="${article.title}" disabled></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#dad9ff">내용</td>
				<td>
					<textarea rows="20" cols="50" name="content" id="id_content" disabled>${article.content}</textarea>
				</td>
			</tr>		
			<c:if test="${not empty article.imageFileName}"><%-- 이미지가 있을때만 수행 --%>
				<tr>
					<td width="150" align="center" bgcolor="#dad9ff" rowspan="2">이미지</td>
					<td>
						<input type="hidden" name="originalFileName" 
						value="${article.imageFileName}">
						<img src="${contextPath}/download.do?articleNo=${article.articleNo}&
						imageFileName=${article.imageFileName}" id="preview"><br>
					</td>
				</tr>
				<tr>
					<td>
						<input colspan="2" type="file" name="imageFileName" 
						id="id_imageFileName" disabled onchange="readURL(this);">
					</td>
				</tr>
			</c:if>	
				<tr>
					<td width="150" align="center" bgcolor="#dad9ff">등록일자</td>
					<td><input type="text" 
					value="<fmt:formatDate value="${article.writeDate}"/>" disabled></td>
				</tr>
				<tr id="btn_modify">
					<td colspan="2" align="center">
						<input type="button" value="수정반영하기" onclick="fn_modify_article(frmArticle)">
						<input type="button" value="취소" onclick="backToList(frmArticle)"> <!-- 리스트로 돌아갈때 폼이름을 준것 --> 
					</td>
				</tr>
				<tr id="all_btn">
					<td colspan="2" align="center">
						<input class="btn" type="button" value="수정하기" onclick="fn_enable(this.form)">
						<input class="btn" type="button" value="삭제하기" 
						onclick="fn_remove_article('${contextPath}/board/removeArticle.do',${article.articleNo})">
						<input class="btn" type="button" value="리스트로 돌아가기" onclick="backToList(this.form)"> <!-- 리스트로 돌아갈때 이 폼으로-->
						<input class="btn" type="button" value="답글쓰기"
						 onclick="fn_reply_form('${contextPath}/board/replyForm.do',${article.articleNo})">
					</td>
				</tr>
			
		</table>	
	</form>
</body>
</html>