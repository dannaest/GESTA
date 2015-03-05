package com.gesta.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gesta.beans.Options;
import com.gesta.beans.User;
import com.gesta.constantes.Constantes;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.OptionDao;
import com.gesta.dao.UserDao;
import com.gesta.dao.UserDaoImpl;

public class home extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String ATT_SESSION_USER         = "sessionUtilisateur";
	public static final String ATT_OPTIONS 		= "lesOptions";
    public static final String VUE_TEMPLATE         = "/WEB-INF/templates/template_home.jsp";  
    public static final String VUE_BODY              = "/WEB-INF/view/home/home_eleve.jsp";
    public static final String VUE_ENTETE      = "/WEB-INF/menu/main_menu.jsp";
    public static final String VUE_MENU_ACTION_ELEVE      = "/WEB-INF/menu/menu_action_accueil_eleve.jsp";
    public static final String VUE_MENU_ACTION_ENSEIGNANT      = "/WEB-INF/menu/menu_action_accueil_enseignant.jsp";
    public static final String VUE_MENU_ACTION_ADMINISTRATEUR      = "/WEB-INF/menu/menu_action_accueil_administrateur.jsp";
    public static final String VUE_FOOTER         = "/WEB-INF/footer/footer.jsp";  
    public static final String VUE_ATT_MENU_ACTIONS_TO_IMPORT =  "menuActionsToImport";
    public static final String VUE_ATT_BODY_TO_IMPORT =  "bodyToImport";
    public static final String VUE_ATT_ENTETE =  "entete";
    public static final String VUE_ATT_FOOTER =  "footer";
    
    private UserDaoImpl     utilisateurDao; 
    private OptionDao optionDao;
	 public void init() throws ServletException {
	        // R�cup�ration d'une instance de notre DAO Utilisateur 
	        this.utilisateurDao = (UserDaoImpl) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUserDao();
	        this.optionDao = (OptionDao)((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getOptionDao();
	    }
		

	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	    	/* R�cup�ration de la session depuis la requ�te */ 
	        HttpSession session = request.getSession();
	        User user = (User)session.getAttribute(ATT_SESSION_USER);
	        
	    	request.setAttribute( ATT_USER,user);  
	    	/*Choix du menu action en fonction du type d'utilisateur*/
	    	if(user.getAffiliation().equals(Constantes.AFFILIATION_ELEVE))
	    		request.setAttribute( VUE_ATT_MENU_ACTIONS_TO_IMPORT,VUE_MENU_ACTION_ELEVE);  
	    	else if(user.getAffiliation().equals(Constantes.AFFILIATION_ENSEIGNANT)) 
	    		request.setAttribute( VUE_ATT_MENU_ACTIONS_TO_IMPORT,VUE_MENU_ACTION_ENSEIGNANT);  
	    	else if(user.getAffiliation().equals(Constantes.AFFILIATION_ADMINISTRATEUR))
	    		request.setAttribute( VUE_ATT_MENU_ACTIONS_TO_IMPORT,VUE_MENU_ACTION_ADMINISTRATEUR);  
	    	
	    	request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);  
	    	request.setAttribute( VUE_ATT_BODY_TO_IMPORT,VUE_BODY);  
	    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER);  
	        this.getServletContext().getRequestDispatcher( VUE_TEMPLATE ).forward( request, response );
	    }
	    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	    	/* R�cup�ration de la session depuis la requ�te */ 
		    //doGet(request,response);
	    }
}
