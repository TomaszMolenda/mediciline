<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<script type="text/javascript"
	src="https://code.jquery.com/jquery-2.2.3.min.js"></script>


<link href="${coreCss}" rel="stylesheet" />
<script src="${coreJs}"></script>

<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>



<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.11/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/responsive/2.0.2/js/dataTables.responsive.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/responsive/2.0.2/js/responsive.bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.11/css/dataTables.bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/responsive/2.0.2/css/responsive.bootstrap.min.css" />




<script type="text/javascript"
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>

<link
	href="/resources/bootstrap-datepicker/css/bootstrap-datepicker3.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="/resources/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript"
	src="/resources/bootstrap-datepicker/locales/bootstrap-datepicker.pl.min.js"></script>
	
<!-- https://gist.github.com/attenzione/7098476 -->
<script type="text/javascript"
	src="/resources/js/jquery.doubletap.js"></script>

<style>
.button {
	margin-right: 20px;
	margin-bottom: 20px;
}

.tableRow:hover {
	cursor: pointer;
}

.modal-block {
	display: none;
	position: fixed;
	z-index: 1000;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	background: rgba(255, 255, 255, .8) url('/resources/jpg/loading.gif')
		50% 50% no-repeat;
}

body.loading {
	overflow: hidden;
}

body.loading .modal-block {
	display: block;
}

.modal-loading {
	position: fixed;
	top: 25% !important;
	left: 0%;
}

.modal-body {
	max-height: calc(100vh - 210px);
	overflow-y: auto;
}

#1add {
	display: none;
	position: absolute;
	/*     top: 0%; */
	/*     left: 0%; */
	/*     width: 100%; */
	/*     height: 100%; */
	/*      background-color: #ababab;   */
	/*     z-index: 1001;  */
	-moz-opacity: 0.8;
	opacity: .70;
	filter: alpha(opacity = 80);
}

#modaltomo {
	display: none;
	position: absolute;
	top: 45%;
	left: 45%;
	width: 64px;
	height: 64px;
	padding: 30px 15px 0px;
	border: 3px solid #ababab;
	box-shadow: 1px 1px 10px #ababab;
	border-radius: 20px;
	background-color: white;
	z-index: 1002;
	text-align: center;
	overflow: auto;
}
</style>