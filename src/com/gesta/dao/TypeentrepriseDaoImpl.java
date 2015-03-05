package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 




import com.gesta.beans.Eleves;
import com.gesta.beans.Typeentreprise;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class TypeentrepriseDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM typeentreprise ";
	private static final String S_SQL_INSERT = "INSERT INTO typeentreprise ('',id_typeentreprise) VALUES (?)";

	
	TypeentrepriseDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	public List<Typeentreprise> lister(String[] fields, String[] values) throws DAOException {  
		ResultSet resultSet = null;
		List<Typeentreprise> Typeentreprises = new ArrayList<Typeentreprise>();
	    try {
	    	resultSet = super.listerMere(fields, values);
		    while ( resultSet.next() ) {
	        	Typeentreprises.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Typeentreprises;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Typeentreprise map( ResultSet resultSet ) throws SQLException {
    	
    	Typeentreprise Typeentreprise = new Typeentreprise();
    	Typeentreprise.setId_typeentreprise(resultSet.getLong( "id_typeentreprise" ));;   
    	Typeentreprise.setTen_nom( resultSet.getString( "ten_nom" ) ); 
    	
    	
    	return Typeentreprise;
        
        
    }
	

}
