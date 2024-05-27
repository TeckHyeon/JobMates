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

/* 추가적인 CSS 스타일링 */
footer {
    position: fixed;
    bottom: 0;
    width: 100%;
    padding: 20px 0;
    text-align: center;
	z-index: 1000;
}

header {
	background-color: #e0f7fa;
	position: fixed;
	top: 0;
	height:100px;
	width: 100%;
	z-index: 1000;
}


section {
    position: relative;
    top: 100px;
    margin-top: 0;
    margin-left: 220px; /* 사이드바(nav)의 너비에 따라 조정 */
    width: calc(100% - 220px); /* 사이드바(nav)의 너비에 따라 조정 */
}


.card-body {
    width: 60%;
    margin: 0 auto;
    border: 0.5px solid #ccc;
    padding: 0;
    box-sizing: border-box;
}
label {
    border-left: none;
    border-right: none;
    background-color: #e0f7fa; 
    height: 60px;
    display: flex;
    align-items: center; /* 상하 가운데 정렬 */
    box-sizing: border-box;
    width: 100%;
}

.col-sm-7,
.col-auto {
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
    position: absolute; /* 변경된 부분 */
    top: 100px; /* 헤더(header)의 높이에 따라 조정 */
    width: 200px;
    z-index: 100;
    min-height: 100%;
    padding-top: 20px;
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

span {
padding-left : 30px;
}

</style>
</head>
<body>
    <%@include file="/WEB-INF/layouts/header.jsp"%>

    <!-- 사이드바 -->
        <nav class="col-sm-3 sidenav">
            <h4 class="mb-4 mt-4" style="text-align:center;">마이페이지</h4>
            <ul class="nav flex-column">
                <li class="nav-item"><a class="nav-link" href="/mypage">회원 정보</a></li>
                <li class="nav-item"><a class="nav-link" href="/mypageUpdateForm">정보 수정</a></li>
                <li class="nav-item"><a class="nav-link" href="/accountDeleteForm">회원 탈퇴</a></li>
            </ul>
        </nav>
    <!-- 사이드바 끝 -->
    <section>
        <div class="info">
            <div class="card-header">
                <h3 class="card-title mt-5 mb-5" style="text-align:center;">회원정보 보기</h3>
            </div>            
            
            <form class="form-horizontal" action="/mypageUpdateForm">
                <div class="card-body mt-5">
                    <div class="form-group row">
                        <label for="inputEmail3" class="col-sm-1 col-form-label" style="width:10%;">아이디</label>
                        <div class="col-sm-7">
                            <span>${userId}</span>
                        </div>
                    </div>
                                        
                    <div class="form-group row">
                        <label for="inputPassword3" class="col-sm-1 col-form-label" style="width:10%;">주소</label>
                        <div class="col-sm-7" style="width:50%;">
                            <span>${person.personAddress}</span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="inputPassword3" class="col-sm-1 col-form-label" style="width:10%;">생년월일</label>
                        <div class="col-sm-7" style="width:50%;">
                            <span>${person.personBirth}</span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="inputPassword3" class="col-sm-1 col-form-label"  style="width:10%;">학력</label>
                        <div class="col-sm-7" style="width:50%;">
                            <span>${person.personEducation}</span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="inputPassword3" class="col-sm-1 col-form-label"  style="width:10%;">이름</label>
                        <div class="col-sm-7" style="width:50%;">
                            <span>${person.personName}</span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="inputPassword3" class="col-sm-1 col-form-label"  style="width:10%;">전화번호</label>
                        <div class="col-sm-7" style="width:50%;">
                            <span>${person.personPhone}</span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="inputPassword3" class="col-sm-1 col-form-label"  style="width:10%;">이메일</label>
                        <div class="col-sm-7" style="width:50%;">
                            <span>${userEmail}</span>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="inputPassword3" class="col-sm-1 col-form-label"  style="width:10%;">기술 스택</label>
                        <div class="col-auto" style="padding-left:25px;" >
                        <c:forEach var="skill" items="${skill}">
                               <div style="margin-left:15px;"class="btn btn-outline-primary">${skill.skillName}</div>
                        </c:forEach>
                        </div>
                    </div>
                </div>    
            </form>
        </div>
    </section>
    <%@include file="/WEB-INF/layouts/footer.jsp"%>
    <script src="/js/bootstrap.bundle.min.js"></script>
    	<script src="/js/postingScrap.js"></script>
</body>
</html>