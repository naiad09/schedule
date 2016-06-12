<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>
<script src="../../resources/js/selectorFinder.js"></script>
<script src="../../resources/js/dynamicList.js"></script>

<h1>${schedule.eduProcGraphic.semester.semesterYear}&nbsp;/&nbsp;
	${schedule.eduProcGraphic.semester.semesterYear+1}-ый учебный год,
	${schedule.eduProcGraphic.semester.fallSpring?'весна':'осень'},
	расписание группы ${schedule.group.groupNumber}</h1>

<script src="../../resources/js/schedule.js"></script>
<script src="../../resources/js/scheduleSettings.js"></script>

<table ondragenter="return dragEnter(event)"
	ondrop="return dragDrop(event)" ondragover="return dragEnter(event)"
	style="width: 1100px; margin-left: -50px;">
	<tr>
		<td id="scheduleOverflow">
			<form action="edit" method="post" id="scheduleForm">
				<table id="schedule" class="borderTable">
					<thead>
						<tr>
							<th rowspan="3" style="width: 21px;"></th>
							<th rowspan="3">Пара</th>
							<th colspan="4">Дисциплины</th>
						</tr>
						<tr>
							<th colspan="2" style="width: 43%;">Числитель</th>
							<th colspan="2" style="width: 43%;">Знаменатель</th>
						</tr>
						<tr>
							<th style="width: 21%;"></th>
							<th style="width: 21%;"></th>
							<th style="width: 21%;"></th>
							<th style="width: 21%;"></th>
						</tr>
					</thead>
					<c:forEach items="${refsContainer.daysOfWeek}" var="day">
						<tbody>
							<tr>
								<th rowspan="${twains.size()*2+2}"><small><fmt:formatDate
											value="${day}" pattern="eeee" /></small></th>

							</tr>
							<c:forEach items="${twains}" var="twain">
								<tr class="empty scheduleTr">
									<td><fmt:formatDate value="${twain.timeStart}"
											pattern="HH'<sup>'mm'</sup>'" /> - <fmt:formatDate
											value="${twain.timeEnd}"
											pattern="HH'
											<sup>'mm'</sup>'" /> <input
										type="hidden" value="${twain.idTwain}" name="idTwain" /></td>
									<td colspan="4" class="scheduleItem"></td>
								</tr>
							</c:forEach>
						</tbody>
					</c:forEach>
				</table>
			</form>
		</td>
		<td>
			<table id="groupLessonTypes">
				<tr>
					<th>
						<h3 style="margin-top: 0">Дисциплины:</h3>
					</th>
				</tr>
				<c:forEach items="${schedule.groupLessonTypesMap}" var="mapRow"
					varStatus="i">
					<tr>
						<td><b>${mapRow.value[0].disc.discName}</b> <br> <small>Часов
								в неделю: <span>0</span> / <c:set var="h"
									value="${mapRow.key.hoursHerWeek}" /> ${h%1==0?h.intValue():h}
						</small> <br> <c:forEach items="${mapRow.value}" var="glt">
								<div class="glt ${glt.lessonType}" id="glt${glt.idLessonType}"
									draggable="true" ondragstart="return dragStartGLT(event)">
									<input type="hidden" value="${glt.idLessonType}"
										name="idLessonType" />
                                    <span class="gltType"><spring:message
											code="${glt.lessonType}.full" /></span>
									<span class="lecturers"></span>
								</div>
							</c:forEach></td>
					</tr>
				</c:forEach>
				<tr>
					<td><br>
						<button onclick="scheduleForm.submit()">Отправить</button></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<div style="display: none">
	<table id="schiTemplate" class="schi">
		<tbody>
			<tr>
				<td class="leftMover mover"></td>
				<td class="inner" draggable="true" ondragstart="return dragStartSCHI(event)">
					<div class="schiButtons">
						<img src="../../resources/add.png" class="button"
							onclick="settings(this)" title="Настроить">
                        <img src="../../resources/cross.png" class="button"
							onclick="processClickDelete(this)" title="Удалить">
					</div>
					<div class="discipline"></div>
                    <input type="hidden" name="weekPlan" />
                    <input type="hidden" name="comment" />
                    <small class="details">
                        <span class="lecturers"></span>
                        <span class="divider"></span>
                        <span class="classrooms"></span>
				    </small>
				</td>
				<td class="rightMover mover"></td>
			</tr>
		</tbody>
	</table>
</div>

<script>
	//Расстановка номеров
	$("#schedule>tbody").each(function(i) {
		this.index = i
		$(this).find("tr.empty").each(function(j) {
			this.schi = [ null, null, null, null ]
		})
	})
</script>

