<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="scheduleDiscSettingsDiv">
	<h2 style="text-align: center; margin: 0;">Настроить дисциплину
		расписания</h2>
	<form id="scheduleSettingsForm">
		<table>
			<tr>
				<td><h3>Предмет</h3></td>
				<td class="discHolder">
					<h3></h3>
				</td>
			</tr>
			<c:forEach items="${refsContainer.lessonTypes}" var="lessonType">
				<tr class="${lessonType} toggleVisible">
					<td><spring:message code="${lessonType}.full" /></td>
					<td><div class="selectorFinderWrapper">
							<span id="${lessonType}Lecturers">
								<div class="default">
									<input type="hidden" name="lecturers[].idLecturer" /> <span></span>
									<img class="delete${lessonType}LecturerLink button"
										src="../../resources/cross.png" title="Удалить">
								</div>
							</span> <input id="${lessonType}LecturerSelectorInput"
								placeholder="Найти преподавателя по имени или кафедре" /></div>
						<img class="button" id="clear${lessonType}LecturerSelection"
                            src="../../resources/cross.png" title="Очистить">
						<select id="${lessonType}LecturerSelector">
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
						<script>
							function onClone(newRow, option) {
								newRow.find("span").text(option.attr("title"))
							}
							var config = {
								holder : $("#${lessonType}Lecturers"),
								rowClass : 'lecturer',
								removeLink : $(".delete${lessonType}LecturerLink"),
								selector : $("#${lessonType}LecturerSelector"),
								defaultRowClass : "default",
								nameToCopy : "idLecturer",
								processCloning : onClone,
								minRows : 1
							}
							new DynamicList(config)

							var configFinder = {
								selector : $("#${lessonType}LecturerSelector"),
								input : $("#${lessonType}LecturerSelectorInput"),
								clearButton : $("#clear${lessonType}LecturerSelection"),
								maxHeightSelect : 100
							}

							new SelectorFindHelper(configFinder)

							$("#clear${lessonType}LecturerSelection")
									.click(
											function() {
												$(
														"#${lessonType}Lecturers .lecturer "
																+ ".delete${lessonType}LecturerLink")
														.click()
											})
						</script></td>
				</tr>
			</c:forEach>
			<tr>
				<td></td>
				<td><button type="button" id="scheduleDiscSettingsSaveButton">Сохранить</button>
					<button type="reset" id="scheduleDiscSettingsResetButton">Отменить</button></td>
			</tr>
		</table>
	</form>
</div>


<script
	src="${pageContext.request.contextPath}/resources/js/schedule/scheduleDiscSettings.js"></script>