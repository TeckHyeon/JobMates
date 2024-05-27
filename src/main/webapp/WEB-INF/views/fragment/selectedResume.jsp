<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
	<p class="fs-5 fw-bold mt-1">지원 이력서</p>
	<hr>
	<input type="hidden" value="${resume.resumeIdx}" name="resumeIdx" id="resumeIdx">
	<p id="resumeTitle">${resume.resumeTitle}</p>
	<p id="resumeComment ">${resume.resumeComment}</p>
	<p id="createdDate ">${resume.createdDate}</p>
</body>
</html>