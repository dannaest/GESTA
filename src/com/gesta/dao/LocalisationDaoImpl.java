package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 







import com.gesta.beans.Eleves;
import com.gesta.beans.Entreprise;
import com.gesta.beans.Localisation;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class LocalisationDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM localisation ";
	private static final String S_SQL_INSERT = "INSERT INTO localisation (lieu_numero,lieu_voie,lieu_ville,lieu_cp,id_pays) VALUES (?,?,?,?,?)";
	private static final String S_SQL_UPDATE = "UPDATE localisation SET ";
	private static final String S_SQL_DELETE = "DELETE localisation FROM ";

	
	LocalisationDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = S_SQL_UPDATE;
		this.SQL_DELETE = S_SQL_DELETE;
	}
	
	public Long creer(Localisation lieu) throws DAOException{
		String[] values = {lieu.getLieu_numero(),lieu.getLieu_voie(),lieu.getLieu_ville(),lieu.getLieu_cp(),String.valueOf(lieu.getId_pays())}; 
		Long id = super.creerMere(Localisation.class, values, true);  
		return id;
	}
	
	public List<Localisation> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Localisation> Localisations = new ArrayList<Localisation>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Localisations.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Localisations;
	}
	
	public boolean mettreAJour(Localisation old_lieu, Localisation lieu)throws DAOException{
		String[] fields = {"lieu_numero","lieu_voie","lieu_ville","lieu_cp","id_pays"};
		String[] values = {lieu.getLieu_numero(),lieu.getLieu_voie(),lieu.getLieu_ville(),lieu.getLieu_cp(),lieu.getId_pays().toString()}; 
		String[] whereFields = new String[] {"id_lieu"};
		int retour = super.mettreAJourMere(old_lieu.getId_lieu(), null,fields, values, whereFields);
		return retour == 0 ? false : true; 
	}
	
	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    public static Localisation map( ResultSet resultSet ) throws SQLException {
    	 return mapPrefix(resultSet,""); 
    }
    public static Localisation mapPrefix( ResultSet resultSet, String prefix ) throws SQLException {
    	
    	Localisation Localisation = new Localisation();
    	Localisation.setId_pays(resultSet.getLong( prefix+"id_pays" ));;   
    	Localisation.setId_lieu( resultSet.getLong( prefix+"id_lieu" ) ); 
    	Localisation.setLieu_cp(resultSet.getString(prefix+"lieu_cp"));
    	Localisation.setLieu_numero(resultSet.getString(prefix+"lieu_numero"));
    	Localisation.setLieu_ville(resultSet.getString(prefix+"lieu_ville"));
    	Localisation.setLieu_voie(resultSet.getString(prefix+"lieu_voie")); 
    	return Localisation; 
    }
	

}
