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
	$('#buttonArchiveDisease').attr("disabled", true);
	id = $(clicked).parent().parent().parent().find('.disease-id').html();
	startDate = $(clicked).parent().parent().parent().find('.disease-start').html();
	showModal($('#modalArchiveDisease'));
}

function clickSubmitAddDisease(submited) {
	var dateStart = $('#diseaseStart').val();
	var dateStartLong = convertDateFromYYYYMMSStoLong(dateStart)
	$('input[id="startLong"]').val(dateStartLong);
	if(validate()){
		$('#loadingAdd').prop('hidden', false);
		$(submited).find('button').attr("disabled", true);
	}
	
	
}

function validate(e) {
	if($('input[id="name"]').val() == "" || $('input[id="startLong"]').val() == 0) {
		var error = /*[[#{ErrorAddDisease}]]*/
		$('#errorForm').html(error).show().delay(3000).fadeOut();
		event.preventDefault();
		return false;
	}
	return true;
}

function putFinishDate(clicked) {
	var stop = convertDateFromYYYYMMSStoLong($(clicked).val());
	var start = convertDateFromYYYYMMSStoLong(startDate);
	if(stop != 0) {
		if(stop < start) {
			var error = /*[[#{DateStopLessThanDateStart}]]*/
			$('#errorForm2').html(error).show().delay(3000).fadeOut();
		} else {
			var url = 'diseases/archive/' + id + '?date=' + stop;
			$('#buttonArchiveDisease').attr("disabled", false);
			$('#buttonArchiveDisease').parent().attr("href", url);
			
			//$('#buttonArchiveDisease')
			// disabled="disabled"
				console.log('jest ok')
		}
	}
	
	
}

function clickButtonArchive() {
	$('#loadingArchive').prop('hidden', false);
	$('#btnCancel').prop('disabled', true);
	$('#buttonArchiveDisease').prop('disabled', true);
}
