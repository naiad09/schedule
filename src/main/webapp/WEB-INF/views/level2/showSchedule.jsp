<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>

<input type="hidden" value="${schedule.eduProcGraphic.semesterStart}"
	id="dateStart" type="hidden" />
<input type="hidden" value="${schedule.eduProcGraphic.scheduleChange}"
	id="dateChange" type="hidden" />
<input type="hidden" value="${schedule.eduProcGraphic.semesterEnd}"
	id="dateEnd" type="hidden" />

<table style="width: 873px;">
	<tr>
		<td><t:insertTemplate template="scheduleEmptyTable.jsp" /></td>
	</tr>
</table>

<div style="display: none">
	<div id="scheduleDisciplines">
		<c:forEach items="${schedule.scheduleDisciplinesMap}" var="mapRow">
			<div class="scheDisc">
				<b>${mapRow.value[0].disc.discName}</b> <br>
				<c:forEach items="${mapRow.value}" var="glt">
					<div class="glt ${glt.lessonType}"
						id="glt${glt.idScheduleDiscipline}">
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
	</div>

	<select id="lecLecturerSelector">
		<c:forEach items="${lecturers}" var="lecturer">
			<c:if test="${!empty lecturer.lecturerJobs}">
				<spring:message code="${lecturer.lecturerJobs[0].jobType}.shortName"
					var="mainJob" />
			</c:if>
			<option value="${lecturer.uid}"
				title="${mainJob} ${lecturer.fullName}" />
		</c:forEach>
	</select>
	<t:insertTemplate template="classroomSelestor.jsp" />

	<table id="schiTemplate" class="schi">
		<tbody>
			<tr>
				<td class="leftMover mover"></td>
				<td class="inner"><span class="twain"></span> <span
					class="numDen"></span> <span class="discipline"></span> <input
					type="hidden"
					name="scheduleDisciplines[].scheduleItems[].idScheduleItem" /> <input
					type="hidden" name="scheduleDisciplines[].scheduleItems[].weekplan" />
					<input type="hidden"
					name="scheduleDisciplines[].scheduleItems[].comment" /> <input
					type="hidden"
					name="scheduleDisciplines[].scheduleItems[].twain.idTwain" /> <input
					type="hidden" name="scheduleDisciplines[].scheduleItems[].weekday" />
					<small class="details"> <span class="lecturers"></span> <span
						class="divider"></span> <span class="classrooms"></span>
				</small> <small class="weekplan"></small></td>
				<td class="rightMover mover"></td>
			</tr>
		</tbody>
	</table>
	<div id="scheduleItemsInfo">
		<c:forEach items="${schedule.scheduleDisciplines}" var="glt">
			<c:forEach items="${glt.scheduleItems}" var="schi">
				<div class="schiInfo">${schi.idScheduleItem},
					${schi.twain.idTwain}, ${glt.idScheduleDiscipline},
					${schi.weekday}, ${schi.weekplan}, ${schi.isConflict()},
					${schi.comment}
					<c:forEach items="${schi.classrooms}" var="c">
						<input type="hidden" class="classroomInput"
							value="${c.idClassroom}"
							name="scheduleDisciplines[].scheduleItems[].classrooms[].idClassroom" />
					</c:forEach>
				</div>
			</c:forEach>
		</c:forEach>
	</div>
</div>

<script src="${baseUrl}/resources/js/schedule/weekplan.js"></script>
<script src="${baseUrl}/resources/js/schedule/schedule.js"></script>
<script>
	$("tr.empty:not(.lab4Prev)").remove()
	$("tbody.weekday").filter(function() {
		return $(this).find("tr").length == 1
	}).remove()
</script>