FormHider("dblclick", $(".schi"), "#scheduleItemSettingsForm",
		dropScheduleItemSettingsForm, showScheduleItemSettingsForm)

function showScheduleItemSettingsForm(e) {
	editingSchi = $(e.target).closest(".schi")
	scheduleItemSettingsForm.editingSchi = editingSchi

	scheduleItemSettingsForm.classList.add(editingSchi[0].classList[1])

	$(scheduleItemSettingsForm).show()
	var bold = $(editingSchi[0].glt).parent().find("b").clone()
	$(scheduleItemSettingsForm).find(".discHolder h3").append(bold)

	scheduleItemSettingsForm.elements['weekday'].value = $(editingSchi)
			.parents("tbody.weekday")[0].index
	scheduleItemSettingsForm.elements['twain'].value = $(editingSchi).parents(
			"tr.scheduleTr").find("input.twainInput").val()
	scheduleItemSettingsForm.elements['comment'].value = $(editingSchi).find(
			"[name*='comment']").val()

	$(editingSchi).find(".classrooms input").each(
			function(i) {
				$(scheduleItemSettingsForm).find(
						"select#classroomSelector option[value='" + this.value
								+ "']").click()
			})

	scrollTo(0, 4000)
}

function dropScheduleItemSettingsForm() {
	scheduleItemSettingsForm.editingSchi = undefined
	scheduleItemSettingsForm.classList.remove(editingSchi[0].classList[1])
	$(scheduleItemSettingsForm).hide().find(".discHolder h3").empty()
	$(scheduleItemSettingsForm).find(".classroom .button").click()
}
scheduleItemSettingsSaveButton.onclick = function() {
	var classrooms = $(scheduleItemSettingsForm).find(".classroom input")
	$(scheduleItemSettingsForm.editingSchi).find(".classrooms").empty().append(
			classrooms.clone())

	$(scheduleItemSettingsForm.editingSchi).find("[name*='comment']").val(
			scheduleItemSettingsForm.elements['comment'].value)

	updateDetails(scheduleItemSettingsForm.editingSchi[0])

	dropScheduleItemSettingsForm()
}

scheduleItemSettingsResetButton.onclick = dropScheduleItemSettingsForm