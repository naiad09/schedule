<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Создать новый набор</h1>

<form:form action="new" method="post" commandName="enroll">
	<table>
		<tr>
			<td><h3>Год</h3></td>
			<td><form:input path="yearStart" /></td>
			<td><form:errors path="yearStart" cssClass="error" /> <form:errors
					path="" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Тип обучения</td>
			<td><c:forEach items="${refsContainer.eduModes}" var="em">
					<form:radiobutton path="eduMode" value="${em}" />
					<spring:message code="${em}" />
				</c:forEach></td>
			<td><form:errors path="eduMode" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Квалификация выпускника</td>
			<td><c:forEach items="${refsContainer.qualTypes}" var="qual">
					<form:radiobutton path="eduQual" value="${qual}" />
					<spring:message code="${qual}.qual" />
				</c:forEach></td>
			<td><form:errors path="eduQual" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Период обучения</td>
			<td><form:input path="periodYears" type="number"
					style="width: 40px;" min="2" max="6" /> года <form:input
					path="periodMonths" type="number" min="0" max="10"
					style="width: 40px;" /> месяцев</td>
			<td><form:errors path="periodYears" cssClass="error" /> <form:errors
					path="periodMonths" cssClass="error" /></td>
		</tr>
	</table>

	<h2>Добавить направления</h2>
	<select size="30" multiple="multiple">
		<c:forEach items="${eduProgGroups}" var='epg'>
			<option disabled="disabled">${epg.eduProgGroupCode<10?'0':''}${epg.eduProgGroupCode}.00.00
				${epg.eduProgGroupName}</option>
			
		</c:forEach>
	</select>

	<table>
		<tr>
			<td></td>
			<td><button>Отправить</button></td>
			<td></td>
		</tr>
	</table>

</form:form>

