package com.gesta.dao;

import java.util.List;

import com.gesta.beans.Options; 

public interface OptionDao {
	long creer( Options option ) throws DAOException;
	
	Options trouver( String email ) throws DAOException;
	
	List<Options> lister( String[] fields, String[] values ) throws DAOException;
}
