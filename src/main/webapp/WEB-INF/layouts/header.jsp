<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<style>
@font-face {
    font-family: 'Binggrae';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/Binggrae.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

*{
font-family: 'Binggrae';
font-weight: bold;

}
</style>
</head>
<body>
	<header class="border-bottom">
		<div class="container-fluid">
			<div
				class="d-flex flex-wrap align-items-center justify-content-between">
				<div class="d-flex align-items-center" style="margin-left:10%;">
					<a href="/"
						class="d-flex align-items-center mb-2 mb-lg-0 link-body-emphasis text-decoration-none me-3">
						<img alt="로고" src="/images/Logo.png" style="width: 130px;">
					</a>
					<c:choose>
						<c:when test="${sessionScope.isLoggedIn}">
							<c:choose>
								<c:when test="${userType == 1}">
									<ul
										class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
										<li><a href="/resumeRecommend" class="nav-link px-2 text-dark">추천 인재</a></li>
										<li><a href="/postings" class="nav-link px-2 text-dark">공고
												관리</a></li>
										<li><a href="/applyPage" class="nav-link px-2 text-dark">채용 관리</a></li>
										<li><a href="/RScrapList" class="nav-link px-2 text-dark">관심 인재</a></li>
										<li><a href="/community"" class="nav-link px-2 text-dark">자유 게시판</a></li>
									</ul>
								</c:when>
								<c:when test="${userType == 2}">
									<ul
										class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
										<li><a href="/postingRecommend" class="nav-link px-2 text-dark">추천 공고</a></li>
										<li><a href="/resumes" class="nav-link px-2 text-dark">이력서
												관리</a></li>
										<li><a href="/applyPage" class="nav-link px-2 text-dark">지원 현황</a></li>

										<li><a href="/postingScrap" class="nav-link px-2 text-dark">관심 기업</a></li>

									

										<li><a href="/community" class="nav-link px-2 text-dark">자유
												게시판</a></li>
									</ul>
								</c:when>
							</c:choose>
						</c:when>
						<c:otherwise>
							<ul
								class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
								<li><a href="/personlogin" class="nav-link px-2 text-dark">추천
										공고</a></li>
								<li><a href="/personlogin" class="nav-link px-2 text-dark">이력서
										관리</a></li>
								<li><a href="/personlogin" class="nav-link px-2 text-dark">지원
										현황</a></li>
								<li><a href="/personlogin" class="nav-link px-2 text-dark">관심
										기업</a></li>
								<li><a href="/community" class="nav-link px-2 text-dark">자유
										게시판</a></li>
							</ul>
						</c:otherwise>
					</c:choose>

				</div>
				<div style="margin-right:10%;">
					<c:choose>
						<c:when test="${sessionScope.isLoggedIn}">
							<ul
								class="nav col-12 col-lg-auto me-3 mb-2 justify-content-center mb-md-0 ">
								<li><a href="/mypage" class="nav-link px-2 text-dark">마이페이지</a></li>
								<li><a href="/logout" class="nav-link px-2 text-dark">로그아웃</a></li>
							</ul>
						</c:when>
						<c:otherwise>
							<ul
								class="nav col-12 col-lg-auto me-3 mb-2 justify-content-center mb-md-0 ">
								<li><a href="/personlogin" class="nav-link px-2 text-dark">로그인</a></li>
								<li><a href="/join" class="nav-link px-2 text-dark">회원가입</a></li>
							</ul>
						</c:otherwise>
					</c:choose>

				</div>
			</div>
		</div>
	</header>
</body>
</html>