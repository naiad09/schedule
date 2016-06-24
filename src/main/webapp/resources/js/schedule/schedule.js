// Управляет интерфейсом расписания. Сокрыщения:
// schi - schedule item, элемент расписания
// glt - group lesson type, дисциплина расписания
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
	var schiInfo = this.innerText.split(/\,\n?\s*/, 6)
	var schi = createSchi($("#glt" + schiInfo[2])[0])
	schi.twain = schiInfo[1]
	var tr = $("tbody.weekday").eq(schiInfo[3]).find("tr.scheduleTr").eq(schi.twain-1)[0]
	$(schi).find("input[name*='idScheduleItem']").val(schiInfo[0])
	if (schiInfo[6]) $(schi).find("input[name*='comment']").val(schiInfo[6].trim())
	if (schiInfo[5] == "true") schi.classList.add("conflict")
	
	$(schi).find(".classrooms").append($(this).find(".classroomInput"))
	schi.weekplan = ("00000000" + (parseInt(schiInfo[4])).toString(2))
	schi.weekplan = schi.weekplan.substr(schi.weekplan.length - 8, 8)
	schi.weekplan = new Weekplan(schi.weekplan)

	writeSchiToTr(schi, tr)
})
function normalizeAllTrs(){
	$("tr.scheduleTr").filter(function(){
		return this.needNormalize
	}).each(function(){
		normalizeTr(this)
	}).each(function(){
		this.needNormalize = undefined
		$(this).find(".schi").each(function(){
			this.style.removeProperty("height")
			this.style.removeProperty("font-size")
		}).filter(":only-child").each(function(){
			var newHeight = $(this.parentElement).height()-3
			var height = $(this).find(".discipline").height()
			if (newHeight / height > 4) {
				this.style.fontSize = "large"
			}
			$(this).height(newHeight)
		})
	})
	
	function normalizeTr(tr) {
		var flagLab4Prev = false
		$(tr).find(".scheduleItem, .schi").remove()
		
		var dominSchiFlag
		var dominSchi
		var td
		var newTdFlag
		
		for(var i = 0;i<4;) {
			
			var beforeSchi = tr.schiBefore[i]
			var afterSchi = tr.schiAfter[i]
			if (!beforeSchi && !afterSchi){
				if (dominSchi || i==0) {
					td = newTd(1)
					newTdFlag = true
					tr.appendChild(td)
				}
				dominSchi = undefined
				td.colSpan = (newTdFlag)?1:td.colSpan+1
						newTdFlag = false
				i++
			} else {
				td = newTd(1)
				tr.appendChild(td)
				
				var before = beforeSchi ? beforeSchi.weekplan
						.toString().substr(0,4).match(/1/g).length : 0
				var after  = afterSchi ? afterSchi.weekplan
						.toString().substr(4,4).match(/1/g).length : 0
				
				dominSchiFlag = before < after
						
			    dominSchi = dominSchiFlag?afterSchi:beforeSchi
			    td.colSpan = dominSchi.weekplan.colspanInDominHalf()
			    if (dominSchi.lab4 && dominSchi.parentElement) {
			    	$(td).hide()
			    	flagLab4Prev = true
			    	td = dominSchi.parentElement
			    }
			    		
				appendSchiToTd(dominSchi, td)
				if (dominSchi.lab4) td.rowSpan = 2
				var newEnd = td.colSpan + i
				var vac = 0
			    for (;i<newEnd+1;i++) {
			    	var nonDominSchi = (dominSchiFlag?tr.schiBefore:tr.schiAfter)[i]
			    	if ((i == newEnd) || nonDominSchi) {
			    		while (vac > 0) {
			    			var minus = (vac == 4)?4:(i==vac && i>1)?2:(i-vac==1)?1:vac
			    			var newWeekplan = Array(i-vac+1).join("0")
			    				+ Array(minus+1).join("1") + Array(4-i+vac-minus+1).join("0")
			    			newWeekplan = (dominSchiFlag?"":"0000") + newWeekplan + (dominSchiFlag?"0000":"")
			    			var vacancy = cloneVacancy(new Weekplan(newWeekplan))
				    		vacancy.twain = calcTwain(tr)
				    		appendSchiToTd(vacancy, td)
				    		vac -= minus
			    		}
				    		
			    		if ((i < newEnd) && nonDominSchi && !nonDominSchi.parentElement)
			    			appendSchiToTd(nonDominSchi, td)
			    	} else vac++
			    }
				if (dominSchiFlag) td.appendChild(dominSchi)
				i--
			}	
					
			
		}
		
		
		
		
		if (tr.getElementsByClassName("schi").length == 0) // && !flagLab4Prev)
	        tr.classList.add("empty")// и определяем класс
	    else  tr.classList.remove("empty")
	}
	
	function cloneVacancy(weekplan) {
		var vacancyClone = vacancyTemplate.cloneNode(true)
		vacancyClone.weekplan = weekplan
		vacancyClone.id = ""
		return vacancyClone
	}
	
	function appendSchiToTd(schi, td) {
		if (!schi || schi.parentElement) return
		td.appendChild(schi)
		$(schi).attr("colspan",schi.weekplan.colspanInDominHalf())
	    updateWeekplanLabel(schi)
	    return schi
	}
	
	// создает новую ячейку td
	function newTd(colSpan) {
		var td = document.createElement("td")
		td.colSpan = colSpan
		td.classList.add("scheduleItem")
		td.ondrop = dragDropTd
		return td
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
	for (var i = 0; i < 4; i++) if (tr.schiBefore[i] == schi) {
		tr.schiBefore[i] = null
	}
	for (var i = 0; i < 4; i++) if (tr.schiAfter[i] == schi) {
		tr.schiAfter[i] = null
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
			removeSchiFromTr(schi, trOld.nextElementSibling, true)
	}
    
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
	var width = $(schi).width()*0.4 + $(schi).find("b").width()*0.6 - 60
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
	var w = schi.weekplan.normalize()
	var span = $(schi).find(".weekplan")[0]
	var dominHalf = w.dominAfterHalf()
	var nonDomin = !dominHalf?w.ac:w.bc
	
	if (w.bc != w.ac) {
		if (nonDomin == "00") {
			span.innerText = (dominHalf?"со":"до") + " смены расписания"
			if (w.base != "every" && (dominHalf?w.ac:w.bc)!= "11") 
				span.innerText += " по числам: " + perDays()			
		} else {
			span.innerText = (!dominHalf?"со":"до") + " смены расписания по"
			switch (nonDomin) {
			case "10": if (w.base == "every") span.innerText += " числителю"
				else span.innerText += " числам: " + perDays()
				break
			case "01": if (w.base == "every") span.innerText += " знаменателю"
				else span.innerText += " числам: " + perDays()
				break
			}
		}
	} else {
		span.innerText = perDays()
	}
	
	$(schi).find(".numDen")[0].innerText = (w.base=="num")?"числ.":(w.base=="den")?"знам.":""
		
	var twainSpan = $(schi).find(".twain")[0]
	if (schi.classList.contains("vacancy") || calcTwain($(schi).parents("tr.scheduleTr")[0]) != schi.twain)
		twainSpan.innerText 
				= $("select[name='twain'] option[value='" + schi.twain + "']").text().match(/\d\d\.\d\d/)
	else twainSpan.innerText = ""
		
	function perDays() {
		var days = schi.weekplan.getDates($(schi).parents("tbody.weekday")[0].index)
		if (!days) return ""
		var a = ""
		var options = {month: 'numeric',day: 'numeric'}
		for (var i = 0; i < days.length; i++) {
			a += days[i].toLocaleDateString("ru", options) + ", "
		}
			
		return a.substr(0, a.length-2);
	}
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
