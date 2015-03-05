package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 



import com.gesta.beans.Eleves;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class EleveDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM statuts_actions ";
	private static final String S_SQL_INSERT = "INSERT INTO statuts_actions (id_statut,id_aledroit) VALUES (?,?)";

	
	EleveDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	public List<Eleves> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Eleves> eleves = new ArrayList<Eleves>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	eleves.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return eleves;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Eleves map( ResultSet resultSet ) throws SQLException {
    	
    	Eleves eleve = new Eleves();
    	eleve.setId(resultSet.getLong( "id_eleve" ));;   
    	eleve.setNiveau( resultSet.getString( "elv_niveau" ) ); 
    	
    	
    	return eleve;
        
        
    }
	

}
