// Управляет интерфейсом расписания. Сокрыщения:
// schi - schedule item, элемент расписания
// glt - group lesson type
// Каждая tr содержит массив[4] ссылок на schi

// удалить schi
function removeSchi(schi) {
    var targetTd = schi.parentElement
    if (!targetTd) return
    // удалить из ячейки
    targetTd.removeChild(schi)
    var trOld = targetTd.parentElement
    // и из массива в tr
	if (trOld) for(var i = 0;i<4;i++) {
		if (trOld.schi[i] == schi) {
			trOld.schi[i] = null
			if (schi.classList.contains("lab4"))
				trOld.nextElementSibling.schi[i] = null
		}
	}
    settingsResetButton.click()
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
	var schi = tr.schi[0]
	for (var i = 0;i<tr.schi.length+1;i++) {// +1, чтобы последняя merge тоже
											// создавалась
		if (schi === tr.schi[i]) merge++ // если совпадает, инкрементируем
		// tr.schi[4]=undefined, поэтому последняя ячейка создается
		else {// иначе создаем новую ячейку с результатом
			var td = newTd(merge)
			merge = 1
			tr.appendChild(td)// и добавляем
			if (schi) {if(schi.classList.contains("lab4")) {// однако если это
															// lab4
				if (schi.parentElement) {// и если schi уже добавлена
											// куда-то, то есть строкой выше
					td.remove()// удаляем ячейку
					flagLab4Prev = true
				}
				else {
					td.rowSpan = 2// ианче ставим rowspan
					td.appendChild(schi)
				}
			} else td.appendChild(schi)}// а если обычная то просто добавляем
		}
		schi = tr.schi[i]
	}
	
    if (tr.getElementsByClassName("schi").length == 0 && !flagLab4Prev) 
        tr.classList.add("empty")// и определяем класс
    else  tr.classList.remove("empty")
}
// добавляет shci в tr.schi, удаляет конфликты
function addSchi(targetTd, schi) {
	var tdOld = schi.parentElement
	var tr = targetTd.parentElement
	
	if (tdOld) {// если schi был перемещен, то нужно удалить старые ссылки
		var oldTr = tdOld.parentElement;
		for(var i = 0;i<4;i++) {
			if (oldTr.schi[i]==schi) {
				oldTr.schi[i] = null
				if(schi.classList.contains("lab4"))
					oldTr.nextElementSibling.schi[i] = null
			}
		}
		if(oldTr != tr){// и если это было в другой строке, нормализовать ее
			normalizeTr(oldTr)
			if(schi.classList.contains("lab4") && tr != oldTr.nextElementSibling)
				normalizeTr(oldTr.nextElementSibling)
			}
	}
	
	var j = -1// из-за ячейки с парой
	for(var i=0;tr.cells[i]!=targetTd;i++)// считаем пропуски в этой строке
		j+=tr.cells[i].colSpan
		
    var prevTr = tr.previousElementSibling// и из-за предыдущий lab4
    for(var i=0;i<prevTr.cells.length && i<targetTd.colSpan;i++)
    	if (prevTr.cells[i].rowSpan==2)
    		j+=prevTr.cells[i].colSpan
    		
    var nextTr = tr.nextElementSibling
		
	for(var i = j;i<targetTd.colSpan+j;i++) {
		var prew = tr.schi[i]
		if (prew && prew != schi) {// если конфликт, удаляем его
			removeSchi(prew)
			if (prew.classList.contains("lab4"))
				normalizeTr(tr.nextElementSibling)
		}
		tr.schi[i] = schi// записываем schi
		if(schi.classList.contains("lab4")) { // если lab4, то запиываем и в
												// следующую строку
			prew = nextTr.schi[i]
			if (prew && prew != schi) {
				removeSchi(prew)// и удаляем конфликты
				if (prew.classList.contains("lab4"))
					normalizeTr(tr.nextElementSibling.nextElementSibling)
			}
			nextTr.schi[i] = schi
		}
	}
	
	normalizeTr(tr)// и нормализуем
	if(schi.classList.contains("lab4"))
		normalizeTr(nextTr)
		
	updateDivider(schi)
}

var schiId = 0
// создает schi по glt
function createSchi(glt) {
    var schi = schiTemplate.cloneNode(true)// клонирует шаблон
    schi.id = "schi" + schiId++// добавляет id
    schi.glt = glt// сохраняет ссылку

    var discipline = $(schi).find(".discipline")

    schi.classList.add(glt.classList[1])
    discipline.append($(glt).find("input[name='idLessonType'], .gltType").clone())
    discipline.append(" ")
    discipline.append($(glt).parent().find("b").clone())
    updateDetails(schi)
    return schi
}

function updateDetails(schi) {
	var details2 = $(schi).find(".details span.classrooms")
	var clrooms = details2.find("input")
	clrooms.each(function(i){
		var cl = $(classroomSelector).find("option[value='"+this.value+"']").attr("title")
		details2.append(cl+((i<clrooms.size()-1)?", ":""))
	})
	
	var details0 = $(schi).find(".details span.lecturers")
	var lects = $(schi.glt).find(".lecturers input")
	lects.each(function(i){
		var cl = $(lecturerSelector).find("option[value='"+this.value+"']").attr("title")
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
        if (schi.classList.contains("lab4") 
        		&& targetTr.parentElement.lastElementChild == targetTr)
        	break;
        addSchi(targetTd, schi)
        break
    case "schi":
        var data = ev.dataTransfer.getData('SCHId')
        schi = document.getElementById(data)
        if (schi.classList.contains("lab4") 
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
    var schi = ev.target
    while (!schi.classList.contains("schi")) {
    	schi = schi.parentNode
        if (schi == document) return
    }
	ev.dataTransfer.setData('SCHId', schi.getAttribute('id'))
    ev.dataTransfer.setData('type', "schi")
    return true
}
function dragEnter(ev) {
    event.preventDefault()
    var td = ev.target
    for (var i = 0; i < 10; i++) {
        if (td.classList.contains("scheduleItem"))
        	return true;
        else {
            td = td.parentNode
            if (td == document) break
            }
    }
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

// обработчик на кнопке удаления
function processClickDelete(link) {
    while (!link.classList.contains("schi")) {
        link = link.parentNode
        if (link == document) return
    }
    var tr = link.parentElement.parentElement
    removeSchi(link)

    normalizeTr(tr)
    if(link.classList.contains("lab4"))
        normalizeTr(tr.nextElementSibling)
}