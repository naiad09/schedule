<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<h1>Пользователи</h1>

<form:form method="get" commandName="personFinder" id="personFindForm">
	<table>
		<tr>
			<td>Имя (часть имени)<br> <form:input style="width:400px"
					path="name" placeholder="Введите часть имени" /></td>
			<td style="width: 260px;">Роль<br> <form:select path="role"
					style="width: 260px;" id="roleSelector">
					<form:option value=""> -- Не важно -- </form:option>
					<form:option value="student">Студент</form:option>
					<form:option value="lecturer">Преподаватель</form:option>
					<form:option value="edudep">Учебный отдел</form:option>
				</form:select>
			</td>
			<td><div class="studentSelector selector">
					Номер зачетной книжки<br>
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
					path="gender" value="" id="anyGender"
					checked="${personFinder.gender==null?'cheked':''}" /> <label
				for="anyGender">Любой</label> <t:insertTemplate
					template="level2/genderSelector.jsp" /></td>
			<td>Логин: <form:radiobutton path="loginExists" value=""
					required="required"
					checked="${personFinder.loginExists==null?'cheked':''}" /> <label
				for="loginExists1">Не важно</label> <form:radiobutton
					path="loginExists" value="true" /> <label for="loginExists2">Есть</label>
				<form:radiobutton path="loginExists" value="false" /> <label
				for="loginExists3">Нет</label><br> <form:input path="login"
					disabled="true" placeholder="Введите логин" /></td>
			<td><div class="lecturerSelector selector">
					Выберите должность<br>
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
			<td><button>Искать</button>
				<button type="reset" onclick="resetForm()">Сбросить</button></td>
			<td><form:input type="hidden" path="page" /></td>
		</tr>
	</table>
</form:form>

<script>
    $("input[name='loginExists']").click(function() {
    	if(this.value=="true") 
    		$("input[name=login]").removeAttr("disabled")
    	else 
    		$("input[name=login]").val("").attr("disabled","true")
    })

	function getPage(i) {
    	personFindForm.elements[page].value=i
        personFindForm.submit()
	}
	    
    $("#roleSelector").change(function() {
            $(".selector").hide();
            $(".selector:not(."+this.value+"Selector)").find("input, select").val("")
            $("."+this.value+"Selector").show()
    })
	$("#roleSelector option[selected], input[type=radio][checked]").click();
    $("#roleSelector").change()
	
	function resetForm() {
        $("#roleSelector option:first-child").click()
        $("#roleSelector").change()
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
		<table class="borderTable">
			<thead>
				<tr>
					<th>ФИО</th>
					<th></th>
					<th></th>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<th></th>
					</sec:authorize>
				</tr>
			</thead>
			<tbody>
				<c:forEach begin="${(personFinder.page-1)*personFinder.perPage}"
					end="${personFinder.page*personFinder.perPage}" items="${persons}"
					var="person" varStatus="i">
					<tr class="${person.role}">
						<td><a href="persons/uid-${person.uid}">${person.fullTextName}</a></td>
						<c:choose>

							<c:when test="${person.role == 'student'}">
								<c:set var="course"
									value="${person.group.curriculum.commonCurriculum.enrollment.course}" />
								<td><c:choose>
										<c:when test="${course == null}">выпускник группы 
										${person.group.groupNumber},</c:when>
										<c:otherwise>cтудент группы ${person.group.groupNumber},
										 ${course}-ый курс,</c:otherwise>
									</c:choose></td>
								<td>выпуск
									${person.group.curriculum.commonCurriculum.enrollment.yearEnd}</td>
							</c:when>

							<c:when test="${person.role == 'lecturer'}">
								<td><spring:message code="${person.degree}.fullName" /></td>
								<td><c:forEach items="${person.lecturerJobs}" var="job"
										varStatus="loop">
										<spring:message code="${job.jobType}.shortName" />
										<a
											href="${baseUrl}/${job.chair.faculty}/${job.chair.shortNameEng}">
											${job.chair.shortName}</a>
										<c:if test="${!loop.last }">,<br>
										</c:if>
									</c:forEach></td>
							</c:when>

							<c:when test="${person.role == 'edudep'}">
								<td>работник учебного отдела</td>
								<td><c:if test="${person.faculty != null}">
										<br>диспетчер
										<a
											href="${baseUrl}/${job.chair.faculty}">
											<spring:message code="${person.faculty}.shortName" />
										</a>
									</c:if></td>
							</c:when>

						</c:choose>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<td><a href="persons/uid-${person.uid}/edit">Редактировать</a></td>
						</sec:authorize>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>
