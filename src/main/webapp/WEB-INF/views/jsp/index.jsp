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
			<div class="container">
				<div class="row">

            <div class="col-lg-4 col-sm-6 text-center">
                <a href="/medicaments.html"><img class="img-circle img-responsive img-center" src="/resources/jpg/195034medicine.jpg-660x330.png" alt=""></a>
                <h3>Wykaz leków

                </h3>
                <p>Wykaz leków w domowej apteczce</p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center">
                <a href="/diseases.html"><img class="img-circle img-responsive img-center" src="/resources/jpg/disease.jpg" alt=""></a>
                <h3>Wykaz chorób
                </h3>
                <p>Wykaz chorób w gospodarstwie</p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center">
                <img class="img-circle img-responsive img-center" src="http://placehold.it/200x200" alt="">
                <h3>John Smith
                    <small>Job Title</small>
                </h3>
                <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center">
                <img class="img-circle img-responsive img-center" src="http://placehold.it/200x200" alt="">
                <h3>John Smith
                    <small>Job Title</small>
                </h3>
                <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center">
                <img class="img-circle img-responsive img-center" src="http://placehold.it/200x200" alt="">
                <h3>John Smith
                    <small>Job Title</small>
                </h3>
                <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center">
                <img class="img-circle img-responsive img-center" src="http://placehold.it/200x200" alt="">
                <h3>John Smith
                    <small>Job Title</small>
                </h3>
                <p>What does this team member to? Keep it short! This is also a great spot for social links!</p>
            </div>
        </div>
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