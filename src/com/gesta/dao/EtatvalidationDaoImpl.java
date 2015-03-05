package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 




import com.gesta.beans.Eleves;
import com.gesta.beans.Etatvalidation;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class EtatvalidationDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM etatvalidation ";
	private static final String S_SQL_INSERT = "INSERT INTO etatvalidation (id_etatvalidation,etv_nom) VALUES (?,?)";

	
	EtatvalidationDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	public List<Etatvalidation> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Etatvalidation> Etatvalidations = new ArrayList<Etatvalidation>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Etatvalidations.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Etatvalidations;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Etatvalidation map( ResultSet resultSet ) throws SQLException {
    	
    	Etatvalidation Etatvalidation = new Etatvalidation();
    	Etatvalidation.setId_etatvalidation(resultSet.getLong( "id_etatvalidation" ));;   
    	Etatvalidation.setEtv_nom( resultSet.getString( "etv_nom" ) ); 
    	
    	
    	
    	return Etatvalidation;
        
        
    }
	

}
