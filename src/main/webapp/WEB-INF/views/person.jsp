<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>${person.lastName}&nbsp;${person.firstName}&nbsp;${person.middleName}</h1>
<p>
	<c:choose>

		<c:when test="${person.role == 'Student'}">
			<c:set var="course" value="${person.group.curriculum.course}" />
			<c:choose>
				<c:when test="${course == null}">выпускник группы ${person.group.groupNumber},</c:when>
				<c:otherwise>cтудент группы ${person.group.groupNumber}, ${course}-ый курс,</c:otherwise>
			</c:choose> выпуск ${person.group.curriculum.yearEnd}

				</c:when>

		<c:when test="${person.role == 'Lecturer'}">
               ${person.degree.transcript.fullName},<br>
			<c:forEach items="${person.lecturerJobs}" var="job" varStatus="loop">
                   ${job.jobType.transcript.fullName} кафедры
                   ${job.chair.chairShortname}
                   <c:if test="${!loop.last }">
					,<br>
				</c:if>
			</c:forEach>
		</c:when>

		<c:when test="${person.role == 'EduDep'}">
		    учебный отдел
		    <c:if test="${person.faculty != null}">
				<br>диспетчер ${person.faculty.transcript.shortName}
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
	<c:if test="${person.birthday != null}"><br>Дата рождения: ${person.birthday}</c:if>

</p>
