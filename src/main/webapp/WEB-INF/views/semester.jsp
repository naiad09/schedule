<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>${semester.semesterYear}/${semester.semesterYear+1}-ый&nbsp;учебный&nbsp;год,
	${semester.fallSpring?'весна':'осень'}</h1>

<c:forEach items="${refsContainer.eduModes}" var="eduMode">
	<spring:eval
		expression="actualEnrolls.?[eduMode.toString()=='${eduMode}']"
		var="eduModeEnrolls" />
	<c:if test="${!empty eduModeEnrolls}">
		<h2>
			Форма обучения:
			<spring:message code="${eduMode}" />
		</h2>
		<c:forEach items="${refsContainer.qualTypes}" var="qual">
			<spring:eval
				expression="eduModeEnrolls.?[eduQual.toString()=='${qual}']"
				var="enrolls" />
			<c:if test="${!empty enrolls}">
				<h3>
					<spring:message code="${qual}.head" />
				</h3>
				<c:forEach begin="1" end="6" varStatus="i">
					<c:forEach items="${enrolls}" var="enroll">
						<c:if test="${semester.semesterYear - enroll.yearStart + 1 == i.index}">
							<p>${i.index}&nbsp;курс:&nbsp;набор&nbsp;${enroll.yearStart}</p>
							<p>
								<c:forEach items="${enroll.commonCurriculums}" var="comcur">
									<a href="../cur/id-${comcur.idCommonCurriculum}">${comcur.eduProgram.eduProgCode}</a>
								</c:forEach>
							</p>
						</c:if>
					</c:forEach>
				</c:forEach>
			</c:if>
		</c:forEach>
	</c:if>
</c:forEach>