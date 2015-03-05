package com.gesta.forms;
 
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.gesta.beans.Contact;
import com.gesta.beans.Encadrant;
import com.gesta.beans.Entreprise;
import com.gesta.beans.Localisation;
import com.gesta.beans.Stage_Entreprise;
import com.gesta.beans.Stage_lieu;
import com.gesta.beans.Stages; 
import com.gesta.beans.User;
import com.gesta.beans.Users_stages;
import com.gesta.constantes.Constantes;
import com.gesta.dao.ContactDao;
import com.gesta.dao.DAOException;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.EncadrantDaoImpl;
import com.gesta.dao.EntrepriseDaoImpl;
import com.gesta.dao.LocalisationDaoImpl;
import com.gesta.dao.Stage_EntrepriseDaoImpl;
import com.gesta.dao.Stage_lieuDaoImpl;
import com.gesta.dao.StagesDaoImpl;
import com.gesta.dao.Stages_optionsDaoImpl;
import com.gesta.dao.Users_stagesDaoImpl;

public class FormStage extends GestionFormulaire {
	private User user = null;
	private StagesDaoImpl stageDao = null;
	private EntrepriseDaoImpl entrepriseDao = null;
	private ContactDao contactDao = null;
	private EncadrantDaoImpl encadrantDao = null;
	private LocalisationDaoImpl localisationDao = null;
	private Stage_EntrepriseDaoImpl stage_entrepriseDao = null;
	private Stage_lieuDaoImpl stage_lieuDao = null;
	private Stages_optionsDaoImpl stage_optionDao = null;
	private Users_stagesDaoImpl user_stageDao = null;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	public FormStage(StagesDaoImpl stageDao,HttpServletRequest request,User user){
    	this.stageDao = stageDao; 
    	this.user = user;
    	init(request);
    }
	private void init(HttpServletRequest request) {
		ServletContext sc = request.getServletContext();
		DAOFactory dao = (DAOFactory)sc.getAttribute(CONF_DAO_FACTORY);
		entrepriseDao = (EntrepriseDaoImpl) dao.getEntrepriseDao();
		contactDao = (ContactDao) dao.getContactDao();
		localisationDao = (LocalisationDaoImpl) dao.getLocalisationDao();
		stage_entrepriseDao = (Stage_EntrepriseDaoImpl)dao.getStage_EntrepriseDao();
		stage_lieuDao = (Stage_lieuDaoImpl)dao.getStage_lieuDao();
		stage_optionDao = (Stages_optionsDaoImpl)dao.getStages_optionsDao();
		user_stageDao = (Users_stagesDaoImpl)dao.getUsers_stagesDao();
		encadrantDao = (EncadrantDaoImpl)dao.getEncadrantDao();
	}
	
	/**
	 * Cette fonction controle chacun des champs, crée le stage puis le retourne.  
	 * Dès qu'un des champs est mal rempli, l'enrgistrement du stage s'arrete
	 * @param request
	 * @return l'objet stage.
	 */
	public Stages ajoutStage(HttpServletRequest request) {
		
		Stages stage = new Stages();
		Entreprise ent = new Entreprise(); 
		
		/*Récupération des valeurs contenues dans le formulaire*/
		try {
			lireFormulaire(request, stage, ent);
			
			if ( erreurs.isEmpty() ) { 
				int step = 0;
				try { 
					/*Création entreprise*/
					Long id_entreprise = entrepriseDao.creer(ent); step++;
					
					/*localisation stage*/
					Long id_lieu_stage = localisationDao.creer(stage.getLieu());step++;
					
					/**--CREATION DU STAGE--**/
					Long id_stage = stageDao.creer( stage ); 
					
					/*Lien stage_entreprise*/ 
					stage_entrepriseDao.creer(new Stage_Entreprise(id_stage,id_entreprise));
					
					/*Lien stage_lieu*/
					stage_lieuDao.creer(new Stage_lieu(id_stage,id_lieu_stage,stage.get_date_debut(),stage.get_date_fin()));
					
					/*Création encadrant*/
					Long id_contact_enc = contactDao.creer(stage.getContactENC());
					Encadrant enc = new Encadrant(null,id_stage,id_contact_enc);
					encadrantDao.creer(enc); 
					
					/*creer jury si c'est possible : Le jury est directement enregistré dans la table stage*/
					/*creer lien stages_options si possible : il faudrait pour ça pouvoir récupérer l'option de l'élève qui n'est pas disponible dans LDAP*/ 
					//------------Long id_option = new Long(this.user.get)
					//------------stage_optionDao.creer(new Stages_options(id_stage,id_option));
					
					/*creer lien users_stages  */
					user_stageDao.creer(new Users_stages(this.user.getId(),id_stage));
					/*creer lien tuteur (tuteur du stage) */ 

				    resultat = "Succès de l'ajout.";
				
				}catch(DAOException e) {
					annuleProcedureEnregistrementStage(step);
					resultat = "Echec de l'ajout.";  
				}
			} else {
			     resultat = "Echec de l'ajout.";  
			}
			
		}catch(NumberFormatException e) { 
			resultat = "Un des nombres saisis n'a pas le bon format.";
			setErreur("","une erreur"); 
			e.printStackTrace();
		} catch(FormValidationException e) {  
			resultat = e.getMessage();
			setErreur("","une erreur"); 
			e.printStackTrace();
		} catch(Exception e) {  
			resultat = "Une erreur est survenue, veuillez contacter l'administrateur.";
			setErreur("","une erreur");
			/*StackTraceElement[] stack = e.getStackTrace();
			System.out.println(this.getClass().getName()+" :: "+e.getClass().getName()+"\n"
					+(stack!=null ? stack[stack.length-1].getLineNumber() : "\n")
					+e.getMessage());*/
			e.printStackTrace();
		}  

		return stage;
	}
	

