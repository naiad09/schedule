FormHider("dblclick", $(".schi"), "#scheduleItemSettingsForm",
		dropScheduleItemSettingsForm, showScheduleItemSettingsForm)

// вычисляет базу понедельного плана
function calcBase(weekplan) {
	var dominAfterChange = calcHalfOfWeekplan(weekplan)
	var domainHalf = weekplan.substr(dominAfterChange ? 4 : 0, 4);
	var nondomainHalf = weekplan.substr(!dominAfterChange ? 4 : 0, 4);
	if (domainHalf == "1111") return "every"
	var domainDenominator = calcHalfOfWeekplan(domainHalf)
	return domainDenominator ? "den" : "num";
	
	// вычисляет, какой половине принадлежит.
	function calcHalfOfWeekplan(weekplan) {
		var a = 0
		var b = 0
		for (var i = 0; i < weekplan.length / 2; i++)
			if (weekplan[i] == "1")
				a++
		for (var i = weekplan.length / 2; i < weekplan.length; i++)
			if (weekplan[i] == "1")
				b++
		return b > a
	}
}

// вычисляет части понедельного плана до и после смены расписания
function calcBCAC(weekplanHalf, base) {
	switch (base) {
	case "every": 
		return weekplanHalf.substr(1,2)
	case "num":
		return weekplanHalf.substr(0,2)
	case "den":
		return weekplanHalf.substr(2,2)
	}
}

// отображает форму редактирования элемента расписания
function showScheduleItemSettingsForm(e) {
	schi = $(e.target).closest(".schi")
	scheduleItemSettingsForm.editingSchi = schi

	scheduleItemSettingsForm.classList.add(schi[0].classList[1])

	weekplan.base = calcBase(schi[0].weekplan)
	$(scheduleItemSettingsForm).find("." + weekplan.base).show()
	scheduleItemSettingsForm.elements["bc"].value = calcBCAC(schi[0].weekplan.substr(0,4),weekplan.base)
	scheduleItemSettingsForm.elements["ac"].value = calcBCAC(schi[0].weekplan.substr(4,4),weekplan.base)

	$(scheduleItemSettingsForm).show()
	var bold = $(schi[0].glt).parent().find("b").clone()
	$(scheduleItemSettingsForm).find(".discHolder h3").append(bold)

	scheduleItemSettingsForm.elements['weekday'].value = $(schi).parents(
			"tbody.weekday")[0].index
	scheduleItemSettingsForm.elements['twain'].value = $(schi).parents(
			"tr.scheduleTr").find("input.twainInput").val()
	scheduleItemSettingsForm.elements['comment'].value = $(schi).find(
			"[name*='comment']").val()

	$(schi).find(".classrooms input").each(
			function(i) {
				$(scheduleItemSettingsForm).find(
						"select#classroomSelector option[value='" + this.value
								+ "']").click()
			})

	scrollTo(0, 4000)
}

function dropScheduleItemSettingsForm() {
	weekplan.base = undefined
	if (!scheduleItemSettingsForm.editingSchi) return
	scheduleItemSettingsForm.classList
			.remove(scheduleItemSettingsForm.editingSchi[0].classList[1])
	scheduleItemSettingsForm.editingSchi = undefined
	$(scheduleItemSettingsForm).hide().find(".discHolder h3").empty()
	$(scheduleItemSettingsForm).find(".classroom .button").click()
	$(scheduleItemSettingsForm).find(".toggleVisiable").hide()
}

scheduleItemSettingsSaveButton.onclick = function() {
	var bc = scheduleItemSettingsForm.elements["bc"].value
	var ac = scheduleItemSettingsForm.elements["ac"].value
	if ((ac == "01" && bc == "10") || (ac == "10" && bc == "01") || (ac == "00" && bc == "00")) {
		alert("Ай-ай-ай!!!")
		return false
	}
	switch (weekplan.base) {
	case "every": 
		bc = bc[0] + bc + bc[1]
		ac = ac[0] + ac + ac[1]
		break
	case "num":
		bc = bc + "00"
		ac = ac + "00"
		break
	case "den":
		bc = "00" + bc
		ac = "00" + ac
		break
	}
	scheduleItemSettingsForm.editingSchi[0].weekplan = (bc + "" + ac)
	
	var classrooms = $(scheduleItemSettingsForm).find(".classroom input")
	scheduleItemSettingsForm.editingSchi.find(".classrooms").empty().append(
			classrooms.clone())

	scheduleItemSettingsForm.editingSchi.find("[name*='comment']").val(
			scheduleItemSettingsForm.elements['comment'].value)

	updateDetails(scheduleItemSettingsForm.editingSchi[0])

	dropScheduleItemSettingsForm()
}

scheduleItemSettingsResetButton.onclick = dropScheduleItemSettingsForm