<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>
	Группа ${group.groupNumber}
	<c:set var="course"
		value="${group.curriculum.commonCurriculum.enrollment.course}" />
	<c:if test="${course != null}">
, ${course}-ый курс
</c:if>
</h1>

<p>
	<a
		href="cur/cur-${group.curriculum.commonCurriculum.idCommonCurriculum}">
		Учебный план ${group.curriculum.skillProfile.eduProgram.eduProgCode}</a>
</p>

<c:if test="${!empty group.students}">
	<h1>Студенты</h1>
	<c:forEach items="${group.students}" var="student">
		<p>
			<a href="../persons/uid-${student.uid}">${student.fullTextName}</a>
		</p>
	</c:forEach>
</c:if>