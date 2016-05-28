<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:forEach items="${refsContainer.discVars}" var="variab">
	<spring:eval
		expression="curDiscs.?[variability.toString() == '${variab}']"
		var="curDiscsVar" />
	<c:if test="${!empty curDiscsVar}">
		<tr>
			<td></td>
			<td colspan="20"><b><spring:message code="${variab}" /></b></td>
		</tr>
		<c:forEach items="${curDiscsVar}" var="curDisc">
			<tr>
				<td>${curDisc.discCode}</td>
				<td>${curDisc.discipline.discName}</td>
				<td>${curDisc.lectureHours}</td>
				<td>${curDisc.labHours}</td>
				<td>${curDisc.seminarHours}</td>

				<c:forEach begin="1" end="${enroll.semesterCount}" varStatus="i">
					<td><spring:eval var="h"
							expression="curDisc.discTerms.^[termNum==${i.index}]?.hoursHerWeek" />
						${h!=null?(h%1==0?h.intValue():h):''}</td>
				</c:forEach>

				<td><c:if test="${curDisc.chair!=null}">
						<a
							href="../${curDisc.chair.faculty}/${curDisc.chair.shortNameEng}">${curDisc.chair.shortName}</a>
					</c:if></td>
			</tr>
		</c:forEach>
	</c:if>
</c:forEach>

