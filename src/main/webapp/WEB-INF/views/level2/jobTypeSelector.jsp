<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<form:select path="${tempPath}" name="${tempName}">
	<form:option value="">-- Выберите должность --</form:option>
	<c:forEach items="${refsContainer.jobTypes}" var="jobType">
		<form:option value="${jobType}">
			<spring:message code="${jobType}.fullName" />
		</form:option>
	</c:forEach>
</form:select>