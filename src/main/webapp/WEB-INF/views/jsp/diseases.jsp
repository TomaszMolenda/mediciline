<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<div class="container">
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
						<td><span class="disease-stop">${disease.stop}</span><span class="rowHiddenId" hidden=true>${disease.id}</span></td>
						

<!-- 						<td class="hide_me"> -->
<!-- 							<table id="tableShowMedicamentsInDisease" class="table table-bordered table-hover table-striped" width="100%"> -->
<!-- 			           			<thead> -->
<!-- 									<tr> -->
<!-- 										<td>nazwa leku</td> -->
<!-- 										<td>opakowanie</td> -->
<!-- 										<td>data waznosci</td> -->
<!-- 										<td>Producent</td> -->
<!-- 								</tr>           			 -->
<!-- 			           			</thead> -->
<!-- 			           			<tbody class="mainTableBodyShowMedicamentsInDisease"> -->
<%-- 									<c:forEach items="${disease.medicaments}" var="medicament"> --%>
<!-- 										<tr class="mainTableRowShowMedicamentsInDisease"> -->
<%-- 											<td>${medicament.medicamentDb.name}</td> --%>
<%-- 											<td>${medicament.medicamentDb.kind}</td> --%>
<%-- 											<td>${medicament.dateExpiration}</td> --%>
<%-- 											<td>${medicament.medicamentDb.producent}</td> --%>
<!-- 										</tr> -->
<%-- 									</c:forEach> --%>
<!-- 			           			</tbody> -->
<!-- 	           				</table> -->
<!-- 						</td> -->
					</tr>

	
				</c:forEach>
			</tbody>
		</table>
		<div style="margin-top: 15px;" class="alert alert-danger" id="noChooseMedicament" hidden="true">Musisz wybrać chorobę!</div>
		<div style="float: left;" class="button">
			<button class="btn btn-warning btn-lg" id="addButton" data-toggle="modal" data-target="#addModal" data-backdrop="static" data-keyboard="false">Dodaj</button>
		</div>
		<div style="float: left;" class="button">
			<button class="btn btn-info btn-lg" id="editButton" data-backdrop="static" data-keyboard="false">Edytuj</button>
		</div>
		<div style="float: left;" class="button">
			<button class="btn btn-danger btn-lg" id="deleteButton" data-backdrop="static" data-keyboard="false">Usuń</button>
		</div>
		<div style="float: left;" class="button">
			<button class="btn btn-info btn-lg" id="addMedicamentButton" data-backdrop="static" data-keyboard="false">Dodaj leki</button>
		</div>
		<div style="float: left;" class="button">
			<button class="btn btn-info btn-lg" id="showMedicamentButton" data-backdrop="static" data-keyboard="false">Pokaż leki</button>
		</div>
		<div style="clear: both;"></div>
		
		
	</div>

<!-- modal dodawanie choroby -->
<div class="modal fade" id="addModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Dodaj chorobę</h4>
            </div>
            <div class="modal-body">
            	<form:form action="change.html" method="post" modelAttribute="disease">
            		<form:hidden path="id" id="diseaseEditIdForm" disabled="true"/>
					<div class="form-group">
						<form:label path="name">Choroba</form:label>
						<form:input path="name" id="dname" cssClass="form-control"/>
					</div>
					<div class="form-group">
						<div class="input-group">
	   						<label for="start" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span></label>
	      						<form:input path="startString" cssClass="datepicker form-control" placeholder="Kliknij kalendarz" id="start"/>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
	   						<label for="stop" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span></label>
	      						<form:input path="stopString" cssClass="datepicker form-control" placeholder="Kliknij kalendarz" id="stop"/>
						</div>
					</div>
					<div class="form-group">
					
					</div>
					<div class="modal-footer">
                		<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                		<input type="submit" value="Dodaj" Class="btn btn-danger loading-block"/>
            		</div>
           		</form:form>
            </div>
        </div>
    </div>
</div>

<!-- modal dodawanie leków do choroby -->
<div class="modal fade" id="addMedicaments">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Dodaj leki</h4>
            </div>
            <div class="modal-body">
            	<form:form action="addMedicaments.html" method="post" modelAttribute="medicamentForm">
            		<form:hidden path="diseaseId" id="addMedicamentsDiseaseId"/>
            		<table id="tableAddMedicaments" class="table table-bordered table-hover table-striped" width="100%">
            			<thead>
							<tr>
								<td>dodaj</td>
								<td>nazwa leku</td>
								<td>opakowanie</td>
								<td>data waznosci</td>
								<td>Producent</td>
						</tr>           			
            			</thead>
            			<tbody>
							<c:forEach items="${medicamentForm.medicaments}" var="medicament" varStatus="status">
								<tr>
									<td><form:checkbox path="ids" value="${medicament.id}"/></td>
									<td>${medicament.name}</td>
									<td>${medicament.kind}</td>
									<td>${medicament.dateExpiration}</td>
									<td>${medicament.producent}</td>
								</tr>
							</c:forEach>
            			</tbody>
            		
            		</table>
					
					
					
					<div class="modal-footer">
                		<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                		<input type="submit" value="Dodaj" Class="btn btn-danger loading-block"/>
            		</div>
           		</form:form>
            </div>
        </div>
    </div>
