package com.gesta.forms;

import javax.servlet.http.HttpServletRequest;
 
import com.gesta.beans.Statut; 
import com.gesta.dao.DAOException;
import com.gesta.dao.StatutDao;

public class FormStatut extends GestionFormulaire {
	public static final String CHAMP_NOM = "nom"; 
	public static final String CHAMP_NIVDROIT = "nivDroit"; 
	 
    private StatutDao      statutDao; 
    
    public FormStatut(StatutDao statutDao){
    	this.statutDao = statutDao; 
    }

    public Statut ajouterStatut( HttpServletRequest request ) {
        /* Récupération des champs du formulaire */
        String nom = getValeurChamp( request, CHAMP_NOM );
        String nivDroit = getValeurChamp( request, CHAMP_NIVDROIT );
        
        Statut statut = new Statut(null, null, 0);
        
        try {
        	try {
                validationNom( nom );
            } catch ( FormValidationException e ) {
            	setErreur( CHAMP_NOM, e.getMessage() );
            }
        	statut.setNom(nom);
        	try {
                validationNivDroit( nivDroit );
            } catch ( FormValidationException e ) {
            	setErreur( CHAMP_NIVDROIT, e.getMessage() );
            }
        	statut.setNivDroit(Integer.parseInt(nivDroit));
        	
			if ( erreurs.isEmpty() ) { 
				statutDao.creer( statut ); 
			    resultat = "Succès de l'ajout.";
			} else {
			     resultat = "Échec de l'ajout."; 
			}
        }catch ( DAOException e ) {
            resultat = "Échec de l'ajout : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }  
        
        return statut;
    }
     
    /* Validation de l'option */
    private void validationNom( String nom) throws FormValidationException {
        if ( nom != null ) { 
            if (nom.length()==0) {
            	throw new FormValidationException("Veuillez saisir un statut valide.");
            }
            String[] Fields = {"sta_nom"}, Values = {nom};
            if ( statutDao.lister( Fields, Values ).size()>0 ) {
                throw new FormValidationException( "Ce statut existe déjà, merci d'en créer un autre." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir un statut." );
        }
    } 
    /* Validation de l'option */
    private void validationNivDroit( String id) throws FormValidationException {
        if ( id != null ) { 
        	try {
        		Long.parseLong(id);
        	}catch(NumberFormatException e) {
        		throw new FormValidationException("Le niveau droit saisi n'est pas un nombre entier (Long).");
        	}
        
            if (id.length()==0) {
            	throw new FormValidationException("Veuillez saisir un niveau de droit valide.");
            }
            String[] Fields = {"sta_nivDroit"}, Values = {String.valueOf(id)};
            if ( statutDao.lister( Fields, Values ).size()>0 ) {
                throw new FormValidationException( "Ce numéro correspond déjà à un statut." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir un niveau de droit." );
        }
    } 
    

}
