package com.gesta.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gesta.beans.Etatvalidation;
import com.gesta.beans.Stages;
import com.gesta.beans.User;
import com.gesta.beans.Users_stages;
import com.gesta.constantes.Constantes;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.StagesDaoImpl;
import com.gesta.dao.Users_stagesDaoImpl;
import com.gesta.forms.FormStage;
import com.gesta.interfaces.Urls;

/**
 * Servlet implementation class stagesEdit */ 
public class stagesEdit extends HttpServlet {
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
    private Users_stagesDaoImpl     users_stageDao; 

	/**
	 * @see Servlet#init()
	 */
	public void init() throws ServletException {
		DAOFactory dao = (DAOFactory) this.getServletContext().getAttribute(CONF_DAO_FACTORY);
		this.stageDao = (StagesDaoImpl)dao.getStagesDao();
		this.users_stageDao = (Users_stagesDaoImpl)((DAOFactory) this.getServletContext().getAttribute(CONF_DAO_FACTORY)).getUsers_stagesDao();
		   
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        User user = (User)session.getAttribute(ATT_SESSION_USER);
        
        // on vérifie qu'un id de stage a été donné
        Urls url = new Urls(request);
        String id_stage =""; 
        try{
        	id_stage = url.parametersUri[4];
        }catch(Exception e) { 
        	request.setAttribute("erreur_message", "L'url est incorrecte.");
        	this.getServletContext().getRequestDispatcher("/WEB-INF/view/errors/error.jsp").forward( request, response );
        	return;
        }
        
        //On tente de récupérer le stage en question
        List<Stages> stages = stageDao.lister(new String[] {"id_stage"}, new String[] {id_stage});
        if(stages.size()==0) {
        	request.setAttribute("erreur_message", "Le stage spécifié n'existe pas.");
        	this.getServletContext().getRequestDispatcher("/WEB-INF/view/errors/error.jsp").forward( request, response ); 
        	return;
        }
        
        //on s'assure que l'utilisateur a le droit d'éditer ce stage
        List<Users_stages> us = users_stageDao.lister(new String []{"id_user","id_stage"}, new String []{user.getId().toString(),id_stage});
        if(!(us.size()>0 || user.getAffiliation()!= Constantes.AFFILIATION_ELEVE)) { 
        	request.setAttribute("erreur_message", "Vous n'avez pas les droits requis pour visiter cette page.");
        	this.getServletContext().getRequestDispatcher("/WEB-INF/view/errors/error.jsp").forward( request, response );
        	return; 
        }
        
        //Gestion de l'état de validation du stage
        List<Etatvalidation> listeDesEtatsStage = (List<Etatvalidation>)getServletContext().getAttribute("listeDesEtatsStage");
        if(request.getParameter("valideSaisi") != null) {
        	Stages old_stage = stages.get(0);   
        	Stages stage = old_stage;
        	String nouvelEtat = request.getParameter("valideSaisi");
        	
        	//On ne modifie que si les états sont différents
        	if(!nouvelEtat.equals(old_stage.getId_etatvalidation().toString())) {
	        	if(user.getAffiliation().equals(Constantes.AFFILIATION_ELEVE)) { // eleve
	        		stage.setId_etatvalidation(listeDesEtatsStage.get(1).getId_etatvalidation());
	        	}
	        	else { //enseignant ou autre 
	        		if(nouvelEtat.equals(listeDesEtatsStage.get(0).getId_etatvalidation().toString()))  
	        			stage.setId_etatvalidation(listeDesEtatsStage.get(0).getId_etatvalidation());//
	        		else if(nouvelEtat.equals(listeDesEtatsStage.get(2).getId_etatvalidation().toString())) 
	    				stage.setId_etatvalidation(listeDesEtatsStage.get(2).getId_etatvalidation());//
	        	} 
	        	
	        	stageDao.mettreAJour(old_stage, stage); 
        	}
        }
        
        Stages stage = stages.get(0);
        // Si l'état de validation est 'public', l'édition devient impossible
        if(stage.getId_etatvalidation().toString().equals(listeDesEtatsStage.get(2).getId_etatvalidation().toString())) {
        	request.setAttribute("erreur_message", "Vous n'avez pas les droits requis pour visiter cette page.");
        	response.sendRedirect(getServletContext().getContextPath()+"/stages/view/"+stage.getId_stage());
        	return;
        }
        
        //Edition par le formulaire
          
           
    	request.setAttribute( "stage",stage); 
    	/*On sauvegarde le stage dans la session pour le récupérer au cas où venait à le modifier.*/
    	session.setAttribute("old_stage", stage);
    	FormStage form = new FormStage(stageDao,request,user); 
        request.setAttribute( "form",form);  
        
    	request.setAttribute( ATT_USER,user);
    	
    	request.setAttribute( "form_localisation",FORM_LOCALISATION);  
    	request.setAttribute( "form_contact",FORM_CONTACT); 
    	
    	request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);      
		request.setAttribute( VUE_ATT_MENU_ACTIONS_TO_IMPORT,"/WEB-INF/menu/menu_action_stages.jsp");  
		request.setAttribute( VUE_ATT_BODY_TO_IMPORT,"/WEB-INF/forms/stage/stageEdit.jsp");    
    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER);  
    	
		request.setAttribute( "menuChoice","menu_action_stages");   
    	request.setAttribute( "formAction",getServletContext().getContextPath()+request.getServletPath()+"/"+stage.getId_stage());  
		request.setAttribute( VUE_ATT_FORM_TITLE,"Edition du stage"); 
	 
    	this.getServletContext().getRequestDispatcher( VUE_TEMPLATE ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        User user = (User)session.getAttribute(ATT_SESSION_USER);
    	request.setAttribute( ATT_USER,user);
    	
    	Urls url = new Urls(request);
        String id_stage =""; 
        try{
        	id_stage = url.parametersUri[4];
        }catch(Exception e) {
        	id_stage = "";
        }
        
        request.setAttribute( VUE_ATT_ENTETE,VUE_ENTETE);   
    	request.setAttribute( VUE_ATT_BODY_TO_IMPORT,"/WEB-INF/forms/stage/stageEdit.jsp");    
    	request.setAttribute( "formAction",getServletContext().getContextPath()+request.getServletPath()+"/"+id_stage);  
		request.setAttribute( VUE_ATT_FORM_TITLE,"Edition d'un stage");  
    	request.setAttribute( VUE_ATT_FOOTER,VUE_FOOTER); 
        
        List<Users_stages> us = users_stageDao.lister(new String []{"id_user","id_stage"}, new String []{user.getId().toString(),id_stage});
        if(us.size()>0 || user.getAffiliation()!= Constantes.AFFILIATION_ELEVE) {
    	    
	        FormStage form = new FormStage(stageDao,request,user);        
	        Stages stage = new Stages(); 
			stage = form.editStage(request, (Stages)session.getAttribute("old_stage"));  
	        request.setAttribute( "stage",stage); 
	        request.setAttribute( "form",form);
	        
	        request.setAttribute( "form_localisation",FORM_LOCALISATION);  
	    	request.setAttribute( "form_contact",FORM_CONTACT);  
	    	
	    	
	    	if(form.getErreurs().isEmpty()){
	    		request.setAttribute( "menuChoice","menu_action_stages");  
	    		request.setAttribute( VUE_ATT_FORM_TITLE,"Résumé de la saisie"); 
	    		request.setAttribute( VUE_ATT_MENU_ACTIONS_TO_IMPORT,"/WEB-INF/menu/menu_action_stages.jsp");  
	    		request.setAttribute( VUE_ATT_BODY_TO_IMPORT,"/WEB-INF/view/stage/stageView.jsp");  
	    	}
        }
    	this.getServletContext().getRequestDispatcher( VUE_TEMPLATE ).forward( request, response );
	}

}
