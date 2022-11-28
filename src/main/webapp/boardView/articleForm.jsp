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
<title>글쓰기 창</title>
<style type="text/css">
    #title {
        border-top: 2px solid #ccc ;
    }
    td {
        border-bottom: 2px solid #ccc;
        padding: 20px;
    }
    tr {
        
        height: 80px;
    }
    .btn {
        background-color: white;
        font-weight: bold;
        padding: 8px 10px;
        cursor: pointer;
    }
    .btn:hover {
        background-color: #a4a1ee;
        font-weight: bold;
        
    }

    
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function listView(obj) {
		obj.action="${contextPath}/board/listArticles.do";
		obj.submit();
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
<body> <!-- enctype="multipart/form-data" 파일 업로드 하는 기능 꼭 필요함 -->
	<h2 align="center">글쓰기</h2>
	<form name="articleForm" action="${contextPath}/board/addArticle.do"
	 method="post" enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td align="center" id="title">제목</td>
				<td id="title" colspan="2"><input type="text" size="50" name="title" style="height:30px;"></td>
			</tr>
			<t>
				<td align="center">내용</td>
				<td colspan="2">
					<textarea rows="10" cols="70" maxlength="4000" name="content"></textarea>
				</td>
			</tr>
			<tr>
				<td align="center">이미지파일 첨부</td>
				<td ><input type="file" name="imageFileName" onchange="readURL(this)"></td>
				<td><img id="preview" src="#" width="200" height="200"></td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<input class="btn" type="submit" value="글쓰기">
					<input class="btn" type="button" value="목록보기" onclick="listView(this.form)">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>