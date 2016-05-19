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
		<c:when test="${person.role == 'student'}"></c:when>
		<c:when test="${person.role == 'lecturer'}"></c:when>
		<c:when test="${person.role == 'edudep'}">
			<table>
				<tr>
					<td>Выберите факультет</td>
					<td><form:select path="faculty">
							<form:option value="null"> -- Без факультета --</form:option>
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

	<h2>Шаг 3: создать логин?</h2>

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

	<table>
		<tr>
			<td></td>
			<td><input type="submit" value="Сохранить"></td>
			<td></td>
		</tr>
	</table>
</form:form>
