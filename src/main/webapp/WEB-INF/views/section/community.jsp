<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판 페이지</title>
</head>
<body>
	<%@include file="/WEB-INF/layouts/header.jsp"%>
	<section id="section">
		<%@ include file="/WEB-INF/views/fragment/communityMain.jsp"%>
	</section>
	<%@include file="/WEB-INF/layouts/footer.jsp"%>
	<script src="/js/communitySearch.js"></script>
</body>
</html>