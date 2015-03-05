package com.gesta.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gesta.beans.Stages;
import com.gesta.beans.User;
import com.gesta.beans.Users_stages;
import com.gesta.constantes.Constantes;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.StagesDaoImpl;
import com.gesta.dao.Users_stagesDaoImpl; 
import com.gesta.interfaces.Urls;

public class stagesView extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_USER         = "utilisateur"; //l'utilisateur connect�  
    public static final String VUE_TEMPLATE         = "/WEB-INF/templates/template_liste.jsp";  
    public static final String VUE_BODY              = "/WEB-INF/lists/listeStages.jsp";
    public static final String VUE_ENTETE      = "/WEB-INF/menu/main_menu.jsp";   
    public static final String VUE_FOOTER         = "/WEB-INF/footer/footer.jsp"; 
    public static final String VUE_MENU_ACTION     = "/WEB-INF/menu/menu_action_liste_stages.jsp";  
    public static final String VUE_ATT_MENU_ACTIONS_TO_IMPORT =  "menuActionsToImport"; 
    public static final String VUE_ATT_BODY_TO_IMPORT =  "bodyToImport";
    public static final String VUE_ATT_ENTETE =  "entete";
    public static final String VUE_ATT_FOOTER =  "footer";
    public static final String VUE_ATT_FORM_TITLE =  "formTitle";

    private StagesDaoImpl     stageDao;  
    private Users_stagesDaoImpl     users_stageDao;  
    
    public void init() throws ServletException  { 
        this.stageDao = (StagesDaoImpl)((DAOFactory) this.getServletContext().getAttribute(CONF_DAO_FACTORY)).getStagesDao();
        this.users_stageDao = (Users_stagesDaoImpl)((DAOFactory) this.getServletContext().getAttribute(CONF_DAO_FACTORY)).getUsers_stagesDao();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        User user = (User)session.getAttribute(ATT_SESSION_USER);
        
        Urls url = new Urls(request);
        String id_stage =""; 
        try{
        	id_stage = url.parametersUri[4];
        }catch(Exception e) {
        	id_stage = "";
        }
        List<Users_stages> us = users_stageDao.lister(new String []{"id_user","id_stage"}, new String []{user.getId().toString(),id_stage});

        if(us.size()>0 || user.getAffiliation() != Constantes.AFFILIATION_ELEVE) {
	        List<Stages> stages = stageDao.lister(new String[] {"id_stage"}, new String[] {id_stage});
	        Stages stage = null;
	        if(stages.size()>0) {
	        	stage = stages.get(0);   
	        	request.setAttribute( "stage",stage);  
	        	request.setAttribute( ATT_USER,user);
		    	
		    	request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);        
		    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER);  
		    	
	    		request.setAttribute( "menuChoice","menu_action_stages");  
	    		request.setAttribute( VUE_ATT_FORM_TITLE,"Résumé de la saisie"); 
	    		request.setAttribute( VUE_ATT_MENU_ACTIONS_TO_IMPORT,"/WEB-INF/menu/menu_action_stages.jsp");  
	    		request.setAttribute( VUE_ATT_BODY_TO_IMPORT,"/WEB-INF/view/stage/stageView.jsp");  
	    		this.getServletContext().getRequestDispatcher( VUE_TEMPLATE ).forward( request, response );
	        }
	        else
	        	this.getServletContext().getRequestDispatcher("/WEB-INF/view/errors/error.jsp").forward( request, response );
                
	    	
    	}
        else
        	this.getServletContext().getRequestDispatcher("/WEB-INF/view/errors/error.jsp").forward( request, response );
    
    	
    }
}
