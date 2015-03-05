package com.gesta.filters;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gesta.beans.ALeDroit;
import com.gesta.beans.ParseXML;
import com.gesta.beans.Statut;
import com.gesta.beans.Statut_Action;
import com.gesta.beans.User;
import com.gesta.dao.ALeDroitDao;
import com.gesta.dao.DAO;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.StatutDao;
import com.gesta.dao.Statut_ActionDaoImpl;
import com.gesta.dao.UserDao;
import com.gesta.dao.UserDaoImpl;

/**
 * Servlet Filter implementation class FilterAccesRights
 * 
 * Ce filtre v�rifie dans les droits d'un utilisateur que celui-ci peut bien acc�der � la page demand�e.
 * @Author Nacder
 *  
 */
@WebFilter("/FilterAccesRights")
public class FilterAccesRights implements Filter {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_SESSION_USER         = "sessionUtilisateur";
    public static final String VUE_CONNEXION         = "/usersConnexion";
    public static final String VUE_RESTRICTION         = "/pageRestreinte"; 
    public static final String VUE_ACCES_RESTREINT     = "/accesRestreint.jsp";
    private UserDaoImpl     utilisateurDao; 
    private StatutDao 	 statutDao;
    private ALeDroitDao 	 aledroitDao;
    private Statut_ActionDaoImpl statut_actionDao;

    /**
     * Default constructor. 
     */
    public FilterAccesRights() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// 
		System.out.println("2e filtre lancé");
		/* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        /* Non-filtrage des ressources statiques */
        String chemin = request.getRequestURI().substring( request.getContextPath().length() ); 
        if ( chemin.startsWith( "/webroot" ) ) {
            chain.doFilter( request, response );
            return;
        }

        /* R�cup�ration de la session depuis la requ�te */
        HttpSession session = request.getSession();
        
        /* on sait que l'utilisateur existe puisqu'on a pass� le 1er filtre */
        User utilisateur = (User)session.getAttribute( ATT_SESSION_USER ); 
        
        /* Maintenant on v�rifie que l'entr�e Servlet-DoMethod-view (dans la table ALeDroit)
         * et le statut de l'utilsiateur (dans la table Statuts)  ont une correspondance dans la table users_statuts */
        //Entr�e Servlet-doMethod (dans la table ALeDroit) 
        ParseXML parsing = new ParseXML();
    	String servletName = parsing.findServletNameFromServletPath(request.getServletPath(),request.getServletContext()), 
        		doMethod = request.getMethod().toLowerCase();  
        String[] fields = {"ald_servlet","ald_do"};
        String[] values = {servletName,doMethod}; 
        /**/
        
        List<ALeDroit> ald = aledroitDao.lister(fields, values);  
        
        // Si l'acc�s � cette servlet-methode n'est pas d�fini dans la base de donn�es on redirige l'utilisateur
        if(ald.size()==0) {
            response.sendRedirect( request.getContextPath() + VUE_ACCES_RESTREINT); 
            return ;
        }
        
        // on r�cup�re les droits de l'utilisateur et on les range dans l'ordre croissant
        List<Statut> statuts = utilisateur.getStatuts();
        Collections.sort(statuts); 
        
        // Si l'utilisateur n'a aucun droit d�fini dans la base de donn�es, on redirige
        if(statuts.size()==0) {
            response.sendRedirect( request.getContextPath() + VUE_ACCES_RESTREINT); 
            return ;
        }
        
        fields = new String[] {"id_statut","id_aledroit"};
        values = new String[] {statuts.get(0).getId().toString(),ald.get(0).getId().toString()}; 
        List<Statut_Action> listStatutActions = statut_actionDao.lister(fields, values); 
        
        
        /**Test des nouvelles fonctions pour le DAO statuts_actions*/
        //Statut_Action sa = new Statut_Action(new Long(4),new Long(4));//listStatutActions.get(0);
        //sa.setIdAledroit(new Long(4)); // pour le 2e statut (el�ve) de l'utilisateur actuel, on rajoute la possibilit� de consulter la page home
        //sa.setIdStatut(new Long(4));
        //statut_actionDao.creer(sa);
        //statut_actionDao.supprimer(sa);
        //statut_actionDao.mettreAJour(sa, new Statut_Action(new Long(1),new Long(6)));
        
        
        // Si la combinaison n'est pas d�finie on redirige
        if(listStatutActions.size()==0) {
        	response.sendRedirect( request.getContextPath() + VUE_ACCES_RESTREINT); 
        	return ;
        }
        	
        	
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// R�cup�ration d'une instance de notre DAO Utilisateur  
        this.utilisateurDao = (UserDaoImpl) ( (DAOFactory) fConfig.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUserDao();
        this.statutDao = (StatutDao) ( (DAOFactory) fConfig.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getStatutDao();
        this.aledroitDao = (ALeDroitDao) ( (DAOFactory) fConfig.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getALeDroitDao();   
        this.statut_actionDao = (Statut_ActionDaoImpl)((DAOFactory) fConfig.getServletContext().getAttribute(CONF_DAO_FACTORY)).getStatut_ActionDao();
	}

}
