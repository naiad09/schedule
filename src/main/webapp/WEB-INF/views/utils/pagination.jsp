<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${fn:length(urlParts)==fn:length(urlMatches)}">
	<a href="${baseUrl}">Главная страница</a>
	<c:forEach var="urlPart" items="${urlParts}" varStatus="i"> &raquo;
    <c:set var="urlPlus" value="${urlPlus}/${urlPart}" />
		<a href="${baseUrl}${urlPlus}"> <spring:message
				var="code" code="${urlMatches[i.index]}" /> <spring:eval
				expression="${code}" /></a>
	</c:forEach>
</c:if>
