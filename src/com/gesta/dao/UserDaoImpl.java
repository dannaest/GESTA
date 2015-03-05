package com.gesta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.gesta.beans.Statut;
import com.gesta.beans.User;
import com.gesta.constantes.Constantes;

import static com.gesta.dao.DAOUtilitaire.*;

public class UserDaoImpl extends DAOMereImpl {
	private DAOFactory daoFactory;
	private ContactDao contactDao;
	private StatutDao statutDao;
	
	private static final String S_SQL_SELECT = "SELECT * FROM users u JOIN contact c WHERE u.id_contact = c.id_contact";
	private static final String SQL_SELECT_PAR_EMAIL = "SELECT * FROM users u JOIN contact c WHERE u.id_contact = c.id_contact AND c.cnt_mail = ?";
	private static final String S_SQL_INSERT = "INSERT INTO users (id_contact, u_login, u_mdp, u_date_inscription) VALUES (?, ?, ?, NOW())"; 
	
	UserDaoImpl(DAOFactory daoFactory){
		super(daoFactory);
		this.daoFactory = daoFactory;
		this.contactDao = this.daoFactory.getContactDao();
		this.statutDao = this.daoFactory.getStatutDao();
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = "";
		this.SQL_DELETE = "";
	}
	
	 
	public long creer(User utilisateur) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;  
	    
	    //Cr�ation du contact associ� � l'utilisateur
	    long id_contact = contactDao.creer(utilisateur.getContact());
	    
	    //cr�ation de l'utilisateur
	    long id = 0;
	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, new String(""+id_contact),utilisateur.getLogin(), utilisateur.getMdp());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourn� par la requ�te d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "�chec lors de la cr�ation de l'utilisateur, aucune ligne ajout�e dans la table." );
	        }
	        /* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propri�t� id du bean Utilisateur avec sa valeur */
	        	id = valeursAutoGenerees.getLong( 1 );
	            utilisateur.setId( id );
	        } else {
	            throw new DAOException( "L'auto-g�n�ration de l'ID a �chou�." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	    //Cr�eation des statuts de l'utilisateur
	    List<Statut> lesStatuts = utilisateur.getStatuts();
	    if(lesStatuts == null || lesStatuts.size() == 0) // si la fonction appelante n'a pas d�fini de statuts, le statut par d�faut est ELEVE
	    	statutDao.creerUserStatut(utilisateur.getId(),Constantes.STATUT_ELEVE); 
	    else { // sinon on enregistre tous les statuts d�finis
	    	for(int i=0;i<lesStatuts.size(); i++) {
	    		statutDao.creerUserStatut(utilisateur.getId(),lesStatuts.get(i).getId());
	    	}
	    }
	    	
	    return id;
	}

	public User trouver(String email) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User utilisateur = null;
	    Object[] objet = {email};

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, objet,false );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
	        /*R�cup�ration de l'utilisateur*/
	        if ( resultSet.next() ) {
	            utilisateur = map( resultSet );
	        } 
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return utilisateur;
	}
	
	public List<User> lister(  String[] fields, String[] values ) throws DAOException{
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    String sql;
	    if(fields==null || values==null || fields.length == 0 || values.length == 0 || fields.length != values.length) {
	    	sql = SQL_SELECT;
	    }else {
	    	sql = "SELECT * FROM users u JOIN contact c WHERE u.id_contact = c.id_contact ";
	    	for(String champs:fields)
	    		sql += " AND "+champs+" = ? ";	    	
	    }

	    List<User> users = new ArrayList<User>();

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
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
    	            users.add(map( resultSet )); 
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
	    return users;
	}
	
	public List<User> listerEleves(User user)throws DAOException {
		DAOMereImpl mere = new DAOMereImpl(daoFactory);
		Hashtable<String,String> hash = new Hashtable<String,String>();
		hash.put("tables", "users u  NATURAL JOIN eleves_enseignants ee, contact c");
		hash.put("conditions", "ee.id_eleve = u.id_user AND c.id_contact=u.id_contact AND ee.id_enseignant = "+user.getId().toString());
		mere.customised(hash);
		List<User> users = new ArrayList<User>();
		try {
			while ( mere.resultSet.next() ) {
			    users.add(map( mere.resultSet )); 
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}finally {
			fermeturesSilencieuses( mere.resultSet, mere.preparedStatement, mere.connexion );
		}
		return users;
	}
	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private static User map( ResultSet resultSet ) throws SQLException {
        User utilisateur = new User();
        utilisateur.setId( resultSet.getLong( "id_contact" ) );
        utilisateur.setLogin( resultSet.getString( "u_login" ) );
        utilisateur.setMdp( resultSet.getString( "u_mdp" ) ); 
        utilisateur.setAffiliation(resultSet.getString( "u_affiliation" ) );
        utilisateur.setDate_inscription( resultSet.getTimestamp( "u_date_inscription" ) );
        utilisateur.getContact().setNom( resultSet.getString( "cnt_nom" ) );
        utilisateur.getContact().setPrenom( resultSet.getString( "cnt_prenom" ) );
        utilisateur.getContact().setMail( resultSet.getString( "cnt_mail" ) );
        utilisateur.getContact().setTel( resultSet.getString( "cnt_tel" ) );
        return utilisateur;
    }

}
