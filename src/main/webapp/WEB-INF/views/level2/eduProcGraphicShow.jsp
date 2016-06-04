<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>

<td><fmt:formatDate pattern="d MMMM" value="${graphic.eduStart}" /></td>
<td><fmt:formatDate pattern="d MMMM" value="${graphic.semestrEnd}" /></td>
<td><fmt:formatDate pattern="d MMMM"
		value="${graphic.recordSessionStart}" /></td>
<td><fmt:formatDate pattern="d MMMM"
		value="${graphic.recordSessionEnd}" /></td>
<td><fmt:formatDate pattern="d MMMM"
		value="${graphic.examsSessionStart}" /></td>
<td><fmt:formatDate pattern="d MMMM"
		value="${graphic.examsSessionEnd}" /></td>