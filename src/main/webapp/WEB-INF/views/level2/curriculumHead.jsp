<%@page contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<thead>
	<tr>
		<th rowspan="3">Код</th>
		<th rowspan="3">Наименование дисциплины</th>
		<th colspan="3" rowspan="2">Часы</th>
		<th colspan="${enroll.semesterCount}">Распределение по семестрам</th>
		<th rowspan="3">Кафедра</th>
	</tr>
	<tr>
		<c:forEach begin="1" varStatus="i"
			end="${enroll.periodYears+(enroll.periodMonths>0?1:0)}">
			<th colspan="2"><small>${i.index} курс </small></th>
		</c:forEach>
	</tr>
	<tr>
		<th>Лек</th>
		<th>Лаб</th>
		<th>Упр</th>
		<c:forEach begin="1" end="${enroll.semesterCount}" varStatus="i">
			<th><small>${i.index}<br>сем.
			</small></th>
		</c:forEach>
	</tr>
</thead>