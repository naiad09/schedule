<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<form:radiobutton path="gender" value="m" required="required"
	id="maleGender" />
<label for="maleGender">Мужской</label>
<form:radiobutton path="gender" value="f" id="femaleGender" />
<label for="femaleGender">Женский</label>
