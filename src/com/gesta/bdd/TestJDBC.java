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
	/* La liste qui contiendra tous les résultats de nos essais */
    private List<String> messages = new ArrayList<String>();

    public List<String> executerTests( HttpServletRequest request ) {
    	/* Chargement du driver JDBC pour MySQL */
    	try {
    	    Class.forName( "com.mysql.jdbc.Driver" );  
    	} catch ( ClassNotFoundException e ) {
    	    /* Gérer les éventuelles erreurs ici. */
    	}
    	
    	/* Connexion à la base de données */
    	String url = "jdbc:mysql://localhost:3306/bdd_gesta";
    	String utilisateur = "java";
    	String motDePasse = "1234";
    	Connection connexion = null; 
    	PreparedStatement pStatement = null;
    	ResultSet resultat = null;
    	try {
    	    connexion = (Connection) DriverManager.getConnection( url, utilisateur, motDePasse );
    	    /* Création de l'objet gérant les requêtes */ 
    	    String query = "";
    	    query = "INSERT INTO user (email, mdp, nom, date_insc) VALUES (?, MD5('lavieestbelle78'), 'jean-marc', NOW());";
    	    /* Exécution d'une requête d'écriture */
    	    String mail = "champion@gmail.fr5";
    	    pStatement = (PreparedStatement) connexion.prepareStatement(query);
    	    pStatement.setString(1,mail);
    	    int statut = pStatement.executeUpdate( );
    	    messages.add("Résultat de la requête d'insertion : "+statut);
    	    
    	    query = "SELECT id, email, mdp, nom  FROM user;";
    	    pStatement.close();
    	    pStatement = (PreparedStatement) connexion.prepareStatement(query);
    	    /* Ici, nous placerons nos requêtes vers la BDD */
    	    
    	    /* Exécution d'une requête de lecture */ 
    	    resultat = (ResultSet)pStatement.executeQuery();

    	    /* Récupération des données du résultat de la requête de lecture */
    	    while ( resultat.next() ) {
    	        int idUtilisateur = resultat.getInt( "id" );
    	        String emailUtilisateur = resultat.getString( "email" );
    	        String motDePasseUtilisateur = resultat.getString( "mdp" );
    	        String nomUtilisateur = resultat.getString( "nom" );

    	        /* Traiter ici les valeurs récupérées. */
    	        messages.add("L'utilisateur "+nomUtilisateur+" a pour mail "+emailUtilisateur);
    	    }
    	    
    	} catch ( SQLException e ) {
    	    /* Gérer les éventuelles erreurs ici */
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
