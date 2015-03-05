package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 




import com.gesta.beans.Eleves;
import com.gesta.beans.Typeconvention;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class TypeconventionDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM typeconvention ";
	private static final String S_SQL_INSERT = "INSERT INTO typeconvention ('',id_typeconvention) VALUES (?)";

	TypeconventionDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	public List<Typeconvention> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Typeconvention> Typeconventions = new ArrayList<Typeconvention>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Typeconventions.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Typeconventions;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Typeconvention map( ResultSet resultSet ) throws SQLException {
    	
    	Typeconvention Typeconvention = new Typeconvention();
    	Typeconvention.setId_typeconvention(resultSet.getLong( "id_typeconvention" ));;   
    	Typeconvention.setTcv_nom( resultSet.getString( "tcv_nom" ) ); 
    	
    	
    	
    	return Typeconvention;
        
        
    }
	

}
