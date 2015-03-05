package com.gesta.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gesta.beans.Stages;
import com.gesta.beans.User;
import com.gesta.constantes.Constantes;
import com.gesta.constantes.Repertoires;
import com.gesta.dao.ContactDao;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.UserDao;
import com.gesta.dao.UserDaoImpl;
import com.gesta.interfaces.Urls;

public class usersListe extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_USER         = "utilisateur"; //l'utilisateur connect�
    public static final String ATT_USERS         = "listeUtilisateurs";  //les utilisateurs qu'on r�cup�re de la bdd
    public static final String VUE              = "/WEB-INF/lists/listeStages.jsp";  
    public static final String ATT_RESULTAT = "resultat";  
    public static final String VUE_TEMPLATE         = "/WEB-INF/templates/template_liste.jsp";  
    public static final String VUE_BODY              = "/WEB-INF/lists/listeUsers.jsp";
    public static final String VUE_ENTETE      = "/WEB-INF/menu/main_menu.jsp";   
    public static final String VUE_FOOTER         = "/WEB-INF/footer/footer.jsp"; 
    public static final String VUE_MENU_ACTION     = "/WEB-INF/menu/menu_action_liste_users.jsp";  
    public static final String VUE_ATT_MENU_ACTIONS_TO_IMPORT =  "menuActionsToImport"; 
    public static final String VUE_ATT_BODY_TO_IMPORT =  "bodyToImport";
    public static final String VUE_ATT_ENTETE =  "entete";
    public static final String VUE_ATT_FOOTER =  "footer";
    
    private UserDaoImpl     utilisateurDao; 
    private ContactDao     contactDao; 
    private Map<String, String> erreurs      = new HashMap<String, String>(); 
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public void init() throws ServletException {
        // R�cup�ration d'une instance de notre DAO Utilisateur 
        utilisateurDao = (UserDaoImpl) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUserDao();
        contactDao = (ContactDao) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getContactDao();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    	HttpSession session = request.getSession();
        User user = (User)session.getAttribute(ATT_SESSION_USER);
        
        if(user.getAffiliation().equals(Constantes.AFFILIATION_ELEVE)) {
        	request.setAttribute("erreur_message", "Vous n'avez pas les droits requis pour visiter cette page.");
        	this.getServletContext().getRequestDispatcher("/WEB-INF/view/errors/error.jsp").forward( request, response );
        	return; 
        }
        
        //Récupération du paramètre
		Urls url = new Urls(request); 
        String param = ""; 
        try {  
        	param = url.getParam(0); 
        }catch(Exception e) {  }
        
        //Récupération des utilisateurs demandés
        String[] vide = {};
        List<User> listeUtilisateurs = null; 
        if(param.equals("meseleves")) {
        	listeUtilisateurs = utilisateurDao.listerEleves(user); 
        }
        else { 
        	listeUtilisateurs = utilisateurDao.lister(vide, vide);
        }
  

    	request.setAttribute( ATT_USER,user);       	
    	request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);  
    	request.setAttribute( VUE_ATT_MENU_ACTIONS_TO_IMPORT,VUE_MENU_ACTION); 
    	request.setAttribute( VUE_ATT_BODY_TO_IMPORT,VUE_BODY);  
    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER);  
    	request.setAttribute( ATT_USERS,listeUtilisateurs); 
        this.getServletContext().getRequestDispatcher( VUE_TEMPLATE ).forward( request, response );
	}
}
