<%@page contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<h1>
	<spring:message code="${faculty}.fullName" />
</h1>
<t:insertTemplate template="level2/chairs.jsp" />

<h2>Группы</h2>
<table style="text-align: center">
	<tr>
		<th>Группа</th>
		<th>Курс</th>
		<th>Набор</th>
		<th>Выпуск</th>
		<th>Учебный план</th>
	</tr>
	<c:forEach items="${chairs}" var="chair">
		<c:forEach items="${chair.skillProfiles}" var="sp">
			<c:forEach items="${sp.curriculums}" var="cur">
				<c:forEach items="${cur.groups}" var="group">
					<tr>
						<td><a href="${faculty}/group-${group.idGroup}">Группа
								${group.groupNumber}</a></td>
						<td><c:set var="course"
								value="${cur.commonCurriculum.enrollment.course}" /> <c:if
								test="${course != null}">${course}-ый курс</c:if></td>
						<td>${cur.commonCurriculum.enrollment.yearStart}-ый год</td>
						<td>${cur.commonCurriculum.enrollment.yearEnd}-ый год</td>
						<td><a
							href="cur/cur-${cur.commonCurriculum.idCommonCurriculum}">
								${sp.eduProgram.eduProgCode}</a></td>
					</tr>
				</c:forEach>
			</c:forEach>
		</c:forEach>
	</c:forEach>
</table>

