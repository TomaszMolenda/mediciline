var id;
var startDate;

$(function() {
	$('.datepicker').datepicker({
		format: "yyyy-mm-dd",
		language: "pl",
		autoclose: true
	});
});

function clickButtonFinish(clicked) {
	id = $(clicked).parent().parent().parent().find('.disease-id').html();
	startDate = $(clicked).parent().parent().parent().find('.disease-start').html();
	showModal($('#modalArchiveDisease'));
}

function clickSubmitAddDisease() {
	var dateStart = $('#diseaseStart').val();
	var dateStartLong = convertDateFromYYYYMMSStoLong(dateStart)
	$('input[id="startLong"]').val(dateStartLong);
	validate(e);
	$('#loadingAdd').prop('hidden', false);
}

function validate(e) {
	if($('input[id="name"]').val() == "" || $('input[id="startLong"]').val() == 0) {
		var error = /*[[#{ErrorAddDisease}]]*/
		$('#errorForm').html(error).show().delay(3000).fadeOut();
		e.preventDefault();
	}
		
}

function clickButtonArchive() {
	var stop = convertDateFromYYYYMMSStoLong($('input[id="diseaseStop"]').val());
	var start = convertDateFromYYYYMMSStoLong(startDate);
	console.log('start '+start);
	console.log('stop '+stop);
	
	var url = 'diseases/archive/' + id + '?date=' + stop;
	if(stop < start) {
		var error = /*[[#{DateStopLessThanDateStart}]]*/
		$('#errorForm2').html(error).show().delay(3000).fadeOut();
	} else {
			$('#btnArchive').parent().attr("href", url);
			$('#loadingArchive').prop('hidden', false);
			$('#btnCancel').prop('disabled', true);
			$('#btnArchive').prop('disabled', true);
	}
}