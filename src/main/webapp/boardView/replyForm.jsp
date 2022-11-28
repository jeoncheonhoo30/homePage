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
<title>답글쓰기 페이지</title>
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
	function listView(obj) { //취소하면 글목록으로 돌아가기
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
<body>
	<h2 align="center">답글쓰기</h2>
	<form name="frmReply" action="${contextPath}/board/addReply.do"
	 method="post" enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td align="center">글쓴이 </td>
				<td><input type="text" value="<%=session.getAttribute("sessionID") %>" disabled></td>
			</tr>
			<tr>
				<td align="center">제목 </td>
				<td><input type="text" name="title" size="50" style="height:30px;"></td>
			</tr>
			<tr>
				<td align="center">내용 </td>
				<td colspan="2">
					<textarea rows="10" cols="70" maxlength="4000" name="content"></textarea>
				</td>
			</tr>
			<tr>
				<td align="center">이미지파일 첨부</td>
				<td><input type="file" name="imageFileName" onchange="readURL(this)"></td>
				<td><img id="preview" src="#" width="200" height="200"></td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input class="btn" type="submit" value="답글 반영하기">
					<input class="btn" type="button" value="취소" onclick="listView(this.form)">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>