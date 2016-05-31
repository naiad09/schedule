function DynamicList(c) {

	// Убирает из селектора option-ы, которые уже выбраны в таблице
	c.holder.find("." + c.rowClass + " input:hidden").each(
			function() {
				c.selector.find("option[value=" + $(this).val() + "]").attr(
						"disabled", "true")
			})

	// Нажатие на кнопку удалить
	if (c.removeLink != null)
		c.removeLink.click(function() {
			removeRow($(this).parent().parent())
		})

		// Удаляет строку при нажатии на кнопку Удалить, а также скрывает option
	function removeRow(row) {
		var id = row.find("input:hidden").attr("value")
		c.selector.find("option[value='" + id + "']").removeAttr("disabled")
		row.remove()
		if (c.holder.find("." + c.rowClass).size() == c.minRows)
			c.processDropAll()
	}

	function cloneRow() {
		if (c.holder.find("." + c.rowClass).size() == 0)
			c.holder.show();
		var defaultRow = c.holder.find("." + c.defaultRowClass);
		var row = defaultRow.clone(true).removeClass(c.defaultRowClass)
				.addClass(c.rowClass).insertBefore(defaultRow)
		return row
	}

	// Обработчик нажатия на option в селекторе. Добавляет стрку в таблицу
	c.selector.find("option").click(function() {
		$(this).attr("disabled", "true")
		c.selector.val("")
		var row = cloneRow()
		c.processCloning(row, $(this))
		row.find("input:hidden").val($(this).val())
	})

}

function FormDynamicListSubmitProcessor(c) {
	c.form.submit(function() {
		c.listHolder.find("." + c.rowClass).each(
				function(i) {
					$(this).find("input,select").each(
							function() {
								$(this).attr(
										"name",
										$(this).attr("name").replace(
												new RegExp(RegExp
														.escape(c.listName)
														+ "\\[\\d*\\]"),
												c.listName + "[" + i + "]"))
							})
				})

		c.listHolder.find("." + c.defaultRowClass).remove()

	})
}