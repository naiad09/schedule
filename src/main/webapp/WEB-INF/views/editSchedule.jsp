<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>
<script src="../../resources/js/selectorFinder.js"></script>
<script src="../../resources/js/dynamicList.js"></script>

<h1>${schedule.eduProcGraphic.semester.semesterYear}/${schedule.eduProcGraphic.semester.semesterYear+1}-ый
	учебный&nbsp;год,
	${schedule.eduProcGraphic.semester.fallSpring?'весна':'осень'},
	расписание группы ${schedule.group.groupNumber}</h1>

<script src="../../resources/js/schedule.js"></script>

<table ondragenter="return dragEnter(event)"
	ondrop="return dragDrop(event)" ondragover="return dragEnter(event)"
	style="width: 1100px; margin-left: -50px;">
	<tr>
		<td id="scheduleOverflow">
			<form action="edit" method="post">
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
								<tr class="empty">
									<td><fmt:formatDate value="${twain.timeStart}"
											pattern="HH'<sup>'mm'</sup>'" /> - <fmt:formatDate
											value="${twain.timeEnd}" pattern="HH'<sup>'mm'</sup>'" /> <input
										type="hidden" value="${twain.idTwain}" /></td>
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
						<h3>Дисциплины:</h3>
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
										name="idLessonType" /> <span><spring:message
											code="${glt.lessonType}.full" /> </span>
								</div>
							</c:forEach></td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>

<div style="display: none">
	<table id="schiTemplate" class="schi">
		<tbody>
			<tr>
				<td rowspan="2" class="leftMover mover"></td>
				<td class="inner" draggable="true"
					ondragstart="return dragStartSCHI(event)">
					<div id="schiButtons" class="schiButtons">
						<img src="../../resources/add.png" class="button"> <img
							src="../../resources/cross.png" class="button"
							onclick="processClickDelete(this)">
					</div>
				</td>
				<td rowspan="2" class="rightMover mover"></td>
			</tr>
		</tbody>
	</table>
</div>

<script>
	//Расстановка номеров
	$("#schedule>tbody").each(function(i) {
		this.index = i
		$(this).find("tr.empty").each(function(j) {
			this.schi=[]
		})
	})
</script>