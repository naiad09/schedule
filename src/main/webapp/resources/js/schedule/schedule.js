// Управляет интерфейсом расписания. Сокрыщения:
// schi - schedule item, элемент расписания
// glt - group lesson type
// Каждая tr содержит два массива[4] ссылок на schi

var schiId = 0 // счетчик schi, чтобы каждому присвоить уникальный id

// Расстановка номеров
$("#schedule>tbody.weekday").each(function(i) {
	this.index = i
	$(this).find("tr.empty").each(function(j) {
		this.schiBefore = [ null, null, null, null ]
		this.schiAfter = [ null, null, null, null ]
	})
})

// парсинг scheduleInfo
$(scheduleItemsInfo).find(".schiInfo").each(function(){
	var schiInfo = this.innerText.split(/\|\n?\s*/, 6)
	var schi = createSchi($("#glt" + schiInfo[2])[0])
	var tr = $("tbody.weekday").eq(schiInfo[3])
		.find("input.twainInput[value='" + schiInfo[1] + "']").parents("tr.scheduleTr")[0]
	$(schi).find("input[name*='idScheduleItem']").val(schiInfo[0])
	if (schiInfo[5]) $(schi).find("input[name*='comment']").val(schiInfo[5].trim())
	
	$(schi).find(".classrooms").append($(this).find(".classroomInput"))
	schi.weekplan = ("00000000" + (parseInt(schiInfo[4])).toString(2))
	schi.weekplan = schi.weekplan.substr(schi.weekplan.length - 8, 8)
	schi.weekplan = new Weekplan(schi.weekplan)

	writeSchiToTr(schi, tr)
	updateWeekplanLabel(schi)
})
function normalizeAllTrs(){
	$("tr.scheduleTr").each(function(){
		if (this.needNormalize) {
			this.needNormalize = undefined
			normalizeTr(this)
		}
	})
	// нормализует tr, то есть составляет правильные ячейки в соответствии с
	// tr.schi[]
	function normalizeTr(tr) {
		var flagLab4Prev = false // нужен для выставления класса
		var flagLab4Has = false // нужен нормалайза следующей строки, если лаба4
		var length = tr.cells.length;
		for (var i = 1; i< length;i++) { // удаляем все старые ячейки
			var td = tr.lastElementChild;
			if (td.firstElementChild) td.removeChild(td.firstElementChild)
			tr.removeChild(td)
		}
		var merge = 1 // определяет, сколько schi или null идут подряд
		var schi
		for (var i = 0;i<tr.schiBefore.length;i++) {
			var array = tr.schiBefore
			schi = array[i]
			if (!schi) {
				array = tr.schiAfter
				schi = array[i]
			}
			
			// +1, чтобы последняя merge тоже создавалась
			if (schi === array[i+1]) merge++ 
			// если совпадает, инкрементируем
			// tr.schi[4]=undefined, поэтому последняя ячейка создается
			else {// иначе создаем новую ячейку с результатом
				var td = newTd(merge)
				merge = 1
				tr.appendChild(td)// и добавляем
				if (schi) {if (schi.lab4) {// однако если это lab4
					if (schi.parentElement) {
						// и если schi уже добавлена куда-то, то есть строкой
						// выше
						$(td).hide()
						// скрываем ячейку, чтобы потом при добавлении можно
						// было их пересчитать
						flagLab4Prev = true
					}
					else {
						td.rowSpan = 2// ианче ставим rowspan
						appendSchiToTd(schi, td)
					}
					// а если обычная то просто добавляем
				} else appendSchiToTd(schi, td)}
			}
		}
		
	    if (tr.getElementsByClassName("schi").length == 0 && !flagLab4Prev) 
	        tr.classList.add("empty")// и определяем класс
	    else  tr.classList.remove("empty")
	}
	
	function appendSchiToTd(schi, td) {
		var invert = schi.weekplan.invert()
		td.appendChild(schi)
		if (invert) {
			var vacancyClone = vacancyTemplate.cloneNode(true)
			vacancyClone.weekplan = invert
			vacancyClone.id = ""
			if (invert.dominAfterHalf()) td.appendChild(vacancyClone)
			else td.insertBefore(vacancyClone, schi)
			updateWeekplanLabel(vacancyClone)
		}
	}
}
normalizeAllTrs()
$(".schi").each(function(){updateDetails(this)})

