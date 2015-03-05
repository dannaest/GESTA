package com.gesta.dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 




import com.gesta.beans.Statut_Action;

import static com.gesta.dao.DAOUtilitaire.*;

public class Statut_ActionDaoImpl extends DAOMereImpl { 
	private static final String S_SQL_SELECT = "SELECT * FROM statuts_actions ";
	private static final String S_SQL_INSERT = "INSERT INTO statuts_actions (id_statut,id_aledroit) VALUES (?,?)";
	private static final String S_SQL_DELETE = "DELETE FROM statuts_actions ";
	private static final String S_SQL_UPDATE = "UPDATE statuts_actions SET ";

	
	Statut_ActionDaoImpl(DAOFactory daoFactory) {
		//on initialise les champs de la mère
		super(daoFactory); 
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = S_SQL_UPDATE;
		this.SQL_DELETE = S_SQL_DELETE;  
	}
	
	public Long creer(Statut_Action s_action) throws DAOException{
		String[] values = {s_action.getIdStatut().toString(),s_action.getIdAledroit().toString()}; 
		return super.creerMere(s_action, values, false);  
	}
	
	public List<Statut_Action> lister(String[] fields, String[] values) throws DAOException {  
		//ResultSet resultSet = null;
		List<Statut_Action> statuts = new ArrayList<Statut_Action>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	statuts.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally { 
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return statuts;
	}
	public boolean mettreAJour(Statut_Action old_action, Statut_Action new_action)throws DAOException{
		String[] fields = {"id_statut","id_aledroit"};
		String[] values = {new_action.getIdStatut().toString(),new_action.getIdAledroit().toString()};
		String[] whereFields = fields;
		int retour = super.mettreAJourMere(old_action.getIdStatut(), old_action.getIdAledroit(), fields, values, whereFields);
		return retour == 0 ? false : true; 
	}
	public boolean supprimer(Statut_Action s_action) throws DAOException{
		//Suppression avec 2 clés primaires
		String[] fields = {"id_statut","id_aledroit"};
		String[] values = {s_action.getIdStatut().toString(),s_action.getIdAledroit().toString()};
		int retour = super.supprimerMere(s_action.getIdStatut(),s_action.getIdAledroit(),fields,values); 
		return retour==0 ? false:true;
	}

	/*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Statut_Action map( ResultSet resultSet ) throws SQLException {
    	Statut_Action statut_action = new Statut_Action();
    	statut_action.setIdStatut( resultSet.getLong( "id_statut" ) );   
    	statut_action.setIdAledroit( resultSet.getLong( "id_aledroit" ) ); 
        return statut_action;
    }
	

}
