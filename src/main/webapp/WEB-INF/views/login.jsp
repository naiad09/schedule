<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Войти в систему</h1>

<c:url var="loginUrl" value="/login" />
<form action="${loginUrl}" method="post">
	<c:if test="${param.error != null}">
		<p class="alert">Неверные логин и пароль.</p>
	</c:if>

	<table>
		<tr>
			<td>Логин</td>
			<td><input type="text" id="username" name="j_username"
				placeholder="Введите логин" required></td>
		</tr>
		<tr>
			<td>Пароль</td>
			<td><input type="password" id="password" name="j_password"
				placeholder="Введите пароль" required></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Войти"></td>
		</tr>
	</table>
</form>