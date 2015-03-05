package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 






import com.gesta.beans.Eleves;
import com.gesta.beans.Encadrant;
import com.gesta.beans.Entreprise;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class EncadrantDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM encadrant ";
	private static final String S_SQL_INSERT = "INSERT INTO encadrant (id_stage,id_contact) VALUES(?,?)";
	private static final String S_SQL_DELETE = "DELETE FROM encadrant ";
	private static final String S_SQL_UPDATE = "UPDATE encadrant SET ";
	
	EncadrantDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = S_SQL_UPDATE;
		this.SQL_DELETE = S_SQL_DELETE;
	}

	
	public long creer(Encadrant enc) throws DAOException{ 
		String[] values = {enc.getId_stage().toString(),enc.getId_contact().toString()};
		return super.creerMere(enc, values, true);  
	}
	
	public List<Encadrant> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Encadrant> encadrants = new ArrayList<Encadrant>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	encadrants.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return encadrants;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    public static Encadrant map( ResultSet resultSet ) throws SQLException {
    	
    	Encadrant encadrant = new Encadrant();
    	encadrant.setId_contact(resultSet.getLong( "id_contact" ));;   
    	encadrant.setId_encadrant( resultSet.getLong( "id_encadrant" ) ); 
    	encadrant.setId_stage(resultSet.getLong("id_stage"));
    	
    	
    	return encadrant;
        
        
    }
	

}
