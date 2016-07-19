var name, email, confirmEmail, password, confirmPassword, isValidate

function sendRegister() {
	name = $('#name').val();
	email = $('#email').val();
	confirmEmail = $('#confirmEmail').val();
	password = $('#password').val();
	confirmPassword = $('#confirmPassword').val();
	isValidate = true;
	$('#error').html('');
	
	validate();

	if(isValidate) {
		var json = {"name" : name, 
				"email" : email,
				"confirmEmail" : confirmEmail,
				"password" : password,
				"confirmPassword" : confirmPassword};
		$.ajax({
			url: '/api/register',
			data: JSON.stringify(json),
			type: 'POST',
			beforeSend: function(xhr) {
				$('#addModalError').html('');
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
				$('#loading').prop('hidden', false);
				$('#button').prop('disabled', true); 
				
			},
			success: function(data){
				$('input').val('');
				$('#info').append(/*[[#{SendedRegistrationLink}]]*/ 
						+ "").show();
				$('#loading').prop('hidden', true);
				$('#button').prop('disabled', false); 
			},//
			error: function(xhr) {
				console.log(xhr)
				var json = JSON.parse(xhr.responseJSON);
				$.each(json.errors, function(index, e) {
					$('#error').append(e.message + "<br>");
					});
				$('#error').show().delay(5000).fadeOut();
				$('#loading').prop('hidden', true);
				$('#button').prop('disabled', false); 
			},
			complete: function(data){
			}
		});
	} else $('#error').show().delay(5000).fadeOut();
	
}

function validate() {
	if(name == '') {
		$('#error').append(/*[[#{NameEmpty}]]*/ 
				+ "<br>");
		isValidate = false;
	}
	
	if(email == '') {
		$('#error').append(/*[[#{EmailEmpty}]]*/ 
				+ "<br>");
		isValidate = false;
	}
	
	if(password == '') {
		$('#error').append(/*[[#{PasswordEmpty}]]*/ 
				+ "<br>");
		isValidate = false;
	}
	
	if(email != confirmEmail) {
		$('#error').append(/*[[#{EmailNotTheSame}]]*/ 
				+ "<br>");
		isValidate = false;
	}
	if(password != confirmPassword) {
		$('#error').append(/*[[#{PasswordNotTheSame}]]*/ 
				+ "<br>");
		isValidate = false;
	}
}