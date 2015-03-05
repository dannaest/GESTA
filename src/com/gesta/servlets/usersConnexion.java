package com.gesta.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gesta.beans.User;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.StatutDao;
import com.gesta.dao.UserDao;
import com.gesta.dao.UserDaoImpl;
import com.gesta.forms.FormUserConnexion;
import com.gesta.interfaces.Urls;

public class usersConnexion extends HttpServlet {  
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_SESSION_USER         = "sessionUtilisateur";
    public static final String ATT_SESSION_CALLER         = "sessionCaller"; // la page demandée avant le redirection pour connexion
    public static final String ATT_FORM         = "form";
    public static final String ATT_LOGIN         = "login";
    public static final String VUE              = "/WEB-INF/forms/user/connexion.jsp";
    public static final String REDIRECION_PATH              = "/home";
    public static final String CHAMP_LOGIN = "login";
    public static final String CHAMP_PASS = "mdp"; 
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";  
    public static final String VUE_ENTETE      = "/WEB-INF/menu/main_menu.jsp";   
    public static final String VUE_FOOTER         = "/WEB-INF/footer/footer.jsp"; 
    public static final String VUE_ATT_ENTETE =  "entete";
    public static final String VUE_ATT_FOOTER =  "footer"; 
    
    private UserDaoImpl     utilisateurDao; 
    private StatutDao 	 statutDao;

    public void init() throws ServletException {
        // Récupération d'une instance de notre DAO Utilisateur 
        this.utilisateurDao = (UserDaoImpl) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUserDao();
        this.statutDao = (StatutDao) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getStatutDao();
        
    }
    

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
    	request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);        
    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER); 
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	FormUserConnexion form = new FormUserConnexion(utilisateurDao,statutDao); 
        /* Traitement de la requête et récupération du bean en résultant */ 
    	//User user = form.connecterUtilisateurLDAP( request ); 	        
    	User user = form.connecterUtilisateurSQL( request ); 	        
        
    	/* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form ); 
        request.setAttribute( ATT_LOGIN, request.getParameter("login"));
    	request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);        
    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER); 
        
        /* Récupération de la session depuis la requête */ 
        HttpSession session = request.getSession();

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur � la session, sinon suppression du bean de la session.
         */ 
        String path = VUE;
        if ( form.getErreurs().isEmpty()) {  
            session.setAttribute( ATT_SESSION_USER, user );
            
            String caller = "";
            try {
            	caller = session.getAttribute(ATT_SESSION_CALLER).toString();
            }catch(IllegalStateException|NullPointerException  e) { } 
            
            if(caller.length()==0 || caller.equals("/usersConnexion"))
            	path = REDIRECION_PATH;
            else 
            	path = caller;
            response.sendRedirect(Urls.LocalPath(path));
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
            this.getServletContext().getRequestDispatcher( path ).forward( request, response );
        }
        
        
        //
    }
}
