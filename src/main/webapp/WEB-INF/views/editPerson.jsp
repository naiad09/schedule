<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h1>${person.fullTextName}</h1>

<c:if test="${error != null}">
	<p class="alert error">Исправьте следующие ошибки:</p>
</c:if>

<form:form action="edit" method="post" commandName="person">
	<input type="hidden" value="${person.role}" name="person" />
	<table>
		<tr>
			<td>Пароль</td>
			<td><form:input path="authData.password" type="password" /></td>
			<td><form:errors path="authData.password" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Email</td>
			<td><form:input path="email" /></td>
			<td><form:errors path="email" cssClass="error" /></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Сохранить"></td>
			<td></td>
		</tr>
	</table>
</form:form>
