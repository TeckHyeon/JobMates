<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인 페이지</title>
    <link rel="icon" type="image/x-icon" href="/images/favicon.png">
    <link href="/css/bootstrap.min.css" rel="stylesheet" />
    <script src="/js/bootstrap.bundle.min.js"></script>
    <style>
        /* 추가적인 CSS 스타일링 */
        body {
            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
            background-color: #f8f9fa;
            color: #343a40;
        }
        footer {
            position: fixed;
            bottom: 0;
            width: 100%;
            padding: 20px 0;
            text-align: center;
            background-color: #343a40;
            color: #ffffff;
        }
        header {
            background-color: #e0f7fa;
        }
        .collapse {
            margin-top: 10px;
        }
        .selected {
            font-weight: bold;
        }
        .faq-question {
            border-bottom: 1px solid #ccc;
            padding: 10px 0;
            cursor: pointer;
            margin-bottom: 10px;
        }
        .faq-question.selected {
            background-color: #D1E2FF;
        }
        .accordion-body .collapse {
            background-color: #ECEBE5;
            padding: 10px;
            margin-bottom: 10px;
        }
        .accordion-button.selected {
            font-weight: bold;
        }
        .faq-title {
            font-size: 2em;
            font-weight: bold;
            text-align: center;
            margin: 20px 0;
            color: #007bff;
        }
        .accordion-button {
            background-color: #007bff;
            color: #ffffff;
        }
        .accordion-button.collapsed {
            background-color: #e9ecef;
            color: #343a40;
        }
        .accordion-button:focus {
            border-color: #007bff;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/layouts/header.jsp" %>
    <section>
    <div class="container">
        <div class="faq-title" >자주 하는 질문</div>
        <div class="accordion" id="faqAccordion">
            <c:forEach var="type" items="${faqlist}" varStatus="status">
                <c:if test="${status.first || faqlist[status.index - 1].faqType != type.faqType}">
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="heading${type.faqType}">
                            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${type.faqType}" aria-expanded="false" aria-controls="collapse${type.faqType}">
                                <c:choose>
                                    <c:when test="${type.faqType == 1}">회원가입 정보</c:when>
                                    <c:when test="${type.faqType == 2}">이력서관리 활용</c:when>
                                    <c:when test="${type.faqType == 3}">입사지원</c:when>
                                    <c:when test="${type.faqType == 4}">채용정보</c:when>
                                    <c:otherwise>기타</c:otherwise>
                                </c:choose>
                            </button>
                        </h2>
                        <div id="collapse${type.faqType}" class="accordion-collapse collapse" aria-labelledby="heading${type.faqType}" data-bs-parent="#faqAccordion">
                            <div class="accordion-body">
                                <c:forEach var="faq" items="${faqlist}">
                                    <c:if test="${faq.faqType == type.faqType}">
                                        <div class="faq-question" data-bs-toggle="collapse" data-bs-target="#answer${faq.faqIdx}" aria-expanded="false" aria-controls="answer${faq.faqIdx}">
                                            ${faq.question}
                                        </div>
                                        <div id="answer${faq.faqIdx}" class="collapse" style="font-size: 14px">${faq.answer}</div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        </div>
    </section>
    <%@ include file="/WEB-INF/layouts/footer.jsp" %>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const faqAccordion = document.querySelector('#faqAccordion');

            // 클래스 토글 함수 정의
            function toggleSelectedClass(target, className) {
                document.querySelectorAll('.' + className).forEach(function(element) {
                    element.classList.remove('selected');
                });
                target.classList.add('selected');
            }

            faqAccordion.addEventListener('click', function(e) {
                if (e.target.classList.contains('faq-question')) {
                    // 모든 collapse를 닫음
                    document.querySelectorAll('.accordion-body .collapse').forEach(function(element) {
                        new bootstrap.Collapse(element, {
                            toggle: false
                        }).hide();
                    });

                    // 선택된 collapse를 엶
                    const targetCollapse = document.querySelector(e.target.getAttribute('data-bs-target'));
                    new bootstrap.Collapse(targetCollapse, {
                        toggle: false
                    }).show();

                    toggleSelectedClass(e.target, 'faq-question');
                }

                if (e.target.classList.contains('accordion-button')) {
                    toggleSelectedClass(e.target, 'accordion-button');
                }
            });
        });
    </script>
</body>
</html>
