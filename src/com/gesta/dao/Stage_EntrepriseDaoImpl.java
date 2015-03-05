package com.gesta.dao;

import static com.gesta.dao.DAOUtilitaire.fermeturesSilencieuses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import com.gesta.beans.Stage_Entreprise; 

public class Stage_EntrepriseDaoImpl extends DAOMereImpl {
	private static final String S_SQL_SELECT = "SELECT * FROM stage_entreprise ";
	private static final String S_SQL_INSERT = "INSERT INTO stage_entreprise (id_stage,id_entreprise) VALUES (?,?)";
	private static final String S_SQL_DELETE = "DELETE FROM stage_entreprise ";
	private static final String S_SQL_UPDATE = "UPDATE stage_entreprise SET ";

	protected Stage_EntrepriseDaoImpl(DAOFactory daoFactory) {
		super(daoFactory);
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = S_SQL_UPDATE;
		this.SQL_DELETE = S_SQL_DELETE;
	} 
	
	public Long creer(Stage_Entreprise s_ent) throws DAOException{
		String[] values = {s_ent.getId_stage().toString(),s_ent.getId_entreprise().toString()};
		return super.creerMere(s_ent, values, true);  
	}
  
	public List<Stage_Entreprise> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Stage_Entreprise> stage_entreprises = new ArrayList<Stage_Entreprise>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
		    	stage_entreprises.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return stage_entreprises;
	}

	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Stage_Entreprise map( ResultSet resultSet ) throws SQLException { 
    	Stage_Entreprise stage_entreprise = new Stage_Entreprise();
    	stage_entreprise.setId_stage(resultSet.getLong( "id_stage" ));   
    	stage_entreprise.setId_entreprise( resultSet.getLong( "id_entreprise" ) );   
    	
    	return stage_entreprise; 
    }
	

}
