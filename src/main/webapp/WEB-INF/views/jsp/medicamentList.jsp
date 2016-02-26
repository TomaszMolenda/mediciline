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

.tableRow:hover
{
	cursor: pointer;

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
                url('/resources/jpg/loading.gif') 
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
					<table id="myTable" class="table table-bordered table-hover table-striped" width="100%">
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
				<button class="btn btn-warning btn-lg" data-toggle="modal" data-target="#add" data-backdrop="static" data-keyboard="false">Dodaj</button>
			</div>
			<div style="float: left;" class="button">
				<button class="btn btn-info btn-lg" id="editButton" data-backdrop="static" data-keyboard="false">Edytuj</button>
			</div>
			<div style="float: left;" class="button">
				<button class="btn btn-danger btn-lg" id="deleteButton" data-backdrop="static" data-keyboard="false">Usuń</button>
			</div>
			<div style="clear: both;"></div>

</div>
<!-- modal add -->
<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Dodaj lek</h4>
            </div>
            <div class="modal-body">
				<form:form method="POST" modelAttribute="medicament" action="add.html" id="addForm">
					<form:label path="name">Wpisz nazwę leku i kliknij w lupkę (min 3 znaki)</form:label>
					<div class="form-group error-add-medicament" id="idMedicamentSearchFormGroup">
						<div class="input-group">
							<span class="input-group-addon" id="searchButt">
								<span class="glyphicon glyphicon-search"></span>
	      					</span>					
							<form:input path="name" id="search" type="search" name="search" placeholder="Wpisz nazwę leku (min 3 znaki)" class="form-control"/>
							<form:errors path="name" cssClass="form-error" />
						</div>
						<form:hidden path="idMedicamentDb" id="hiddenId" />
						<form:errors path="idMedicamentDb" cssClass="form-error text-danger" />
						<span id="errorNoChooseMedicament" hidden="true" class="errorAddMedicament">Nie wybrałeś leku</span>
						<span id="erronNoFoundMedicament" hidden="true" class="errorAddMedicament">Nic nie znaleziono</span>
						<span id="erronNoThreeSign" hidden="true" class="errorAddMedicament">Wpisz minimum 3 znaki</span>
					<div style="margin-top: 10px;" hidden="true" id="bar-loader"><img alt="" src="/resources/jpg/bar-loader.gif"></div>
					</div>
		        	<table class="table table-striped" id="table" hidden="true"></table>
		        	<div class="error-add-medicament" id="idMedicamentDateFormGroup">
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
					<div class="modal-footer">
                		<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                		<input type="submit" value="Dodaj lek" Class="btn btn-danger" id="button-submit"/>
            		</div>
				</form:form>
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
   
<!-- modal edit -->   
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
            	<form:form action="edit.html" method="post" modelAttribute="medicament">
					<form:hidden path="id" id="medicamentEditIdForm"/>
					<div class="form-group">
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


<!-- validate addForm -->
<script type="text/javascript">

$(function(){
	$('#addForm').on('submit', function(e){
		var isValid = false;
		$('.errorAddMedicament').attr('hidden',true).removeClass('help-block');
		$('#erronWrongDate').attr('hidden',true).removeClass('help-block');
		$('.error-add-medicament').removeClass('has-error');
		if($('#hiddenId').val() == '')
			{
				$('#idMedicamentSearchFormGroup').addClass('has-error');
				$('#errorNoChooseMedicament').attr('hidden',false).addClass('help-block');
			}
		if($('#valueYear').val() == '' | $('#valueMonth').val() == '')
		{
			$('#idMedicamentDateFormGroup').addClass('has-error');
			$('#erronWrongDate').attr('hidden',false).addClass('help-block');
		}
		//erronWrongDate
		if($('#hiddenId').val() == '' | $('#valueYear').val() == '' | $('#valueMonth').val() == '')
			{
			e.preventDefault();
			}
		else
			{
			$('#add').modal('hide');
			$('body').addClass('loading');
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


<!-- create edit and delete form data by clicking rows table -->
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

<!-- datatable -->
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

<!-- color medicament row with wrong date -->
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



<script type="text/javascript">
var expirationDate = '';
 $(document).ready(function() {
    	$('#table').on('click', '.tableRow', function(){
     		$('#hiddenId').val($(this).children('.medicament-list-id').html());
     		$(this).addClass('info');
     		$(this).siblings().removeClass('info');
     		});
 });
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
    	$('.error-add-medicament').removeClass('has-error');
    	$('.tableRow').remove();
    	$('.table-row-header').remove();
    	$("[for=hiddenId]").remove();
    	$('#hiddenId').val('');
    	var addedTableTitle = false;
 		if($('#search').val().length >= 3)
 		{
 			$('#bar-loader').attr('hidden', false);
	    	$.getJSON("medicaments-db.json", function(result)
	    			{
	    			$.each(result, function(i, medicament)
	    					{
	    						if(medicament.name.toLowerCase().indexOf($('#search').val().toLowerCase()) >= 0)
	    						{
	    							if(i >= 0 & addedTableTitle == false)
	    							{
	    								addedTableTitle = true;
	    								$('#table').append("<tr class=\"table-row-header\"><th>Nazwa</th><th>Producent</th><th>Opakowanie</th><th>Cena</th></tr>");
	    							}
	    							$('#idMedicamentSearchFormGroup').removeClass('has-error');
	    							$('#table').append("<tr class=\"tableRow\"><td class=\"medicament-list-name\">" + medicament.name + 
										    							 "</td><td class=\"medicament-list-id\" hidden=\"true\">" + medicament.id + 
										    							 "</td><td class=\"medicament-list-producent\">" + medicament.producent + 
										    							 "</td><td class=\"medicament-list-kind\">" + medicament.kind + 
										    							 "</td><td class=\"medicament-list-price\">" + medicament.price + "</td></tr>");
	    							return;
	    						}
	    					});
	    			$('#bar-loader').attr('hidden', true);
	    			if(addedTableTitle == false)
	     			{
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
	var i = 0;
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
			$('#valueMonth').val(months[i]);
			$('#idMonth').val(i+1);
		}
		else
		{
			i++;
			if(i >= months.length) i = 0;
			$('#valueMonth').val(months[i]);
			$('#idMonth').val(i+1);
		}
		
	});
	
	$('#downMonth').click(function(){
	if($('#valueMonth').val() == '')
	{
		i = 11;
		$('#valueMonth').val(months[i]);
		$('#idMonth').val(i+1);
	}
	else
	{
		i--;
		if(i == -1) i = months.length - 1;
		$('#valueMonth').val(months[i]);
		$('#idMonth').val(i+1);
	}	
	});
	});

</script>
	
<!-- show modal -->
<script>
	$('#confirm-delete').on('show.bs.modal', function(e) {
		$(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
	$('#edit').on('show.bs.modal', function(e) {
	});
	$('#add').on('show.bs.modal', function(e) {
	});
</script>

	
</body>
</html>