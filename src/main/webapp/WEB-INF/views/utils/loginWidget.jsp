<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<sec:authorize access="isAnonymous()">
    Вы не авторизированны. 
    <a href="${pageContext.request.contextPath}/login">Войти</a>
</sec:authorize>

<sec:authorize access="isAuthenticated()">

	<a
		href="${pageContext.request.contextPath}/persons/uid${currentUser.uid}">
		${currentUser.fullName} </a>
	<br>

	<sec:authorize access="hasRole('ROLE_STUDENT')">
	   гр. ${currentUser.group.groupNumber}</sec:authorize>

	<sec:authorize access="hasRole('ROLE_LECTURER')">
		<c:forEach items="${currentUser.lecturerJobs}" var="job"
			varStatus="loop">
                ${job.chair.chairShortname} (${job.jobType.transcript.shortName})
            <c:if test="${!loop.last }">
				<br>
			</c:if>
		</c:forEach>
	</sec:authorize>

	<sec:authorize access="hasRole('ROLE_EDUDEP')">
		<c:if test="${!empty currentUser.faculty}">
		      диспетчер ${currentUser.faculty.transcript.shortName}
		</c:if>
	</sec:authorize>

	<br>
	<a href="${pageContext.request.contextPath}/logout">Выйти</a>
</sec:authorize>