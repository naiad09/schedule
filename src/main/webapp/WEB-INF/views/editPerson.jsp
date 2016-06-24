<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@taglib
	uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h1>Редактировать профиль</h1>

<c:if test="${error != null}">
	<p class="alert error">Исправьте следующие ошибки:</p>
</c:if>

<form:form action="edit" method="post" modelAttribute="person">
	<input type="hidden" value="${person.role}" name="person" />
	<table>
		<tr>
			<td>Пароль</td>
			<td width="200px"><input name="authData.password"
				type="password" pattern="[\w0-9]{6,}" /></td>
			<td><form:errors path="authData.password" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Подтвердите пароль</td>
			<td><input name="confirmPassword" type="password" /></td>
			<td id="errorConfirm" class="error"></td>
		</tr>
		<tr>
			<td>Email</td>
			<td><form:input path="authData.email" type="email" /></td>
			<td><form:errors path="authData.email" cssClass="error" /></td>
		</tr>
		<c:if test="${person.role != 'edudep'}">
			<tr>
				<td>Получать рассылку</td>
				<td><form:checkbox path="authData.submit" /></td>
				<td>Получать письма о внеплановом обновлении расписания</td>
			</tr>
		</c:if>
		<sec:authorize
			access="hasRole('ROLE_ADMIN') and principal.uid != #personId">
			<tr>
				<td>Пользователь активен</td>
				<td><form:checkbox path="authData.active" /></td>
				<td></td>
			</tr>
		</sec:authorize>
		<t:insertTemplate template="utils/submitButton.jsp" />
	</table>
</form:form>

<script>
	function confirmPassword() {
		if (person.elements['authData.password'].value != person.elements['confirmPassword'].value) {
			errorConfirm.innerText = "Пароли не совпадают"
			return false
		} else {
			errorConfirm.innerText = ""
			return true
		}
	}
	person.onsubmit = function() {
		if (!confirmPassword())
			return false
		if (person.elements['authData.password'].value == "")
			person.elements['authData.password'].removeAttribute("name")
	}

	person.elements['authData.password'].onchange = confirmPassword
	person.elements['confirmPassword'].onchange = confirmPassword
</script>
