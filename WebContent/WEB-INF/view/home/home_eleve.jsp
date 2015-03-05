 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<h2>Mon compte</h2>
<table class="table table-striped table-condensed table-hover col-xs-12" border="0" cellspacing="0" cellpadding="10" bordercolor="gray">  
	<tbody>
	<tr><th>Nom </th><td></td><td>${ sessionUtilisateur.getContact().getNom() }</td></tr>
	<tr><th>Prénom </th><td></td><td>${ sessionUtilisateur.getContact().getPrenom() }</td></tr>
	<tr><th>Affiliation </th><td></td><td>${ sessionUtilisateur.getAffiliation() }</td></tr>
	
	<c:choose> 
		<c:when test="${sessionUtilisateur.getAffiliation()==affiliation_eleve }">
			<tr><th>Cursus </th><td></td><td>${ sessionUtilisateur.getCursus() }</td></tr>
			<tr><th>Formation </th><td></td><td> </td></tr>
			<tr><th>Groupe </th><td></td><td>${ sessionUtilisateur.getGroupe() }</td></tr>
		</c:when>
		<c:when test="${sessionUtilisateur.getAffiliation()==affiliation_enseignant }">
			<tr><th>Statut(s) </th><td></td><td>
				<c:forEach items="${ sessionUtilisateur.getStatuts() }" var="statut"> 
					${ statut.getNom() }
				</c:forEach>
				
			</td></tr>
		</c:when>
	</c:choose> 
	</tbody>
</table>