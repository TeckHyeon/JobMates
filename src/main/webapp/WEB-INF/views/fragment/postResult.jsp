<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
	<c:if test="${not empty posts}">
		<div class="d-flex flex-wrap whole-posting">
			<input type="hidden" value="${person.personIdx}" id="personIdx">
			<!-- flex-wrap 추가 가능 -->
			<c:forEach items="${posts}" var="post">
				<div class="me-5 detail-div"
					data-posting-idx="${post.postingDto.postingIdx}">
					<!-- 각 포스트를 감싸는 div 추가 -->
					<img alt="기업 로고" src="${post.companyFileDto.filePath}" class="mb-3" 
					style="display: block; margin-left: auto; margin-right: auto; width: 250px; height: 200px;">
					<div class="d-flex justify-content-between mb-3">
						<!-- flex-direction 변경 및 가운데 정렬 -->
						<div class="d-flex flex-column" style="text-align:left; width:200px; margin-left:3%;">
							<span>${ post.postingDto.postingTitle }</span> <span>${ post.companyDto.companyName }</span>
						</div>
						<c:choose>
							<c:when test="${sessionScope.isLoggedIn}">
								<button class="btn btn-primary scrapBtn" style="background-color: transparent; border-color: transparent;">
									<svg
										class="w-10 h-10 text-gray-800 dark:text-white text-dark scrapSvg end-0"
										data-posting-idx="${post.postingDto.postingIdx}"
										aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
										width="32" height="32" fill="none" viewBox="0 0 24 24">
	                            <path stroke="currentColor"stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
								d="m17 21-5-4-5 4V3.889a.92.92 0 0 1 .244-.629.808.808 0 0 1 .59-.26h8.333a.81.81 0 0 1 .589.26.92.92 0 0 1 .244.63V21Z" />
	                       		</svg>
								</button>
							</c:when>
						</c:choose>

					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${empty posts}">
		<p>검색 결과가 없습니다.</p>
	</c:if>
</body>
