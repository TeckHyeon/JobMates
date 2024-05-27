<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<link rel="icon" type="image/x-icon" href="/images/favicon.png">
<link href="/css/bootstrap.min.css" rel="stylesheet" />
<style type="text/css">





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


#section {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    min-height: 40vh;
    text-align: center;
    margin-bottom: 100px;
}

#result {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 80%;
}




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
<h3 class="mt-3" style="text-align:center;"> 추천 공고 </h3>
<hr>
<section id="section">
	<div id="result" class="ms-5">
		<c:if test="${not empty posting}">
			<div class="d-flex flex-wrap">
				<input type="hidden" value="${person.personIdx}" id="personIdx">
				<!-- flex-wrap 추가 가능 -->
				<c:forEach var="posting" items="${posting}">
					<div class="me-5 detail-div" data-posting-idx="${posting.postingIdx}">
						<!-- 각 포스트를 감싸는 div 추가 -->
						<img alt="기업 로고" style="display: block; margin-left: auto; margin-right: auto; height:200px; width:250px;" src="${posting.filePath}" class="mb-3">
						<div class="d-flex justify-content-between mb-3">
							<!-- flex-direction 변경 및 가운데 정렬 -->
							<div class="d-flex flex-column" style="text-align:left; width:200px; margin-left:3%;">
								<span>${ posting.postingTitle }</span> <span>${ posting.companyName }</span>
							</div>
							<c:choose>
								<c:when test="${sessionScope.isLoggedIn}">
							<button class="btn btn-secondary scrapBtn d-flex align-items-center ms-3" style="background-color: transparent; border-color: transparent;">
                           <svg
                              class="w-10 h-10 text-gray-800 text-dark scrapSvg end-0"
                              data-posting-idx="${posting.postingIdx}"
                              aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                              width="50" height="50" fill="none" viewBox="0 0 24 24">
                                  <path    stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                 d="m17 21-5-4-5 4V3.889a.92.92 0 0 1 .244-.629.808.808 0 0 1 .59-.26h8.333a.81.81 0 0 1 .589.26.92.92 0 0 1 .244.63V21Z" />
                                   </svg>
                                   </button>
								</c:when>
							</c:choose>
	 
						</div>
					</div>
				</c:forEach>
				
			</div>
			<script src="/js/postingScrap.js"></script>

			</c:if>
		<c:if test="${empty posting}">
			<p>추천 공고가 없습니다.</p>
		</c:if>
	</div>
</section>
<%@include file="/WEB-INF/layouts/footer.jsp"%>

<script src="/js/bootstrap.bundle.min.js"></script>

</body>
</html>