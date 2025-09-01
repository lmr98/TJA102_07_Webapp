<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.*"%>
<%@ page import="com.tibafit.sport.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
List<SportVO> list = (List<SportVO>) request.getAttribute("partialList");
// SportServlet java(Controller), 存入 req 的 sportVO物件
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>系統運動清單-部分</title>

<style>
* {
	box-sizing: border-box;
}

html, body {
	width: 100%;
	height: 100%;
	margin: 0;
}

.uni-page {
	background-color: #e0e0e0;
	width: 100%;
	height: 100%;
}

.uni-page-block {
	width: 100%;
	height: 100%;
}

.uni-content-block {
	width: 100%;
	height: 100%;
	background-color: #ffffff;
	padding: 16px;
	display: flex;
	flex-direction: column;
	justify-content: start;
	align-items: center;
}

.uni-f-bsis-top {
	width: 100%;
	height: fit-content;
	flex: 0 0 auto;
}

.uni-subject-block {
	width: 100%;
	background-color: lightblue;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	padding: 8px;
	background-color: lightblue;
}

.uni-subject-text {
	font-size: 20px;
	font-weight: bold;
}

.uni-be-sport-table-block {
	width: 100%;
	/* 	height: 100%; */
	flex: 1 1 0;
	overflow: auto;
}

.uni-btn-do {
	margin-right: 10px;
}

table#uni-be-sport-table {
	width: 100%;
	text-align: center;
	border-collapse: collapse;
}

table#uni-be-sport-table .uni-form-manual-column {
	display: flex;
	justify-content: center;
	align-items: center;
}

table#uni-be-sport-table th {
	color: #333333;
	/* 	border: 1px solid red; */
	padding: 10px;
	background-color: lightblue;
}

table#uni-be-sport-table td {
	color: #333333;
	border: 1px solid #e0e0e0;
	padding: 10px;
}
</style>
</head>
<body>
	<div class="uni-page">
		<div class="uni-page-block">
			<div class="uni-content-block">

				<div class="uni-f-bsis-top">
					<div class="uni-subject-block">
						<div class="uni-subject-text">系統運動清單 - 部分資料 -
							sportListPartial.jsp</div>
						<div>~ 此頁暫練習採用 Script 的寫法取值 ~</div>

						<br> <a
							href="<%=request.getContextPath()%>/back-end/sport/sportMainPage.jsp"><img
							src="<%=request.getContextPath()%>/resources/images/back1.gif"
							width="100" height="32" border="0">回首頁</a>
					</div>
				</div>

				<div class="uni-be-sport-table-block">
					<table id="uni-be-sport-table">
						<tr>
							<th>運動編號</th>
							<th>運動名稱</th>
							<th>描述</th>
							<th>強度</th>
							<th>估算消耗熱量 (60kg，1hr)</th>
							<th>等級</th>
							<th>資料狀態</th>
							<!-- <th>建立人員</th> -->
						</tr>
						<c:forEach var="sportVO" items="${partialList}">
							<tr>
								<td>${sportVO.sportId}</td>
								<td>${sportVO.sportName}</td>
								<td>${sportVO.sportDescription}</td>
								<td>${sportVO.sportMets}</td>
								<td>${sportVO.sportEstimatedCalories}</td>
								<td>${sportVO.sportLevel == "junior"? "初階" : sportVO.sportLevel == "senior"? "中階" : "高階" }</td>
								<td>${sportVO.sportDataStatus == 0? "下架" : sportVO.sportDataStatus == 1? "上架" : "刪除" }</td>
								<%-- <td>${sportVO.adminId}</td> --%>
							</tr>
						</c:forEach>
					</table>
				</div>

			</div>
		</div>
	</div>
</body>
</html>