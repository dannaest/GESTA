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
import com.gesta.beans.Statut;
import com.gesta.beans.User;

public class StatutDaoImpl implements StatutDao {
	private DAOFactory daoFactory;
	private static final String SQL_SELECT = "SELECT * FROM statuts ORDER BY sta_nivDroit";
	private static final String SQL_SELECT_PAR_ID_USER = "SELECT * FROM statuts s JOIN users_statuts us WHERE s.sta_nivDroit = us.sta_nivDroit AND us.id_user = ?";
	private static final String SQL_INSERT = "INSERT INTO statuts (sta_nom,sta_nivDroit) VALUES (?,?)";
	
	private static final String SQL_INSERT_USER_STATUT = "INSERT INTO users_statuts (id_user,sta_nivDroit) VALUES (?,?)";
	
	private static final String SQL_SELECT_STATUT_ACTION = "SELECT * FROM statuts_actions ";
	private static final String SQL_INSERT_STATUT_ACTION = "INSERT INTO statuts_actions (id_statut,id_aledroit) VALUES (?,?)";

	StatutDaoImpl(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	@Override
	public long creer(Statut statut) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    long id = 0;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, statut.getNom(),statut.getNivDroit() );
	        int retour = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( retour == 0 ) {
	            throw new DAOException( "Échec lors de la création du statut, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	        	id = valeursAutoGenerees.getLong( 1 );
	            statut.setId( id );
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
	public List<Statut> trouver(Long id_user) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null; 
	    Object[] objet = {id_user};
	    List<Statut> statuts = new ArrayList<Statut>();

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID_USER, false, objet,false );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        /*Récupération de l'utilisateur*/ 
	        while ( resultSet.next() ) {
	        	statuts.add(map( resultSet ));
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return statuts;
	}

	@Override
	public List<Statut> lister(String[] fields, String[] values) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    String sql;
	    if(fields.length == 0 || values.length == 0 || fields.length != values.length) {
	    	sql = SQL_SELECT;
	    }else {
	    	sql = SQL_SELECT+" WHERE 1<>0 ";
	    	for(String champs:fields)
	    		sql += " AND "+champs+" = ? ";	    	
	    } 

	    List<Statut> statuts = new ArrayList<Statut>();
	
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
		        	statuts.add(map( resultSet ));
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
	    return statuts;
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
    private static Statut map( ResultSet resultSet ) throws SQLException {
    	Statut statut = new Statut(new Long(0),null,0);
    	statut.setId( resultSet.getLong( "id_statut" ) );   
    	statut.setNom( resultSet.getString( "sta_nom" ) );
    	statut.setNivDroit( Integer.parseInt( resultSet.getString( "sta_nivDroit" ) ) );
        return statut;
    }
    
    // GESTION DES USERS_STATUTS
	@Override
	public int creerUserStatut(Long id, Long id_statut) throws DAOException{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    int retour = 0;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_USER_STATUT, false, String.valueOf(id),String.valueOf(id_statut) );
	        retour = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( retour == 0 ) {
	            throw new DAOException( "Échec lors de la création du statut pour l'utilisateur, aucune ligne ajoutée dans la table." );
	        } 
	    } catch ( SQLException e ) {
	    	throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	    return retour;
	}
	
	// GESTION DES STATUTS_ACTIONS
	@Override
	public int creerStatutAction(Long id_statut, Long id_aledroit) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    int retour = 0;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_STATUT_ACTION, false, String.valueOf(id_statut),String.valueOf(id_aledroit) );
	        retour = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( retour == 0 ) {
	            throw new DAOException( "Échec lors de la création du droit de l'utilisateur à l'action spécifiée, aucune ligne ajoutée dans la table." );
	        } 
	    } catch ( SQLException e ) { 
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	    return retour;
	}
	
	public List<Statut> listerStatutsActions(String[] fields, String[] values) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    String sql;
	    if(fields.length == 0 || values.length == 0 || fields.length != values.length) {
	    	sql = SQL_SELECT_STATUT_ACTION;
	    }else {
	    	sql = SQL_SELECT_STATUT_ACTION+" WHERE 1<>0 ";
	    	for(String champs:fields)
	    		sql += " AND "+champs+" = ? ";	    	
	    } 

	    List<Statut> statuts = new ArrayList<Statut>();
	
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
		        	statuts.add(map( resultSet ));
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
	    return statuts;
	}
	
	

}
