function DynamicList(c) {

	// Убирает из селектора option-ы, которые уже выбраны в таблице
	c.holder.find(
			"." + c.rowClass + " input:hidden[name*='" + c.nameToCopy + "']")
			.each(
					function() {
						c.selector.find("option[value=" + $(this).val() + "]")
								.attr("disabled", "true")
					})

	// Нажатие на кнопку удалить
	if (c.removeLink != null)
		c.removeLink.click(function() {
			removeRow($(this).parents("." + c.rowClass))
		})

		// Удаляет строку при нажатии на кнопку Удалить, а также скрывает option
	function removeRow(row) {
		var id = row.find("input:hidden[name*='" + c.nameToCopy + "']").attr(
				"value")
		c.selector.find("option[value='" + id + "']").removeAttr("disabled")
		row.remove()
		if (c.holder.find("." + c.rowClass).size() == c.minRows)
			if (c.processDropAll)
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
		row.find("input:hidden[name*='" + c.nameToCopy + "']").val(this.value)
	})

}

// Подготавливаем динамический лист к отправке, устанавливая нужные name у
// элементов формы. c - конфигурация. c.listHolder - блок, где искать элементы
// формы (ГДЕ искать). c.rowClass - класс элемента (ЧТО искать). c.listName -
// имя списка, рядом с которым надо поставить номер по порядку (что МЕНЯТЬ).
// c.defaultRowClass - класс элемента по умолчанию, который надо почистить перед
// отправкой (необязательный параметр)
function processDynamicListForm(c) {
	c.listHolder.find("." + c.rowClass).each(
			function(i) {
				var set = $(this)
				if (!set.is("input,select"))
					set = set.find("input,select")
				set.each(function() {
					this.name = this.name.replace(new RegExp(RegExp
							.escape(c.listName)
							+ "\\[\\d*\\]"), c.listName + "[" + i + "]")
				})
			})

	c.listHolder.find("." + c.defaultRowClass).remove()
}