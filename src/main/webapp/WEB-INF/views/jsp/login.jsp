<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<%@ include file="../jsp/head.jsp"%>
</head>
<body>
	<style>
.form-signin {
	max-width: 330px;
	padding: 15px;
	margin: 0 auto;
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin .checkbox {
	font-weight: normal;
}

.form-signin .form-control {
	position: relative;
	height: auto;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	padding: 10px;
	font-size: 16px;
}

.form-signin .form-control:focus {
	z-index: 2;
}

.form-signin input[type="email"] {
	margin-bottom: -1px;
	border-bottom-right-radius: 0;
	border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
	margin-bottom: 10px;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
</style>
	<div id="wrapper">

		<%@ include file="../jsp/sidebar.jsp"%>

		<div id="page-content-wrapper">

			<form name="f" action="/login" method="POST" class="form-signin">
				<h2 class="form-signin-heading">Zaloguj sie</h2>
				<label for="username" class="sr-only">nazwa</label> <input
					type="text" name='username' id="username" class="form-control"
					placeholder="nazwa" required autofocus> <label
					for="password" class="sr-only">Password</label> <input
					type="password" id="password" name="password" class="form-control"
					placeholder="Password" required>

				<button class="btn btn-lg btn-primary btn-block" type="submit">Zaloguj</button>
				<a href="/register.html" class="btn-lg btn btn-success btn-block" role="button">Rejestracja</a>
			</form>
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
						url : "/medicaments.json",
						getValue : "name",

						list : {
							match : {
								enabled : true
							},
							onSelectItemEvent : function() {
								var selectedItemValue = $("#basics")
										.getSelectedItemData().id;
								$("#inputTwo").val(selectedItemValue).trigger(
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

					$("#basics").easyAutocomplete(options);
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