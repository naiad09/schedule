<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<script src="../../resources/js/selectorFinder.js"></script>
<script src="../../resources/js/dynamicList.js"></script>

<h1>${semester.semesterYear}/${semester.semesterYear+1}-ый&nbsp;учебный&nbsp;год,
	${semester.fallSpring?'весна':'осень'}</h1>

<c:set var="dateMin"
	value="${semester.semesterYear+(semester.fallSpring?'1':'0')}-${semester.fallSpring?'02':'09'}-01" />
<c:set var="dateMax"
	value="${semester.semesterYear+1}-${semester.fallSpring?'07-15':'01-31'}" />

<form:form action="edit" method="post" modelAttribute="semester">

	<table>
		<thead>
			<tr>
				<th>Курс, образовательная программа</th>
				<th>Начало семестра</th>
				<th>Конец семестра</th>
				<th>Начало зачетной недели</th>
				<th>Конец зачетной недели</th>
				<th>Начало экзаменационной сессии</th>
				<th>Конец экзаменационной сессии</th>
				<th></th>
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

							<tbody
								class="eduProcGraphics ${eduMode} ${qual} course${i.index}">
								<tr class="selectCurriculum toggleVisiable">
									<td></td>
									<td colspan="20"><input class="curriculumSelectorInput"
										placeholder="Найти образовательную программу по названию" /><img
										class="button clearSelection" src="../../resources/cross.png"
										title="Очистить"><br> <select
										class="curriculumSelector">
											<c:forEach items="${enrollCourse.commonCurriculums}"
												var="comCur">
												<option value="${comCur.idCommonCurriculum}">
													${comCur.eduProgram.eduProgCode},
													${comCur.eduProgram.eduProgName}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr class="default">
									<td>${i.index}&nbsp;курс,<br> <small></small></td>
									<td><input type="hidden"
										name="eduProcGraphics[].curriculums[0].idCommonCurriculum" />
										<input name="eduProcGraphics[].eduStart" type="date"
										min="${dateMin}" value="${dateMin}" max="${dateMax}" /></td>
									<td><input name="eduProcGraphics[].semestrEnd" type="date"
										min="${dateMin}" max="${dateMax}" /></td>
									<td><input name="eduProcGraphics[].recordSessionStart"
										type="date" min="${dateMin}" max="${dateMax}" /></td>
									<td><input name="eduProcGraphics[].recordSessionEnd"
										type="date" min="${dateMin}" max="${dateMax}" /></td>
									<td><input name="eduProcGraphics[].examsSessionStart"
										type="date" min="${dateMin}" max="${dateMax}" /></td>
									<td><input name="eduProcGraphics[].examsSessionEnd"
										type="date" min="${dateMin}" max="${dateMax}" /></td>
									<td><img class="deleteLink button"
										src="../../resources/cross.png" title="Удалить"></td>
								</tr>
								<tr class="allCurriculums eduProcGraphic">
									<td class="allCurriculumsTd">${i.index}&nbsp;курс,<br>
										<small>все направления</small></td>
									<td><input type="hidden" value="${enrollCourse.idEnroll}"
										name="eduProcGraphics[].enroll.idEnroll" /> <input
										name="eduProcGraphics[].eduStart" type="date"
										required="required" min="${dateMin}" max="${dateMax}"
										value="${dateMin}" /></td>
									<td><input name="eduProcGraphics[].semestrEnd" type="date"
										required="required" min="${dateMin}" max="${dateMax}" /></td>
									<td><input name="eduProcGraphics[].recordSessionStart"
										type="date" min="${dateMin}" max="${dateMax}" /></td>
									<td><input name="eduProcGraphics[].recordSessionEnd"
										type="date" min="${dateMin}" max="${dateMax}" /></td>
									<td><input name="eduProcGraphics[].examsSessionStart"
										type="date" min="${dateMin}" max="${dateMax}" /></td>
									<td><input name="eduProcGraphics[].examsSessionEnd"
										type="date" min="${dateMin}" max="${dateMax}" /></td>
									<td><img src="../../resources/add.png"
										title="Уточнить для..." class="button pinpointCurriculumLink" /></td>
								</tr>


							</tbody>

						</c:if>
					</c:forEach>

				</c:if>
			</c:forEach>
		</c:forEach>

		<t:insertTemplate template="utils/submitButton.jsp" />
	</table>
</form:form>

<script>
	var configSubmit = {
		form : $("form#semester"),
		listHolder : $("form#semester"),
		listName : "eduProcGraphics",
		rowClass : 'eduProcGraphic',
		defaultRowClass : "default"
	}
	new FormDynamicListSubmitProcessor(configSubmit)

	$(".eduProcGraphics").each(function() {
		var allCurriculumsSmall = $(this).find(".allCurriculumsTd small")

		function onClone(newRow, option) {
			var code = option.text().match(/\d\d\.\d\d\.\d\d/)
			var td = newRow.find("td:first-child small")
			td.text("" + code)
			allCurriculumsSmall.text("другие направления")
		}

		function onDropAll(newRow, option) {
			allCurriculumsSmall.text("все направления")
		}

		var config = {
			holder : $(this),
			rowClass : 'eduProcGraphic',
			removeLink : $(this).find(".deleteLink"),
			selector : $(this).find(".curriculumSelector"),
			defaultRowClass : "default",
			processCloning : onClone,
			processDropAll : onDropAll,
			minRows : 1
		}
		new DynamicList(config)

		var trSelect = $(this).find(".selectCurriculum")

		function onDropSelection() {
			trSelect.toggleClass("toggleVisiable")
		}

		var selectorInput = $(this).find(".curriculumSelectorInput")

		var configFinder = {
			selector : $(this).find(".curriculumSelector"),
			input : selectorInput,
			clearButton : $(this).find(".clearSelection"),
			maxHeightSelect : 100,
			onDropSelection : onDropSelection
		}

		$(this).find(".pinpointCurriculumLink").click(function() {
			trSelect.toggleClass("toggleVisiable")
			selectorInput.focus()
		})

		new SelectorFindHelper(configFinder)

	})
</script>