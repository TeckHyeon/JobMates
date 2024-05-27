<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지원 현황 페이지</title>
<link rel="icon" type="image/x-icon" href="/images/favicon.png">
<link href="/css/bootstrap.min.css" rel="stylesheet" />
<style>
/* 추가적인 CSS 스타일링 */
footer {
	position: fixed;
	bottom: 0;
	width: 100%;
	padding: 20px 0;
	text-align: center;
}

header {
	background-color: #e0f7fa;
}
</style>

</head>
<body>
	<%@include file="/WEB-INF/layouts/header.jsp"%>
	<section id="section">
		<c:choose>
			<c:when test="${sessionScope.isLoggedIn}">
				<c:choose>
					<c:when test="${userType == 1}">
						<%@ include file="/WEB-INF/views/fragment/companyApply.jsp"%>
					</c:when>
					<c:when test="${userType == 2}">
						<%@ include file="/WEB-INF/views/fragment/personApply.jsp"%>
					</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>
				<%@ include file="/WEB-INF/views/fragment/personMain.jsp"%>
			</c:otherwise>
		</c:choose>


	</section>
	<%@include file="/WEB-INF/layouts/footer.jsp"%>
	<script src="/js/bootstrap.bundle.min.js"></script>
</body>

</html>