<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

			<div class="container">
			
					<table id="myTable" class="table table-striped table-bordered dt-responsive nowrap" width="100%">
						<thead>
							<tr>
								<th></th>
								<th>Nazwa</th>
								<th>Rodzaj</th>
								<th>Data ważności</th>
								<th>Producent</th>	
								<th>Cena</th>						
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${medicaments}" var="medicament">
								<tr>
									<td></td>
									<td class="medicament-name">${medicament.name}</td>
									<td class="medicament-kind">${medicament.kind}</td>
									<td>${medicament.dateExpirationYearMonth.monthAndYear}
										<p hidden="true" class="medicament-date-year">${medicament.dateExpirationYearMonth.year}</p>
										<p hidden="true" class="medicament-date-month">${medicament.dateExpirationYearMonth.month}</p>
										<p hidden="true" class="medicament-date-month-id">${medicament.dateExpirationYearMonth.monthId}</p>
									</td>
									<td class="medicament-producent">${medicament.producent}</td>
									<td>
										<span class="medicament-price">${medicament.price}</span>
										<span hidden="true" class="rowHiddenId">${medicament.id}</span>
									</td>
									
								</tr>
							</c:forEach>
						</tbody>
					</table>

			
			<div style="margin-top: 15px;" class="alert alert-danger" id="noChooseMedicament" hidden="true">Musisz wybrać lek!</div>

			<div style="float: left;" class="button">
				<button class="btn btn-warning btn-lg" id="addButton" data-toggle="modal" data-target="#add" data-backdrop="static" data-keyboard="false">Dodaj</button>
			</div>
			<div style="float: left;" class="button">
				<button class="btn btn-info btn-lg" id="editButton" data-backdrop="static" data-keyboard="false">Edytuj</button>
			</div>
			<div style="float: left;" class="button">
				<button class="btn btn-danger btn-lg" id="deleteButton" data-backdrop="static" data-keyboard="false">Usuń</button>
			</div>
			<div style="clear: both;"></div>




<!-- modal add -->
<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Dodaj lek</h4>
            </div>
            <div class="modal-body">
				<form:form method="POST" modelAttribute="medicament" action="change.html" id="addForm">
					<input id="medicamentEditIdForm" name="id" type="hidden" value="0" disabled="true"/>
					<div id="showIfAddHideIfEdit">
						<form:label path="name">Wpisz nazwę leku i kliknij w lupkę (min 3 znaki)</form:label>
						<div class="form-group" id="idMedicamentSearchFormGroup">
							<div class="input-group">
								<span class="input-group-addon" id="searchButt">
									<span class="glyphicon glyphicon-search"></span>
		      					</span>					
								<form:input path="search" id="search" type="search" name="search" placeholder="Wpisz nazwę leku (min 3 znaki)" class="form-control"/>
							</div>
							<span><form:errors path="search" cssClass="form-error" /></span>	
							<span id="errorNoChooseMedicament" hidden="true" class="errorAddMedicament">Nie wybrałeś leku</span>
							<span id="erronNoFoundMedicament" hidden="true" class="errorAddMedicament">Nic nie znaleziono</span>
							<span id="erronNoThreeSign" hidden="true" class="errorAddMedicament">Wpisz minimum 3 znaki</span>
						</div>
		        	</div>
		        	<div id="idMedicamentDateFormGroup" style="float: left;">
						<div style="float: left;"><img src="/resources/jpg/up.png" id="upYear"/></div>
						<div style="float: left;"><img src="/resources/jpg/up.png" id="upMonth"/></div>
						<div style="clear: both;"></div>
						
						<div style="float: left; width: 60px; text-align:center;">
							<form:input path="dateExpirationYearMonth.year" style="text-align:center; border-style: none;" id="valueYear" readonly="readonly" size="2" type="text"/>
						</div>
						<div style="float: left;">
							<form:input path="dateExpirationYearMonth.month" style="text-align:center; border-style: none;" id="valueMonth" readonly="readonly" size="6" type="text"/>
						</div>
						<form:hidden path="dateExpirationYearMonth.monthId" id="idMonth"/>
						<div style="clear: both;"></div>
						<div style="float: left;"><img src="/resources/jpg/down.png" id="downYear"/></div>
						<div style="float: left;"><img src="/resources/jpg/down.png" id="downMonth"/></div>
						<div style="clear: both;"></div>
						<span id="erronWrongDate" hidden="true">Błędna data</span>
						<form:errors path="dateExpirationYearMonth.year" cssClass="form-error text-danger" />
					</div>
					<div style="float: left;" class="has-error">
						<table class="table table-bordered text-left">
						<tr>
						<td class="col-md-2">Nazwa</th>
						<td class="col-md-4"><form:input path="name" cssStyle="border: none;outline: none;" id="inputName"/></th>
						</tr>
						<tr>
						<td class="col-md-2">Producent</td>
						<td class="col-md-4"><form:input path="producent" cssStyle="border: none;outline: none;" id="inputProducent"/></td>
						</tr>
						<tr>
						<td class="col-md-2">Rodzaj</td>
						<td class="col-md-4"><form:input path="kind" cssStyle="border: none;outline: none;" id="inputKind"/></td>
						</tr>
						<tr>
						<td class="col-md-2">Cena</td>
						<td class="col-md-4"><form:input path="price" cssStyle="border: none;outline: none;" id="inputPrice"/></td>
						</tr>
						</table>
						<span id="erronNoMedicament" hidden="true" class="errorAddMedicament">Nie wybrałeś leku</span>
					</div>
					<div style="clear: both;"></div>
					<div class="modal-footer">
                		<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                		<input type="submit" value="Dodaj lek" Class="btn btn-danger" id="button-submit"/>
            		</div>
				</form:form>
            </div>
        </div>
    </div>
