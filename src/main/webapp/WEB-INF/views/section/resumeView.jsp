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
main {
	margin-bottom: 100px;
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

	<main>
		<div>
			<div class="container" style="width: 85%;">

				<!--이력서 공개 여부 자동으로 공개로 해놓음 -->

				<div class="mt-5">
					<label class="form-control w-100" style="text-align: center;">${resume.resumeTitle}</label>
					<input type="hidden" name="publish" value=1>
				</div>

				<div class="row">
					<!-- 왼쪽에 이미지 -->
					<div class="col-md-4 mt-5">
						<img src="${resumeFile.filePath}" id="imagePreview"
							style="width: 250px; height: 310px;"
							class="mb-2 border border-tertiary">
					</div>

					<!-- 오른쪽에 입력 필드 -->
					<div class="col-md-8 mt-5">
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">이름</span> <label
								class="form-control">${person.personName}</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">생년월일</span> <label
								class="form-control">${person.personBirth}</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">연락처</span> <label
								class="form-control">${person.personPhone}</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">주소</span> <label
								class="form-control">${person.personAddress}</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">학력</span> <label
								class="form-control">${person.personEducation}</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">이메일</span> <label
								class="form-control">${person.userEmail}</label>
						</div>
					</div>
				</div>
			</div>





			<div class="container" style="width: 85%;">
				<div class="row justify-content-center ">

					<div class="input-group mb-3">

						<h5>포트폴리오 주소</h5>
						<label class="form-control w-100" name="portfolio">${resume.portfolio}</label>
					</div>

					<div class="mb-3">
						<label for="skills" class="form-label">기술스택</label>
						<div class="mx-auto row" id="skills">

							<c:forEach var="skill" items="${skill}">
								<div class="col-auto">
									<label class="btn btn-outline-primary">${skill.skillName}</label>
								</div>
							</c:forEach>

							<input type="hidden" id="defaultSkillIdx" name="skillIdx"
								value="0">
						</div>
					</div>


					<br>

					<div style="margin-top: 15px;">
						<h4>자기소개</h4>
						<textarea name="resumeComment" class="w-100" rows="10" readonly>${resume.resumeComment }</textarea>
					</div>
					<div class="d-flex mt-4 justify-content-center">
						<div class="px-2">
							<c:if
								test="${sessionScope.isLoggedIn && sessionScope.userType == 2}">
								<!-- 로그인 상태고 userType이 2일 때 보여질 부분 -->
								<a href="/resumeEditForm?resumeIdx=${resume.resumeIdx}"
									id="edit-resume" class="btn btn-primary">수정하기</a>
							</c:if>

							<!-- 로그인 상태에 관계없이 항상 보여질 부분 -->
							<a href="/resumes" id="cancel-resume" class="btn btn-primary">목록으로</a>
						</div>
					</div>
				</div>

			</div>
			</form>
		</div>
	</main>

	<%@include file="/WEB-INF/layouts/footer.jsp"%>

	<script src="/js/bootstrap.bundle.min.js"></script>

</body>
</html>