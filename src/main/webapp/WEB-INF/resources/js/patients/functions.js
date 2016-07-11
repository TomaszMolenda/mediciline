$(function() {
	createPatientsTable();
	
})

function createPatientsTable() {
	$.ajax({
		url: '/api/patients',
		type: 'GET',
		context: document.body,
		beforeSend: function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function(data){
			$('table').empty();
			$('table').append(
					"<thead>" + 
						"<tr>" + 
							"<td>" + /*[[#{FirstName}]]*/
							+ "</td>" + 
							"<td>" + /*[[#{Birthday}]]*/
							+ "</td>" + 
							"<td>" + /*[[#{id}]]*/
							+ "</td>" + 
						"</tr>" + 
					"</thead>" + 
					"<tbody>");
			$.each(data, function(index, element){
				var i = index + 1;
				$('table').append(
						"<tr>" + 
							"<td>" + element.name + "</td>" + 
							"<td>" + converDateToYYYYMMDD(element.birthday) + "</td>" + 
							"<td class=\"patientsTableId\">" + element.id + "</td>" + 
						"</tr>");
			});
			$('table').append("</tbody>");
			$('body').show();
		}
	});
}

function savePatient(clicked) {
	$('#addModalError').html('');
	var name = $('#addModalName').val();
	var birthdayLong = convertDateFromYYYYMMSStoLong($('#addModalBirthDate').val());
	if(name.length > 20 || name.length == 0) {
		$('#addModalError').append(/*[[#{ProblemWithFirstName}]]*/ 
				+ "<br>");
	}
	if(birthdayLong == 0) {
		$('#addModalError').append(/*[[#{ProblemWithDate}]]*/ 
				+ "<br>");
	}
	if(name.length <= 20 && name.length != 0 && birthdayLong > 0) {
		$(clicked).prop('disabled', true);
		var id = $('#addModalId').html();
		var json = {"name" : name, 
				"birthdayLong" : birthdayLong};
		$.ajax({
			url: '/api/patient.json',
			data: JSON.stringify(json),
			type: 'POST',
			beforeSend: function(xhr) {
				$('#addModalError').html('');
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
			success: function(data){
				hideModal($('#modalAddPatient'));
			},
			error: function(xhr) {
				var json = JSON.parse(xhr.responseJSON);
				$.each(json.errors, function(index, e) {
					$('#addModalError').append(e.message + "<br>");
					});
				$('#addModalError').show().delay(5000).fadeOut();
			},
			complete: function(data){
				$(clicked).prop('disabled', false);
				createPatientsTable();
			}
		});
	} else $('#addModalError').show().delay(5000).fadeOut();
}



									
									