</div>

<!-- modal search medicament -->
<div class="modal fade" id="searchMedicament">
     <div class="modal-dialog">
         <div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                 <h4 class="modal-title">Wybierz lek</h4>
             </div>
             <div class="modal-body">
                 <table class="table table-striped" id="table" hidden="true"></table>
             </div>
             <div class="modal-footer">
                 <button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                 <a class="btn btn-danger" id="chosenMedicament">Wybierz</a>
             </div>
         </div>
     </div>
</div>

<script type="text/javascript">
var expirationDate = '';
var name;
var kind;
var producent;
var price;
$(function(){
	$('#chosenMedicament').on('click', function(){
		$('#searchMedicament').modal('hide');
		$('#inputName').val(name);
		$('#inputProducent').val(producent);
		$('#inputKind').val(kind);
		$('#inputPrice').val(price);
		});
})
    $('#table').on('click', '.tableRow', function(){
   		$(this).addClass('info');
   		$(this).siblings().removeClass('info');
   		name = $(this).children('.medicament-list-name').html();
   		kind = $(this).children('.medicament-list-kind').html();
   		producent = $(this).children('.medicament-list-producent').html();
   		price = $(this).children('.medicament-list-price').html();
   		});

</script>

<!-- modal loading -->
<div class="modal fade modal-loading" id="loading">
     <div class="modal-dialog">
         <div class="modal-content">
             
             <div class="modal-body">
                 <div class="progress progress-striped active" style="margin-bottom:0;">
                 	<div class="progress-bar" style="width: 100%;"></div>
                 </div>
             </div>
             
         </div>
     </div>
</div>

<!-- modal delete -->
<div class="modal fade" id="confirm-delete">
     <div class="modal-dialog">
         <div class="modal-content">
             <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                 <h4 class="modal-title">Potwierdź usunięcie</h4>
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
  


        <div id="modaltomo">
         <img id="loadertomo" src="/resources/jpg/loading.gif" />
</div>

</div>

<script type="text/javascript">
$(function(){
	var monthId = 0;
})



</script>

<!-- validate addForm -->
<script type="text/javascript">

