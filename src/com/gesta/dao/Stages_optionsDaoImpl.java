package com.gesta.dao;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 





import com.gesta.beans.Eleves;
import com.gesta.beans.Stage_lieu;
import com.gesta.beans.Stages_options;
import com.gesta.beans.Stages;
import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class Stages_optionsDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM statuts_actions ";
	private static final String S_SQL_INSERT = "INSERT INTO statuts_actions (id_statut,id_aledroit) VALUES (?,?)";

	
	Stages_optionsDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la m�re
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}

	public Long creer(Stages_options s_opt) throws DAOException{
		String[] values = {s_opt.getId_stage().toString(),s_opt.getId_option().toString()};
		return super.creerMere(s_opt, values, true);  
	}
	
	public List<Stages_options> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Stages_options> Stages_optionss = new ArrayList<Stages_options>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	Stages_optionss.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return Stages_optionss;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Stages_options map( ResultSet resultSet ) throws SQLException {
    	
    	Stages_options Stages_options = new Stages_options();
    	Stages_options.setId_option(resultSet.getLong( "id_option" ));;   
    	Stages_options.setId_stage( resultSet.getLong( "id_stage" ) ); 
    	
    	
    	
    	return Stages_options;
        
        
    }
	

}
