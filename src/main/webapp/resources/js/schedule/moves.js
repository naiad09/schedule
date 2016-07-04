// обработчик на кнопке удаления
function processClickDelete(link) {
	link = $(link).parents(".schi")[0]
	removeSchi(link)
	normalizeAllTrs()
}

// вытаскивает или создает schi из события
function getSchiForEvent(ev) {
	var targetTd = $(ev.target).closest(".scheduleItem")[0]
	var targetTr = targetTd.parentElement
	var schi = null

	switch (ev.dataTransfer.getData('type')) {
	case "glt":
		var data = ev.dataTransfer.getData('GLTId')
		var glt = document.getElementById(data)
		schi = createSchi(glt)
		break
	case "schi":
		var data = ev.dataTransfer.getData('SCHId')
		schi = document.getElementById(data)
		break
	}
	if (schi.lab4 && targetTr.parentElement.lastElementChild == targetTr)
		return null
	return schi;
}
function dragStartGLT(ev) {
	ev.dataTransfer.effectAllowed = 'move'
	ev.dataTransfer.setData('GLTId', ev.target.getAttribute('id'))
	ev.dataTransfer.setData('type', "glt")
	return true
}
function dragStartSCHI(ev) {
	ev.dataTransfer.effectAllowed = 'copyMove'
	var schi = $(ev.target).closest(".schi")[0]
	ev.dataTransfer.setData('SCHId', schi.getAttribute('id'))
	ev.dataTransfer.setData('type', "schi")
	return true
}

function dragEnter(ev) {
	event.preventDefault()
	if (!$(ev.target).closest("td.scheduleItem")[0]) {
		ev.dataTransfer.dropEffect = "none"
		return false
	}
	if (ev.ctrlKey) ev.dataTransfer.dropEffect = "copy"
	else ev.dataTransfer.dropEffect = "move"
	return true
}

function dragDropVacancy(ev) {
	var vacancy = $(ev.target).closest(".vacancy")[0]
	var schi = getSchiForEvent(ev)
	if (schi) {
		if (ev.ctrlKey)
			schi = createSchi(schi.glt)
		else
			removeSchi(schi)
	
		schi.weekplan = vacancy.weekplan;
		schi.twain = vacancy.twain
	
		var tr = $(vacancy).parents("tbody.weekday").find("tr.scheduleTr").eq(schi.twain-1)[0]
		writeSchiToTr(schi, tr)
	}
	ev.stopPropagation()
	normalizeAllTrs()
	return false
}

function dragDropSchi(ev) {
	var schiOld = $(ev.target).closest(".schi")[0]
	var schiNew = getSchiForEvent(ev)
	if(schiNew) {
		var trOld = $(schiOld).closest("tr.scheduleTr")[0]
		var trNew = $(schiNew).closest("tr.scheduleTr")[0]
	
		removeSchi(schiNew)
		removeSchi(schiOld)
	
		var w = schiNew.weekplan
		schiNew.weekplan = schiOld.weekplan
		schiOld.weekplan = w
		
		var tw = schiNew.twain
		schiNew.twain = schiOld.twain
		schiOld.twain = tw
	
		if (ev.ctrlKey && trNew) {
			writeSchiToTr(schiOld, trNew)
		}
	
		writeSchiToTr(schiNew, trOld)

	}
	ev.stopPropagation()

	normalizeAllTrs()
	return false
}

function dragDropTd(ev) {
	var td = ev.target
	var schi = getSchiForEvent(ev)
	if(schi) {
		if (ev.ctrlKey)
			schi = createSchi(schi.glt)
		else
			removeSchi(schi)
	
		schi.weekplan = calcTdWeekplan(td)
	
		var tr = $(td).closest("tr.scheduleTr")[0]
		schi.twain = calcTwain(tr)
		writeSchiToTr(schi, tr)
	}

	ev.stopPropagation()
	normalizeAllTrs()
	return false
}

