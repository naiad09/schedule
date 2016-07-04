<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Войти в систему</h1>
<c:if test="${param.error == true}">
	<p class="alert error">Неверные логин и пароль.</p>
</c:if>
<c:url var="loginUrl" value="/login" />
<form action="${loginUrl}" method="post">
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
			<td><button>Войти</button></td>
		</tr>
	</table>
</form>