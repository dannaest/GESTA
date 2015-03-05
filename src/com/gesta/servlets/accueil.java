package com.gesta.servlets;
 
import java.io.IOException; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gesta.interfaces.Urls;

public class accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ATT_URL_CONNEXION = "url_connexion"; 
    public static final String VUE_TEMPLATE         = "/WEB-INF/templates/template_main.jsp";  
    public static final String VUE_BODY              = "/WEB-INF/lists/listeStages.jsp";
    public static final String VUE_ENTETE      = "/WEB-INF/menu/main_menu.jsp";   
    public static final String VUE_FOOTER         = "/WEB-INF/footer/footer.jsp"; 
    public static final String VUE_MENU_ACTION     = "/WEB-INF/menu/menu_action_liste_stages.jsp";  
    public static final String VUE_ATT_MENU_ACTIONS_TO_IMPORT =  "menuActionsToImport"; 
    public static final String VUE_ATT_BODY_TO_IMPORT =  "bodyToImport";
    public static final String VUE_ATT_ENTETE =  "entete";
    public static final String VUE_ATT_FOOTER =  "footer";
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute( ATT_URL_CONNEXION, Urls.LocalPath("/usersConnexion") ); 
		request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);        
    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER);  
    	      
		request.setAttribute( VUE_ATT_BODY_TO_IMPORT,"/WEB-INF/accueil.jsp");  
		this.getServletContext().getRequestDispatcher( VUE_TEMPLATE ).forward( request, response );
	}
}
