/**
 * Обрабатывает форму добавления преподавателя: таблицу с его кафедрами.
 * Добавляет, удаляет строки с кафедрами, а также подготавливает фрму к отправке
 * на сервер.
 */

// Убирает из chairSelector option-ы, которые уже выбраны в таблице
$("#chairs tr.chair input[type='hidden']").each(function() {
	$("#chairSelector option[value=" + $(this).val() + "]").hide()
})

// Удаляет строку при нажатии на кнопку Удалить, а также скрывает option
function deleteRow(anchor) {
	var row = $(anchor).parent().parent()
	var idChair = row.find("input[type='hidden']").attr("value")
	$("#chairSelector").find("option[value='" + idChair + "']").show()
	row.remove()
	if ($("#chairs tr").size() == 1)
		$("#chairs").hide();
}

// Обработчик нажатия на option в селекторе. Добавляет стрку в
// таблицу с выбранной кафедрой: названием, номером и селектором должности
$("#chairSelector option:not(:first-child)")
		.click(
				function() {
					if ($("#chairs tr").size() == 1)
						$("#chairs").show();
					$(this).hide()
					$("#chairSelector").val("")
					$("#chairs")
							.append(
									'<tr class="chair"><td><input value="'
											+ $(this).val()
											+ '" type="hidden">'
											+ $(this).text().trim()
											+ '</td><td><select required="required"><option value="">-- Выберите должность'
											+ ' --</option><option value="pr">профессор</option><option value="doc">'
											+ 'доцент</option><option value="stp">старший преподаватель</option></select>'
											+ '</td><td><a id="deleteChairLink" onclick="deleteRow(this)">'
											+ 'Удалить</a></td></tr>')
				})

// Подготавливает форму к отправке на сервер: проставляет name у hidden inputs
// what contains idChair
function prerareChairs() {
	$("#chairs tr.chair").each(function(i) {
		var j = "lecturerJobs[" + i + "]."
		$(this).find("input.[type='hidden']").attr("name", j + "chair.idChair")
		$(this).find("select").attr("name", j + "jobType")
	})
}