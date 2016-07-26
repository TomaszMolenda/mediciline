var idM;

function clickDrugsBitton(clicked) {
	idM = $(clicked).parent().parent().parent().find('.medicament-id').html();
	getDosages();
}

function unblockAddButton() {
	var checkedCount = $('#formAddMedicaments').find('input[type="checkbox"]:checked').length;
	if(checkedCount > 0)
		$('#formAddMedicaments').find('button').attr('disabled', false);
	else
		$('#formAddMedicaments').find('button').attr('disabled', true);
}

function unblockRemoveButton() {
	var checkedCount = $('#formRemoveMedicaments').find('input[type="checkbox"]:checked').length;
	if(checkedCount > 0)
		$('#formRemoveMedicaments').find('button').attr('disabled', false);
	else
		$('#formRemoveMedicaments').find('button').attr('disabled', true);
}

function showEmailModal() {
	showModal($('#modalSendEmail'));
}

function sendEmail() {
	email = $('#inputEmail').val();
	$.ajax({
		url: '/api/dosage/email/' + id,
		data:{email:email},
		type: 'GET',
		beforeSend: function(xhr) {
			if(!validateEmail(email)) {
				var error = /*[[#{InputOkEmail}]]*/
				$('#error').html(error).show().delay(3000).fadeOut();
				xhr.abort();
			} else {
				$('#loadingSend').show();
				$('#btnSend').attr("disabled", true);
			}
			
			
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function(data){
			var info = /*[[#{EmailSended}]]*/
			$('#info').html(info).show();
		},
		error: function(xhr) {
			var error = /*[[#{SomethingWrong}]]*/
				$('#error').html(error).show().delay(3000).fadeOut();
		},
		complete: function(data) {
			$('#loadingSend').hide();
			$('#btnSend').attr("disabled", false);
		}
	});
}

function validateEmail(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regex.test(email);
	
}
	