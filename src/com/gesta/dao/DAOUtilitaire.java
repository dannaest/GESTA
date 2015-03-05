package com.gesta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Map;

public class DAOUtilitaire {
	private final String REQ_TYPE = " type ";
	private final String REQ_TYPE_SEARCH = "select";
	private final String REQ_TYPE_INSERT = "insert";
	private final String REQ_TYPE_UPDATE = "update";
	private final String REQ_TYPE_DELETE = "delete";
	/*
	 * Construit une requ�te select en fonction des diff�rentes clauses
	 * fields, tables, conditions, limit, order, 
	 * values
	 */ 
	public String buildRequest(String type, Map<String, String> options) throws DAOException{
		String sql = "";
		try {
			if(!(options.get("tables").length()>0)) { 
				throw new DAOException("Le champ 'tables' est obligatoire. ");
			}
		}catch(DAOException e) {
			throw new DAOException( e );
		} 
		switch(options.get(REQ_TYPE)) {
			case REQ_TYPE_SEARCH : 
				sql = "SELECT ";
				if(options.get("fields").length()>0) {
					sql += options.get("fields");
				}else {
					sql += " * ";
				}
				sql += " FROM ";
				sql += options.get("tables");
				if(options.get("conditions").length()>0) {
					sql += " WHERE ";
					sql += options.get("conditions");
				}
				if(options.get("order").length()>0) {
					sql += " ORDER BY ";
					sql += options.get("order");
				}
				if(options.get("limit").length()>0) {
					sql += " LIMIT ";
					sql += options.get("limit");
				}
				break;
			case REQ_TYPE_INSERT : 
				sql = "INSERT INTO ";
				sql += options.get("tables");
				if(options.get("fields").length()>0) {
					sql += "("+options.get("fields")+")";
				}
				try {
					if(options.get("values").length()>0) {
						sql += " VALUES ";
						sql += "("+options.get("values")+")";
					}else {
						throw new DAOException(REQ_TYPE_INSERT + " : L'option 'values' n'a pas �t� renseign�e.");
					}
				}catch(DAOException e) {
					throw new DAOException( e );
				} 
				break;
			case REQ_TYPE_UPDATE : 
				sql = "UPDATE ";
				sql += options.get("tables");
				sql += " SET ";
				try {
					if(options.get("affectations").length()>0) { 
						sql += options.get("affectations");
					}else {
						throw new DAOException(REQ_TYPE_UPDATE + " : L'option 'affecatations' n'a pas �t� renseign�e.");
					}
				}catch(DAOException e) {
					throw new DAOException( e );
				} 
				if(options.get("conditions").length()>0) {
					sql += " WHERE ";
					sql += options.get("conditions");
				}
				break;
			case REQ_TYPE_DELETE : 
				sql = "DELETE FROM ";
				sql += options.get("tables");
				if(options.get("conditions").length()>0) {
					sql += " WHERE ";
					sql += options.get("conditions");
				}
				if(options.get("order").length()>0) {
					sql += " ORDER BY ";
					sql += options.get("order");
				}
				if(options.get("limit").length()>0) {
					sql += " LIMIT ";
					sql += options.get("limit");
				}
				break;
			default : sql = ""; 
				break;
		}
		
		return sql;
	}
	/*
     * Initialise la requ�te pr�par�e bas�e sur la connexion pass�e en argument,
     * avec la requ�te SQL et les objets donn�s.
     */
    public static PreparedStatement initialisationRequetePreparee( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }
    /*
     * Initialise la requ�te pr�par�e bas�e sur la connexion pass�e en argument,
     * avec la requ�te SQL et les objets donn�s.
     */
    public static PreparedStatement initialisationRequetePreparee( Connection connexion, String sql, boolean returnGeneratedKeys, Object[] objets, boolean fakearg ) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
        	if(!objets[i].equals(String.valueOf("NULL")))
        		preparedStatement.setObject( i + 1, objets[i] );
        	else
        		preparedStatement.setNull(i+1, Types.NULL);
        }
        return preparedStatement;
    }
    
	/* Fermeture silencieuse du resultset */
    public static void fermetureSilencieuse( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "�chec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse du statement */
    public static void fermetureSilencieuse( PreparedStatement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "�chec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }

    /* Fermeture silencieuse de la connexion */
    public static void fermetureSilencieuse( Connection connexion ) {
        if ( connexion != null ) {
            try {
                connexion.close();
            } catch ( SQLException e ) {
                System.out.println( "�chec de la fermeture de la connexion : " + e.getMessage() );
            }
        }
    }

    /* Fermetures silencieuses du statement et de la connexion */
    public static void fermeturesSilencieuses( PreparedStatement statement, Connection connexion ) {
        fermetureSilencieuse( statement );
        fermetureSilencieuse( connexion );
    }

    /* Fermetures silencieuses du resultset, du statement et de la connexion */
    public static void fermeturesSilencieuses( ResultSet resultSet, PreparedStatement statement, Connection connexion ) {
        fermetureSilencieuse( resultSet );
        fermetureSilencieuse( statement );
        fermetureSilencieuse( connexion );
    }
    
    /*
     * Recherche
     */
    public static void search() {
    	
    }
    
    public static int CountResult(ResultSet resultset) throws SQLException {
    	int rowcount = 0;
    	if (resultset.last()) {
    	  rowcount = resultset.getRow();
    	  resultset.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
    	}
    	return rowcount;
    }
}
