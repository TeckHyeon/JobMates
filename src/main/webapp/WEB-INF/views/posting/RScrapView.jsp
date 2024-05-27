<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<div class="container" style="width: 100%;">
		
				<input type="hidden" name=companyIdx id=companyIdx value=${companyIdx }>
				

				<div class="mt-3">
					<label class="form-control w-100" style="text-align: center;">${resume.resumeTitle}</label>
					<input type="hidden" name="publish" value=1>
				</div>

				<div class="row">
					<!-- 왼쪽에 이미지 -->
					<div class="col-md-4 mt-5">
						<img src="${resumeFile.filePath}" id="imagePreview"
							style="width: 240px; height: 305px;"
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





			<div class="container" style="width: 100%;">
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

			</div>
		</div>

	<script src="/js/bootstrap.bundle.min.js"></script>
</body>
