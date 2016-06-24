<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<h1>Менеджер конфликтов расписания</h1>

<form:form modelAttribute="conflictFinder" id="conflictFindForm"
	method="get">
	<table>
		<tr>
			<td><form:select path="faculty">
					<form:option value=""> -- Любой факультет --</form:option>
					<c:forEach items="${refsContainer.faculties}" var="faculty">
						<form:option value="${faculty}">
							<spring:message code="${faculty}.fullName" />
						</form:option>
					</c:forEach>
				</form:select></td>
			<td width="180px"><form:checkbox path="classroomConflict"
					id="classroomConflict" value="true" /><label
				for="classroomConflict">Конфликты аудиторий</label></td>
			<td><form:checkbox path="lecturerConflict" id="lecturerConflict"
					value="true" /><label for="lecturerConflict">Конфликты
					преподавателей</label></td>
		</tr>
		<tr>
			<td>На странице: <form:select style="min-width:50px"
					path="perPage">
					<form:option value="10" />
					<form:option value="15" />
					<form:option value="20" />
					<form:option value="30" />
					<form:option value="50" />
					<form:option value="100" />
				</form:select></td>
			<td><button>Искать</button>
				<button type="reset">Сбросить</button></td>
		</tr>
	</table>
</form:form>

<script>
function getPage(i) {
	conflictFindForm.elements[page].value=i
	conflictFindForm.submit()
}
</script>
<c:choose>
	<c:when test="${!empty conflictList}">
		<p>
			<c:set value="${conflictList.size()/conflictFinder.perPage+1}"
				var="pages" />
			Найдено: ${conflictList.size()} <br>
			<c:if test="${pages>=2}">Страницы:
            <c:forEach begin="1" end="${pages}" varStatus="i">
					<a onclick="getPage(${i.index})" class="page">${i.index}</a>
				</c:forEach>
			</c:if>
		</p>
		<table class="borderTable">
			<thead>
				<tr>
					<th></th>
					<th>Конфликтующие элементы расписания</th>
					<th>Пара</th>
					<th>Конфликт</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${conflictList}" var="conflict"
					begin="${(conflictFinder.page-1)*conflictFinder.perPage}"
					end="${conflictFinder.page*conflictFinder.perPage}">
					<tr>
						<th><fmt:formatDate
								value="${refsContainer.daysOfWeek[conflict.schiFrom.weekday]}"
								pattern="eee" /></th>


						<td><c:forEach items="${conflict.twoItems}" var="schi">
								<c:set value="${schi.scheduleDiscipline}" var="gltFrom" />
								<c:set value="${gltFrom.schedule}" var="scheduleFrom" />
								<b><a href="schedule-${scheduleFrom.idSchedule}/edit">
										<spring:message
											code="${scheduleFrom.group.curriculum.skillProfile.chair.faculty}.shortName" />,
										${scheduleFrom.group.groupNumber},
										${scheduleFrom.eduProcGraphic.semester.semesterYear
                                - scheduleFrom.group.curriculum.commonCurriculum.enrollment.yearStart
                                + 1}-ый
										курс
								</a></b>
								<br>
								<spring:message code="${gltFrom.lessonType}.short" />
                            ${gltFrom.disc.discName}<br>
							</c:forEach></td>

						<td><c:forEach items="${conflict.twoItems}" var="schi">
						          ${schi.twain.idTwain}-ая&nbsp;пара<br>
							</c:forEach></td>

						<td><c:forEach items="${conflict.twoItems}" var="schi"
								varStatus="i">
								<c:if test="${conflict.classroomConflict}">
									<c:forEach items="${schi.classrooms}" var="classroom"
										varStatus="j">
										<c:choose>
											<c:when
												test="${fn:contains(conflict.twoItems[1-i.index].classrooms, classroom)}">
												<b>a. ${classroom.classroomNumber}</b>
											</c:when>
											<c:otherwise>a. ${classroom.classroomNumber}
										</c:otherwise>
										</c:choose>
										<c:if test="${!j.last}">,</c:if>
									</c:forEach>
									<br>
								</c:if>
								<c:if test="${conflict.lecturerConflict}">
									<c:forEach items="${schi.scheduleDiscipline.lecturers}"
										var="lecturer" varStatus="j">
										<spring:message
											code="${lecturer.lecturerJobs[0].jobType}.shortName"
											var="mainJob" />
										<c:choose>
											<c:when
												test="${fn:contains(conflict.twoItems[1-i.index].scheduleDiscipline.lecturers, lecturer)}">
												<b>${mainJob} ${lecturer.fullName}</b>
											</c:when>
											<c:otherwise>${mainJob} ${lecturer.fullName}
                                        </c:otherwise>
										</c:choose>
										<c:if test="${!j.last}">,</c:if>
									</c:forEach>
									<br>
								</c:if>
							</c:forEach></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>По заданным критериям поиска ничего не найдено</c:otherwise>
</c:choose>
