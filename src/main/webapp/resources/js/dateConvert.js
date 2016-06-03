function converDate(longDate){
	var months = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"];
	var date = new Date(longDate);
	var month = date.getMonth();
	var year = date.getFullYear();
	return months[month] + " " + year;
}

function converDateToYYYYMMDD(longDate){
	//http://stackoverflow.com/a/6040556/5753094
	var date = new Date(longDate);
	var month = ("0" + (date.getMonth() + 1)).slice(-2);
	var year = date.getFullYear();
	var day = ("0" + date.getDate()).slice(-2);
	var fullYear = year + "-" + month + "-" + day;
	return fullYear;
}