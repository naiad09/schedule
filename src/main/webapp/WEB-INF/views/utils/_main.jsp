<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">

<link
	href="${pageContext.request.contextPath}/resources/style/style.css"
	rel="stylesheet" />
<link rel="stylesheet/less" type="text/css"
	href="${pageContext.request.contextPath}/resources/style/style.less">

<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/less.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/dynamicList.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/formHider.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/selectorFinder.js"></script>

<c:set var="urlMatches" scope="request"
	value="${fn:split(requestScope['org.springframework.web.servlet.HandlerMapping.bestMatchingPattern'], '/')}" />
<c:set var="urlParts" scope="request"
	value="${fn:split(requestScope['javax.servlet.forward.servlet_path'],'/')}" />

<c:set value="${fn:length(urlMatches)}" var="urlLength" />

<title>РГРТУ<c:choose>
		<c:when test="${urlLength>0}">
			<c:forEach var="m" items="${urlMatches}" varStatus="i"
				begin="${(urlLength>2)?(urlLength-2):0}"> -
        <spring:message var="code" code="${m}" />
				<spring:eval expression="${code}" />
			</c:forEach>
		</c:when>
		<c:otherwise>- Расписание занятий и сессии</c:otherwise>
	</c:choose></title>

<script>
	RegExp.escape = function(s) {
		return s.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
	}
</script>
</head>
<body id="${(urlLength>0)?urlMatches[urlLength-1]:'startPage'}">
	<div id="page">
		<div id="header">
			<div id="title">
				<a href="${pageContext.request.contextPath}">Рязанский
					Государственный Радиотехнический Университет</a>
			</div>
			<div id="login">
				<t:insertAttribute name="loginWidget" />
			</div>
		</div>
		<h3 id="topPagination">
			<t:insertTemplate template="pagination.jsp" />
		</h3>
		<div id="main">
			<t:insertAttribute name="content" />
		</div>
		<h3 id="bottomPagenation">
			<t:insertTemplate template="pagination.jsp" />
		</h3>
		<div id="footer"></div>
	</div>
</body>
</html>