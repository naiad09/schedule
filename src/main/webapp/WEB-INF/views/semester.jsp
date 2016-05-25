<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Создать семестр</h1>

<form:form action="new-semester" method="post" commandName="semester">

	<table>
		<tr>
			<td><h3>Год</h3></td>
			<td><form:input path="semesterYear" required="required" /></td>
			<td><form:errors path="semesterYear" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Полугодие</td>
			<td>Весна <form:radiobutton path="fallSpring" value="false" />
				Осень <form:radiobutton path="fallSpring" value="true"
					required="required" /></td>
			<td><form:errors path="fallSpring" cssClass="error" /></td>
		</tr>
	</table>

	<h2>Графики учебного процесса</h2>

	<table id="eduProcGraphics">
		<c:forEach items="${semester.eduProcGraphics}">ff</c:forEach>
	</table>

	<table>
		<tr>
			<td></td>
			<td><button>Отправить</button></td>
			<td></td>
		</tr>
	</table>

</form:form>
