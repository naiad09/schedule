<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${!empty studentList}">
	<h3>Студенты</h3>
	<table class="data">
		<c:forEach items="${studentList}" var="student">
			<tr>
				<td>${student.lastName}${student.firstName}
					${student.middleName}</td>
				<td>${student.email}</td>
				<td>${student.gender}</td>
				<td>${student.birthday}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>


<c:if test="${!empty lecturerList}">
	<h3>Преподаватели</h3>
	<table class="data">
		<c:forEach items="${lecturerList}" var="lecturer">
			<tr>
				<td>${lecturer.lastName}${lecturer.firstName}
					${lecturer.middleName}</td>
				<td>${lecturer.email}</td>
				<td>${lecturer.gender}</td>
				<td>${lecturer.birthday}</td>
				<td>${lecturer.degree}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<h3>Кафедры</h3>
<table class="data">
	<c:forEach items="${chairs}" var="chair">
		<tr>
			<td><spring:message code="${chair.faculty}.shortName" /></td>
			<td>${chair.chairName}</td>
			<td>${chair.chairShortname}</td>
		</tr>
	</c:forEach>
</table>
