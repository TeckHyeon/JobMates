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
.info {
	margin-top:7%;
}

.card-body1 {
    width: 100%;
    margin: 0 auto;
    margin-top: 30px; 
    margin-left:20%;
    padding: 0;
    display: flex;
    align-items: flex-start;
}

.image-container {
    
    margin-right: 15%; /* 이미지와 텍스트 사이의 간격을 조절 */
}

.card-body2 {
    width: 60%;
    margin: 0 auto;
    margin-left:20%;
    border: 0.5px solid #ccc;
    padding: 0;
    box-sizing: border-box;
}

.col-form-label {
    border-left: none;
    border-right: none;
    background-color: #e0f7fa; 
    height: 50px;
    display: flex;
    align-items: center; /* 상하 가운데 정렬 */
    padding-left: 10px;
    padding-right: 10px;
    box-sizing: border-box;
    margin: 0;
}

.col-sm-6 {
    display: flex;
    align-items: center; /* 상하 가운데 정렬 */
    margin: 0;
    padding: 0 10px; 
    box-sizing: border-box;
}

.form-group {
    margin: 0;
    border-bottom: 1px solid #ccc;
}

/* 사이드바 스타일링 */

.sidenav {
    background-color: #f1f1f1;
    position: fixed;
    top:100px;
    width: 200px;
    min-height: 100%; /* 화면의 높이만큼 사이드바의 높이 설정 */
    padding-top: 20px; /* padding 추가 */
}

.sidenav ul {
    list-style-type: none; /* 기본 리스트 스타일 제거 */
    padding: 0; /* 기본 패딩 제거 */
}

.sidenav li {
    margin: 10px 0; /* li 요소들 사이에 수직 간격 추가 */
}
.sidenav li a {
    display: block;
    text-align: center;
}

.sidenav a {
    text-decoration: none; /* 링크의 기본 밑줄 제거 */
    color: #000; /* 링크 텍스트 색상 설정 */
    display: block; /* 전체 너비를 클릭할 수 있도록 설정 */
    padding: 10px; /* 링크 요소에 패딩 추가 */
}

.sidenav a:hover {
    background-color: #ddd; /* 마우스 오버 시 배경색 변경 */
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
	position: fixed;
	top: 0;
	height:100px;
	width: 100%;
	z-index: 1000;
}

.detail-info {
padding-left : 30px;
}

.input-group {
    display: flex;
    align-items: center; /* 수평 가운데 정렬 */
}

.input-group-text {
    width: 15%;
    flex: 0 0 auto;
    background-color: #e0f7fa;
    text-align: center;
}

.form-control {
    flex: 1; /* flex-grow를 사용하여 남은 공간을 차지하도록 설정 */
    width:500px !important;
    margin: 0;
}

