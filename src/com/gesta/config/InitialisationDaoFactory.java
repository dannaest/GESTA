package com.gesta.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gesta.beans.Etatvalidation;
import com.gesta.beans.Pays;
import com.gesta.beans.Tailleentreprise;
import com.gesta.beans.Typeconvention;
import com.gesta.beans.Typeentreprise;
import com.gesta.beans.Typestage;
import com.gesta.constantes.Constantes;
import com.gesta.dao.DAOConfigurationException;
import com.gesta.dao.DAOException;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.EtatvalidationDaoImpl;
import com.gesta.dao.PaysDaoImpl;
import com.gesta.dao.TailleentrepriseDaoImpl;
import com.gesta.dao.TypeconventionDaoImpl;
import com.gesta.dao.TypeentrepriseDaoImpl;
import com.gesta.dao.TypestageDaoImpl;

public class InitialisationDaoFactory implements ServletContextListener {
	private static final String ATT_DAO_FACTORY = "daofactory";

	private DAOFactory          daoFactory;
    private TypeentrepriseDaoImpl typeEntDao = null;
    private TypestageDaoImpl typeStageDao = null;
    private TypeconventionDaoImpl typeConventionDao = null;
    private TailleentrepriseDaoImpl tailleentrepriseDao = null;
    private PaysDaoImpl paysDao = null;
    private EtatvalidationDaoImpl etatvalidationDao = null;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		/* R�cup�ration du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();
        /* Instanciation de notre DAOFactory */
         
        try { 
        	this.daoFactory = (DAOFactory)DAOFactory.getInstance(); 
        }catch(DAOConfigurationException e) {
        	throw new DAOConfigurationException( e );
        }
        /* Enregistrement dans un attribut ayant pour port�e toute l'application */
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
        servletContext.setAttribute("URL", servletContext.getContextPath());
        servletContext.setAttribute("Constantes", new Constantes());
        
        System.out.println(servletContext.getContextPath());
        
        servletContext.setAttribute("affiliation_eleve", Constantes.AFFILIATION_ELEVE);
        servletContext.setAttribute("affiliation_enseignant", Constantes.AFFILIATION_ENSEIGNANT); 
        
        /*Récupération des listes*/
        this.typeEntDao = (TypeentrepriseDaoImpl)this.daoFactory.getTypeentrepriseDao();
        this.typeStageDao = (TypestageDaoImpl)this.daoFactory.getTypestageDao();
        this.typeConventionDao = (TypeconventionDaoImpl)this.daoFactory.getTypeconventionDao();
        this.tailleentrepriseDao = (TailleentrepriseDaoImpl)this.daoFactory.getTailleentrepriseDao();
        this.paysDao = (PaysDaoImpl)this.daoFactory.getPaysDao();
        this.etatvalidationDao = (EtatvalidationDaoImpl)this.daoFactory.getEtatvalidationDao();
        
        String[] vide = {};
        List<Typeentreprise> listeTypeEntreprise = typeEntDao.lister(vide, vide); 
        List<Typestage> listeTypeStage = typeStageDao.lister(vide, vide);
        List<Typeconvention> listeTypeConvention = typeConventionDao.lister(vide, vide);
        List<Tailleentreprise> listeTailleEntreprise = tailleentrepriseDao.lister(vide, vide);
        List<Pays> listeDesPays = paysDao.lister(vide, vide);
        List<Etatvalidation> listeDesEtatsStage = etatvalidationDao.lister(vide, vide);
        
        servletContext.setAttribute("listeTypeEntreprise", listeTypeEntreprise);
        servletContext.setAttribute("listeTypeStage", listeTypeStage);
        servletContext.setAttribute("listeTypeConvention", listeTypeConvention);
        servletContext.setAttribute("listeTailleEntreprise", listeTailleEntreprise);
        servletContext.setAttribute("listeDesPays", listeDesPays);
        servletContext.setAttribute("listeDesEtatsStage", listeDesEtatsStage);
	
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	

}
