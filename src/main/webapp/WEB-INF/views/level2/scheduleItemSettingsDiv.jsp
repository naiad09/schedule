<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>

<div id="scheduleItemSettingsDiv">
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
					</select> <select name="idTwain" style="min-width: 100px;"
						disabled="disabled">
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
							</div>
						</div>
						<div style="display: inline-block;">
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
					<td><div class="selectorFinderWrapper">
							<span id="lecturers">
								<div class="default">
									<input type="hidden" name="lecturers[].idLecturer" /> <span></span><img
										class="deleteLecturerLink button"
										src="../../resources/cross.png" title="Удалить">
								</div>
							</span> <input id="lecturerSelectorInput"
								placeholder="Найти преподавателя по имени или кафедре" /><br>
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
                                     кафедры ${job.chair.shortName}<c:if
												test="${!loop.last }">,</c:if>
										</c:forEach>
									</option>
								</c:forEach>
							</select>
						</div> <img class="button" id="clearLecturerSelection"
						src="../../resources/cross.png" title="Очистить"> <script>
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