function writeSchiToTr(schi, tr, yet) {
	var weekplan = schi.weekplan.toString();
	for (var i = 0; i < 4; i++) if (weekplan[i] == "1") {
		if (tr.schiBefore[i]) removeSchi(tr.schiBefore[i])
		tr.schiBefore[i] = schi
	}
	for (var i = 4; i < 8; i++) if (weekplan[i] == "1") {
		if (tr.schiAfter[i-4]) removeSchi(tr.schiAfter[i-4])
		tr.schiAfter[i-4] = schi
	}
	tr.needNormalize = true
	if (schi.lab4 && !yet) writeSchiToTr(schi, tr.nextElementSibling, true)
}

// удаляет schi из массивов schiBefore и schiAfter
function removeSchiFromTr(schi, tr, yet) {
	var weekplan = schi.weekplan.toString();
	for (var i = 0; i < 4; i++) if (weekplan[i] == "1") {
		tr.schiBefore[i] = null
	}
	for (var i = 4; i < 8; i++) if (weekplan[i] == "1") {
		tr.schiAfter[i-4] = null
	}
	tr.needNormalize = true
	if (schi.lab4 && !yet) removeSchiFromTr(schi, tr.nextElementSibling, true)
}

// удалить schi
function removeSchi(schi) {
    var targetTd = schi.parentElement
    if (!targetTd) return
    // удалить из ячейки
    targetTd.removeChild(schi)
    var trOld = targetTd.parentElement
    // и из массива в tr
	if (trOld) {
		removeSchiFromTr(schi, trOld)
		if (schi.lab4)
			removeSchiFromTr(schi, trOld.nextElementSibling)
	}
    
}

// добавляет shci в tr.schi, удаляет конфликты
function addSchi(targetTd, schi) {
	var tr = targetTd.parentElement
	
	var weekplan = "0000"
	var j = -1
	for (var i = 0; tr.cells[i] != targetTd; i++)
		// считаем пропуски в этой строке
		j += tr.cells[i].colSpan
		
	for (var i = j; i < targetTd.colSpan + j; i++) {
		weekplan = weekplan.substr(0, i).concat("1").concat(weekplan.substring(i + 1))
	}
	
	removeSchi(schi)
	
	schi.weekplan = new Weekplan(weekplan + "" + weekplan) // magic
	
	writeSchiToTr(schi, tr)
		
	updateDivider(schi)
}

// создает schi по glt
function createSchi(glt) {
    var schi = schiTemplate.cloneNode(true)// клонирует шаблон
    schi.id = "schi" + schiId++ // добавляет id
    schi.glt = glt// сохраняет ссылку

    var discipline = $(schi).find(".discipline")

    schi.classList.add(glt.classList[1])
    schi.classList.add(glt.id)
    discipline.append($(glt).find("input[name*='idScheduleDiscipline']")
		.clone()
		.attr("name","scheduleDisciplines[].scheduleItems[].scheduleDiscipline.idScheduleDiscipline")
    )
    discipline.append($(glt).find(".gltType").clone())
    discipline.append(" ")
    discipline.append($(glt).parent().find("b").clone())
    if(schi.classList.contains("lab4")) schi.lab4 = true
    updateDetails(schi)
    
    return schi
}

function updateDetails(schi) {
	var details2 = $(schi).find(".details span.classrooms")
	var clrooms = details2.find("input")
	details2.empty().append(clrooms)
	clrooms.each(function(i){
		var cl = $(classroomSelector).find("option[value='"+this.value+"']").attr("title")
		details2.append(cl+((i<clrooms.size()-1)?", ":""))
	})
	
	var details0 = $(schi).find(".details span.lecturers").empty()
	var lects = $(schi.glt).find(".lecturers input")
	lects.each(function(i){
		var cl = $(lecLecturerSelector).find("option[value='"+this.value+"']").attr("title")
		details0.append(cl+((i<lects.size()-1)?", ":""))
	})
	
	updateDivider(schi)
}
function updateDivider(schi) {
	var details2 = $(schi).find(".details span.classrooms")
	var details0 = $(schi).find(".details span.lecturers")
	
	var divider = $(schi).find(".details span.divider");
	var width = $(schi).find("b").width() + 20
	if(details2.width() && details0.width()) {
		width-=details0.width()
		width-=details2.width()
		divider.width(width)
		divider.css("display","inline-block")
	} else {
		divider.css("display","none")
	}
}

