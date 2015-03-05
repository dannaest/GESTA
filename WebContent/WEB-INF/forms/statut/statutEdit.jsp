<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<form method="post" action="testjdbc">
		<h1>Edition d'un statut</h1> 
		<!-- Informations principales -->
		<fieldset class="">
		<legend>Informations </legend>
			<p>
				<label for="nom">Statut :</label>
				<input id="" class="" name="nom" type="text" value="<c:out value="${statut.getNom()}"/>" placeholder="Nouveau statut"/>
				<span class="erreur">${form.erreurs['nom']}</span>
			</p>
			<p>
				<label for="nivDroit">Niveau de droit :</label>
				<input id="" class="" name="nivDroit" type="text" value="<c:out value="${statut.getNivDroit()}"/>" placeholder="Niveau de droit correspondant"/>
				<span class="erreur">${form.erreurs['nivDroit']}</span>
				<br>
				<!-- Liste de tous les statuts avec les niveaux de droit correspondant  -->				
				<ul>
					<c:forEach items="${lesStatuts}" var="ldd"> 
						<li>${ldd.getNivDroit()} ${ldd.getNom()}</li> 
					</c:forEach> 
				</ul> 
			</p>
	 		<p class="${empty form.erreurs ? 'succès' : 'erreur'}">${form.resultat}</p>
			<input type="submit" value="Sauvegarder" />
		</fieldset>
	</form>