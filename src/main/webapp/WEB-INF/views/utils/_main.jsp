<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">

<link href="${pageContext.request.contextPath}/resources/style/style.css"
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

<title>РГРТУ - <t:insertAttribute name="title" /></title>

<script>
	RegExp.escape = function(s) {
		return s.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
	}
</script>
</head>
<body>
	<div id="page">
		<div id="header">
			<div id="title">
				<a href="${pageContext.request.contextPath}/">Рязанский
					Государственный Радиотехнический Университет</a>
			</div>
			<div id="login">
				<t:insertAttribute name="loginWidget" />
			</div>
		</div>
		<div id="main">
			<t:insertAttribute name="content" />
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>