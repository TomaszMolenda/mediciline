
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="sidebar-wrapper">
	<ul class="sidebar-nav navigation">
		<li class="sidebar-brand"><a href="/index.html">Medicine app</a></li>
		<sec:authorize access="hasRole('ROLE_USER')">
			<li><a href="/logout">Wyloguj</a></li>
			<li class="toggle"><a>Manu leków</a></li>
				<li class="content"><a>menu leków 1</a></li>
				<li class="content"><a>menu leków 2</a></li>
				
			<li><a href="/medicament/add.html">Dodaj lek</a></li>
			<li><a href="/disease/add.html">Dodaj chorobe</a></li>
		</sec:authorize>
	</ul>
</div>

<script type="text/javascript">
$(function() {
 	  $("li.content").hide();
	  $("ul.navigation").delegate("li.toggle", "click", function() { 
	      $(this).next().toggle("fast").siblings(".content").hide("fast");
	  });
	});
</script>