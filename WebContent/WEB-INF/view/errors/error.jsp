<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>OUP's</title>
	<link href="${URL }/webroot/style_menu.css" rel="stylesheet">
    <link rel="stylesheet" href="${URL }/webroot/bootstrap/dist/css/print.css" type="text/css" media="print" />
	
	<!-- Bootstrap core CSS -->
    <link href="${URL }/webroot/bootstrap/dist/css/signin.css" rel="stylesheet">
    <link href="${URL }/webroot/bootstrap/dist/css/sticky-footer-navbar.css" rel="stylesheet">
    <link href="${URL }/webroot/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link href="${URL }/webroot/bootstrap/dist/css/bootstrap-social.css" rel="stylesheet">
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${URL }/webroot/bootstrap/dist/css/starter-template.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="${URL }/webroot/bootstrap/assets/js/html5shiv.js"></script>
      <script src="${URL }/webroot/bootstrap/assets/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<c:import url="/WEB-INF/menu/main_menu.jsp"/> 
	<div class="container"> 
		<div class="row" style="width:90%; margin:auto; "> 
			<div class="news"> 
			<c:if test="${not empty erreur_message }">
				<div class="alert alert-danger" role="alert">
				${erreur_message}
				</div>
			</c:if> 
			</div> 
		</div> 
	</div> 
	<div class="container"></div>
	<div class="footer"> <c:import url="/WEB-INF/footer/footer.jsp"/> 
	</div>   
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="${URL}/webroot/bootstrap/dist/js/bootstrap.min.js"></script>
	<script src="${URL}/webroot/bootstrap/dist/js/myquery.js"></script>
	<script src="${URL}/webroot/bootstrap/docs/assets/js/docs.min.js"></script>

	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="${URL}/webroot/bootstrap/docs/assets/js/ie10-viewport-bug-workaround.js"></script>
	<script type="text/javascript">
		function imprimer_page(){
		  window.print();
		}
	</script>
</body>
</html>