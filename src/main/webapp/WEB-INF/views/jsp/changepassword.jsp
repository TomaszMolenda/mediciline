<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">

<form:form method="POST" modelAttribute="userChangePassword">
	<form:input type="password" path="password" cssClass="form-control"	placeholder="hasło" />
	<form:errors path="password" cssClass="form-error" />
	<form:input type="password" path="confirmPassword" cssClass="form-control"	placeholder="powtórz hasło" />
	<form:errors path="confirmPassword" cssClass="form-error" />
	<button type="submit" class="btn btn-default">Zapisz</button>

</form:form>

</div>