$(function(){
	$('#addForm').on('submit', function(e){
		var isValid = false;
		$('.errorAddMedicament').attr('hidden',true).removeClass('help-block');
		$('#erronWrongDate').attr('hidden',true).removeClass('help-block');
		$('#idMedicamentDateFormGroup').removeClass('has-error');
		if($('#inputName').val() == '')
			{
				$('#erronNoMedicament').attr('hidden',false).addClass('help-block');
			}
		if($('#valueYear').val() == '' | $('#valueMonth').val() == '')
		{
			$('#idMedicamentDateFormGroup').addClass('has-error');
			$('#erronWrongDate').attr('hidden',false).addClass('help-block');
		}
		if($('#inputName').val() == '' | $('#valueYear').val() == '' | $('#valueMonth').val() == '')
			{
			e.preventDefault();
			}
		else
			{
			$('#loading').modal({ 
 				show: true,			
 				backdrop: 'static',
 				keyboard: true
 				});
			}
		
		
	});
	
	
});

</script>

<!-- no choose medicament and show loading when edit or delete -->
<script type="text/javascript">
$(document).ready(function() {
	$('#editButton').click(function(){
		if($('#medicamentEditIdForm').val() == 0)
			{
			$('#noChooseMedicament').show();
			}
		$('#showIfAddHideIfEdit').hide();
		$('#medicamentEditIdForm').prop('disabled', false);
		
	});
	$('#deleteButton').click(function(){
		if($('#medicamentEditIdForm').val() == 0)
			{
			$('#noChooseMedicament').show();
			}
	});
	$('#addButton').click(function(){
		$('#showIfAddHideIfEdit').show();
		$('#medicamentEditIdForm').val('');
		$('#medicamentEditIdForm').prop('disabled', true);
    	$('#inputName').val('');
    	$('#inputProducent').val('');
    	$('#inputKind').val('');
    	$('#inputPrice').val();
    	$('#valueYear').val('');
    	$('#valueMonth').val('');
    	$('#idMonth').val('');
		
	});
	$('.loading-block').click(function(){
		$('#loading').modal({ 
				show: true,			
				backdrop: 'static',
				keyboard: true
				});
	});
	
});

</script>


<!-- create edit and delete form data by clicking rows table -->
<script type="text/javascript">
 $(document).ready(function() {
   	$('#myTable').on('click', 'tbody > tr[role=row]', function(){
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
			"data-href": "/medicament/edit/" + $(this).find('.rowHiddenId').html() + ".html",
			"data-toggle": "modal",
			"data-target": "#add"
			
							});
    	$('#deleteButton').attr({
    					"data-href": "/medicament/remove/" + $(this).find('.rowHiddenId').html() + ".html",
    					"data-toggle": "modal",
    					"data-target": "#confirm-delete"
    					
    							});
    	$('#medicamentEditIdForm').val($(this).find('.rowHiddenId').html());
    	$('#inputName').val($(this).children('.medicament-name').html());
    	$('#inputProducent').val($(this).children('.medicament-producent').html());
    	$('#inputKind').val($(this).children('.medicament-kind').html());
    	$('#inputPrice').val($(this).find('.medicament-price').html());
    	$('#valueYear').val($(this).find('.medicament-date-year').html());
    	$('#valueMonth').val($(this).find('.medicament-date-month').html());
    	$('#idMonth').val($(this).find('.medicament-date-month-id').html());
    	monthId = $('#idMonth').val();
    	$('#noChooseMedicament').hide();
   		
   		});
   	
 });
</script>

