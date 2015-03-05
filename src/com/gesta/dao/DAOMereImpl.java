package com.gesta.dao;

import static com.gesta.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.gesta.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.Hashtable;

public class DAOMereImpl implements DAO {
	protected DAOFactory daoFactory;
	
	protected   String SQL_INSERT = "";
	protected   String SQL_SELECT = "";
	protected   String SQL_UPDATE = "";
	protected   String SQL_UPDATE_2ID = "";
	protected   String SQL_DELETE = "";   
	protected Connection connexion = null;
	protected PreparedStatement preparedStatement = null;
	protected ResultSet resultSet = null;
	protected ResultSet valeursAutoGenerees = null;
	
	protected DAOMereImpl(DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
	/**
	 * La m�thode se charge de cr�er une entr�e dans la base de donn�es.
	 * @param objet - L'objet � cr�er
	 * @param values - tableau des valeurs permettant de cr�er la requ�te
	 * @param returnGeneratedKeys - boolean qui indique si l'on souhaite r�cup�rer la valeur de l'id
	 * @return L'id de la ligne qui vient d'�tre entrer si le param�tre returnGeneratedKeys vaut true, -1 sinon
	 */
	@Override
	public <T> long creerMere(T objet, String[] values, boolean returnGeneratedKeys) throws DAOException { 
		long id = -1;

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, returnGeneratedKeys,values,false );
	        int statut = preparedStatement.executeUpdate();
	        
	        /* Analyse du statut retourn� par la requ�te d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "�chec lors de la cr�ation de l'bjet "+objet.getClass().getName() +", aucune ligne ajout�e dans la table." );
	        }
	        
	        /* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
	        if(returnGeneratedKeys) {
		        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
		        if ( valeursAutoGenerees.next() ) {
		            /* Puis initialisation de la propri�t� id du bean Utilisateur avec sa valeur */
		        	id = valeursAutoGenerees.getLong( 1 ); 
		        } else {
		            throw new DAOException( "L'auto-g�n�ration de l'ID a �chou�." );
		        }
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	    return id;
	}

	/**
	 * Cette fonction r�cup�re la premi�re entr�e des r�sultats qui matchent avec la requ�te
	 * @param fields - tableau des champs permettant de cr�er la requ�te
	 * @param values - tableau des valeurs permettant de cr�er la requ�te  
	 * @return Un objet de type ResultSet contenant la ligne qui correspond � la requ�te ou null
	 */
	@Override
	public <T> ResultSet trouverMere(String[] fields, String[] values) throws DAOException {
		resultSet = listerMere(fields,values);
		try { 
			if(resultSet.next())
				return resultSet;
		}catch(SQLException e) {
			new DAOException("");
		}
		return null;
	}
	
	/**
	 * Cette fonction r�cup�re toutes les entr�es qui matchent avec la requ�te
	 * @param fields - tableau des champs permettant de cr�er la requ�te
	 * @param values - tableau des valeurs permettant de cr�er la requ�te  
	 * @return Un objet de type ResultSet contenant la ligne qui correspond � la requ�te ou null
	 */
	@Override
	public <T>ResultSet listerMere( String[] fields, String[] values ) throws DAOException {
		//Connection connexion = null;
	    //PreparedStatement preparedStatement = null;
	    //ResultSet resultSet = null;
	    String sql = buildSqlQueryString(fields,  values); 
	
	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
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
	        }
	        catch(SQLException e) {
	        	throw new SQLException(e.getMessage()+" : Impossible de compter les lignes de r�sultats.");
	        }
	        
	        
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        //fermeturesSilencieuses(  preparedStatement, connexion );
	    } 
	    return resultSet;
	}
	
	public <T>ResultSet customised(Hashtable<String, String> hash) throws DAOException{
		String sql = "SELECT ";
		try {
			sql += hash.get("fields").toString();
		}catch(NullPointerException  e) {
			sql += " * ";
		}
		sql += " FROM ";
		try {
			sql += " "+hash.get("tables").toString()+" ";
		}catch(NullPointerException  e) {
			throw new DAOException("Aucun table n'a pas été spécifiée pour la recherche.");
		}
		
		String conditions = "";
		try {
			conditions = " "+hash.get("conditions").toString()+" ";
		}catch(NullPointerException  e) {
			conditions = "";
		}finally {
			if(conditions.length()>0) sql += " WHERE "+conditions;
		}
		
		String order = "";
		try {
			order = " "+hash.get("order").toString()+" ";
		}catch(NullPointerException  e) {
			order = "";
		}finally {
			if(order.length()>0) sql += " ORDER BY "+order;
		}
		
		String limit = "";
		try {
			limit = " "+hash.get("limit").toString()+" ";
		}catch(NullPointerException  e) {
			limit = "";
		}finally {
			if(limit.length()>0) sql += " LIMIT "+limit;
		}
		//System.out.println(sql);
	
	    try { 
	        connexion = daoFactory.getConnection();
	         
	        try {
	        	preparedStatement = connexion.prepareStatement(sql); 
		        preparedStatement.executeQuery();
		        resultSet = preparedStatement.getResultSet();	 
	        }
	        catch(SQLException e) {
	        	throw new SQLException(e.getMessage()+" : Impossible de compter les lignes de r�sultats.");
	        }
	        
	        
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        //fermeturesSilencieuses( preparedStatement, connexion );
	    } 
	    return resultSet;
	}
	
	@Override
	public <T> int mettreAJourMere(Long id, String[] fields, String[] values ) throws DAOException {
		int statut = 0;
		
		//Construction de la requete avec les ?
		String sql ="" ;//buildSqlUpdateString(false,id,null,fields,values);
		Object[] objets = new String[values.length+1];
		objets[values.length] = id.toString();

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false,objets,false );
	        statut = preparedStatement.executeUpdate();
	        
	        /* Analyse du statut retourn� par la requ�te d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "�chec lors de la mise � jour, aucune ligne modifi�e dans la table." );
	        }
	         
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses(  preparedStatement, connexion );
	    }
	    return statut;
	}

	@Override
	public <T> int mettreAJourMere(Long id1, Long id2, String[] fields, String[] values, String[] whereFields ) throws DAOException {
		int statut = 0;
		if(id1 == null)
			throw new DAOException("Le premier param�tre est null");
		
		//Construction de la requete avec les ?
		String sql = buildSqlUpdateString(fields,values,whereFields); //System.out.println(sql);
		Object[] objets = new String[(id2 != null) ? values.length+2 : values.length+1];
		for(int i=0; i<values.length; i++)
			objets[i] = values[i];
		objets[values.length] = id1.toString();
		if(id2 != null)
			objets[values.length+1] = id2.toString();

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, sql, false,objets,false );
	        statut = preparedStatement.executeUpdate();
	        
	        /* Analyse du statut retourn� par la requ�te d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "�chec lors de la mise � jour, aucune ligne modifi�e dans la table." );
	        }
	         
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses(  preparedStatement, connexion );
	    }
	    return statut;
	}

	@Override
	public <T> int supprimerMere(Long id, String[] fields, String[] values ) throws DAOException { 
		int statut = 0;
		String sql = buildSqlDeleteString(fields, values );
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, true, id.toString() );
            statut = preparedStatement.executeUpdate();  
            if ( statut == 0 ) {
                throw new DAOException( "�chec de la suppression aucune ligne supprim�e de la table." );
            }  
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
        return statut;
	}
	@Override
	public <T> int supprimerMere(Long id1, Long id2, String[] fields, String[] values ) throws DAOException {
		int statut = 0;
		String sql = buildSqlDeleteString(fields, values ); 
        try {
            connexion = daoFactory.getConnection();
            if(id2 == null)
            	preparedStatement = initialisationRequetePreparee( connexion, sql, true, id1.toString() );
            else
            	preparedStatement = initialisationRequetePreparee( connexion, sql, true, id1.toString(), id2.toString() );
            
            statut = preparedStatement.executeUpdate();  
            if ( statut == 0 ) {
                throw new DAOException( "�chec de la suppression aucune ligne supprim�e de la table." );
            }  
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( preparedStatement, connexion );
        }
        return statut;
	}

	protected String buildSqlQueryString(String[] fields, String[] values) {
		String sql = "";		
		if(fields.length == 0 || values.length == 0 || fields.length != values.length) {
	    	sql = SQL_SELECT;
	    }else {
	    	sql = SQL_SELECT+" WHERE 1<>0 ";
	    	for(String champs:fields)
	    		sql += " AND "+champs+" = ? ";	    	
	    }  
		return sql;
	}
	protected String buildSqlUpdateString(String[] fields, String[] values, String[] whereFields) throws DAOException{
		String sql = "";		
		if(fields.length == 0 || values.length == 0 || fields.length != values.length) {
	    	throw new DAOException("Aucune condition n'a �t� donn�e.");
	    } 
    	sql = SQL_UPDATE;
    	for(int i=0; i<fields.length; i++) {
    		sql += fields[i] + " = ? ";
    		if(i != fields.length-1)
    			sql += " , ";
    	} 
    	sql += (whereFields.length>0) ?" WHERE " : "";
    	for(int i=0; i<whereFields.length; i++) {
    		sql +=  whereFields[i] + " = ? "; 
    		if(i != whereFields.length-1)
    			sql += " AND ";
    	}
	    
		return sql;
	}
	protected String buildSqlDeleteString( String[] fields, String[] values) throws DAOException{
		String sql = SQL_DELETE; 
		
		if(fields.length == 0 || values.length == 0 || fields.length != values.length) {
	    	throw new DAOException("Aucune condition n'a �t� donn�e.");
	    }
		sql += " WHERE ";
		for(int i=0; i<fields.length; i++) {
    		sql += fields[i] + " = ? ";
    		if(i != fields.length-1)
    			sql += " AND ";
    	}  
		return sql;
	}
	
}
