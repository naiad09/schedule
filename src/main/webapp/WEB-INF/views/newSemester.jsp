<%@page contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<h1>Создать семестр</h1>

<form:form action="new-semester" method="post" commandName="semester">

	<table>
		<tr>
			<td><h3>Учебный год</h3></td>
			<td><form:input path="semesterYear" type="number"
					required="required" style="width: 70px;" /> / <input
				id="semesterYear2" type="number" value="${semester.semesterYear+1}"
				style="width: 70px;" /></td>
			<td><form:errors path="semesterYear" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Полугодие</td>
			<td>Весна <form:radiobutton path="fallSpring" value="true" />
				Осень <form:radiobutton path="fallSpring" value="false"
					required="required" /></td>
			<td><form:errors path="fallSpring" cssClass="error" /></td>
		</tr>

		<t:insertTemplate template="utils/submitButton.jsp" />
	</table>
	<script>
		$("#semesterYear").change(function() {
			$("#semesterYear2").val(parseInt($(this).val()) + 1)
		})
		$("#semesterYear2").change(function() {
			$("#semesterYear").val(parseInt($(this).val()) - 1)
		})
	</script>
</form:form>