$(schedule).on("mousedown", ".leftMover.mover", function(e) {
    var schi = this.parentElement.parentElement.parentElement
    var startX = e.pageX
    var startWidth = schi.clientWidth
    var startMargin = parseInt($(schi).css("margin-left"))
    
    var rightBound = schi.parentElement.parentElement.firstElementChild.getBoundingClientRect().right
    moveAt(e)
    function moveAt(e) {
        var newWidth = startWidth - e.pageX + startX + 2
        if (e.pageX < rightBound + 9 || newWidth < 180) return
        schi.style.marginLeft = e.pageX - startX + startMargin + 'px'
        schi.style.width = newWidth + 'px'
    }
    document.onmousemove = function(e) {
        moveAt(e)
    }
    document.onmouseup = function() {
        document.onmousemove = null
        document.onmouseup = null

        var coef = schi.clientWidth / startWidth
        schi.style=""
        if (coef < 0.8) {
            breakTwaise(schi, true)
        } if (coef > 1.2) mergeTwaise(schi, true)
        normalizeAllTrs()
        updateDivider(schi)
    }
})

$(schedule).on("mousedown", ".rightMover.mover", function(e) {
    var schi = this.parentElement.parentElement.parentElement
    var startX = e.pageX
    var startWidth = schi.clientWidth
    var startMargin = parseInt($(schi).css("margin-right"))
    
    var leftBound = $(schi).parents(".scheduleTr")[0].getBoundingClientRect().right
    moveAt(e)
    function moveAt(e) {
        var newWidth = startWidth + e.pageX - startX + 2
        if (e.pageX > leftBound - 9 || newWidth < 180) return
        schi.style.marginRight = - e.pageX + startX + startMargin + 'px'
        schi.style.width = newWidth + 'px'
    }
    document.onmousemove = function(e) {
        moveAt(e)
    }
    document.onmouseup = function() {
        document.onmousemove = null
        document.onmouseup = null
        var coef = schi.clientWidth / startWidth
        schi.style=""
        if (coef < 0.8) {
            breakTwaise(schi, false)
        } if (coef > 1.2) mergeTwaise(schi, false)
        normalizeAllTrs()
        updateDivider(schi)
    }
})
// разбиение ячейки
function breakTwaise(schi, left) {
    if (schi.attributes["colspan"].value == 1) return
    var tr = $(schi).parents(".scheduleTr")[0]
    removeSchi(schi)
    schi.weekplan.breakTwise(left)
    writeSchiToTr(schi, tr)
}
// слияние ячеек
function mergeTwaise(schi, left) {
    if (schi.attributes["colspan"].value == 4) return
    var tr = $(schi).parents(".scheduleTr")[0]
    removeSchi(schi)
    schi.weekplan.mergeTwise(left)
    writeSchiToTr(schi, tr)
}

scheduleForm.onsubmit = function() {
	var configSubmit = {
		listHolder : $(scheduleDisciplines),
		listName : "scheduleDisciplines",
		rowClass : "glt"
	}
	processDynamicListForm(configSubmit)
	
	$(".glt").each(function(i) {
		var configSubmit = {
			listHolder : $(this),
			listName : "lecturers",
			rowClass : "lecturerInput"
		}
		processDynamicListForm(configSubmit)
		
		$(".schi." + this.id).each(function(){
			$(this).find("input").each(function() {
				this.name = this.name.replace(/scheduleDisciplines\[\d*\]/,
						"scheduleDisciplines[" + i + "]")
			})
			$(this).find("input[name*='weekday']").val($(this).parents("tbody.weekday")[0].index)
			$(this).find("input[name*='idTwain']").val(this.twain)
			$(this).find("input[name*='weekplan']").val(parseInt(this.weekplan.toString(), 2))
			var inputId = $(this).find("input[name*='idScheduleItem']")
			if (!inputId.val()) inputId.remove()
			
			var configSubmit = {
				listHolder : $(this),
				rowClass : "classroomInput",
				listName : "classrooms"
			}
			processDynamicListForm(configSubmit)
		})
		
		var configSubmit = {
			listHolder : $(schedule),
			rowClass : "schi." + this.id,
			listName : "scheduleItems"
		}
		processDynamicListForm(configSubmit)
	})
}

function calcTdWeekplan(td) {
	var tr = td.parentElement
	var weekplan = "0000"
	var j = -1
	for (var i = 0; tr.cells[i] != td; i++)
		// считаем пропуски в этой строке
		j += tr.cells[i].colSpan
		
	for (var i = j; i < td.colSpan + j; i++) {
		weekplan = weekplan.substr(0, i).concat("1").concat(weekplan.substring(i + 1))
	}
	return new Weekplan(weekplan + "" + weekplan)
}
