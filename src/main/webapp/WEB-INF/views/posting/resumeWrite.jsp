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
<style>
main {
	margin-bottom : 100px;
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
<form method="post" action="/resumeWrite" enctype="multipart/form-data">
<div class="container" style="width: 85%;">

<!--이력서 공개 여부 자동으로 공개로 해놓음 -->

<div class="mt-5">
<input type="text" class="form-control w-100" name="resumeTitle" style="text-align:center;" placeholder="제목을 입력 해주세요." required>
<input type="hidden" name="publish" value=1>
</div>

    <div class="row">
        <!-- 왼쪽에 이미지 -->
        <div class="col-md-4 mt-5">
            <img src="#" id="imagePreview"
                style="width:250px; height: 310px;" class="mb-2 border border-tertiary">
        </div>

        <!-- 오른쪽에 입력 필드 -->
        <div class="col-md-8 mt-5">
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">이름</span>
                <input type="text" class="form-control" value="${person.personName }" readonly>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">생년월일</span>
                <input type="text" class="form-control" value="${person.personBirth}" readonly>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">연락처</span>
                <input type="text" class="form-control" value="${person.personPhone }" readonly>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">주소</span>
                <input type="text" class="form-control" value="${person.personAddress}" readonly>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">학력</span>
                <input type="text" class="form-control" value="${person.personEducation}" readonly>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">이메일</span>
                <input type="text" class="form-control" value="${person.userEmail}" readonly>
            </div>
        </div>
    </div>
</div>





				<div class="container" style="width: 85%;">
				         <div class="row justify-content-center ">
				             <div class="col-md-14 mx-auto mb-4">
				                 <input type="file" name="file" id="uploadInput" class="form-control mt-2" onchange="previewImage()" required/>
				             </div>
				             
							<div class="input-group mb-3">
					            <h5>포트폴리오 주소</h5>
					            <input type="text" class="form-control w-100" name="portfolio" required>
							</div>
                         	
                        	<div class="mb-3">
								<label for="skills" class="form-label">기술스택</label>
								<div class="mx-auto row" id="skills">
										
						<c:forEach var="skill" items="${skill}">
							<div class="col-auto">
									<label class="btn btn-outline-primary">${skill.skillName}</label>
							</div>
						</c:forEach>
																			
									<input type="hidden" id="defaultSkillIdx" name="skillIdx" value="0">
								</div>
							</div>
                         	
                         
                         <br>

                         <div  style="margin-top:15px;">
                                 <h4>자기소개</h4>
                                 <textarea name="resumeComment" class="w-100" rows="10"
                                     placeholder="내용을 입력하세요" required></textarea>
                             </div>
                             <div class="d-flex mt-4 justify-content-center">
                                 <div class="px-2">
                                     <button type="submit" id="save-resume" class="btn btn-primary">저장</button>   <a href="/resumes" id="cancel-resume" class="btn btn-primary">목록으로</a>
                                 </div>
                             </div>
                         </div>

                     </div>
             </form>
         </div>
</main>

<%@include file="/WEB-INF/layouts/footer.jsp"%>

<script src="/js/bootstrap.bundle.min.js"></script>



<script>
var hiddenField = document.querySelector('input[type="hidden"][name="skillIdx"]');
var checkboxes = document.querySelectorAll('input[type="checkbox"][name="skillIdx"]');
var isChecked = Array.from(checkboxes).some(checkbox => checkbox.checked);

if (!isChecked) {
    // 체크박스가 하나도 선택되지 않았다면 hidden 필드를 텍스트로 변경하고 기본값 설정
    document.getElementById('defaultSkillIdx').type = 'hidden';
    hiddenField.value = '0';
    hiddenField.disabled = false;
} else {
    // 체크박스가 선택되었다면 hidden 필드 비활성화
    hiddenField.disabled = true;
}
</script>

<script>
document.getElementById("save-resume").addEventListener("click", function(event) {
    confirmSubmission(event);
});

function confirmSubmission(event) {
    if (!confirm("이력서를 입력 하시겠습니까?")) {
        // confirm() 함수가 false를 반환하면 폼 제출을 막습니다.
        event.preventDefault();
    }
}

document.getElementById("cancel-resume").addEventListener("click", confirmCancel);

function confirmCancel(event) {
    event.preventDefault();
    if (confirm("목록으로 돌아가시겠습니까?")) {
        window.location.href = this.getAttribute("href");
    }
}
</script>

<script>
function previewImage() {
    const fileInput = document.getElementById('uploadInput');
    const imagePreview = document.getElementById('imagePreview');
    
    if (fileInput.files && fileInput.files[0]) {
        const reader = new FileReader();

        reader.onload = function(e) {
            imagePreview.src = e.target.result;
        }

        reader.readAsDataURL(fileInput.files[0]); // 파일을 읽어 데이터 URL로 변환
    }
}
</script>
</body>
</html>