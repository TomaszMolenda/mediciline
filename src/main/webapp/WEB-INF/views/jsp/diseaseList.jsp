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
								<tr class="accordion-toggle">
									<td><button class="btn btn-default btn-xs" data-toggle="collapse" data-target="#${disease.id}"><span class="glyphicon glyphicon-eye-open"></span></button></td>
									<td>${disease.name}</td>
									<td>${disease.start}</td>
									<td>${disease.stop}</td>
									<td>
										<a href="/disease/remove/${disease.id}.html"
										class="btn btn-danger" role="button">usun</a>
										<a href="/disease/edit/${disease.id}.html"
										class="btn btn-info" role="button">edytuj</a>
										<a href="/disease/addmedicaments/${disease.id}.html"
										class="btn btn-primary" role="button">dodaj lek</a>
										</td>
								</tr>
								<tr>
									<td colspan="12" class="hiddenRow">
										<div class="accordian-body collapse" id="${disease.id}">
											<table class="table table-striped">
												<thead>
													<tr>
														<th>Lek</th>
														<th>Opakowanie</th>
														<th>Data ważności</th>
														<th>Producent</th>							
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${disease.medicaments}" var="medicament">
													<tr>
														<td>${medicament.medicamentDb.name}</td>
														<td>${medicament.medicamentDb.kind}</td>
														<td>${medicament.dateExpiration}</td>
														<td>${medicament.medicamentDb.producent}</td>
													</tr>
													</c:forEach>
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