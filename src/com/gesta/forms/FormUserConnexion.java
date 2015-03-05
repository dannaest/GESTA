package com.gesta.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor; 

import com.gesta.beans.Statut;
import com.gesta.beans.User;
import com.gesta.constantes.Constantes;
import com.gesta.dao.DAOException;
import com.gesta.dao.StatutDao;
import com.gesta.dao.UserDao;
import com.gesta.dao.UserDaoImpl;
import com.gesta.ldap.LDAPConfiguration;
import com.gesta.ldap.LDAPException;

public final class FormUserConnexion extends GestionFormulaire {
    public static final String CHAMP_LOGIN = "login";
    public static final String CHAMP_PASS = "mdp"; 
    public static final String ATT_RESULTAT = "resultat"; 
 
    private UserDaoImpl      utilisateurDao; 
    private User 		 utilisateur = new User();
    
    private StatutDao 	 statutDao;
    
    private static final String ALGO_CHIFFREMENT = "SHA-256";
    
    public FormUserConnexion(UserDaoImpl utilisateurDao2, StatutDao statutDao){
    	this.utilisateurDao = utilisateurDao2; 
    	this.statutDao = statutDao; 
    }
    
    public User connecterUtilisateurLDAP(HttpServletRequest request) {
        /* R�cup�ration des champs du formulaire */ 
        String login = getValeurChamp( request, CHAMP_LOGIN );
        String mdp = getValeurChamp( request, CHAMP_PASS ); 
        
        //phase de validation des donn�es saisies
        try{
            validationLogin(login); 
        }catch(FormValidationException e){
            setErreur( CHAMP_LOGIN, e.getMessage() );
        }
        try{
            validationMotDePasse(mdp);    
        }catch(FormValidationException e){
            setErreur( CHAMP_PASS, e.getMessage() );
        }         
        
        // si le formulaire contient des erreurs, c'est pas la peine de tester la connexion
        if(!getErreurs().isEmpty())
            return null;
        
        //test des identifiants sur le serveur
        //User user = new User();
        utilisateur.setLogin(login);
        utilisateur.setMdp(mdp);
        
        LDAPConfiguration ldap = new LDAPConfiguration();   
        try{
        	if(ldap.identify(utilisateur)) { 
        		resultat = "Connexion r�ussie."; 
        		utilisateur = ldap.getUtilisateur();
        		//r�cup�ration du ou des statuts de l'utilisateur
        		
        		
        	}
        	else {
        		resultat = "La connexion a �chou�."; 
        	}
        }catch(LDAPException e){
        	resultat = e.getMessage(); 
        }
        return utilisateur;
    }
    
    public User connecterUtilisateurSQL( HttpServletRequest request ) {
        /* R�cup�ration des champs du formulaire */
        String login = getValeurChamp( request, CHAMP_LOGIN );
        String mdp = getValeurChamp( request, CHAMP_PASS );

        /* Validation du champ mot de passe. 
         * On veut s'assurer que le mot de passe est long d'au moins 3 caract�res.*/
        try {
            validationMotDePasse( mdp );
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }
        utilisateur.setMdp( mdp );

        /* Validation du champ email. */
        try {
            validationLoginMdp( login,mdp );
        } catch ( Exception e ) {
            setErreur( CHAMP_LOGIN, e.getMessage() );
        }
        utilisateur.setLogin( login ); 
        //utilisateur.setAffiliation(Constantes.AFFILIATION_ELEVE);

        /* Initialisation du r�sultat global de la validation. */
        if ( erreurs.isEmpty() ) { 
        	resultat = "Succ�s de la connexion.";
        	try {
        		utilisateur.setStatuts(getStatutsUtilisateur());
        	}catch(DAOException e) {
        		utilisateur = new User();
        		resultat = "Echec lors de l'attribution des droits.";
        		setErreur(CHAMP_LOGIN,e.getMessage());
        	}
            
        } else {
            resultat = "�chec de la connexion.";
        }

        return utilisateur;
    }
    /**
     * @Role R�cup�rer les statuts d'un utiliateur. Si l'utilisateur n'a pas de statuts enregistr� dans la base de donn�es
     * on en cr�e un qui lui accorde les droits minimaux correspondant � son affiliation 
     * @return
     */
    private List<Statut> getStatutsUtilisateur() throws DAOException {
    	List<Statut> lesStatuts = new ArrayList<Statut>();
    	
    	/*
    	 * Selon le type de connexion, l'utilisateur obtient un identifiant (id_user, pour SQL) et ou non (LDAP)
    	 * En 1er lieu on s'assure que le champ id est non nul et diff�rent de 0 
    	 */
    	Long id_user = utilisateur.getId();
    	try {
	    	if(id_user == null || id_user == 0) { // connexion LDAP
	    		//on essaie ensuite de r�cup�rer son id dans la table en passant par son login	    		
	    		List<User> users = utilisateurDao.lister(new String[] {"u_login"}, new String[] {utilisateur.getLogin()});
	    		if(users==null || users.size()==0) { // il s'agit donc de la 1�re visite. 
	    			id_user = utilisateurDao.creer(utilisateur); // On le rajoute � la base de donn�es
	    			//on met � jour l'utilisateur
	    			utilisateur = utilisateurDao.lister(new String[] {"u_login"}, new String[] {utilisateur.getLogin()}).get(0);
	    			id_user = utilisateur.getId();
	    		}
	    	}
	    	// son id est bien dans la base de donn�es
	    	lesStatuts = statutDao.trouver(id_user);
	    	
	    	if(lesStatuts.size()==0) {
	    		if(utilisateur.getAffiliation().equals(Constantes.AFFILIATION_ELEVE))
	    			statutDao.creerUserStatut(utilisateur.getId(),Constantes.STATUT_ELEVE);
	    		else
	    			statutDao.creerUserStatut(utilisateur.getId(),Constantes.STATUT_ENSEIGNANT);  
	    		//on met � jour ses statuts qui viennent d'�tre enregistr�s
	    		lesStatuts = statutDao.trouver(utilisateur.getId());
	    	} 
    		
    	}catch(DAOException e) {
    		throw new DAOException(e.getMessage());
    	}
    	
    	return lesStatuts;
    }
    
    /**
     * Valide le login saisi
     */
    private void validationLogin(String login) throws FormValidationException{
        if(login == null || login.length()==0){ 
            throw new FormValidationException("Merci de sisir un login.");
        }
    }
    /**
     * Valide l'adresse email saisie.
     */
    private void validationLoginMdp( String login , String mdp) throws FormValidationException {
    	/*Encryptage du mot de passe avant test de validation*/
    	ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        //String motDePasseChiffre = passwordEncryptor.encryptPassword( mdp );
        //System.out.println(motDePasseChiffre);
        
        if ( login != null ) { 
        	String[] fields = {"u_login"}; 
        	String[] values = {login};
        	List<User> liste = new ArrayList<User>();
        	try {
        		liste = utilisateurDao.lister( fields, values );
        		utilisateur = liste.get(0);
        	}catch(DAOException e) {
        		throw new FormValidationException("Une erreur est survenue. Veuillez ressayer plus tard.");
        	}
            if ( utilisateur==null || !passwordEncryptor.checkPassword(mdp,utilisateur.getMdp())) {
                throw new FormValidationException( "Aucune correspondance pour le couple 'login/mot de passe' saisi." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir un login." );
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse( String motDePasse ) throws FormValidationException {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 3 ) {
                throw new FormValidationException( "Le mot de passe doit contenir au moins 3 caract�res." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir votre mot de passe." );
        }
    }

}
