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
			<form id="postingForm" method="post" action="/postingWrite">
				<div class="container" style="width: 85%;">
					<input type="hidden" name="postingIdx">

					<div class="mt-5">
						<input type="text" class="form-control w-100" name="postingTitle"
							style="text-align: center;" placeholder="공고 제목을 입력해주세요" required="required">
					</div>

					<div class="row">
						<!-- 왼쪽 필드 -->
						<div class="col-md-6 mt-5">
							<div class="input-group mb-3">
								<span class="input-group-text w-25 justify-content-center"
									style="background-color: #e0f7fa;">근무형태</span> <input
									type="text" name="empType" class="form-control" required>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text w-25 justify-content-center"
									style="background-color: #e0f7fa;">경력</span> <input type="text"
									name="experience" class="form-control" required>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text w-25 justify-content-center"
									style="background-color: #e0f7fa;">근무지역</span> <input
									type="text" name="postingAddress" class="form-control" required>
							</div>
						</div>

						<!-- 오른쪽 필드 -->
						<div class="col-md-6 mt-5">
							<div class="input-group mb-3">
								<span class="input-group-text w-25 justify-content-center"
									style="background-color: #e0f7fa;">근무시간</span> <label
									class="form-control"><input type="time"
									name="startTime" value="09:00" required> ~ <input
									type="time" name="endTime" value="18:00" required></label>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text w-25 justify-content-center"
									style="background-color: #e0f7fa;">직무</span> <input type="text"
									name="jobType" class="form-control" required>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text w-25 justify-content-center"
									style="background-color: #e0f7fa;">연봉</span> <input type="text"
									name="salary" class="form-control" required>
							</div>

						</div>
					</div>
				</div>





				<div class="container mt-3" style="width: 85%;">
					<div class="row justify-content-center ">

						<div class="mb-3">
							<label for="skills" class="form-label">기술스택</label>
							<div class="mx-auto row" id="skills">

								<c:forEach var="skill" items="${skill}">
									<div class="col-auto">
										<input type="checkbox" class="btn-check"
											id="skill_${skill.skillIdx}" value="${skill.skillIdx}"
											name="skillIdx" autocomplete="off"> <label
											class="btn btn-outline-primary" for="skill_${skill.skillIdx}">${skill.skillName}</label>
									</div>
								</c:forEach>

							</div>
						</div>

						<div class="input-group mb-3">
							<h5>공고 마감일</h5>
							<div class="w-100" style="text-align: left;">
								<input type="date" id="postingDeadline" name="postingDeadline"
									required>
							</div>


						</div>

						<br>

						<div style="margin-top: 15px;">
							<h4>기업소개</h4>
							<textarea name="resumeComment" class="w-100" rows="18"
								style="resize: none;" readonly>

	기업 이름	  : 	${company.companyName}

	기업 대표	  : 	${company.companyRepName }

	기업 직종	  : 	${company.companySector }

	기업 주소 	  : 	${company.companyAddress }

	기업 규모	  : 	${company.companySize }

	직원 수 	  : 	${company.companyEmp }

	설립 연도	  : 	${company.companyYear }

	기업 전화번호 : 	${company.companyPhone }
</textarea>
						</div>

						<br>

						<div style="margin-top: 15px;">
							<h4>공고소개</h4>
							<textarea name="postingComment" class="w-100" rows="10" required></textarea>
						</div>

						<div class="d-flex mt-4 justify-content-center">
							<div class="px-2">
								<button type="submit" id="save-write" class="btn btn-primary">공고등록</button>
								<a href="/postings" id="cancel-posting" class="btn btn-primary">목록으로</a>
							</div>
						</div>
					</div>
					<input type="hidden" value="${userIdx}" name="userIdx">
				</div>
			</form>
		</div>
	</main>

	<%@include file="/WEB-INF/layouts/footer.jsp"%>

	<script src="/js/bootstrap.bundle.min.js"></script>

	<script>
		document.getElementById("save-write").addEventListener("click",
				function(event) {
					// 스킬을 선택했는지 확인하는 함수 호출
					if (!validateSkills()) {
						event.preventDefault(); // 선택되지 않은 경우 폼 제출 방지
					} else {
						confirmSubmission(event); // 선택된 경우 확인 다이얼로그 표시
					}
				});

		function validateSkills() {
			// 스킬 체크박스 요소들을 가져옵니다.
			var skills = document.getElementsByName("skillIdx");
			var skillSelected = false;

			// 모든 스킬 체크박스를 확인하여 하나라도 선택된 것이 있는지 확인합니다.
			for (var i = 0; i < skills.length; i++) {
				if (skills[i].checked) {
					skillSelected = true;
					break;
				}
			}

			// 선택된 스킬이 없는 경우 알림을 표시하고 false 반환
			if (!skillSelected) {
				alert("기술스택을 선택하세요.");
				return false;
			}
			return true;
		}

		// 공고를 등록할 때 확인 다이얼로그 표시
		function confirmSubmission(event) {
			if (confirm("공고를 등록 하시겠습니까?")) {
				// 확인 버튼을 클릭한 경우 폼 제출
				return true;
			} else {
				// 취소 버튼을 클릭한 경우 폼 제출 취소
				event.preventDefault();
				return false;
			}
		}

		document.getElementById("cancel-posting").addEventListener("click",
				confirmCancel);

		function confirmCancel(event) {
			event.preventDefault();
			if (confirm("목록으로 돌아가시겠습니까?")) {
				window.location.href = this.getAttribute("href");
			}
		}
	</script>

	<!-- 공고 마감일 현재로 value값 설정 -->
	<script>
		//현재 날짜를 가져옵니다.
		const today = new Date();

		// 날짜를 YYYY-MM-DD 형식으로 변환합니다.
		const formattedDate = today.toISOString().split('T')[0];

		// 변환한 날짜를 input 태그의 value로 설정합니다.
		document.getElementById('postingDeadline').value = formattedDate;
	</script>

</body>
</html>