package com.gesta.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 

import com.gesta.beans.Pays;
import com.gesta.dao.DAOException;
import com.gesta.dao.DAOFactory;
import com.gesta.dao.DAOMereImpl;

import static com.gesta.dao.DAOUtilitaire.*;

public class PaysDaoImpl extends DAOMereImpl {
	private static final String S_SQL_SELECT = "SELECT * FROM pays ";
	private static final String S_SQL_INSERT = "INSERT INTO pays (pay_nom) VALUES (?)";
	private static final String S_SQL_DELETE = "DELETE FROM pays ";
	private static final String S_SQL_UPDATE = "UPDATE pays SET ";
	
	public PaysDaoImpl(DAOFactory daoFactory) {
		super(daoFactory);
		this.SQL_INSERT = S_SQL_INSERT;
		this.SQL_SELECT = S_SQL_SELECT;
		this.SQL_UPDATE = S_SQL_UPDATE;
		this.SQL_DELETE = S_SQL_DELETE;
	}
 
	public List<Pays> lister(String[] fields, String[] values) throws DAOException {  
		List<Pays> pays = new ArrayList<Pays>();
	    try {
	    	super.resultSet = super.listerMere(fields, values);
		    while ( super.resultSet.next() ) {
	        	pays.add(map( resultSet ));
	        }
		   
	    }catch (SQLException e) {
	    	throw new DAOException (e.getMessage());
		}finally {
			//fermetureSilencieuse(resultSet);
			fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}
	  
	    return pays;
	}
	
	
	public Long creer(Pays pays) throws DAOException{
		String[] values = {pays.getPay_nom()};
		return super.creerMere(pays, values, true);  
	}

	
	public boolean supprimer(Pays pays) throws DAOException{
		//Suppression avec 2 cl�s primaires
		String[] fields = {"id_pays"};
		String[] values = {String.valueOf(pays.getId_pays())};
		int retour = super.supprimerMere(pays.getId_pays(),null,fields,values); 
		return retour==0 ? false:true;
	}

	
	public boolean mettreAJour(Pays old_pays, Pays new_pays)throws DAOException{
		String[] fields = {"pay_nom"};
		String[] values = {new_pays.getPay_nom()}; 
		String[] whereFields = fields;
		int retour = super.mettreAJourMere(old_pays.getId_pays(), null,fields, values, whereFields);
		return retour == 0 ? false : true; 
	}
	
	
	
	/*
     * Simple m�thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des contacts (un
     * ResultSet) et un bean Contact.
     */
    private static Pays map( ResultSet resultSet ) throws SQLException {    	
    	Pays pays = new Pays();
    	pays.setId_pays(resultSet.getLong( "id_pays" ));   
    	pays.setPay_nom(resultSet.getString( "pay_nom" ));  
    	return pays; 
    }
	

}
