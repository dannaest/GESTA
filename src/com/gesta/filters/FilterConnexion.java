package com.gesta.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FilterConnexion
 */
 
public class FilterConnexion implements Filter {
    public static final String ATT_SESSION_USER         = "sessionUtilisateur";
    public static final String ATT_SESSION_CALLER         = "sessionCaller"; // la page demand�e avant le redirection pour connexion
    //public static final String VUE              		= "/WEB-INF/view/users/accesRestreint.jsp";
    public static final String VUE              		= "/usersConnexion";

    /**
     * Default constructor. 
     */
    public FilterConnexion() {
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
		 /* Cast des objets request et response */
		System.out.println("1er filtre lancé");
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
        
        /*On rajoute la page demand�e � la session*/
        String caller = request.getServletPath();
        session.setAttribute(ATT_SESSION_CALLER, caller);
        

        /*
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connect�.
         */
        if ( !caller.equals(VUE) && session.getAttribute( ATT_SESSION_USER ) == null ) {
            /* Redirection vers la page publique */ 
        	response.sendRedirect( request.getContextPath() + VUE ); 
            return ;
        }  
        /* Affichage de la page restreinte */
        chain.doFilter( request, response ); 
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
