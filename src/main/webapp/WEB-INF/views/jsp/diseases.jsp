<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="true" %>
<div class="container">

<c:if test="${param.fileToBig eq true}">
		<div class="alert alert-success">Plik jest za duży!! Rozmiar maksymalny to 5MB</div>

</c:if>

<c:if test="${patientObj == null}">

	<div>Wybierz osobę</div>
	<form:form action="patient.html" modelAttribute="patientForm" method="post">
	<table id="patientsTable" class="display table table-striped table-bordered dt-responsive nowrap" width="100%">
		<thead>
			<tr>
				<td hidden="true">rb</td>
				<td>Imię</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${patientForm.patients}" var="patient">
				<tr>
					<td hidden="true"><form:radiobutton path="id" value="${patient.id}" cssClass="radioButton"/></td>
					<td><span>${patient.name}</span></td>
				</tr>
			</c:forEach>		
		</tbody>
	</table>
		<input type="submit" value="Wybierz" Class="btn btn-danger loading-block" disabled id="btnSubmitPatient">
	</form:form>
</c:if>
</div>

<script type="text/javascript">
 $(function() {
   	$('#patientsTable').on('click', 'tbody > tr', function(){
   		$(this).addClass('info');
    	$(this).siblings().removeClass('info');
    	$('#btnSubmitPatient').prop('disabled', false);
    	$(this).find('.radioButton').prop("checked", true);
   		});
 });
</script>
<c:if test="${patientObj != null}">
	<div class="container">
		
			<p>
			
					Wybrana osoba: <c:out value="${patientObj.name}"/>
				<a href="/disease/patient/delete.html" class="btn btn-info" role="button">Zmień</a>
			<p>
		<table id="myTable" class="display table table-striped table-bordered dt-responsive nowrap" width="100%">
			<thead>
				<tr>
					<th>Choroba</th>
					<th>Rozpocz</th>
					<th>Zakoncz</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${diseases}" var="disease">
					<tr>
						<td class="disease-name">${disease.name}</td>
						<td class="disease-start">${disease.start}</td>
						<td>
							<span class="disease-stop">${disease.stop}</span>
							<span class="rowHiddenId" hidden=true>${disease.id}</span>
							<span class="disease-description" hidden=true>${disease.description}</span>
						</td>
					</tr>

	
				</c:forEach>
			</tbody>
		</table>
		<div style="margin-top: 15px;" class="alert alert-danger " id="noChooseMedicament" hidden="true">Musisz wybrać chorobę!</div>
		<div style="margin-top: 15px;" class="alert alert-danger " id="noMedicamentsInDisease" hidden="true">Brak leków przypisanych do choroby!</div>
		<div style="margin-top: 15px;" class="alert alert-danger" id="noMedicaments" hidden="true">Nie masz żadnych leków!</div>
		<div style="float: left;" class="button">
			<button class="btn btn-warning btn-lg" id="addButton">Dodaj</button>
		</div>
		<div style="float: left;" class="button">
			<button class="btn btn-info btn-lg" id="editButton">Edytuj</button>
		</div>
		<div style="float: left;" class="button">
			<button class="btn btn-danger btn-lg" id="deleteButton">Usuń</button>
		</div>
		<div style="float: left;" class="button">
			<button class="btn btn-info btn-lg" id="addMedicamentButton">Dodaj leki</button>
		</div>
		<div style="float: left;" class="button">
			<button class="btn btn-info btn-lg" id="showMedicamentButton">Pokaż leki</button>
		</div>
		<div style="float: left;" class="button">
			<button class="btn btn-info btn-lg" id="addFile">Dodaj plik</button>
		</div>
		<div style="clear: both;"></div>
		
		
	</div>
	
	
<script type="text/javascript">
$('#addButton').click(function(){
	$('.form-control').val('');
	$('.info').removeClass('info');
	$('#diseaseEditIdForm').prop('disabled', true);
	$('#addOrChangeTitle').text('Dodaj')
	$('#addModal').modal({
		  backdrop: 'static',
		  keyboard: false
		}).show();
});

$('#editButton').on('click', function(){
	if($('#diseaseEditIdForm').prop('disabled')) {
		$('#noChooseMedicament').show().delay(5000).fadeOut();
	}
	else {
		$('#addOrChangeTitle').text('Edytuj')
		$('#addModal').modal({
			  backdrop: 'static',
			  keyboard: false
			}).show();
	}
});
</script>

