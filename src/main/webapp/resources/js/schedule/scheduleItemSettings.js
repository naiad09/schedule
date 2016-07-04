FormHider("dblclick", ".schi", "#scheduleItemSettingsForm",
		dropScheduleItemSettingsForm, showScheduleItemSettingsForm)

// отображает форму редактирования элемента расписания
function showScheduleItemSettingsForm(e) {
	// Порядок аудиторий
	$(classroomSelector).find(".yellow").prependTo(classroomSelector)
	$(classroomSelector).find(".green").prependTo(classroomSelector)
	$(classroomSelector).find(".blue").prependTo(classroomSelector)
	
	schi = $(e.target).closest(".schi")
	scheduleItemSettingsForm.editingSchi = schi
	
	searchConflictClassroomsViaAjax(schi[0])

	scheduleItemSettingsForm.classList.add(schi[0].classList[1])

	weekplan = schi[0].weekplan
	$(scheduleItemSettingsForm).find("." + weekplan.base).show()
	scheduleItemSettingsForm.elements["bc"].value = weekplan.bc
	scheduleItemSettingsForm.elements["ac"].value = weekplan.ac

	$(scheduleItemSettingsForm).show()
	var bold = $(schi[0].glt).parent().find("b").clone()
	$(scheduleItemSettingsForm).find(".discHolder h3").append(bold)

	scheduleItemSettingsForm.elements['weekday'].value = schi.parents(
			"tbody.weekday")[0].index
	scheduleItemSettingsForm.elements['twain'].value = schi[0].twain
	scheduleItemSettingsForm.elements['comment'].value = $(schi).find(
			"[name*='comment']").val()

	$(schi).find(".classrooms input").each(
			function(i) {
				$(scheduleItemSettingsForm).find(
						"select#classroomSelector option[value='" + this.value
								+ "']").click()
			})

	scrollTo(0, 4000)
	
	function searchConflictClassroomsViaAjax(schi) {
		var search = {
				"idScheduleItem" : $(schi).find("input[name*='idScheduleItem']").val(),
				"weekday" : $(schi).parents("tbody.weekday")[0].index,
				"weekplan" : parseInt(schi.weekplan.toString(), 2),
				"scheduleDiscipline.lessonType" : schi.glt.classList[1],
				"twain.idTwain" : schi.twain
			} 
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "edit/conflict-classrooms",
			data : search,
			dataType : "json",
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				$(classroomSelector.options).filter(valueInDataFilter)
					.addClass("red").appendTo(classroomSelector)
				$(".classroom .classroomInput").filter(valueInDataFilter).parent().addClass("conflict")
				
				function valueInDataFilter(){
					return data.includes(parseInt(this.value))
				}
			}
		})
	}
}

function dropScheduleItemSettingsForm() {
	if (!scheduleItemSettingsForm.editingSchi) return
	scheduleItemSettingsForm.classList
			.remove(scheduleItemSettingsForm.editingSchi[0].classList[1])
	scheduleItemSettingsForm.editingSchi = undefined
	$(scheduleItemSettingsForm).hide().find(".discHolder h3").empty()
	$(scheduleItemSettingsForm).find(".classroom .button").click()
	$(scheduleItemSettingsForm).find(".toggleVisiable").hide()
	$(classroomSelector.options).removeClass("red")
	$(".classroom").removeClass("conflict")
}

scheduleItemSettingsSaveButton.onclick = function() {
	var bc = scheduleItemSettingsForm.elements["bc"].value
	var ac = scheduleItemSettingsForm.elements["ac"].value
	if ((ac == "01" && bc == "10") || (ac == "10" && bc == "01") || (ac == "00" && bc == "00")) {
		alert("Некорректный понедельный план")
		return false
	}
	
	var schi = scheduleItemSettingsForm.editingSchi[0]

	var tr = $(scheduleItemSettingsForm.editingSchi).parents("tr.scheduleTr")[0];
	removeSchi(schi)
	schi.weekplan.bc = bc
	schi.weekplan.ac = ac
	schi.weekplan.normalize()
	writeSchiToTr(schi, tr)
	tr.needNormalize = true
	
	var classrooms = $(scheduleItemSettingsForm).find(".classroom input")
	scheduleItemSettingsForm.editingSchi.find(".classrooms").empty().append(
			classrooms.clone())

	scheduleItemSettingsForm.editingSchi.find("[name*='comment']").val(
			scheduleItemSettingsForm.elements['comment'].value)

	dropScheduleItemSettingsForm()
	normalizeAllTrs()

	updateWeekplanLabel(schi)
	updateDetails(schi)
}

scheduleItemSettingsResetButton.onclick = dropScheduleItemSettingsForm

DynamicList({
	holder : $("#classrooms"),
	rowClass : 'classroom',
	removeLink : $(".deleteClassroomLink"),
	selector : $("#classroomSelector"),
	defaultRowClass : "default",
	nameToCopy : "idClassroom",
	processCloning : function(newRow, option) {
		newRow.find("span").text(option.attr("title"))
		if (option.hasClass("red"))
			newRow.addClass("conflict")
	},
	minRows : 1
})

SelectorFindHelper({
	selector : $("#classroomSelector"),
	input : $("#classroomSelectorInput"),
	clearButton : $("#clearClassroomSelection"),
	maxHeightSelect : 100
})