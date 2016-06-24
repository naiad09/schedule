<%@page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<form:select path="degree">
	<form:option value=""> -- Выберите научную степень --</form:option>
	<c:forEach items="${refsContainer.degrees}" var="degree">
		<form:option value="${degree}">
			<spring:message code="${degree}.fullName" />
		</form:option>
	</c:forEach>
</form:select>