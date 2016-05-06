<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
/* Start by setting display:none to make this hidden.
   Then we position it in relation to the viewport window
   with position:fixed. Width, height, top and left speak
   for themselves. Background we set to 80% white with
   our animation centered, and no-repeating */
.modal {
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

/* When the body has the loading class, we turn
   the scrollbar off with overflow:hidden */
body.loading {
    overflow: hidden;   
}

/* Anytime the body has the loading class, our
   modal element will be visible */
body.loading .modal {
    display: block;
}
</style>
    
    
<script type="text/javascript">
//http://stackoverflow.com/a/10787789
$(function(){
	var hash = document.location.hash;
	console.log(hash);
	
	var prefix = "tab_";
	console.log(hash.replace(prefix,""));
	if (hash) {
 	    $('.nav-tabs a[href="'+hash.replace(prefix,"")+'"]').tab('show');
	} 


	// Change hash for page-reload
	$('.nav-tabs a').on('shown.bs.tab', function (e) {
	    window.location.hash = e.target.hash.replace("#", "#" + prefix);
	});
})
</script>
    
<div class="container">

	<c:if test="${param.success eq true}">
		<div class="alert alert-success">Backup succeessfull!!</div>
	</c:if>
	<c:if test="${param.success eq false}">
		<div class="alert alert-danger">Backup ERROR!!</div>
	</c:if>
	
	<ul class="nav nav-tabs" role="tablist">
		<li><a href="#databases" aria-controls="home" role="tab"	data-toggle="tab">Baza danych</a></li>
		<li><a href="#users" aria-controls="home" role="tab"	data-toggle="tab">Użytkownicy</a></li>
	</ul>
	
	<div class="tab-content">
		<div class="tab-pane" id="databases">
			<br>
			<div>
				<a href="/admin/postgresql/backup.html" class="btn btn-info" role="button">Postgresql backup</a>
				<button class="btn btn-danger" id="test">Postgresql restore</button>
				<script type="text/javascript">
					var backupId;
					var backupSuccess;
					$('#test').click(function(){
						if(backupSuccess == 'true') {
							$.ajax({
								url: 'postgresql/restore.html',
								beforeSend: function(xhr){
									xhr.setRequestHeader('id', backupId)
									;},
								success: function(data, textStatus, request){
									console.log(request.getResponseHeader('status'));
								},
								error: function(xhr) {
									console.log('error');
								}
							
								
							});
						}
						
					});
				</script>
			</div>
			<br>
			<table id="tableBackup" class="table table-bordered table-hover table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Date</th>
					<th>Size</th>
					<th>Success</th>
					<th>Type</th>
					<th>Id</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${backups}" var="backup">
					<tr>
						<td>${backup.name}</td>
						<td>${backup.date}</td>
						<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${backup.size/(1024*1024)}" /> MB</td>
						<td class="backup-success">${backup.success}</td>
						<td>${backup.type}</td>
						<td class="backup-id">${backup.id}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<script type="text/javascript">
 				$(document).ready(function() {
   					$('#tableBackup').on('click', 'tbody > tr', function(){
	   					$(this).addClass('info');
	    				$(this).siblings().removeClass('info');
	    				backupId = $(this).children('.backup-id').html();
	    				backupSuccess = $(this).children('.backup-success').html();
	    				console.log(backupId);
	    				console.log(backupSuccess);
    				});
				});
</script>
		</div>
		<div class="tab-pane" id="users">
			<br>
			<table class="table table-bordered table-hover table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Email</th>
					<th>Date</th>
					<th>Roles</th>
					<th>Active</th>
					<td>Medicaments</td>
					<td>Patients</td>
					<td>Diseases</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.name}</td>
						<td>${user.email}</td>
						<td>${user.date}</td>
						<td>
							<c:forEach items="${user.roles}" var="role">
								${role.name}<br>
							</c:forEach>
						</td>
						<td>${user.active}</td>
						<td>${fn:length(user.medicaments)}</td>
						<td>${fn:length(user.patients)}</td>
						<td>${fn:length(user.diseases)}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- http://stackoverflow.com/a/1964871/5753094 -->
<div class="modal"><!-- Place at bottom of page --></div>
<script type="text/javascript">
$body = $("body");

$(document).on({
    ajaxStart: function() { $body.addClass("loading");    },
     ajaxStop: function() { $body.removeClass("loading"); }    
});
</script>