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
		
				<input type="hidden" name=companyIdx id=companyIdx value=${companyIdx }>

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

							<a id="btn-go-list" href="/resumeRecommend" class="btn btn-primary">목록으로</a>

							<button class="btn btn-outline-secondary scrapBtn d-flex align-items-center ms-3">
									<svg class="w-6 h-6 text-gray-800 dark:text-white scrapSvg me-2"
										data-resume-idx="${resume.resumeIdx}"
										aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
										width="24" height="24" fill="none" viewBox="0 0 24 24">
                            		<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
											d="m17 21-5-4-5 4V3.889a.92.92 0 0 1 .244-.629.808.808 0 0 1 .59-.26h8.333a.81.81 0 0 1 .589.26.92.92 0 0 1 .244.63V21Z" /></svg>
									<span>스크랩</span>
								</button>

				</div>
			</div>
		</div>
	</main>
<script>
document.addEventListener("DOMContentLoaded", function() {
    const companyIdx = document.getElementById('companyIdx').value;

    function updatescrapSvgs() {
        const scrapSvgs = document.querySelectorAll('.scrapSvg');
        scrapSvgs.forEach(function(button) {
            const resumeIdx = button.getAttribute('data-resume-idx');

            // 스크랩 상태 확인 요청
            fetch(`/checkResumeScrap?resumeIdx=` + resumeIdx + `&companyIdx=` + companyIdx, {
                method: 'GET',
            })
            .then(response => response.json())
            .then(isScraped => {
                console.log('Scrap status for resumeIdx:', resumeIdx, 'is', isScraped); // 확인용 로그
                button.setAttribute('data-scraped', isScraped);
                if (isScraped) {
                    button.setAttribute('fill', 'yellow');
                } else {
                    button.setAttribute('fill', 'none');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });
    }

    updatescrapSvgs(); // 페이지 로드 시 스크랩 버튼 상태 갱신

    document.addEventListener('click', async function(e) {
        const button = e.target.closest('.scrapBtn'); // 상위 요소인 버튼을 찾기
        if (button) {
            e.preventDefault(); // 기본 동작 방지
            e.stopPropagation(); // 이벤트 전파 방지
            const scrapSvg = button.querySelector('.scrapSvg');
            const resumeIdx = scrapSvg.getAttribute('data-resume-idx');
            const companyIdx = document.getElementById('companyIdx').value;
            const isScraped = scrapSvg.getAttribute('data-scraped') === 'true';
            console.log('Button clicked for resumeIdx:', resumeIdx, 'isScraped:', isScraped);
            
            try {
                let response;
                if (isScraped) {
                    // 스크랩 삭제 요청
                    response = await fetch(`/resumeScrapDelete`, {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({ resumeIdx, companyIdx }), // 변경된 부분
                    });
                } else {
                    // 스크랩 추가 요청
                    response = await fetch('/resumeScrapAdd', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({ resumeIdx, companyIdx }), // 변경된 부분
                    });
                }

                if (response.ok) {
                    const message = isScraped ? '스크랩이 해제되었습니다.' : '스크랩되었습니다.';
                    alert(message);
                    updatescrapSvgs(); // 모든 스크랩 버튼 상태 갱신
                } else {
                    throw new Error('error.');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            }
            return; // 여기서 함수 실행 종료
        }

    });
});
</script>
	<script src="/js/bootstrap.bundle.min.js"></script>
</body>
</html>