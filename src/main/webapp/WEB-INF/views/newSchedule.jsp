<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<h1>Создать расписание</h1>
<a href="new">Сюда</a>

<form:form action="new" method="post" commandName="schedule">
	<table>
		<tr>
			<td>Группа</td>
			<td><form:select required="required" path="group.idGroup"
					id="group">
					<option value="">-- Выберите группу --</option>
					<c:forEach items="${groups}" var="group">
						<c:set var="enroll"
							value="${group.curriculum.commonCurriculum.enrollment}" />
						<form:option value="${group.idGroup}"
							terms="${enroll.semesterCount}"> ${group.groupNumber}, 
					    <c:if test="${enroll.course != null}">${enroll.course}-ый курс,</c:if> выпуск ${enroll.yearEnd}
					    </form:option>
					</c:forEach>
				</form:select></td>
			<td><form:errors path="group.idGroup" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Номер семестра</td>
			<td><form:input path="termNum" required="required" type="number"
					min="1" /></td>
			<td><form:errors path="termNum" cssClass="error" /></td>
		</tr>
		<tr>
			<td></td>
			<td class="success"><form:errors path="" cssClass="error" /></td>
		</tr>
		<t:insertTemplate template="utils/submitButton.jsp" />
	</table>
</form:form>
<script>
	$("#group option").click(function() {
		$("#termNum").attr("max", $(this).attr("terms"))
	})
</script>