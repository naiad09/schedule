<%@page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<sec:authorize access="isAnonymous()">
	<c:url value="/login" var="loginUrl" />
    Вы не авторизированны. 
    <a href="${loginUrl}">Войти</a>
</sec:authorize>

<sec:authorize access="isAuthenticated()">

	<c:url value="/persons/uid-${currentUser.uid}" var="profileUrl" />
	<a href="${profileUrl}">${currentUser.fullName}</a>
	<br>

	<sec:authorize access="hasRole('ROLE_STUDENT')">
	   гр. ${currentUser.group.groupNumber}</sec:authorize>

	<sec:authorize access="hasRole('ROLE_LECTURER')">
		<c:forEach items="${currentUser.lecturerJobs}" var="job"
			varStatus="loop">
                ${job.chair.shortName} (<spring:message
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
			<c:if test="${currentUser.admin}">
				<br>
			</c:if>
		</c:if>
		<c:if test="${currentUser.admin}">администратор</c:if>
	</sec:authorize>

	<br>
	<c:url value="/logout" var="logoutUrl" />
	<a href="${logoutUrl}">Выйти</a>
</sec:authorize>