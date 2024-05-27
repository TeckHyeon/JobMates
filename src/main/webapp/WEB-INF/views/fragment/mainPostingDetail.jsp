<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.search-box {
	position: relative;
	display: flex;
	align-items: center;
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
</style>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById('applyButton').addEventListener('click', async function() {
        const personIdx = this.getAttribute('data-person-idx');
        const postingIdx = this.getAttribute('data-posting-idx');

        try {
            const data = await checkApplyStatus(personIdx, postingIdx);
            handleApplyStatus(data, postingIdx);
        } catch (error) {
            alert('지원 상태를 확인할 수 없습니다. 잠시 후 다시 시도해주세요.');
            console.error('An error occurred:', error);
        }
    });
});

async function checkApplyStatus(personIdx, postingIdx) {
    const response = await fetch(`/applyCheck/` + personIdx + `/` + postingIdx, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    });

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }

    return response.json();
}

function handleApplyStatus(data, postingIdx) {
    if (data.message === "지원 가능합니다.") {
        openApplyWindow(postingIdx);
    } else if (data.message === "이미 해당 공고에 지원하셨습니다.") {
        if (confirm('이미 지원한 공고 입니다. 지원 현황을 확인 하겠습니까?')) {
            window.location.href = '/ApplyPage';
        }
    }
}

function openApplyWindow(postingIdx) {
    const screenWidth = window.screen.width;
    const screenHeight = window.screen.height;
    const windowWidth = screenWidth * 0.4;
    const windowHeight = screenHeight * 0.7;
    const left = (screenWidth - windowWidth) / 2;
    const top = (screenHeight - windowHeight) / 2;
    const options = 'width=' + windowWidth + ',height=' + windowHeight + ',left=' + left + ',top=' + top;
    window.open(`/applyForm/` + postingIdx, 'ApplyWindow', options);
}

</script>
</head>
<body>
	<main>
		<div>
			<div class="container" style="width: 85%;">

				<div class="mt-5">
					<label class="form-control w-100" style="text-align: center;">${posting.postingTitle}</label>
					<input type="hidden" name="postingDeadline"
						value="${posting.postingDeadline}">
				</div>
				<input type="hidden" value="${person.personIdx}" id="personIdx">
				<div class="row">
					<!-- 왼쪽 필드 -->
					<div class="col-md-6 mt-5">
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">근무형태</span> <label
								class="form-control">${posting.empType}</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">경력</span> <label
								class="form-control">${posting.experience}</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">근무지역</span> <label
								class="form-control">${posting.postingAddress }</label>
						</div>
					</div>

					<!-- 오른쪽 필드 -->
					<div class="col-md-6 mt-5">
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">근무시간</span> <label
								class="form-control">${posting.startTime} ~
								${posting.endTime}</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">직무</span> <label
								class="form-control">${posting.jobType}</label>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text w-25 justify-content-center"
								style="background-color: #e0f7fa;">연봉</span> <label
								class="form-control">${posting.salary }</label>
						</div>
					</div>
				</div>
			</div>

			<div class="container mt-3" style="width: 85%;">
				<div class="row justify-content-center ">

					<div class="mb-3">
						<h5 for="skills" class="form-label">기술스택</h5>
						<div class="mx-auto row" id="skills">

							<c:forEach var="skill" items="${skills}">
								<div class="col-auto">
									<label class="btn btn-outline-primary">${skill.skillName}</label>
								</div>
							</c:forEach>
						</div>
					</div>

					<div class="input-group mb-3">
						<h5>공고 마감일</h5>
						<label class="form-control w-100" name="postingDeadline"
							style="text-align: center;">${posting.postingDeadline}</label>
					</div>
					<div class="d-flex justify-content-center">
						<c:choose>
							<c:when test="${sessionScope.isLoggedIn}">
								<c:if test="${userType == 2}">
									<button id="applyButton" class="btn btn-success"
										data-posting-idx="${posting.postingIdx}" data-person-idx="${person.personIdx}">지원하기</button>
							<button
									class="btn btn-outline-secondary scrapBtn d-flex align-items-center ms-3">
									<svg class="w-6 h-6 text-gray-800 dark:text-white scrapSvg me-2"
										data-posting-idx="${posting.postingIdx}"
										aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
										width="24" height="24" fill="none" viewBox="0 0 24 24">
                            		<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
											d="m17 21-5-4-5 4V3.889a.92.92 0 0 1 .244-.629.808.808 0 0 1 .59-.26h8.333a.81.81 0 0 1 .589.26.92.92 0 0 1 .244.63V21Z" /></svg>
									<span>스크랩</span>
								</button>
								</c:if>
								<!-- 이 경우에만 목록으로 버튼을 표시합니다. -->
								<a class="btn btn-info ms-3" href="javascript:history.back();">목록으로</a>
							</c:when>
							<c:otherwise>
								<!-- 로그인하지 않은 경우에도 목록으로 버튼을 표시합니다. -->
								<a class="btn btn-info ms-3" href="javascript:history.back();">목록으로</a>
							</c:otherwise>
						</c:choose>

					</div>


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
						<textarea name="postingComment" class="w-100" rows="10" readonly>${posting.postingComment }</textarea>
					</div>

				</div>

			</div>
		</div>
	</main>
	<script src="/js/postingScrap.js"></script>
</body>
</html>