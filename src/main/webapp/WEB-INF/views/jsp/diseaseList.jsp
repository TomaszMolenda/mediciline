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



			<div class="infinite-container">
			
				<table class="table table-condensed" style="border-collapse:collapse;">
					<thead>
						<tr>
							<th>&nbsp;</th>
							<th>Choroba</th>
							<th>Rozpoczecie</th>
							<th>Zakonczenie</th>
							<th>Akcja</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${diseases}" var="disease">
								<tr data-toggle="collapse" data-target="#${disease.id}" class="accordion-toggle">
									<td><button class="btn btn-default btn-xs"><span class="glyphicon glyphicon-eye-open"></span></button></td>
									<td>${disease.name}</td>
									<td>${disease.start}</td>
									<td>${disease.stop}</td>
									<td>
										<a href="/disease/remove/${disease.id}.html"
										class="btn btn-danger" role="button">usun</a>
										<a href="/disease/edit/${disease.id}.html"
										class="btn btn-info" role="button">edytuj</a>
										<a href="/addmedicaments/${disease.id}.html"
										class="btn btn-primary" role="button">dodaj lek</a>
										</td>
								</tr>
								<tr>
									<td colspan="12" class="hiddenRow">
										<div class="accordian-body collapse" id="${disease.id}">
											<table class="table table-striped">
												<thead>
													<tr><td><a href="WorkloadURL">Workload link</a></td><td>Bandwidth: Dandwidth Details</td><td>OBS Endpoint: end point</td></tr>
                        							<tr><th>Access Key</th><th>Secret Key</th><th>Status </th><th> Created</th><th> Expires</th><th>Actions</th></tr>
												</thead>
												<tbody>
													<tr><td>access-key-1</td><td>secretKey-1</td><td>Status</td><td>some date</td><td>some date</td><td><a href="#" class="btn btn-default btn-sm">
                 					 				<i class="glyphicon glyphicon-cog"></i></a></td></tr>
													
												</tbody>
											</table>
										</div>
									</td>
									
								</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle
				Menu</a>
		</div>

	</div>


	<script>
		$("#menu-toggle").click(function(e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});
	</script>
</body>
</html>