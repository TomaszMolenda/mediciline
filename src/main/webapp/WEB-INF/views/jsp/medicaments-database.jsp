<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
converDate(1451602800000);
<!--

//-->
</script>

<div class="container">
	<div class="col-xs-6">
		<input type="text" class="form-control" id="search" autocomplete="off" placeholder="wpisz minimum 3 znaki">
	</div>
	<button class="btn btn-warning btn-md" id="button">Szukaj</button>
	
	<span id="sessionDB" hidden="true">${sessionDB}</span>
	<p>Kliknij 2 razy w lek aby uzyskać dodatkowe informacje</p>
	<p>
		<table id="tableMedicaments" class="table-bordered table-striped table-condensed">
		</table>
	</p>
	

</div>

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

<script type="text/javascript">

</script>

<script type="text/javascript">
var sessionDB;
var search;
var packageID;
$(function(){
	
	$('#button').on('click', function(){
		$('#tableMedicaments').html('');
		search = $('#search').val();
		sessionDB = $('#sessionDB').html();
		getMedicaments();
	});
	$('#tableMedicaments').on('dblclick', 'tbody > tr', function(){
		doubleClickTap($(this));
   		});
	$('#tableMedicaments').on('doubletap', 'tbody > tr', function(event){
		doubleClickTap($(this));
		});
	
})

function doubleClickTap(thisObj) {
	packageID = thisObj.find('.packageID').html();
	$('#myModalLabel').html('');
	$('#myModalLabel').append(thisObj.find('.productName').html() + ' ');
	$('#myModalLabel').append(thisObj.find('.form').html() + ' ');
	$('#myModalLabel').append(thisObj.find('.dosage').html());
	
	getAdditional();
}

function getMedicaments(){
	$.ajax({
		url: 'database.json',
		dataType: 'json',
		data:{session:sessionDB, search:search},
		success: function(data){
 			$('#tableMedicaments').append("<thead><tr><th>Nazwa</th><th>Producent</th><th>Rodzaj</th><th>Forma</th><th>Dawka</th></tr></thead><tbody>");

			$.each(data, function(index, element){
				$('#tableMedicaments').append("<tr><td><span class=\"productName\">" 
						+ element.productName + "</span><span hidden=\"true\" class=\"packageID\">" 
						+ element.packageID + "</span></td><td>" + 
						element.producer + "</td><td>" + 
						element.pack + "</td><td class=\"form\">" + 
						element.form + "</td><td class=\"dosage\">" + 
						element.dosage + "</td></tr>");
			});

			$('#tableMedicaments').append("</tbody>");
		},
		error: function(xhr) {
			console.log('error')
		}
	
		
	});
}

function getAdditional(){
	$.ajax({
		url: 'database/information.json',
		dataType: 'json',
		data:{session:sessionDB, packageID:packageID},
		success: function(data){
			$('.content-of-additional').html('');
			$('#composition').append(data.medicamentAdditional.composition);
			$('#effects').append(data.medicamentAdditional.effects);
			$('#indications').append(data.medicamentAdditional.indications);
			$('#contraindications').append(data.medicamentAdditional.contraindications);
			$('#precaution').append(data.medicamentAdditional.precaution);
			$('#pregnancy').append(data.medicamentAdditional.pregnancy);
			$('#sideeffects').append(data.medicamentAdditional.sideeffects);
			$('#interactions').append(data.medicamentAdditional.interactions);
			$('#dosage').append(data.medicamentAdditional.dosage);
			$('#remark').append(data.medicamentAdditional.remark);
			$('#additionalModal').modal('show');
		},
		error: function(xhr) {
			console.log('erroradd')
		}
	
		
	});
}
</script>