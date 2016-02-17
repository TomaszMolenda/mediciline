<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<%@ include file="../jsp/head.jsp"%>


</head>
<body>
	<div id="wrapper">
		<%@ include file="../jsp/sidebar.jsp"%>
		<div id="page-content-wrapper">
			<form:form method="POST" modelAttribute="medicament" action="add.html" id="form">
				<form:label path="name">Wpisz nazwę leku i kliknij w lupkę (min 3 znaki)</form:label>
				<div class="form-group" id="idMedicamentSearchFormGroup">
					<div class="input-group">
						<span class="input-group-addon" id="searchButt">
							<span class="glyphicon glyphicon-search"></span>
      					</span>					
						<form:input path="name" id="search" type="search" name="search" placeholder="Wpisz nazwę leku (min 3 znaki)" class="form-control"/>
						<form:errors path="name" cssClass="form-error" />
					</div>
					<form:hidden path="idMedicamentDb" id="hiddenId" />
					<form:errors path="idMedicamentDb" cssClass="form-error text-danger" />
				</div>
	        	<table class="table table-striped" id="table" hidden="true"></table>
				<form:label path="dateStringExpiration">Wybierz datę ważności klikająć w kalendarz</form:label>
				<div class="form-group">
					<div class='input-group date' id='datetimepicker3'>
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
						</span>
						<form:input path="dateStringExpiration" cssClass="form-control" placeholder="Kliknij kalendarz"/>
					</div>
					<form:errors path="dateStringExpiration" cssClass="form-error text-danger" />
				</div>				
				<input type="submit" value="Dodaj lek" Class="btn btn-default" />
			</form:form>
			<a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a>
		</div>
	</div>
	
<script type="text/javascript">

$(function() {
	$('#form').validate(
			{
				ignore: "",
				rules: {
					idMedicamentDb:{
						required : true
					},
					dateStringExpiration:{
						required : true
					}
				},
				messages: {
					idMedicamentDb:{
						required : "Nie wybrałeś leku"
					},
					dateStringExpiration:{
						required : "Nie wybrałeś daty"
					}
				},
				highlight: function(element) {
		            $(element).closest('.form-group').addClass('has-error');
		        },
		        unhighlight: function(element) {
		            $(element).closest('.form-group').removeClass('has-error');
		        },
		        errorElement: 'span',
		        errorClass: 'help-block',
		        errorPlacement: function(error, element) {
		            if(element.parent('.input-group').length) {
		                error.insertAfter(element.parent());
		            } else {
		                error.insertAfter(element);
		            }
		        }
			});
})


</script>

<script type="text/javascript">
 $(document).ready(function() {
    	$('#table').on('click', '.tableRow', function(){		
     		$('#hiddenId').val($(this).children('.hiddenMedicamentIdList').html());
     		$(this).addClass('success');
     		$(this).siblings().removeClass('success');
     	});
 });
</script>

<script type="text/javascript">
$(function() {
    $('#searchButt').click(function() {
    	$('.tableRow').remove();
    	$("[for=hiddenId]").remove();
    	$('#hiddenId').val('');
    	var addedTableTitle = false;
 		if($('#search').val().length >= 3)
 		{
	    	$.getJSON("medicaments-db.json", function(result)
	    			{
	    			$.each(result, function(i, medicament)
	    					{
	    						if(medicament.name.toLowerCase().indexOf($('#search').val().toLowerCase()) >= 0)
	    						{
	    							if(i >= 0 & addedTableTitle == false)
	    							{
	    								addedTableTitle = true;
	    								$('#table').append("<tr class=\"tableRow\"><th>Nazwa</th><th>Producent</th><th>Opakowanie</th><th>Cena</th></tr>");
	    							}
	    							$('#idMedicamentSearchFormGroup').removeClass('has-error');
	    							$('#table').append("<tr class=\"tableRow\"><td class=\"medicamentNameList\">" 
		    						+ medicament.name + "</td><td class=\"hiddenMedicamentIdList\" hidden=\"true\">" 
		    						+ medicament.id + "</td><td>" 
		    						+ medicament.producent + "</td><td>" 
		    						+ medicament.kind + "</td><td>" 
		    						+ medicament.price + "</td></tr>");
	    							return;
	    						}
	    					});
	    			if(addedTableTitle == false)
	     			{
	     				$('#idMedicamentSearchFormGroup').append("<span class=\"help-block tableRow\">Nic nie znaleziono</span>")
	     				$('#idMedicamentSearchFormGroup').addClass('has-error');
	     			}
	    			});
 		}
 		else
 			{
 			$('#idMedicamentSearchFormGroup').append("<span class=\"help-block tableRow\">Wpisz minimun 3 znaki</span>")
 			$('#idMedicamentSearchFormGroup').addClass('has-error');
 			}
 		$('#table').show();
    });
})
</script>

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


<script>
function test(){
$('#datetimepicker3').datetimepicker({
		locale : 'pl',
		format : 'YYYY-MM-DD',
		});
	}
	$(function()  {
	test();
	});
</script>


	<script>
		$("#menu-toggle").click(function(e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});
	</script>
	
</body>
</html>