<!-- modal dodawanie choroby -->
<div class="modal fade" id="addModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel"><span id="addOrChangeTitle"></span> chorobę</h4>
            </div>
            <div class="modal-body">
            	<form:form action="change.html" method="post" modelAttribute="disease" id="addDiseaseForm">
            		<form:hidden path="id" id="diseaseEditIdForm" disabled="true"/>
					<div class="form-group">
						<form:label path="name">Choroba</form:label>
						<form:input autocomplete="off" path="name" id="dname" cssClass="form-control"/>
					</div>
					<div class="form-group">
						<div class="input-group">
	   						<label for="start" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span></label>
	      						<form:input autocomplete="off" path="startString" cssClass="datepicker form-control" placeholder="Kliknij kalendarz" id="start"/>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
	   						<label for="stop" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span></label>
	      						<form:input autocomplete="off" path="stopString" cssClass="datepicker form-control" placeholder="Kliknij kalendarz" id="stop"/>
						</div>
					</div>
					<div class="form-group">
						<label for="comment">Opis:</label>
  						<form:textarea path="description" id="diseaseDescription" rows="5" cols="30" cssClass="form-control"/>
					</div>
					<div class="modal-footer">
						<span hidden="true" id=addDiseaseLoadingSave>
							Zapisywanie...
							<img src="/resources/jpg/loading.gif">
						</span>
                		<button type="button" class="btn btn-default btn-disable" data-dismiss="modal">Anuluj</button>
                		<input type="submit" value="Zapisz" Class="btn btn-danger loading-block btn-disable"/>
            		</div>
           		</form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$('#addDiseaseForm').on('submit', function(e){
	$('#addDiseaseLoadingSave').prop('hidden', false);
	$('.btn-disable').prop('disabled', true);
});
</script>

<script type="text/javascript">
$('#addMedicamentButton').on('click', function(){
	if($('#diseaseEditIdForm').prop('disabled')) {
		$('#noChooseMedicament').show().delay(5000).fadeOut();
	}
	else {
		if ($('#tableAddMedicaments > tbody').children().length == 0) {
			$('#noMedicaments').show().delay(5000).fadeOut();
		}
		else {
			$('#addMedicaments').modal({
				  backdrop: 'static',
				  keyboard: false
				}).show();
		}	
	}
});
</script>


<!-- modal dodawanie leków do choroby -->
<div class="modal fade" id="addMedicaments">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">

                <h4 class="modal-title" id="myModalLabel">Dodaj leki</h4>
            </div>
            <div class="modal-body">
            	<form:form action="addMedicaments.html" method="post" modelAttribute="medicamentForm" id="addMedicamentsForm">
            		<form:hidden path="diseaseId" id="addMedicamentsDiseaseId"/>
            		<table id="tableAddMedicaments" class="table table-bordered table-hover table-striped" width="100%">
            			<thead>
							<tr>
								<td hidden="true">dodaj</td>
								<td>nazwa leku</td>
								<td>opakowanie</td>
								<td>data waznosci</td>
								<td>Producent</td>
						</tr>           			
            			</thead>
            			<tbody>
							<c:forEach items="${medicamentForm.medicaments}" var="medicament" varStatus="status">
								<tr>
									<td hidden="true"><form:checkbox path="ids" value="${medicament.id}"/></td>
									<td>${medicament.name}</td>
									<td>${medicament.kind}</td>
									<td>${medicament.dateExpiration}</td>
									<td>${medicament.producent}</td>
								</tr>
							</c:forEach>
            			</tbody>
            		</table>
					<div class="modal-footer">
						<span hidden="true" id=addMedicamentsLoadingSave>
							Zapisywanie...
							<img src="/resources/jpg/loading.gif">
						</span>
                		<button type="button" class="btn btn-default btn-disable" data-dismiss="modal">Anuluj</button>
                		<input type="submit" value="Dodaj" Class="btn btn-danger btn-disable"/>
            		</div>
           		</form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$('#tableAddMedicaments').on('click', 'tbody > tr', function(){
  	$(this).toggleClass('info');
  	if($(this).find('input').prop("checked")) {
  		$(this).find('input').prop("checked", false);
  	}
  	else {
  		$(this).find('input').prop("checked", true);
  	}
});

$('#addMedicamentsForm').on('submit', function(e){
	if($('#tableAddMedicaments > tbody').children().hasClass('info')) {
		$('#addMedicamentsLoadingSave').prop('hidden', false);
		$('.btn-disable').prop('disabled', true);
	}
	else {
		e.preventDefault();
	}
	
});
</script>

<script type="text/javascript">
$('#deleteButton').on('click', function(){
	if($('#diseaseEditIdForm').prop('disabled')) {
		$('#noChooseMedicament').show().delay(5000).fadeOut();
	}
	else {
		$('#confirmDelete').modal({
		  backdrop: 'static',
		  keyboard: false
		}).show();	
	}
});
</script>

