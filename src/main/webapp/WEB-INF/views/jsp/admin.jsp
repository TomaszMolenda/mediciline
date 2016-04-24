<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
    
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
				<a href="/user/admin/postgresql/backup.html" class="btn btn-info" role="button">Postgresql backup</a>
			</div>
			<br>
			<table class="table table-bordered table-hover table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Date</th>
					<th>Size</th>
					<th>Success</th>
					<th>Type</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${backups}" var="backup">
					<tr>
						<td>${backup.name}</td>
						<td>${backup.date}</td>
						<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${backup.size/(1024*1024)}" /> MB</td>
						<td>${backup.success}</td>
						<td>${backup.type}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
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