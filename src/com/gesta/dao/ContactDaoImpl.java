package com.gesta.dao;

import static com.gesta.dao.DAOUtilitaire.*; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gesta.beans.Contact;  
import com.gesta.beans.Localisation;

public class ContactDaoImpl extends DAOMereImpl implements ContactDao{ 
	private static final String S_SQL_SELECT = "SELECT * FROM contact";
	private static final String SQL_SELECT_PAR_EMAIL = "SELECT * FROM contact WHERE cnt_mail = ?";
	private static final String S_SQL_INSERT = "INSERT INTO contact ( cnt_nom, cnt_prenom, cnt_mail,cnt_tel,cnt_fax) VALUES (?, ?, ?, ?,'')";
	private static final String S_SQL_UPDATE = "UPDATE contact SET ";
	private static final String S_SQL_DELETE = "DELETE FROM contact ";
	
	ContactDaoImpl(DAOFactory daoFactory){
		super(daoFactory);  
		//on initialise les champs de la m�re
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = S_SQL_UPDATE;
		this.SQL_DELETE = S_SQL_DELETE;
	}
	
	@Override
	public long creer(Contact contact) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;
	    long id = 0;

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, S_SQL_INSERT, true, contact.getNom(), contact.getPrenom(), contact.getMail(), contact.getTel() );
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourn� par la requ�te d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "�chec lors de la cr�ation du contact, aucune ligne ajout�e dans la table." );
	        }
	        /* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propri�t� id du bean Utilisateur avec sa valeur */
	        	id = valeursAutoGenerees.getLong( 1 );
	            contact.setId( id );
	        } else {
	            throw new DAOException( "L'auto-g�n�ration de l'ID a �chou�." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	    return id;
	}

	@Override
	public Contact trouver(String email) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Contact contact = null; 

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, (Object)email,false );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
	        if ( resultSet.next() ) {
	            contact = map( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return contact;
	}
	
	@Override
	public List<Contact> lister(  String[] fields, String[] values ) throws DAOException{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    String sql;
	    if(fields.length == 0 || values.length == 0 || fields.length != values.length) {
	    	sql = S_SQL_SELECT;
	    }else {
	    	sql = "SELECT * FROM contact c WHERE 1<>0 ";
	    	for(String champs:fields)
	    		sql += " AND "+champs+" = ? ";	    	
	    }

	    List<Contact> contacts = new ArrayList<Contact>();

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

		        while ( resultSet.next() ) {
    	            contacts.add(map( resultSet ));
    	        }
	        }
	        catch(SQLException e) {
	        	throw new SQLException(e.getMessage()+" : Impossible de compter les lignes de r�sultats.");
	        }
	        
	        
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    } 
	    return contacts;
	}
	  
	public boolean mettreAJour(Contact old_cnt, Contact cnt)throws DAOException{
		String[] fields = {"cnt_nom","cnt_prenom","cnt_mail","cnt_tel"};
		String[] values = {cnt.getNom(),cnt.getPrenom(),cnt.getMail(),cnt.getTel()}; 
		String[] whereFields = new String[] {"id_contact"};
		int retour = super.mettreAJourMere(old_cnt.getId(), null,fields, values, whereFields);
		return retour == 0 ? false : true; 
	}
	
	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    public static Contact map( ResultSet resultSet ) throws SQLException {
        Contact contact = new Contact(null,"", "", "", "", "");
        contact.setId( resultSet.getLong( "id_contact" ) );  
        //contact.setCivilite( resultSet.getString( "cnt_civilite" ) );
        contact.setNom( resultSet.getString( "cnt_nom" ) );
        contact.setPrenom( resultSet.getString( "cnt_prenom" ) );
        contact.setMail( resultSet.getString( "cnt_mail" ) );
        contact.setTel( resultSet.getString( "cnt_tel" ) );
        return contact;
    }
}
