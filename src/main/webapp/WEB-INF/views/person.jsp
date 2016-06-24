<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>

<h1>${person.fullTextName}</h1>
<c:if test="${param.saved == true}">
	<p class="alert success">Профиль обновлен.</p>
</c:if>
<c:if test="${person.authData!=null}">
	<c:url value="/persons/uid-${person.uid}/edit" var="editUrl" />
	<sec:authorize
		access="hasRole('ROLE_ADMIN') or (isAuthenticated() and principal.uid==#personId)">
		<div id="edit">
			<a href="${editUrl}">Редактировать профиль</a>
		</div>
	</sec:authorize>
</c:if>
<p>
	<c:choose>

		<c:when test="${person.role == 'student'}">
			<c:set var="course"
				value="${person.group.curriculum.commonCurriculum.enrollment.course}" />
			<c:choose>
				<c:when test="${course == null}">выпускник группы ${person.group.groupNumber},</c:when>
				<c:otherwise>cтудент группы ${person.group.groupNumber}, ${course}-ый курс,</c:otherwise>
			</c:choose> выпуск ${person.group.curriculum.commonCurriculum.enrollment.yearEnd}
        </c:when>

		<c:when test="${person.role == 'lecturer'}">
			<spring:message code="${person.degree}.fullName" />
			<br>
			<c:forEach items="${person.lecturerJobs}" var="job" varStatus="loop">
				<spring:message code="${job.jobType}.fullName" />
				<a
					href="${baseUrl}/${job.chair.faculty}/${job.chair.shortNameEng}">
					кафедры ${job.chair.shortName}</a>
				<c:if test="${!loop.last }">
					,<br>
				</c:if>
			</c:forEach>
		</c:when>

		<c:when test="${person.role == 'edudep'}">
			<a href="${baseUrl}/ed">учебный отдел</a>
			<c:if test="${person.faculty != null}">
				<br>диспетчер <a
					href="${baseUrl}/${job.chair.faculty}"><spring:message
						code="${person.faculty}.shortName" /></a>
			</c:if>
		</c:when>

	</c:choose>
</p>

<p>
	Пол:
	<c:choose>
		<c:when test="${person.gender == 'm'}">мужской</c:when>
		<c:otherwise>женский</c:otherwise>
	</c:choose>
	<c:if test="${person.birthday != null}">
		<br>Дата рождения: <fmt:formatDate value="${person.birthday}"
			pattern="d MMMM yyyy" />
	</c:if>

</p>
