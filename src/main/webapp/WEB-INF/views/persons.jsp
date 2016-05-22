<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>

<h1>Пользователи</h1>

<form:form action="" method="get" commandName="personFinder"
	id="personFindForm"
	onsubmit="${person.role == 'lecturer' ? 'prerareChairs()' : ''}">
	<table>
		<tr>
			<td>Имя (часть имени)<br> <form:input style="width:300px"
					path="name" /></td>
			<td>Роль<br> <form:select path="role" id="roleSelector">
					<form:option value=""> -- Выберите роль -- </form:option>
					<form:option value="student">Студент</form:option>
					<form:option value="lecturer">Преподаватель</form:option>
					<form:option value="edudep">Учебный отдел</form:option>
				</form:select>
			</td>
			<td id="selectors"><div id="studentSelector"
					style="${personFinder.role=='student'?'':'display:none'}">
					Номер зачетной книжки
					<form:input path="recordBookNumber" pattern="\d{5}" />
				</div>
				<div id="lecturerSelector"
					style="${personFinder.role=='lecturer'?'':'display:none'}">
					Выберите научную степеть<br>
					<t:insertTemplate template="level2/degreeSelector.jsp" />
					<br> Выберите должность
					<c:set var="tempPath" scope="request" value="jobType" />
					<t:insertTemplate template="level2/jobTypeSelector.jsp" />
				</div>
				<div id="edudepSelector"
					style="${personFinder.role=='edudep'?'':'display:none'}">
					Выберите факультет<br>
					<t:insertTemplate template="level2/facultySelector.jsp" />
				</div> <script>
                $("#roleSelector option").click(
                        function(){
                            $("#selectors>div").hide();
                            $("#"+$(this).val()+"Selector").show()
                            }
                        )                   
                </script></td>
		</tr>
		<tr>
			<td>На странице: <form:select style="min-width:50px"
					path="perPage">
					<form:option value="10" />
					<form:option value="20" />
					<form:option value="30" />
					<form:option value="50" />
					<form:option value="100" />
				</form:select></td>
			<td><input type="submit" value="Искать"></td>
			<td><form:input type="hidden" path="page" /></td>
		</tr>
	</table>
</form:form>

<c:choose>
	<c:when test="${empty persons}">
		<p>Ничего не найдено.</p>
	</c:when>
	<c:otherwise>
		<p>
			<c:set value="${persons.size()/personFinder.perPage+1}" var="pages" />
			Найдено: ${persons.size()}<br>
			<c:if test="${pages>=2}">Страницы:
			<c:forEach begin="1" end="${pages}" varStatus="i">
					<a onclick="getPage(${i.index})" class="page">${i.index}</a>
				</c:forEach>
			</c:if>
		</p>
		<script>function getPage(i) {
			$("input#page").val(i)
	        $("#personFindForm").submit()
	        }</script>
		<table>
			<tr>
				<th>ФИО</th>
				<th>Роль</th>
			</tr>
			<c:forEach begin="${(personFinder.page-1)*personFinder.perPage}"
				end="${personFinder.page*personFinder.perPage}" items="${persons}"
				var="person" varStatus="i">
				<tr class="${person.role}">
					<td><a href="uid-${person.uid}">${person.fullTextName}</a></td>
					<td>${(person.role == 'student')?'студент':
					(person.role == 'lecturer')?'преподаватель':'учебный отдел'}
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>

