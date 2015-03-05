package com.gesta.forms;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.gesta.beans.Eleves;
import com.gesta.beans.User;
import com.gesta.beans.Enseignant;
import com.gesta.dao.ContactDao;
import com.gesta.dao.DAOException;
import com.gesta.dao.UserDao; 

public final class InscriptionForm extends GestionFormulaire{
    private static final String CHAMP_NOM    = "nom";
    private static final String CHAMP_PRENOM    = "prenom";
	private static final String CHAMP_MAIL  = "mail";
	private static final String CHAMP_MAIL2  = "mail";
	private static final String CHAMP_TEL  = "tel";
	private static final String CHAMP_LOGIN  = "login";
    private static final String CHAMP_PASS   = "mdp";
    private static final String CHAMP_PASS2   = "mdp2";
    private static final String CHAMP_TYPE   = "type_user";
    private static final String CHAMP_NIVEAU   = "niveau";
    private static final String CHAMP_DEPARTEMENT   = "departement";
    private static final String CHAMP_OPTION   = "option"; 
    
    public  static final int VALUE_TYPE_ELEVE = 0; 
    public  static final int VALUE_TYPE_ENSEIGNANT = 1;
    
    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();
    private UserDao      utilisateurDao; 
    
    private static final String ALGO_CHIFFREMENT = "SHA-256";
    
    public InscriptionForm(UserDao utilisateurDao) {
    	this.utilisateurDao = utilisateurDao; 
    }
    
    public User inscrireUtilisateur( HttpServletRequest request ) {
        String nom = getValeurChamp( request, CHAMP_NOM );
        String prenom = getValeurChamp( request, CHAMP_PRENOM );
        String mail = getValeurChamp( request, CHAMP_MAIL );
        String mail2 = getValeurChamp( request, CHAMP_MAIL2 );
        String tel = getValeurChamp( request, CHAMP_TEL );
        String login = getValeurChamp( request, CHAMP_LOGIN );
        String mdp = getValeurChamp( request, CHAMP_PASS );
        String mdp2 = getValeurChamp( request, CHAMP_PASS2 );
        int type = Integer.parseInt(getValeurChamp( request, CHAMP_TYPE ));
        String niveau = getValeurChamp( request, CHAMP_NIVEAU );
        String departement = getValeurChamp( request, CHAMP_DEPARTEMENT );
        int option = Integer.parseInt(getValeurChamp( request, CHAMP_OPTION ));
        

        User utilisateur = new User();

        try {
            traiterNom( nom, utilisateur );
            traiterPrenom( prenom, utilisateur );
            traiterEmail( mail, mail2, utilisateur );
            traiterTel(tel,utilisateur);
            traiterLogin( login, utilisateur );
            traiterMotsDePasse( mdp, mdp2, utilisateur );

            if(type==VALUE_TYPE_ELEVE) {
            	departement = ""; 
            }
            else {
            	niveau = ""; 
            }
             
            if ( erreurs.isEmpty() ) {
            	if(type==VALUE_TYPE_ELEVE) { 
                	Eleves eleve = new Eleves(utilisateur); 
                	eleve.setNiveau(niveau);
                	utilisateurDao.creer( eleve );
                }
                else {
                	Enseignant enseignant = new Enseignant(utilisateur);
                	enseignant.setDepartement(departement);
                	utilisateurDao.creer( enseignant );
                } 
                resultat = "Succès de l'inscription.";
            } else {
                resultat = "Échec de l'inscription."; 
            }
        } catch ( DAOException e ) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }  
        
        return utilisateur;
    }
    /*
     * Appel à la validation de l'adresse email reçue et initialisation de la
     * propriété email du bean
     */
    private void traiterEmail( String email, String email2, User utilisateur ) {
    	try {
            validationEmail( email, email2 );
        } catch ( FormValidationException e ) {
        	setErreur( CHAMP_MAIL, e.getMessage() );
        }
        utilisateur.getContact().setMail( email );
    }
    /*
     * Appel à la validation du login reçue et initialisation de la
     * propriété login du bean
     */
    private void traiterLogin( String login, User utilisateur ) {
        try {
            validationLogin( login );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_LOGIN, e.getMessage() );
        }
        utilisateur.setLogin( login );
    }

    /*
     * Appel à la validation des mots de passe reçus, chiffrement du mot de
     * passe et initialisation de la propriété motDePasse du bean
     */
    private void traiterMotsDePasse( String motDePasse, String confirmation, User utilisateur ) {
        try {
            validationMotsDePasse( motDePasse, confirmation );
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
            setErreur( CHAMP_PASS2, null );
        }

        /*
         * Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe
         * efficacement.
         * 
         * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage
         * aléatoire et un grand nombre d'itérations de la fonction de hashage.
         * 
         * La String retournée est de longueur 56 et contient le hash en Base64.
         */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        String motDePasseChiffre = passwordEncryptor.encryptPassword( motDePasse );

        utilisateur.setMdp( motDePasseChiffre );
    }

    private void traiterNom( String nom, User utilisateur ) {
    	if(nom==null || nom.length()==0) 
		setErreur( CHAMP_NOM,"Veuillez saisir un nom." );
    	utilisateur.getContact().setNom(nom);
    }
    private void traiterPrenom( String prenom, User utilisateur ) {
    	if(prenom==null || prenom.length()==0) 
    		setErreur( CHAMP_PRENOM,"Veuillez saisir un prénom." );
    	utilisateur.getContact().setPrenom(prenom);
    }
    private void traiterTel(String tel, User utilisateur) {
    	if(tel==null)
    		tel = " ";
    	utilisateur.getContact().setTel(tel);
    }
    /* Validation de l'adresse email */
    private void validationEmail( String email, String email2 ) throws FormValidationException {
        if ( email != null && email2 != null ) {
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
                throw new FormValidationException( "Merci de saisir une adresse mail valide." );
            } 
            else if (!email.equals(email2)) {
            	throw new FormValidationException("Les emails ne sont pas identiques.");
            }
            else if ( utilisateurDao.trouver( email ) != null ) {
                throw new FormValidationException( "Cette adresse email est déjà utilisée, merci d'en choisir une autre." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir une adresse mail et sa confirmation." );
        }
    }     

    /* Validation du login*/
    private void validationLogin( String login ) throws FormValidationException {
        if ( login != null ) {
        	String[] fields = {"u_login"};
        	String[] values = {login};
            if ( utilisateurDao.lister( fields, values ).size() != 0 ) {
                throw new FormValidationException( "Ce login est déjà utilisé, merci d'en choisir un autre." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir un login." );
        }
    } 

    /**
     * Valide les mots de passe saisis.
     */
    private void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception{
        if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
            if (!motDePasse.equals(confirmation)) {
                throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
            } else if (motDePasse.trim().length() < 3) {
                throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
            }
        } else {
            throw new Exception("Merci de saisir et confirmer votre mot de passe.");
        }
    }

    /**
     * Valide le nom d'utilisateur saisi.
     */
    private void validationNom( String nom ) throws Exception {
        if ( nom != null && nom.trim().length() < 3 ) {
            throw new Exception( "Le nom d'utilisateur doit contenir au moins 3 caractères." );
        }
    }


}
