<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">

<c:set value="${pageContext.request.contextPath}" var="baseUrl"
	scope="request" />

<link href="${baseUrl}/resources/style/style.css" rel="stylesheet" />
<link rel="stylesheet/less" type="text/css"
	href="${baseUrl}/resources/style/style.less">

<script src="${baseUrl}/resources/js/jquery-3.0.0.js"></script>
<script src="${baseUrl}/resources/js/less.min.js"></script>

<script src="${baseUrl}/resources/js/dynamicList.js"></script>
<script src="${baseUrl}/resources/js/formHider.js"></script>
<script src="${baseUrl}/resources/js/selectorFinder.js"></script>

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
<body
	id="${(urlLength>0)?fn:replace(fn:replace(urlMatches[urlLength-1],'{',''),'}',''):'startPage'}">

	<div id="page">
		<div id="header">
			<a href="${baseUrl}"><img src="${baseUrl}/resources/img/logo.png" id="logo"></a>
			<div id="title">
				Рязанский Государственный Радиотехнический Университет <br> <span
					style="font-size: 2.1em">Электронное расписание</span><br> <a
					href="${baseUrl}/ed">Учебный отдел</a> &laquo; <a
					href="${baseUrl}/persons">Пользователи</a>
			</div>
			<div id="login">
				<t:insertAttribute name="loginWidget" />
			</div>
		</div>
		<h3 id="topPagination" class="pagination">
			<t:insertTemplate template="pagination.jsp" />
		</h3>
		<div id="main">
			<t:insertAttribute name="content" />
		</div>
		<h3 id="bottomPagenation" class="pagination">
			<t:insertTemplate template="pagination.jsp" />
		</h3>
		<div id="footer"></div>
	</div>
</body>
</html>