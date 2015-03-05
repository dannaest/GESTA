package com.gesta.dao;

import static com.gesta.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gesta.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gesta.beans.ALeDroit; 

public class ALeDroitDaoImpl implements ALeDroitDao {
	private DAOFactory daoFactory;
	private static final String SQL_SELECT = "SELECT * FROM aledroit ORDER BY ald_servlet";
	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM aledroit WHERE id_aledroit = ?";
	private static final String SQL_INSERT = "INSERT INTO aledroit (ald_servlet,ald_do,ald_action) VALUES (?,?,?)";

	ALeDroitDaoImpl(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	@Override
	public long creer(ALeDroit aledroit) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    long id = 0;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, aledroit.getServlet(),aledroit.getDoMethod(),aledroit.getAction() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Échec lors de la création de l'action, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	        	id = valeursAutoGenerees.getLong( 1 );
	            aledroit.setId( id );
	        } else {
	            throw new DAOException( "L'auto-génération de l'ID a échoué." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	    return id;
	}

	@Override
	public ALeDroit trouver(Long id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ALeDroit> lister(String[] fields, String[] values){
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    String sql;
	    if(fields.length == 0 || values.length == 0 || fields.length != values.length) {
	    	sql = SQL_SELECT;
	    }else {
	    	sql = "SELECT * FROM aledroit WHERE 1<>0 ";
	    	for(String champs:fields)
	    		sql += " AND "+champs+" = ? ";	
	    	sql += " ORDER BY ald_servlet";
	    } 

	    List<ALeDroit> actions = new ArrayList<ALeDroit>();
	
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        Object[] objets = new Object[values.length];
	        int i = 0;
	        for(String str : values){
	        	objets[i++] = str;
	        }
	        try {
	        	preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets, false ); 
		        preparedStatement.executeQuery();
		        resultSet = preparedStatement.getResultSet();
	
		        while ( resultSet.next() ) {
		            actions.add(map( resultSet ));
		        }
	        }
	        catch(SQLException e) {
	        	throw new SQLException(e.getMessage()+" : Impossible de compter les lignes de résultats.");
	        }
	        
	        
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    } 
	    return actions;
	}

	@Override
	public boolean mettreAJour(Long id) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(Long id) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static ALeDroit map( ResultSet resultSet ) throws SQLException {
    	ALeDroit aledroit = new ALeDroit();
    	aledroit.setId( resultSet.getLong( "id_aledroit" ) );  
    	aledroit.setServlet( resultSet.getString( "ald_servlet" ) );
    	aledroit.setDoMethod( resultSet.getString( "ald_do" ) ); 
    	aledroit.setAction( resultSet.getString( "ald_action" ) );
        return aledroit;
    }
}
