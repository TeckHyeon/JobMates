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
        
         .posting-write {
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
    <input type="hidden" id="companyValue1" value="${company.companySector}">
    <input type="hidden" id="companyValue2" value="${company.companyEmp}">
    <input type="hidden" id="companyValue3" value="${company.companySize}">
    <input type="hidden" id="companyValue4" value="${company.companyPhone}">
    <input type="hidden" id="companyValue5" value="${company.companyMgrName}">
    <input type="hidden" id="companyValue6" value="${company.companyMgrPhone}">
    <h3 class="mb-3 mt-3" style="text-align:center;"> 내 공고 관리 </h3>
	<hr>

        <div class="mt-5">
		   <div id="postings-container">		   
            <c:forEach var="posting" items="${posting}">
                <div class="posting-box mb-3"  data-postings-idx="${posting.postingIdx}">
	                <input type="hidden" name="postingIdx" value="${posting.postingIdx}">
	                    <div class="m-4">${posting.postingTitle}</div>
	                    <div class="m-4">마감일 : ${posting.postingDeadline }</div>
						
					<a class="btn btn-primary posting-delete" href="/postingDelete?postingIdx=${posting.postingIdx }">삭제</a>
					
                </div>
            </c:forEach>
		  </div>
            <br>
            <div class="posting-write-container">
                <a href="/postingWriteForm" id="postingWriteBtn" class="btn btn-outline-primary posting-write" >공고 작성</a>
            </div>
        </div>

    </article>
</section>

<!-- Modal Structure -->
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


<%@include file="/WEB-INF/layouts/footer.jsp"%>
<script src="/js/bootstrap.bundle.min.js"></script>


<script>
$(document).ready(function() {
    // 게시물 박스 클릭 이벤트
    $('#postings-container').on('click', '.posting-box', function(e) {
        // 삭제 버튼 클릭 이벤트가 버블링되는 것을 방지
        if (!$(e.target).closest('.posting-delete').length) {
            var postingIdx = $(this).data('postings-idx'); // 게시물 인덱스 가져오기

            // AJAX 요청으로 postingView.jsp 내용을 가져옴
            $.ajax({
                url: '/postingView',
                type: 'GET',
                data: { postingIdx: postingIdx },
                success: function(response) {
                    // 모달의 내용 채우기
                    $('#postingModalBody').html(response);
                    // 모달 표시
                    var postingModal = new bootstrap.Modal(document.getElementById('postingModal'), {});
                    postingModal.show();
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching posting details:', error);
                    alert('공고 정보를 불러오는데 실패했습니다.');
                }
            });
        }
    });
});
</script>


<!-- 공고 삭제 -->
<script>
document.addEventListener('DOMContentLoaded', function() {
    // 'posting-delete' 클래스를 가진 모든 요소를 선택합니다.
    var deleteButtons = document.querySelectorAll('.posting-delete');
    
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

<!-- 회사정보에 비어있는 정보 확인 -->
<script>
    // 회사 정보 값 확인 함수
    function checkCompanyValues() {
        var companyValue1 = $('#companyValue1').val();
        var companyValue2 = $('#companyValue2').val();
        var companyValue3 = $('#companyValue3').val();
        var companyValue4 = $('#companyValue4').val();
        var companyValue5 = $('#companyValue5').val();
        var companyValue6 = $('#companyValue6').val();

        return [companyValue1, companyValue2, companyValue3, companyValue4, companyValue5, companyValue6].some(value => value === null || value === '');
    }

    // 공고 작성 버튼 클릭 이벤트
    $('#postingWriteBtn').click(function(event) {
        if (checkCompanyValues()) {
            event.preventDefault(); // 기본 동작 중단

            var confirmResult = confirm('채워지지 않은 정보가 있습니다. 추가 하시겠습니까?');
            if (confirmResult) {
                window.location.href = '/mypageCompanyUpdateForm';
            } else {
                window.location.href = '/postingWriteForm';
            }
        }
    });
</script>

</body>
</html>
