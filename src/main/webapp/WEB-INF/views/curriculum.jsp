<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<c:set var="eduProg" value="${cur.eduProgram}" />

<h3>Направление подготовки</h3>
<h1>${eduProg.eduProgCode}"${eduProg.eduProgName}"</h1>
<c:if
	test="${cur.curriculums.size() > 0 && cur.curriculums[0].skillProfile.profileName != null}">
	<p>
		<small><c:forEach items="${cur.curriculums}" var="cur"
				varStatus="i">ООП ${i.index+1} "${cur.skillProfile.profileName}"<br>
			</c:forEach></small>
	</p>
</c:if>

<c:set var="enroll" value="${cur.enrollment}" scope="request" />
<table>
	<tr>
		<td><small>Форма обучения: <spring:message
					code="${enroll.eduMode}" /><br>Набор ${enroll.yearStart} года
		</small></td>
		<td><small>Квалификация выпускника - <spring:message
					code="${enroll.eduQual}.qual" /><br>Срок обучения -
				${enroll.periodYears} ${enroll.periodYears>4?'лет':'года'}<c:if
					test="${enroll.periodMonths!=0}">
                ${enroll.periodMonths} месяцев</c:if>
		</small></td>
	</tr>
</table>

<h2>Учебные дисциплины</h2>

<table id="curriculum" class="borderTable">
	<c:forEach begin="1" end="3" varStatus="i">
		<t:insertTemplate template="level2/curriculumHead.jsp" />

		<tr class="blockHead${i.index}">
			<th>Блок 1</th>
			<th>Дисциплины (модули)<br> <small><spring:message
						code="mod${i.index}" /></small></th>
			<th colspan="20"><c:if test="${cur.curriculums.size() > 0}">Для всех ООП данного направления</c:if></th>
		</tr>
		<spring:eval scope="request" var="selectionProfDisc"
			expression="cur.commonDisciplines.![profileDisciplines.^[curriculum==null && discipline.discMod==${i.index}]].?[#this!=null]" />
		<t:insertTemplate template="level2/curDisc.jsp" />
	</c:forEach>


	<c:forEach items="${cur.curriculums}" var="profileCur" varStatus="i">
		<t:insertTemplate template="level2/curriculumHead.jsp" />
		<tr class="blockHead4">
			<th></th>
			<th colspan="21">ООП ${i.index+1}
				"${profileCur.skillProfile.profileName}"</th>
			<c:set var="code" scope="request" value="${i.index+1}" />
			<spring:eval scope="request" var="selectionProfDisc"
				expression="cur.commonDisciplines.![profileDisciplines.^[curriculum==${i.index+1}]].?[#this!=null]" />
			<t:insertTemplate template="level2/curDisc.jsp" />
		</tr>
	</c:forEach>
</table>