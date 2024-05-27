<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
<c:if test="${not empty posts}">
	<div class="container mt-4">
		<div id="result" class="mt-5 ms-5">
			<div class="d-flex flex-wrap">
				<!-- flex-wrap 추가 가능 -->
				<c:forEach items="${posts}" var="post">
					<div class="me-5 each-postings">
						<!-- 각 포스트를 감싸는 div 추가 -->
						<img alt="기업 로고" src="${post.companyFileDto.filePath}" class="mb-3" 
					style="display: block; margin-left: auto; margin-right: auto; width: 250px; height: 200px;">
						<div class="d-flex justify-content-between">
							<!-- flex-direction 변경 및 가운데 정렬 -->
							<div class="d-flex flex-column" data-posting-idx="${post.postingDto.postingIdx}">
								<span>${ post.postingDto.postingTitle }</span> <span>${post.postingDto.postingDeadline}</span>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="modal fade" id="postingModal" tabindex="-1" aria-labelledby="postingModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="postingModalLabel">공고 보기</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body" id="postingModalBody">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>
</c:if>
<c:if test="${empty posts}">
<div class="mt-5" style="margin:0 auto; text-align:center;">
	<h3 class="mb-5">회원님이 작성하신 공고가 없습니다!</h3>
	<a href="/postings" style="text-decoration-line:none;"> 공고를 작성하시겠습니까? </a>
</div>
</c:if>
<script>
document.addEventListener('DOMContentLoaded', function() {
    // each-postings 클래스를 가진 모든 div에 대해 클릭 이벤트 리스너를 추가합니다.
    document.querySelectorAll('.each-postings').forEach(function(div) {
        div.addEventListener('click', function() {
            // 클릭된 div의 postingIdx를 가져옵니다.
            var postingIdx = this.querySelector('[data-posting-idx]').getAttribute('data-posting-idx');
            
            // fetch를 사용하여 서버에 요청을 보냅니다.
            fetch('/postingView?postingIdx=' + postingIdx)
                .then(function(response) {
                    // 응답을 텍스트로 변환합니다.
                    return response.text();
                })
                .then(function(html) {
                    // 모달의 본문에 받은 HTML을 설정합니다.
                    document.getElementById('postingModalBody').innerHTML = html;
                    // 모달을 표시합니다.
                    var postingModal = new bootstrap.Modal(document.getElementById('postingModal'));
                    postingModal.show();
                })
                .catch(function(error) {
                    console.error('Error:', error);
                });
        });
    });
});
</script>
</body>
</html>