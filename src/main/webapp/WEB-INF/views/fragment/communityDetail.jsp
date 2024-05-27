<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.reply-container>div {
	border-top: 1px solid #ced4da; /* 부트스트랩의 기본 테두리 색상을 적용한 상단 테두리 */
	border-bottom: 1px solid #ced4da; /* 부트스트랩의 기본 테두리 색상을 적용한 하단 테두리 */
}

.reply-container>div:first-child {
	border-bottom: none; /* 첫 번째 div는 하단 테두리 제거 */
}

.reply-container>div:last-child {
	border-top: none; /* 마지막 div는 상단 테두리 제거 */
}
</style>
<input type="hidden" value="${isLoggedIn}" id="isLoggedIn">
<input type="hidden" value="${community.communityIdx}" id="communityIdx">
<div class="border container py-3" id="communityDetail">
	<div class="container mt-2 d-flex align-items-center">
		<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30"
			fill="#99ccff" class="bi bi-quora" viewBox="0 0 16 16">
    <path
				d="M8.73 12.476c-.554-1.091-1.204-2.193-2.473-2.193-.242 0-.484.04-.707.142l-.43-.863c.525-.45 1.373-.808 2.464-.808 1.697 0 2.568.818 3.26 1.86.41-.89.605-2.093.605-3.584 0-3.724-1.165-5.636-3.885-5.636-2.68 0-3.839 1.912-3.839 5.636 0 3.704 1.159 5.596 3.84 5.596.425 0 .811-.046 1.166-.15Zm.665 1.3a7 7 0 0 1-1.83.244C3.994 14.02.5 11.172.5 7.03.5 2.849 3.995 0 7.564 0c3.63 0 7.09 2.828 7.09 7.03 0 2.337-1.09 4.236-2.675 5.464.512.767 1.04 1.277 1.773 1.277.802 0 1.125-.62 1.179-1.105h1.043c.061.647-.262 3.334-3.178 3.334-1.767 0-2.7-1.024-3.4-2.224Z" />
  </svg>
		<p class="fs-5 ms-3 mt-2" id="communityTitle">${community.communityTitle}</p>
	</div>
	<div class="d-flex justify-content-between">
		<div class="container d-flex my-2 align-items-center">
			<div>
				<span> <svg xmlns="http://www.w3.org/2000/svg" width="16"
						height="16" fill="currentColor" class="bi bi-eye"
						viewBox="0 0 16 16">
                                    <path
							d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z" />
                                    <path
							d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0" />
                                </svg>
				</span> <span id="view${community.communityIdx}">${community.viewCount}</span>
			</div>
			<span class="vr ms-3 align-self-center" style="height: 50%"></span>
			<div class="ms-3">
				<span class="like_btn"
					data-community-idx="${community.communityIdx}"> <svg
						xmlns="http://www.w3.org/2000/svg" width="16" height="16"
						fill="currentColor" class="bi bi-hand-thumbs-up"
						viewBox="0 0 16 16">
                                    <path
							d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2 2 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a10 10 0 0 0-.443.05 9.4 9.4 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a9 9 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.2 2.2 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.9.9 0 0 1-.121.416c-.165.288-.503.56-1.066.56z" />
                                </svg>
				</span> <span id="communityIdx${community.communityIdx}">${community.likeCount}</span>
			</div>
			<span class="vr ms-3 align-self-center" style="height: 50%"></span>
			<div class="ms-3">
				<span> <svg xmlns="http://www.w3.org/2000/svg" width="16"
						height="16" fill="currentColor" class="bi bi-chat"
						viewBox="0 0 16 16">
                                    <path
							d="M2.678 11.894a1 1 0 0 1 .287.801 11 11 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8 8 0 0 0 8 14c3.996 0 7-2.807 7-6s-3.004-6-7-6-7 2.808-7 6c0 1.468.617 2.83 1.678 3.894m-.493 3.905a22 22 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a10 10 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9 9 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105" />
                                </svg>
				</span> <span id="reply${community.communityIdx}">${community.replyCount}</span>
			</div>
			<span class="vr ms-3 align-self-center" style="height: 50%"></span>
			<div class="ms-3">
				<span>${community.createdDate}</span>
			</div>
		</div>
		<c:if test="${user.userIdx == community.userIdx}">
			<div class="d-flex container justify-content-end">
				<button class="btn updateBtn">수정</button>
				<span class="vr align-self-center"></span>
				<button class="btn deleteBtn">삭제</button>
			</div>
		</c:if>
	</div>

	<div class="container">
		<hr class="container">
	</div>

	<div class="fs-6 container" id="communityContent">${community.communityContent}</div>
	<div class="container mt-3">
		<span>${community.communityName}</span>
	</div>
	<div class="container">
		<hr class="container">
	</div>
