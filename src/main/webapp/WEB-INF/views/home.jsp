<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<h1>Добро пожаловать в систему электронного расписания РГРТУ!</h1>

<p>
	Система электронного расписания предназначена для просмотра расписания
	<br>учебных занятий и графика проведения экзаменов, а также их
	составления и публикации.
</p>

<h2>${currentSemester.semesterYear}/${currentSemester.semesterYear+1}-ый&nbsp;учебный&nbsp;год,
	${currentSemester.fallSpring?'весна':'осень'}, числитель</h2>
<c:forEach items="${refsContainer.faculties}" var="faculty">
	<p>
		<a href="${faculty}"><spring:message code="${faculty}.fullName" /></a>
	</p>
</c:forEach>