</style>
</head>
<body>
	<%@include file="/WEB-INF/layouts/header.jsp"%>
	<!-- 사이드바 -->
    <aside>
        <nav class="col-sm-3 sidenav">
            <h4 class="mb-4 mt-4" style="text-align:center;">마이페이지</h4>
            <ul class="nav flex-column">
                <li class="nav-item"><a class="nav-link" href="/mypage">회원 정보</a></li>
                <li class="nav-item"><a class="nav-link" href="/mypageCompanyUpdateForm">정보 수정</a></li>
                <li class="nav-item"><a class="nav-link" href="/accountDeleteForm">회원 탈퇴</a></li>
            </ul>
        </nav>
    </aside>
    <!-- 사이드바 끝 -->
	<section>

		<div class="info">
		
			<div class="card-header">
				<h3 class="card-title mt-1" style="text-align:center;">기업 회원정보 수정</h3>
			</div>
		
		<form class="form-horizontal" action="/mypageCompanyUpdate" method="POST" enctype="multipart/form-data">
		
			<div class="card-body1">
				<input type="hidden" name="userIdx" value="${userIdx}">
				<input type="hidden" name="companyIdx" value="${companyIdx}">
			<div>
				<img src="${companyFile.filePath}" id="imagePreview" style="width:250px; height: 200px;" class="mb-2 border border-tertiary">
					<div class="row justify-content-center">
						<div class="col mb-4 row" >
							<input type="file" name="file" id="uploadInput" class="form-control mt-1" onchange="chooseImage(this)"/>
						</div>
					</div>
			</div>
			    <div class="details-container ms-5">
			
			        <div class="input-group mb-3">
			            <span class="input-group-text">아이디</span>
			            <input type="text" class="form-control" id="id" placeholder="아이디"
								value="${userId}" name="userId"
								readonly="readonly">
			        </div>
			        <div class="input-group mb-3">
			            <span class="input-group-text">기업 이름</span>
							<input type="text" class="form-control" id="companyName"
								placeholder="기업이름" value="${company.companyName}"
								name="companyName" required>
			        </div>
			        <div class="input-group mb-3">
			            <span class="input-group-text">대표 이름</span>
							<input type="text" class="form-control" id="companyRepName"
								placeholder="대표이름" value="${company.companyRepName}"
								name="companyRepName" required>
			        </div>
			        <div class="input-group mb-3">
			            <span class="input-group-text">회사 직종</span>
							<input type="text" class="form-control" id="companySector"
								placeholder="회사직종" value="${company.companySector}"
								name="companySector" required>
			        </div>
			        
			    </div>
			</div>

				<div class="card-body2">

					<div class="form-group row">
						<label  class="col-sm-2 col-form-label">회사주소</label>
						<div class="col-sm-6" style="width:83%;">
							<input type="text" class="form-control" id="companyAddress" style="outline:none; border:none;"
								placeholder="회사주소" value="${company.companyAddress}"
								name="companyAddress" required>
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 col-form-label">회사직원수</label>
						<div class="col-sm-6" style="width:83%;">
							<input type="number" min="1" max="99999999" class="form-control" id="companyEmp" style="outline:none; border:none;"
								placeholder="숫자만 입력해주세요" value="${company.companyEmp}"
								name="companyEmp">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 col-form-label">회사규모</label>
						<div class="col-sm-6" style="width:83%;">
							<input type="text" class="form-control" id="companySize" style="outline:none; border:none;"
								placeholder="회사규모" value="${company.companySize}"
								name="companySize" required>
						</div>
					</div>


					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">회사 전화번호</label>
						<div class="col-sm-6" style="width:83%;">
							<input type="text" class="form-control" id="companyPhone" style="outline:none; border:none;"
								placeholder="회사전화번호" value="${company.companyPhone}"
								name="companyPhone" >
						</div>
					</div>

					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">채용 담당자</label>
						<div class="col-sm-6" style="width:83%;">
							<input type="text" class="form-control" id="companyMgrName" style="outline:none; border:none;"
								placeholder="채용담당자이름" value="${company.companyMgrName}"
								name="companyMgrName">
						</div>
					</div>

					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">담당자 번호</label>
						<div class="col-sm-6" style="width:83%;">
							<input type="text" class="form-control" id="companyMgrPhone" style="outline:none; border:none;"
								placeholder="채용담당자전화번호" value="${company.companyMgrPhone}"
								name="companyMgrPhone">
						</div>
					</div>


					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">설립 연도</label>
						<div class="col-sm-6" style="width:83%;">
						<select id="yearSelect" class="form-control"  style="outline:none; border:none;"
								placeholder="설립연도" value="${company.companyYear}"
								name="companyYear"></select>						
						</div>
					</div>

					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">회사 이메일</label>
						<div class="col-sm-6" style="width:83%;">
							<input type="email" class="form-control" id="email" style="outline:none; border:none;"
								placeholder="이메일" value="${userEmail}"
								name="userEmail" required>
						</div>
					</div>

				</div>
					<div style="text-align:center; margin-top :20px; margin-bottom:80px;"><button type="submit" class="btn btn-outline-primary" style="width:15%;">저장</button></div>
			</form>

			</div>



	</section>			
	<%@include file="/WEB-INF/layouts/footer.jsp"%>

	<script src="/js/bootstrap.bundle.min.js"></script>

	
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var saveButton = document.querySelector('button[type="submit"]');

        // '저장' 버튼 클릭 이벤트에 대한 핸들러를 추가합니다.
        saveButton.addEventListener("click", function(event) {
            event.preventDefault(); // 폼의 기본 제출 이벤트를 중단합니다.
            var confirmResult = confirm("회원정보를 수정하시겠습니까?"); // 확인 메시지를 표시합니다.

            if (confirmResult) {
                // 사용자가 '예'를 선택한 경우, 폼을 프로그래매틱하게 제출합니다.
                event.target.form.submit();
            }
        });
    });
</script>
	
<script>
function chooseImage(input) {
    const fileInput = input.files[0];
    const imagePreview = document.getElementById('imagePreview');
    const currentImage = imagePreview.src;

    if (fileInput) {
        const reader = new FileReader();

        reader.onload = function(e) {
            imagePreview.src = e.target.result;
        }
     // 파일을 읽어 데이터 URL로 변환
        reader.readAsDataURL(fileInput); 
    } else {
    	// 파일이 선택되지 않았을 때는 현재 이미지 유지
        imagePreview.src = currentImage; 
    }
}
</script>
<script>
  var companyYear = "${company.companyYear}"; // 서버에서 받아온 기본 연도 값
  var year = new Date().getFullYear(); // 현재 연도
  var startYear = year - 94; // 시작 연도
  var endYear = year + 5; // 종료 연도
  var select = document.getElementById('yearSelect');

  for(var i = startYear; i <= endYear; i++) {
    var option = document.createElement('option');
    option.value = option.textContent = i;
    if(i == companyYear) {
      option.selected = true; // 기본 값 설정
    }
    select.appendChild(option);
  }
</script>
<script>
document.querySelector('input[type="number"]').addEventListener('input', function(e) {
	  if (!/^\d+$/.test(e.target.value)) {
	    alert('숫자만 입력해주세요!');
	    e.target.value = '';
	  }

});
</script>

</body>
</html>