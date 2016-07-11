var timeToSend = "12:00";
$(function(){
	$('#btnHourUp').click(function(){
		var hour = $('#hourValue').html();
		hour++;
		if(hour==24) hour = 0;
		$('#hourValue').html(hour);
	});
	$('#btnHourDown').click(function(){
		var hour = $('#hourValue').html();
		hour--;
		if(hour==-1) hour = 23;
		$('#hourValue').html(hour);
	});
	$('#btnMinuteUp').click(function(){
		var minute = $('#minuteValue').html();
		minute = minute * 1 + 5;
		if(minute==60) minute = 0;
		if(minute<10) $('#minuteValue').html("0" + minute);
		else $('#minuteValue').html(minute);
	});
	$('#btnMinuteDown').click(function(){
		var minute = $('#minuteValue').html();
		minute = minute - 5;
		if(minute<0) minute = 55;
		if(minute<10) $('#minuteValue').html("0" + minute);
		else $('#minuteValue').html(minute);
	});
	$('.btn-time').click(function(){
		var hour = $('#hourValue').html().trim();
		var minute = $('#minuteValue').html().trim();
		if(hour<10)
			timeToSend = "0" + hour + ":" + minute;
		else
			timeToSend = hour + ":" + minute;
	});
})

function saveDosage() {
	$('#loadingAdd').prop('hidden', false);
	$('#saveDosage').attr("disabled", true);
	var dose = $('#addDosageDosage').val();
		var json = { "idD" : idD,
				"idM" : idM,
			"takeTime" : timeToSend, 
			"dose" : dose};
		$.ajax({
		url: '/api/dosage/add',
		data: JSON.stringify(json),
		type: 'POST',
		beforeSend: function(xhr) {
			
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		complete: function(data){
			$('#loadingAdd').prop('hidden', true);
			$('#saveDosage').attr("disabled", false);
			hideModal($('#modalAddDosage'));
			getDosages();
		}
	});
}

function getDosages(){
	$.ajax({
		url: '/api/dosages',
		data:{idM:idM, idD:idD},
		type: 'GET',
		beforeSend: function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function(data){
			$('#modalDosagesDisease').html(/*[[#{Disease}]]*/
				+ ': ' + data.disease.name);
			$('#modalDosagesMedicament').html(/*[[#{Medicament}]]*/
				+ ': ' + data.medicament.name + 
					' (' + /*[[#{inPack}]]*/
					+ ' :' + data.medicament.quantity + ', ' + /*[[#{unit}]]*/
					+ ': ' + data.medicament.unit + ')');
			
			$('#tableDosages').empty();
			$('#tableDosages').append("<thead><tr><td>godzina</td><td>dawka</td><td></td></tr></thead><tbody>")
			$.each(data.dosages, function(index, element){
				
				var date = new Date(element.takeTime);
				var formattedDate = moment(date).format('HH:mm');
				$('#tableDosages').append(
						"<tr>" + 
							"<td>" + formattedDate + "</td>" + 
							"<td>" + element.dose + "</td>" + 
							"<td><a><img style=\"cursor: pointer\" src=\"/resources/jpg/archive.png\" class=\"delete-dosage\" url=\"/api/dosage/delete/" + element.id + "\"></a></td>" + 
						"</tr>")
			});
			$('#tableDosages').append("</tbody>")
			showModal($('#modalDosages'));
		},
	});
}

function deleteDosage(url) {
	$.ajax({
		url: url,
		type: 'DELETE',
		beforeSend: function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		complete: function(data) {
			getDosages();
		}
		
	});
}