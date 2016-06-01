function converDate(longDate){
	var months = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"];
	var date = new Date(longDate);
	var month = date.getMonth();
	var year = date.getFullYear();
	return months[month] + " " + year;
}