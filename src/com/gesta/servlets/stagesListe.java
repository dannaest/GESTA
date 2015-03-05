package com.gesta.servlets;

import java.io.IOException;







import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gesta.beans.Stages;
import com.gesta.beans.User;
import com.gesta.beans.Users_stages;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.StagesDaoImpl;
import com.gesta.dao.UserDao;
import com.gesta.dao.UserDaoImpl;
import com.gesta.dao.Users_stagesDaoImpl;
import com.gesta.interfaces.Urls;

/**
 * Servlet implementation class stagesListe
 */

public class stagesListe extends HttpServlet {
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
       
    private StagesDaoImpl     stageDao;  
    private Users_stagesDaoImpl     users_stageDao;  
    private UserDaoImpl     userDao;  

    public void init() throws ServletException  { 
        this.stageDao = (StagesDaoImpl)((DAOFactory) this.getServletContext().getAttribute(CONF_DAO_FACTORY)).getStagesDao();
        this.users_stageDao = (Users_stagesDaoImpl)((DAOFactory) this.getServletContext().getAttribute(CONF_DAO_FACTORY)).getUsers_stagesDao();
        userDao = (UserDaoImpl) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUserDao();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* R�cup�ration de la session depuis la requ�te */ 
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(ATT_SESSION_USER);
        
        Urls url = new Urls(request); 
        String param = "";
        try {  
        	param = url.getParam(0);
        	request.setAttribute("voirStagesDe", param);
        }catch(Exception e) {
        	
        }
        
        List<Stages> lesStages = new ArrayList<Stages>();  
        if(param.equals("messtages")) {
        	lesStages = stageDao.lister(user); 
        }
        else if(param.equals("sesstages")) {
        	try {  
        		String [] fields = null, values = null;
            	param = url.getParam(1);
            	request.setAttribute("voirStagesDeid", param); 
            	
            	List<User> listeUtilisateurs = userDao.lister(new String[] {"id_user"}, new String[] {param});
            	if(listeUtilisateurs.size()>0) {
            		
            		/*/tri des stages
                	if(url.getParameters().length >= 3) { 
                		String tri = url.getParam(2);
                		Hashtable<String, String> hash = new Hashtable<String,String>();
                		hash.put("tables", "stage")
						userDao.customised(hash);
                	}
                	//pas de tri
                	else*/
                		lesStages = stageDao.lister(listeUtilisateurs.get(0)); 
            	} 
            }catch(Exception e) { }
        	
        }
        else {
        	String[] vide = {};
            lesStages = stageDao.lister(new String[] {"id_etatvalidation"}, new String[] {"3"});
        }
         
        
        
    	request.setAttribute( ATT_USER,user);       	
    	request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);  
    	request.setAttribute( VUE_ATT_MENU_ACTIONS_TO_IMPORT,VUE_MENU_ACTION); 
    	request.setAttribute( VUE_ATT_BODY_TO_IMPORT,VUE_BODY);  
    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER);  
    	request.setAttribute( "lesStages",lesStages);  
        this.getServletContext().getRequestDispatcher( VUE_TEMPLATE ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
