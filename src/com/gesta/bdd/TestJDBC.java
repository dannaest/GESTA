package com.gesta.bdd;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class TestJDBC {
	/* La liste qui contiendra tous les r�sultats de nos essais */
    private List<String> messages = new ArrayList<String>();

    public List<String> executerTests( HttpServletRequest request ) {
    	/* Chargement du driver JDBC pour MySQL */
    	try {
    	    Class.forName( "com.mysql.jdbc.Driver" );  
    	} catch ( ClassNotFoundException e ) {
    	    /* G�rer les �ventuelles erreurs ici. */
    	}
    	
    	/* Connexion � la base de donn�es */
    	String url = "jdbc:mysql://localhost:3306/bdd_gesta";
    	String utilisateur = "java";
    	String motDePasse = "1234";
    	Connection connexion = null; 
    	PreparedStatement pStatement = null;
    	ResultSet resultat = null;
    	try {
    	    connexion = (Connection) DriverManager.getConnection( url, utilisateur, motDePasse );
    	    /* Cr�ation de l'objet g�rant les requ�tes */ 
    	    String query = "";
    	    query = "INSERT INTO user (email, mdp, nom, date_insc) VALUES (?, MD5('lavieestbelle78'), 'jean-marc', NOW());";
    	    /* Ex�cution d'une requ�te d'�criture */
    	    String mail = "champion@gmail.fr5";
    	    pStatement = (PreparedStatement) connexion.prepareStatement(query);
    	    pStatement.setString(1,mail);
    	    int statut = pStatement.executeUpdate( );
    	    messages.add("R�sultat de la requ�te d'insertion : "+statut);
    	    
    	    query = "SELECT id, email, mdp, nom  FROM user;";
    	    pStatement.close();
    	    pStatement = (PreparedStatement) connexion.prepareStatement(query);
    	    /* Ici, nous placerons nos requ�tes vers la BDD */
    	    
    	    /* Ex�cution d'une requ�te de lecture */ 
    	    resultat = (ResultSet)pStatement.executeQuery();

    	    /* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
    	    while ( resultat.next() ) {
    	        int idUtilisateur = resultat.getInt( "id" );
    	        String emailUtilisateur = resultat.getString( "email" );
    	        String motDePasseUtilisateur = resultat.getString( "mdp" );
    	        String nomUtilisateur = resultat.getString( "nom" );

    	        /* Traiter ici les valeurs r�cup�r�es. */
    	        messages.add("L'utilisateur "+nomUtilisateur+" a pour mail "+emailUtilisateur);
    	    }
    	    
    	} catch ( SQLException e ) {
    	    /* G�rer les �ventuelles erreurs ici */
    		messages.add( "Erreur lors de la connexion : <br/>"+ e.getMessage() );

    	} finally {
    		if ( resultat != null ) {
    	        try {
    	            /* On commence par fermer le ResultSet */
    	            resultat.close();
    	        } catch ( SQLException ignore ) {
    	        }
    	    }
    	    if ( pStatement != null ) {
    	        try {
    	            /* Puis on ferme le Statement */
    	            pStatement.close();
    	        } catch ( SQLException ignore ) {
    	        }
    	    } 
    	    if ( connexion != null )
    	        try {
    	            /* Fermeture de la connexion */
    	            connexion.close();
    	        } catch ( SQLException ignore ) {
    	            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
    	        }
    	}

        return messages;
    }

}