	/**
	 * Cette fonction controle chacun des champs, crée le stage puis le retourne.  
	 * Dès qu'un des champs est mal rempli, l'enrgistrement du stage s'arrete
	 * @param request
	 * @return l'objet stage contenant les données mises à jour.
	 */
	public Stages editStage(HttpServletRequest request, Stages old_stage) {
		
		Stages stage = new Stages();
		Entreprise ent = new Entreprise(); 
		
		/*Récupération des valeurs contenues dans le formulaire*/
		try {
			lireFormulaire(request, stage, ent);
			
			if ( erreurs.isEmpty() ) { 
				int step = 0;
				try { 
					/*Création entreprise*/
					entrepriseDao.mettreAJour(old_stage.getEntreprise(), ent); step++;
					
					/*localisation stage*/
					localisationDao.mettreAJour(old_stage.getLieu(), stage.getLieu()) ;step++;
					
					/**--CREATION DU STAGE--**/
					stageDao.mettreAJour(old_stage, stage); 
					
					/*Lien stage_entreprise : rien ne change*/  
					
					/*Lien stage_lieu : rien ne change*/

					/*Encadrant : seul le contact change */
					contactDao.mettreAJour(old_stage.getContactENC(), stage.getContactENC());  
					
					/*creer lien users_stages : rien ne change */

					/*creer lien tuteur (tuteur du stage) : rien ne change */ 

				    resultat = "Succès de la modification";
				
				}catch(DAOException e) {
					annuleProcedureEnregistrementStage(step);
					resultat = "Echec de la modification.";  
					e.printStackTrace();
				}
			} else {
			     resultat = "Echec de la modification.";  
			}
			
		}catch(NumberFormatException e) { 
			resultat = "Un des nombres saisis n'a pas le bon format.";
			setErreur("","une erreur"); 
			e.printStackTrace();
		} catch(FormValidationException e) {  
			resultat = e.getMessage();
			setErreur("","une erreur"); 
			e.printStackTrace();
		} catch(Exception e) {  
			resultat = "Une erreur est survenue, veuillez contacter l'administrateur.";
			setErreur("","une erreur");
			/*StackTraceElement[] stack = e.getStackTrace();
			System.out.println(this.getClass().getName()+" :: "+e.getClass().getName()+"\n"
					+(stack!=null ? stack[stack.length-1].getLineNumber() : "\n")
					+e.getMessage());*/
			e.printStackTrace();
		}  
		List<Stages> stages = stageDao.lister(new String[] {"id_stage"}, new String[] {old_stage.getId_stage().toString()});
		return stages.get(0); 
	}
	
	/**
	 * En cas d'échec de la procédure, d'enregistrement d'un stage, il faudrait effacer toutes les données enregistrées
	 * dans la procédure en cours 
	 * @author Nacder
	 * */
	private void annuleProcedureEnregistrementStage(int step) {
		
	}

