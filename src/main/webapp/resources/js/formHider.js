// используется для показа-скрытия форм или чего-то похожего. 
// при событии на обозначенный выключатель форма показывается (processShow),
// при клике вне обозначенного селектора - скрывается (processHide)
function FormHider(event, turner, selector, processHide, processShow) {
	var focus = false
	$("body").on(event, turner, function(e) {
		if (!focus) {
			focus = true
			if (processShow)
				processShow(e)
			$(document).bind("mousedown", clickDocument)
		}
	})
	function processUnbind() {
		focus = false
		$(document).unbind("mousedown", clickDocument)
	}
	function clickDocument(e) {
		var closestInForm = $(e.target).closest(selector);
		var closestInDocument = $(e.target).closest("body");
		if (focus && closestInDocument.length != 0 && closestInForm.length == 0) {
			processUnbind()
			processHide()
		}
	}
}