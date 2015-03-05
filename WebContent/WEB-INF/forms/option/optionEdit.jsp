<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<form method="post" action="testjdbc">
		<h1>Edition d'une option</h1> 
		<!-- Informations principales -->
		<fieldset class="">
		<legend>Informations </legend>
			<p>
				<label for="nom">Nom :</label>
				<input id="" class="" name="nom" type="text" value="<c:out value="${option.getNom()}"/>" placeholder="Nom de l'option"/>
				<span class="erreur">${form.erreurs['nom']}</span>
			</p>
	 		<p class="${empty form.erreurs ? 'succès' : 'erreur'}">${form.resultat}</p>
			<input type="submit" value="Sauvegarder" />
	</form>