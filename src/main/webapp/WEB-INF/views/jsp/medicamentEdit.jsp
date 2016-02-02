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

				<form:form action="do.html" method="post" modelAttribute="medicament">
					<div class="form-group">
						<form:label path="name">Lek</form:label>
						<form:input path="name" disabled="true"/>
						<form:hidden path="id" />
					</div>
					<form:label path="dateStringExpiration">Data waznosci</form:label>
					<div class="form-group">
						<div class='input-group date' id='datetimepicker3'>
							<span class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"></span>
							</span>
							<form:input path="dateStringExpiration" cssClass="form-control" />

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
					<br />


					<input type="submit">

				</form:form>
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