<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Кафедра &laquo;${chair.fullName}&raquo;</h1>
<c:if test="${!empty chair.lecturerJobs}">
	<h2>Преподаватели кафедры</h2>
	<div id="tableList">
		<c:forEach items="${chair.lecturerJobs}" var="job">
			<div>
				<h3>
					<a href="../persons/uid-${job.lecturer.uid}">${job.lecturer.fullName}</a>
				</h3>
				<p>
					<spring:message code="${job.lecturer.degree}.fullName" />
					<br>
					<spring:message code="${job.jobType}.fullName" />
				</p>
			</div>
		</c:forEach>
	</div>
</c:if>
