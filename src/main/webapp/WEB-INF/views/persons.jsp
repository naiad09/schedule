<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<h1>Пользователи</h1>

<form:form action="" method="get" commandName="personFinder"
	id="personFindForm"
	onsubmit="${person.role == 'lecturer' ? 'prerareChairs()' : ''}">
	<table>
		<tr>
			<td>Имя (часть имени)<br> <form:input style="width:300px"
					path="name" placeholder="Введите часть имени"/></td>
			<td style="width: 260px;">Роль<br> <form:select path="role"
					id="roleSelector">
					<form:option value=""> -- Не важно -- </form:option>
					<form:option value="student">Студент</form:option>
					<form:option value="lecturer">Преподаватель</form:option>
					<form:option value="edudep">Учебный отдел</form:option>
				</form:select>
			</td>
			<td><div class="studentSelector selector">
					Номер зачетной книжки
					<form:input path="recordBookNumber" pattern="\d{5}" />
				</div>
				<div class="lecturerSelector selector">
					Выберите научную степеть<br>
					<t:insertTemplate template="level2/degreeSelector.jsp" />
				</div>
				<div class="edudepSelector selector">
					Выберите факультет<br>
					<t:insertTemplate template="level2/facultySelector.jsp" />
				</div></td>
		</tr>
		<tr>
			<td style="vertical-align: top;">Пол: <form:radiobutton
					path="gender" value=""
					checked="${personFinder.gender==null?'cheked':''}" /> Любой <t:insertTemplate
					template="level2/genderSelector.jsp" /></td>
			<td>Логин: <form:radiobutton path="loginExists"
					required="required"
					checked="${personFinder.loginExists==null?'cheked':''}" value="" />
				Не важно <form:radiobutton path="loginExists" value="true" /> Есть<form:radiobutton
					path="loginExists" value="false" /> Нет<br> <form:input
					path="login" disabled="true" placeholder="Введите логин" /></td>
			<td><div class="lecturerSelector selector">
					Выберите должность
					<c:set var="tempPath" scope="request" value="jobType" />
					<t:insertTemplate template="level2/jobTypeSelector.jsp" />
				</div></td>
		</tr>
		<tr>
			<td>На странице: <form:select style="min-width:50px"
					path="perPage">
					<form:option value="10" />
					<form:option value="15" />
					<form:option value="20" />
					<form:option value="30" />
					<form:option value="50" />
					<form:option value="100" />
				</form:select></td>
			<td><button onclick="prepareForm()">Искать</button>
				<button type="button" onclick="resetForm()">Сбросить</button></td>
			<td><form:input type="hidden" path="page" /></td>
		</tr>
	</table>
</form:form>

<script>
    $("input[name='loginExists']").click(function() {
    	if($(this).val()=="true") 
    		$("input[name=login]").removeAttr("disabled")
    	else 
    		$("input[name=login]").val("").attr("disabled","true")
    })

	function getPage(i) {
	    $("input#page").val(i)
	    $("#personFindForm").submit()
	    }
	    
	$("#roleSelector option").click(function(){
			    $(".selector").hide();
			    $(".selector:not(."+$(this).val()+"Selector)").find("input, select").val("")
			    $("."+$(this).val()+"Selector").show()
			})     
	$("#roleSelector option[selected], input[type=radio][checked]").click();
	
	function resetForm() {
		$("form input:not([type=radio]):not([type=hidden]), form select").val("")
		$("input[type=radio][value=''], #roleSelector option:first-child").click()
	}
	
	// Подготавливает форму к отправке, удаляя имена у пустых атрибутов
	function prepareForm() {
		$("select").filter(
				function(){
					if($(this).val()=="") return true
					}
				)
		.add("input[value='']:not([type='radio'])").attr("name","")
		$("input[type=radio][value='']:checked").each(function(){
			 $("input[type=radio][name="+$(this).attr("name")+"]")
			     .removeAttr("required")
			     .removeAttr("name")
			 })
	}
</script>

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
		<table>
			<tr>
				<th>ФИО</th>
				<th>Роль</th>
			</tr>
			<c:forEach begin="${(personFinder.page-1)*personFinder.perPage}"
				end="${personFinder.page*personFinder.perPage}" items="${persons}"
				var="person" varStatus="i">
				<tr class="${person.role}">
					<td><a href="persons/uid-${person.uid}">${person.fullTextName}</a></td>
					<td>${(person.role == 'student')?'студент':
					(person.role == 'lecturer')?'преподаватель':'учебный отдел'}
					</td>
					<td><sec:authorize url="/persons/uid-${person.uid}/edit">
							<a href="persons/uid-${person.uid}/edit">Редактировать</a>
						</sec:authorize></td>
				</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
