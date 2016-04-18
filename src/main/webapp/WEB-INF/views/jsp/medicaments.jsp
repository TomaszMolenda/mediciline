<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<span id="sessionDB" hidden="true">${sessionDB}</span>
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
						<span hidden="true" class="productLineID">${medicament.productLineID}</span>
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>

			
			<div style="margin-top: 15px;" class="alert alert-danger" id="noChooseMedicament" hidden="true">Musisz wybrać lek!</div>
			<div style="margin-top: 15px;" class="alert alert-danger" id="noProductLineID" hidden="true">Brak informacji dodatkowych! Lek był edytowany</div>
			<div style="float: left;" class="button">
				<button class="btn btn-warning btn-lg" id="addButton" data-toggle="modal" data-target="#add" data-backdrop="static" data-keyboard="false">Dodaj</button>
			</div>
			<div style="float: left;" class="button">
				<button class="btn btn-info btn-lg" id="editButton" data-backdrop="static" data-keyboard="false">Edytuj</button>
			</div>
			<div style="float: left;" class="button">
				<button class="btn btn-danger btn-lg" id="deleteButton" data-backdrop="static" data-keyboard="false">Usuń</button>
			</div>
			<div style="float: left;" class="button">
				<button class="btn btn-danger btn-lg" id="infoButton" data-backdrop="static" data-keyboard="false">Info</button>
			</div>
			<div style="clear: both;"></div>


</div>

<!-- modal add -->
<div class="modal fade" id="add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><span id="addTitle"></span></h4>
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
								<form:input autocomplete="off" path="search" id="search" type="search" name="search" placeholder="Wpisz nazwę leku (min 3 znaki)" class="form-control"/>
							</div>
							<span hidden="true" id=loadingSearch>
							Szukam...
							<img src="/resources/jpg/loading.gif">
							</span>
							<span><form:errors path="search" cssClass="form-error" /></span>	
							<span id="errorNoChooseMedicament" hidden="true" class="errorAddMedicament">Nie wybrałeś leku</span>
							<span id="erronNoFoundMedicament" hidden="true" class="errorAddMedicament">Nic nie znaleziono</span>
							<span id="erronNoThreeSign" hidden="true" class="errorAddMedicament">Wpisz minimum 3 znaki</span>
						</div>
		        	</div>
		        	<div style="float: left;">
		        	Wybierz datę:
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
						<span class="button-checkbox">
        					<button type="button" class="btn" data-color="primary">Edytuj</button>
        						<input type="checkbox" class="hidden" />
    					</span>
    					<span>
    						<img src="/resources/jpg/info.jpg" width="40" height="40" data-toggle="tooltip" title="Edytując stracisz dodatkowe informacje, np ulotkę" data-placement="right">
    					</span>
						<form:hidden path="productLineID" id="productLineID"/>
						<table class="table table-bordered text-left">
						<tr>
						<td class="col-md-2">Nazwa</th>
						<td class="col-md-4"><form:input autocomplete="off" path="name" cssStyle="border: none;outline: none;" id="inputName" readonly="true" cssClass="read-only-field"/></th>
						</tr>
						<tr>
						<td class="col-md-2">Producent</td>
						<td class="col-md-4"><form:input autocomplete="off" path="producent" cssStyle="border: none;outline: none;" id="inputProducent" readonly="true" cssClass="read-only-field"/></td>
						</tr>
						<tr>
						<td class="col-md-2">Rodzaj</td>
						<td class="col-md-4"><form:input autocomplete="off" path="kind" cssStyle="border: none;outline: none;" id="inputKind" readonly="true" cssClass="read-only-field"/></td>
						</tr>
						<tr>
						<td class="col-md-2">Cena</td>
						<td class="col-md-4"><form:input autocomplete="off" path="price" cssStyle="border: none;outline: none;" id="inputPrice"/></td>
						</tr>
						</table>
						<span id="erronNoMedicament" hidden="true" class="errorAddMedicament">Nie wybrałeś leku</span>
					</div>
					<div style="clear: both;"></div>
					<div class="modal-footer">
						<span hidden="true" id=loadingSave>
							Zapisywanie...
							<img src="/resources/jpg/loading.gif">
						</span>
                		<button type="button" class="btn btn-default btn-disable" data-dismiss="modal">Anuluj</button>
                		<input type="submit" value="Zapisz" Class="btn btn-danger btn-disable" id="button-submit"/>
            		</div>
				</form:form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$(function () {
	$('[data-toggle="tooltip"]').tooltip();
})

