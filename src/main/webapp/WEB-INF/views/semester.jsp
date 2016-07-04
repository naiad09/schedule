<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<sec:authorize access="hasRole('ROLE_EDUDEP')">
	<h3 style="float: right">
		<a href="semester-${semester.idSemester}/edit">Редактировать</a>
	</h3>
</sec:authorize>
<h1>${semester.semesterYear}/${semester.semesterYear+1}-ый&nbsp;учебный&nbsp;год,
	${semester.fallSpring?'весна':'осень'}</h1>
<form id="semester">
	<table class="borderTable">
		<thead>
			<tr>
				<th>Курс, образовательная программа</th>
				<th>Начало семестра</th>
				<th>Конец семестра</th>
				<th>Начало зачетной недели</th>
				<th>Конец зачетной недели</th>
				<th>Начало экзаменационной сессии</th>
				<th>Конец экзаменационной сессии</th>
			</tr>
		</thead>
		<c:forEach items="${refsContainer.eduModes}" var="eduMode">
			<c:forEach items="${refsContainer.qualTypes}" var="qual">
				<spring:eval
					expression="actualEnrolls.?[eduQual.toString()=='${qual}' and eduMode.toString()=='${eduMode}']"
					var="enrolls" />
				<c:if test="${!empty enrolls}">
					<tr>
						<td colspan="20"><h3 style="text-align: center">
								Форма обучения:
								<spring:message code="${eduMode}" />
								.
								<spring:message code="${qual}.head" />
							</h3></td>
					</tr>
					<c:forEach begin="1" end="6" varStatus="i">
						<spring:eval var="enrollCourse"
							expression="enrolls.^[${semester.semesterYear}- yearStart + 1 == ${i.index}]" />
						<c:if test="${!empty enrollCourse}">
							<spring:eval
								expression="semester.eduProcGraphics.?[enroll.idEnroll==${enrollCourse.idEnroll}]"
								var="hereGraphics" />
							<spring:eval var="defaultGraphic"
								expression="T(schedule.service.RefsContainer).getDefaultEduProcGraphicForList(hereGraphics)" />
							<tbody
								class="eduProcGraphics ${eduMode} ${qual} course${i.index}">
								<c:forEach items="${hereGraphics}" var="g">
									<c:if test="${g!=defaultGraphic}">
										<tr class="eduProcGraphic">
											<td>${i.index}&nbsp;курс,<br> <small>${g.curriculums[0].eduProgram.eduProgCode}</small>
											</td>
											<c:set value="${g}" var="graphic" scope="request" />
											<t:insertTemplate template="level2/eduProcGraphicShow.jsp" />
										</tr>
									</c:if>
								</c:forEach>
								<tr class="allCurriculums eduProcGraphic">
									<td class="allCurriculumsTd">${i.index}&nbsp;курс,<br>
										<small>${hereGraphics.size() le 1?'все направления':'другие направления'}</small>
									</td>
									<c:set value="${defaultGraphic}" var="graphic" scope="request" />
									<t:insertTemplate template="level2/eduProcGraphicShow.jsp" />
								</tr>
							</tbody>
						</c:if>
					</c:forEach>
				</c:if>
			</c:forEach>
		</c:forEach>
	</table>
</form>

<h2 align="center">Расписание</h2>
<div id="tableList" style="text-align: center">
	<c:forEach items="${semester.eduProcGraphics}" var="graphic">
		<c:forEach items="${graphic.curriculums}" var="comCur">
			<c:forEach items="${comCur.curriculums}" var="cur">
				<c:forEach items="${cur.groups}" var="thisGroup">
					<div>
						<c:set value="${thisGroup.idGroup}" var="idThisGroup" />
						<h3 style="margin: 0">
							<a
								href="${baseUrl}/
								${thisGroup.curriculum.skillProfile.chair.faculty}/group-${idThisGroup}">
								${thisGroup.groupNumber},&nbsp;${graphic.enroll.course}-ый курс</a>
						</h3>
						<spring:eval var="schedule"
							expression="graphic.schedules.^[#this.group.idGroup==${idThisGroup}]" />
						<c:if test="${schedule!=null}">
							<a
								href="semester-${semester.idSemester}/schedule-${schedule.idSchedule}">Посмотреть</a>
						</c:if>
						<br>
						<sec:authorize access="hasRole('ROLE_EDUDEP')">
							<c:choose>
								<c:when test="${schedule==null}">
									<form method="post" name="schedule"
										action="semester-${semester.idSemester}/new-schedule">
										<input type="hidden" name="group.idGroup"
											value="${thisGroup.idGroup}" /> <input type="hidden"
											name="eduProcGraphic.idEduPeriod"
											value="${graphic.idEduPeriod}" /> <a
											onclick="this.parentElement.submit()">Создать расписание</a>
									</form>
								</c:when>
								<c:otherwise>
									<a
										href="semester-${semester.idSemester}/schedule-${schedule.idSchedule}/edit">Редактировать</a>
									<form method="post" name="schedule"
										onsubmit="confirm('Подтвердите удаление расписания')"
										action="semester-${semester.idSemester}/schedule-${schedule.idSchedule}/delete">
										<a onclick="this.parentElement.submit()">Удалить</a>
									</form>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
					</div>
				</c:forEach>
			</c:forEach>
		</c:forEach>
	</c:forEach>
</div>

<sec:authorize access="hasRole('ROLE_EDUDEP')">
	<h3 align="center">
		<a
			href="semester-${semester.idSemester}/conflicts?faculty=${currentUser.faculty}">Менеджер
			конфликтов расписания</a>
	</h3>
</sec:authorize>