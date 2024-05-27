<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<h3 class="mt-3" style="text-align: center;">지원 관리</h3>
	<hr class="mb-3">
	<div class="container mt-3">
		<table class="table" id="myTable">
			<thead class="table-secondary">
				<tr>
					<th><input type="checkbox" id="deleteAll" /></th>
					<th>회사명</th>
					<th>공고 제목</th>
					<th>지원일</th>
					<th>지원 이력서</th>
					<th>지원 결과</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${apply}" var="apply">
					<tr data-apply-idx="${apply.applyDto.applyIdx}">
						<td><input type="checkbox" class="deleteCheckbox" /></td>
						<td>${ apply.companyDto.companyName }</td>
						<td><a href="/mainPosting/${ apply.postingDto.postingIdx }"
							class="text-dark text-decoration-none">${ apply.postingDto.postingTitle }</a></td>
						<td>${ apply.applyDto.createdDate }</td>
						<td><a href="javascript:void(0);"
							onclick="openPopup('/resumeView?resumeIdx=${apply.resumeDto.resumeIdx}')"
							class="text-dark text-decoration-none">${apply.resumeDto.resumeTitle}</a>
						</td>

						<td><c:choose>
								<c:when test="${apply.applyDto.applyStatus == 1}">
                                    미처리
                                </c:when>
								<c:when test="${apply.applyDto.applyStatus == 2}">
                                    합격
                                </c:when>
								<c:when test="${apply.applyDto.applyStatus == 3}">
                                    불합격
                                </c:when>
								<c:otherwise>
                                    상태 미정
                                </c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="d-flex flex-row-reverse">
			<button id="deleteApply" class="btn btn-outline-danger">지원
				취소</button>
		</div>
	</div>

	<script>
	
    function openPopup(url) {
        var screenWidth = window.screen.width;
        var screenHeight = window.screen.height;
        var windowWidth = screenWidth * 0.7;
        var windowHeight = screenHeight * 0.7;
        var left = (screenWidth - windowWidth) / 2;
        var top = (screenHeight - windowHeight) / 2;
        var options = 'width=' + windowWidth + ',height=' + windowHeight + ',left=' + left + ',top=' + top;
        window.open(url, 'resumeView', options);
    }
	
        document.addEventListener('DOMContentLoaded', function() {
            var deleteAll = document.getElementById('deleteAll');
            var deleteCheckboxes = document.querySelectorAll('.deleteCheckbox');
            var deleteApply = document.getElementById('deleteApply');

            // '모두 선택' 체크박스 클릭 이벤트
            deleteAll.addEventListener('click', function() {
                var isChecked = this.checked;
                deleteCheckboxes.forEach(function(checkbox) {
                    checkbox.checked = isChecked;
                });
            });

            // '삭제' 버튼 클릭 이벤트
            deleteApply.addEventListener('click', function() {
                deleteCheckboxes.forEach(function(checkbox) {
                    if (checkbox.checked) {
                        var tr = checkbox.closest('tr');
                        var applyIdx = tr.getAttribute('data-apply-idx');
                        
                        // 서버로 지원 취소 요청을 보냄
                        fetch('/applycancel/'+applyIdx, {
                            method: 'DELETE', // 또는 'DELETE', API 설계에 따라 달라질 수 있음
                            headers: {
                                'Content-Type': 'application/json',
                            },
                        })
                        .then(data => {
                            console.log('Success:', data);
                            // 성공적으로 처리된 후 UI 업데이트, 예를 들어 선택된 항목 제거
                            tr.remove();
                        })
                        .catch((error) => {
                            console.error('Error:', error);
                        });
                    }
                });
            });
            
  

        });
    </script>
</body>