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
        
		.resume-box button {
		    position: absolute; /* 버튼을 절대 위치로 설정 */
		    right: 10px; /* 오른쪽 끝에서부터 10px 떨어진 곳에 위치 */
		    top: 50%; /* 상단에서부터 50% 위치, 버튼을 상하 중앙에 위치시키기 위함 */
		    transform: translateY(-50%); /* 버튼을 상하 중앙에 정확히 위치시키기 위해 Y축으로 -50% 이동 */
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

	<h2 class="mt-3 mb-3" style="text-align:center;">관심 인재</h2>
	<hr>

<input type="hidden" name=companyIdx id=companyIdx value=${companyIdx }>
	
	            
<div class="resume-container">
	<c:forEach var="RScrapList" items="${RScrapList}">
		<div class="resume-box mb-3" data-resume-idx="${RScrapList.resumeIdx}" data-person-idx="${RScrapList.personIdx}">
		                <input type="hidden" id="resumeIdx" name="resumeIdx" value="${RScrapList.resumeIdx}">
		                <input type="hidden" name="userIdx" value="${RScrapList.userIdx}">
		                <input type="hidden" name="personIdx" value="${RScrapList.personIdx}">
		                
		                <p class="m-4">${RScrapList.personName}</p>	                
		                <p class="m-4">${RScrapList.resumeTitle}</p>
			<button class="btn btn-outline-secondary d-flex align-items-center ms-3 scrapBtn">
			    <svg class="w-6 h-6 text-gray-800 dark:text-white scrapSvg me-2"
			        data-resume-idx="${RScrapList.resumeIdx}"
			            aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
			            width="24" height="24" fill="none" viewBox="0 0 24 24">
			            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
			                d="m17 21-5-4-5 4V3.889a.92.92 0 0 1 .244-.629.808.808 0 0 1 .59-.26h8.333a.81.81 0 0 1 .589.26.92.92 0 0 1 .244.63V21Z" />
			        </svg>
			        <span>스크랩</span>
			</button>
		</div>
	</c:forEach>
</div>

<!-- 모달 -->
<div class="modal fade" id="resumeModal" tabindex="-1" aria-labelledby="resumeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="resumeModalLabel">이력서 상세</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body" id="resumeModalBody">
        <!-- 내용이 AJAX로 여기에 로드됩니다 -->
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
document.addEventListener("DOMContentLoaded", function() {
    const companyIdx = document.getElementById('companyIdx').value;

    function updatescrapSvgs() {
        const scrapSvgs = document.querySelectorAll('.scrapSvg');
        scrapSvgs.forEach(function(button) {
            const resumeIdx = button.getAttribute('data-resume-idx');

            fetch(`/checkResumeScrap?resumeIdx=` + resumeIdx + `&companyIdx=` + companyIdx, {
                method: 'GET',
            })
            .then(response => response.json())
            .then(isScraped => {
                console.log('Scrap status for resumeIdx:', resumeIdx, 'is', isScraped);
                button.setAttribute('data-scraped', isScraped);
                if (isScraped) {
                    button.setAttribute('fill', 'yellow');
                } else {
                    button.setAttribute('fill', 'none');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });
    }

    updatescrapSvgs();

    document.addEventListener('click', async function(e) {
        const button = e.target.closest('.scrapBtn');
        if (button) {
            e.preventDefault();
            e.stopPropagation();
            const scrapSvg = button.querySelector('.scrapSvg');
            const resumeIdx = scrapSvg.getAttribute('data-resume-idx');
            const isScraped = scrapSvg.getAttribute('data-scraped') === 'true';
            console.log('Button clicked for resumeIdx:', resumeIdx, 'isScraped:', isScraped);
            
            try {
                let response;
                if (isScraped) {
                    response = await fetch(`/resumeScrapDelete`, {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({ resumeIdx, companyIdx }),
                    });
                } else {
                    response = await fetch('/resumeScrapAdd', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({ resumeIdx, companyIdx }),
                    });
                }

                if (response.ok) {
                    const message = isScraped ? '스크랩이 해제되었습니다.' : '스크랩되었습니다.';
                    alert(message);
                    updatescrapSvgs();
                } else {
                    throw new Error('error.');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            }
            return;
        }

        const clickedElement = e.target.closest('.resume-box');
        if (clickedElement) {
            e.preventDefault();
            e.stopPropagation();
            const resumeIdx = clickedElement.getAttribute('data-resume-idx');
            const personIdx = clickedElement.getAttribute('data-person-idx');
            
            fetch(`/RScrapView?resumeIdx=` + resumeIdx + '&personIdx=' + personIdx)
                .then(response => response.text())
                .then(html => {
                    document.getElementById('resumeModalBody').innerHTML = html;
                    var resumeModal = new bootstrap.Modal(document.getElementById('resumeModal'));
                    resumeModal.show();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('오류가 발생했습니다. 다시 시도해주세요.');
                });
        }
    });
});
</script>


	
</body>
</html>