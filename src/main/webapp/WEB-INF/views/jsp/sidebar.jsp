
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="container">
  <nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
          <span class="sr-only">Rozwiń nawigację</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">"Mediciline"</a>
      </div>
   
      <!-- Grupowanie elementów menu w celu lepszego wyświetlania na urządzeniach moblinych -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li><a class="navbar-position" href="/medicament/list.html">Leki</a></li>
          <li><a class="navbar-position" href="/disease/list.html">Choroby</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
        	<li><a class="navbar-position" href="/register.html">Zarejestruj</a></li>
       		<li><a class="navbar-position" href="/login.html">Zaloguj</a></li>
        </ul>
      </div>
    </div>
  </nav>
</div>

<script type="text/javascript">
$(document).ready(function(){
	var current = window.location.pathname;
	$('.navbar-position').each(function(){
		link = $(this).attr('href');
		if(link == current)
			{
			$(this).parent().addClass('active');
			}
	});
	
});


</script>
