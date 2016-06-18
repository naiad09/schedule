<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<a href="${pageContext.request.contextPath}">Главная страница</a>
<c:forEach var="urlPart" items="${urlParts}" varStatus="i"> &raquo;
    <c:set var="urlPlus" value="${urlPlus}/${urlPart}" />
	<a href="${pageContext.request.contextPath}${urlPlus}"> <spring:message
			var="code" code="${urlMatches[i.index]}" /> <spring:eval
			expression="${code}" /></a>
</c:forEach>
