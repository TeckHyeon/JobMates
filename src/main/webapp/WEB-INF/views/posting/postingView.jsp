<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container" style="width: 100%;">

<div class="mt-5">
    <label class="form-control w-100" style="text-align:center;">${posting.postingTitle}</label>
    <input type="hidden" name="postingDeadline" value="${posting.postingDeadline}">
</div>

    <div class="row">
        <!-- 왼쪽 필드 -->
        <div class="col-md-6 mt-5">
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">근무형태</span>
                <label class="form-control">${posting.empType}</label>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">경력</span>
                <label class="form-control">${posting.experience}</label>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">근무지역</span>
                <label class="form-control">${posting.postingAddress}</label>
            </div>
        </div>

        <!-- 오른쪽 필드 -->
        <div class="col-md-6 mt-5">
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">근무시간</span>
                <label class="form-control">${posting.startTime} ~ ${posting.endTime}</label>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">직무</span>
                <label class="form-control">${posting.jobType}</label>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text w-25 justify-content-center" style="background-color: #e0f7fa;">연봉</span>
                <label class="form-control">${posting.salary}</label>
            </div>
        </div>
    </div>
</div>

<div class="container mt-3" style="width: 100%;">
    <div class="row justify-content-center">
        <div class="mb-3">
            <h5 for="skills" class="form-label">기술스택</h5>
            <div class="mx-auto row" id="skills">
                <c:forEach var="skill" items="${skill}">
                    <div class="col-auto">
                        <label class="btn btn-outline-primary">${skill.skillName}</label>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="input-group mb-3">
            <h5>공고 마감일</h5>
            <label class="form-control w-100" name="postingDeadline" style="text-align:center;">${posting.postingDeadline}</label>
        </div>

        <div style="margin-top:15px;">
            <h4>기업소개</h4>
            <textarea name="resumeComment" class="w-100" rows="15" style="resize:none;" readonly>
기업 이름		:	${company.companyName}

기업 대표		:	${company.companyRepName}

기업 직종		:	${company.companySector}

기업 주소		:	${company.companyAddress}

기업 규모		:	${company.companySize}

직원 수		:	${company.companyEmp}

설립 연도		:	${company.companyYear}

기업 전화번호	:	${company.companyPhone}</textarea>
        </div>

        <div style="margin-top:15px;">
            <h4>공고소개</h4>
            <textarea name="postingComment" class="w-100" rows="10" readonly>${posting.postingComment}</textarea>
        </div>

        <div class="d-flex mt-4 justify-content-center">
            <div class="px-2">
                <a href="/postingEditForm?postingIdx=${posting.postingIdx}" id="edit-posting" class="btn btn-primary">수정하기</a>
            </div>
        </div>
    </div>
</div>
