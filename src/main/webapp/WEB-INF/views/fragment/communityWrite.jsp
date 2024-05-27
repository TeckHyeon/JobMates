<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<main class="container mt-5">
	<div class="row">
		<div class="col-sm">
			<form id="postWrite" action="/CommunityWrite" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="communityName" value="${name}" />
				<div class="my-3">
				<label for="communityTitle">제목</label>
					<input type="text" class="form-control mt-2" name="communityTitle"
						id="communityTitle" /> 
				</div>
				<div class="my-3">
				<label for="communityContent">내용</label>
					<textarea rows="20" class=" form-control mt-2" id="communityContent"
						name="communityContent" style="height: 250px; width: 100%;"></textarea>
				</div>
				<div class="my-2 d-flex justify-content-center align-items-center">
					<div>
						<button type="button" id="community-write" class="btn btn-primary me-2">등록</button>
						<button type="reset" id="btn-cancel" class="btn border ">취소</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</main>