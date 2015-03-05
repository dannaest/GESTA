package com.gesta.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gesta.beans.User;
import com.gesta.constantes.*;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.UserDao;
import com.gesta.dao.UserDaoImpl;
import com.gesta.forms.FormUserConnexion;
import com.gesta.interfaces.Urls;

public class users extends HttpServlet { 
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/forms/user/userEdit.jsp"; 
    public static final String CHAMP_LOGIN = "login";
    public static final String CHAMP_PASS = "mdp"; 
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_ERREUR_MESSAGE  = "message";
    public static final String ATT_RESULTAT = "resultat"; 
    public static final String URL_REDIRECTION = "http://localhost/GESTA";
    
    private UserDaoImpl     utilisateurDao; 
    private Map<String, String> erreurs      = new HashMap<String, String>(); 
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() throws ServletException {
        // R�cup�ration d'une instance de notre DAO Utilisateur 
        this.utilisateurDao = (UserDaoImpl) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUserDao();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		Urls url = new Urls(request);
		String param0 = "";
		try {
			param0 = url.getParam(0);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			param0 = "";
		}
		if(param0.equals("deconnexion")) {
			/* R�cup�ration et destruction de la session en cours */
	        HttpSession session = request.getSession();
	        session.invalidate();

	        /* Redirection vers le Site du Z�ro ! */
	        response.sendRedirect( URL_REDIRECTION );
		}
		String path = aiguiller(url);  
		if(path == Repertoires.page_error)
	        request.setAttribute( ATT_ERREURS,erreurs); 
		this.getServletContext().getRequestDispatcher(path ).forward( request, response );		
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Urls url = new Urls(request);
		String param0 = "";
		try {
			param0 = url.getParam(0);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			param0 = "";
		}
		if(param0.equals("connexion")){
			System.out.println("Formulaire recu");
			System.out.println(request.getParameter( CHAMP_LOGIN ));
			System.out.println(request.getParameter( CHAMP_PASS ));
			
			FormUserConnexion form = new FormUserConnexion(utilisateurDao,null);
			
			/* Traitement de la requ�te et r�cup�ration du bean en r�sultant */
	        User utilisateur = form.connecterUtilisateurSQL( request );

	        /* R�cup�ration de la session depuis la requ�te */
	        HttpSession session = request.getSession();

	        /**
	         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
	         * Utilisateur � la session, sinon suppression du bean de la session.
	         */
	        if ( form.getErreurs().isEmpty() ) {
	            session.setAttribute( ATT_SESSION_USER, utilisateur );
	        } else {
	            session.setAttribute( ATT_SESSION_USER, null );
	        }
	        /* Stockage du formulaire et du bean dans l'objet request */
	        request.setAttribute( ATT_FORM, form );
	        request.setAttribute( ATT_USER, utilisateur );
		}

		
        
		String path = aiguiller(url); 
		if(path == Repertoires.page_error)
	        request.setAttribute( ATT_ERREURS,erreurs);
		System.out.println("5"+erreurs);
		this.getServletContext().getRequestDispatcher(path ).forward( request, response );
	}
	
	protected String aiguiller(Urls url) {
		String path = "";
		String param0 = "";
		try {
			param0 = url.getParam(0);
		}catch(Exception e) { 
			setErreur(ATT_ERREUR_MESSAGE,e.getMessage());
			param0 = "";
		}
		if(param0.equals("connexion")){
			path = Repertoires.CTRL_USER+"/connexion.jsp";
		}
		else if(param0.equals("deconnexion")){
			path = Repertoires.CTRL_USER+"/deconnexion.jsp";
		}
		else if(param0.equals("restreint")){
			path = Repertoires.CTRL_USER+"/restreint.jsp";
		}
		else if(param0.equals("liste")){
			path = Repertoires.CTRL_USER+"/liste.jsp";
		}
		else if(param0.length()>0) { 
			setErreur(ATT_ERREUR_MESSAGE,"L'url sp�cifi� n'existe pas.");
			path = Repertoires.page_error;
		}
		else {  
			path = Repertoires.page_error;
		}
		return path;
	}
	public void setErreur(String attr, String value) {
		erreurs.put(attr, value);  
	} 
}

