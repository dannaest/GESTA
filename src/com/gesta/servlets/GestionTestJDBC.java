package com.gesta.servlets;

import java.io.IOException;   
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gesta.bdd.TestJDBC;
import com.gesta.beans.ALeDroit;
import com.gesta.beans.Options;
import com.gesta.beans.ParseXML;
import com.gesta.beans.Statut;
import com.gesta.dao.ALeDroitDao;
import com.gesta.dao.OptionDao;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.StatutDao;
import com.gesta.forms.FormALeDroit;
import com.gesta.forms.FormOption;
import com.gesta.forms.FormStatut;

public class GestionTestJDBC extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_OPTION = "option";
	public static final String ATT_OPTIONS = "lesOptions";
	public static final String ATT_ACTIONS = "lesActions";
	public static final String ATT_STATUTS = "lesStatuts";
    //public static final String VUE          = "/WEB-INF/test_jdbc.jsp";
    //public static final String VUE          = "/WEB-INF/lists/listeOptions.jsp";
    //public static final String VUE          = "/WEB-INF/lists/listeActionsUtilisateurs.jsp";
	//public static final String VUE          = "/WEB-INF/lists/listeStatuts.jsp";
    //public static final String VUE          = "/WEB-INF/forms/option/optionEdit.jsp";
    public static final String VUE          = "/WEB-INF/forms/aledroit/aledroitEdit.jsp";
	//public static final String VUE          = "/WEB-INF/forms/statut/statutEdit.jsp";
    public static final String ATT_FORM         = "form";

    private OptionDao     optionDao;  
    private StatutDao     statutDao;  
    private ALeDroitDao     aledroitDao;   
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    
    public void init() throws ServletException {
        // Récupération d'une instance de notre DAO Utilisateur 
    	optionDao = (OptionDao) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getOptionDao();
    	aledroitDao = (ALeDroitDao) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getALeDroitDao();
    	statutDao = (StatutDao) ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getStatutDao();
    }
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Initialisation de l'objet Java et récupération des messages */
        //TestJDBC test = new TestJDBC();
        //List<String> messages = test.executerTests( request );

        /* Enregistrement de la liste des messages dans l'objet requête */
        //request.setAttribute( ATT_MESSAGES, messages );

    	
    	//TEst de lecture des options
    	/*String[] vide = {}; //tableau vide
    	List<Options> listeOptions = optionDao.lister( vide,vide); 
    	request.setAttribute( ATT_OPTIONS,listeOptions); */ 
    	
    	//Map<String,ServletRegistration> map = (Map<String, ServletRegistration>) this.getServletContext().getServletRegistrations();
    	
    	
    	
    	//_Utilitaire util = new _Utilitaire();
    	//List<String> lesServlets = util.getServletsList(); 
    	Collection<ServletRegistration> lesServlets = _Utilitaire.getServletsCollection(this);
    	//TEst de lecture des actions
    	String[] vide = {}; //tableau vide
    	List<ALeDroit> listeActions = aledroitDao.lister(vide,vide); 
    	request.setAttribute( ATT_ACTIONS,listeActions);  
    	request.setAttribute( "lesServlets",lesServlets);  
    	//request.setAttribute( ATT_SERVLETS,this.getServletContext().get .getServletNames());  

    	//TEst de lecture des statuts
    	/*String[] vide = {}; //tableau vide
    	List<Statut> listeStatuts = statutDao.lister(vide,vide); 
    	request.setAttribute( ATT_STATUTS,listeStatuts);*/
    	
    	
        /* Transmission vers la page en charge de l'affichage des résultats */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	//Test d'enregistrement des options
    	/*FormOption form = new FormOption(optionDao);
    	Options option = form.ajouterOption(request);
    	request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_OPTION, option );*/  
    	
    	
    	
    	//Test d'enregistrement des actions
    	_Utilitaire util = new _Utilitaire();
    	List<String> lesServlets = util.getServletsList(); 
    	String[] vide = {}; //tableau vide
    	List<ALeDroit> listeActions = aledroitDao.lister(vide,vide); 
    	
    	FormALeDroit form = new FormALeDroit(aledroitDao);
    	ALeDroit action = new ALeDroit();	
    	action = form.ajouterAction(request);
    	request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_OPTION, action );
    	request.setAttribute( "lesServlets",lesServlets); 
    	
    	//Test d'enregistrement des statuts
    	/*FormStatut form = new FormStatut(statutDao);
    	Statut statut = new Statut(null, null,0);	
    	statut = form.ajouterStatut(request);
    	request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_STATUTS, statut );
        */

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}
