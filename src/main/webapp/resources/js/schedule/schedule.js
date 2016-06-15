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
var trsToNormalize = []
$(scheduleItemsInfo).find(".schiInfo").each(function(){
	var schiInfo = this.innerText.split(/\|\n?\s*/, 6)
	var schi = createSchi($("#glt" + schiInfo[2])[0])
	var tr = $("tbody.weekday").eq(schiInfo[3])
		.find("input.twainInput[value='" + schiInfo[1] + "']").parents("tr.scheduleTr")[0]
	trsToNormalize[trsToNormalize.length] = tr
	$(schi).find("input[name*='idScheduleItem']").val(schiInfo[0])
	if (schiInfo[5]) $(schi).find("input[name*='comment']").val(schiInfo[5].trim())
	
	$(schi).find(".classrooms").append($(this).find(".classroomInput"))
	schi.weekplan = ("00000000" + (parseInt(schiInfo[4])).toString(2))
	schi.weekplan = schi.weekplan.substr(schi.weekplan.length - 8, 8)

	writeSchiToTr(schi, tr)
	if (schi.lab4) writeSchiToTr(schi, tr.nextElementSubling)
})
for (var i = 0; i < trsToNormalize.length; i++) {
	normalizeTr(trsToNormalize[i])
}
$(".schi").each(function(){updateDetails(this)})
function writeSchiToTr(schi, tr) {
	for (var i = 0; i < 4; i++) if (schi.weekplan[i] == "1") {
		if (tr.schiBefore[i]) removeSchi(tr.schiBefore[i])
		tr.schiBefore[i] = schi
	}
	for (var i = 4; i < 8; i++) if (schi.weekplan[i] == "1") {
		if (tr.schiAfter[i-4]) removeSchi(tr.schiAfter[i-4])
		tr.schiAfter[i-4] = schi
	}
}

//
function removeSchiFromTr(schi, tr) {
	for (var i = 0; i < 4; i++) if (schi.weekplan[i] == "1") {
		tr.schiBefore[i] = null
	}
	for (var i = 4; i < 8; i++) if (schi.weekplan[i] == "1") {
		tr.schiAfter[i-4] = null
	}
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
			removeSchiFromTr(schi, trOld.nextElementSubling)
	}
}
function newTd(colSpan) {// создает новую ячейку td
	var td = document.createElement("td")
	td.colSpan = colSpan
	td.classList.add("scheduleItem")
	return td
}
// нормализует tr, то есть составляет правильные ячейки в соответствии с
// tr.schi[]
function normalizeTr(tr) {
	var flagLab4Prev = false // нужен для выставления класса
	var length = tr.cells.length;
	for (var i = 1; i< length;i++) { // удаляем все старые ячейки
		var td = tr.lastElementChild;
		if(td.firstElementChild)td.removeChild(td.firstElementChild)
		tr.removeChild(td)
	}
	var merge = 0 // определяет, сколько schi или null идут подряд
	var schi = tr.schiBefore[0]
	for (var i = 0;i<tr.schiBefore.length+1;i++) {// +1, чтобы последняя merge
													// тоже
											// создавалась
		if (schi === tr.schiBefore[i]) merge++ // если совпадает,
												// инкрементируем
		// tr.schi[4]=undefined, поэтому последняя ячейка создается
		else {// иначе создаем новую ячейку с результатом
			var td = newTd(merge)
			merge = 1
			tr.appendChild(td)// и добавляем
			if (schi) {if(schi.lab4) {// однако если это lab4
				if (schi.parentElement) {// и если schi уже добавлена
											// куда-то, то есть строкой выше
					$(td).hide()// скрываем ячейку, чтобы потом при добавлении
								// можно было их пересчитать
					flagLab4Prev = true
				}
				else {
					td.rowSpan = 2// ианче ставим rowspan
					td.appendChild(schi)
				}
			} else td.appendChild(schi)}// а если обычная то просто добавляем
		}
		schi = tr.schiBefore[i]
	}
	
    if (tr.getElementsByClassName("schi").length == 0 && !flagLab4Prev) 
        tr.classList.add("empty")// и определяем класс
    else  tr.classList.remove("empty")
}
// удаляет с предыдущего места
function flushSchi(schi, withoutTr) {
	var tdOld = schi.parentElement
	if (!tdOld) return;
	var oldTr = tdOld.parentElement;
	removeSchi(schi)
	if (oldTr != tr){// и если это было в другой строке, нормализовать ее
		normalizeTr(oldTr)
		if( schi.lab4 && tr != oldTr.nextElementSibling)
			normalizeTr(oldTr.nextElementSibling)
		}
}
// добавляет shci в tr.schi, удаляет конфликты
function addSchi(targetTd, schi) {
	var tr = targetTd.parentElement
	flushSchi(schi, tr)
	
	var j = -1// из-за ячейки с парой
	for(var i = 0; tr.cells[i] != targetTd; i++)
		// считаем пропуски в этой строке
		j += tr.cells[i].colSpan
    		
    var nextTr = tr.nextElementSibling
		
	for(var i = j; i < targetTd.colSpan + j; i++) {
		var prew = tr.schiBefore[i]
		if (prew && prew != schi) {// если конфликт, удаляем его
			removeSchi(prew)
			if (prew.lab4)
				normalizeTr(tr.nextElementSibling)
		}
		tr.schiBefore[i] = schi// записываем schi
		if(schi.lab4) { // если lab4, то запиываем и в
												// следующую строку
			prew = nextTr.schiBefore[i]
			if (prew && prew != schi) {
				removeSchi(prew)// и удаляем конфликты
				if (prew.lab4)
					normalizeTr(tr.nextElementSibling.nextElementSibling)
			}
			nextTr.schiBefore[i] = schi
		}
	}
	
	normalizeTr(tr)// и нормализуем
	if (schi.lab4)
		normalizeTr(nextTr)
		
	updateDivider(schi)
}

