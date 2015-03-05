<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta charset="utf-8">
	<title>GESTA</title>
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
    <title>Connexion</title>
</head>
<body> 
	<c:if test="${ not empty entete}"> 
		<c:import url="${entete }"/>
	</c:if>   
	<div class="container">
	<div class="row" style="width:300px; margin:auto; ">
		<h1>Espace connexion</h1>
        <form method="post" action="<c:url value="/usersConnexion" />">
            <fieldset>
                <legend>Connexion</legend>
                <p>Veuillez utiliser vos identifiants pour vous connecter.</p>

                <label for="login">Login<span class="requis">*</span></label>
                <input class="form-control" type="text" id="login" name="login" value="<c:out value="${not empty utilisateur ? utilisateur.getLogin() : ''}"/>" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['login']}</span>
                <br />

                <label for="mdp">Mot de passe <span class="requis">*</span></label>
                <input class="form-control" type="password" id="mdp" name="mdp" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['mdp']}</span>
                <br />

                <input class="btn btn-primary pull-right"  type="submit" value="Connexion" class="sansLabel" />
                <br />
                
                <p class="${empty form.erreurs ? 'succès' : 'erreur'}">${form.getResultat()}</p>
            </fieldset>
        </form>
	</div> 
	</div> 
	<div class="container"></div>
	<div class="footer">
		<c:if test="${ not empty footer}">
			<c:import url="${footer}"/>
		</c:if> 
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