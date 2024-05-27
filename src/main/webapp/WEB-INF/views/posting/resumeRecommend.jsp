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
        .posting-box {
            border: 1px solid #ccc;
            border-radius: 10px;
            margin: 0 auto;
            margin-left: 20%;
            margin-right: 20%;
            height: 100px;
            position: relative;
            transition: transform 0.3s ease;
        }
		
		
		.posting-box:hover {
		    transform: scale(1.015); /* 마우스를 올렸을 때 커지는 효과 */
		}
        
	
        .posting-box p {
            display: inline-block;
            margin: 0;
        }

        .posting-box button,
        .posting-box a {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
        }

        .posting-box button {
            right: 100px; /* 버튼 사이 간격 조정 */
        }

        .posting-box a {
            right: 10px; /* 오른쪽 여백 조정 */
        }

        .posting-write-container {
            text-align: center;
        }
        
        
        .resume-container {
            border: 1px solid #ccc; /* 테두리 추가 */
			border-top:none;
            margin: 0px auto; /* 위아래 마진 추가 */
            padding: 20px; /* 내부 여백 추가 */
            width: 58%; /* 너비 설정 */
            background-color : #f8f9fa;
        }
        
         .resume-box {
            border: 1px solid #ccc;
            border-radius: 10px;
            margin: 0 auto;
            height: 100px;
            position: relative;
            transition: transform 0.3s ease;
            background-color : white;
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
	<h3 class="mb-3 mt-3" style="text-align:center;"> 공고별 추천 인재 </h3>
	<hr>

<article>
    <input type="hidden" name="companyIdx" id="companyIdx" value="${companyIdx}">
    <div class="mt-5">
        <div id="postings-container">
            <c:forEach var="entry" items="${resumeRecMap}">
                <c:set var="postingIdx" value="${entry.key}"/>
                <c:set var="resumeRecList" value="${entry.value}"/>
                <c:if test="${not empty resumeRecList}">
                    <div class="posting-box mt-4" data-postings-idx="${postingIdx}" style="cursor: pointer;">
                        <input type="hidden" name="postingIdx" value="${postingIdx}">
                        <div class="m-4">${resumeRecList[0].postingTitle}</div>
                        <div class="m-4">마감일: ${resumeRecList[0].postingDeadline}</div>
<a class="btn btn-primary posting-view" href="javascript:void(0);" data-posting-idx="${postingIdx}">공고 확인</a>
                    </div>
                    <!-- 현재 공고에 해당하는 이력서 목록 표시 -->
                    <div class="resume-container" style="display: none;">
                        <c:forEach var="resumeItem" items="${resumeRecList}">
                            <div class="resume-box mb-3" data-resume-idx="${resumeItem.resumeIdx}" data-person-idx="${resumeItem.personIdx}">
                                <input type="hidden" name="personIdx" value="${resumeItem.personIdx}">
                                <input type="hidden" name="resumeIdx" value="${resumeItem.resumeIdx}">
                                <p class="m-4">${resumeItem.personName}</p>
                                <p class="m-4">${resumeItem.resumeTitle}</p>
                                <button class="btn btn-outline-secondary d-flex align-items-center ms-3 scrapBtn">
                                    <svg class="w-6 h-6 text-gray-800 dark:text-white scrapSvg me-2"
                                        data-resume-idx="${resumeItem.resumeIdx}"
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
                </c:if>
            </c:forEach>
        </div>
        <br>
    </div>
</article>
</section>

<!-- 모달 -->
<div class="modal fade" id="postingModal" tabindex="-1" aria-labelledby="postingModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="postingModalLabel">공고 상세</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body" id="postingModalBody">
        <!-- 내용이 AJAX로 여기에 로드됩니다 -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>



<%@ include file="/WEB-INF/layouts/footer.jsp" %>
<script src="/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        var postingBoxes = document.querySelectorAll('.posting-box');

        postingBoxes.forEach(function (box) {
            box.addEventListener('click', function () {
                var resumeContainer = this.nextElementSibling;
                var displayStatus = resumeContainer.style.display;
                resumeContainer.style.display = displayStatus === 'block' ? 'none' : 'block';
            });
        });

        const companyIdx = document.getElementById('companyIdx').value;

        function updatescrapSvgs() {
            const scrapSvgs = document.querySelectorAll('.scrapSvg');
            scrapSvgs.forEach(function (button) {
                const resumeIdx = button.getAttribute('data-resume-idx');
                fetch(`/checkResumeScrap?resumeIdx=` + resumeIdx + `&companyIdx=` + companyIdx, {
                    method: 'GET',
                })
                    .then(response => response.json())
                    .then(isScraped => {
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

        updatescrapSvgs(); // 페이지 로드 시 스크랩 버튼 상태 갱신

        document.addEventListener('click', async function (e) {
            const button = e.target.closest('.scrapBtn');
            if (button) {
                e.preventDefault();
                e.stopPropagation();
                const scrapSvg = button.querySelector('.scrapSvg');
                const resumeIdx = scrapSvg.getAttribute('data-resume-idx');
                const isScraped = scrapSvg.getAttribute('data-scraped') === 'true';

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

            const postingViewBtn = e.target.closest('.posting-view');
            if (postingViewBtn) {
                e.preventDefault();
                e.stopPropagation();
                const postingIdx = postingViewBtn.getAttribute('data-posting-idx');
                fetch(`/postingView?postingIdx=` + postingIdx)
                    .then(response => response.text())
                    .then(html => {
                        document.getElementById('postingModalBody').innerHTML = html;
                        var postingModal = new bootstrap.Modal(document.getElementById('postingModal'));
                        postingModal.show();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('오류가 발생했습니다. 다시 시도해주세요.');
                    });
            }

            const clickedElement = e.target.closest('.resume-box');
            if (clickedElement) {
                const resumeIdx = clickedElement.getAttribute('data-resume-idx');
                const personIdx = clickedElement.getAttribute('data-person-idx');
                window.location.href = '/resumeRecommendView?resumeIdx=' + resumeIdx + '&personIdx=' + personIdx;
            }
        });
    });
</script>


</body>
</html>