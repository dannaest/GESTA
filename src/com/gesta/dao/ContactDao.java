package com.gesta.dao;

import java.util.List;

import com.gesta.beans.Contact; 

public interface ContactDao {
	long creer( Contact contact ) throws DAOException;
	
	Contact trouver( String email ) throws DAOException;
	
	List<Contact> lister( String[] fields, String[] values ) throws DAOException;
	
	public boolean mettreAJour(Contact old_cnt, Contact cnt)throws DAOException;
}
