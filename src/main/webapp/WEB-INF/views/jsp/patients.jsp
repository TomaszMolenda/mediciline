<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<div class="container">
	<table id="patientsTable" class="display table table-striped table-bordered dt-responsive nowrap" width="100%">
	</table>
	<script type="text/javascript">
	function createPatientsTable(){
		$.ajax({
 			url: '/api/patients.json',
 			type: 'GET',
 			context: document.body,
 			beforeSend: function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},
 			success: function(data){
 				$('#patientsTable').empty();
 				$('#patientsTable').append(
 						"<thead>" + 
 							"<tr>" + 
 								"<td>Imię</td>" + 
 								"<td>Data urodzenia</td>" + 
 								"<td hidden=\"true\"></td>" + 
 							"</tr>" + 
 						"</thead>" + 
 						"<tbody>");
 				$.each(data, function(index, element){
 					var i = index + 1;
 					$('#patientsTable').append(
	 							"<tr>" + 
	 								"<td>" + element.name + "</td>" + 
	 								"<td>" + converDateToYYYYMMDD(element.birthday) + "</td>" + 
	 								"<td class=\"patientsTableId\" hidden=\"true\">" + element.id + "</td>" + 
	 							"</tr>");
 				});
 				$('#patientsTable').append("</tbody>");
 			},
 			error: function(xhr) {
 			}
 		});
	};
	$(window).load(function(){
		createPatientsTable();
	});
 	$(function() {
		$('#patientsTable').on('click', 'tbody > tr', function(){
	   		$(this).addClass('info');
	    	$(this).siblings().removeClass('info');
   		});
 	});
	</script>
	

	<button class="btn btn-warning btn-lg" id="addButton">Dodaj</button>
		<script type="text/javascript">
			$('#addButton').click(function(){
				$('#addModal').modal({
					  backdrop: 'static',
					  keyboard: false
					}).show();
			});
		</script>
<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
	<button class="btn btn-warning btn-lg" id="editButton">Edytuj</button>
		<script type="text/javascript">
		$('#editButton').click(function(){
			var patientId = $('#patientsTable').find('.info').find('.patientsTableId').html();
			$.ajax({
	 			url: '/api/patient/' + patientId + '.json',
	 			type: 'GET',
	 			beforeSend: function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type", "application/json");
				},
	 			success: function(data){
	 				$('#addModalName').val(data.name);
	 				var date = converDateToYYYYMMDD(data.birthday);
	 				$('#addModalBirthDate').val(date);
	 				$('#addModal').modal({
	 					  backdrop: 'static',
	 					  keyboard: false
	 					}).show();
	 			},
	 			error: function(xhr) {
	 			}
	 		});
			
		});
		</script>
	<button class="btn btn-warning btn-lg" id="deleteButton">Usuń</button>
		<script type="text/javascript">
		$('#deleteButton').click(function(){
			var patientId = $('#patientsTable').find('.info').find('.patientsTableId').html();
			$.ajax({
	 			url: '/api/patient/delete/' + patientId + '.json',
	 			type: 'DELETE',
	 			beforeSend: function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type", "application/json");
				},
	 			success: function(data){
	 				console.log(data);
	 				createPatientsTable();
	 			},
	 			error: function(xhr) {
	 				console.log(xhr);
	 			}
	 		});
			
		});
		</script>
</div>
</sec:authorize>
<div class="modal fade" id="addModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Dodaj osobę</h4>
            </div>
            <div class="modal-body">
					<div class="form-group">
						Imię
						<input id="addModalName" autocomplete="off" class="form-control"/>
						<input id="addModalId" hidden="true" value="0"/>
					</div>
					<div class="form-group">
						Data urodzenia:
						<div class="input-group">
	   						<label class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span></label>
	      						<input id="addModalBirthDate" autocomplete="off" class="datepicker form-control" placeholder="Kliknij tu"/>
						</div>
					</div>
					<div style="margin-top: 15px;" class="alert alert-danger " id="addModalNoName" hidden="true">Wpisz imię</div>
					<div style="margin-top: 15px;" class="alert alert-danger " id="addModalBadDate" hidden="true">Wybierz poprawną datę</div>
					<div class="modal-footer">
						<span hidden="false" id=addModalLoadingSave>
						Zapisywanie...
						<img src="/resources/jpg/loading.gif">
						</span>
                		<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                		<button id="addModalSave" type="button" class="btn btn-danger">Zapisz</button>
                			<script type="text/javascript">
								$('#addModalSave').on('click', function(){
									$('#addModalSave').prop('disabled', true);
									var name = $('#addModalName').val();
									var birthday = $('#addModalBirthDate').val();
									var id = $('#addModalId').html();
									var json = { "id" : id, 
											"name" : name, 
											"birthdayString" : birthday};
									$.ajax({
										url: '/api/patient.json',
										data: JSON.stringify(json),
										type: 'POST',
										beforeSend: function(xhr) {
											xhr.setRequestHeader("Accept", "application/json");
											xhr.setRequestHeader("Content-Type", "application/json");
										},
										success: function(data){
											console.log(data);
											$('#addModal').modal('hide');
										},
										error: function(xhr) {
											if(xhr.statusText == "No such element")
												$('#addModalNoName').show().delay(5000).fadeOut();
											if(xhr.statusText == "Parse exception")
												$('#addModalBadDate').show().delay(5000).fadeOut();
										},
										complete: function(data){
											$('#addModalSave').prop('disabled', false);
											createPatientsTable();
											
										}
									});
								});
							</script>
						
            		</div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$(function() {
	$('.datepicker').datepicker({
		format: "yyyy-mm-dd",
		language: "pl",
	    autoclose: true
	});
});

</script> 
