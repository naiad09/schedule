// DEPRECATED

function settings(schi) {
	settingsResetButton.click()
	location.hash = "#"
	location.hash = "#settings"
	
    while (!schi.classList.contains("schi")) {
        schi = schi.parentNode
        if (schi == document) return
    }
    
    settingsFieldset.disabled = false

    var disc = schi.getElementsByClassName("discipline")[0]
    $(discHolder).find("span").hide()
    $(discHolder).find("h3").append($(disc).clone())
    // Добавляем дисциплину

    $(settingsFieldset).find("[name='dayOfWeek']").val($(schi).parents("tbody").get(0).index)
    // День недели
    var twain = $(schi).parents("tr").find("input[name='idTwain']").val()
    $(settingsFieldset)    // и пару
    	.find("select[name='idTwain']").val(twain)
    	
    
    $(schi).find(".details span.classrooms input").each(function(i){
		$(classroomSelector).find("option[value='"+this.value+"']").click()
	})
	
	$(schi.glt).find(".lecturers input").each(function(i){
		$(lecturerSelector).find("option[value='"+this.value+"']").click()
	})
    	
    scheduleSettingsForm.elements['comment'].value = $(schi).find("[name='comment']").val()

	settingsSaveButton.onclick = function() {
	    var classroom = $(classrooms).find(".classroom")
	    var lecturer = $(lecturers).find(".lecturer")
	    var details = $(schi).find(".details span")
	    details.empty()
	    lecturer.each(function(i){
	    	$(schi.glt).find(".lecturers").empty().append($(this).find("input").clone()[0])
	    })
	    classroom.each(function(i){
	    	details[2].appendChild($(this).find("input").clone()[0])
	    })
	    updateDetails(schi)
	    
	    $(schi).find("[name='comment']").val(scheduleSettingsForm.elements['comment'].value)
	    
	    settingsResetButton.click()
	}

    settingsResetButton.onclick = function onReset() {
    	settingsFieldset.disabled = true
    	$(scheduleSettingsForm)
    		.find(".classroom .deleteClassroomLink, .lecturer .deleteLecturerLink").click()
    	$(discHolder).find("h3").empty()
    	$(discHolder).find("span").show()
    	scheduleSettingsForm.reset()
    	
    	settingsSaveButton.onclick = undefined
    }
}