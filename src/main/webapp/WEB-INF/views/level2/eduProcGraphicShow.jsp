<%@page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>

<td><fmt:formatDate pattern="d MMMM" value="${graphic.semesterStart}" /></td>
<td><fmt:formatDate pattern="d MMMM" value="${graphic.semesterEnd}" /></td>
<td><fmt:formatDate pattern="d MMMM"
		value="${graphic.recordSessionStart}" /></td>
<td><fmt:formatDate pattern="d MMMM"
		value="${graphic.recordSessionEnd}" /></td>
<td><fmt:formatDate pattern="d MMMM"
		value="${graphic.examsSessionStart}" /></td>
<td><fmt:formatDate pattern="d MMMM"
		value="${graphic.examsSessionEnd}" /></td>