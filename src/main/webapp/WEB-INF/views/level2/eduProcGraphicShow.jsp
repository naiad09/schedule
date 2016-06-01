<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<td><fmt:formatDate pattern="yyyy-MM-dd"
		value="${graphic.eduStart}" /></td>
<td><fmt:formatDate pattern="yyyy-MM-dd"
		value="${graphic.semestrEnd}" /></td>
<td><fmt:formatDate pattern="yyyy-MM-dd"
		value="${graphic.recordSessionStart}" /></td>
<td><fmt:formatDate pattern="yyyy-MM-dd"
		value="${graphic.recordSessionEnd}" /></td>
<td><fmt:formatDate pattern="yyyy-MM-dd"
		value="${graphic.examsSessionStart}" /></td>
<td><fmt:formatDate pattern="yyyy-MM-dd"
		value="${graphic.examsSessionEnd}" /></td>