<%@page contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/custom.tld" prefix="fmt"%>
<div id="scheduleOverflow">
	<table id="schedule" class="borderTable"
		ondragover="return dragEnter(event)">
		<thead>
			<tr>
				<th rowspan="3" style="width: 21px;"></th>
				<th rowspan="3">Пара</th>
				<th colspan="4" style="font-size: 26px;">${schedule.group.groupNumber}</th>
			</tr>
			<tr>
				<th colspan="2"
					style="width: 43%; border-bottom: none; padding-bottom: 0">Числитель</th>
				<th colspan="2"
					style="width: 43%; border-bottom: none; padding-bottom: 0">Знаменатель</th>
			</tr>
			<tr>
				<th
					style="width: 21%; border-top: none; border-right: none; height: 0"></th>
				<th
					style="width: 21%; border-top: none; border-left: none; height: 0"></th>
				<th
					style="width: 21%; border-top: none; border-right: none; height: 0"></th>
				<th
					style="width: 21%; border-top: none; border-left: none; height: 0"></th>
			</tr>
		</thead>
		<c:forEach items="${refsContainer.daysOfWeek}" var="day">
			<tbody class="weekday">
				<tr>
					<th rowspan="${twains.size()*2+2}"><small><fmt:formatDate
								value="${day}" pattern="eeee" /></small></th>
				</tr>
				<c:forEach items="${twains}" var="twain">
					<tr class="empty scheduleTr">
						<td><fmt:formatDate value="${twain.timeStart}"
								pattern="HH'<sup>'mm'</sup>'" /> - <fmt:formatDate
								value="${twain.timeEnd}" pattern="HH' <sup>'mm'</sup>'" /> <input
							value="${twain.idTwain}" class="twainInput" type="hidden" /></td>
						<td colspan="4" class="scheduleItem"
							ondrop="return dragDropTd(event)"></td>
					</tr>
				</c:forEach>
			</tbody>
		</c:forEach>
	</table>
</div>