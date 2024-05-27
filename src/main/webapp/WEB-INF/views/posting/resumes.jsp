<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인 페이지</title>
    <link rel="icon" type="image/x-icon" href="/images/favicon.png">
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    
    <style>
    section {
	margin-bottom : 100px;
}
        .resume-box {
            border: 1px solid #ccc;
            border-radius: 10px;
            margin: 0 auto;
            margin-left: 20%;
            margin-right: 20%;
            height: 100px;
            position: relative;
            transition: transform 0.3s ease;
            
        }
		
		
		.resume-box:hover {
		    transform: scale(1.015); /* 마우스를 올렸을 때 커지는 효과 */
		}
        

        .resume-box p {
            display: inline-block;
            margin: 0;
        }

        .resume-box button,
        .resume-box a {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
        }

        .resume-box button {
            right: 100px; /* 버튼 사이 간격 조정 */
        }

        .resume-box a {
            right: 10px; /* 오른쪽 여백 조정 */
        }

        .resume-write-container {
            text-align: center;
        }
        
        .resume-write {
  			width: 50%; /* 부모 요소의 가로 길이에 맞추기 위해 100%로 설정 */
			padding-top: 20px;
    		padding-bottom: 20px;
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
        }
    </style>
    
</head>
<body>
<%@include file="/WEB-INF/layouts/header.jsp"%>


<section>
    <article>
    <input type="hidden" id="personSkillCheck" value="${personSkill}">
        <h3 class="mb-3 mt-3" style="text-align:center;"> 내 이력서 관리 </h3>
	<hr>

        <div class="mt-5">
		   <div id="resumes-container">		   
            <c:forEach var="resume" items="${resumes}">
                <div class="resume-box mb-3"  data-resume-idx="${resume.resumeIdx}">
	                <input type="hidden" name="resumeIdx" value="${resume.resumeIdx}">
	                    <p class="m-4">${resume.resumeTitle}</p>
								
					<button id="btn-publish-${resume.resumeIdx}" class="btn btn-publish ${resume.publish == 1 ? 'btn-primary' : 'btn-warning'} mx-1" 
					    data-id="${resume.resumeIdx}" data-status="${resume.publish}">
					    ${resume.publish == 1 ? '공개중' : '비공개중'}
					</button>
	
								
					<a class="btn btn-primary resume-delete" href="/resumeDelete?resumeIdx=${resume.resumeIdx }">삭제</a>
					
                </div>
            </c:forEach>
		  </div>
            <br>
            <div class="resume-write-container mt-4">
                <a href="/resumeWriteForm" id="resumeWriteBtn" class="btn btn-outline-primary resume-write" >이력서 작성</a>
            </div>
        </div>

    </article>
</section>

<div class="modal fade" id="resumeModal" tabindex="-1" aria-labelledby="resumeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="resumeModalLabel">선택하신 이력서</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body" id="resumeModalBody">
        <!-- Content will be loaded here -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>

<%@include file="/WEB-INF/layouts/footer.jsp"%>
<script src="/js/bootstrap.bundle.min.js"></script>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // "이력서 작성" 버튼에 대한 클릭 이벤트 리스너를 추가합니다.
    document.getElementById('resumeWriteBtn').addEventListener('click', function(event) {
        // 기본 이벤트 동작(링크 이동)을 방지합니다.
        event.preventDefault();
        
        // personSkillCheck의 값을 받아옵니다.
        // JSON.parse를 사용하여 문자열을 배열로 변환합니다.
        var personSkill = JSON.parse(document.getElementById('personSkillCheck').value);

        // personSkill이 배열이고, 그 배열이 비어 있는 경우 경고창을 띄웁니다.
        if (Array.isArray(personSkill) && personSkill.length === 0) {
            var userChoice = confirm('기술스택이 없습니다. 기술스택을 추가하시겠습니까?');

            if (userChoice) {
                // 사용자가 '예'를 선택한 경우, /mypageUpdateForm 페이지로 이동합니다.
                window.location.href = '/mypageUpdateForm';
            } else {
                // 사용자가 '아니오'를 선택한 경우, /resumeWriteForm 페이지로 이동합니다.
                window.location.href = '/resumeWriteForm';
            }
        } else {
            // personSkill이 있는 경우, 기본적으로 설정된 링크(/resumeWriteForm)로 이동합니다.
            window.location.href = '/resumeWriteForm';
        }
    });
});
</script>

