function Weekplan(code) {
	this.base = calcBase(code)
	this.bc = calcBCAC(code.substr(0, 4), this.base)
	this.ac = calcBCAC(code.substr(4, 4), this.base)

	this.normalize = function() {
		var n = new Weekplan(this.toString())
		this.base = n.base
		this.bc = n.bc
		this.ac = n.ac
		return this
	}

	this.invert = function() {
		var w = new Weekplan(this.toString())
		if (w.bc == w.ac)
			return false
		w.bc = w.bc.replace(/1/g, "2").replace(/0/g, "1").replace(/2/g, "0")
		w.ac = w.ac.replace(/1/g, "2").replace(/0/g, "1").replace(/2/g, "0")
		return w
	}

	this.toString = function() {
		var ac = this.ac
		var bc = this.bc
		if ((ac == "01" && bc == "10") || (ac == "10" && bc == "01")
				|| (ac == "00" && bc == "00")) {
			return false
		}
		switch (this.base) {
		case "every":
			bc = bc[0] + bc + bc[1]
			ac = ac[0] + ac + ac[1]
			break
		case "num":
			bc = bc + "00"
			ac = ac + "00"
			break
		case "den":
			bc = "00" + bc
			ac = "00" + ac
			break
		}
		return (bc + "" + ac)
	}
	this.dominAfterHalf = function() {
		return calcHalfOfWeekplan(this.toString())
	}
	this.colspanInDominHalf = function() {
		return this.toString().substr(this.dominAfterHalf() ? 4 : 0, 4).match(
				/1/g).length
	}
	this.breakTwise = function(left) {
		if (this.base == "every")
			this.base = left ? "den" : "num"
		else if (left) {
			this.bc = "0" + this.bc[0]
			this.ac = "0" + this.ac[0]
		} else {
			this.bc = this.bc[0] + "0"
			this.ac = this.ac[0] + "0"
		}
		this.normalize()
	}
	this.mergeTwise = function(left) {
		if (this.base != "every" && this.colspanInDominHalf() > 1)
			this.base = "every"
		else if (left) {
			this.bc = this.bc[1] + this.bc[1]
			this.ac = this.ac[1] + this.ac[1]
		} else {
			this.bc = this.bc[0] + this.bc[0]
			this.ac = this.ac[0] + this.ac[0]
		}
		this.normalize()
	}

	// вычисляет базу понедельного плана
	function calcBase(weekplan) {
		var dominAfterChange = calcHalfOfWeekplan(weekplan)
		var domainHalf = weekplan.substr(dominAfterChange ? 4 : 0, 4);
		var nondomainHalf = weekplan.substr(!dominAfterChange ? 4 : 0, 4);
		if (domainHalf == "1111")
			return "every"
		var domainDenominator = calcHalfOfWeekplan(domainHalf)
		return domainDenominator ? "den" : "num";
	}
	// вычисляет, какая половина больше
	function calcHalfOfWeekplan(weekplan) {
		var a = 0
		var b = 0
		for (var i = 0; i < weekplan.length / 2; i++)
			if (weekplan[i] == "1")
				a++
		for (var i = weekplan.length / 2; i < weekplan.length; i++)
			if (weekplan[i] == "1")
				b++
		return b > a
	}
	// вычисляет части понедельного плана до и после смены расписания
	function calcBCAC(weekplanHalf, base) {
		switch (base) {
		case "every":
			return weekplanHalf.substr(1, 2)
		case "num":
			return weekplanHalf.substr(0, 2)
		case "den":
			return weekplanHalf.substr(2, 2)
		}
	}

	this.getDates = function(weekday) {
		this.normalize()
		if (this.base == "every")
			return false

		var dates = []

		var aa = this.ac.match(/1/g)

		var domin = (aa && aa.length == 1) ? this.ac : this.bc
		var week = (domin == "10") ? 0 : 2
		if (this.base == "den")
			week++

		var start = new Date($("#dateStart").val())
		while (start.getDay() != 1) {
			start.setDate(start.getDate() - 1)
		}

		var dateStart = new Date($(
				(this.bc != "00" && this.bc != "11") ? "#dateStart"
						: "#dateChange").val())
		var dateEnd = new Date($(
				(this.ac != "00" && this.ac != "11") ? "#dateEnd"
						: "#dateChange").val())

		var days = 1000 * 60 * 60 * 24

		for (var i = start.getTime() + (weekday + week * 7) * days; i <= dateEnd
				.getTime(); i += 7 * 4 * days) {
			if (i >= dateStart.getTime())
				dates[dates.length] = new Date(i)
		}

		return dates
	}
}