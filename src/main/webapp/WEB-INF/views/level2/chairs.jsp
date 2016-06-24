<%@page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${!empty chairs}">
	<table>
		<c:forEach items="${chairs}" var="chair">
			<tr>
				<td>${chair.shortName}</td>
				<td><a href="${chair.faculty}/${chair.shortNameEng}">${chair.fullName}</a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>