</div>
<div class="border container py-3" id="editForm" style="display: none">
	<div class="container mt-2 d-flex align-items-center">
		<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30"
			fill="#99ccff" class="bi bi-quora" viewBox="0 0 16 16">
    <path
				d="M8.73 12.476c-.554-1.091-1.204-2.193-2.473-2.193-.242 0-.484.04-.707.142l-.43-.863c.525-.45 1.373-.808 2.464-.808 1.697 0 2.568.818 3.26 1.86.41-.89.605-2.093.605-3.584 0-3.724-1.165-5.636-3.885-5.636-2.68 0-3.839 1.912-3.839 5.636 0 3.704 1.159 5.596 3.84 5.596.425 0 .811-.046 1.166-.15Zm.665 1.3a7 7 0 0 1-1.83.244C3.994 14.02.5 11.172.5 7.03.5 2.849 3.995 0 7.564 0c3.63 0 7.09 2.828 7.09 7.03 0 2.337-1.09 4.236-2.675 5.464.512.767 1.04 1.277 1.773 1.277.802 0 1.125-.62 1.179-1.105h1.043c.061.647-.262 3.334-3.178 3.334-1.767 0-2.7-1.024-3.4-2.224Z" />
  </svg>
		<input class="fs-5 ms-3 mt-2" id="editTitle"
			value="${community.communityTitle}">
	</div>
	<div class="d-flex justify-content-between">
		<div class="container d-flex my-2 align-items-center">
			<div>
				<span> <svg xmlns="http://www.w3.org/2000/svg" width="16"
						height="16" fill="currentColor" class="bi bi-eye"
						viewBox="0 0 16 16">
                                    <path
							d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z" />
                                    <path
							d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0" />
                                </svg>
				</span> <span id="view${community.communityIdx}">${community.viewCount}</span>
			</div>
			<span class="vr ms-3 align-self-center" style="height: 50%"></span>
			<div class="ms-3">
				<span class="like_btn"
					data-community-idx="${community.communityIdx}"> <svg
						xmlns="http://www.w3.org/2000/svg" width="16" height="16"
						fill="currentColor" class="bi bi-hand-thumbs-up"
						viewBox="0 0 16 16">
                                    <path
							d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2 2 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a10 10 0 0 0-.443.05 9.4 9.4 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a9 9 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.2 2.2 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.9.9 0 0 1-.121.416c-.165.288-.503.56-1.066.56z" />
                                </svg>
				</span> <span id="communityIdx${community.communityIdx}">${community.likeCount}</span>
			</div>
			<span class="vr ms-3 align-self-center" style="height: 50%"></span>
			<div class="ms-3">
				<span> <svg xmlns="http://www.w3.org/2000/svg" width="16"
						height="16" fill="currentColor" class="bi bi-chat"
						viewBox="0 0 16 16">
                                    <path
							d="M2.678 11.894a1 1 0 0 1 .287.801 11 11 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8 8 0 0 0 8 14c3.996 0 7-2.807 7-6s-3.004-6-7-6-7 2.808-7 6c0 1.468.617 2.83 1.678 3.894m-.493 3.905a22 22 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a10 10 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9 9 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105" />
                                </svg>
				</span> <span id="reply${community.communityIdx}">${community.replyCount}</span>
			</div>
			<span class="vr ms-3 align-self-center" style="height: 50%"></span>
			<div class="ms-3">
				<span>${community.createdDate}</span>
			</div>
		</div>
		<c:if test="${user.userIdx == community.userIdx}">
			<div class="d-flex container justify-content-end">
				<button class="btn editBtn">저장</button>
				<span class="vr align-self-center"></span>
				<button class="btn cancelBtn">취소</button>
			</div>
		</c:if>
	</div>

	<div class="container">
		<hr class="container">
	</div>

	<textarea rows="20"
		class=" form-control mt-2 fs-6 text-truncate-2 container "
		id="editContent" name="communityContent"
		style="height: 250px; width: 100%;">${community.communityContent}</textarea>
	<div class="container mt-3">
		<span>${community.communityName}</span>
	</div>
	<div class="container">
		<hr class="container">
	</div>
</div>
<div class="border container py-3 mt-2 mb-3">
	<div>
		<p class="container">댓글</p>
		<div class="mt-3 reply-container">
			<c:forEach items="${reply}" var="reply">
				<!-- Current reply form -->
				<div class="px-3 py-4 reply-form"
					id="replyCurrentForm${reply.replyIdx}"
					data-reply-idx="${reply.replyIdx}">
					<div class="d-flex justify-content-between me-2">
						<p>${reply.replyName}</p>
						<p>${reply.createdDate}</p>
					</div>
					<div class="d-flex justify-content-between">
						<p class="replyContent">${reply.replyContent}</p>
						<c:if test="${user.userIdx == reply.userIdx}">
							<div class="d-flex justify-content-end">
								<button class="btn replyEditBtn">수정</button>
								<span class="vr align-self-center"></span>
								<button class="btn replyDeleteBtn">삭제</button>
							</div>
						</c:if>
					</div>
				</div>
				<!-- Edit reply form -->
				<div class="px-3 py-4 reply-form hidden"
					id="replyEditForm${reply.replyIdx}"
					data-reply-idx="${reply.replyIdx}">
					<div class="d-flex justify-content-between me-2">
						<p>${reply.replyName}</p>
						<p>${reply.createdDate}</p>
					</div>
					<div class="d-flex justify-content-between">
						<textarea class="form-control replyeditContent col"
							placeholder="댓글 달기..." style="border: none; height: 3rem">${reply.replyContent}</textarea>
						<c:if test="${user.userIdx == reply.userIdx}">
							<div class="d-flex justify-content-end">
								<button class="btn replyEditSave">저장</button>
								<span class="vr align-self-center"></span>
								<button class="btn replyDeleteCancel">취소</button>
							</div>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</div>

	</div>
	<div class="mt-3 row">
		<textarea class="form-control replyBox col" placeholder="댓글 달기..."
			data-community-idx="${community.communityIdx}"
			style="border: none; height: 3rem"></textarea>
	</div>
</div>