// обновляет название weekplan
function updateWeekplanLabel(schi) {
	var w = schi.weekplan
	var span = $(schi).find(".weekplan")
	span.text("")
	
	if (w.bc != w.ac) {
		if (w.ac == "00") span.text("до смены расписания")
		if (w.bc == "00") span.text("после смены расписания")
	}
	if(span.text()) span.text("(" + span.text() + ")")
}

// обработчик на кнопке удаления
function processClickDelete(link) {
	link = $(link).parents(".schi")[0]
    removeSchi(link)
	normalizeAllTrs()
}

// обработчик перемещения в таблицу
function dragDrop(ev) {
    var targetTd = $(ev.target).closest(".scheduleItem")[0]        
    var schi = getSchiForEvent(ev)
    if (targetTd == schi.parentElement) return
    addSchi(targetTd, schi)
    ev.stopPropagation()
    normalizeAllTrs()
    updateDivider(schi)
    updateWeekplanLabel(schi)
    return false
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
    ev.dataTransfer.effectAllowed = 'move'
    var schi = $(ev.target).closest(".schi")[0]
	ev.dataTransfer.setData('SCHId', schi.getAttribute('id'))
    ev.dataTransfer.setData('type', "schi")
    return true
}


function dragEnter(ev) {
    event.preventDefault()
// info.innerHTML = ev.target.classList
    if ($(ev.target).closest(".scheduleItem")[0]) return true;
// ev.dataTransfer.dropEffect = "none"
// return false
}
document.ondragend = function(){
	schedule.classList.remove('dragovering')
}

function dragDropVacancy(ev) {
	var vacancy = $(ev.target).closest(".vacancy")[0]
	var schi = getSchiForEvent(ev);
	schi.weekplan = vacancy.weekplan;
	
	var tr = $(vacancy).closest("tr.scheduleTr")[0]
	writeSchiToTr(schi, tr)
	
    ev.stopPropagation()
	
	normalizeAllTrs()
    return false
}

$(".leftMover.mover").live("mousedown", function(e) {
    var schi = this.parentElement.parentElement.parentElement
    var startX = e.pageX
    var startWidth = schi.clientWidth
    
    var rightBound = schi.parentElement.parentElement.firstElementChild.getBoundingClientRect().right
    moveAt(e)
    function moveAt(e) {
        var newWidth = startWidth - e.pageX + startX + 2
        if (e.pageX < rightBound + 9 || newWidth < 180) return
        schi.style.marginLeft = e.pageX - startX + 'px'
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
    }
})

$(".rightMover.mover").live("mousedown", function(e) {
    var schi = this.parentElement.parentElement.parentElement
    var startX = e.pageX
    var startWidth = schi.clientWidth
    
    var leftBound = $(schi).parents(".scheduleTr")[0].getBoundingClientRect().right
    moveAt(e)
    function moveAt(e) {
        var newWidth = startWidth + e.pageX - startX + 2
        if (e.pageX > leftBound - 9 || newWidth < 180) return
        schi.style.marginRight = - e.pageX + startX + 'px'
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
    }
})
// разбиение ячейки
function breakTwaise(schi, left) {
    var td = schi.parentElement
    if (td.colSpan == 1) return
    var tr = td.parentElement
    removeSchi(schi)
    schi.weekplan.breakTwise(left)
    writeSchiToTr(schi, tr)
}
// слияние ячеек
function mergeTwaise(schi, left) {
    var td = schi.parentElement
    if (td.colSpan == 4) return
    var tr = td.parentElement
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
			$(this).find("input[name*='idTwain']").val(
					$(this).parents("tr.scheduleTr").find("input.twainInput").val()
				)
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

// создает новую ячейку td
function newTd(colSpan) {
	var td = document.createElement("td")
	td.colSpan = colSpan
	td.classList.add("scheduleItem")
	return td
} 