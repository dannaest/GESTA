package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List; 

import com.gesta.beans.Contact;
import com.gesta.beans.Entreprise;
import com.gesta.beans.Localisation;
import com.gesta.dao.DAOException;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.DAOMereImpl;

import static com.gesta.dao.DAOUtilitaire.*;

public class EntrepriseDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM entreprise ";
	private static final String S_SQL_INSERT = "INSERT INTO entreprise (ent_nom,id_typeentreprise,ent_secteur,ent_siret,id_tailleentreprise,ent_groupe,id_ent_rh,id_ent_representant) VALUES (?,?,?,?,?,?,?,?)";
	private static final String S_SQL_DELETE = "DELETE FROM entreprise ";
	private static final String S_SQL_UPDATE = "UPDATE entreprise SET ";
	
	ContactDaoImpl contactDao = null;
	LocalisationDaoImpl localisationDao = null;

	EntrepriseDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = S_SQL_UPDATE;
		this.SQL_DELETE = S_SQL_DELETE;
	}

	
	public Long creer(Entreprise ent) throws DAOException{  
		contactDao = (ContactDaoImpl) daoFactory.getContactDao();
		localisationDao = (LocalisationDaoImpl) daoFactory.getLocalisationDao();
		
		Long id_ent_rh = contactDao.creer(ent.getCnt_Rh());
		Long id_ent_rep = contactDao.creer(ent.getCnt_Representant());
		
		localisationDao.creer(ent.getLieu());
		String[] values = {ent.getEnt_nom(),ent.getId_typeentreprise().toString(), ent.getEnt_secteur(), ent.getEnt_siret(),
				ent.getId_tailleentreprise().toString(),ent.getEnt_groupe(),id_ent_rh.toString(), id_ent_rep.toString()};
		Long id_entreprise = super.creerMere(ent, values, true);  
		ent.setId(id_entreprise);
		
		return id_entreprise;
	}
	
	public List<Entreprise> lister(String[] fields, String[] values) throws DAOException {  
		ResultSet resultSettmp2 = null;
		Hashtable<String,String> hash = null;
		List<Entreprise> entreprises = new ArrayList<Entreprise>();
		
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
		    	Entreprise ent = map( resultSet );
		    	//représentant
		    	hash = new Hashtable<String,String>();
		    	hash.put("tables", "contact c NATURAL JOIN entreprise e");
		    	hash.put("conditions", "e.id_ent_representant = c.id_contact AND e.id_ent_representant = "+resultSet.getString("id_ent_representant"));
		    	resultSettmp2 = super.customised(hash);
		    	if(resultSettmp2.next()) {
		    		Contact cnt = ContactDaoImpl.map(resultSettmp2);
		    		ent.setCnt_Representant(cnt);
		    	}
		    	//rh
		    	hash = new Hashtable<String,String>();
		    	hash.put("tables", "contact c NATURAL JOIN entreprise e");
		    	hash.put("conditions", "e.id_ent_rh = c.id_contact AND e.id_ent_rh = "+resultSet.getString("id_ent_rh"));
		    	resultSettmp2 = super.customised(hash);
		    	if(resultSettmp2.next()) {
		    		Contact cnt = ContactDaoImpl.map(resultSettmp2);
		    		ent.setCnt_Rh(cnt);
		    	}
		    	//lieu
		    	hash = new Hashtable<String,String>();
		    	hash.put("tables", "localisation l NATURAL JOIN entreprise_lieu el");
		    	hash.put("conditions", "l.id_lieu = el.id_lieu AND el.id_entreprise = "+ent.getId().toString());
		    	resultSettmp2 = super.customised(hash);
		    	if(resultSettmp2.next()) {
		    		Localisation lieu = LocalisationDaoImpl.map(resultSettmp2);
		    		ent.setLieu(lieu);
		    	}
	        	entreprises.add(ent);
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return entreprises;
	}

	
	public boolean supprimer(Entreprise ent) throws DAOException{
		//Suppression avec 2 cl�s primaires
		String[] fields = {"id_typeentreprise","id_tailleentreprise","id_ent_representant","id_ent_rh","id_lieu","ent_nom","ent_siret","ent_secteur","ent_groupe"};
		String[] values = {ent.getId_typeentreprise().toString(),ent.getId_tailleentreprise().toString(),ent.getId_ent_representant().toString(),ent.getId_ent_rh().toString(),ent.getId_lieu().toString(),ent.getEnt_nom(),ent.getEnt_siret(),ent.getEnt_secteur(),ent.getEnt_groupe()}; 
		int retour = super.supprimerMere(ent.getId_entreprise(),null,fields,values); 
		return retour==0 ? false:true;
	}

	
	public boolean mettreAJour(Entreprise old_ent, Entreprise ent)throws DAOException{
		String id_representant = old_ent.getCnt_Representant()!=null ? old_ent.getCnt_Representant().getId().toString() : "";
		String id_rh = old_ent.getCnt_Rh()!=null ? old_ent.getCnt_Rh().getId().toString() : ""; 
		String[] fields = {"id_typeentreprise","id_tailleentreprise","id_ent_representant","id_ent_rh","ent_nom","ent_siret","ent_secteur","ent_groupe"};
		String[] values = {ent.getId_typeentreprise().toString(),ent.getId_tailleentreprise().toString(),id_representant,
				id_rh,ent.getEnt_nom(),ent.getEnt_siret(),ent.getEnt_secteur(),
				ent.getEnt_groupe()}; 
		String[] whereFields = new String[] {"id_entreprise"};
		int retour = super.mettreAJourMere(old_ent.getId_entreprise(), null,fields, values, whereFields);
		return retour == 0 ? false : true; 
	}
	
	
	
	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    public static Entreprise map( ResultSet resultSet ) throws SQLException { 
    	return mapPrefix(resultSet,""); 
    }
    
    public static Entreprise mapPrefix(ResultSet resultSet , String prefix)throws SQLException{
    	Entreprise entreprise = new Entreprise();     
    	entreprise.setId_entreprise(resultSet.getLong(prefix+"id_entreprise"));
    	entreprise.setEnt_nom( resultSet.getString( prefix+"ent_nom" ) ); 
    	entreprise.setId_typeentreprise(resultSet.getLong(prefix+"id_typeentreprise")); 
    	entreprise.setEnt_secteur(resultSet.getString(prefix+"ent_secteur"));
    	entreprise.setEnt_siret(resultSet.getString(prefix+"ent_siret"));
    	entreprise.setId_tailleentreprise(resultSet.getLong(prefix+"id_tailleentreprise"));
    	entreprise.setEnt_groupe(resultSet.getString( prefix+"ent_groupe" )); 
    	//entreprise.setId_ent_representant(resultSet.getLong(prefix+"id_ent_representant"));
    	//entreprise.setId_ent_rh(resultSet.getLong(prefix+"id_ent_rh"));
    	//entreprise.setId_lieu(resultSet.getLong(prefix+"id_lieu"));
    	return entreprise; 
    }
	

}
