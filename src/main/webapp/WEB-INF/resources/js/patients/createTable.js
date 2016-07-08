$(function(){
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
			}
		});
	})