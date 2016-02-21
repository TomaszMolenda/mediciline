<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>

<%@ include file="../jsp/head.jsp"%>
<style>
.button
{
	margin-right: 20px;
	margin-bottom: 20px;
}

.modal-block {
    display:    none;
    position:   fixed;
    z-index:    1000;
    top:        0;
    left:       0;
    height:     100%;
    width:      100%;
    background: rgba( 255, 255, 255, .8 ) 
                url('http://i.stack.imgur.com/FhHRx.gif') 
                50% 50% 
                no-repeat;
}

body.loading {
    overflow: hidden;   
}

body.loading .modal-block {
    display: block;
}

</style>
</head>
<body>


		<%@ include file="../jsp/sidebar.jsp"%>




			<div class="container">
					<table id="myTable"
						class="table table-bordered table-hover table-striped" width="100%">
						<thead>
							<tr>
								<td>Nazwa</td>
								<td>Rodzaj</td>
								<td>Data ważności</td>
								<td>Producent</td>	
								<td>Cena</td>
								<td hidden="true"></td>							
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${medicaments}" var="medicament">
								<tr>
									<td class="medicament-name">${medicament.medicamentDb.name}</td>
									<td class="medicament-kind">${medicament.medicamentDb.kind}</td>
									<td class="medicament-date">${medicament.dateExpiration}</td>
									<td class="medicament-producent">${medicament.medicamentDb.producent}</td>
									<td class="medicament-price">${medicament.medicamentDb.price}</td>
									<td hidden="true" class="rowHiddenId">${medicament.id}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

			
			<div style="margin-top: 15px;" class="alert alert-danger" id="noChooseMedicament" hidden="true">Musisz wybrać lek!</div>
			<div style="float: left;" class="button">
				<a href="/medicament/add.html" class="btn btn-warning btn-lg" role="button">Dodaj</a>
			</div>
			<div style="float: left;" class="button">
				<button class="btn btn-info btn-lg" id="editButton">Edytuj</button>
			</div>
			<div style="float: left;" class="button">
				<button class="btn btn-danger btn-lg" id="deleteButton">Usuń</button>
			</div>
			<div style="clear: both;"></div>
			<a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a>

</div>
	
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
                 <p class="debug-url"></p>
             </div>
             <div class="modal-footer">
                 <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                 <a class="btn btn-danger btn-ok loading-block">Usuń</a>
             </div>
         </div>
     </div>
</div>
   
<div class="modal fade" id="edit">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Aktualizuj datę</h4>
            </div>
            <div class="modal-body">
            	<table class="table table-bordered text-left" id=table-choosed-medicament>
					<tr><td class="col-md-2">Nazwa</td><td id="medicamentEditNameForm">eee</td></tr>
					<tr><td class="col-md-2">Producent</td><td id="medicamentEditProducentForm"></td></tr>
					<tr><td class="col-md-2">Rodzaj</td><td id="medicamentEditKindForm"></td></tr>
					<tr><td class="col-md-2">Cena</td><td id="medicamentEditPriceForm"></td></tr>
				</table>
            	<form:form action="do.html" method="post" modelAttribute="medicament">
					<form:hidden path="id" id="medicamentEditIdForm"/>
					<div class="form-group" id="form-group-calendar">
						<div class="input-group">
							<label for="expirationDateValue" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span></label>
		   					<form:input path="dateStringExpiration" cssClass="datepicker form-control" placeholder="Kliknij kalendarz" id="expirationDateValue"/>
						</div>
					</div>
					<div class="modal-footer">
                		<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                		<input type="submit" value="Aktualizuj" Class="btn btn-danger loading-block"/>
            		</div>
           		</form:form>
            </div>
        </div>
    </div>
</div>

<div class="modal-block"></div>

<script type="text/javascript">
$(document).ready(function() {
	$('#editButton').click(function(){
		if($('#medicamentEditIdForm').val() == 0)
			{
			$('#noChooseMedicament').show();
			}
		
	});
	$('#deleteButton').click(function(){
		if($('#medicamentEditIdForm').val() == 0)
			{
			$('#noChooseMedicament').show();
			}
		
	});
	$('.loading-block').click(function(){
		$('#confirm-delete').modal('hide');
		$('#edit').modal('hide');
		$('body').addClass('loading');
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
<script type="text/javascript">
// $(function() {
// 	var dangerExist = false;
// 	$('tbody').delegate('tr','mouseover mouseleave', function(e){
// 	    if (e.type == 'mouseover') {
// 	    	if($(this).hasClass('danger'))
// 	    		{
// 	    		dangerExist = true;
// 	    		$(this).removeClass('danger');
// 	    		};
// 	        $(this).addClass('success');
// 	    } else {
// 	    	if(dangerExist == true)
// 	    		{
// 	    		$(this).addClass('danger');
// 	    		dangerExist = false;
// 	    		}
// 	        $(this).removeClass('success');
// 	    }
// 	});
// });
</script>

<script type="text/javascript">
 $(document).ready(function() {
   	$('#myTable').on('click', 'tbody > tr', function(){
   		if($(this).siblings().hasClass('danger-tmp'))
   			{
   			$('.danger-tmp').addClass('danger');
   			$('.danger-tmp').removeClass('danger-tmp');
   			}
   		if($(this).hasClass('danger'))
		{
   			$(this).addClass('danger-tmp');
			$(this).removeClass('danger');
		};
   		$(this).addClass('info');
    	$(this).siblings().removeClass('info');
    	$('#editButton').attr({
			"data-href": "/medicament/edit/" + $(this).children('.rowHiddenId').html() + ".html",
			"data-toggle": "modal",
			"data-target": "#edit"
			
							});
    	$('#deleteButton').attr({
    					"data-href": "/medicament/remove/" + $(this).children('.rowHiddenId').html() + ".html",
    					"data-toggle": "modal",
    					"data-target": "#confirm-delete"
    					
    							});
    	$('#medicamentEditNameForm').html($(this).children('.medicament-name').html());
    	$('#medicamentEditProducentForm').html($(this).children('.medicament-producent').html());
    	$('#medicamentEditKindForm').html($(this).children('.medicament-kind').html());
    	$('#medicamentEditPriceForm').html($(this).children('.medicament-price').html());
    	$('#medicamentEditIdForm').val($(this).children('.rowHiddenId').html());
    	$('#expirationDateValue').val($(this).children('.medicament-date').html());
    	$('#noChooseMedicament').hide();
   		});
 });
</script>


<script>
	$(document).ready(function() {

		$('#myTable').dataTable({
			"language" : {
				"lengthMenu" : "Wyświetl _MENU_ leków na strone",
				"zeroRecords" : "Nic nie znaleziono",
				"info" : "Pokazano _PAGE_ z _PAGES_ stron",
				"infoEmpty" : "No records available",
				"infoFiltered" : "(filtered from _MAX_ total records)",
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
	var table = document.getElementById("myTable");
	var rows = table.getElementsByTagName("tr");
	var date = table.getElementsByTagName("td");
	for (var r = 1, n = table.rows.length; r < n; r++) {
		//for (var c = 0, m = table.rows[r].cells.length; c < m; c++) {
		var date = table.rows[r].cells[2].innerText;
		var dt = new Date(date);
		if (dt < new Date()) {
			rows[r].className = "danger";
		}

	}
</script>

<script>
	$("#menu-toggle").click(function(e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled");
	});
</script>
	
<script>
	$('#confirm-delete').on('show.bs.modal', function(e) {
		$(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
</script>

<script>
	$('#edit').on('show.bs.modal', function(e) {
// 		$(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
</script>
	
</body>
</html>