// создает schi по glt
function createSchi(glt) {
    var schi = schiTemplate.cloneNode(true)// клонирует шаблон
    schi.id = "schi" + schiId++// добавляет id
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



// обработчик на кнопке удаления
function processClickDelete(link) {
	link = $(link).parents(".schi")[0]
    var tr = $(link).parents("tr.scheduleTr")[0]
    removeSchi(link)

    normalizeTr(tr)
    if(link.lab4)
        normalizeTr(tr.nextElementSibling)
}

// обработчик перемещения в таблицу
function dragDrop(ev) {
    var targetTd = ev.target
    while (!targetTd.classList.contains("scheduleItem"))
        targetTd = targetTd.parentElement
        
    var targetTr = targetTd.parentElement
    var schi = null
    
    switch (ev.dataTransfer.getData('type')) {
    case "glt":
        var data = ev.dataTransfer.getData('GLTId')
        var glt = document.getElementById(data)
        schi = createSchi(glt)
        if (schi.lab4 
        		&& targetTr.parentElement.lastElementChild == targetTr)
        	break;
        addSchi(targetTd, schi)
        break
    case "schi":
        var data = ev.dataTransfer.getData('SCHId')
        schi = document.getElementById(data)
        if (schi.lab4 
        		&& targetTr.parentElement.lastElementChild == targetTr)
        	break;
        var oldTr = schi.parentElement.parentElement
        addSchi(targetTd, schi)
        break
    }
    ev.stopPropagation()
    return false
}
function dragStartGLT(ev) {
    ev.dataTransfer.effectAllowed = 'move'
    ev.dataTransfer.setData('GLTId', ev.target.getAttribute('id'))
    ev.dataTransfer.setData('type', "glt")
    return true
}
function dragStartSCHI(ev) {
    ev.dataTransfer.effectAllowed = 'move'
    var schi = $(ev.target).parents(".schi")[0]
	ev.dataTransfer.setData('SCHId', schi.getAttribute('id'))
    ev.dataTransfer.setData('type', "schi")
    return true
}
function dragEnter(ev) {
    event.preventDefault()
    if ($(ev.target).closest(".scheduleItem")[0]) return true;
    ev.dataTransfer.dropEffect = "none"
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
        if (e.pageX < rightBound + 9 || newWidth < 100) return
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
            breakTwaise(schi, true, coef)
        } if (coef > 1.2) mergeTwaise(schi, true, coef)
    }
})
$(".rightMover.mover").live("mousedown", function(e) {
    var schi = this.parentElement.parentElement.parentElement
    var startX = e.pageX
    var startWidth = schi.clientWidth
    
    var leftBound = schi.parentElement.parentElement.getBoundingClientRect().right
    moveAt(e)
    function moveAt(e) {
        var newWidth = startWidth + e.pageX - startX + 2
        if (e.pageX > leftBound - 9 || newWidth < 100) return
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
            breakTwaise(schi, false, coef)
        } if (coef > 1.2) mergeTwaise(schi, false, coef)
    }
})
// разбиение ячейки
function breakTwaise(schi, left, coef) {
    var td = schi.parentElement
    if (td.colSpan == 1) return
    var tr = td.parentElement
    var newColSpan = Math.round(td.colSpan * coef);
    if (left) {
    	var dif = td.colSpan - newColSpan
    	if (dif == 0) return
    	tr.insertBefore(newTd(dif), td)
    }
    td.colSpan = newColSpan
    addSchi(td, schi)
}
// слияние ячеек
function mergeTwaise(schi, left, coef) {
    var td = schi.parentElement
    if (td.colSpan == 4) return
    var tr = td.parentElement
    var newColSpan = Math.round(td.colSpan * coef)
    var dif = newColSpan - td.colSpan 
    if (dif == 0) return
	td.colSpan = newColSpan
    var targetTd = left ? td.previousElementSibling : td.nextElementSibling
    if (targetTd.colSpan - dif == 0) targetTd.remove()
    else targetTd.colSpan -= dif
    addSchi(td, schi)
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
			$(this).find("input[name*='weekplan']").val(parseInt(this.weekplan, 2))
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

$(schi0).dblclick()