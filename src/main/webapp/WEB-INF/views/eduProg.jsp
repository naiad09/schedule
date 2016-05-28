<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<c:set var="eduProg" value="${curs[0].skillProfile.eduProgram}" />

<h3>Направление подготовки</h3>
<h1>${eduProg.eduProgCode}"${eduProg.eduProgName}"</h1>
<c:if
	test="${curs.size() > 0 && !empty curs[0].skillProfile.profileName}">
	<p>
		<small><c:forEach items="${curs}" var="cur" varStatus="i">ООП ${i.index+1} "${cur.skillProfile.profileName}"<br>
			</c:forEach></small>
	</p>
</c:if>

<table>
	<tr>
		<td><small>Обучение <spring:message
					code="${curs[0].enrollment.eduMode}" /><br>Набор
				${curs[0].enrollment.yearStart} года
		</small></td>
		<c:set var="enroll" value="${curs[0].enrollment}" scope="request" />
		<td><small>Квалификация выпускника - <spring:message
					code="${enroll.eduQual}.qual" /><br>Срок обучения -
				${enroll.periodYears} ${enroll.periodYears>4?'лет':'года'}<c:if
					test="${enroll.periodMonths!=0}">
				${enroll.periodMonths} месяцев</c:if>
		</small></td>
	</tr>
</table>

<h2>Учебные дисциплины</h2>

<table id="curriculum">
	<thead>
		<tr>
			<th rowspan="3">Код</th>
			<th rowspan="3">Наименование дисциплины</th>
			<th colspan="3" rowspan="2">Часы</th>
			<th colspan="${enroll.semesterCount}">Распределение по семестрам</th>
			<th rowspan="3">Кафедра</th>
		</tr>
		<tr>
			<c:forEach begin="1" varStatus="i"
				end="${enroll.periodYears+(enroll.periodMonths>0?1:0)}">
				<th colspan="2"><small>${i.index}<br>курс
				</small></th>
			</c:forEach>
		</tr>
		<tr>
			<th>Лек</th>
			<th>Лаб</th>
			<th>Упр</th>
			<c:forEach begin="1" end="${enroll.semesterCount}" varStatus="i">
				<th><small>${i.index}<br>сем.
				</small></th>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<spring:eval
			expression="curs[0].curDisciplines.?[commonProfile==true]"
			var="common" />
		<c:forEach begin="1" end="3" varStatus="i">
			<tr class="blockHead${i.index}">
				<th>Блок ${i.index}</th>
				<th>Дисциплины (модули)<br> <small><spring:message
							code="mod${i.index}" /></small></th>
				<th colspan="20"><c:if test="${curs.size() > 0}">Для всех ООП данного направления</c:if></th>
			</tr>
			<spring:eval expression="common.?[discipline.discMod == ${i.index}]"
				var="curDiscs" scope="request" />
			<t:insertTemplate template="level2/curDisc.jsp" />
		</c:forEach>

		<c:forEach items="${curs}" var="cur" varStatus="i">
			<tr class="blockHead4">
				<th></th>
				<th colspan="21">ООП ${i.index+1}
					"${cur.skillProfile.profileName}"</th>
			</tr>
			<spring:eval expression="cur.curDisciplines.?[commonProfile==false]"
				var="curDiscs" scope="request" />
			<t:insertTemplate template="level2/curDisc.jsp" />
		</c:forEach>
	</tbody>
</table>