//http://bootsnipp.com/snippets/featured/jquery-checkbox-buttons
$(function () {
    $('.button-checkbox').each(function () {

        // Settings
        var $widget = $(this),
            $button = $widget.find('button'),
            $checkbox = $widget.find('input:checkbox'),
            color = $button.data('color'),
            settings = {
                on: {
                    icon: 'glyphicon glyphicon-check'
                },
                off: {
                    icon: 'glyphicon glyphicon-unchecked'
                }
            };

        // Event Handlers
        $button.on('click', function () {
            $checkbox.prop('checked', true);
            $checkbox.triggerHandler('change');
            $('.read-only-field').prop('readonly', false)
            $('#productLineID').val('0');
            updateDisplay();
        });
        $checkbox.on('change', function () {
            updateDisplay();
        });

        // Actions
        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');

            // Set the button's state
            $button.data('state', (isChecked) ? "on" : "off");

            // Set the button's icon
            $button.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$button.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $button
                    .removeClass('btn-default')
                    .addClass('btn-' + color + ' active');
            }
            else {
                $button
                    .removeClass('btn-' + color + ' active')
                    .addClass('btn-default');
            }
        }

        // Initialization
        function init() {

            updateDisplay();

            // Inject the icon if applicable
            if ($button.find('.state-icon').length == 0) {
                $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i> ');
            }
        }
        init();
    });
});
</script>

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
var productLineID;

$('#chosenMedicament').on('click', function(){
	$('#searchMedicament').modal('hide');
	$('#inputName').val(name);
	$('#inputProducent').val(producent);
	$('#inputKind').val(kind);
	$('#inputPrice').val(price);
	$('#productLineID').val(productLineID);
	});

$('#table').on('click', '.tableRow', function(){
	$(this).addClass('info');
	$(this).siblings().removeClass('info');
	name = $(this).children('.medicament-list-name').html();
	kind = $(this).children('.medicament-list-kind').html();
	producent = $(this).children('.medicament-list-producent').html();
	price = $(this).children('.medicament-list-price').html();
	productLineID = $(this).children('.medicament-list-productLineID').html();
	});
	
	
// validate addForm
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
		$('#loadingSave').prop('hidden', false);
		$('.btn-disable').prop('disabled', true);
		}
});

</script>



<!-- modal delete -->
<div class="modal fade" id="confirm-delete">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Potwierdź usunięcie</h4>
			</div>
			<div class="modal-body">
				<p>Czy chcesz na pewno usunąć lek?</p>
				<p>Operacja jest nieodwracalna?</p>
			</div>
			<div class="modal-footer">
     			<form id="formDelete">
                	<span hidden="false" id=loadingDelete>
						Usuwanie..
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
var sessionDB;
$(function(){
	var monthId = 0;
	sessionDB = $('#sessionDB').html();
})

$('#formDelete').on('submit', function(){
	$('#loadingDelete').prop('hidden', false);
	$('#btnCancel').prop('disabled', true);
	$('#btnDelete').prop('disabled', true);
});

</script>



<!-- no choose medicament-->
<script type="text/javascript">
$(document).ready(function() {
	$('#editButton').click(function(){
		if($('#medicamentEditIdForm').val() == 0)
			{
			$('#noChooseMedicament').show().delay(5000).fadeOut();
			}
		$('#showIfAddHideIfEdit').hide();
		$('#medicamentEditIdForm').prop('disabled', false);
		
	});
	$('#deleteButton').click(function(){
		if($('#medicamentEditIdForm').val() == 0)
			{
			$('#noChooseMedicament').show().delay(5000).fadeOut();
			}
		else {
			$('#confirm-delete').modal('show');
		}
	});
	$('#addButton').click(function(){
		$('#showIfAddHideIfEdit').show();
		$('#medicamentEditIdForm').val('');
		$('#medicamentEditIdForm').prop('disabled', true);
    	$('#inputName').val('');
    	$('#inputProducent').val('');
    	$('#inputKind').val('');
    	$('#inputPrice').val('0');
    	$('#valueYear').val('');
    	$('#valueMonth').val('');
    	$('#idMonth').val('');
   		$('#addTitle').html('Dodaj lek');
    	$('#editButton').attr({
			"data-href": "",
			"data-toggle": "",
 			"data-target": ""
		});
    	$('tr').removeClass('info');
    	console.log(sessionDB);
	});
	$('#infoButton').click(function(){
		if($('#medicamentEditIdForm').val() == 0) $('#noChooseMedicament').show().delay(5000).fadeOut();
		else {
			getAdditional();
		}
		
		
	});

	
});
function getAdditional(){
	$.ajax({
		url: 'database/additional.json',
		dataType: 'json',
		data:{session:sessionDB, productLineID:productLineID},
		success: function(data){
			$('.content-of-additional').html('');
			$('#composition').append(data.composition);
			$('#effects').append(data.effects);
			$('#indications').append(data.indications);
			$('#contraindications').append(data.contraindications);
			$('#precaution').append(data.precaution);
			$('#pregnancy').append(data.pregnancy);
			$('#sideeffects').append(data.sideeffects);
			$('#interactions').append(data.interactions);
			$('#dosage').append(data.dosage);
			$('#remark').append(data.remark);
			$('#additionalModal').modal('show');
		},
		error: function(xhr) {
			console.log('erroradd')
			$('#noProductLineID').show().delay(5000).fadeOut();
		}
	
		
	});
}
</script>


