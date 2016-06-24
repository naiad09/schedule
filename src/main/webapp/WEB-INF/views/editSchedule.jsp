<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>

<h1>${schedule.eduProcGraphic.semester.semesterYear}&nbsp;/
	${schedule.eduProcGraphic.semester.semesterYear+1}-ый учебный год,
	${schedule.eduProcGraphic.semester.fallSpring?'весна':'осень'},
	расписание группы ${schedule.group.groupNumber}</h1>

<input type="hidden" value="${schedule.eduProcGraphic.semesterStart}"
	id="dateStart" type="hidden" />
<input type="hidden" value="${schedule.eduProcGraphic.scheduleChange}"
	id="dateChange" type="hidden" />
<input type="hidden" value="${schedule.eduProcGraphic.semesterEnd}"
	id="dateEnd" type="hidden" />

<form action="edit" method="post" id="scheduleForm">
	<table style="width: 1100px; margin-left: -50px;">
		<tr>
			<td style="padding: 0;"><div id="scheduleOverflow">
					<table id="schedule" class="borderTable"
						ondragover="return dragEnter(event)">
						<thead>
							<tr>
								<th rowspan="3" style="width: 21px;"></th>
								<th rowspan="3">Пара</th>
								<th colspan="4" style="font-size: 26px;">${schedule.group.groupNumber}</th>
							</tr>
							<tr>
								<th colspan="2"
									style="width: 43%; border-bottom: none; padding-bottom: 0">Числитель</th>
								<th colspan="2"
									style="width: 43%; border-bottom: none; padding-bottom: 0">Знаменатель</th>
							</tr>
							<tr>
								<th style="width: 21%; border-top: none; border-right: none; height: 0"></th>
								<th style="width: 21%; border-top: none; border-left: none; height: 0"></th>
								<th style="width: 21%; border-top: none; border-right: none; height: 0"></th>
								<th style="width: 21%; border-top: none; border-left: none; height: 0"></th>
							</tr>
						</thead>
						<c:forEach items="${refsContainer.daysOfWeek}" var="day">
							<tbody class="weekday">
								<tr>
									<th rowspan="${twains.size()*2+2}"><small><fmt:formatDate
												value="${day}" pattern="eeee" /></small></th>
								</tr>
								<c:forEach items="${twains}" var="twain">
									<tr class="empty scheduleTr">
										<td><fmt:formatDate value="${twain.timeStart}"
												pattern="HH'<sup>'mm'</sup>'" /> - <fmt:formatDate
												value="${twain.timeEnd}" pattern="HH' <sup>'mm'</sup>'" />
											<input value="${twain.idTwain}" class="twainInput"
											type="hidden" /></td>
										<td colspan="4" class="scheduleItem"
											ondrop="return dragDropTd(event)"></td>
									</tr>
								</c:forEach>
							</tbody>
						</c:forEach>
					</table>
				</div></td>
			<td id="scheduleDisciplines">
				<h3 style="margin-top: 0">Дисциплины:</h3> <c:forEach
					items="${schedule.scheduleDisciplinesMap}" var="mapRow"
					varStatus="i">
					<div class="scheDisc" title="Нажмите дважды, чтобы настроить">
						<b>${mapRow.value[0].disc.discName}</b> <br>
						<c:forEach items="${mapRow.value}" var="glt">
							<div class="glt ${glt.lessonType}"
								id="glt${glt.idScheduleDiscipline}" draggable="true"
								ondragstart="return dragStartGLT(event)">
								<input type="hidden" value="${glt.idScheduleDiscipline}"
									name="scheduleDisciplines[].idScheduleDiscipline" /> <span
									class="gltType"><spring:message
										code="${glt.lessonType}.short" /></span> <span class="lecturers">
									<c:forEach items="${glt.lecturers}" var="lecturer">
										<input type="hidden" class="lecturerInput"
											name="scheduleDisciplines[].lecturers[].uid"
											value="${lecturer.uid}">
									</c:forEach>
								</span>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
				<button>Сохранить</button> <br>
				<button type="button" style="font-size: 9px; margin-top: 3px;"
					onclick="if(confirm('Продолжить?\nВсе текущие изменения будут отменены')) location = location">
					Отменить изменения</button>
			</td>
		</tr>
	</table>
</form>

<t:insertTemplate template="level2/scheduleDiscSettingsForm.jsp" />
<t:insertTemplate template="level2/scheduleItemSettingsForm.jsp" />

<div style="display: none">
	<table id="schiTemplate" class="schi"
		ondrop="return dragDropSchi(event)"
		title="Нажмите дважды, чтобы настроить">
		<tbody>
			<tr>
				<td class="leftMover mover"
					title="Потяните, чтобы настроить понедельный план"></td>
				<td class="inner" draggable="true"
					ondragstart="return dragStartSCHI(event)">
					<div class="schiButtons">
						<img src="${baseUrl}/resources/img/cross.png" class="button"
							onclick="processClickDelete(this)" title="Удалить">
					</div> <span class="twain"></span> <span class="numDen"></span> <span
					class="discipline"></span> <input type="hidden"
					name="scheduleDisciplines[].scheduleItems[].idScheduleItem" /> <input
					type="hidden" name="scheduleDisciplines[].scheduleItems[].weekplan" />
					<input type="hidden"
					name="scheduleDisciplines[].scheduleItems[].comment" /> <input
					type="hidden"
					name="scheduleDisciplines[].scheduleItems[].twain.idTwain" /> <input
					type="hidden" name="scheduleDisciplines[].scheduleItems[].weekday" />
					<small class="details"> <span class="lecturers"></span> <span
						class="divider"></span> <span class="classrooms"></span>
				</small> <small class="weekplan"></small>
				</td>
				<td class="rightMover mover"
					title="Потяните, чтобы настроить понедельный план"></td>
			</tr>
		</tbody>
	</table>
</div>

<div id="scheduleItemsInfo" style="display: none">
	<c:forEach items="${schedule.scheduleDisciplines}" var="glt">
		<c:forEach items="${glt.scheduleItems}" var="schi">
			<div class="schiInfo">${schi.idScheduleItem},
				${schi.twain.idTwain}, ${glt.idScheduleDiscipline}, ${schi.weekday},
				${schi.weekplan}, ${schi.isConflict()}, ${schi.comment}
				<c:forEach items="${schi.classrooms}" var="c">
					<input type="hidden" class="classroomInput"
						value="${c.idClassroom}"
						name="scheduleDisciplines[].scheduleItems[].classrooms[].idClassroom" />
				</c:forEach>
			</div>
		</c:forEach>
	</c:forEach>
</div>

<div class="vacancy" id="vacancyTemplate"
	ondrop="return dragDropVacancy(event)">
	<span class="twain"></span> <span class="numDen"></span> <span
		class="weekplan"></span>
</div>

<script
	src="${baseUrl}/resources/js/schedule/moves.js"></script>
<script
	src="${baseUrl}/resources/js/schedule/schedule.js"></script>
