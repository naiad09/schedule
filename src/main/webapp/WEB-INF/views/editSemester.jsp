<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<h1>${semester.semesterYear}/${semester.semesterYear+1}-ый&nbsp;учебный&nbsp;год,
	${semester.fallSpring?'весна':'осень'}</h1>

<c:set var="dateMin" scope="request"
	value="${semester.semesterYear+(semester.fallSpring?'1':'0')}-${semester.fallSpring?'02':'09'}-01" />
<c:set var="dateMax" scope="request"
	value="${semester.semesterYear+1}-${semester.fallSpring?'07-15':'01-31'}" />

<form:form action="edit" method="post" commandName="semester">

	<table class="borderTable" style="border: 0">
		<thead>
			<tr>
				<th style="padding: 7px">Курс, образовательная программа</th>
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
						<td colspan="7"><h3 style="text-align: center">
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
							<spring:eval var="hereGraphics"
								expression="semester.eduProcGraphics.?[enroll.idEnroll==${enrollCourse.idEnroll}]" />
							<spring:eval var="defaultGraphic"
								expression="T(schedule.service.RefsContainer).getDefaultEduProcGraphicForList(hereGraphics)" />
							<tbody
								class="eduProcGraphics ${eduMode} ${qual} course${i.index}">
								<tr class="selectCurriculum toggleVisiable">
									<td colspan="7"><div style="width: 600px; padding-left: 370px;">
											<input class="curriculumSelectorInput"
												placeholder="Найти образовательную программу по названию" /><br>
											<select class="curriculumSelector">
												<c:forEach items="${enrollCourse.commonCurriculums}"
													var="comCur">
													<option value="${comCur.idCommonCurriculum}">
														${comCur.eduProgram.eduProgCode},
														${comCur.eduProgram.eduProgName}</option>
												</c:forEach>
											</select>
										</div></td>
									<td style="border: 0"><img class="button clearSelection"
										src="${baseUrl}/resources/img/cross.png" title="Очистить"></td>
								</tr>
								<c:forEach items="${hereGraphics}" var="g">
									<c:if test="${g!=defaultGraphic}">
										<tr class="eduProcGraphic">
											<td>${i.index}&nbsp;курс,<br> <small>${g.curriculums[0].eduProgram.eduProgCode}</small>
												<input type="hidden"
												value="${g.curriculums[0].idCommonCurriculum}"
												name="eduProcGraphics[].curriculums[0].idCommonCurriculum" />
												<input type="hidden" value="${g.idEduPeriod}"
												name="eduProcGraphics[].idEduPeriod" /><input type="hidden"
												value="${enrollCourse.idEnroll}"
												name="eduProcGraphics[].enroll.idEnroll" /></td>

											<c:set value="${g}" var="graphic" scope="request" />
											<t:insertTemplate template="level2/eduProcGraphicEdit.jsp" />

											<td style="border: 0"><c:if
													test="${g.idEduPeriod==null}">
													<img class="deleteLink button"
														src="${baseUrl}/resources/img/cross.png" title="Удалить">
												</c:if></td>
										</tr>
									</c:if>
								</c:forEach>
								<tr class="default">
									<td>${i.index}&nbsp;курс,<br> <small></small><input
										type="hidden"
										name="eduProcGraphics[].curriculums[0].idCommonCurriculum" /><input
										type="hidden" name="eduProcGraphics[].idEduPeriod" value="" /><input
										type="hidden" value="${enrollCourse.idEnroll}"
										name="eduProcGraphics[].enroll.idEnroll" /></td>

									<c:set value="${null}" var="graphic" scope="request" />
									<t:insertTemplate template="level2/eduProcGraphicEdit.jsp" />

									<td style="border: 0"><img class="deleteLink button"
										src="${baseUrl}/resources/img/cross.png" title="Удалить"></td>
								</tr>
								<tr class="allCurriculums eduProcGraphic">
									<td class="allCurriculumsTd">${i.index}&nbsp;курс,<br>
										<small>${hereGraphics.size() le 1?'все направления':'другие направления'}</small>
										<input type="hidden" value="${enrollCourse.idEnroll}"
										name="eduProcGraphics[].enroll.idEnroll" /><input
										type="hidden" name="eduProcGraphics[].idEduPeriod"
										value="${defaultGraphic.idEduPeriod==null?'':defaultGraphic.idEduPeriod}" /></td>

									<c:set value="${defaultGraphic}" var="graphic" scope="request" />
									<t:insertTemplate template="level2/eduProcGraphicEdit.jsp" />

									<td style="border: 0"><img src="${baseUrl}/resources/img/add.png"
										title="Уточнить для..." class="button pinpointCurriculumLink" /></td>
								</tr>


							</tbody>

						</c:if>
					</c:forEach>

				</c:if>
			</c:forEach>
		</c:forEach>
	</table>
	<button style="margin: 7px 0 0 100px">Отправить</button>
</form:form>


<script>
	$(".eduProcGraphics")
			.each(
					function() {
						var allCurriculumsSmall = $(this).find(
								".allCurriculumsTd small")

						function onClone(newRow, option) {
							var code = option.text().match(/\d\d\.\d\d\.\d\d/)
							var td = newRow.find("td:first-child small")
							td.text("" + code)
							allCurriculumsSmall.text("другие направления")
						}

						function onDropAll(newRow, option) {
							allCurriculumsSmall.text("все направления")
						}

						var selector = $(this).find(".curriculumSelector")
						var holder = $(this)

						var config = {
							holder : holder,
							rowClass : 'eduProcGraphic',
							removeLink : $(this).find(".deleteLink"),
							selector : selector,
							nameToCopy : "idCommonCurriculum",
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

						var selectorInput = $(this).find(
								".curriculumSelectorInput")

						var configFinder = {
							selector : $(this).find(".curriculumSelector"),
							input : selectorInput,
							clearButton : $(this).find(".clearSelection"),
							maxHeightSelect : 100,
							onDropSelection : onDropSelection
						}

						$(this).find(".pinpointCurriculumLink").click(
								function() {
									trSelect.toggleClass("toggleVisiable")
									selectorInput.focus()
								})

						new SelectorFindHelper(configFinder)

						$("form#semester")
								.submit(
										function() {
											selector
													.find(
															"option:not(:disabled)")
													.each(
															function(i) {
																holder
																		.find(
																				".allCurriculumsTd")
																		.append(
																				'<input type="hidden" name="'
															+'eduProcGraphics[].curriculums['+
	                                               i+'].idCommonCurriculum" value="'+this.value+'"/>')
															})
										})
					})

	$("form#semester").submit(function() {
		var configSubmit = {
			listHolder : $("form#semester"),
			listName : "eduProcGraphics",
			rowClass : 'eduProcGraphic',
			defaultRowClass : "default"
		}
		processDynamicListForm(configSubmit)
	})
</script>