<%@page contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:forEach items="${refsContainer.discVars}" var="variab">
	<spring:eval var="discsVar"
		expression="selectionProfDisc.?[commonDiscipline.variability.toString() == '${variab}']" />
	<c:if test="${!empty discsVar}">
		<tr>
			<td></td>
			<td colspan="20"><b><spring:message code="${variab}" /></b></td>
		</tr>
		<c:forEach items="${discsVar}" var="discProfVar">
			<c:set value="${discProfVar.commonDiscipline}" var="discComVar" />
			<tr>
				<td>${discComVar.discCode}</td>
				<td>${discProfVar.discipline.discName}</td>
				<td>${discComVar.lectureHours}</td>
				<td>${discComVar.labHours}</td>
				<td>${discComVar.seminarHours}</td>

				<c:forEach begin="1" end="${enroll.semesterCount}" varStatus="i">
					<td><spring:eval var="h"
							expression="discComVar.discTerms.^[termNum==${i.index}]?.hoursHerWeek" />
						${h!=null?(h%1==0?h.intValue():h):''}</td>
				</c:forEach>

				<td><c:if test="${discProfVar.chair!=null}">
						<a
							href="../${discProfVar.chair.faculty}/${discProfVar.chair.shortNameEng}">${discProfVar.chair.shortName}</a>
					</c:if></td>
			</tr>
		</c:forEach>
	</c:if>
</c:forEach>

