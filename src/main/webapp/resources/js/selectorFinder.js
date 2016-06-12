function SelectorFindHelper(c) {
	var options = c.selector.find("option")
	var optionHeight = 16.75
	var count = options.size()
	c.input.width(c.selector.width())
	c.selector.width(c.selector.width())
	c.selector.css("position", "absolute")
	c.selector.wrap("<div></div>")
	c.selector.css("margin-top", "-1px")
	c.selector.css("z-index", "1000")
	c.selector.attr("size", 2)
	c.selector.append("<option id='notFound' disabled>Ничего не найдено</option>")
	var notFound = c.selector.find("#notFound")
	
	focus = false 
	
	c.input.click(function(){
		if (!focus) {
			focus = true
			
			document.onclick = function(e) {
	            var closest = $(e.target)
	            	.closest("#" + c.input.attr("id") + ", #" + c.selector.attr("id"));
				if (focus && closest.length == 0) {
	                dropSelection()
	            }
	        };			
		}
	})
	
	c.clearButton.click(dropSelection)
	c.selector.hide()
	options.click(dropSelection)
	
	c.input.keypress(function(e) {
		var text = c.input.val() + getChar(e)
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
		
	function dropSelection() {
		focus = false
        document.onclick = undefined
		c.selector.hide()
		c.input.val("")
		if(c.onDropSelection)
			c.onDropSelection()
	}
	
	function showSelection(filtred) {
		notFound.hide()
		options.hide()
		filtred.show()
		c.selector.show()
		var height = filtred.size() * optionHeight + 3
		c.selector.height(Math.min(height,c.maxHeightSelect))
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