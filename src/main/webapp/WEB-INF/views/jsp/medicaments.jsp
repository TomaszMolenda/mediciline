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
				<div class="table-responsive">
					<table id="myTable"
						class="table table-bordered table-hover table-striped">
						<thead>
							<tr>
<!-- 								<td>id</td> -->
								<td>nazwa leku</td>
<!-- 								<td>producent</td> -->
								<td>opakowanie</td>
<!-- 								<td>rodzaj</td> -->
<!-- 								<td>data otwarcia</td> -->
								<td>data waznosci</td>
<!-- 								<td>data konca</td> -->
								<td>akcja</td>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${medicaments}" var="medicament">
								<tr>
<%-- 									<td>${lek.id}</td> --%>
									<td>${medicament.medicamentDb.name}</td>
<%-- 									<td>${lek.medicamentDb.producent}</td> --%>
									<td>${medicament.medicamentDb.pack}</td>
<%-- 									<td>${lek.medicamentDb.kind}</td> --%>
<%-- 									<td>${lek.dateOpen}</td> --%>
									<td>${medicament.dateExpiration}</td>
<%-- 									<td>${lek.dateEnd}</td> --%>
									<td><center><a href="/medicament/remove/${medicament.id}.html"
										class="btn btn-danger" role="button">usun</a>
										<a href="/edit-medicament-${medicament.id}.html"
										class="btn btn-info" role="button">edytuj</a>
										</center></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
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