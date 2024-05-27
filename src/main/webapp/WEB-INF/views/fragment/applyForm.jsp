<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/x-icon" href="/images/favicon.png">
<link href="/css/bootstrap.min.css" rel="stylesheet" />
<style type="text/css">
#btn-resume-select::after {
	display: none;
}
</style>
</head>
<body>
	<div class="container">
		<div class="mt-3 container border py-3">
			<div class="d-flex flex-column">
				<label for="companyName" class="fs-5 fw-bold">회사명</label> <span
					class="mt-1" id="companyName">${company.companyName}</span>
			</div>
			<div class="d-flex flex-column mt-2">
				<label for="postingTitle" class="fs-5 fw-bold">공고명</label> <span
					class="mt-1" id="postingTitle">${posting.postingTitle}</span>
			</div>
		</div>
		<div class="mt-3">
			<div class="dropdown w-100">
				<button
					class="btn btn-white border dropdown-toggle bg-transparent w-100"
					type="button" id="btn-resume-select" data-bs-toggle="dropdown"
					aria-expanded="false">이력서 선택</button>
				<ul class="dropdown-menu w-100" aria-labelledby="dropdownMenuButton">
					<c:forEach items="${resumes}" var="resume">
						<li><a class="dropdown-item resumeSelect" data-resume-idx="${resume.resumeIdx}">${resume.resumeTitle}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<form>
		<input type="hidden" value="${person.personIdx }" name="personIdx" id="personIdx">
		<input type="hidden" value="${company.companyIdx }" name="companyIdx" id="companyIdx">
		<div class="border container mt-3" id="selected-resume">
		</div>
		<div class="d-flex justify-content-center mt-3">
			<button id="btnApply" class="btn btn-success"
				data-posting-idx="${posting.postingIdx}">지원하기</button>
		</div>
		</form>
	</div>
	<script>
	document.addEventListener("DOMContentLoaded", function() {
		document.body.addEventListener('click', function(e) {
			if (e.target.classList.contains('resumeSelect')) {
				const button = e.target;
				const resumeIdx = button.getAttribute('data-resume-idx');
				const url = `/resumeSelect?resumeIdx=` + resumeIdx;
				console.log(resumeIdx);
				fetch(url, {
					method: 'GET',
				})
					.then(response => response.text())
					.then(response => {
						document.querySelector("#selected-resume").innerHTML = response;
					})
					.catch(error => {
						console.error("Error: " + error);
						document.querySelector("#selected-resume").innerHTML = "이력서 선택에 실패했습니다";
					});
			}
		});
		
		document.getElementById('btnApply').addEventListener('click', function(e) {
		    e.preventDefault(); // 폼 제출 기본 이벤트 방지

		    const postingIdx = this.getAttribute('data-posting-idx');
		    const resumeIdx = document.querySelector('#resumeIdx').value;
		    const personIdx = document.querySelector('#personIdx').value;
		    const companyIdx = document.querySelector('#companyIdx').value;
		    const formData = new FormData(); // 필요한 데이터 추가
		    fetch('/applyPosting', { 
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/json'
		          },
		          body: JSON.stringify({ postingIdx, resumeIdx, personIdx, companyIdx }),
		    })
		    .then(response => {
		        if (response.ok) {
		            return response.text(); // 또는 response.json() 등 서버 응답에 맞게 처리
		        }
		        throw new Error('Network response was not ok.');
		    })
		    .then(response => {
		        alert('지원이 완료되었습니다!'); // 성공 메시지 출력
		        window.close(); // 현재 팝업 창 닫기
		       window.opener.location.href = '/applyPage'; // 부모 창을 '/ApplyPerson' 페이지로 이동
		    })
		    .catch(error => {
		        console.error('There has been a problem with your fetch operation:', error);
		        alert('지원 처리 중 문제가 발생했습니다.'); // 실패 메시지 출력
		    });
		});

		
	});
	</script>
	<script src="/js/bootstrap.bundle.min.js"></script>
</body>
</html>