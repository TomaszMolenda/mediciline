<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<table id="patientsTable" class="display table table-striped table-bordered dt-responsive nowrap" width="100%">
		<thead>
			<tr>
				<td>Imię</td>
				<td>Data urodzenia</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${patients}" var="patient">
				<tr>
					<td>${patient.name}</td>
					<td>${patient.birthday}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<button class="btn btn-warning btn-lg" id="addButton" data-toggle="modal" data-target="#addModal" data-backdrop="static" data-keyboard="false">Dodaj</button>
</div>


<div class="modal fade" id="addModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Dodaj osobę</h4>
            </div>
            <div class="modal-body">
            	<form:form action="add.html" method="post" modelAttribute="patient">
					<div class="form-group">
						<form:label path="name">Imię</form:label>
						<form:input autocomplete="off" path="name" cssClass="form-control"/>
					</div>
					<div class="form-group">
						<div class="input-group">
	   						<label for="birthday" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span></label>
	      						<form:input autocomplete="off" path="birthdayString" cssClass="datepicker form-control" placeholder="Kliknij kalendarz" id="birthday"/>
						</div>
					</div>
					<div class="modal-footer">
                		<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
                		<input type="submit" value="Zapisz" Class="btn btn-danger loading-block"/>
            		</div>
           		</form:form>
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
