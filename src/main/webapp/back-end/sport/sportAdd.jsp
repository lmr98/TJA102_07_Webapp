<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>

<%@ page import="java.util.*"%>
<%@ page import="com.tibafit.sport.model.*"%>

<%
// 見com.tibafit.sport.controller.SportServlet.java，存入req的sportVO物件 (此為輸入格式有錯誤時的sportVO物件)
SportVO sportVO = (SportVO) request.getAttribute("sportVO");
String rawSportMets = (String) request.getAttribute("rawSportMets");
String rawSportCalorie = (String) request.getAttribute("rawSportCalorie");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[新增] 系統運動資料 - sportAdd.jsp</title>

<style>
* {
	box-sizing: border-box;
	font-size: 16px;
}

html, body {
	width: 100%;
	height: 100%;
	margin: 0;
}

.uni-subject-block {
	width: 100%;
	height: fit-content;
	background-color: lightblue;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 20px;
}

.uni-subject-block-l {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	padding: 0px 30px;
}

.uni-subject-text {
	font-size: 20px;
	font-weight: bold;
}

.uni-turn-a {
	font-weight: 600;
	padding-top: 10px;
}

.uni-form-block {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	padding: 20px;
}

.uni-send-btn {
	width: 100%;
}



table {
	width: 600x;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 10px;
	height: 20px;
}

.required-text {
	color: red;
	font-size: 12px;
	font-weight: bold;
	padding-right: 1px;
}
</style>

</head>
<body bgcolor='white'>

	<div class="uni-subject-block">
		<img src="<%=request.getContextPath()%>/resources/images/tomcat.png"
			width="130" height="100" border="0">
		<div class="uni-subject-block-l">
			<div class="uni-subject-text">[新增] 系統運動資料</div>
			<div>~ sportAdd.jsp ~</div>
			<a class="uni-turn-a"
				href="<%=request.getContextPath()%>/back-end/sport/sportMainPage.jsp">
				回首頁 </a>
		</div>
		<img
			src="<%=request.getContextPath()%>/resources/images/tomcat_reverse.png"
			width="130" height="100" border="0">

	</div>

	<div class="uni-form-block">
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		
		<hr/>
		<div class="uni-subject-text">請填寫下列新增資料</div>
		<hr/>

		<FORM METHOD="post"
			ACTION="<%=request.getContextPath()%>/sport/sport_do" name="form1">
			<table>
				<tr>
					<td><span class="required-text">*</span>運動名稱:</td>
					<td><input type="TEXT" name="sportName" placeholder="請填寫運動名稱"
						value="<%=(sportVO == null || sportVO.getSportName() == null) ? "" : sportVO.getSportName()%>"
						size="45" /></td>
				</tr>
				<tr>
					<td>運動描述:</td>
					<td><input type="TEXT" name="sportDescription"
						placeholder="請填寫運動描述"
						value="<%=(sportVO == null || sportVO.getSportDescription() == null) ? "" : sportVO.getSportDescription()%>"
						size="45" /></td>
				</tr>
				<tr>
					<td><span class="required-text">*</span>運動強度:</td>
					<td><input type="TEXT" name="sportMets"
						placeholder="請填正數，可至小數第二位"
						value="<%=(rawSportMets == null) ? "" : rawSportMets%>" size="45" /></td>
				</tr>
				<tr>
					<td><span class="required-text">*</span>估算消耗熱量 <br> (60kg，1hr):</td>
					<td><input type="TEXT" name="sportEstimatedCalories"
						placeholder="請填正整數"
						value="<%=(rawSportCalorie == null) ? "" : rawSportCalorie%>"
						size="45" /></td>
				</tr>
			</table>
			<br>
			<input type="hidden" name="action" value="insert">
			<input class="uni-send-btn" type="submit" value="送出新增">
		</FORM>
	</div>

</body>
</html>