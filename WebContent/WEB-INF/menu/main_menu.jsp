<%@ page language="java" import="com.gesta.constantes.Constantes" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page trimDirectiveWhitespaces="true" %>

<div class="top_bande">	
	<a href="${URL }/accueil" style="font-size:30px; font-weight:bold;">GESTA</a>
	<div style="float:right; right:0px; top:0px; relative"> 
	
	<ul>	
		<li>
			<input name="Recherche" type="text" placeHolder="Recherche" size="30"/> 
		</li>
		<li><a href="${URL }/accueil">Accueil</a></li>
		<li>
			<c:if  test="${not empty sessionUtilisateur}" var="isUser">
				<c:choose> 
					<c:when test="${sessionUtilisateur.getAffiliation()==affiliation_eleve }">
						<a href="${URL }/stages/liste?messtages"> Mes stages</a>
						</c:when>
					<c:when test="${sessionUtilisateur.getAffiliation()==affiliation_enseignant }">
						<a href="${URL }/users/liste?meseleves"> Mes élèves</a>
						</c:when>
				</c:choose> 
			</c:if> 
		</li>
		<li><a href="${URL }/stages/liste">Consulter Stages</a></li> 
		<li>
			<c:if  test="${not empty sessionUtilisateur}" var="isUser">
				<a href="${URL }/home">${sessionUtilisateur.getContact().getNom().toUpperCase()}</a>
			</c:if> 
		</li>
		<li>
			<c:if  test="${empty sessionUtilisateur}" var="isUser">
				<a href="${URL }/usersConnexion">Connexion</a> 
			</c:if> 	
		</li>
		<li>
			<c:if  test="${not empty sessionUtilisateur}" var="isUser">
				<a href="${URL }/users/deconnexion">Déconnexion</a> 
			</c:if>
		</li>
	</ul>
	</div>
</div>