<!-- modal usuwanie choroby -->	
<div class="modal fade" id="confirmDelete">
    <div class="modal-dialog">
         <div class="modal-content">
             <div class="modal-header">
                 <h4 class="modal-title" id="myModalLabel">Potwierdź usunięcie</h4>
             </div>
             <div class="modal-body">
                 <p>Czy chcesz na pewno usunąć chorobę?</p>
                 <p>Operacja jest nieodwracalna?</p>
             </div>
             <div class="modal-footer">
             	<form id="formDelete">
                	<span hidden="false" id="loadingDeleteDisease">
						Usuwanie...
						<img src="/resources/jpg/loading.gif">
					</span>
                	<button type="button" class="btn btn-default" data-dismiss="modal" id="btnCancel">Anuluj</button>
    				<input type="submit" value="Usuń" Class="btn btn-danger btn-disable" id="btnDelete">
				</form>
             </div>
         </div>
     </div>
</div>

<script type="text/javascript">
$('#formDelete').on('submit', function(e){
	$('#loadingDeleteDisease').prop('hidden', false);
	$('.btn-disable').prop('disabled', true);	
});
</script>

<script type="text/javascript">
var diseaseId;
var url;

$('#showMedicamentButton').on('click', function(){
	if($('#diseaseEditIdForm').prop('disabled')) {
		$('#noChooseMedicament').show().delay(5000).fadeOut();
	}
	else {
		url = diseaseId + '/medicaments.json' 
		getMedicamentsForDisease();	
	}
});

function getMedicamentsForDisease(){
	$.ajax({
		url: 'medicaments.json',
		dataType: 'json',
		data:{id:diseaseId},
		success: function(data){
			$.each(data, function(index, element){
				var i = index + 1;
				$('#tableBodyShowMedicamentsInDisease').append("<tr><td hidden=\"true\"><input id=\"ids" + i + "\" name=\"ids\" type=\"checkbox\" value=\"" + element.id + "\"/><input type=\"hidden\" name=\"_ids\" value=\"on\"/></td><td>" + element.name + "</td><td>" + element.kind + "</td><td>" + element.dateExpiration + "</td><td>" + element.producent + "</td></tr>");
			});
			$('#showMedicaments').modal({
				  backdrop: 'static',
				  keyboard: false
				}).show();
		},
		error: function(xhr) {
			$('#noMedicamentsInDisease').show().delay(5000).fadeOut();
		}
	});
}
</script>

<!-- modal show medicaments in disease -->
<div class="modal fade" id="showMedicaments">
    <div class="modal-dialog">
        <div class="modal-content">
        	<form:form action="removeMedicaments.html" method="post" modelAttribute="medicamentRemoveForm" id="removeMedicamentsForm">
	            <div class="modal-header">
	                <h4 class="modal-title" id="myModalLabel">Leki przypisane do choroby</h4>
	            </div>
	            <div class="modal-body">
					<table id="tableShowMedicamentsInDisease" class="table table-bordered table-hover table-striped" width="100%">
	           			<thead>
							<tr>
								<td hidden="true">
								<td>nazwa leku</td>
								<td>opakowanie</td>
								<td>data waznosci</td>
								<td>Producent</td>
						</tr>           			
	           			</thead>
	           			<tbody id="tableBodyShowMedicamentsInDisease">
	           			</tbody>
	           		</table>
	           		<form:hidden path="diseaseId" id="removeMedicamentsDiseaseId"/>
				</div>		
				<div class="modal-footer">
                	<span hidden="false" id=deleteMedicamentsLoadingSave>
						Usuwanie..
						<img src="/resources/jpg/loading.gif">
					</span>
                	<button type="button" class="btn btn-default btn-disable" data-dismiss="modal" id="buttonMedicamentsInDiseaseClose">Anuluj</button>
    				<script type="text/javascript">
	    				$('#buttonMedicamentsInDiseaseClose').on('click', function(){
	    					$('#tableBodyShowMedicamentsInDisease').children('tr').remove();
	    				});
    				</script>
    				<input type="submit" value="Usuń" Class="btn btn-danger btn-disable" id="btnDelete">
    				<a id="addDosage"><button type="button" class="btn btn-default">Dodaj dawkowanie</button></a>
	            </div>
            </form:form>
        </div>
    </div>
</div>

