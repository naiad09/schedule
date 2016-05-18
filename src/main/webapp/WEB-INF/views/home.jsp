<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<h1>Факультеты и кафедры</h1>
<table class="data">
	<c:forEach items="${chairsMap}" var="faculty">
		<h2>
			<a href="${faculty.key}"><spring:message
					code="${faculty.key}.fullName" /></a>
		</h2>
		<c:set var="chairs" value="${faculty.value}" scope="request" />
		<t:insertTemplate template="level2/chairs.jsp" />
	</c:forEach>

</table>
