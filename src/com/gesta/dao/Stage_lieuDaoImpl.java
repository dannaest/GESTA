package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 





import com.gesta.beans.Eleves;
import com.gesta.beans.Stage_Entreprise;
import com.gesta.beans.Stage_lieu;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class Stage_lieuDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM stage_lieu ";
	private static final String S_SQL_INSERT = "INSERT INTO stage_lieu (id_stage,id_lieu,stl_debut,stl_fin) VALUES (?,?,?,?)";

	
	Stage_lieuDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	public Long creer(Stage_lieu s_lieu) throws DAOException{
		String[] values = {s_lieu.getId_stage().toString(),s_lieu.getId_lieu().toString(),s_lieu.getStl_debut().toString(),s_lieu.getStl_fin().toString()};
		return super.creerMere(s_lieu, values, true);  
	}
	
	public List<Stage_lieu> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Stage_lieu> Stage_lieus = new ArrayList<Stage_lieu>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Stage_lieus.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Stage_lieus;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Stage_lieu map( ResultSet resultSet ) throws SQLException {
    	
    	Stage_lieu Stage_lieu = new Stage_lieu();
    	Stage_lieu.setId_stage(resultSet.getLong( "id_stage" ));   
    	Stage_lieu.setId_lieu( resultSet.getLong( "id_lieu" ) ); 
    	Stage_lieu.setStl_debut(resultSet.getDate("stl_debut"));
    	Stage_lieu.setStl_fin(resultSet.getDate("stl_fin"));

    	
    	return Stage_lieu;
        
        
    }
	

}
