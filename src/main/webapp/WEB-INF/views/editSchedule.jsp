<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>

<script
	src="${pageContext.request.contextPath}/resources/js/schedule.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/scheduleSettings.js"></script>

<h1>${schedule.eduProcGraphic.semester.semesterYear}&nbsp;/&nbsp;
	${schedule.eduProcGraphic.semester.semesterYear+1}-ый учебный год,
	${schedule.eduProcGraphic.semester.fallSpring?'весна':'осень'},
	расписание группы ${schedule.group.groupNumber}</h1>

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
                                            <sup>'mm'</sup>'" />
										<input type="hidden" value="${twain.idTwain}" name="idTwain" /></td>
									<td colspan="4" class="scheduleItem"></td>
								</tr>
							</c:forEach>
						</tbody>
					</c:forEach>
				</table>
			</form>
		</td>
		<td id="scheduleDisciplines" rowspan="2">
			<h3 style="margin-top: 0">Дисциплины:</h3>
            <c:forEach items="${schedule.scheduleDisciplinesMap}" var="mapRow"
				varStatus="i">
				<div class="scheDisc" title="Нажмите дважды, чтобы настроить">
					<b>${mapRow.value[0].disc.discName}</b>
					<br><c:forEach items="${mapRow.value}" var="glt">
						<div class="glt ${glt.lessonType}"
							id="glt${glt.idScheduleDiscipline}" draggable="true"
							ondragstart="return dragStartGLT(event)">
							<input type="hidden" value="${glt.idScheduleDiscipline}"
								name="idScheduleDiscipline" /> <span class="gltType"><spring:message
									code="${glt.lessonType}.short" /></span> <span class="lecturers"></span>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
			<button onclick="scheduleForm.submit()">Отправить</button>

		</td>
	</tr>
	<tr>
		<td><t:insertTemplate
				template="level2/scheduleDiscSettingsDiv.jsp" /></td>
	</tr>
</table>

<div style="display: none">
	<table id="schiTemplate" class="schi">
		<tbody>
			<tr>
				<td class="leftMover mover"></td>
				<td class="inner" draggable="true"
					ondragstart="return dragStartSCHI(event)">
					<div class="schiButtons">
						<img src="../../resources/add.png" class="button"
							onclick="settings(this)" title="Настроить"> <img
							src="../../resources/cross.png" class="button"
							onclick="processClickDelete(this)" title="Удалить">
					</div>
					<div class="discipline"></div> <input type="hidden" name="weekPlan" />
					<input type="hidden" name="comment" /> <small class="details">
						<span class="lecturers"></span> <span class="divider"></span> <span
						class="classrooms"></span>
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
