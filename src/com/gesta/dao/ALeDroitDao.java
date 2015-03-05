package com.gesta.dao;

import java.util.List;

import com.gesta.beans.ALeDroit; 

public interface ALeDroitDao {
	long creer( ALeDroit aledroit ) throws DAOException;
	
	ALeDroit trouver( Long id ) throws DAOException;
	
	List<ALeDroit> lister( String[] fields, String[] values ) throws DAOException;
	
	boolean mettreAJour(Long id) throws DAOException;
	
	boolean supprimer(Long id) throws DAOException;
}
