<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Edition d'un utilisateur</title>
</head>
<body>
	<form method="post" action="">
	<h1>Création d'un utilisateur</h1>
	<p class="${empty form.erreurs ? 'succès' : 'erreur'}">${form.resultat}</p>
	<!-- Informations principales -->
	<fieldset class="">
	<legend>Informations personnelles</legend>
		<p>
			<label for="nom">Nom :</label>
			<input id="" class="" name="nom" type="text" value="<c:out value="${utilisateur.contact.getNom()}"/>" placeholder="Nom de famille"/>
			<span class="erreur">${form.erreurs['nom']}</span>
		</p>
		<p>
			<label for="prenom">Prénom :</label>
			<input id="" class="" name="prenom" type="text" value="<c:out value="${utilisateur.contact.getPrenom()}"/>" placeholder="Prénom"/>
			<span class="erreur">${form.erreurs['prenom']}</span>
		</p>
		<p>
			<label for="mail">E-mail :</label>
			<input id="" class="" name="mail" type="text" value="<c:out value="${utilisateur.contact.getMail()}"/>" placeholder="Email"/>
			<span class="erreur">${form.erreurs['mail']}</span>
		</p>
		<p>
			<label for="mail2">Confirmation de l'E-mail :</label>
			<input id="" class="" name="mail2" type="text" value="<c:out value="${utilisateur.contact.getMail()}"/>" placeholder="Ressaisissez l'email"/>
			<span class="erreur">${form.erreurs['mail2']}</span>
		</p>
		<p>
			<label for="tel">Téléphone :</label>
			<input id="" class="" name="tel" type="text" value="<c:out value="${utilisateur.contact.getTel()}"/>" placeholder="Numéro de téléphone"/>
			<span class="erreur">${form.erreurs['tel']}</span>
		</p> 		
	</fieldset>	
	<fieldset class="">
	<legend><strong>Catégorie de l'utilisateur :</strong></legend>		
		<p>
		Elève : <input name="type_user" type="radio" value="0" checked/>
		Enseignant : <input name="type_user" type="radio" value="1"/>
		</p>
		<p id="user_type_eleve"> 
			<label for="niveau">Niveau :</label>
			<select name="niveau">
				<option value="EI1">EI1</option>
			</select>  
		</p>
		<p id="user_type_enseignant"> 
			<label for="departement">Département :</label>
			<select name="departement">
				<option value="Maths">Mathématiques</option>
			</select>  
		</p>
	</fieldset>
	<fieldset class="">
	<legend><strong>Identifiants</strong></legend>	 
		<p> 
			<label for="login">Login :</label>
			<input name="login" type="text" value="<c:out value="${utilisateur.getLogin()}"/>" placeholder="Login" /> 
			<span class="erreur">${form.erreurs['login']}</span>
		</p>
		<p> 
			<label for="mdp">Mot de passe :</label>
			<input name="mdp" type="password" value="" placeholder="Mot de passe" /> 
			<span class="erreur">${form.erreurs['mdp']}</span>
		</p>
		<p> 
			<label for="mdp2">Confirmation du mot de passe :</label>
			<input name="mdp2" type="password" value="" placeholder="Confirmation" /> 
		</p>
	</fieldset>
	<fieldset>	
	<legend>Autres</legend>
	Si votre option n'est pas dans liste ci-dessous, veuillez contacter l'administrateur.
		<p>
			<label for="option">Options :</label>
			<select name="option">
				<c:forEach items="${lesOptions}" var="option"> 
					<option value="<c:out value="${option.getId()}" />" > <c:out value="${option.getNom()}" /></option> 
				</c:forEach> 
			</select>
		</p>
	</fieldset>	
		<p class="${empty form.erreurs ? 'succès' : 'erreur'}">${form.resultat}</p>
		<input type="submit" value="Sauvegarder" />
	</form>

<script type="text/javascript">
 	//alert("erer");
</script>
</body>
</html>