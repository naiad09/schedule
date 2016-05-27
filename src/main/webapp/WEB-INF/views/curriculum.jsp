<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3>Рабочий учебный план</h3>
<h1>Направление подготовки ${eduProgram.eduProgCode}
	"${eduProgram.eduProgName}"</h1>
<c:if
	test="${eduProgram.skillProfiles.size() > 0 && !empty eduProgram.skillProfiles[0].profileName}">
	<p>
		<small><c:forEach items="${eduProgram.skillProfiles}" var="sp"
				varStatus="i">
			ООП ${i.index+1} "${sp.profileName}" (кафедра
				${sp.chair.shortName})
		</c:forEach></small>
	</p>
</c:if>

<table>
	<tr>
		<td><small>Обучение <spring:message code="${mode}" /></small><br>Набор
			${year} года</td>
		<td>Квалификация выпускника - <spring:message
				code="${eduProgram.eduQual}.qual" /><br>Срок обучения - ${eduProgram.eduQual}</td>
	</tr>
</table>