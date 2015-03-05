<%@page import="com.gesta.forms.GestionFormulaire"%>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8" 
	import="com.gesta.forms.GestionFormulaire.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%  pageContext.setAttribute("Formulaire", new GestionFormulaire()); %>
	<p class="erreur">${form.getResultat() }</p>

	<!-- Informations principales -->
	<fieldset class="">
		<legend> Informations principales</legend>
		<p>
			<label for="stg_type">Type :</label>
			<c:if test="${not empty listeTypeStage }">
			<select name="stg_type">
				<c:forEach items="${listeTypeStage}" var="item">
					<c:set var="testlocal" value="${item.getId_typestage() == stage.getId_typestage() ? 'checked' : ''}" /> 
					<option value="${item.getId_typestage()}" ${testlocal }><c:out value="${item.getTst_nom()}" /></option>
				</c:forEach>
			</select>
			</c:if> 
		</p>
		<p>
			<label for="stg_sujet">Sujet :</label>
			<input id="" class="" name="stg_sujet" type="text" value="${stage.get_sujet() }" placeholder="Intitulé du stage" size="80"/>
			<span class="erreur">${form.erreurs['stg_sujet']}</span>
		</p>
		<p>
			<label for="stg_objet">Objet : </label>
			<input id="" class="" name="stg_objet" type="text" value="${stage.get_objet() }" placeholder="Objet du stage" size="80"/>
			<span class="erreur">${form.erreurs['stg_objet']}</span>
		</p>
		<p>
			<label for="stg_domaine">Domaine : </label>
			<input id="" class="" name="stg_domaine" type="text" value="${stage.get_domaine() }" placeholder="Le secteur d'activités" size="80"/>
			<span class="erreur">${form.erreurs['stg_domaine']}</span>
		</p>
		<p>
			<label for="stg_convention">Type de convention :</label> 
			<c:if test="${not empty listeTypeConvention }"> 
				<c:forEach items="${listeTypeConvention}" var="item"> 
					<c:set var="testlocal" value="${item.getId_typeconvention() == stage.getId_typeconvention() ? 'checked' : ''}" /> 
					<c:out value="${item.getTcv_nom()}" /><input name="stg_convention" type ="radio" value="${item.getId_typeconvention()}" ${testlocal}/>
				</c:forEach> 
			</c:if>  
		</p>	
	</fieldset>
	
	<p class="erreur">${form.getResultat() }</p>
	<!-- Caractéristiques du stage -->
	<fieldset>
	<legend>Caractéristiques du stage</legend>
		<p>
			<label for="stg_debut_stage">Début du stage :</label>
			<input id="" class="" name="stg_debut_stage" value="${Formulaire.DateToString(stage.get_date_debut()) }" type="text"   />
			(Format jj/mm/aaaa, Ex : 30/12/2015)</br>
			<span class="erreur">${form.erreurs['stg_debut_stage']}</span>
		</p>
		<p>
			<label for="stg_fin_stage">Fin du stage :</label>
			<input id="" class="" name="stg_fin_stage" value="${Formulaire.DateToString(stage.get_date_fin()) }" type="text" />
			(Format jj/mm/aaaa, Ex : 30/12/2015)</br>
			<span class="erreur">${form.erreurs['stg_fin_stage']}</span>
		</p>
		<p>
			<label for="stg_duree">Durée en mois :</label>
			<input id="" class="" name="stg_duree" type="text" value="${stage.get_duree() }" size="4" /> 
			<span class="erreur">${form.erreurs['stg_duree']}</span> 
		</p>
		<p>
			<label for="stg_gratification">Gratification (en Euros) :</label> Ex : 950,56 € 
			<input id="" class="" name="stg_gratification" type="text" value="${stage.get_gratification() }" size="4" />  
			<span class="erreur">${form.erreurs['stg_gratification']}</span> 
		</p>
		<p>
			<label for="stg_competences">Compétences requises :</label> </br>
			<textarea id="" class="" name="stg_competences" type="text" cols="80" rows="5">${stage.get_competences() }</textarea>  </br>
			<span class="erreur">${form.erreurs['stg_gratification']}</span> 
		</p>
		<p>
			<label for="stg_horaires">Horaires :</label> 
			<input id="" class="" name="stg_horaires" type="text" value="${stage.get_horaires() }" size="15" />  (Ex : 8h-15h)</br> 
			<span class="erreur">${form.erreurs['stg_horaires']}</span> 
		</p>
		<p>
			Jours de travail :
			Lundi <input id="" class="" name="stg_jours_lu" type="checkbox" value="1" checked/>  
			Mardi <input id="" class="" name="stg_jours_ma" type="checkbox" value="1" checked/>  
			Mercredi <input id="" class="" name="stg_jours_me" type="checkbox" value="1" checked/>  
			Jeudi <input id="" class="" name="stg_jours_je" type="checkbox" value="1" checked/>  
			Vendredi <input id="" class="" name="stg_jours_ve" type="checkbox" value="1" checked/>  
			Samedi <input id="" class="" name="stg_jours_sa" type="checkbox" value="1" />  
			Dimanche <input id="" class="" name="stg_jours_di" type="checkbox" value="1" />   
			<span class="erreur">${form.erreurs['stg_jours']}</span>  
		</p>
		<p>
			<label for="stg_h_week">Nombre d'heures de travail par semaine :</label> 
			<input id="" class="" name="stg_h_week" type="text" value="${stage.get_h_week() }" size="15" /> (Exmple: 35 pour 35h/semaine) <br>  
			<span class="erreur">${form.erreurs['stg_h_week']}</span>  
		</p>
		<p>
			<label for="stg_w_ferie">Travail le week-end et/ou les jours fériés (A préciser dans la description):</label> </br>
			<c:set var="testlocal1" value="${1 == stage.get_w_ferie() ? 'checked' : ''}" /> 		   
			<c:set var="testlocal0" value="${0 == stage.get_w_ferie() ? 'checked' : ''}" /> 		   
			oui<input id="" class="" name="stg_w_ferie" type="radio" value="1" size="15" ${testlocal1 } />     
			non<input id="" class="" name="stg_w_ferie" type="radio" value="0" size="15" ${testlocal0 }/>  
			<span class="erreur">${form.erreurs['stg_w_ferie']}</span>  
		</p>
		<p>
			<label for="stg_nuit">Travail de nuit :</label>   </br>
			<c:set var="testlocal1" value="${1 == stage.get_nuit() ? 'checked' : ''}" /> 		   
			<c:set var="testlocal0" value="${0 == stage.get_nuit() ? 'checked' : ''}" />  
			oui<input id="" class="" name="stg_nuit" type="radio" value="1" size="15" ${testlocal1 } />     
			non<input id="" class="" name="stg_nuit" type="radio" value="0" size="15" ${testlocal0 }/>		   
			     
			<span class="erreur">${form.erreurs['stg_nuit']}</span>  
		</p>
		<p>
			<label for="stg_avantages">Avantages (Si oui préciser lesquels, 255 caractères max) :</label> 
			<textarea id="" class="" name="stg_avantages" type="text" cols="80" rows="3" >${stage.get_avantages() }</textarea>       
			<span class="erreur">${form.erreurs['stg_avantages']}</span>  
		</p>
		
	</fieldset>
	
	<p class="erreur">${form.getResultat() }</p>
	<!-- Entreprise -->
	<fieldset>
	<legend>Informations sur l'entreprise</legend>
		<p>
			<label for="ent_nom">Dénomination : </label>
			<input id="" class="" name="ent_nom" type="text" value="${stage.getEntreprise().getEnt_nom() }" placeholder="" size="60"/> 
			<span class="erreur">${form.erreurs['ent_nom']}</span> 
		</p>
		<p>
			<label for="ent_type">Type : </label> 
			<c:if test="${not empty listeTypeEntreprise }">
			<select name="ent_type">
				<c:forEach items="${listeTypeEntreprise}" var="item">
					<c:set var="testlocal" value="${item.getId_typeentreprise() == stage.getEntreprise().getId_typeentreprise() ? 'checked' : ''}" /> 
					<option value="${item.getId_typeentreprise()}" ${testlocal }><c:out value="${item.getTen_nom()}" /></option>
				</c:forEach>
			</select>
			</c:if> 
		</p>
		<p>
			<label for="ent_secteur">Secteur : </label>
			<input id="" class="" name="ent_secteur" type="text" value="${stage.getEntreprise().getEnt_secteur() }" placeholder="" size="80"/> </br>
			<span class="erreur">${form.erreurs['ent_secteur']}</span> 
		</p>
		<p>
			<label for="ent_siret">N° Siret : </label>
			<input id="" class="" name="ent_siret" type="text" value="${stage.getEntreprise().getEnt_siret() }" placeholder=""/> 
			<span class="erreur">${form.erreurs['ent_siret']}</span> 
		</p>
		<p> 			
			Taille de l'entreprise : <br>
			<c:if test="${not empty listeTailleEntreprise }"> 
				<c:forEach items="${listeTailleEntreprise}" var="item"> 
					<c:set var="testlocal" value="${item.getId_tailleentreprise() == stage.getEntreprise().getId_tailleentreprise() ? 'checked' : ''}" /> 
					<c:out value="${item.getTae_nom()}" /><input name="ent_taille" type ="radio" value="${item.getId_tailleentreprise()}" ${testlocal}/>
				</c:forEach> 
			</c:if>
		</p>
		<p>
			<label for="ent_groupe">L'entreprise appartient à un groupe : </label>
			<input name="ent_groupe" type="text" value="${stage.getEntreprise().getEnt_groupe() }"size="50"/></br> 
		</p> 
		<c:import url="${form_localisation }"> 
			<c:param name="intitule" value="Adresse postale du siege sociale"/>
			<c:param name="prefix" value="ent"/>
		</c:import>
	
	</fieldset>
	
	<p class="erreur">${form.getResultat() }</p>
	<!-- Contacts au sein de l'entreprise -->
	<fieldset>
	<legend>Contacts au sein de l'entreprise</legend>
		<c:import url="${form_contact }"> 
			<c:param name="intitule" value="Departement Ressources Humaines"/>
			<c:param name="prefix" value="rh"/>
		</c:import> 
		<c:import url="${form_contact }"> 
			<c:param name="intitule" value="Encadrant de stage"/>
			<c:param name="prefix" value="enc"/>
		</c:import>  
		<c:import url="${form_contact }"> 
			<c:param name="intitule" value="Represenant legal de l'entreprise"/>
			<c:param name="prefix" value="rep"/>
		</c:import> 	
	</fieldset>
	
	<p class="erreur">${form.getResultat() }</p>
	<!-- Localisation du stage -->
	<fieldset>
	<legend>Lieu du stage</legend> 
		<p>  
			<c:import url="${form_localisation }"> 
				<c:param name="intitule" value="Lieu du stage si different du siege sociale"/>
				<c:param name="prefix" value="sta"/>
			</c:import>   
		</p>
	</fieldset>
	
	<p class="erreur">${form.getResultat() }</p>
	<!-- Résumé -->
	<fieldset> 
		<legend>Résumé du stage</legend>
		<p>
			<textarea id="" class="" name="stg_resume" placeholder="Résumé de votre stage" cols="80" rows="10">${stage.get_resume() }</textarea>
			<span class="erreur">${form.erreurs['stg_resume']}</span>
		</p> 
	</fieldset>
	
	<c:set var="styleLocal" value="${sessionUtilisateur.getAffiliation()==affiliation_eleve ? 'visibility:hidden' : '' }"></c:set>
	<p class="erreur" style="${styleLocal}">${form.getResultat() }</p>
	<!-- Espace scolarité -->
	<fieldset style="${styleLocal}">
	<legend>Espace Scolarité</legend>
		<c:set var="disabled" value="${sessionUtilisateur.getAffiliation()==affiliation_eleve ? '' : '' }"/> 
		<p>
			<label for="stg_datesoutenance">Date de soutenance : (<i>Ex : 30/12/2015</i>)</label>
			<input id="" class="" name="stg_datesoutenance" type="text" value="${stage.get_datesoutenance()}" placeholder="15/10/2015" ${disabled } /> 
			<span class="erreur">${form.erreurs['stg_datesoutenance']}</span>
		</p> 
		<p>
			<label for="stg_jury">Membres du jury</label> <i>(Ecrivez les noms en espaçant d'une virgule)</i> : </br>
			<textarea id="" class="" name="stg_jury" placeholder="Prénom Nom, Prénom Nom, ..." cols="80" rows="2" ${disabled } >${stage.get_jury() }</textarea> 
			<span class="erreur">${form.erreurs['stg_jury']}</span>
		</p>
	</fieldset> 
	<input class="btn btn-primary" type="submit" value="Sauvegarder" />