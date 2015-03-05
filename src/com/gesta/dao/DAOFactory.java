package com.gesta.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.gesta.dao.UtilisateurDao;
import com.mysql.jdbc.Driver;

public class DAOFactory {

    private static final String FICHIER_PROPERTIES       = "/com/gesta/dao/dao.properties";
	//private static final String FICHIER_PROPERTIES       = "dao.properties";
    //private static final String FICHIER_PROPERTIES       = "/WEB-INF/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE    = "motdepasse";

    private String              url;
    private String              username;
    private String              password;
    
     
    DAOFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    } 

    /*
     * M�thode charg�e de r�cup�rer les informations de connexion � la base de
     * donn�es, charger le driver JDBC et retourner une instance de la Factory
     */

	public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String nomUtilisateur;
        String motDePasse;
         
        Thread thread = Thread.currentThread();
        ClassLoader classLoader; 
        InputStream fichierProperties = null;
        try {
        	classLoader = (ClassLoader)thread.getContextClassLoader(); 
        	fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );
        }catch(SecurityException e) {
        	throw new SecurityException("Impossible de r�cup�rer le context ClassLoader.");
        }
           
		/*try {
			fichierProperties = new FileInputStream(new File(FICHIER_PROPERTIES));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." ); 
        }

        try {
        	properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );            //url = "jdbc:mysql://localhost:3306/bdd_gesta";
            driver = properties.getProperty( PROPERTY_DRIVER );            //driver = "com.mysql.jdbc.Driver";
            nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );            //nomUtilisateur = "java";
            motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );            //motDePasse = "1234";
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        } 

        try {
            Class.forName( driver ); 
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
        }  

        DAOFactory instance = new DAOFactory( url, nomUtilisateur, motDePasse );
        return instance;
    }

    /* M�thode charg�e de fournir une connexion � la base de donn�es package */ 
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection( url, username, password );  
    }
    
    public DAO getDAOMere() {
    	return new DAOMereImpl(this);
    }
    /*
     * M�thodes de r�cup�ration de l'impl�mentation des diff�rents DAO (un seul
     * pour le moment)
     */
    public UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDaoImpl( this );
    }
    
    /*
     * M�thodes de r�cup�ration de l'impl�mentation  du DAO User
     */
    public DAO getUserDao() {
        return new UserDaoImpl( this );
    }
    
    /*
     * M�thodes de r�cup�ration de l'impl�mentation  du DAO Contact
     */
    public ContactDao getContactDao() {
        return new ContactDaoImpl( this );
    }
    
    /*
     * M�thodes de r�cup�ration de l'impl�mentation  du DAO Contact
     */
    public OptionDao getOptionDao() {
        return new OptionDaoImpl( this );
    }
    
    public ALeDroitDao getALeDroitDao() {
    	return new ALeDroitDaoImpl(this);
    }
    
    public StatutDao getStatutDao() {
    	return new StatutDaoImpl(this);
    }
    
    public DAO getStatut_ActionDao() {
    	return new Statut_ActionDaoImpl(this);
    }
    
    public DAO getStagesDao() {
    	return new StagesDaoImpl(this);
    }
    
    public DAO getEntrepriseDao() {
    	return new EntrepriseDaoImpl(this);
    }
    public DAO getTypeentrepriseDao() {
    	return new TypeentrepriseDaoImpl(this);
    }
    public DAO getTypestageDao() {
    	return new TypestageDaoImpl(this);
    }
    public DAO getTypeconventionDao() {
    	return new TypeconventionDaoImpl(this);
    }
    public DAO getTailleentrepriseDao() {
    	return new TailleentrepriseDaoImpl(this);
    }
    public DAO getLocalisationDao() {
    	return new LocalisationDaoImpl(this);
    }
    public DAO getPaysDao(){
    	return new PaysDaoImpl(this);
    }
    public DAO getStages_optionsDao() {
    	return new Stages_optionsDaoImpl(this);
    }
    public DAO getStage_lieuDao() {
    	return new Stage_lieuDaoImpl(this);
    }
    public DAO getStage_EntrepriseDao() {
    	return new Stage_EntrepriseDaoImpl(this);
    } 
    public DAO getUsers_stagesDao() {
    	return new Users_stagesDaoImpl(this);
    }

	public DAO getEncadrantDao() { 		return new EncadrantDaoImpl(this);	}
	public DAO getEtatvalidationDao() {		return new EtatvalidationDaoImpl(this);	}
    
}