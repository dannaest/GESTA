package com.gesta.forms;

import javax.servlet.http.HttpServletRequest;

import com.gesta.beans.Eleves;
import com.gesta.beans.Enseignant;
import com.gesta.beans.Options;
import com.gesta.beans.User;
import com.gesta.dao.DAOException;
import com.gesta.dao.OptionDao;

public final class FormOption extends GestionFormulaire {
    public static final String CHAMP_NOM = "nom"; 
 
    private OptionDao      optionDao; 
    
    public FormOption(OptionDao optionDao){
    	this.optionDao = optionDao; 
    }
    public Options ajouterOption( HttpServletRequest request ) {
        /* R�cup�ration des champs du formulaire */
        String nom = getValeurChamp( request, CHAMP_NOM );
        
        Options option = new Options();
        
        try {
        	traiterOption(nom,option);
			if ( erreurs.isEmpty() ) { 
			     optionDao.creer( option ); 
			     resultat = "Succ�s de l'ajout.";
			} else {
			     resultat = "�chec de l'ajout."; 
			}
        }catch ( DAOException e ) {
            resultat = "�chec de l'ajout : une erreur impr�vue est survenue, merci de r�essayer dans quelques instants.";
            e.printStackTrace();
        }  
        
        return option;
    }
    private void traiterOption( String nom, Options option ) {
    	try {
            validationOption( nom );
        } catch ( FormValidationException e ) {
        	setErreur( CHAMP_NOM, e.getMessage() );
        }
        option.setNom(nom);
    }
    /* Validation de l'option */
    private void validationOption( String nom) throws FormValidationException {
        if ( nom != null ) { 
            if (nom.length()==0) {
            	throw new FormValidationException("Veuillez saisir une option valide.");
            }
            else if ( optionDao.trouver( nom ) != null ) {
                throw new FormValidationException( "Cette option existe d�j�, merci d'en cr�er une autre." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir une option." );
        }
    } 
}
