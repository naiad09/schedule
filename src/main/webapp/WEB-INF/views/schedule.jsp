<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="http://www.springframework.org/security/tags"
    prefix="sec"%>

<sec:authorize access="hasRole('ROLE_EDUDEP')">
    <h3 style="float: right">
        <a href="schedule-${schedule.idSchedule}/edit">Редактировать</a>
    </h3>
</sec:authorize>
<h1>${schedule.eduProcGraphic.semester.semesterYear}&nbsp;/
	${schedule.eduProcGraphic.semester.semesterYear+1}-ый учебный год,
	${schedule.eduProcGraphic.semester.fallSpring?'весна':'осень'},
	расписание группы ${schedule.group.groupNumber}</h1>

<t:insertTemplate template="level2/showSchedule.jsp" />