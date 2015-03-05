package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 




import com.gesta.beans.Eleves;
import com.gesta.beans.Tailleentreprise;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class TailleentrepriseDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM tailleentreprise ";
	private static final String S_SQL_INSERT = "INSERT INTO tailleentreprise ('',id_tailleentreprise) VALUES (?)";

	
	TailleentrepriseDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	public List<Tailleentreprise> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Tailleentreprise> Tailleentreprises = new ArrayList<Tailleentreprise>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Tailleentreprises.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Tailleentreprises;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Tailleentreprise map( ResultSet resultSet ) throws SQLException {
    	
    	Tailleentreprise Tailleentreprise = new Tailleentreprise();
    	Tailleentreprise.setId_tailleentreprise(resultSet.getLong( "id_tailleentreprise" ));;   
    	Tailleentreprise.setTae_nom(resultSet.getString("tae_nom"));
    	
    	
    	return Tailleentreprise;
        
        
    }
	

}
