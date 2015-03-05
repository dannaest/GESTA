<%@page import="com.gesta.forms.GestionFormulaire"%>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"   %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%  pageContext.setAttribute("Formulaire", new GestionFormulaire()); %> 

	<table style="width:100%" class="table table-striped table-condensed table-hover col-xs-12" border="0" cellspacing="0" cellpadding="10" bordercolor="gray">
	<tbody>
		<!-- Informations principales --> 
		<tr><td colspan="3"><h2> Informations principales</h2></td></tr> 
		<tr><th>Sujet</th><td></td><td>${stage.get_sujet() }</td></tr> 
		<tr><th>Objet</th><td></td><td>${stage.get_objet() }</td></tr> 
		<tr><th>Type</th><td></td><td>
			<c:if test="${not empty listeTypeStage }"> 
				<c:forEach items="${listeTypeStage}" var="item">
					<c:if test="${item.getId_typestage() == stage.getId_typestage() }" >${item.getTst_nom()}</c:if> 
				</c:forEach> 
			</c:if>
		</td></tr> 
		<tr><th>Domaine</th><td></td><td>${stage.get_domaine() }</td></tr> 
		<tr><th>Type de convention</th><td></td><td>
			<c:if test="${not empty listeTypeConvention }"> 
				<c:forEach items="${listeTypeConvention}" var="item"> 
					<c:if test="${item.getId_typeconvention() == stage.getId_typeconvention()}">${item.getTcv_nom()}</c:if>
				</c:forEach> 
			</c:if>
		</td></tr>  
		
		<!-- Caractéristiques du stage --> 
		<tr><td colspan="3"><h2> Caractéristiques du stage</h2></td></tr> 
		<tr><th>Période</th><td></td><td> ${Formulaire.DateToString(stage.get_date_debut()) } - 
		 									${Formulaire.DateToString(stage.get_date_fin()) } </td></tr> 
		<tr><th>Durée en mois</th><td></td><td>${stage.get_duree() }</td></tr> 
		<tr><th>Gratification (en Euros)</th><td></td><td>${stage.get_gratification() }</td></tr> 
		<tr><th>Compétences requises</th><td></td><td>${stage.get_competences() }</td></tr> 
		<tr><th>Horaires</th><td></td><td> ${stage.get_horaires() }</td></tr> 
		<tr><th>Heures/semaine</th><td></td><td> ${stage.get_h_week() }</td></tr> 
		<tr><th>Week-end et/ou jours fériés</th><td></td><td><c:set var="testlocal1" value="${1 == stage.get_w_ferie() ? 'oui' : 'non'}" /> ${testlocal1 }  </td></tr> 
		<tr><th>Travail de nuit</th><td></td><td><c:set var="testlocal1" value="${1 == stage.get_nuit() ? 'oui' : 'non'}" /> ${testlocal1 } </td></tr> 
		<tr><th>Avantages</th><td></td><td> ${stage.get_avantages() } </td></tr>  
		
		<!-- Entreprise --> 
		<tr><td colspan="3"><h2> Informations sur l'entreprise </h2></td></tr> 
		<tr><th>Dénomination</th><td></td><td>${stage.getEntreprise().getEnt_nom() }</td></tr> 
		<tr><th>Type</th><td></td><td>
		<c:if test="${not empty listeTypeEntreprise }"> 
				<c:forEach items="${listeTypeEntreprise}" var="item">
					<c:if test="${item.getId_typeentreprise() == stage.getEntreprise().getId_typeentreprise() }" >
					${item.getTen_nom()}</c:if>  
				</c:forEach> 
			</c:if> 
		</td></tr> 
		<tr><th>Secteur</th><td></td><td>${stage.getEntreprise().getEnt_secteur() }</td></tr> 
		<tr><th>N° Siret</th><td></td><td>${stage.getEntreprise().getEnt_siret() }</td></tr> 
		<tr><th>Taille de l'entreprise</th><td></td><td>
		<c:if test="${not empty listeTailleEntreprise }"> 
				<c:forEach items="${listeTailleEntreprise}" var="item"> 
					<c:if test="${item.getId_tailleentreprise() == stage.getEntreprise().getId_tailleentreprise()}" >
					${item.getTae_nom()}</c:if>
				</c:forEach> 
			</c:if>
		</td></tr> 
		<tr><th>Appartient à un groupe</th><td></td><td>${not empty stage.getEntreprise().getEnt_groupe() ? stage.getEntreprise().getEnt_groupe() : 'non' }</td></tr> 
		<tr><th>Adresse </th><td></td><td>
			 <!-- Adresse entreprise -->
			 ${ stage.getEntreprise().getLieu().getLieu_numero()}
			 ${ stage.getEntreprise().getLieu().getLieu_voie() }
			 ${ stage.getEntreprise().getLieu().getLieu_cp()} 
			 ${ stage.getEntreprise().getLieu().getLieu_ville()}		
		</td></tr>  
		
		<!-- Contacts importants --> 	
		<tr><td colspan="3"><h2>  Contacts importants</h2></td></tr> 
		<tr><th>Représentant</th><td></td><td>
			${stage.getEntreprise().getCnt_Representant().getNom()} <span> </span>
			${stage.getEntreprise().getCnt_Representant().getPrenom()} <span> </span>
			${stage.getEntreprise().getCnt_Representant().getMail()}  <span> </span>
			${String.valueOf(stage.getEntreprise().getCnt_Representant().getTel())}
		</td></tr> 
		<tr><th>Ressources humaines</th><td></td><td> 
		${stage.getEntreprise().getCnt_Rh().getNom()}<span> </span>
		${stage.getEntreprise().getCnt_Rh().getPrenom()} <span> </span>
		${stage.getEntreprise().getCnt_Rh().getMail()}  <span> </span>
		${String.valueOf(stage.getEntreprise().getCnt_Rh().getTel())}
		</td></tr> 
		<tr><th>Encadrant </th><td></td><td>
		${stage.getContactENC().getNom()} <span> </span>
		${stage.getContactENC().getPrenom()}<span> </span>
		${stage.getContactENC().getMail()}<span> </span>
		${stage.getContactENC().getTel()} 
		</td></tr> 
		
		<!-- Localisation du stage --> 	
		<tr><td colspan="3"><h2>Lieu du stage</h2></td></tr> 
		<tr><th>Adresse</th><td></td><td> 
		 ${ stage.getLieu().getLieu_numero()}<span> </span>
		 ${ stage.getLieu().getLieu_voie() }<span> </span>
		 ${ stage.getLieu().getLieu_cp()} <span> </span>
		 ${ stage.getLieu().getLieu_ville()} <span> </span>
		 </td></tr> 
		
		<!-- Soutenance --> 	
		<tr><td colspan="3"><h2> Soutenance</h2></td></tr> 
		<tr><th>Date de soutenance</th><td></td><td>${stage.get_datesoutenance()}</td></tr> 
		<tr><th>Membres du jury</th><td></td><td>${stage.get_jury() } </td></tr> 
		
		<!-- Résumé --> 	
		<tr><td colspan="3"><h2> Résumé du stage</h2></td></tr> 
		<tr><td colspan="3">${stage.get_resume() }</td></tr> 
	</tbody>
	</table>   