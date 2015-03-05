package com.gesta.forms;

import javax.servlet.http.HttpServletRequest;

import com.gesta.beans.ALeDroit; 
import com.gesta.constantes.Constantes;
import com.gesta.dao.*;

public class FormALeDroit extends GestionFormulaire {
	public static final String CHAMP_SERVLET = "servlet"; 
	public static final String CHAMP_DO = "do"; 
	public static final String CHAMP_ACTION = "action"; 
	 
    private ALeDroitDao      aledroitDao; 
    
    public FormALeDroit(ALeDroitDao aledroitDao){
    	this.aledroitDao = aledroitDao; 
    }

    public ALeDroit ajouterAction( HttpServletRequest request ) {
        /* Récupération des champs du formulaire */
        String servlet = getValeurChamp( request, CHAMP_SERVLET );
        String doMethod = getValeurChamp( request, CHAMP_DO );
        String action = getValeurChamp( request, CHAMP_ACTION );
        
        ALeDroit aledroit = new ALeDroit();
        
        try {
            validationServlet( servlet );
        } catch ( FormValidationException e ) {
        	setErreur( CHAMP_SERVLET, e.getMessage() );
        }
    	aledroit.setServlet(servlet);
    	
    	try {
            validationDoMethod( doMethod );
        } catch ( FormValidationException e ) {
        	setErreur( CHAMP_DO, e.getMessage() );
        }
    	aledroit.setDoMethod(doMethod);
    	
    	try {
            validationAction( action );
        } catch ( FormValidationException e ) {
        	setErreur( CHAMP_ACTION, e.getMessage() );
        }
    	aledroit.setAction(action); 
    	        
        try { 
			if ( erreurs.isEmpty() ) { 
				validationCorrespondance_Servlet_DoMethode_Action(servlet,doMethod,action);
		    	aledroitDao.creer( aledroit ); 
			    resultat = "Succès de l'ajout.";
			} else {
			     resultat = "Échec de l'ajout."; 
			}
        }catch ( FormValidationException e ) {
        	setErreur( "0", "Force error" );
        	resultat = e.getMessage();
        }catch ( DAOException e ) {
            resultat = "Échec de l'ajout : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }  
        
        return aledroit;
    }

    /* Validation du trio servlet, doMethode, action */
    private void validationCorrespondance_Servlet_DoMethode_Action( String servlet, String doMethod, String action) throws FormValidationException {
        String[] Fields = {"ald_servlet","ald_do","ald_action"}, 
        		 Values = {servlet,doMethod,action};
        if ( aledroitDao.lister( Fields, Values ).size()>0 ) {
            throw new FormValidationException( "Cette configuration existe déjà, merci d'en créer une autre." );
        } 
    } 
    /* Validation de la servlet */
    private void validationServlet( String servlet) throws FormValidationException {
        if(servlet == null || servlet.length()==0) {
        	throw new FormValidationException("Veuillez saisir un nom de servlet valide.");
        }
        String[] Fields = {"ald_action"}, Values = {servlet};
        if ( aledroitDao.lister( Fields, Values ).size()>0 ) {
            throw new FormValidationException( "Cette servlet existe déjà, merci d'en créer une autre." );
        } 
    } 
    /* Validation de la doMethod */
    private void validationDoMethod( String doMethod) throws FormValidationException {
        if ( doMethod == null || doMethod.length() == 0) { 
        	throw new FormValidationException("Veuillez saisir une méthode valide.");
        } 
        if(!doMethod.equals(Constantes.SERVLET_METHOD_GET)
        		&& !doMethod.equals(Constantes.SERVLET_METHOD_POST)
        		&& !doMethod.equals(Constantes.SERVLET_METHOD_HEAD))
        	throw new FormValidationException("Veuillez saisir une des méthodes suivantes : do, post, head");
    } 
    /* Validation de la doMethod */
    private void validationAction( String action) throws FormValidationException {
        if ( action == null || action.length()==0) {
        	throw new FormValidationException("Veuillez saisir une action valide.");
        } 
    } 

}
