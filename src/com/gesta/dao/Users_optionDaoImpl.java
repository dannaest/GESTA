package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 
import com.gesta.beans.Users_options;
import static com.gesta.dao.DAOUtilitaire.*;

public class Users_optionDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM statuts_actions ";
	private static final String S_SQL_INSERT = "INSERT INTO statuts_actions (id_statut,id_aledroit) VALUES (?,?)";

	
	Users_optionDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	public List<Users_options> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Users_options> Users_options = new ArrayList<Users_options>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Users_options.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Users_options;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Users_options map( ResultSet resultSet ) throws SQLException {
    	
    	Users_options Users_option = new Users_options();
    	Users_option.setId_option(resultSet.getLong( "id_option" ));;   
    	Users_option.setId_user( resultSet.getLong( "id_user" ) ); 
    	
    	
    	return Users_option;
        
        
    }
	

}