<!-- datatable -->
<script>
	$(document).ready(function() {

		$('#myTable').dataTable({
			
	        columnDefs: [ {
	            className: 'control',
	            orderable: false,
	            targets:   0
	        } ],
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

<!-- color medicament row with wrong date -->
<script type="text/javascript">
	var table = document.getElementById("myTable");
	var rows = table.getElementsByTagName("tr");
	var date = table.getElementsByTagName("td");
	for (var r = 1, n = table.rows.length; r < n; r++) {
		var date = table.rows[r].cells[2].innerText;
		var dt = new Date(date);
		if (dt < new Date()) {
			rows[r].className = "danger";
		}

	}
</script>

<!-- color searches medicament when moseover -->
<script type="text/javascript">
$(function() {
	$('#table').delegate('.tableRow','mouseover mouseleave', function(e){
	    if (e.type == 'mouseover') {
	        $(this).addClass('success');
	    } else {
	        $(this).removeClass('success');
	    }
	});
});
</script>


<!-- search medicament in add form -->
<script type="text/javascript">
$(function() {
    $('#searchButt').click(function() {
    	$('.errorAddMedicament').attr('hidden',true).removeClass('help-block');
    	$('.tableRow').remove();
    	$('.table-row-header').remove();
    	$("[for=hiddenId]").remove();
    	//$('#hiddenId').val('');
    	var addedTableTitle = false;
 		if($('#search').val().length >= 3)
 		{
	    	$.getJSON("medicaments-db.json", function(result)
	    			{
	    			$.each(result, function(i, medicament)
	    					{
	    						if(medicament.ProductName != null)
	    							{

		    						if(medicament.ProductName.toLowerCase().indexOf($('#search').val().toLowerCase()) >= 0)
		    						{
		    							if(i >= 0 & addedTableTitle == false)
		    							{
		    								addedTableTitle = true;
		    								$('#table').append("<tr class=\"table-row-header\"><th>Nazwa</th><th>Producent</th><th>Opakowanie</th><th>Cena</th></tr>");
		    							}
		    							$('#idMedicamentSearchFormGroup').removeClass('has-error');
		    							$('#table').append("<tr class=\"tableRow\"><td class=\"medicament-list-name\">" + medicament.ProductName + 
											    							 "</td><td class=\"medicament-list-id\" hidden=\"true\">" + medicament.PackageID + 
											    							 "</td><td class=\"medicament-list-producent\">" + medicament.Producer + 
											    							 "</td><td class=\"medicament-list-kind\">" + medicament.Pack + 
											    							 "</td><td class=\"medicament-list-price\">" + medicament.Price + "</td></tr>");

		    							$('#searchMedicament').modal('show');
		    							return;
		    						}
	    							}
	    					});

	    			if(addedTableTitle == false)
	     			{
	    				$('#loading').modal('hide');
	    				$('#erronNoFoundMedicament').attr('hidden',false).addClass('help-block');
	     				$('#idMedicamentSearchFormGroup').addClass('has-error');
	     			}
	    			});
 		}
 		else
 			{
 			$('#erronNoThreeSign').attr('hidden',false).addClass('help-block');
 			$('#idMedicamentSearchFormGroup').addClass('has-error');

 			}
 		$('#table').show();
    });
})
</script>

<!-- block ESC key -->
<script type="text/javascript">
$(document).ready(function() {
	  $(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	});
</script>


<!-- to choose year and month -->
<script type="text/javascript">

$(function() {
	var months = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"];
	
	
	$('#valueYear').val('');
	$('#upYear').click(function(){
		if($('#valueYear').val() == '') $('#valueYear').val(2016);
		else
		{
			var year = $('#valueYear').val();
			year++;
			$('#valueYear').val(year);
		}
	});
	$('#downYear').click(function(){
		if($('#valueYear').val() == '') $('#valueYear').val(2016);
		else
		{
			var year = $('#valueYear').val();
			year--;
			$('#valueYear').val(year);
		}
	});
	$('#upMonth').click(function(){
		if($('#valueMonth').val() == '')
		{
			monthId = 0
			$('#valueMonth').val(months[monthId]);
			$('#idMonth').val(monthId+1);
		}
		else
		{
			monthId++;
			if(monthId >= months.length) monthId = 0;
			$('#valueMonth').val(months[monthId]);
			$('#idMonth').val(monthId+1);
		}
		
	});
	
	$('#downMonth').click(function(){
	if($('#valueMonth').val() == '')
	{
		monthId = 11;
		$('#valueMonth').val(months[monthId]);
		$('#idMonth').val(monthId+1);
	}
	else
	{
		monthId--;
		if(monthId == -1) i = months.length - 1;
		$('#valueMonth').val(months[monthId]);
		$('#idMonth').val(monthId+1);
	}	
	});
	});

</script>
	
<!-- show modal -->
<script>
	$('#confirm-delete').on('show.bs.modal', function(e) {
		$(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
</script>