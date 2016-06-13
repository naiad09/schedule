// используется для показа-скрытия форм или чего-то похожего. 
// при событии на обозначенный выключатель форма показывается (processShow),
// при клике вне обозначенного селектора - скрывается (processHide)
function FormHider(event, turner, selector, processHide, processShow) {
	var focus = false
	turner.bind(event, function(e) {
		if (!focus) {
			focus = true

			if (processShow)
				processShow(e)

			$(document).bind("click", clickDocument)

		}
	})
	function processUnbind() {
		focus = false
		$(document).unbind("click", clickDocument)
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