<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:if test="${not empty menuChoice }">
<c:choose>
	<c:when test="${ menuChoice == 'menu_action_stages'}">
		<div class="panel panel-info menu_action"> 
			<div class="panel-heading centered"><h3>Actions</h3></div>
			<div class="panel-body"> 
	
				<ul>
				<li><a href="${URL }/stages/edit/${stage.getId_stage()}">Editer</a></li> 
				<li><a href="${URL }/stages/delete">Supprimer</a></li> 
				
				<c:set var="color" value="${stage.getId_etatvalidation()==3 ? 'green' : stage.getId_etatvalidation()==2 ? 'orange' : 'red'  }" ></c:set>
				<c:set var="etatvalidation" value="${sessionUtilisateur.getAffiliation()==affiliation_eleve ? 2 : 3  }" ></c:set>
				<c:if test="${stage.getId_etatvalidation()!=3}">
					<li><a href="${URL }/stages/edit/${stage.getId_stage()}?valideSaisi=${etatvalidation}" style="color:${color}">Confirmer pour vue publique</a></li> 
				</c:if>
				<c:if test="${sessionUtilisateur.getAffiliation()!=affiliation_eleve && stage.getId_etatvalidation()==3}">
					<li><a href="${URL }/stages/edit/${stage.getId_stage()}?valideSaisi=1" style="color:red">Annuler la publication</a></li> 
				</c:if>
				<li><a href="${URL }/stages/pdf">Télécharger au format PDF</a></li> 
				<li><a href="#" id="impression"  onclick="imprimer_page()"  >Imprimer la fiche</a></li> 
				</ul>
			</div>
		</div>
	</c:when>
</c:choose>
</c:if>
