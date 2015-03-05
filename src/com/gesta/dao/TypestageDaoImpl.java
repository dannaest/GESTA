
package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 




import com.gesta.beans.Eleves;
import com.gesta.beans.Typestage;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class TypestageDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM typestage ";
	private static final String S_SQL_INSERT = "INSERT INTO typestage ('',tst_nom) VALUES (?)";

	
	TypestageDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	public List<Typestage> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Typestage> Typestages = new ArrayList<Typestage>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Typestages.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Typestages;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Typestage map( ResultSet resultSet ) throws SQLException {
    	
    	Typestage Typestage = new Typestage();
    	Typestage.setId_typestage(resultSet.getLong( "id_typestage" ));;   
    	Typestage.setTst_nom( resultSet.getString( "tst_nom" ) ); 
    	
    	
    	
    	return Typestage;
        
        
    }
	

}
