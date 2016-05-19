<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="form-group">
		<form:form action="" method="post" modelAttribute="dosage">
			idMD
			<form:input path="idMD"/>
			takietime
			<form:input path="takeTime" />
			wholepackage
			<form:input path="wholePackage"/>
			unit
			<form:input path="unit"/>
			dose
			<form:input path="dose"/>
			<input type="submit" value="Zapisz"/>
		</form:form>
	</div>

</body>
</html>