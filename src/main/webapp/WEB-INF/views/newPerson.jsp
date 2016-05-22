<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<h1>Создать ${person.role == 'student' ? 'студента' : person.role == 'lecturer'
 ? 'преподавателя' : 'работника учебного отдела'}
</h1>

<c:if test="${error == true}">
	<p class="alert error">Исправьте следующие ошибки:</p>
</c:if>
<c:if test="${!empty param.success}">
	<p class="alert success">
		Новый пользователь <a href="uid-${param.success}">создан</a>.
	</p>
</c:if>

<form:form action="new-${person.role}" method="post"
	commandName="person"
	onsubmit="${person.role == 'lecturer' ? 'prerareChairs()' : ''}">
	<input type="hidden" value="${person.role}" name="person" />
	<h2>Шаг 1: общая информация</h2>
	<table>
		<tr>
			<td>Фамилия</td>
			<td><form:input path="lastName" required="required"
					pattern="[А-ЯЁ][а-яё]+(-[А-Я][а-я]+)*" /></td>
			<td><form:errors path="lastName" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Имя</td>
			<td><form:input path="firstName" required="required"
					pattern="[А-ЯЁ][а-яё]+" /></td>
			<td><form:errors path="firstName" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Отчество</td>
			<td><form:input path="middleName" required="required"
					pattern="[А-ЯЁ][а-яё]+" /></td>
			<td><form:errors path="middleName" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Пол</td>
			<td><form:radiobutton path="gender" value="m"
					required="required" />Мужской <form:radiobutton path="gender"
					value="f" /> Женский</td>
			<td><form:errors path="gender" cssClass="error" /></td>
		</tr>
		<tr>
			<td>Дата рождения<br /> <small>например, 2012-05-07</small>
			</td>
			<td><form:input path="birthday" pattern="\d{4}-\d{2}-\d{2}" /></td>
			<td><form:errors path="birthday" cssClass="error" /></td>
		</tr>
	</table>

	<h2>Шаг 2: детальная информация</h2>
	<table>
		<c:choose>
			<c:when test="${person.role == 'student'}">
				<tr>
					<td>Номер зачетной книжки</td>
					<td><form:input path="recordBookNumber" required="required"
							pattern="\d{5}" /></td>
					<td><form:errors path="recordBookNumber" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Группа</td>
					<td><form:select required="required"
							path="${person.group!=null? 'group.idGroup':''}"
							name="${person.group==null? 'group.idGroup':''}">
							<form:option value=""> -- Выберите группу -- </form:option>
							<c:forEach items="${groups}" var="group">
								<c:set var="course" value="${group.curriculum.course}" />
								<form:option value="${group.idGroup}">
								    ${group.groupNumber},
									<c:if test="${course != null}">${course}-ый курс,</c:if>
									выпуск ${group.curriculum.yearEnd}
								</form:option>
							</c:forEach>
						</form:select></td>
					<td><form:errors
							path="${person.group!=null?'group.idGroup':''}" cssClass="error" /></td>
				</tr>
			</c:when>
			<c:when test="${person.role == 'lecturer'}">
				<tr>
					<td>Научная степень</td>
					<td><t:insertTemplate template="level2/degreeSelector.jsp" /></td>
					<td><form:errors path="degree" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><h3 style="text-align: center;">Кафедры</h3> <form:errors
							path="lecturerJobs" cssClass="error" />
						<table
							style="margin-bottom: 20px; 
						        ${empty person.lecturerJobs ? 'display:none' : ''}"
							id="chairs">
							<tr>
								<th style="width: 600px;">Кафедра</th>
								<th style="width: 210px;">Должность</th>
								<th></th>
							</tr>
							<c:forEach items="${person.lecturerJobs}" var="job" varStatus="i">
								<spring:eval
									expression="chairs.^[idChair == ${job.chair.idChair}]"
									var="chair" />
								<tr class="chair">
									<td><form:input type="hidden"
											path="lecturerJobs[${i.index}].chair.idChair" /> <spring:message
											code="${chair.faculty}.shortName" />, ${chair.fullName}</td>
									<td><c:set var="tempPath" scope="request"
											value="lecturerJobs[${i.index}].jobType" /> <form:errors
											path="${tempPath}" cssClass="error" /> <t:insertTemplate
											template="level2/jobTypeSelector.jsp" /></td>
									<td><a id="deleteChairLink" onclick="deleteRow(this)">Удалить</a></td>
								</tr>
							</c:forEach>
						</table>Добавить: <select id="chairSelector">
							<option>-- Выберите кафедру --</option>
							<c:forEach items="${chairs}" var="chair">
								<option value="${chair.idChair}">
									<spring:message code="${chair.faculty}.shortName" />,
									${chair.fullName}
								</option>
							</c:forEach>
					</select></td>
				</tr>
				<c:url var="jsUrl" value="../resources/js/new-lecturer.js" />
				<script src="${jsUrl}"></script>
			</c:when>
			<c:when test="${person.role == 'edudep'}">
				<tr>
					<td>Выберите факультет</td>
					<td><t:insertTemplate template="level2/facultySelector.jsp" /></td>
					<td><form:errors path="faculty" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Права администратора</td>
					<td><form:checkbox path="admin" /></td>
					<td>администратор имеет полные права на редактирование системы</td>
				</tr>
			</c:when>
		</c:choose>
	</table>

	<h2>
		Шаг 3: создать логин
		<c:if test="${person.role != 'edudep'}">? 
            <input type="button" id="authFormAddButton"
				value="${(person.authData == null) ? 'Создать логин' : 'Удалить логин'}" />
		</c:if>
	</h2>

	<form:errors path="authData" cssClass="error" />

	<div id="authForm">
		<c:if test="${person.authData != null}">
			<table>
				<tr>
					<td>Логин</td>
					<td><form:input path="authData.login" required="required"
							pattern="[a-z][a-z\\d_-]+" /></td>
					<td><form:errors path="authData.login" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Пароль</td>
					<td><form:input path="authData.password" required="required"
							type="password" /></td>
					<td><form:errors path="authData.password" cssClass="error" /></td>
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
			</table>
		</c:if>
	</div>

	<script>
		authForm = '<table><tr><td>Логин</td><td><input id="authData.login"'+
        ' name="authData.login" required="required" type="text" pattern="[a-z][a-z\\d_-]+" />'
				+ '</td><td></td></tr><tr><td>Пароль</td><td><input id="authData.password" '+
        'required="required" name="authData.password"'+
        ' type="password" value=""/></td><td></td></tr><tr><td>Email</td><td><input'+
        ' id="authData.email" name="authData.email" type="email" /></td><td></td></tr>'
	</script>

	<c:if test="${person.role != 'edudep'}">
		<script>
			authForm += '<tr><td>Получать рассылку</td><td><input type="checkbox" '+
			'checked="checked"name="authData.submit" />'
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


	<c:if test="${person.authData != null}">
		<script>
			authFormAdded = true
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
					<option value="true" ${returnHere == true ?'selected':''}>
						Вернуться на эту страницу</option>
					<option value="false" ${returnHere == false ?'selected':''}>
						Открыть страницу нового пользователя</option>
			</select></td>
			<td></td>
		</tr>
	</table>
</form:form>
