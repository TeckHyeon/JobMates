<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<link rel="icon" type="image/x-icon" href="/images/favicon.png">
<link href="/css/bootstrap.min.css" rel="stylesheet" />
<style>
.detail-div {
	cursor: pointer;
	margin-bottom: 20px;
	border:1px solid #ccc;
	transition: transform 0.3s ease, box-shadow 0.3s ease;
	}
.detail-div:hover{
	transform: scale(1.03);
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
.whole-posting{
margin-bottom:10%;
}
.each-postings {
	cursor: pointer;
	margin-bottom: 20px;
	border:1px solid #ccc;
	transition: transform 0.3s ease, box-shadow 0.3s ease;
	}
.each-postings:hover{
	transform: scale(1.03);
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
.search-box {
	position: relative;
	display: flex;
	align-items: center;
}

.scrapBtn {
	display: inline-block; /* 올바른 크기 조정을 위해 */
	cursor: pointer; /* 마우스 커서 변경 */
}

.search-box input {
	padding-left: 35px; /* 왼쪽에 아이콘을 위한 여백 */
	padding-right: 35px; /* 'X' 버튼을 위한 여백 */
}

.search-icon, .clear-icon {
	position: absolute;
	width: 20px;
	height: 20px;
	z-index: 10;
}

.search-icon {
	left: 10px;
	top: 50%;
	transform: translateY(-50%);
}

.clear-icon {
	right: 10px; /* 입력란 바깥쪽에 위치 */
	top: 50%;
	cursor: pointer;
	transform: translateY(-50%);
	display: none; /* 초기 상태에서 숨김 */
}

/* 입력란에 값이 있을 때만 X 아이콘 표시 */
.search-box input:not(:placeholder-shown) ~ .clear-icon {
	display: block;
}

#searchBox {
	position: fixed; /* 또는 absolute, 화면 상단에 고정 */
	top: 0; /* 화면 상단에서부터의 위치 */
	left: 230px; /* 화면 왼쪽에서부터의 위치 */
	height: 100%;
	/* 전체 화면 너비를 차지하도록 설정 */
	width: 300px;
	height: 100%;
	display: none;
	cursor: pointer;
	/* 필요한 추가 스타일 */
}
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
						<%@ include file="/WEB-INF/views/fragment/companyMain.jsp"%>
					</c:when>
					<c:when test="${userType == 2}">
						<%@ include file="/WEB-INF/views/fragment/personMain.jsp"%>
					</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>
				<%@ include file="/WEB-INF/views/fragment/personMain.jsp"%>
			</c:otherwise>
		</c:choose>


	</section>
	<%@include file="/WEB-INF/layouts/footer.jsp"%>
	<script src="/js/search.js"></script>
	<script src="/js/postingScrap.js"></script>
	<script src="/js/updatescrapSvgs.js"></script>
	<script src="/js/bootstrap.bundle.min.js"></script>
</body>

</html>