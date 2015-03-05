package com.gesta.dao;
 
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
 























import com.gesta.beans.Contact;
import com.gesta.beans.Encadrant;
import com.gesta.beans.Entreprise;
import com.gesta.beans.Localisation;
import com.gesta.beans.Stage_Entreprise;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut;
import com.gesta.beans.Stages;
import com.gesta.beans.Stages;
import com.gesta.beans.Typestage;
import com.gesta.beans.User;
import com.gesta.beans.Users_stages;
import com.gesta.forms.GestionFormulaire;

import static com.gesta.dao.DAOUtilitaire.*;

public class StagesDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM stages ";
	//private static final String S_SQL_INSERT = "INSERT INTO stages (id_typestage,stg_sujet,stg_objet,stg_domaine,stg_resume,stg_datesoutenance,stg_jury,id_typeconvention,id_etatvalidation,stg_date_debut,stg_date_fin,stg_duree,stg_gratification,stg_competences,stg_horaires,stg_jours,stg_h_week,stg_w_ferie,stg_nuit,stg_avantages) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String S_SQL_INSERT = "INSERT INTO stages (id_typestage,stg_sujet,stg_objet,stg_domaine,stg_resume,stg_jury,id_typeconvention,id_etatvalidation,stg_date_debut,stg_date_fin,stg_duree,stg_gratification,stg_competences,stg_horaires,stg_jours,stg_h_week,stg_w_ferie,stg_nuit,stg_avantages,stg_datesoutenance) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())";
	private static final String S_SQL_DELETE = "DELETE FROM stages ";
	private static final String S_SQL_UPDATE = "UPDATE stages SET ";
	
	private Stage_EntrepriseDaoImpl stage_entrepriseDao = null;
	private EntrepriseDaoImpl entrepriseDao = null;

	
	StagesDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = S_SQL_UPDATE;
		this.SQL_DELETE = S_SQL_DELETE;
		stage_entrepriseDao = (Stage_EntrepriseDaoImpl)daoFactory.getStage_EntrepriseDao();
		entrepriseDao = (EntrepriseDaoImpl)daoFactory.getEntrepriseDao();
	}
	public Long creer(Stages stage) throws DAOException{
		String[] values = {stage.getId_typestage().toString(),
				stage.get_sujet(),
				stage.get_objet(),
				stage.get_domaine(),
				stage.get_resume(),
				//(stage.get_datesoutenance() != null ? stage.get_datesoutenance().toString(): " "),
				stage.get_jury(),
				stage.getId_typeconvention().toString(),
				stage.getId_etatvalidation().toString(),
				stage.get_date_debut().toString(),
				stage.get_date_fin().toString(),String.valueOf(stage.get_duree()),
				stage.get_gratification().toString(),stage.get_competences(),
				stage.get_horaires(),stage.get_jours(),String.valueOf(stage.get_h_week()),
				String.valueOf(stage.get_w_ferie()),String.valueOf(stage.get_nuit()),stage.get_avantages()}; 
		return super.creerMere(stage, values, true);  
	}

	public List<Stages> lister(String[] fields, String[] values) throws DAOException {   
		ContactDao contactDao = (ContactDaoImpl)daoFactory.getContactDao();
		TypestageDaoImpl typeStageDao = (TypestageDaoImpl)daoFactory.getTypestageDao();
		
		List<Stages> stages = new ArrayList<Stages>();
		Hashtable<String,String> hash = null;
		ResultSet resultSettmp = null, resultSettmp2= null;
		Stages stage = null; 
	    try {
	    	resultSettmp = super.listerMere(fields, values);
		    while ( resultSettmp.next() ) {
		    	//récupération du stage
		    	stage = map( resultSettmp );
		    	String id_stage = stage.getId_stage().toString();
		    	 		    	
		    	//récupération de l'entreprise dans laquelle s'est déroulé le stage
		    	hash = new Hashtable<String,String>();
		    	hash.put("tables", "stage_entreprise se NATURAL JOIN entreprise e");
		    	hash.put("fields", "e.id_entreprise");
		    	hash.put("conditions", "se.id_entreprise = e.id_entreprise AND se.id_stage = "+id_stage);
		    	resultSettmp2 = super.customised(hash);
		    	if(resultSettmp2.next()) { 
		    		List<Entreprise> ent = entrepriseDao.lister(new String[] {"id_entreprise"}, new String[] {resultSettmp2.getString("id_entreprise")});//EntrepriseDaoImpl.map(resultSettmp2);
		    		if(ent.size()>0)
		    			stage.setEntreprise(ent.get(0));
		    	}
		    	//récupération du lieu du stage
		    	hash = new Hashtable<String,String>();
		    	hash.put("tables", "stage_lieu sl NATURAL JOIN localisation l");
		    	hash.put("conditions", "sl.id_lieu = l.id_lieu AND sl.id_stage = "+id_stage);
		    	resultSettmp2 = super.customised(hash);
		    	if(resultSettmp2.next()) { 
		    		Localisation lieu = LocalisationDaoImpl.map(resultSettmp2);
		    		//récupération du pays
			    	stage.setLieu(lieu);
		    	}
		    	//encadrant
		    	hash = new Hashtable<String,String>();
		    	hash.put("tables", "encadrant e NATURAL JOIN contact c, stages s ");
		    	hash.put("conditions", "e.id_contact = c.id_contact AND e.id_stage = s.id_stage  AND e.id_stage = "+id_stage);
		    	resultSettmp2 = super.customised(hash);
		    	if(resultSettmp2.next()) { 
		    		Encadrant enc = EncadrantDaoImpl.map(resultSettmp2); 
		    		List<Contact> cnt = contactDao.lister(new String[] {"id_contact"}, new String[] {enc.getId_contact().toString()});
		    		//récupération du pays
			    	stage.setContactENC(cnt.get(0));
		    	}
	        	stages.add(stage);  
	        }
		    fermetureSilencieuse(resultSettmp);
		    fermetureSilencieuse(resultSettmp2);
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally { 
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return stages;
	}
	
	public List<Stages> lister(User user) throws DAOException{
		Users_stagesDaoImpl users_stageDao = (Users_stagesDaoImpl)daoFactory.getUsers_stagesDao();
		
		ResultSet resultSettmp = null, resultSettmp2= null;
		   
		String [] fieldstmp = {"id_user"}, valuestmp = {user.getId().toString()};
    	List<Users_stages> us = users_stageDao.lister(fieldstmp,valuestmp);
    	Hashtable<String,String> hash = new Hashtable<String,String>();
    	hash.put("tables", "users_stages us NATURAL JOIN stages s");
    	hash.put("conditions", "us.id_stage = s.id_stage AND us.id_user = "+user.getId().toString());

    	List<Stages> stages = new ArrayList<Stages>();
	    try {
	    	super.resultSet = super.customised(hash);
		    while ( super.resultSet.next() ) { 
		    	Stages stage = new Stages();
		    	stage = map( resultSet );
		    	//récupération de l'entreprise dans laquelle s'est déroulé le stage
		    	hash = new Hashtable<String,String>();
		    	hash.put("tables", "stage_entreprise se NATURAL JOIN entreprise e");
		    	hash.put("fields", "e.id_entreprise");
		    	hash.put("conditions", "se.id_entreprise = e.id_entreprise AND se.id_stage = "+stage.getId_stage().toString());
		    	resultSettmp2 = super.customised(hash);
		    	if(resultSettmp2.next()) { 
		    		List<Entreprise> ent = entrepriseDao.lister(new String[] {"id_entreprise"}, new String[] {resultSettmp2.getString("id_entreprise")});//EntrepriseDaoImpl.map(resultSettmp2);
		    		if(ent.size()>0)
		    			stage.setEntreprise(ent.get(0));
		    	}
		    	//récupération du lieu
		    	//encadrant
	        	stages.add(stage);
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally { 
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return stages;		
	}

	public boolean supprimer(Stages stage) throws DAOException{
		//Suppression avec 2 cl�s primaires
		String[] fields = {"id_typestage","id_entreprise","stg_sujet","stg_objet","stg_domaine","stg_resume","stg_datesoutenance","id_typeconvention","id_etatvalidation","stg_date_debut","stg_date_fin","stg_duree","stg_gratification","stg_competences","stg_horaires","stg_jours","stg_h_week","stg_w_ferie","stg_nuit","stg_avantages"};
		String[] values = {stage.getId_typestage().toString(),stage.getId_entreprise().toString(),stage.get_sujet(),stage.get_objet(),stage.get_domaine(),stage.get_resume(),stage.get_datesoutenance().toString(),stage.getId_typeconvention().toString(),stage.getId_etatvalidation().toString(),stage.get_date_debut().toString(),stage.get_date_fin().toString(),String.valueOf(stage.get_duree()),stage.get_gratification().toString(),stage.get_competences(),stage.get_horaires(),stage.get_jours().toString(),String.valueOf(stage.get_h_week()),String.valueOf(stage.get_w_ferie()),String.valueOf(stage.get_nuit()),stage.get_avantages()};
		int retour = super.supprimerMere(stage.getId_stage(),null,fields,values); 
		return retour==0 ? false:true;
	}
	

	public boolean mettreAJour(Stages old_stage, Stages stage)throws DAOException{
		String datesoutenance = stage.get_datesoutenance()!=null ? stage.get_datesoutenance().toString() : "NULL";
		String[] fields = {"id_typestage","stg_sujet","stg_objet","stg_domaine","stg_resume","stg_datesoutenance","id_typeconvention","id_etatvalidation","stg_date_debut","stg_date_fin","stg_duree","stg_gratification","stg_competences","stg_horaires","stg_jours","stg_h_week","stg_w_ferie","stg_nuit","stg_avantages"};
		String[] values = {stage.getId_typestage().toString(),stage.get_sujet(),stage.get_objet(),stage.get_domaine(),stage.get_resume(),
				datesoutenance,
				stage.getId_typeconvention().toString(),stage.getId_etatvalidation().toString(),stage.get_date_debut().toString(),stage.get_date_fin().toString(),String.valueOf(stage.get_duree()),stage.get_gratification().toString(),stage.get_competences(),stage.get_horaires(),stage.get_jours().toString(),String.valueOf(stage.get_h_week()),String.valueOf(stage.get_w_ferie()),String.valueOf(stage.get_nuit()),stage.get_avantages()};
		String[] whereFields = new String[] {"id_stage"};
		int retour = super.mettreAJourMere(old_stage.getId_stage(), null, fields, values, whereFields);
		return retour == 0 ? false : true; 
	}
	
	
	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Stages map( ResultSet resultSet ) throws SQLException {
    	
    	Stages stage = new Stages();
    	stage.setId_stage( resultSet.getLong( "id_stage" ) );   
    	stage.setId_typestage( resultSet.getLong( "id_typestage" ) ); 
    	stage.set_sujet( resultSet.getString( "stg_sujet" ) );
    	stage.set_objet( resultSet.getString( "stg_objet" ) );
    	stage.set_domaine( resultSet.getString( "stg_domaine" ) );
    	stage.set_resume( resultSet.getString( "stg_resume" ) );
    	//String buffer = resultSet.getString( "stg_datesoutenance" );
    	//Calendar cal = Calendar.getInstance();
    	//java.sql.Date date = new Date(cal.getTime().getTime());
    	//stage.set_datesoutenance(null);
    	stage.set_jury(resultSet.getString("stg_jury"));
    	stage.setId_typeconvention( resultSet.getLong( "id_typeconvention" ) ); 
    	stage.setId_etatvalidation( resultSet.getLong( "id_etatvalidation" ) );
    	stage.set_date_debut( resultSet.getDate( "stg_date_debut" ) );
    	stage.set_date_fin( resultSet.getDate( "stg_date_fin" ) );
    	stage.set_duree( resultSet.getInt( "stg_duree" ) );
    	stage.set_gratification( resultSet.getString( "stg_gratification" ) );
    	stage.set_competences( resultSet.getString( "stg_competences" ) );
    	stage.set_horaires( resultSet.getString( "stg_horaires" ) );
    	stage.set_jours( resultSet.getString( "stg_jours" ) );
    	stage.set_h_week( resultSet.getInt( "stg_h_week" ) );
    	stage.set_w_ferie( resultSet.getInt( "stg_w_ferie" ) ); 
    	stage.set_nuit( resultSet.getInt( "stg_nuit" ) );
    	stage.set_avantages( resultSet.getString( "stg_avantages" ) );
    	
    	return stage;
        
        
    }
	

}
