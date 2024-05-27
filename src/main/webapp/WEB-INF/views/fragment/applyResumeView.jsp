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
							<button id="btnPass" class="btn btn-success"
								data-apply-status="2" data-apply-idx="${apply.applyIdx}">합격</button>
							<button id="btnFail" class="btn btn-danger ms-3"
								data-apply-status="3" data-apply-idx="${apply.applyIdx}">불합격</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
<script>
document.addEventListener('DOMContentLoaded', function() {
    const btnPass = document.getElementById('btnPass');
    const btnFail = document.getElementById('btnFail');

    btnPass.addEventListener('click', () => handleButtonClick(btnPass));
    btnFail.addEventListener('click', () => handleButtonClick(btnFail));

    function handleButtonClick(button) {
        const applyStatus = button.getAttribute('data-apply-status');
        const applyIdx = button.getAttribute('data-apply-idx');

        fetch('/applyProcess/' + applyIdx + '/' + applyStatus, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                applyIdx: applyIdx,
                applyStatus: applyStatus
            })
        })
        .then(response => {
            if (!response.ok) {
                // 성공적으로 처리되지 않았을 경우, 오류 메시지를 추출하여 예외를 발생시킴
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text(); // 성공 시, 별도의 데이터가 없으므로 이 부분은 그냥 통과됨
        })
        .then(data => {
            // 성공적으로 처리되었을 때의 로직
            alert('처리가 완료되었습니다.');

            // 부모 창이 접근 가능한지 확인
            if (window.opener && !window.opener.closed) {
                // 업데이트된 ApplyPage 콘텐츠를 가져옴
                fetch('/companyApply', {
                    method: 'GET',
                })
                .then(response => response.text())
                .then(responseText => {
                    // 부모 창의 section 내용을 업데이트
                    window.opener.document.querySelector("#section").innerHTML = responseText;
                    // 팝업 창을 닫음
                    window.close();
                })
                .catch(error => {
                    console.error("부모 창 업데이트 오류: " + error);
                });
            } else {
                console.error("부모 창에 접근할 수 없습니다.");
            }
        })
        .catch((error) => {
            // fetch 또는 응답 처리 중 오류 처리 로직
            console.error('오류:', error);
            alert('처리 중 오류가 발생했습니다: ' + error.message);
        });
    }
});

</script>


	<script src="/js/bootstrap.bundle.min.js"></script>
</body>
</html>