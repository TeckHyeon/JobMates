<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<h3 class="mt-3" style="text-align:center;"> 채용 관리 </h3>
<hr class="mb-3">
	<div class="container mt-3">
		<table class="table" id="myTable">
			<thead class="table-secondary">
				<tr>
					<th><input type="checkbox" id="processAll" /></th>
					<th>지원자명</th>
					<th>이력서 제목</th>
					<th>지원일</th>
					<th>지원 공고</th>
					<th>지원 결과</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${apply}" var="apply">
					<tr data-apply-idx="${apply.applyDto.applyIdx}"
						data-apply-status="${apply.applyDto.applyStatus}">
						<td><input type="checkbox" class="processCheckbox" /></td>
						<td>${ apply.personDto.personName }</td>
						<td><a
							href="/applyResumeView/${ apply.resumeDto.resumeIdx }/${ apply.personDto.personIdx }/${apply.postingDto.postingIdx}"
							class="text-dark text-decoration-none">${ apply.resumeDto.resumeTitle }</a></td>
						<td>${ apply.applyDto.createdDate }</td>
						<td><a href="/mainPosting/${ apply.postingDto.postingIdx }"
							class="text-dark text-decoration-none">${ apply.postingDto.postingTitle }</a></td>
						<td class="processTd"
							data-resume-idx="${ apply.resumeDto.resumeIdx }"
							data-person-idx="${ apply.personDto.personIdx }"
							data-posting-idx="${apply.postingDto.postingIdx}"
							style="cursor: pointer;"><c:choose>
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
			<button id="processApplyFail" class="btn btn-danger">일괄 불합격</button>
			<button id="processApplyPass" class="btn btn-success me-3">일괄
				합격</button>
		</div>
	</div>

	<script>
	document.addEventListener('DOMContentLoaded', function() {
	    var processAll = document.getElementById('processAll');
	    var processApplyFail = document.getElementById('processApplyFail');
	    var processApplyPass = document.getElementById('processApplyPass');

	    // '모두 선택' 체크박스 클릭 이벤트
	    document.addEventListener('click', function() {
	        if (event.target.matches('#processAll')) {
		        var isChecked = processAll.checked;
		        var processCheckboxes = document.querySelectorAll('.processCheckbox');
		        processCheckboxes.forEach(function(checkbox) {
		            checkbox.checked = isChecked;
		        });
	        }
	    });

	    // 일괄 처리 요청 함수
	    function processApplications(applyStatus, confirmMessage) {
	        if (!confirm(confirmMessage)) return;

	        var processCheckboxes = document.querySelectorAll('.processCheckbox:checked');
	        processCheckboxes.forEach(function(checkbox) {
	            var tr = checkbox.closest('tr');
	            var applyIdx = tr.getAttribute('data-apply-idx');

	            fetch('/applyProcess/' + applyIdx + '/' + applyStatus, {
	                method: 'PATCH',
	                headers: {
	                    'Content-Type': 'application/json'
	                },
	                body: JSON.stringify({
	                    applyIdx: applyIdx,
	                    applyStatus: applyStatus
	                })
	            })
	            .then(response => {
	                if (!response.ok) {
	                    return response.text().then(text => { throw new Error(text) });
	                }
	                return response.text();
	            })
	            .then(data => {
	                fetch('/companyApply', { method: 'GET' })
	                .then(response => response.text())
	                .then(responseText => {
	                    document.querySelector("#section").innerHTML = responseText;
	                    initializeEventListeners(); // 업데이트 후 이벤트 리스너 다시 설정
	                })
	                .catch(error => {
	                    console.error("업데이트 오류: " + error);
	                });
	            })
	            .catch((error) => {
	                console.error('Error:', error);
	            });
	        });
	    }

	    document.addEventListener('click', function() {
	        if (event.target.matches('#processApplyFail')) {
	        	processApplications(3, '전부 불합격 처리 하시겠습니까?');
	        }
	    });
	    
	    document.addEventListener('click', function() {
	        if (event.target.matches('#processApplyPass')) {
	        	processApplications(2, '전부 합격 처리 하시겠습니까?');
	        }
	    });

        document.addEventListener('click', function(event) {
            if (event.target.matches('.processTd')) {
                var resumeIdx = event.target.getAttribute('data-resume-idx');
                var personIdx = event.target.getAttribute('data-person-idx');
                var postingIdx = event.target.getAttribute('data-posting-idx');
                openPopup('/applyResumeView/' + resumeIdx + '/' + personIdx + '/' + postingIdx);
            }
        });
        
	    // 팝업 열기 함수
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
        
	});

    </script>
</body>
