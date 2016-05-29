RegExp.escape= function(s) {
    return s.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
}

function selectorFindHelper(input, select, clearButton, maxHeight) {
	var options = select.find("option")
	var optionHeight = options.height()
	var count = options.size()
	input.width(select.width() - 7)
	select.width(select.width())
	select.css("position", "absolute")
	select.wrap("<div></div>")
	select.css("margin-top", "-5px")
	select.attr("size", 2)
	select.append("<option id='notFound' disabled>Ничего не найдено</option>")
	var notFound = select.find("#notFound")
	
	clearButton.click(dropSelection)
	
	select.hide()
	
	input.keypress(function(e) {
		var text = input.val() + getChar(e)
		switch (e.keyCode) {
		case 27: dropSelection()
				 return
		case 8: text = text.substring(0,text.length-1)
		}
		var filtred = options.filter(":not(:disabled)").filter(function(){
					return $(this).html().match(new RegExp(RegExp.escape(text), "im"))
					})
		if (filtred.size()>0) showSelection(filtred)
		else showSelection(notFound)
	})
	
	
	options.click(dropSelection)

	function dropSelection() {
		select.hide()
		input.val("")
	}
	
	function showSelection(filtred) {
		notFound.hide()
		options.hide()
		filtred.show()
		select.show()
		var height = filtred.size() * optionHeight + 3
		select.height(Math.min(height,maxHeight))
	}
}

function getChar(event) {
	  if (event.which == null) { // IE
	    if (event.keyCode < 32) return ""; // спец. символ
	    return String.fromCharCode(event.keyCode)
	  }

	  if (event.which != 0 && event.charCode != 0) { // все кроме IE
	    if (event.which < 32) return ""; // спец. символ
	    return String.fromCharCode(event.which); // остальные
	  }

	  return ""; // спец. символ
	}