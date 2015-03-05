package com.gesta.dao;

import com.gesta.beans.Utilisateur;

public interface UtilisateurDao {
	void creer( Utilisateur utilisateur ) throws DAOException;
	
	Utilisateur trouver( String email ) throws DAOException;

}
