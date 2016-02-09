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
				<form:form method="POST" modelAttribute="medicamentForm" action="do.html">
					<div class="table-responsive">
						<table id="myTable"
							class="table table-bordered table-hover table-striped">
							<thead>
								<tr>
									<td>dodaj</td>
									<td>nazwa leku</td>
									<td>opakowanie</td>
									<td>data waznosci</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${medicamentForm.medicaments}" var="medicament" varStatus="status">
									<tr>
										<td><form:checkbox path="ids" value="${medicament.id}"/><input name="diseaseId" type="hidden" value="${medicamentForm.diseaseId}"/></td>
										<td>${medicament.medicamentDb.name}</td>
										<td>${medicament.medicamentDb.pack}</td>
										<td>${medicament.dateExpiration}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<input type="submit" value="Dodaj leki" Class="btn btn-default" />
				</form:form>
			</div>

			<a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle
				Menu</a>
		</div>

	</div>


	<script>
		$(document).ready(function() {

			$('#myTable').dataTable({
				"bProcessing" : true,
				"language" : {
					"lengthMenu" : "Wyswietl _MENU_ leków na strone",
					"zeroRecords" : "Nic nie znaleziono",
					"info" : "Pokazano _PAGE_ z _PAGES_ stron",
					"infoEmpty" : "No records available",
					"infoFiltered" : "(filtered from _MAX_ total records)",
					"search" : "Szukaj",
					"paginate" : {
						"first" : "Pierwszy",
						"previous" : "Poprzedni",
						"next" : "Nastepny",
						"last" : "Ostatni"
					}
				}
			});
		});
	</script>

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

	<script>
		$("#menu-toggle").click(function(e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});
	</script>
</body>
</html>