<script>
document.addEventListener('DOMContentLoaded', function() {
    var resumesContainer = document.getElementById('resumes-container');

    resumesContainer.addEventListener('click', function(event) {
        var target = event.target;

        // 버튼이나 링크 클릭 시 이동하지 않음
        if (target.tagName.toLowerCase() === 'button' || target.tagName.toLowerCase() === 'a') {
            return;
        }

        // 클릭된 요소부터 상위 요소로 거슬러 올라가며 resume-box 클래스를 가진 div 탐색
        while (target != null && !target.classList.contains('resume-box')) {
            target = target.parentElement;
        }

        // resume-box를 찾았다면 페이지 이동 대신 모달 띄우기
        if (target != null) {
            const resumeIdx = target.getAttribute('data-resume-idx');

            fetch('/resumeView?resumeIdx=' + resumeIdx)
                .then(response => response.text())
                .then(html => {
                    // 모달의 내용 채우기
                    document.getElementById('resumeModalBody').innerHTML = html;
                    // 모달 표시
                    var resumeModal = new bootstrap.Modal(document.getElementById('resumeModal'), {});
                    resumeModal.show();
                })
                .catch(error => {
                    console.error('Error fetching resume details:', error);
                    alert('이력서 정보를 불러오는데 실패했습니다.');
                });
        }
    });
});
</script>


<!-- 공개 비공개 -->
<script>
document.addEventListener('DOMContentLoaded', function() {
    document.addEventListener('click', async function(e) {
        if (e.target && e.target.classList.contains('btn-publish')) {
            const button = e.target;
            const resumeIdx = button.getAttribute('data-id');
            const publish = button.getAttribute('data-status');

            // fetch API를 사용하여 상태 변경 요청
            fetch('/resumes/togglePublish', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    resumeIdx: resumeIdx,
                    publish: publish
                }),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('에러 뜸');
                }
                return response.json();
            })
            .then(data => {
                // 버튼 상태와 텍스트 업데이트
                button.setAttribute('data-status', data.newStatus);
                var btnText = (data.newStatus == 1) ? '공개중' : '비공개중';
                
                // 클래스 제거 및 추가
                button.classList.remove('btn-primary', 'btn-warning');
                button.classList.add(data.newStatus == 1 ? 'btn-primary' : 'btn-warning');
                
                // 버튼 텍스트 변경
                button.textContent = btnText;
            })
            .catch(error => {
                console.error('Error:', error);
                alert('상태 변경에 실패했습니다.');
            });
        }
    });
});

</script>

<!-- 이력서 삭제 -->
<script>
document.addEventListener('DOMContentLoaded', function() {
    // 'resume-delete' 클래스를 가진 모든 요소를 선택합니다.
    var deleteButtons = document.querySelectorAll('.resume-delete');
    
    // 각 요소에 대해 클릭 이벤트 리스너를 추가합니다.
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            // 클릭 이벤트가 발생하면, 기본 동작(링크를 통한 페이지 이동)을 중단합니다.
            event.preventDefault();
            
            // 사용자에게 삭제 여부를 묻는 confirm 창을 띄웁니다.
            var confirmResult = confirm('정말로 삭제하시겠습니까?');
            
            // 사용자가 '예'를 선택한 경우에만, 링크의 href 속성에 따라 페이지를 이동시킵니다.
            if (confirmResult) {
                window.location.href = this.getAttribute('href');
            }
        });
    });
});
</script>


</body>
</html>
