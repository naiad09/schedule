<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<form:select path="faculty">
	<form:option value=""> -- Без факультета --</form:option>
	<c:forEach items="${refsContainer.faculties}" var="faculty">
		<form:option value="${faculty}">
			<spring:message code="${faculty}.shortName" />
		</form:option>
	</c:forEach>
</form:select>