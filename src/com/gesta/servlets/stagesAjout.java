package com.gesta.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gesta.beans.Stages;
import com.gesta.beans.Tailleentreprise;
import com.gesta.beans.Typeconvention;
import com.gesta.beans.Typeentreprise;
import com.gesta.beans.Typestage;
import com.gesta.beans.User;
import com.gesta.dao.*;
import com.gesta.forms.FormStage;
import com.gesta.forms.FormValidationException;

public class stagesAjout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_USER         = "utilisateur"; //l'utilisateur connect�  
    public static final String VUE_TEMPLATE         = "/WEB-INF/templates/template_form.jsp";  
    public static final String VUE_BODY              = "/WEB-INF/forms/stage/stageEdit.jsp"; 
    public static final String VUE_ENTETE      = "/WEB-INF/menu/main_menu.jsp";   
    public static final String VUE_FOOTER         = "/WEB-INF/footer/footer.jsp";   
    public static final String VUE_ATT_MENU_ACTIONS_TO_IMPORT =  "menuActionsToImport"; 
    public static final String VUE_ATT_BODY_TO_IMPORT =  "bodyToImport";
    public static final String VUE_ATT_FORM_ACTION =  "formAction";
    public static final String VUE_ATT_FORM_TITLE =  "formTitle";
    public static final String VUE_ATT_ENTETE =  "entete";
    public static final String VUE_ATT_FOOTER =  "footer";
    public static final String FORM_CONTACT =  "/WEB-INF/forms/contact/contactEdit.jsp";
    public static final String FORM_LOCALISATION =  "/WEB-INF/forms/localisation/localisationEdit.jsp";
	
    private StagesDaoImpl stageDao = null;
    
    public void init() throws ServletException  { 
        this.stageDao = (StagesDaoImpl)((DAOFactory) this.getServletContext().getAttribute(CONF_DAO_FACTORY)).getStagesDao();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* R�cup�ration de la session depuis la requ�te */ 
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(ATT_SESSION_USER);
        
       
         
        
    	request.setAttribute( ATT_USER,user);       	
    	request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);   
    	request.setAttribute( VUE_ATT_BODY_TO_IMPORT,VUE_BODY);  
    	request.setAttribute( VUE_ATT_FORM_ACTION,getServletContext().getContextPath()+request.getServletPath());  
    	request.setAttribute( VUE_ATT_FORM_TITLE,"Ajout d'un stage");  
    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER);  
    	
    	request.setAttribute( "form_localisation",FORM_LOCALISATION);  
    	request.setAttribute( "form_contact",FORM_CONTACT);  
    	
        this.getServletContext().getRequestDispatcher( VUE_TEMPLATE ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* R�cup�ration de la session depuis la requ�te */ 
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(ATT_SESSION_USER);
    	request.setAttribute( ATT_USER,user);
        
        FormStage form = new FormStage(stageDao,request,user);        
        Stages stage = new Stages(); 
		stage = form.ajoutStage(request);  
        request.setAttribute( "stage",stage); 
        request.setAttribute( "form",form);      
    	
    	request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);   
    	request.setAttribute( VUE_ATT_BODY_TO_IMPORT,VUE_BODY);  
    	request.setAttribute( VUE_ATT_FORM_ACTION,getServletContext().getContextPath()+request.getServletPath());  
    	request.setAttribute( VUE_ATT_FORM_TITLE,"Ajout d'un stage");  
    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER);  
    	
    	request.setAttribute( "form_localisation",FORM_LOCALISATION);  
    	request.setAttribute( "form_contact",FORM_CONTACT);  
    	
    	if(form.getErreurs().isEmpty()){
    		request.setAttribute( "menuChoice","menu_action_stages");  
    		request.setAttribute( VUE_ATT_FORM_TITLE,"Résumé de la saisie"); 
    		request.setAttribute( VUE_ATT_MENU_ACTIONS_TO_IMPORT,"/WEB-INF/menu/menu_action_stages.jsp");  
    		request.setAttribute( VUE_ATT_BODY_TO_IMPORT,"/WEB-INF/view/stage/stageView.jsp");  
    	}
    	this.getServletContext().getRequestDispatcher( VUE_TEMPLATE ).forward( request, response );
	}
}
