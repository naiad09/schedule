<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<link href="${pageContext.request.contextPath}/resources/style.css"
	rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/jquery.min.js"
	type="text/javascript"></script>
<title>РГРТУ - <t:insertAttribute name="title" /></title>
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