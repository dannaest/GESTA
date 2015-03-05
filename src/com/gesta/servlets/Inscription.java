package com.gesta.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gesta.beans.Options;
import com.gesta.beans.User; 
import com.gesta.dao.ContactDao;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.OptionDao;
import com.gesta.dao.UserDao; 
import com.gesta.forms.InscriptionForm;

public class Inscription extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
	public static final String ATT_OPTIONS = "lesOptions";
    public static final String VUE              = "/WEB-INF/forms/user/userEdit.jsp";
    public static final String CHAMP_EMAIL = "email";
    public static final String CHAMP_PASS = "motdepasse";
    public static final String CHAMP_CONF = "confirmation";
    public static final String CHAMP_NOM = "nom";
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";
    
    private UserDao     utilisateurDao; 
    private OptionDao optionDao;

    public void init() throws ServletException {
        // Récupération d'une instance de notre DAO Utilisateur 
        this.utilisateurDao = (UserDao) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUserDao();
        this.optionDao = (OptionDao)((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getOptionDao();
    }
	

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
    	String[] vide = {}; //tableau vide
    	List<Options> listeOptions = optionDao.lister(vide,vide); 
    	request.setAttribute( ATT_OPTIONS,listeOptions);  
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm( utilisateurDao );

        /* Traitement de la requête et récupération du bean en résultant */
        User utilisateur = form.inscrireUtilisateur( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );
    	    	
    	// Test d'enregistrement d'une option
    	
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
}
