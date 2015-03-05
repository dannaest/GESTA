package com.gesta.dao;

import static com.gesta.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gesta.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gesta.beans.Options;

public class OptionDaoImpl implements OptionDao {
	private DAOFactory daoFactory;
	
	private static final String SQL_SELECT = "SELECT * FROM options";
	private static final String SQL_SELECT_PAR_NOM = "SELECT * FROM options WHERE opt_nom = ?";
	private static final String SQL_INSERT = "INSERT INTO options (opt_nom) VALUES (?)"; 
	
	
	OptionDaoImpl(DAOFactory daoFactory){
		this.daoFactory = daoFactory; 
	}
	
	/*
	 * */
	@Override
	public long creer(Options option) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;  
	    
	    long id = 0; 

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true,option.getNom());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Échec lors de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	        	id = valeursAutoGenerees.getLong( 1 );
	            option.setId( id );
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
	
	/**/
	@Override
	public Options trouver(String nom) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Options option = null;
	    Object[] objet = {nom};

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_NOM, false, objet,false );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        /*Récupération de l'utilisateur*/
	        if ( resultSet.next() ) {
	            option = map( resultSet );
	        } 
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return option;
	}
	
	public List<Options> lister(  String[] fields, String[] values ) throws DAOException{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    String sql;
	    if(fields==null || values==null || fields.length == 0 || values.length == 0 || fields.length != values.length) {
	    	sql = SQL_SELECT;
	    }else {
	    	sql = "SELECT * FROM users u WHERE 1 ";
	    	for(String champs:fields)
	    		sql += " AND "+champs+" = ? ";	    	
	    }

	    List<Options> options = new ArrayList<Options>();

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        Object[] objets = {};
	        if(values.length>0) {
		        objets = new Object[values.length];
		        int i = 0;
		        for(String str : values){
		        	objets[i++] = str;
		        }
	        }
	        try {
	        	preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets, false );
		        preparedStatement.executeQuery();
		        resultSet = preparedStatement.getResultSet();

		        while ( resultSet.next() ) {
    	            options.add(map( resultSet )); 
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
	    return options;
	}
	/*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private static Options map( ResultSet resultSet ) throws SQLException {
    	return new Options(resultSet.getLong("id_option"),resultSet.getString("opt_nom")); 
    }

}
