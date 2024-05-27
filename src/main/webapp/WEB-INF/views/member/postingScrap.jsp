<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스크랩 기능 구현</title>
<link rel="icon" type="image/x-icon" href="/images/favicon.png">
<link href="/css/bootstrap.min.css" rel="stylesheet" />

<style>
/* 스타일 설정 */
footer {
	position: fixed;
	bottom: 0;
	width: 100%;
	padding: 20px 0;
	text-align: center;
}

header {
	background-color: #e0f7fa;
	margin-bottom: 20px;
}

/* 직접 스타일 추가하기 */
.tab-panel {
	width: 60%;
	margin: 0 auto;
	margin-top: 30px;
	margin-left: 450px;
	padding: 0;
	box-sizing: border-box;
}

#scrap {
	width: 100%;
	border-collapse: collapse;
	margin-top: 40px;
}

#scrap th, #scrap td {
	border: 1px solid #000;
	text-align: center;
	padding: 10px;
	width: 100px; /* 셀의 너비를 150px로 설정 (필요에 따라 조정 가능) */
	height: 50px; /* 셀의 높이를 50px로 설정 (필요에 따라 조정 가능) */
}

#scrap th {
	background-color: #e0f7fa;
}
</style>
</head>
<body>

	<main>
		<%@include file="/WEB-INF/layouts/header.jsp"%>
		<input type="hidden" value="${personIdx}" id="personIdx">
		<h3 style="text-align: center;">관심기업</h3>
		<div class="container" id="book-id">
			<table id="scrap">
				<thead>
					<tr>
						<th>회사명</th>
						<th>공고 제목</th>
						<th>분야</th>
						<th>마감일자</th>
						<th>스크랩</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach var="bookmark" items="${bookmarks}">
						<tr>
							<td>${bookmark.companyName}</td>
							<td>${bookmark.postingTitle}</td>
							<td>${bookmark.jobType}</td>
							<td>${bookmark.postingDeadline}</td>
							<td><button class="btn btn-outline-secondary scrapBtn">
									<svg
										class="w-6 h-6 text-gray-800 dark:text-white scrapSvg me-2"
										data-posting-idx="${bookmark.postingIdx}" aria-hidden="true"
										xmlns="http://www.w3.org/2000/svg" width="24" height="24"
										fill="none" viewBox="0 0 24 24">
                            		<path stroke="currentColor"
											stroke-linecap="round" stroke-linejoin="round"
											stroke-width="2"
											d="m17 21-5-4-5 4V3.889a.92.92 0 0 1 .244-.629.808.808 0 0 1 .59-.26h8.333a.81.81 0 0 1 .589.26.92.92 0 0 1 .244.63V21Z" /></svg>

									<span>스크랩
								</button>
								</span></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<%@include file="/WEB-INF/layouts/footer.jsp"%>
	</main>
	<script src="/js/bootstrap.bundle.min.js"></script>
	<script src="/js/postingScrap.js"></script>
	<script>
		
	</script>

</body>
</html>