package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 

import com.gesta.beans.Jury;
import static com.gesta.dao.DAOUtilitaire.*;

public class JuryDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM statuts_actions ";
	private static final String S_SQL_INSERT = "INSERT INTO statuts_actions (id_statut,id_aledroit) VALUES (?,?)";

	
	JuryDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	public List<Jury> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Jury> Jurys = new ArrayList<Jury>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Jurys.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Jurys;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Jury map( ResultSet resultSet ) throws SQLException {
    	
    	Jury Jury = new Jury();
    	Jury.setId_contact(resultSet.getLong( "id_contact" ));;   
        Jury.setId_stage(resultSet.getLong("id_stage"));
    	
    	
    	return Jury;
        
        
    }
	

}
