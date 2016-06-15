<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>

<form id="scheduleItemSettingsForm" class="scheduleSettingsForm">
	<h2 style="text-align: center; margin: 0;">Настроить элемент
		расписания</h2>
	<table>
		<tr>
			<td><h3>Предмет</h3></td>
			<td class="discHolder">
				<h3></h3>
			</td>
		</tr>
		<tr>
			<td style="padding: 6px 5px;">День недели, пара</td>
			<td><select name="weekday" disabled="disabled"
				style="max-width: 151px;">
					<c:forEach items="${refsContainer.daysOfWeek}" var="day"
						varStatus="i">
						<option value="${i.index}">
							<fmt:formatDate value="${day}" pattern="eeee" />
						</option>
					</c:forEach>
			</select> <select name="twain" style="max-width: 100px;" disabled="disabled">
					<c:forEach items="${twains}" var="twain">
						<option value="${twain.idTwain}">
							<fmt:formatDate value="${twain.timeStart}" pattern="HH:mm" /> -
							<fmt:formatDate value="${twain.timeEnd}" pattern="HH:mm" />
						</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>Аудитории</td>
			<td><div class="selectorFinderWrapper">
					<span id="classrooms"> <span class="default"> <input
							type="hidden" class="classroomInput"
							name="scheduleDisciplines[].scheduleItems[].classrooms[].idClassroom" />
							<span></span> <img class="deleteClassroomLink button"
							src="../../resources/cross.png" title="Удалить">
					</span>
					</span> <input id="classroomSelectorInput"
						placeholder="Найти аудиторию по названию" />
				</div> <img class="button" id="clearClassroomSelection"
				src="../../resources/cross.png" title="Очистить"> <select
				id="classroomSelector">
					<c:forEach items="${classrooms}" var="classroom">
						<option value="${classroom.idClassroom}"
							title="а. ${classroom.classroomNumber}">ауд.
							${classroom.classroomNumber}, корпус ${classroom.campus}
							<c:if test="${classroom.chair!=null}">
                                        , каф. ${classroom.chair.shortName}</c:if>
						</option>
					</c:forEach>
			</select> <script>
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
			<td>Понедельный план</td>
			<td><c:forEach begin="0" end="7" varStatus="i">
					<input type="checkbox" name="scheduleChange" value="${i.index}" />
				</c:forEach> <output name="weekplan">Выберите нужные пункты</output></td>
		</tr>
		<tr>
			<td>Комментарий</td>
			<td><textarea name="comment" style="width: 423px; height: 30px;"></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td><button type="button" id="scheduleItemSettingsSaveButton">Сохранить</button>
				<button type="reset" id="scheduleItemSettingsResetButton">Отменить</button></td>
		</tr>
	</table>
</form>


<script
	src="${pageContext.request.contextPath}/resources/js/schedule/scheduleItemSettings.js"></script>