<script type="text/javascript">
$('#tableShowMedicamentsInDisease').on('click', 'tbody > tr', function(){
  	$(this).toggleClass('info');
  	var n = $("input:checkbox:checked").length;
  	console.log("before: "+n);
  	if($(this).find('input').prop("checked")) {
  		$(this).find('input').prop("checked", false);
  	}
  	else {
  		$(this).find('input').prop("checked", true);
  	}
  	
  	console.log();
  	n = $("input:checkbox:checked").length;
  	console.log("after: "+n);
  	if(n==1) {
  		$('#addDosage').attr('href', '/disease/addDosage.html?idd=' + diseaseId + '&idm='+$(this).find("input[type='checkbox']").val());
  	}
  	else {
  		$('#addDosage').removeAttr('href');
  	}
});

$('#removeMedicamentsForm').on('submit', function(e){
	if($('#tableShowMedicamentsInDisease > tbody').children().hasClass('info')) {
		$('#deleteMedicamentsLoadingSave').prop('hidden', false);
		$('.btn-disable').prop('disabled', true);
	}
	else {
		e.preventDefault();
	}
	
});
</script>


<div class="modal fade" id="addFileModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Dodaj plik</h4>
			</div>
			<form:form class="form-horizontal" method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" action="upload.html" id="formAddFile">
				<div class="modal-body">
					<label for="url" class="col-sm-2 control-label">Plik</label>
						<div class="col-sm-10">
						<form:input path="file" type="file" class="btn btn-default btn-file"/>
						</div>
				</div>
				<div class="modal-footer">
					<span hidden="false" id="loadingAddFiles">
						Zapisywanie pliku do bazy...
						<img src="/resources/jpg/loading.gif">
					</span>
					<button type="button" class="btn btn-default btn-disable" data-dismiss="modal">Close</button>
					<input type="submit" value="Upload" Class="btn btn-primary btn-disable">
					<script type="text/javascript">
						$('#formAddFile').on('submit', function(e){
							$('#loadingAddFiles').prop('hidden', false);
							$('.btn-disable').prop('disabled', true);	
							});
					</script>
				</div>
			</form:form>
		</div>
	</div>
</div>

<script type="text/javascript">
$('#addFile').on('click', function(){
	if($('#diseaseEditIdForm').prop('disabled')) {
		$('#noChooseMedicament').show().delay(5000).fadeOut();
	}
	else {
		if ($('#tableAddMedicaments > tbody').children().length == 0) {
			$('#noMedicaments').show().delay(5000).fadeOut();
		}
		else {
			$('#addFileModal').modal({
				  backdrop: 'static',
				  keyboard: false
				}).show();
		}	
	}
});
</script>



<script type="text/javascript">
 $(document).ready(function() {
   	$('#myTable').on('click', 'tbody > tr', function(){
   		if(!$(this).find('td').hasClass('dataTables_empty')) {
	   		$(this).addClass('info');
	    	$(this).siblings().removeClass('info');

	    	$('#formDelete').prop('action', '/disease/remove/' + $(this).find('.rowHiddenId').html() + '.html'); //zostaje
	    	$('#diseaseEditIdForm').val($(this).find('.rowHiddenId').html()); //zostaje
	    	$('#dname').val($(this).children('.disease-name').html());
	    	$('#start').val($(this).children('.disease-start').html());
	    	$('#stop').val($(this).find('.disease-stop').html());
	    	$('#diseaseDescription').val($(this).find('.disease-description').html());
	    	$('#diseaseEditIdForm').prop('disabled', false);  //zostaje
	    	diseaseId = $(this).find('.rowHiddenId').html();
	    	$('#addMedicamentsDiseaseId').val($(this).find('.rowHiddenId').html());
	    	$('#removeMedicamentsDiseaseId').val($(this).find('.rowHiddenId').html());
	    	$('#formAddFile').attr('action', $(this).find('.rowHiddenId').html() + '/upload.html')
   		}
   		});
 });
</script>

<script>
	$(document).ready(function() {

		$('#myTable').dataTable({
			responsive: true,
			"language" : {
				"lengthMenu" : "Wyświetl _MENU_ chorób na strone",
				"zeroRecords" : "Nic nie znaleziono",
				"info" : "Pokazano _PAGE_ z _PAGES_ stron",
				"infoEmpty" : "",
				"infoFiltered" : "",
				"search" : "Szukaj",
				"paginate" : {
					"first" : "Pierwszy",
					"previous" : "Poprzedni",
					"next" : "Następny",
					"last" : "Ostatni"
				}
			}
		});
	});
</script>

<script type="text/javascript">
$(function() {
	$('.datepicker').datepicker({
		format: "yyyy-mm-dd",
		language: "pl",
	    autoclose: true
	});
});


</script>
</c:if>