	private void lireFormulaire(HttpServletRequest request,Stages stage, Entreprise ent) throws Exception{
		/*Informations principales*/ 
		
			String buffer = getValeurChamp(request, "stg_type");
				stage.setId_typestage(Long.valueOf(buffer));		
			buffer = getValeurChamp(request, "stg_sujet");
				if(buffer==null || buffer.length()==0) setErreur("","Veuillez remplir tous les champs. ");
				else stage.set_sujet(buffer);
			buffer = getValeurChamp(request, "stg_objet");
				if(buffer==null || buffer.length()==0) setErreur("","Veuillez remplir tous les champs. ");
				else stage.set_objet(buffer);
			buffer = getValeurChamp(request, "stg_domaine");
				if(buffer==null || buffer.length()==0) setErreur("","Veuillez remplir tous les champs. ");
				else stage.set_domaine(buffer);
			buffer = getValeurChamp(request, "stg_convention");
				if(buffer==null || buffer.length()==0) stage.setId_typeconvention(new Long(1));
				else stage.setId_typeconvention(Long.valueOf(buffer));
			
			/*Caractéristiques du stage*/
			buffer = getValeurChamp(request, "stg_debut_stage");
				//vérifier le format aussi
				if(buffer==null || buffer.length()==0) setErreur("","Veuillez remplir tous les champs. ");
				else stage.set_date_debut(stringToDate(buffer));
			buffer = getValeurChamp(request, "stg_fin_stage");
				if(buffer==null || buffer.length()==0) setErreur("","Veuillez remplir tous les champs. ");
				else stage.set_date_fin(stringToDate(buffer));
			buffer = getValeurChamp(request, "stg_duree");
				if(buffer==null || buffer.length()==0) setErreur("","Veuillez remplir tous les champs. ");
				else stage.set_duree(Integer.valueOf(buffer));
			buffer = getValeurChamp(request, "stg_gratification");
				if(buffer==null || buffer.length()==0) setErreur("","Veuillez remplir tous les champs. ");
				else stage.set_gratification(buffer);
			buffer = getValeurChamp(request, "stg_competences"); 
			stage.set_competences(buffer);
			buffer = getValeurChamp(request, "stg_horaires");
				if(buffer==null || buffer.length()==0) setErreur("","Veuillez remplir tous les champs. ");
				else stage.set_horaires(buffer);
			buffer = getValeurChamp(request, "stg_jours_lu")+","+getValeurChamp(request, "stg_jours_ma")+","+getValeurChamp(request, "stg_jours_me")+","+getValeurChamp(request, "stg_jours_je")+","+getValeurChamp(request, "stg_jours_ve")+","+getValeurChamp(request, "stg_jours_sa")+","+getValeurChamp(request, "stg_jours_di");
			//pour reconstruire cette chaine : String rebuilt_record = Arrays.toString(fields).replace(", ", delim).replaceAll("[\\[\\]]", "");
				if(buffer==null || buffer.length()==0) setErreur("","Veuillez remplir tous les champs. ");
				else stage.set_jours(buffer);
			buffer = getValeurChamp(request, "stg_h_week");
				if(buffer==null || buffer.length()==0) stage.set_h_week(0);
				else stage.set_h_week(Integer.valueOf(buffer));
			buffer = getValeurChamp(request, "stg_w_ferie");
				if(buffer==null || buffer.length()==0)  stage.set_w_ferie(0);
				else stage.set_w_ferie(Integer.parseInt(buffer));
			buffer = getValeurChamp(request, "stg_nuit");
				if(buffer==null || buffer.length()==0)  stage.set_nuit(0);
				else stage.set_nuit(Integer.parseInt(buffer));
			buffer = getValeurChamp(request, "stg_avantages");
			stage.set_avantages(buffer);
			
			/*Entreprise*/
			
			buffer = getValeurChamp(request, "ent_nom");
				if(buffer==null || buffer.length()==0) setErreur("","Renseigner le nom de l'entreprise. ");
				else	ent.setEnt_nom(buffer);
			buffer = getValeurChamp(request, "ent_type");
			ent.setId_typeentreprise(Long.valueOf(buffer));
			buffer = getValeurChamp(request, "ent_secteur");
				if(buffer==null || buffer.length()==0) setErreur("","Renseigner le secteur de l'entreprise. ");
				else ent.setEnt_secteur(buffer);
			buffer = getValeurChamp(request, "ent_siret");
				if(buffer==null || buffer.length()==0 || buffer.length()!=Constantes.ENTREPRISE_NUMERO_SIRET) 
					setErreur("","Renseigner le bon numéro SIRET de l'entreprise. ");
				else ent.setEnt_siret(buffer);
			buffer = getValeurChamp(request, "ent_taille");
				if(buffer==null || buffer.length()==0)	ent.setId_tailleentreprise(Long.valueOf(1));
				else ent.setId_tailleentreprise(Long.valueOf(buffer));
			buffer = getValeurChamp(request, "ent_groupe");
				if(buffer==null || buffer.length()==0)  ent.setEnt_groupe(" ");
				else ent.setEnt_groupe(buffer);
			/*Lieu entreprise*/
			Hashtable<String,String> lieuEntreprise = getValeurChampLocalisation(request,"ent");
			ent.setLieu(new Localisation(lieuEntreprise));
			
			/*Contacts */
			Hashtable<String,String> cnt_rh = getValeurChampContact(request,"rh");
			ent.setCnt_Rh(new Contact(cnt_rh));
			Hashtable<String,String> cnt_rep = getValeurChampContact(request,"rep");
			ent.setCnt_Representant(new Contact(cnt_rep));
			Hashtable<String,String> cnt_enc = getValeurChampContact(request,"enc");			
			stage.setContactENC(new Contact(cnt_enc));
			
			stage.setEntreprise(ent);
			
			/*Lieu stage*/
			Hashtable<String,String> lieuStage = getValeurChampLocalisation(request,"sta");
			stage.setLieu(new Localisation(lieuStage));
			
			/*Soutenance*/
			buffer = getValeurChamp(request, "stg_datesoutenance");
			stage.set_datesoutenance(null);
			/*if(buffer==null || buffer.length()==0) stage.set_datesoutenance(null);
			else stage.set_datesoutenance(stringToTimestamp(buffer));*/
			buffer = getValeurChamp(request, "stg_jury");
			stage.set_jury(buffer);
			buffer = getValeurChamp(request, "stg_resume");  
			stage.set_resume(buffer);
			
			stage.setId_etatvalidation(new Long(0));
	}
}
