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
		<tr id="weekplan">
			<td>Понедельный план</td>
			<td><table style="width: 500px;">
					<tr>
						<th colspan="4"><span class="every toggleVisiable">Каждую
								неделю</span> <span class="num toggleVisiable">Числитель</span> <span
							class="den toggleVisiable">Знаменатель</span></th>
					</tr>
					<tr>
						<td colspan="2" style="text-align: right"><small>До
								смены расписания</small></td>
						<td colspan="2"><small>После смены расписания</small></td>
					</tr>
					<tr>
						<td><input value="11" type="radio" id="bcFull" name="bc" />
							<label for="bcFull">Полностью</label></td>
						<td><input value="00" type="radio" id="bcNone" name="bc" />
							<label for="bcNone">Нет</label></td>
						<td><input value="11" type="radio" id="acFull" name="ac" /><label
							for="acFull">Полностью</label></td>
						<td><input value="00" type="radio" id="acNone" name="ac" /><label
							for="acNone">Нет</label></td>
					</tr>
					<tr>
						<td><input type="radio" value="10" id="bcFirst" name="bc" />
							<label for="bcFirst" class="every toggleVisiable">Числитель</label>
							<label for="bcFirst" class="num toggleVisiable">Первый
								числитель</label> <label for="bcFirst" class="den toggleVisiable">Первый
								знаменатель</label></td>
						<td><input type="radio" value="01" id="bcSecond" name="bc" />
							<label for="bcSecond" class="every toggleVisiable">Знаменатель</label>
							<label for="bcSecond" class="num toggleVisiable">Второй
								числитель</label> <label for="bcSecond" class="den toggleVisiable">Второй
								знаменатель</label></td>
						<td><input type="radio" value="10" id="acFirst" name="ac" />
							<label for="acFirst" class="every toggleVisiable">Числитель</label>
							<label for="acFirst" class="num toggleVisiable">Первый
								числитель</label> <label for="acFirst" class="den toggleVisiable">Первый
								знаменатель</label></td>
						<td><input type="radio" value="01" id="acSecond" name="ac" />
							<label for="acSecond" class="every toggleVisiable">Знаменатель</label>
							<label for="acSecond" class="num toggleVisiable">Второй
								числитель</label> <label for="acSecond" class="den toggleVisiable">Второй
								знаменатель</label></td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td>Комментарий</td>
			<td colspan="2"><textarea name="comment"
					style="width: 423px; height: 30px;"></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="2"><button type="button"
					id="scheduleItemSettingsSaveButton">Сохранить</button>
				<button type="reset" id="scheduleItemSettingsResetButton">Отменить</button></td>
		</tr>
	</table>
</form>


<script
	src="${pageContext.request.contextPath}/resources/js/schedule/scheduleItemSettings.js"></script>
