$("#scheduleDisciplines>div").each(
		function() {
			FormHider("dblclick", $(this), "#scheduleDiscSettingsDiv",
					dropScheduleDiscSettingsForm, showScheduleDiscSettingsForm)

			function showScheduleDiscSettingsForm(e) {
				editingDisc = $(e.target).closest(".scheDisc")
				scheduleDiscSettingsDiv.editingDisc = editingDisc
				$(scheduleDiscSettingsDiv).show()
				var bold = editingDisc.find("b").clone()
				$(scheduleDiscSettingsDiv).find(".discHolder h3").append(bold)
				editingDisc.find(".glt").each(
						function() {
							var tr = $(scheduleDiscSettingsDiv).find(
									"tr." + this.classList[1]);
							tr.show()
							$(this).find(".lecturers input").each(
									function(i) {
										$(tr).find(
												"select option[value='"
														+ this.value + "']")
												.click()
									})
						})
				scrollTo(0, 4000)
			}
		})

function dropScheduleDiscSettingsForm() {
	scheduleDiscSettingsDiv.editingDisc = undefined
	$(scheduleDiscSettingsDiv).hide().find(".discHolder h3").empty()
	$(scheduleDiscSettingsDiv).find("tr.toggleVisible").hide().find(
			".lecturer .button").click()
}
scheduleDiscSettingsSaveButton.onclick = function() {
	scheduleDiscSettingsDiv.editingDisc.find(".glt").each(
			function() {
				var lecturers = $(scheduleDiscSettingsDiv).find(
						"tr." + this.classList[1] + " .lecturer input")
				$(this).find(".lecturers").empty().append(lecturers.clone())

				// обновить schi
			})
	dropScheduleDiscSettingsForm()
}

scheduleDiscSettingsResetButton.onclick = dropScheduleDiscSettingsForm