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
				<form:form method="POST" modelAttribute="disease">
					<div class="form-group">
						<form:label path="name">Choroba</form:label>
						<form:input path="name" id="name" />
					</div>
					<form:label path="startString">rozpoczecie</form:label>
					<div class="form-group">
						<div class='input-group date' id='datetimepicker3'>
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"></span>
							</span>
							<form:input path="startString" cssClass="form-control" />

						</div>
					</div>
					<script>
						$(function() {
							$('#datetimepicker3').datetimepicker({
								locale : 'pl',
								format : 'YYYY-MM-DD',
							//defaultDate: "${dateTo}",
							});
						});
					</script>
				<form:label path="stopString">zakonczenie</form:label>
					<div class="form-group">
						<div class='input-group date' id='datetimepicker4'>
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"></span>
							</span>
							<form:input path="stopString" cssClass="form-control" />

						</div>
					</div>
					<script>
						$(function() {
							$('#datetimepicker4').datetimepicker({
								locale : 'pl',
								format : 'YYYY-MM-DD',
							//defaultDate: "${dateTo}",
							});
						});
					</script>
					<input type="submit" value="Dodaj chorobe" Class="btn btn-default" />
				</form:form>
			</div>
			<a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle
				Menu</a>
		</div>
	</div>












	<script>
		$(document).ready(
				function() {
					var options = {
						//data: ["blue", "green", "pink", "red", "yellow"]
						//url: "/colors.json"
						url : "/medicaments-db.json",
						requestDelay : 1000,
						getValue : "name",

						list : {
							maxNumberOfElements : 100,
							match : {
								enabled : true
							},
							onSelectItemEvent : function() {
								var selectedItemValue = $("#name")
										.getSelectedItemData().id;
								$("#id").val(selectedItemValue).trigger(
										"change");
							}

						},
						template : {
							type : "description",
							fields : {
								description : "description"
							}
						}

					};

					$("#name").easyAutocomplete(options);
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