FormHider("dblclick", $("#scheduleDisciplines>div"),
		"#scheduleDiscSettingsForm", dropScheduleDiscSettingsForm,
		showScheduleDiscSettingsForm)

function showScheduleDiscSettingsForm(e) {
	editingDisc = $(e.target).closest(".scheDisc")
	scheduleDiscSettingsForm.editingDisc = editingDisc
	$(scheduleDiscSettingsForm).show()
	var bold = editingDisc.find("b").clone()
	$(scheduleDiscSettingsForm).find(".discHolder h3").append(bold)
	editingDisc.find(".glt").each(function() {
		var tr = $(scheduleDiscSettingsForm).find("tr." + this.classList[1]);
		tr.show()
		$(this).find(".lecturers input").each(function(i) {
			$(tr).find("select option[value='" + this.value + "']").click()
		})
	})
	scrollTo(0, 4000)
}

function dropScheduleDiscSettingsForm() {
	scheduleDiscSettingsForm.editingDisc = undefined
	$(scheduleDiscSettingsForm).hide().find(".discHolder h3").empty()
	$(scheduleDiscSettingsForm).find("tr.toggleVisible").hide().find(
			".lecturer .button").click()
}
scheduleDiscSettingsSaveButton.onclick = function() {
	scheduleDiscSettingsForm.editingDisc.find(".glt").each(
			function() {
				var lecturers = $(scheduleDiscSettingsForm).find(
						"tr." + this.classList[1] + " .lecturer input")
				$(this).find(".lecturers").empty().append(lecturers.clone())

				var glt = this
				$(".schi." + this.id).each(function() {
					updateDetails(this)
				})
			})
	dropScheduleDiscSettingsForm()
}

scheduleDiscSettingsResetButton.onclick = dropScheduleDiscSettingsForm