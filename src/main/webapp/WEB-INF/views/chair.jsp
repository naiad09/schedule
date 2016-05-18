<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Кафедра "${chair.fullName}"</h1>
<p>
	<spring:message code="${chair.faculty}.fullName" />
</p>
<c:if test="${!empty chair.lecturerJobs}">
	<h2>Преподаватели кафедры</h2>
	<table>
		<c:forEach items="${chair.lecturerJobs}" var="job">
			<tr>
				<td><h3>
						<a href="/persons/uid${job.lecturer.uid}">${job.lecturer.fullName}</a>
					</h3>
					<p>
						<spring:message code="${job.lecturer.degree}.fullName" />
						<br>
						<spring:message code="${job.jobType}.fullName" />
					</p></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
