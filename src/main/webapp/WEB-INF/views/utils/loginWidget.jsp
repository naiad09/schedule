<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<sec:authorize access="isAnonymous()">
    Вы не авторизированны. 
    <a href="${pageContext.request.contextPath}/login">Войти</a>
</sec:authorize>

<sec:authorize access="isAuthenticated()">

	<a href="${pageContext.request.contextPath}/profile">
		${currentUser.fullName} </a>
	<br>

	<sec:authorize access="hasRole('ROLE_STUDENT')">
	   гр. ${currentUser.group.groupNumber}</sec:authorize>

	<sec:authorize access="hasRole('ROLE_LECTURER')">
		<c:forEach items="${currentUser.lecturerJobs}" var="job"
			varStatus="loop">
                ${job.chair.chairShortname} (<spring:message
				code="${job.jobType}.shortName" />)
            <c:if test="${!loop.last }">
				<br>
			</c:if>
		</c:forEach>
	</sec:authorize>

	<sec:authorize access="hasRole('ROLE_EDUDEP')">
		<c:if test="${!empty currentUser.faculty}">
		      диспетчер <spring:message
				code="${currentUser.faculty}.shortName" />
		</c:if>
	</sec:authorize>

	<br>
	<a href="${pageContext.request.contextPath}/logout">Выйти</a>
</sec:authorize>