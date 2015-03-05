package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 





import com.gesta.beans.Eleves;
import com.gesta.beans.Stage_lieu;
import com.gesta.beans.Users_stages;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class Users_stagesDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM users_stages ";
	private static final String S_SQL_INSERT = "INSERT INTO users_stages (id_user,id_stage) VALUES (?,?)";

	
	Users_stagesDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}

	public long creer(Users_stages u_sta) throws DAOException{
		String[] values = {u_sta.getId_user().toString(),u_sta.getId_stage().toString()};
		return super.creerMere(u_sta, values, true);  
	}
	
	public List<Users_stages> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Users_stages> Users_stagess = new ArrayList<Users_stages>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Users_stagess.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Users_stagess;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Users_stages map( ResultSet resultSet ) throws SQLException {
    	
    	Users_stages Users_stages = new Users_stages();
    	Users_stages.setId_stage(resultSet.getLong( "id_stage" ));;   
    	Users_stages.setId_user( resultSet.getLong( "id_user" ) ); 
    	
    	
    	return Users_stages;
        
        
    }
	

}
