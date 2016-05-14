<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<t:importAttribute name="toTitle" />
<spring:message code="${toTitle}" />