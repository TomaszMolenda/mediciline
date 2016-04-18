<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">

	<form:form method="POST" modelAttribute="user"
		cssClass="form-horizontal">
		<div class="form-group">
			<label for="name" class="col-sm-2 control-label">Nazwa</label>
			<div class="col-sm-10">
				<form:input type="text" path="name" cssClass="form-control"
					placeholder="nazwa użytkownika" />
				<form:errors path="name" cssClass="form-error" />
			</div>
		</div>
		<div class="form-group">
			<label for="email" class="col-sm-2 control-label">e-mail</label>
			<div class="col-sm-10">
				<form:input type="text" path="email" cssClass="form-control"
					placeholder="adres e-mail" />
				<form:errors path="email" />
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="col-sm-2 control-label">hasło</label>
			<div class="col-sm-10">
				<form:input type="password" path="password" cssClass="form-control"
					placeholder="hasło" />
				<form:errors path="password" />
			</div>
		</div>
		<div class="form-group">
			<label for="confirmPassword" class="col-sm-2 control-label">powtórz
				hasło</label>
			<div class="col-sm-10">
				<form:input type="password" path="confirmPassword"
					cssClass="form-control" placeholder="powtórz hasło" />
				<form:errors path="confirmPassword" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-default">Zapisz</button>
			</div>
		</div>
	</form:form>

</div>
