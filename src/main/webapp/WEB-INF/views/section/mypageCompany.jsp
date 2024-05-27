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
    margin-bottom:20px;
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
    margin-bottom:100px;
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
				<h3 class="card-title mt-2" style="text-align:center;">기업 회원정보 보기</h3>
			</div>
		

<div class="card-body1">

    <div class="image-container">
        <img src="${companyFile.filePath}" style="height:200px; width:250px;" id="imagePreview" class="image-preview mb-2 border border-tertiary">
    </div>
    <div class="details-container">

        <div class="input-group mb-3">
            <span class="input-group-text">아이디</span>
            <label class="form-control">${userId}</label>
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text">기업 이름</span>
            <label class="form-control">${company.companyName}</label>
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text">대표 이름</span>
            <label class="form-control">${company.companyRepName}</label>
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text">회사 직종</span>
            <label class="form-control">${company.companySector}</label>
        </div>
        
    </div>
</div>
				
				<div class="card-body2">

					<div class="form-group row">
						<label  class="col-sm-2 col-form-label">회사주소</label>
						<div class="col-sm-6">
							<span class="detail-info">${company.companyAddress}</span>
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 col-form-label">회사직원수</label>
						<div class="col-sm-6">
							<span class="detail-info">${company.companyEmp}</span>
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 col-form-label">회사규모</label>
						<div class="col-sm-6">
							<span class="detail-info">${company.companySize}</span>
						</div>
					</div>


					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">회사 전화번호</label>
						<div class="col-sm-6">
							<span class="detail-info">${company.companyPhone}</span>
						</div>
					</div>

					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">채용 담당자</label>
						<div class="col-sm-6">
							<span class="detail-info">${company.companyMgrName}</span>
						</div>
					</div>

					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">담당자 번호</label>
						<div class="col-sm-6">
							<span class="detail-info">${company.companyMgrPhone}</span>
						</div>
					</div>


					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">설립 연도</label>
						<div class="col-sm-6">
							<span class="detail-info">${company.companyYear}</span>
						</div>
					</div>

					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">회사 이메일</label>
						<div class="col-sm-6">
							<span class="detail-info">${userEmail}</span>
						</div>
					</div>

				</div>
			</div>


	</section>
	<%@include file="/WEB-INF/layouts/footer.jsp"%>
	<script src="/js/bootstrap.bundle.min.js"></script>
</body>
</html>