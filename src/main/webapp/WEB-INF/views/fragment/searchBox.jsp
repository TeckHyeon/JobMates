<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<body>
    <c:choose>
        <c:when test="${results != null and results.isEmpty() and isSearched}">
            <p>검색 결과가 없습니다.</p>
        </c:when>
        <c:when test="${results != null and results.isEmpty() and !isSearched}">
            <p>검색 결과가 여기에 나타납니다.</p>
        </c:when>
        <c:when test="${results != null and !results.isEmpty()}">
            <!-- 검색 결과를 보여주는 로직 -->
                    <div class="search-item">
					<c:forEach items="${results}" var="item">
							<span class="resultText row d-block ms-2" onmouseover="this.classList.add('bg-light');" onmouseout="this.classList.remove('bg-light');" style="transition: background-color 0.3s;">${item.jobType}</span>	
					</c:forEach>
				</div>                    
        </c:when>
    </c:choose>
</body>
</html>