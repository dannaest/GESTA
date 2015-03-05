<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link href="webroot/style_menu.css" rel="stylesheet">
	<title>Edition d'un stage</title>
</head>
<body>
<div class="">GESTA</div>
	<form method="post" action="">
	
		<!-- Informations principales -->
	<fieldset class="">
		<legend> Informations principales</legend>
		<p>
			<label for="stg_type">Type :</label>
			<select name="stg_type" >
				<option value="1" >CME</option>
				<option value="2" >STING</option>
				<option value="3" >MASTER</option>
				<option value="4" >Césure</option>
				<option value="5" >Redoublant</option> 
			</select>
		</p>
		<p>
			<label for="stg_sujet">Sujet :</label>
			<input id="" class="" name="stg_sujet" type="text" value="" placeholder="Intitulé du stage"/>
		</p>
		<p>
			<label for="stg_objet">Objet : </label>
			<input id="" class="" name="stg_objet" type="text" value="" placeholder="Objet du stage"/>
		</p>
		<p>
			<label for="stg_domaine">Domaine : </label>
			<input id="" class="" name="stg_domaine" type="text" value="" placeholder="Le secteur d'activités"/>
		</p>
		<p>
			<label for="stg_resume">Résumé de stage :</label>
			<textarea id="" class="" name="stg_resume" type="" value="" placeholder="Résumé de votre stage" ></textarea>
		</p>
		<p>
			<label for="stg_convention">Type de convention :</label> 
			<select>
				<option value="1">Ecole</option>
				<option value="2">Entreprise</option>
				<option value="3">Association</option>
			</select>
		</p>	
	</fieldset>
	
	<!-- Caractéristiques du stage -->
	<fieldset>
	<legend>Caractéristiques du stage</legend>
		<p>
			<label for="stg_debut_stage">Début du stage :</label>
			<input id="" class="" name="stg_debut_stage_j" type="text" size="2" /> 
			/<input id="" class="" name="stg_debut_stage_m" type="text" size="2" />
			/<input id="" class="" name="stg_debut_stage_a" type="text" size="4" />
		</p>
		<p>
			<label for="stg_debut_stage">Fin du stage :</label>
			<input id="" class="" name="stg_debut_stage_j" type="text" size="2" /> 
			/<input id="" class="" name="stg_debut_stage_m" type="text" size="2" />
			/<input id="" class="" name="stg_debut_stage_a" type="text" size="4" />
		</p>
		<p>
			<label for="stg_duree">Durée en mois :</label>
			<input id="" class="" name="stg_duree" type="text" size="4" />  
		</p>
		<p>
			<label for="stg_gratification">Gratification (en Euros) :</label> Ex : 950,56 € 
			<input id="" class="" name="stg_gratification" type="text" size="4" />  
		</p>
		<p>
			<label for="stg_compétences">Compétences requises :</label> 
			<input id="" class="" name="stg_compétences" type="text" size="4" />  
		</p>
		<p>
			<label for="stg_horaires">Horaires :</label> 
			<input id="" class="" name="stg_horaires" type="text" size="15" />  
		</p>
		<p>
			<label for="stg_jours">Jours de travail :</label> 
			Lundi <input id="" class="" name="stg_jours" type="checkbox" value="1" />  
			Mardi <input id="" class="" name="stg_jours" type="checkbox" value="2" />  
			Mercredi <input id="" class="" name="stg_jours" type="checkbox" value="4" />  
			Jeudi <input id="" class="" name="stg_jours" type="checkbox" value="8" />  
			Vendredi <input id="" class="" name="stg_jours" type="checkbox" value="16" />  
			Samedi <input id="" class="" name="stg_jours" type="checkbox" value="32" />  
			Dimanche <input id="" class="" name="stg_jours" type="checkbox" value="64" />   
		</p>
		<p>
			<label for="stg_h_week">Nombre d'heures de travail par semaine :</label> 
			<input id="" class="" name="stg_h_week" type="text" size="15" />  
		</p>
		<p>
			<label for="stg_w_ferie">Travail le week-end et les jours fériés :</label> 
			<input id="" class="" name="stg_w_ferie" type="text" size="15" />  
		</p>
		<p>
			<label for="stg_nuit">Travail de nuit :</label> 
			<input id="" class="" name="stg_nuit" type="text" size="15" />  
		</p>
		<p>
			<label for="stg_avantages">Avantages (Si oui préciser le montant, sinon laisser vide) :</label> 
			<input id="" class="" name="stg_avantages" type="text" size="15" />   
		</p>
		
	</fieldset>
	
	<!-- Localisation du stage -->
	<fieldset>
	<legend>Lieu du stage</legend>	
		<strong>Lieu du stage si différent du siège sociale : </strong>
		<p>
			N° : <input id="" class="" name="sta_lieu_numero" type="text" size="4" /> 
			Voie : <input id="" class="" name="sta_lieu_voie" type="text" size="15"/> 
			Ville : <input id="" class="" name="sta_lieu_ville" type="text" size="10"/> 
			Code postal : <input id="" class="" name="sta_lieu_cp" type="text" size="5"/> 
			Pays :  
			<select name="sta_lieu_pays">
				<option value="France">France</option>
			</select>
		</p>
	</fieldset>
	
	<!-- Entreprise -->
	<fieldset>
	<legend>Informations sur l'entreprise</legend>
		<p>
			<label for="ent_nom">Dénomination : </label>
			<input id="" class="" name="ent_nom" type="text" value="" placeholder=""/> 
		</p>
		<p>
			<label for="ent_type">Type : </label> 
			<select name="ent_type">
				<option value="">type 1</option>
				<option value="">type 1</option>
			</select>
		</p>
		<p>
			<label for="ent_secteur">Secteur : </label>
			<input id="" class="" name="ent_secteur" type="text" value="" placeholder=""/> 
		</p>
		<p>
			<label for="ent_siret">N° Siret : </label>
			<input id="" class="" name="ent_siret" type="text" value="" placeholder=""/> 
		</p>
		<p>
			<label for="ent_taille">Taille de l'entreprise : </label> 			
			<10 <input id="" class="" name="ent_taille" type="checkbox" value="1" />  
			>10 <input id="" class="" name="ent_taille" type="checkbox" value="2" />  
			>50 <input id="" class="" name="ent_taille" type="checkbox" value="4" />  
			>100 <input id="" class="" name="ent_taille" type="checkbox" value="8" />  
			>500 <input id="" class="" name="ent_taille" type="checkbox" value="16" />  
			>1000 <input id="" class="" name="ent_taille" type="checkbox" value="32" />  
			>2500 <input id="" class="" name="ent_taille" type="checkbox" value="64" />  
		</p>
		<p>
			<label for="ent_groupe">L'entreprise appartient à un groupe : </label>
			<select name="ent_groupe_liste">
				<option value="">-- Indifférent --</option>
				<option value="">EADS</option>
				<option value="">EUROGICIEL</option>
				<option value="">GDF</option>
				<option value="">MAZARS</option>
			</select></br>
			Si le nom du groupe n'apparait pas dans la liste suivante, veuillez le renseigner dans le champ suivant.
			<input id="" class="" name="ent_groupe_2" type="text" value="" placeholder=""/> 
		</p>
		<strong>Adresse postale du siège sociale : </strong>
		<p>
			N° : <input id="" class="" name="ent_lieu_numero" type="text" size="4" /> 
			Voie : <input id="" class="" name="ent_lieu_voie" type="text" size="15"/> 
			Ville : <input id="" class="" name="ent_lieu_ville" type="text" size="10"/> 
			Code postal : <input id="" class="" name="ent_lieu_cp" type="text" size="5"/> 
			Pays :  
			<select>
				<option name="ent_lieu_pays" value="France">France</option>
			</select>
		</p>
	
	</fieldset>
	
	<!-- Contacts au sein de l'entreprise -->
	<fieldset>
	<legend>Contacts au sein de l'entreprise</legend>
		<strong>Département Ressources Humaines</strong>
		<p>
			Nom : <input id="" class="" name="rh_cnt_nom" type="text" /> 
			Prénom : <input id="" class="" name="rh_cnt_prenom" type="text" /> 
			E-mail : <input id="" class="" name="rh_cnt_mail" type="text" /> 
			Téléphone : <input id="" class="" name="rh_cnt_tel" type="text" />  
		</p>		
		<strong>Encadrant de stage</strong>
		<p>
			Nom : <input id="" class="" name="enc_cnt_nom" type="text" /> 
			Prénom : <input id="" class="" name="enc_cnt_prenom" type="text" /> 
			E-mail : <input id="" class="" name="enc_cnt_mail" type="text" /> 
			Téléphone : <input id="" class="" name="enc_cnt_tel" type="text" />  
		</p>
		<strong>Représenant légal de l'entreprise</strong>
		<p>
			Nom : <input id="" class="" name="rep_cnt_nom" type="text" /> 
			Prénom : <input id="" class="" name="rep_cnt_prenom" type="text" /> 
			E-mail : <input id="" class="" name="rep_cnt_mail" type="text" /> 
			Téléphone : <input id="" class="" name="rep_cnt_tel" type="text" />  
		</p>		
	</fieldset>
	
	<!-- Espace scolarité -->
	<fieldset>
	<legend>Espace Scolarité</legend>
		<p>
			<label for="stg_datesoutenance">Date de soutenances</label>
			<input id="" class="" name="stg_datesoutenance" type="date" value="" placeholder=""/> 
		</p>
		<p>
			<label for="stg_jury">Membres du jury</label></br>
			Ecrivez les noms en espaçant d'une virgule.</br>
			<input id="" class="" name="stg_jury" type="text" value="" placeholder=""/> 
		</p>
	</fieldset>
		<input type="submit" value="Sauvegarder" />
	</form>
</body>
</html>