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
				<div class="table-responsive">
					<table id="myTable"
						class="table table-bordered table-hover table-striped">
						<thead>
							<tr>
								<td>nazwa leku</td>
								<td>opakowanie</td>
								<td>data waznosci</td>
								<td>akcja</td>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${medicaments}" var="medicament">
								<tr>
									<div class="nazwaleku"><td>${medicament.medicamentDb.name}</td></div>
									<td>${medicament.medicamentDb.pack}</td>
									<td>${medicament.dateExpiration}</td>
									<td>
										<button class="btn btn-danger" data-href="/medicament/remove/${medicament.id}.html" data-toggle="modal" data-target="#confirm-delete">usun</button>
										<a href="/medicament/edit/${medicament.id}.html" class="btn btn-info" role="button">edytuj</a>
										</td>
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
	
	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Potwierdź usunięcie</h4>
                </div>
            
                <div class="modal-body">
                    <p>Czy chcesz na pewno usunąć lek?</p>
                    <p>Operacja jest nieodwracalna?</p>
                    <p class="debug-url"></p>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-danger btn-ok">Delete</a>
                </div>
            </div>
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
	
	<script>
        $('#confirm-delete').on('show.bs.modal', function(e) {
            $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
        });
    </script>
	
</body>
</html>