<!-- create edit and delete form data by clicking rows table -->
<script type="text/javascript">
 $(document).ready(function() {
   	$('#myTable').on('click', 'tbody > tr[role=row]', function(){
   		if(!$(this).find('td').hasClass('dataTables_empty')) {
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
	    	$('#formDelete').prop('action', '/medicament/remove/' + $(this).find('.rowHiddenId').html() + '.html');
	
	    	$('#medicamentEditIdForm').val($(this).find('.rowHiddenId').html());
	    	$('#inputName').val($(this).children('.medicament-name').html());
	    	$('#inputProducent').val($(this).children('.medicament-producent').html());
	    	$('#inputKind').val($(this).children('.medicament-kind').html());
	    	$('#inputPrice').val($(this).find('.medicament-price').html());
	    	$('#valueYear').val($(this).find('.medicament-date-year').html());
	    	$('#valueMonth').val($(this).find('.medicament-date-month').html());
	    	$('#idMonth').val($(this).find('.medicament-date-month-id').html());
	    	monthId = $('#idMonth').val();
	   		$('#addTitle').html('Edytuj lek');
	   		productLineID = $(this).find('.productLineID').html()
   		}
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
var search;
$(function() {
    $('#searchButt').click(function() {
     	$('.errorAddMedicament').attr('hidden',true).removeClass('help-block');
     	$('.tableRow').remove();
     	$('.table-row-header').remove();
     	$("[for=hiddenId]").remove();
     	search = $('#search').val();
     	$('#idMedicamentSearchFormGroup').removeClass('has-error');
 		if($('#search').val().length >= 3)
 		{
 			$('#loadingSearch').prop('hidden', false);
  			getMedicaments();
 		}
 		else
 			{
 			$('#erronNoThreeSign').attr('hidden',false).addClass('help-block');
 			$('#idMedicamentSearchFormGroup').addClass('has-error');

 			}
    });
})

function getMedicaments(){
	$.ajax({
		url: 'database.json',
		dataType: 'json',
		data:{session:sessionDB, search:search},
		success: function(data){
			$('#table').append("<tr class=\"table-row-header\"><th>Nazwa</th><th>Producent</th><th>Opakowanie</th><th>Cena</th></tr>");
			$.each(data, function(index, element){
				$('#table').append("<tr class=\"tableRow\"><td class=\"medicament-list-name\">" + element.productName + 
						 "</td><td class=\"medicament-list-productLineID\" hidden=\"true\">" + element.productLineID + 
						 "</td><td class=\"medicament-list-producent\">" + element.producer + 
						 "</td><td class=\"medicament-list-kind\">" + element.pack + 
						 "</td><td class=\"medicament-list-price\">" + element.price + "</td></tr>");
			});
		if(data == '') {
			$('#erronNoFoundMedicament').attr('hidden',false).addClass('help-block');
			$('#idMedicamentSearchFormGroup').addClass('has-error');
			
		}
		else {
  				$('#table').show();
  	  	 		$('#searchMedicament').modal('show');
		}
		$('#loadingSearch').prop('hidden', true);
		},
		error: function(xhr) {
			console.log('error')
		}
	
		
	});
}
</script>

<div class="modal fade" id="additionalModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <b><h4 class="modal-title" id="myModalLabel"></h4></b>
            </div>
            <div class="modal-body">
	            <p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#composition">Skład</button>
	            	<div id="composition" class="collapse content-of-additional"></div>
				</p>
	            <p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#effects">Działanie</button>
	            	<div id="effects" class="collapse content-of-additional"></div>
	            </p>
	            <p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#indications">Wskazania</button>
	            	<div id="indications" class="collapse content-of-additional"></div>
	           	</p>
	            <p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#contraindications">Przeciwwskazania</button>
	            	<div id="contraindications" class="collapse content-of-additional"></div>
	           	</p>
	           	<p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#precaution">Środki ostrożności</button>
	            	<div id="precaution" class="collapse content-of-additional"></div>
	            </p>
	            <p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#pregnancy">Ciąża i karmienie piersią</button>
	            	<div id="pregnancy" class="collapse content-of-additional"></div>
	            </p>
	            <p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#sideeffects">Działania niepożądane</button>
	            	<div id="sideeffects" class="collapse content-of-additional"></div>
	            </p>
	            <p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#interactions">Interakcje</button>
	            	<div id="interactions" class="collapse content-of-additional"></div>
	            </p>
	            <p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#dosage">Dawkowanie</button>
	            	<div id="dosage" class="collapse content-of-additional"></div>
	            </p>
	            <p>
	            	<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#remark">Uwagi</button>
	            	<div id="remark" class="collapse content-of-additional"></div>
	            </p>
            </div>
        </div>
    </div>
</div>

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