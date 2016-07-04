<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<h1>
	Группа ${group.groupNumber}
	<c:set var="course"
		value="${group.curriculum.commonCurriculum.enrollment.course}" />
	<c:if test="${course != null}">
, ${course}-ый курс
</c:if>
</h1>

<h3>
	<a
		href="${baseUrl}/cur/cur-${group.curriculum.commonCurriculum.idCommonCurriculum}">
		Учебный план ${group.curriculum.skillProfile.eduProgram.eduProgCode}</a>
</h3>

<c:if test="${!empty group.students}">
	<h2>Студенты</h2>
	<div id="tableList">
		<c:forEach items="${group.students}" var="student">
			<div>
				<h3>
					<a href="${baseUrl}/persons/uid-${student.uid}">${student.fullTextName}</a>
				</h3>
			</div>
		</c:forEach>
	</div>
</c:if>

<c:if test="${schedule!=null}">
	<h2>Расписание на текущий семестр</h2>
	<t:insertTemplate template="level2/showSchedule.jsp" />
</c:if>