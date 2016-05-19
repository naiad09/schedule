<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<h1>
	<spring:message code="newPerson" />
</h1>

<c:if test="${error != null}">
	<p class="alert error">Исправьте следующие ошибки:</p>
</c:if>

<form:form action="new-${person.role}" method="post"
	commandName="person">
	<input type="hidden" value="${person.role}" name="person" />
	<h2>Шаг 1: общая информация</h2>
	<table>
		<tr class="required">
			<td>Фамилия</td>
			<td><form:input path="lastName" required="required" /></td>
			<td><form:errors path="lastName" cssClass="error" /></td>
		</tr>
		<tr class="required">
			<td>Имя</td>
			<td><form:input path="firstName" required="required" /></td>
			<td><form:errors path="firstName" cssClass="error" /></td>
		</tr>
		<tr class="required">
			<td>Отчество</td>
			<td><form:input path="middleName" required="required" /></td>
			<td><form:errors path="middleName" cssClass="error" /></td>
		</tr>
		<tr class="required">
			<td>Пол</td>
			<td><form:radiobutton path="gender" value="m" />Мужской <form:radiobutton
					path="gender" value="f" /> Женский</td>
			<td><form:errors path="gender" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Дата рождения</td>
			<td><form:input path="birthday" /></td>
			<td><form:errors path="birthday" cssClass="error" /></td>
		</tr>
	</table>

	<h2>Шаг 2: детальная информация</h2>

	<c:choose>
		<c:when test="${person.role == 'student'}">
			<table>
				<tr class="required">
					<td>Номер зачетной книжки</td>
					<td><form:input path="recordBookNumber" /></td>
					<td><form:errors path="recordBookNumber" cssClass="error" /></td>
				</tr>
				<tr class="required">
					<td>Группа</td>
					<td><form:select path="group">
							<form:options items="${groupList}" itemValue="idGroup"
								itemLabel="groupNumber" />
						</form:select></td>
					<td><form:errors path="group" cssClass="error" /></td>
				</tr>
			</table>
		</c:when>
		<c:when test="${person.role == 'lecturer'}"></c:when>
		<c:when test="${person.role == 'edudep'}">
			<table>
				<tr>
					<td>Выберите факультет</td>
					<td><form:select path="faculty">
							<form:option value=""> -- Без факультета --</form:option>
							<c:forEach items="${faculties}" var="faculty">
								<form:option value="${faculty}">
									<spring:message code="${faculty}.shortName" />
								</form:option>
							</c:forEach>
						</form:select></td>
					<td><form:errors path="faculty" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Права администратора</td>
					<td><form:checkbox path="admin" /></td>
					<td>администратор имеет полные права на редактирование системы</td>
				</tr>
			</table>
		</c:when>
	</c:choose>

	<h2>
		Шаг 3: создать логин
		<c:if test="${person.role != 'edudep'}">? 
            <input type="button" id="authFormAddButton"
				value="Создать логин" />
		</c:if>
	</h2>

	<form:errors path="authData" cssClass="error" />

	<div id="authForm">
		<c:if test="${person.authData != null}">
			<table>
				<tr>
					<td>Логин</td>
					<td><form:input path="authData.login" type="text" /></td>
					<td><form:errors path="authData.login" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Пароль</td>
					<td><form:input path="authData.password" type="password" /></td>
					<td><form:errors path="authData.password" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><form:input path="authData.email" /></td>
					<td><form:errors path="authData.email" cssClass="error" /></td>
				</tr>
				<c:if test="${person.role != 'edudep'}">
					<tr>
						<td>Получать рассылку</td>
						<td><form:checkbox path="authData.submit" /></td>
						<td>Получать письма о внеплановом обновлении расписания</td>
					</tr>
				</c:if>
			</table>
		</c:if>
	</div>

	<script>
		authForm = '<table><tr><td>Логин</td><td><input id="authData.login"'+
        ' name="authData.login" type="text" value=""/></td><td></td></tr>'
				+ '<tr><td>Пароль</td><td><input id="authData.password" name="authData.password"'+
        ' type="password" value=""/></td><td></td></tr><tr><td>Email</td><td><input'+
        ' id="authData.email" name="authData.email" type="text" value=""/></td><td></td></tr>'
	</script>

	<c:if test="${person.role != 'edudep'}">
		<script>
			authForm += '<tr><td>Получать рассылку</td><td><input type="checkbox" name="authData.submit" />'
					+ '</td><td>Получать письма о внеплановом обновлении расписания</td></tr>'
		</script>
	</c:if>

	<script>
		authForm += '</table>'

		var authFormAdded = false

		$("#authFormAddButton").click(function() {
			if (authFormAdded) {
				authFormAdded = false
				$("#authForm").empty()
				$("#authFormAddButton").attr("value", "Создать логин")
			} else {
				authFormAdded = true
				$("#authForm").html(authForm)
				$("#authFormAddButton").attr("value", "Удалить логин")
			}
		})
	</script>

	<c:if test="${person.role == 'edudep' && person.authData == null}">
		<script>
			$("#authForm").html(authForm)
		</script>
	</c:if>

	<table>
		<tr>
			<td></td>
			<td><input type="submit" value="Отправить"></td>
			<td></td>
		</tr>
		<tr>
			<td><h3>После сохранения:</h3></td>
			<td><select name="returnHere">
					<option value="true"
						<c:if test="${returnHere}">selected="selected"</c:if>>Вернуться
						на эту страницу</option>
					<option value="false"
						<c:if test="${!returnHere}">selected="selected"</c:if>>Открыть
						страницу нового пользователя</option>
			</select></td>
			<td></td>
		</tr>
	</table>
</form:form>
