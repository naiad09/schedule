<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<select id="classroomSelector">
	<c:forEach items="${classrooms}" var="classroom">
		<option value="${classroom.idClassroom}"
			class="${(classroom.chair.faculty==currentUser.faculty)?'green':''}
                            ${(empty classroom.chair.faculty)?'yellow':''}
                            ${classroom.chair.idChair==schedule.group.curriculum.skillProfile.chair.idChair?'blue':''}"
			title="а.&nbsp;${classroom.classroomNumber}${(classroom.campus=='1')?
                                 '':('&nbsp;'.concat(classroom.campus=='БИ'?'':'к.').concat(classroom.campus))}">ауд.
			${classroom.classroomNumber}, корпус ${classroom.campus}
			<c:if test="${classroom.chair!=null}">
                                        , каф. ${classroom.chair.shortName}</c:if>
		</option>
	</c:forEach>
</select>