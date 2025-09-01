<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>系統運動-主頁</title>

<style>
* {
	box-sizing: border-box;
}

html, body {
	width: 100%;
	height: auto;
	margin: 0;
	background-color: #e0e0e0;
}

.uni-page {
	width: 100%;
	height: 100%;
}

.uni-page-block {
	width: 100%;
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
}

.uni-content-block {
	width: 600px;
	height: fit-content;
	background-color: #ffffff;
	padding: 16px;
}

.uni-subject-block {
	width: 100%;
	height: fit-content;
	background-color: lightblue;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	padding: 10px 0px;
}

.uni-subject-text {
	font-size: 20px;
	font-weight: bold;
}

.uni-error-msg-text {
	font-size: 12px;
	font-weight: bold;
	color: red;
}
</style>
</head>
<body>
	<div class="uni-page">
		<div class="uni-page-block">
			<div class="uni-content-block">
				<div class="uni-subject-block">
					<img
						src="<%=request.getContextPath()%>/resources/images/tomcat_think.png"
						width="60" border="0">
					<div class="uni-subject-text">Sport 系統運動 (MVC)</div>
					<div>~ Hello ~</div>
				</div>

				<br>
				<hr>
				<div class="uni-subject-text">全部查詢</div>

				<ul>
					<li><a
						href='<%=request.getContextPath()%>/back-end/sport/sportListAll.jsp'>系統運動清單
							(全)</a></li>
				</ul>

				<hr>
				<div class="uni-subject-text">條件查詢</div>

				<ul>
					<li>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/sport/sport_do">
							<div>
								<b>運動編號 (例: 2，精確) :</b>
							</div>
							<input type="text" name="sportId"> <input type="hidden"
								name="action" value="getOne_For_Display"> <input
								type="submit" value="查詢">
						</FORM> <%-- 錯誤表列 --%> <c:if test="${not empty idErrorMsgs}">
							<c:forEach var="message" items="${idErrorMsgs}">
								<div class="uni-error-msg-text">${message}</div>
							</c:forEach>
						</c:if> <br>
					</li>

					<jsp:useBean id="sportSvc" scope="page"
						class="com.tibafit.sport.model.SportService" />
					<li>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/sport/sport_do">
							<div>
								<b>運動資料狀態:</b>
							</div>

							<select size="1" name="sportDataStatus">
								<c:forEach var="sportDataStatus"
									items="${sportSvc.sportDataStatusOptions}">
									<option value="${sportDataStatus.codeNum}">${sportDataStatus.displayName}
								</c:forEach>
							</select> <input type="hidden" name="action"
								value="getPartial_For_Display"> <input type="submit"
								value="查詢">
						</FORM> <%-- 錯誤表列 --%> <c:if test="${not empty statusErrorMsgs}">
							<c:forEach var="message" items="${statusErrorMsgs}">
								<div class="uni-error-msg-text">${message}</div>
							</c:forEach>
						</c:if> <br>
					</li>

					<li>
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/sport/sport_do">
							<div>
								<b>運動名稱 (例: 跑步，模糊):</b>
							</div>
							<input type="text" name="keyword"> <input type="hidden"
								name="action" value="getNameFuzzy_For_Display"> <input
								type="submit" value="查詢">
						</FORM> <%-- 錯誤表列 --%> <c:if test="${not empty nameErrorMsgs}">
							<c:forEach var="message" items="${nameErrorMsgs}">
								<div class="uni-error-msg-text">${message}</div>
							</c:forEach>
						</c:if> <br>
					</li>
				</ul>

				<hr>
				<div class="uni-subject-text">運動管理</div>

				<ul>
					<li><a
						href='<%=request.getContextPath()%>/back-end/sport/sportAdd.jsp'>新增系統運動</a></li>
				</ul>

			</div>
		</div>


	</div>


</body>
</html>