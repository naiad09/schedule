function removeSchi(schi) {
    var targetTd = schi.parentElement
    if (!targetTd) return
    targetTd.removeChild(schi)
    var trOld = targetTd.parentElement
	if (trOld) for(var i = 0;i<4;i++) {
		if (trOld.schi[i]==schi) {
			trOld.schi[i] = null
			if(schi.classList.contains("lab4"))
				trOld.nextElementSibling.schi[i] = null
		}
	}
}
function newItem(colSpan) {
	var td = document.createElement("td")
	td.colSpan = colSpan
	td.classList.add("scheduleItem")
	return td
}
function normalizeTr(tr) {
	var flagLab4Prev = false
	var length = tr.cells.length;
	for (var i = 1; i< length;i++) {
		var td = tr.lastElementChild;
		if(td.firstElementChild)td.removeChild(td.firstElementChild)
		tr.removeChild(td)
	}
	var merge = 0
	var schi = tr.schi[0]
	for (var i = 0;i<tr.schi.length+1;i++) {
		if (schi === tr.schi[i]) merge++
		else {
			var td = newItem(merge)
			merge = 1
			tr.appendChild(td)
			if (schi) {if(schi.classList.contains("lab4")) {
				if (schi.parentElement) {
					td.remove()
					flagLab4Prev = true
				}
				else {
					td.rowSpan = 2
					td.appendChild(schi)
				}
			} else td.appendChild(schi)}
		}
		schi = tr.schi[i]
	}
	
    if (tr.getElementsByClassName("schi").length == 0 && !flagLab4Prev) 
        tr.classList.add("empty")
    else  tr.classList.remove("empty")
}
function addSchi(targetTd, schi) {
	var tdOld = schi.parentElement
	var tr = targetTd.parentElement
	
	if (tdOld) {
		var oldTr = tdOld.parentElement;
		for(var i = 0;i<4;i++) {
			if (oldTr.schi[i]==schi) {
				oldTr.schi[i] = null
				if(schi.classList.contains("lab4"))
					oldTr.nextElementSibling.schi[i] = null
			}
		}
		if(oldTr != tr){
			normalizeTr(oldTr)
			if(schi.classList.contains("lab4") && tr != oldTr.nextElementSibling)
				normalizeTr(oldTr.nextElementSibling)
			}
	}
	
	var j = -1// из-за ячейки с парой
	for(var i=0;tr.cells[i]!=targetTd;i++)
		j+=tr.cells[i].colSpan
		
    var prevTr = tr.previousElementSibling
    for(var i=0;i<prevTr.cells.length && i<targetTd.colSpan;i++)
    	if (prevTr.cells[i].rowSpan==2)
    		j+=prevTr.cells[i].colSpan
    		
    var nextTr = tr.nextElementSibling
		
	for(var i = j;i<targetTd.colSpan+j;i++) {
		var prew = tr.schi[i]
		if (prew && prew != schi) {
			removeSchi(prew)
			if (prew.classList.contains("lab4"))
				normalizeTr(tr.nextElementSibling)
		}
		tr.schi[i] = schi
		if(schi.classList.contains("lab4")) {
			prew = nextTr.schi[i]
			if (prew && prew != schi) {
				removeSchi(prew)
				if (prew.classList.contains("lab4"))
					normalizeTr(tr.nextElementSibling.nextElementSibling)
			}
			nextTr.schi[i] = schi
		}
	}
	
	normalizeTr(tr)
	if(schi.classList.contains("lab4"))
		normalizeTr(nextTr)
}

var schiId = 0
function createSchi(glt) {
    var schi = schiTemplate.cloneNode(true)
    schi.id = "schi" + schiId++
    schi.glt = glt

    var inner = schi.getElementsByClassName("inner")[0]

    schi.classList.add(glt.classList[1])
    inner.appendChild(glt.firstElementChild.cloneNode(true))
    inner.appendChild(glt.lastElementChild.cloneNode(true))
    inner.appendChild(glt.parentElement.firstElementChild.cloneNode(true))
    return schi
}

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
    for (var i = 0; i < 6; i++) {
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
function breakTwaise(schi, left, coef) {
    var td = schi.parentElement
    if (td.colSpan == 1) return
    var tr = td.parentElement
    var newColSpan = Math.round(td.colSpan * coef);
    if (left) {
    	var dif = td.colSpan - newColSpan
    	if (dif == 0) return
    	tr.insertBefore(newItem(dif), td)
    }
    td.colSpan = newColSpan
    addSchi(td, schi)
}

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