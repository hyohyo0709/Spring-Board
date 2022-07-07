<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 작성</title>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script type="text/javascript">
  $(document).ready(function(){
	 $('#btnList').click(function() {
		 $('#frm').attr("action", "list.sb");/* 요청할 값  */
		 $('#frm').submit(); /*요청하는 행위  */
		
	})
	
	$('#btnSave').click(function() {
		$('[name=content]').val(
				$('[name=content]').val().replace(/\n/gi, '<br/>'));
		
		$('#frm').attr('action','writesave.sb').submit();
	})
	
	$('#filepath').change(function() {
		if(this.files[0].size>100000000){
			alert('1GB 이하만 가능!');
			$(this).val('');
			return false;
		}
	})
	 
  });
</script>

</head>
<body>
	<form name="frm" id="frm" method="post"
	      enctype="multipart/form-data">

		<table>
			<tr>
				<td width="20%" align="center">글쓴이</td>
				<td><input type="text" name="writer" size="10" maxlength="10" /></td>
			</tr>

			<tr>
				<td width="20%" align="center">Email</td>
				<td><input type="text" name="email" size="30" maxlength="30" /></td>
			</tr>

			<tr>
				<td width="20%" align="center">제목</td>
				<td> 
				<c:if test="${dto!=null }">답변</c:if>
				<input type="text" name="subject" size="40" /></td>
			</tr>

			<tr>
				<td width="20%" align="center">내용</td>
				<td><textarea name="content" rows="13" cols="40"></textarea></td>
			</tr>

			<tr>
				<td width="20%" align="center">첨부파일</td>
				<td><input type="file" name="filename" id="filepath"/>
			</tr>
		</table>
		
		<!--답변글일 때  -->
		<c:if test="${dto!=null }">
<!--밑에 힘든 해놓은것은 관리자 모드임. 개발 중에 잘 들어갔는지 확인 하기 위해  -->
<input type="hidden" name="num" id="num" value="${dto.num }"/>
<input type="hidden" name="currentPage" id="currentPage" value="${currentPage }"/>
<input type="hidden" name="ref" value="${dto.ref }"/>
<input type="hidden" name="re_step" value="${dto.re_step }"/>
<input type="hidden" name="re_level" value="${dto.re_level }"/>
		</c:if>
		<input type="button" id="btnList" value="리스트" /> 
		<input type="button" id="btnSave" value="저장" />
	</form>
</body>
</html>