<div id="settingsDiv">
	<h2 style="text-align: center; margin: 0;">
		<a name="settings"></a>Настроить элемент расписания
	</h2>
	<form id="scheduleSettingsForm">
		<fieldset disabled="disabled" id="settingsFieldset">
			<table>
				<tr>
					<td><h3>Предмет</h3></td>
					<td id="discHolder"><span>Выберите элемент расписания и
							нажмите кнопку "Настроить"</span>
						<h3 style="margin: 0;"></h3></td>
				</tr>
				<tr>
					<td style="padding: 6px 5px;">День недели, пара</td>
					<td><select name="dayOfWeek" disabled="disabled"
						style="min-width: 151px;">
							<c:forEach items="${refsContainer.daysOfWeek}" var="day"
								varStatus="i">
								<option value="${i.index}">
									<fmt:formatDate value="${day}" pattern="eeee" />
								</option>
							</c:forEach>
					</select> <select name="idTwain" style="min-width: 100px;" disabled="disabled">
							<c:forEach items="${twains}" var="twain">
								<option value="${twain.idTwain}">
									<fmt:formatDate value="${twain.timeStart}" pattern="HH:mm" />
									-
									<fmt:formatDate value="${twain.timeEnd}" pattern="HH:mm" />
								</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>Аудитории</td>
					<td><div id="classrooms" style="display: inline-block;">
							<div class="default">
								<input type="hidden" name="classrooms[].idClassroom" /><span></span><img
									class="deleteClassroomLink button"
									src="../../resources/cross.png" title="Удалить">
							</div></div><div style="display: inline-block;">
							<input id="classroomSelectorInput"
								placeholder="Найти аудиторию по названию" /><img class="button"
								id="clearClassroomSelection" src="../../resources/cross.png"
								title="Очистить"><br> <select id="classroomSelector">
								<c:forEach items="${classrooms}" var="classroom">
									<option value="${classroom.idClassroom}"
										title="а. ${classroom.classroomNumber}">ауд.
										${classroom.classroomNumber}, корпус ${classroom.campus}
										<c:if test="${classroom.chair!=null}">
                                        , каф. ${classroom.chair.shortName}</c:if>
									</option>
								</c:forEach>
							</select>
						</div> <script>
							function onClone(newRow, option) {
								newRow.find("span").text(option.attr("title"))
							}
							var config = {
								holder : $("#classrooms"),
								rowClass : 'classroom',
								removeLink : $(".deleteClassroomLink"),
								selector : $("#classroomSelector"),
								defaultRowClass : "default",
								nameToCopy : "idClassroom",
								processCloning : onClone,
								minRows : 1
							}
							new DynamicList(config)

							var configFinder = {
								selector : $("#classroomSelector"),
								input : $("#classroomSelectorInput"),
								clearButton : $("#clearClassroomSelection"),
								maxHeightSelect : 100
							}

							new SelectorFindHelper(configFinder)
						</script></td>
				</tr>
				<tr>
					<td style="padding: 6px 5px;">Преподаватели</td>
					<td><div id="lecturers">
							<div class="default">
								<input type="hidden" name="lecturers[].idLecturer" /><span></span><img
									class="deleteLecturerLink button"
									src="../../resources/cross.png" title="Удалить">
							</div>
						</div>
						<div>
							<input id="lecturerSelectorInput"
								placeholder="Найти преподавателя по имени или кафедре" /><img
								class="button" id="clearLecturerSelection"
								src="../../resources/cross.png" title="Очистить"><br>
							<select id="lecturerSelector">
								<c:forEach items="${lecturers}" var="lecturer">
									<c:if test="${!empty lecturer.lecturerJobs}">
										<spring:message
											code="${lecturer.lecturerJobs[0].jobType}.shortName"
											var="mainJob" />
									</c:if>
									<option value="${lecturer.uid}"
										title="${mainJob} ${lecturer.fullName}">
										${lecturer.fullTextName},
										<c:forEach items="${lecturer.lecturerJobs}" var="job"
											varStatus="loop">
											<spring:message code="${job.jobType}.fullName" />
									 кафедры ${job.chair.shortName}<c:if test="${!loop.last }">,</c:if>
										</c:forEach>
									</option>
								</c:forEach>
							</select>
						</div> <script>
							function onClone(newRow, option) {
								newRow.find("span").text(option.attr("title"))
							}
							var config = {
								holder : $("#lecturers"),
								rowClass : 'lecturer',
								removeLink : $(".deleteLecturerLink"),
								selector : $("#lecturerSelector"),
								defaultRowClass : "default",
								nameToCopy : "idLecturer",
								processCloning : onClone,
								minRows : 1
							}
							new DynamicList(config)

							var configFinder = {
								selector : $("#lecturerSelector"),
								input : $("#lecturerSelectorInput"),
								clearButton : $("#clearLecturerSelection"),
								maxHeightSelect : 100
							}

							new SelectorFindHelper(configFinder)
						</script></td>
				</tr>
				<tr>
					<td>Временной план</td>
					<td><c:forEach begin="0" end="7" varStatus="i">
							<input type="checkbox" name="scheduleChange" value="${i.index}" />
						</c:forEach> <output name="weekplan">Выберите нужные пункты</output></td>
				</tr>
				<tr>
					<td>Комментарий</td>
					<td><textarea name="comment"
							style="width: 423px; height: 30px;"></textarea></td>
				</tr>
				<tr>
					<td></td>
					<td><button type="button" id="settingsSaveButton">Сохранить</button>
						<button type="reset" id="settingsResetButton">Отменить</button></td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>