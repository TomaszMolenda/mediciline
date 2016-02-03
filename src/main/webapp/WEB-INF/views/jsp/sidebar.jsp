
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="sidebar-wrapper">
	<ul class="sidebar-nav">
		<li class="sidebar-brand"><a href="/index.html">Medicine app</a></li>
		<sec:authorize access="hasRole('ROLE_USER')">
			<li><a href="/logout">Wyloguj</a></li>
			<li><a href="/medicament/add.html">Dodaj lek</a></li>
			<li><a href="/disease/add.html">Dodaj chorobe</a></li>
		</sec:authorize>
	</ul>
</div>