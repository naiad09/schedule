<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Все семестры</h1>

<c:forEach items="${semesters}" var="s">
	<p>
		<a href="ed/semester-${s.idSemester}">${s.semesterYear}/${s.semesterYear+1}-ый учебный год,
			${s.fallSpring?'весна':'осень'}</a>
	</p>
</c:forEach>

<h3><a href="ed/new-semester">Создать новый семестр</a></h3>