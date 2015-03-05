package com.gesta.dao;

import java.util.List;

import com.gesta.beans.User;

public interface UserDao {
	
	long creer( User utilisateur ) throws DAOException;
	
	User trouver( String email ) throws DAOException;
	
	List<User> lister( String[] fields, String[] values ) throws DAOException;
	List<User> listerEleves(User user)throws DAOException;
	 
}
