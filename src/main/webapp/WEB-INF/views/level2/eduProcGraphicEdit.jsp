<%@page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<td><input name="eduProcGraphics[].semesterStart" type="date"
	min="${dateMin}" max="${dateMax}"
	value="${graphic!=null?graphic.semesterStart:''}" /></td>
<td><input name="eduProcGraphics[].semesterEnd" type="date"
	min="${dateMin}" max="${dateMax}"
	value="${graphic!=null?graphic.semesterEnd:''}" /></td>
<td><input name="eduProcGraphics[].recordSessionStart" type="date"
	min="${dateMin}" max="${dateMax}"
	value="${graphic!=null?graphic.recordSessionStart:''}" /></td>
<td><input name="eduProcGraphics[].recordSessionEnd" type="date"
	min="${dateMin}" max="${dateMax}"
	value="${graphic!=null?graphic.recordSessionEnd:''}" /></td>
<td><input name="eduProcGraphics[].examsSessionStart" type="date"
	min="${dateMin}" max="${dateMax}"
	value="${graphic!=null?graphic.examsSessionStart:''}" /></td>
<td><input name="eduProcGraphics[].examsSessionEnd" type="date"
	min="${dateMin}" max="${dateMax}"
	value="${graphic!=null?graphic.examsSessionEnd:''}" /></td>