</div>

<!-- modal usuwanie choroby -->	
<div class="modal fade" id="confirm-delete">
    <div class="modal-dialog">
         <div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                 <h4 class="modal-title" id="myModalLabel">Potwierdź usunięcie</h4>
             </div>
             <div class="modal-body">
                 <p>Czy chcesz na pewno usunąć lek?</p>
                 <p>Operacja jest nieodwracalna?</p>
             </div>
             <div class="modal-footer">
                 <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                 <a class="btn btn-danger btn-ok loading-block">Usuń</a>
             </div>
         </div>
     </div>
</div>

<!-- modal show medicaments in disease -->
<div class="modal fade" id="showMedicaments">
    <div class="modal-dialog">
        <div class="modal-content">
        	<form:form action="removeMedicaments.html" method="post" modelAttribute="medicamentRemoveForm">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">Leki przypisane do choroby</h4>
	            </div>
	            <div class="modal-body">
					<table id="tableShowMedicamentsInDisease" class="table table-bordered table-hover table-striped" width="100%">
	           			<thead>
							<tr>
								<td>
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
					<input type="submit" value="Usuń" Class="btn btn-danger loading-block"/>
		       		<button type="button" class="btn btn-default" data-dismiss="modal" id="buttonMedicamentsInDiseaseClose">Zamknij</button>
	            </div>
            </form:form>
        </div>
    </div>
</div>

<div class="modal-block"></div>

<script type="text/javascript">

var diseaseId;
var url;
$(function(){
	$('#showMedicamentButton').on('click', function(){
		console.log(diseaseId);
		url = diseaseId + '/medicaments.json' 
		getMedicamentsForDisease();
	});
	
	$('#buttonMedicamentsInDiseaseClose').on('click', function(){
		$('#tableBodyShowMedicamentsInDisease').children('tr').remove();
		console.log('exit');
	});
})

function getMedicamentsForDisease(){
	$.ajax({
		url: url,
		dataType: 'json',
		success: function(data){
			$.each(data, function(index, element){
				console.log(index);
				var i = index + 1;
				$('#tableBodyShowMedicamentsInDisease').append("<tr><td><input id=\"ids" + i + "\" name=\"ids\" type=\"checkbox\" value=\"" + element.id + "\"/><input type=\"hidden\" name=\"_ids\" value=\"on\"/></td><td>" + element.name + "</td><td>" + element.kind + "</td><td>" + element.dateExpiration + "</td><td>" + element.producent + "</td></tr>");
			});
			$('#showMedicaments').modal('show');
		}
		
	});
}
</script>

<script type="text/javascript">
 $(document).ready(function() {
   	$('#myTable').on('click', 'tbody > tr', function(){
   		$(this).addClass('info');
    	$(this).siblings().removeClass('info');
    	
    	$('#editButton').attr({
			"data-toggle": "modal",
			"data-target": "#addModal"
			
							});
    	$('#deleteButton').attr({
    					"data-href": "/disease/remove/" + $(this).find('.rowHiddenId').html() + ".html",
    					"data-toggle": "modal",
    					"data-target": "#confirm-delete"
    					
    							});
    	$('#addMedicamentButton').attr({
			"data-toggle": "modal",
			"data-target": "#addMedicaments"
							});
    	$('#showMedicamentButton').attr({
			"data-toggle": "modal",
			"data-target": "#showMedicaments"
							});
    	$('#diseaseEditIdForm').val($(this).find('.rowHiddenId').html());
    	$('#dname').val($(this).children('.disease-name').html());
    	$('#start').val($(this).children('.disease-start').html());
    	$('#stop').val($(this).find('.disease-stop').html());
    	$('#diseaseEditIdForm').prop('disabled', false);
    	diseaseId = $(this).find('.rowHiddenId').html();
    	$('#addMedicamentsDiseaseId').val($(this).find('.rowHiddenId').html());
    	$('#removeMedicamentsDiseaseId').val($(this).find('.rowHiddenId').html());
   		});
 });
</script>

<script>
	$(document).ready(function() {

		$('#myTable').dataTable({
			responsive: true,
			"language" : {
				"lengthMenu" : "Wyświetl _MENU_ leków na strone",
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


<script>
	$('#confirm-delete').on('show.bs.modal', function(e) {
		$(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
	$('#addModal').on('show.bs.modal', function(e) {
	});
	$('#addMedicaments').on('show.bs.modal', function(e) {
	});
	$('#showMedicaments').on('show.bs.modal', function(e) {
		$('#tableBodyShowMedicamentsInDisease').children('.mainTableRowShowMedicamentsInDisease').remove();
		$('#tableBodyShowMedicamentsInDisease').append($('.info').find('.mainTableBodyShowMedicamentsInDisease').html());
	});
</script>


<script type="text/javascript">
$(function() {
	$('#addButton').click(function(){
    	$('#dname').val('');
    	$('#start').val('');
    	$('#stop').val('');
    	$('.info').removeClass('info');
    	$('#editButton').attr('data-target', '');
    	$('#deleteButton').attr('data-target', '');
    	$('#addMedicamentButton').attr('data-target', '');
    	$('#showMedicamentButton').attr('data-target', '');
    	$('#diseaseEditIdForm').prop('disabled', true);
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